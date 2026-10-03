/* Vehicle Rental UI - shared engine. Each page sets window.CFG (see the page's HTML). */
const C = window.CFG, DEMO = window.DEMO === true, PER = 6;
const TONES = ['blue','green','purple','amber','teal','rose','indigo'];
const ID_ERR = {CustomerId:'customerId', VehicleId:'vehicleId', DriverId:'driverId', BookingId:'bookingId', TripId:'tripId'};
const P = {
  dash:'<rect x="3" y="3" width="7" height="9" rx="1"/><rect x="14" y="3" width="7" height="5" rx="1"/><rect x="14" y="12" width="7" height="9" rx="1"/><rect x="3" y="16" width="7" height="5" rx="1"/>',
  user:'<circle cx="12" cy="8" r="4"/><path d="M4 21c0-4 4-6 8-6s8 2 8 6"/>',
  car:'<path d="M3 13l2-6h14l2 6v5h-2M3 13v5h2M3 13h18"/><circle cx="7.5" cy="17.5" r="2"/><circle cx="16.5" cy="17.5" r="2"/>',
  wheel:'<circle cx="12" cy="12" r="9"/><circle cx="12" cy="12" r="2"/><path d="M12 14v7M10 11.5L3.5 10M14 11.5l6.5-1.5"/>',
  cal:'<rect x="3" y="5" width="18" height="16" rx="2"/><path d="M8 3v4M16 3v4M3 10h18"/>',
  pay:'<rect x="2" y="5" width="20" height="14" rx="2"/><path d="M2 10h20M6 15h4"/>',
  pin:'<path d="M12 21s7-6 7-11a7 7 0 10-14 0c0 5 7 11 7 11z"/><circle cx="12" cy="10" r="2.5"/>',
  chat:'<path d="M21 12a8 8 0 01-11.5 7.2L3 21l1.8-5.5A8 8 0 1121 12z"/>',
  gear:'<circle cx="12" cy="12" r="3"/><path d="M12 2v3M12 19v3M2 12h3M19 12h3M5 5l2 2M17 17l2 2M19 5l-2 2M7 17l-2 2"/>',
  eye:'<path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"/><circle cx="12" cy="12" r="3"/>',
  edit:'<path d="M12 20h9M16.5 3.5a2.1 2.1 0 013 3L7 19l-4 1 1-4z"/>',
  del:'<path d="M3 6h18M8 6V4h8v2M6 6l1 14h10l1-14"/>',
  plus:'<path d="M12 5v14M5 12h14"/>',
  search:'<circle cx="11" cy="11" r="7"/><path d="M21 21l-4.3-4.3"/>',
  db:'<ellipse cx="12" cy="5" rx="8" ry="3"/><path d="M4 5v14c0 1.7 3.6 3 8 3s8-1.3 8-3V5M4 12c0 1.7 3.6 3 8 3s8-1.3 8-3"/>',
  chev:'<path d="M6 9l6 6 6-6"/>',
  person:'<circle cx="12" cy="8" r="4.5"/><path d="M3 24c0-5 4-8 9-8s9 3 9 8z"/>'
};
const NAV = [['Customers','user',8081],['Vehicles','car',8082],['Drivers','wheel',8083],['Bookings','cal',8084],['Payments','pay',8085],['Trips','pin',8086],['Feedback','chat',8087]];
const HOME = 'http://localhost:8081/dashboard';
const url = (t, port) => `http://localhost:${port}/` + t.replace(/s$/, '').toLowerCase();
const $ = id => document.getElementById(id);
const svg = k => `<svg viewBox="0 0 24 24">${P[k]}</svg>`;
const esc = v => String(v ?? '').replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const g = (r, k) => r[k] ?? r[Object.keys(r).find(x => x.toLowerCase() === k.toLowerCase())] ?? ((C.alias || {})[k] || []).map(a => r[a]).find(v => v != null);
const ini = n => String(n || '?').trim().split(/\s+/).map(w => w[0]).slice(0, 2).join('').toUpperCase();
const NUM = f => f.t === 'int' || f.t === 'phone';
const S = {page:1};
let data = [], tm;
let mock = (C.demo || []).map(x => ({...x})), nid = mock.reduce((m, x) => Math.max(m, x.id), 0) + 1;

function link(i, t, href, cls = ''){ return `<a href="${href}" class="${cls}" onclick="nav(event,'${t}')">${svg(i)}<span>${t}</span></a>`; }
function nav(e, t){
  if(t === C.title){ e.preventDefault(); return; }
  if(DEMO){ e.preventDefault(); toast('Demo: ' + t + ' page opens here'); }
}
document.title = 'Vehicle Rental - ' + C.title;
document.body.insertAdjacentHTML('afterbegin', `
<aside><div class="brand"><div class="logo"><i>c</i></div><span>Vehicle Rental</span></div>
  <nav>${link('dash','Dashboard',HOME)}${NAV.map(([t, i, p]) => link(i, t, url(t, p), t === C.title ? 'on' : '')).join('')}${link('gear','Settings','#','set')}</nav></aside>
<div class="right">
  <header>
    <div class="crumb">Dashboard / <b>${C.title}</b></div>
    <label class="search">${svg('search')}<input id="q" inputmode="numeric" maxlength="10" autocomplete="off" placeholder="Search ${C.one.toLowerCase()} by ID..." oninput="onSearch(this)"></label>
    <div class="tools">
      <a class="db" href="http://localhost:8761" target="_blank" rel="noopener" title="Eureka registry">${svg('db')}</a>
      <div class="me"><span class="pic"><svg viewBox="0 0 24 24">${P.person}</svg></span><div><b>Uday Chourasiya</b><small>udaychourasiya221@gmail.com</small></div>
        <svg viewBox="0 0 24 24" style="width:16px;height:16px;color:#8a97a8">${P.chev}</svg></div>
    </div>
  </header>
  <main>
    <div class="bar"><div><h2>${C.title}</h2><p>Add, view, edit and delete ${C.title.toLowerCase()}.</p></div>
      <button class="add" onclick="openM('add')">${svg('plus')}Add ${C.one}</button></div>
    <section class="panel"><div class="tw"><table><thead><tr><th>ID</th>${C.cols.map(c => `<th>${c.l}</th>`).join('')}<th>Actions</th></tr></thead><tbody id="rows"></tbody></table></div>
      <div class="pg" id="pg"></div></section>
  </main>
  <div class="ov" id="ov" onclick="if(event.target===this)closeM()"><div class="dlg" id="dlg"></div></div>
</div>
<div id="toast"></div>`);

function toast(m, err){
  const t = $('toast'); t.textContent = m; t.className = 'show' + (err ? ' err' : '');
  clearTimeout(t._t); t._t = setTimeout(() => t.className = '', 2800);
}
async function api(path = '', opt = {}){
  const m = opt.method || 'GET';
  if(DEMO){
    const [p, qs] = path.split('?'), id = parseInt(p.replace('/', '')), q = new URLSearchParams(qs || '');
    if(m === 'GET'){
      if(isNaN(id)) return mock.map(x => ({...x}));
      const r = mock.find(x => x.id === id);
      if(!r) throw Object.assign(new Error('Not found'), {status:404});
      return {...r};
    }
    if(m === 'POST'){
      const b = JSON.parse(opt.body);
      for(const [pk, fk] of Object.entries(C.params || {})) b[fk] = Number(q.get(pk));
      if(C.auto) b[C.auto] = new Date().toISOString().slice(0, 10);
      b.id = nid++; mock.push(b); return b;
    }
    if(m === 'PUT'){ const b = JSON.parse(opt.body); mock = mock.map(x => x.id === b.id ? b : x); return b; }
    if(m === 'DELETE'){ mock = mock.filter(x => x.id !== id); return null; }
  }
  if(opt.body) opt.headers = {'Content-Type':'application/json'};
  let r;
  try{ r = await fetch(C.api + path, opt); }
  catch{ toast(`Cannot reach ${C.svc} (port ${C.port}). Check that it is running and CORS is enabled.`, true); throw Object.assign(new Error('net'), {status:0}); }
  if(!r.ok){
    const t = await r.text().catch(() => '');
    const e = Object.assign(new Error(t && t.length < 120 && !t.startsWith('{') ? t : 'Server error ' + r.status), {status:r.status});
    if(!opt.silent && !(opt.quiet && (r.status === 404 || r.status === 400))) toast(e.message, true);
    throw e;
  }
  return r.status === 204 ? null : r.json();
}

/* Search: ID only - shows just the record with that exact ID */
function onSearch(el){ el.value = el.value.replace(/\D/g, ''); S.page = 1; clearTimeout(tm); tm = setTimeout(run, 300); }
async function run(){
  const q = $('q').value.trim();
  try{ data = q ? [await api('/' + q, {quiet:true})] : await api(); }catch{ data = []; }
  data.sort((a, b) => a.id - b.id);
  draw();
}

const cell = (r, c) => c.k === C.main
  ? `<div class="who"><span class="av ${TONES[r.id % TONES.length]}">${esc(ini(g(r, c.k)))}</span>${esc(g(r, c.k))}</div>`
  : `<div class="tx" title="${esc(g(r, c.k))}">${esc(g(r, c.k))}</div>`;
function go(p){ S.page = p; draw(); }
function draw(){
  const pages = Math.max(1, Math.ceil(data.length / PER));
  S.page = Math.min(S.page, pages);
  const st = (S.page - 1) * PER, rs = data.slice(st, st + PER), q = $('q').value.trim();
  $('rows').innerHTML = rs.map(r => `<tr><td>#${r.id}</td>${C.cols.map(c => `<td>${cell(r, c)}</td>`).join('')}
    <td><div class="acts">
      <button class="ib v" title="View" onclick="openM('view',${r.id})">${svg('eye')}</button>
      <button class="ib e" title="Edit" onclick="openM('edit',${r.id})">${svg('edit')}</button>
      <button class="ib d" title="Delete" onclick="askDel(${r.id})">${svg('del')}</button></div></td></tr>`).join('')
    || `<tr class="empty"><td colspan="${C.cols.length + 2}">${q ? `No ${C.one.toLowerCase()} found with ID ${esc(q)}.` : `No ${C.title.toLowerCase()} yet.`}</td></tr>`;
  $('pg').innerHTML = `<span>Showing ${data.length ? st + 1 : 0}–${Math.min(st + PER, data.length)} of ${data.length} ${C.title.toLowerCase()}</span><div>
    <button ${S.page === 1 ? 'disabled' : ''} onclick="go(${S.page - 1})">‹</button>
    ${Array.from({length:pages}, (_, i) => `<button class="${i + 1 === S.page ? 'cur' : ''}" onclick="go(${i + 1})">${i + 1}</button>`).join('')}
    <button ${S.page === pages ? 'disabled' : ''} onclick="go(${S.page + 1})">›</button></div>`;
}

const fieldsFor = mode => C.fields.filter(f => !(f.editOnly && mode === 'add'));
function closeM(){ $('ov').classList.remove('show'); }
function openM(mode, id){
  const c = data.find(r => r.id === id) || {}, ro = mode === 'view';
  $('dlg').innerHTML = `<h3>${mode === 'add' ? 'Add ' : mode === 'edit' ? 'Edit ' : ''}${C.one}${ro ? ' Details' : ''}</h3>
    <p>${mode === 'add' ? `Enter the details of the new ${C.one.toLowerCase()}.` : C.one + ' #' + c.id}</p>` +
    fieldsFor(mode).map(f => `<label>${f.l}<input id="f_${f.k}" type="${f.t === 'date' ? 'date' : 'text'}" ${NUM(f) ? 'inputmode="numeric"' : ''} ${f.t === 'phone' ? 'maxlength="10"' : ''} value="${esc(g(c, f.k))}" ${ro || (mode === 'edit' && f.lock) ? 'disabled' : ''}><span class="err" id="e_${f.k}"></span></label>`).join('') +
    `<div class="row"><button class="b2" onclick="closeM()">${ro ? 'Close' : 'Cancel'}</button>
    ${ro ? '' : `<button class="b1" onclick="save(${c.id || 0})">${mode === 'add' ? 'Save ' : 'Update '}${C.one}</button>`}</div>`;
  $('ov').classList.add('show');
  const first = $('dlg').querySelector('input:not([disabled])'); if(first) first.focus();
}
function chk(f, v){
  if(!v) return f.l + ' is required';
  if(f.t === 'email' && !/^\S+@\S+\.\S+$/.test(v)) return 'Enter a valid email';
  if(f.t === 'phone' && !/^\d{10}$/.test(v)) return 'Enter a 10-digit mobile number';
  if(f.t === 'int'){
    const min = f.min ?? 1, max = f.max ?? 2147483647;
    if(!/^\d+$/.test(v) || +v < min || +v > max) return f.max ? `Enter a number from ${min} to ${max}` : 'Enter a valid number';
  }
  return '';
}
async function save(id){
  const vals = {}; let bad = false;
  for(const f of fieldsFor(id ? 'edit' : 'add')){
    const el = $('f_' + f.k); if(el.disabled) continue;
    const v = el.value.trim(), e = chk(f, v);
    $('e_' + f.k).textContent = e; if(e) bad = true;
    vals[f.k] = NUM(f) ? Number(v) : v;
  }
  if(bad) return;
  for(const [k, list] of Object.entries(C.alias || {})) if(k in vals) list.forEach(a => vals[a] = vals[k]);
  try{
    if(id){
      const o = data.find(r => r.id === id);
      await api(C.putId ? '/' + id : '', {method:'PUT', body:JSON.stringify({...o, ...vals, id}), silent:true});
    }else{
      const q = new URLSearchParams(), body = {};
      for(const [k, v] of Object.entries(vals)){
        const pk = Object.keys(C.params || {}).find(x => C.params[x] === k);
        pk ? q.append(pk, v) : body[k] = v;
      }
      await api(q.toString() ? '?' + q : '', {method:'POST', body:JSON.stringify(body), silent:true});
    }
    closeM(); toast(`${C.one} ${id ? 'updated' : 'added'}`); await run();
  }catch(err){
    const hit = Object.keys(ID_ERR).find(k => err.message && err.message.toLowerCase().includes(k.toLowerCase()));
    const fk = hit && ID_ERR[hit];
    if(fk && $('e_' + fk)) $('e_' + fk).textContent = err.message;
    else toast(err.message || 'Something went wrong', true);
  }
}
function askDel(id){
  $('dlg').innerHTML = `<h3>Delete ${C.one.toLowerCase()}?</h3><p>${C.one} #${id} will be removed permanently.</p>
    <div class="row"><button class="b2" onclick="closeM()">Cancel</button><button class="b1 red" onclick="del(${id})">Delete</button></div>`;
  $('ov').classList.add('show');
}
async function del(id){ try{ await api('/' + id, {method:'DELETE'}); closeM(); toast(`${C.one} deleted`); await run(); }catch{} }
document.addEventListener('keydown', e => { if(e.key === 'Escape') closeM(); });
run();
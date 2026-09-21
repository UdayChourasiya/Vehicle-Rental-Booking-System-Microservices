package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY )
private int id;
private String Vehiclename;
private String model;
private String type;
private int dailyfee;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getVehiclename() {
	return Vehiclename;
}
public void setVehiclename(String vehiclename) {
	Vehiclename = vehiclename;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public int getDailyfee() {
	return dailyfee;
}
public void setDailyfee(int dailyfee) {
	this.dailyfee = dailyfee;
}


}

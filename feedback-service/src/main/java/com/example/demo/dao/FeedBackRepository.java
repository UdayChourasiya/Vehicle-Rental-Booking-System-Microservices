package com.example.demo.dao;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FeedBack;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {

}

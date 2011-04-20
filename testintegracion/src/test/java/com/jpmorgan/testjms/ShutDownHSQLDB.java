package com.jpmorgan.testjms;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShutDownHSQLDB {

	public static void main(String args[]) {

		System.out.println("RECONTRA COME COSAS!!!!");
		BeanFactory bf = new ClassPathXmlApplicationContext("classpath:testjms_spring.xml");
		DataSource datasource = (DataSource) bf.getBean("datasource");
		try {
			Connection con  = datasource.getConnection();
			String sql = "SHUTDOWN";
			Statement stmt = con.createStatement();
			stmt.execute(sql);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}

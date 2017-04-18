/**
 * 
 */
package com.ailk.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Administrator
 *
 */
public class ConnectionFactory
{
	public static Connection getConnection()
	{
		Connection conn=null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.21.20.58:1521:hicrmtst","UCR_JOUR1","123");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.238.99.133:1521:crmdev","UCR_JOUR","UCR_JOUR");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.200.138.9:1521:crmcs","UCR_FILE2","1q1w1e1r");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.200.138.9:1521:crmcs","UCR_JOUR2","1q1w1e1r");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.200.138.9:1521:crmcs","UCR_CP","1q1w1e1r");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.200.138.9:1521:crmcs","UCR_CRM1","1q1w1e1r");
		}catch(Exception e)
		{
			System.out.println("1111111111111");
			e.printStackTrace();
		}
		return conn;
	}
}

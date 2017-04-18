/**
 * 
 */
package com.ailk.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class DBTest
{

	public static void main(String[] args) throws Exception
	{
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement statement = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from om_line where order_line_id = '9116121948265481'");
		
		statement = conn.prepareStatement(sql.toString());
		ResultSet rs = statement.executeQuery();
		rs.next();
		System.out.println(rs.getString("CREATE_DATE"));
		
		statement.close();
		conn.close();
	}
}

package com.ailk.sqlutil;

import java.io.IOException;

import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import com.ailk.constant.Constant;
import com.ailk.customvar.LowerFirstCharacter;
import com.ailk.customvar.UpperFirstCharacter;
import com.ailk.db.ConnectionFactory;
import com.ailk.db.DBHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SelAllColSql {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Scanner input=new Scanner(System.in);   
		String tabName =input.next();
		input.close();
		Connection conn = null;
		PreparedStatement statement = null;
		String name = "A";
		try{
			conn = ConnectionFactory.getConnection();
			statement = conn.prepareStatement((new StringBuilder()).append("select * from ").append(tabName.toUpperCase()).append(" where 1 = 0").toString());
		    ResultSetMetaData metaData = statement.executeQuery().getMetaData();
	        int i = 1;
	        StringBuilder sb= new StringBuilder();
			sb.append("SELECT ").append("\n");
			
	        for(int cnt = metaData.getColumnCount(); i <= cnt; i++)
	        {
	        	String colName =metaData.getColumnName(i).toUpperCase();
	        	if(metaData.getColumnType(i) == 93)//日期类型
				{
					sb.append("TO_CHAR(").append(name).append(".").append(colName).append(",'").append(Constant.TIME_PATTERN_STAND).append("') ").append(colName);
				}else
				{	
					sb.append(name).append(".").append(colName);
				}
				if(i<cnt)
				{
					sb.append(",");
				}
				if(i%5 == 0)
				{
					sb.append("\n");
				}
//				sb.append("\n");
				
	        }
	        sb.append("\n");
	        sb.append("FROM ").append(StringUtils.upperCase(tabName)).append(" ").append(name);
			System.out.println(sb.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(null!=statement)
			{
				statement.close();
			}
			
			if(null!=conn)
			{
				conn.close();
			}
		}
	}
}

package com.ai.ipaas.bc.common.auto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ailk.db.ConnectionFactory;
import com.ailk.db.DBHelper;
 
public class GenFinishSql {

	public static void main(String[] args) throws Exception
	{
		String omTableName = "UM_SUBSCRIBER_CHA";
		String partitionCol = "UM_SUBSCRIBER_INS_ID";
		String[] updCol = new String[] {"SUBSCRIBER_INS_ID","PARTITION_ID"};
		GenFinishSql.run(omTableName, partitionCol, updCol);
	}
	
	public static String run(String omTableName, String partitionCol, String[] updCol) throws Exception
	{
		String umTableName = omTableName.replace("OM_", "UM_");
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement statement = null;
		List<String> columnNames = new ArrayList<String>();
		statement = conn.prepareStatement((new StringBuilder()).append("select * from ").append(umTableName.toUpperCase()).append(" where 1 = 0").toString());
	    ResultSetMetaData metaData = statement.executeQuery().getMetaData();
		
		String[] cols = DBHelper.getColumnNames(conn, omTableName, false);
//		StringBuffer selSql = new StringBuffer();
//		selSql.append(" SELECT * FROM ").append(omTableName).append("\n");
//		selSql.append(" WHERE ORDER_LINE_ID = :ORDER_LINE_ID");
		
//		System.out.println("-----------查询语句--------------");
//		System.out.println(selSql);
		
		/*
		StringBuffer insSql = new StringBuffer();
		insSql.append(" INSERT INTO ").append(umTableName).append("\n");
		insSql.append("(");
		if(StringUtils.isNotBlank(partitionCol))
		{
//			insSql.append("PARTITION_ID, ");
		}
		for(int i = 0, size = cols.length; i < size; i++)
		{
			if(isFilterCol(cols[i]))
			{
				continue;
			}
			
			insSql.append(cols[i]);
			if(i != cols.length - 1)
			{
				insSql.append(", ");
			}
			if((i+1)%7 == 0 && i != cols.length - 1)
			{
				insSql.append("\n");
			}
		}
		insSql.append(")\n");
		insSql.append(" VALUES").append("\n");
		insSql.append("(");
		if(StringUtils.isNotBlank(partitionCol))
		{
//			insSql.append("MOD(:").append(partitionCol).append(", 10000), ");
		}
		for(int i = 0, size = cols.length; i < size; i++)
		{
			if(isFilterCol(cols[i]))
			{
				continue;
			}
			
			
			if(metaData.getColumnType(i+1) == 93)//日期
			{
				insSql.append("TO_DATE(:").append(cols[i]).append(", 'YYYY-MM-DD HH24:MI:SS')");
			}
			else
			{
				insSql.append(":").append(cols[i]);
			}
			
			
			if(i != cols.length - 1)
			{
				insSql.append(", ");
			}
			if((i+1)%7 == 0)
			{
				insSql.append("\n");
			}
		}
		insSql.append(")");
		
//		System.out.println("-----------INSERT语句--------------");
//		System.out.println(insSql);
	*/
		
		
		boolean flag = false;
		StringBuffer updSql = new StringBuffer();
		updSql.append(" UPDATE ").append(umTableName).append("\n");
		updSql.append(" SET ");
		for(int i = 1, size = metaData.getColumnCount(); i <= size; i++)
		{
			String colName = metaData.getColumnName(i).toUpperCase();
			flag = false;
			if(isFilterCol(colName))
			{
				continue;
			}
			for(int j = 0, size1 = updCol.length; j < size1; j++)
			{
				if(colName.equals(updCol[j]))
				{
					flag = true;
					break;
				}
			}
			if(flag)
			{
				continue;
			}
			if(metaData.getColumnType(i) == 93)//日期
			{
				updSql.append(colName).append(" = ").append("TO_DATE(:").append(colName).append(", 'YYYY-MM-DD HH24:MI:SS')");
			}
			else
			{
				updSql.append(colName).append(" = ").append(":").append(colName);
			}
			
			if(i != size)
			{
				updSql.append(", ");
			}
			if((i+1)%7 == 0 && i != size)
			{
				updSql.append("\n");
			}
		}
		updSql.append("\n");
		for(int i = 0, size = updCol.length; i < size; i++)
		{
			if(i == 0)
			{
				updSql.append(" WHERE ").append(updCol[i]).append(" = :").append(updCol[i]);
			}
			else
			{
				updSql.append(" AND ").append(updCol[i]).append(" = :").append(updCol[i]);
			}
			
		}
		
		
//		System.out.println("-----------UPDATE语句--------------");
//		System.out.println(updSql);
		
		
		/*
		StringBuffer delSql = new StringBuffer();
		delSql.append(" UPDATE ").append(umTableName).append("\n");
		delSql.append(" SET EXPIRE_DATE = TO_DATE(:EXPIRE_DATE, 'YYYY-MM-DD HH24:MI:SS'), DONE_DATE = TO_DATE(:DONE_DATE, 'YYYY-MM-DD HH24:MI:SS'), OP_ID = :OP_ID, ORG_ID = :ORG_ID");
		delSql.append("\n");
		for(int i = 0, size = updCol.length; i < size; i++)
		{
			if(i == 0)
			{
				delSql.append(" WHERE ").append(updCol[i]).append(" = :").append(updCol[i]);
			}
			else
			{
				delSql.append(" AND ").append(updCol[i]).append(" = :").append(updCol[i]);
			}
			
		}
//		delSql.append(" WHERE ").append(pkCol).append(" = :").append(pkCol);
//		if(StringUtils.isNotBlank(partitionCol))
//		{
//			delSql.append(" AND PARTITION_ID = MOD(:").append(partitionCol).append(", 10000) ");
//		}
		
//		System.out.println("-----------DELETE语句--------------");
//		System.out.println(delSql);
*/
 		
		
		return updSql.toString();
	}
	
	private static boolean isFilterCol(String colName) throws Exception
	{
		boolean flag = false;
		Map<String, String> filterCol = new HashMap<String, String>();
		filterCol.put("DONE_CODE", "DONE_CODE");
		filterCol.put("ORDER_ID", "ORDER_ID");
		filterCol.put("ORDER_LINE_ID", "ORDER_LINE_ID");
		filterCol.put("ORDER_ITEM_ID", "ORDER_ITEM_ID");
		filterCol.put("ACTION", "ACTION");
		
		if(filterCol.containsKey(colName))
		{
			flag = true;
		}
		
		if(colName.indexOf("KID") != -1)
		{
			flag = true;
		}
		
		return flag;
	}
}

package com.ailk.check;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ailk.db.ConnectionFactory;
 
public class SqlCheckTools {

	public static void main(String[] args) throws Exception
	{
		String sqls = "SEL_TRADE_SMS_INTF--SEL_TRADESMS_PARA--SEL_TRADE_SMS_BY_KYES--SEL_ELEMENT_TWOCHECK--";
		Connection conn = null;
		PreparedStatement statement = null;
		String sqlRef = null;
		try
		{
			File dir = new File("D:\\hnan_new\\sql\\serv\\TD_B_TRADE_SMS");
			conn = ConnectionFactory.getConnection();
			File[] fList = dir.listFiles();
			String[] tmpArrSql = sqls.split("--"); 
			for(File f : fList)
			{
				boolean isFound = false;
				sqlRef = f.getName().replace(".sql", "");
				for(int i = 0; i < tmpArrSql.length; i++)
				{
					if(sqlRef.equals(tmpArrSql[i]))
					{
						isFound = true;
						break;
					}
				}
				if(isFound)
				{
//					System.out.println(sqlRef);
					InputStream fis = new FileInputStream(f);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] rtn = SqlCheckTools.readLine(baos, fis);
					
					String content = new String(rtn).toUpperCase();
					int index = content.indexOf("TD_B_TRADE_SMS");
					int whereIndex = content.indexOf("WHERE");
					content = content.substring(0, index) + content.substring(index, whereIndex) + " where 1=2";
//					System.out.println(content);
					StringBuilder sql = new StringBuilder(content);
					statement = conn.prepareStatement(content);
					statement.executeQuery();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("SQL名字>>>>>>>>>>>" + sqlRef);
		}
		finally
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
	
	public static byte[] readLine(ByteArrayOutputStream baos, InputStream in) throws IOException {
		byte[] rtn = null;

		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			

			baos.write(b, 0, 1);
		}

		rtn = baos.toByteArray();
		
		baos.reset();
		
		return rtn;
	}
	
	
}

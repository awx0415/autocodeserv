package com.ailk.sqlutil;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.ailk.db.ConnectionFactory;
import com.ailk.util.InReadUtil;
 
/***
 * 
 */
public class RemoveFiledName {

	public static void main(String[] args) throws Exception
	{
		Connection conn = null;
		PreparedStatement statement = null;
		
		List filedNameList = new ArrayList();
		String sqls = "SEL_BY_EPARCHY_CODE--SEL_BY_OPER_CODE--SEL_BY_PK--SEL_BY_SVCID--SEL_BY_SVCID_OLDSTATE--SEL_NEW_STATE_CODE--";
		String[] tmpArrSql = sqls.split("--"); 
		
		String dirName = "TD_S_TRADE_SVCSTATE";
		File dir = new File("D:\\hnan_new\\sql\\serv\\"+dirName);
		File[] fList = dir.listFiles();
		String sqlRef = null;
		try
		{
			for(File f : fList)
			{
				boolean isFound = false;
				filedNameList.clear();
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
					InputStream fis = new FileInputStream(f);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] rtn = InReadUtil.readLine(baos, fis);
					
					String content = new String(rtn);
					String tmpContent = content.toUpperCase();
					if(tmpContent.indexOf("IS_CACHE=Y") == -1)
					{
						continue;
					}
					
					int fromIndex = content.toUpperCase().indexOf("FROM");
					String tmpBeforeFromSql = content.substring(0, fromIndex);
					String tmpBeginFromSql = content.substring(fromIndex, content.length());
					
					String[] arrTmpBeforeFromSql = tmpBeforeFromSql.split(",");
					for(int i = 0; i < arrTmpBeforeFromSql.length ; i++)
					{
						filedNameList.add(arrTmpBeforeFromSql[i]);
					}
					
					StringBuilder sqlBuf = new StringBuilder();
					for(int i = 0; i < filedNameList.size(); i++)
					{
						String fieldName = (String)filedNameList.get(i);
						
						if(fieldName.toUpperCase().indexOf("REMARK") == -1
								&& fieldName.toUpperCase().indexOf("UPDATE_TIME") == -1
								&& fieldName.toUpperCase().indexOf("UPDATE_STAFF_ID") == -1
								&& fieldName.toUpperCase().indexOf("UPDATE_DEPART_ID") == -1
								)
						{
							sqlBuf.append(fieldName);
							sqlBuf.append(",");
						}
					}
					//去最后的逗号
					tmpBeforeFromSql = sqlBuf.substring(0, sqlBuf.length() -1);
					//写入文件
					String sql = tmpBeforeFromSql + " " + tmpBeginFromSql;//拼装后的SQL
					
					//这一段是校验
					conn = ConnectionFactory.getConnection();
					String tmpUpContent = sql.toUpperCase();
					int index = tmpUpContent.indexOf(dirName);
					int whereIndex = tmpUpContent.indexOf("WHERE");
					tmpUpContent = tmpUpContent.substring(0, index) + tmpUpContent.substring(index, whereIndex) + " where 1=2";
					statement = conn.prepareStatement(tmpUpContent);
					statement.executeQuery();
					
					//校验通过后写入文件
					FileOutputStream fos = new FileOutputStream(f);
					fos.write(sql.getBytes());
//					
					fos.flush();
					fos.close();	
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
	
	
}

package com.ailk.check;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.ailk.util.InReadUtil;

public class NeedCacheSqlCheck {

	public static void main(String[] args) throws Exception {
		String problemSqlfilePath = "D:\\没有加IS_CACHE=Y的SQL.txt";
		String dirName = "D:\\hnan_new\\sql\\serv";
		File rootDir = new File(dirName);
		File[] dirList = rootDir.listFiles();
		StringBuilder content = new StringBuilder(40000);
		for (File subDir : dirList) {
			if(subDir.isDirectory())
			{
				File[] fileList = subDir.listFiles();
				for (File file : fileList) {
					InputStream fis = new FileInputStream(file);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] rtn = InReadUtil.readLine(baos, fis);
					String sql = new String(rtn);
					
					if(NeedCacheSqlCheck.isNeedCacheSql(sql))
					{
						content.append("TAB_NAME:"+subDir.getName());
						content.append("--");
						content.append("SQL_REF:"+file.getName().replace(".sql",""));
						content.append("\n");
					}
				}
			}
			else
			{
				InputStream fis = new FileInputStream(subDir);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] rtn = InReadUtil.readLine(baos, fis);
				String sql = new String(rtn);
				
				if(NeedCacheSqlCheck.isNeedCacheSql(sql))
				{
					content.append("TAB_NAME:"+subDir.getName());
					content.append("--");
					content.append("SQL_REF:"+subDir.getName().replace(".sql",""));
					content.append("\n");
				}
			}
			
		}
		// 写进文件
		FileOutputStream fos = new FileOutputStream(problemSqlfilePath);
		fos.write(content.toString().getBytes());
		//
		fos.flush();
		fos.close();
	}
	
	/**
	 * *
	 * 判断该SQL是否需要加IS_CACHE=Y
	 * @param sql
	 * @return
	 */
	public static boolean isNeedCacheSql(String sql) 
	{
		boolean needAddIsCache = false;
		
		String firstField = sql.split(",")[0].toUpperCase();
		if (firstField.indexOf("IS_CACHE=Y") != -1) {
			//如果已经有IS_CACHE了，则不用再加了
			return false;
		}
		if (firstField.indexOf("SELECT ") == -1) {
			//如果开头不是SELECT则不用
			return false;
		}
		if (firstField.indexOf("DELETE ") != -1) {
			//如果开头是DELETE则不用
			return false;
		}
		if (firstField.indexOf("UPDATE ") != -1) {
			//如果开头是UPDATE则不用
			return false;
		}
		
		if (sql.toUpperCase().indexOf("TF_") == -1
				&& sql.toUpperCase().indexOf("TI_") == -1
				&& sql.toUpperCase().indexOf("TD_") != -1) {
			//如果表名仅有TD开头的，则是读的参数表
			needAddIsCache = true;
		}
		
		return needAddIsCache;
	}
}

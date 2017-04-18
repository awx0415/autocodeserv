package com.ailk.sqlutil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import com.ailk.util.InReadUtil;

/**
 * 根据SQL生成相应的SqlBuffer代码
 * @author Administrator
 *
 */
public class BuildSqlBuffer {

	public static void main(String[] args) throws Exception
	{
		InputStream input = System.in;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		String sql = "";
		char endFlag = 59;
		byte[] rtn = InReadUtil.readLine(baos, input, endFlag);
		
		sql = new String(rtn);
		//sql = sql.toUpperCase();
		input.close();
		
//		System.out.println("/*");
//		System.out.println(sql);
//		System.out.println("*/");
		
		BufferedReader br = new BufferedReader(new StringReader(sql));
		String line = br.readLine();
		
		System.out.println("StringBuilder sql = new StringBuilder(1000);");
//		System.out.println("");
		while (line != null) {
			System.out.println("sql.append(\"" + line + " \");");
			line = br.readLine();
		}
		System.out.println("");
		System.out.println("IDataset ids = Dao.qryBySql(sql, param, routeEparchyCode);");
		System.out.println("return ids;");
	}
}

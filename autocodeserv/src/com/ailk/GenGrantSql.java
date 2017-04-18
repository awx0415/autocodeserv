package com.ailk;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
 
public class GenGrantSql {

	public static void main(String[] args) throws Exception
	{
		String domain = "CP";
		InputStream in = new FileInputStream("H:\\newtable.txt");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			baos.write(b, 0, 1);
		}
		
		String tables = new String(baos.toByteArray());
		
		baos.reset();
		in.close();
		
		String[] arrTableList = tables.split(",");
		for(String table : arrTableList)
		{
			StringBuilder sql = new StringBuilder();
			//grant select, insert, update, delete on UM_PROSECUTION to uop_file;
			sql.append("grant select, insert, update, delete on ").append(table.trim()).append(" to uop_").append(domain).append(";");
			System.out.println(sql);
		}
	}
}

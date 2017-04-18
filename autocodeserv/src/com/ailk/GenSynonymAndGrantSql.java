package com.ailk;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
 
public class GenSynonymAndGrantSql {

	public static void main(String[] args) throws Exception
	{
		String domain = "JOUR42";
		InputStream in = new FileInputStream("H:/J2EE/HNAN/dev/aicrm5/veris-app/order/doc/request_doc/alltables.txt");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			baos.write(b, 0, 1);
		}
		
		String tables = new String(baos.toByteArray());
		
		baos.reset();
		in.close();
		
		System.out.println("建同名--------------------");
		String[] arrTableList = tables.split("\n");
		for(String table : arrTableList)
		{
			StringBuilder sql = new StringBuilder();
			sql.append("create or replace synonym ").append(table.trim()).append(" for ucr_").append(domain).append(".").append(table.trim()).append(";");
			System.out.println(sql);
		}
		
		System.out.println("建赋权语句--------------------");
		for(String table : arrTableList)
		{
			StringBuilder sql = new StringBuilder();
			//grant select, insert, update, delete on UM_PROSECUTION to uop_file;
			sql.append("grant select, insert, update, delete on ").append(table.trim()).append(" to uop_").append(domain).append(";");
			System.out.println(sql);
		}
		
		System.out.println("删同义词--------------------");
		for(String table : arrTableList)
		{
			StringBuilder sql = new StringBuilder();
			//grant select, insert, update, delete on UM_PROSECUTION to uop_file;
			sql.append("drop synonym ").append(table.trim()).append(";");
			System.out.println(sql);
		}
	}
}

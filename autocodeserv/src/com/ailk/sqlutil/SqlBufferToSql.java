package com.ailk.sqlutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlBufferToSql {

	public static void main(String[] args) throws Exception
	{
		String sql = "select * from table1, table2";
		
		SqlBufferToSql.getTableName(sql);
		
		/*
		List list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		int i = 0;
		for(Iterator iter = list.iterator(); iter.hasNext(); )
		{
			i++;
			if(i == 4)
			{
				list.remove(2);
			}
			String str = (String)iter.next();
			
			System.out.println(str);
		}
		*/
	}
	
	public static void test(int i) throws Exception
	{
		i++;
	}
	
	public static void getTableName(String sql) throws Exception
	{
		String regex="(.*from\\s)(\\w*)(.*)";
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(sql);
		if(m.find()){
			System.out.println(m.group(2));
			String table=m.group().replace("from","");
			System.out.println(""+table.trim());
		}else{
			System.out.println("not found");
		}
	}
}

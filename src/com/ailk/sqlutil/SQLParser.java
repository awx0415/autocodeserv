package com.ailk.sqlutil;

import java.util.List;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
 
public class SQLParser {

	public static void main(String[] args) throws Exception
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM table1 a, table2 b where a.id = b.id");
		sql.append(" AND EXISTS (SELECT * FROM TABLE3 c");
		sql.append(" 			WHERE A.ID = C.ID)");
		
		Statement stmt = CCJSqlParserUtil.parse(sql.toString());
		Select selectStatement = (Select) stmt;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
		for(int i = 0; i < tableList.size(); i ++)
		{
			System.out.println(tableList.get(i));
		}
	}
}

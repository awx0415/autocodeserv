package com.ailk;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.customvar.LowerFirstCharacter;
import com.ailk.customvar.UpperFirstCharacter;
import com.ailk.db.ConnectionFactory;
import com.ailk.db.DBHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
 
public class GenSync {

	public static void main(String[] args) throws Exception{
		StringBuilder result = new StringBuilder(5000);
		String[] syncTables = getAllSyncTable();
		for(int i = 0; i < syncTables.length; i++)
		{
			result.append(getMethodContent(syncTables[i]));
			result.append("\n");
		}
		
		
	}
	
	public static String getMethodContent(String syncTable) throws Exception
	{
		Configuration cfg = new Configuration();
		String partitionCol = "USER_ID";
//		String syncTable = "TI_B_USER_SVC";
		String methodName = getMethodName(syncTable);
		String BOF_SYNC_TAB_NAME = getBofSyncTabName(syncTable);
		
		cfg.setClassForTemplateLoading(TableTradeDataBuilder.class, "/");
		Template t = cfg.getTemplate("freemarker/bof/sync.ftl");
				
		Map data = new HashMap();
		data.put("methodName", methodName);//
		data.put("BOF_SYNC_TAB_NAME", BOF_SYNC_TAB_NAME);//
		data.put("PARTITION_COL", partitionCol);
		data.put("SYNC_TABLE_NAME", syncTable);
		
//		PrintWriter pw = new PrintWriter();
		BufferedWriter out  = new BufferedWriter(new OutputStreamWriter(System.out));
//		OutputStream out=System.out;
		
		t.process(data, out);
		String result = "";
		out.write(result);
		
		return result;
	}

	public static String getMethodName(String tableName) throws Exception
	{
		String methodName = "sync";
		tableName = tableName.toLowerCase();
		String[] tmps = tableName.split("_");
		int length = tmps.length;
		for(int i = 2; i < length; i++)
		{
			methodName += tmps[i].substring(0, 1).toUpperCase() + tmps[i].substring(1);
		}		
		
		return methodName;
	}
	
	public static String getBofSyncTabName(String tableName) throws Exception
	{
		String name = "BOF_SYNC_";
		String[] tmps = tableName.split("_");
		int length = tmps.length;
		for(int i = 2; i < length; i++)
		{
			name += tmps[i];
		}		
		return name;
	}
	
	public static String[] getAllSyncTable() throws Exception
	{
		InputStream in = new FileInputStream("H:/J2EE/HNAN/dev/aicrm5/veris-app/order/doc/synctable.txt");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			baos.write(b, 0, 1);
		}
		
		String tables = new String(baos.toByteArray());
		
		baos.reset();
		in.close();
		
		String[] arrTableList = tables.split("\n");
		return arrTableList;
	}
}

package com.ailk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import freemarker.template.TemplateException;

/**
 * 根据表自动生成相应的TradeData对象
 * 使用到了tableTradeDataClass.ftl模板
 */
public class TableTradeDataBuilder {

	
	public static void main(String[] args) throws Exception{
		Configuration cfg = new Configuration();
		Connection conn = null;
		String dirPath = "D:/file/extend";
		try {
			String fileName = "Individual";
			
        	cfg.setClassForTemplateLoading(TableTradeDataBuilder.class, "/");
        	cfg.setSharedVariable("upperFC", new UpperFirstCharacter());
        	cfg.setSharedVariable("lowerFC", new LowerFirstCharacter());
			Template t = cfg.getTemplate("tableTradeDataClass.ftl");
			
			String tableName = "CB_INDIVIDUAL";
			
			Map data = new HashMap();
			data.put("package", "com.ai.of.cisf.domain.order.entity");//
			data.put("className", fileName);
			data.put("tableName", tableName);
			
			FileOutputStream fos = new FileOutputStream(dirPath + "/" + fileName + ".java");
			
			List pros = new ArrayList();
			conn = ConnectionFactory.getConnection();
			String[] cols = DBHelper.getColumnNames(conn, tableName, true);
			for(int i = 0, size = cols.length; i < cols.length; i++)
			{
				StringBuilder proName = new StringBuilder();
				String colName = cols[i];
				String[] tmpArr = colName.split("_");
				for(int j = 0, tmpArrsize = tmpArr.length; j < tmpArrsize; j++)
				{
					String tmpStr = tmpArr[j].toLowerCase();
					if(j != 0)
					{
						tmpStr = tmpStr.substring(0, 1).toUpperCase() + tmpStr.substring(1, tmpStr.length());
					}
					proName.append(tmpStr);
				}
				
				Map pro_1 = new HashMap();
				pro_1.put("proType", String.class.getSimpleName());
				pro_1.put("proName", proName.toString());
				pro_1.put("colName", colName);
				pros.add(pro_1);
			}
			data.put("properties", pros);
			
			List pros2 = new ArrayList();
			String[] cols2 = DBHelper.getColumnNames(conn, tableName, false);
			for(int i = 0, size = cols2.length; i < cols2.length; i++)
			{
				StringBuilder proName = new StringBuilder();
				String colName = cols2[i];
				String[] tmpArr = colName.split("_");
				for(int j = 0, tmpArrsize = tmpArr.length; j < tmpArrsize; j++)
				{
					String tmpStr = tmpArr[j].toLowerCase();
					if(j != 0)
					{
						tmpStr = tmpStr.substring(0, 1).toUpperCase() + tmpStr.substring(1, tmpStr.length());
					}
					proName.append(tmpStr);
				}
				
				Map pro_1 = new HashMap();
				pro_1.put("proType", String.class.getSimpleName());
				pro_1.put("proName", proName.toString());
				pro_1.put("colName", colName);
				pros2.add(pro_1);
			}
			data.put("properties2", pros2);
			
			t.process(data, new OutputStreamWriter(fos));//
			
			fos.flush();
			fos.close();
		} catch(Exception e)
		{
			e.printStackTrace();
		} finally
		{			
			if(null!=conn)
			{
				conn.close();
			}
		}
	}

}

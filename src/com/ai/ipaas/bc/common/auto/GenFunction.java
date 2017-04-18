package com.ai.ipaas.bc.common.auto;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ailk.customvar.LowerFirstCharacter;
import com.ailk.customvar.UpperFirstCharacter;

import freemarker.template.Configuration;
import freemarker.template.Template;
 
public class GenFunction {

	public static void main(String[] args) throws Exception
	{
		List<String> tables = new ArrayList<String>();
		tables.add("UM_SUBSCRIBER");
		tables.add("UM_SUBSCRIBER_REL");
		tables.add("UM_RES");
		tables.add("UM_SUBS_CYCLE");
		tables.add("UM_PROD_STA");
		tables.add("UM_MKT_RES");
		tables.add("UM_PROD");
		tables.add("UM_OFFER_CHA");
		tables.add("UM_PRICE_PLAN");
		tables.add("UM_BUNDLE_OFFER_REL");
//		tables.add("UM_OFFER");
		tables.add("UM_SALE_DEPOSIT");
		tables.add("UM_PLATSVC");
		tables.add("UM_SALE_ACTIVE");
		tables.add("AM_PAYMENT_METHOD");
		tables.add("CM_ACCOUNT");
		tables.add("AM_CHARGE_ITEM");
		tables.add("CM_ACCT_CYCLE");
		
		StringBuilder functionStr = new StringBuilder(1000);
		
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(NewGenEntity.class, "/");
		cfg.setSharedVariable("upperFC", new UpperFirstCharacter());
		cfg.setSharedVariable("lowerFC", new LowerFirstCharacter());
		Template t = cfg.getTemplate("freemarker/function.ftl");
		FileOutputStream fos = new FileOutputStream("D:\\function.txt");
		
		Map data = new HashMap();
		List pros = new ArrayList();
		for (int i = 0, size = tables.size(); i < size; i++)
		{
			StringBuilder proName = new StringBuilder();
			String tableName = tables.get(i);
			String[] tmpArr = tableName.split("_");
			for (int j = 0, tmpArrsize = tmpArr.length; j < tmpArrsize; j++)
			{
				String tmpStr = tmpArr[j].toLowerCase();
				if (j != 0)
				{
					tmpStr = tmpStr.substring(0, 1).toUpperCase() + tmpStr.substring(1, tmpStr.length());
				}
				proName.append(tmpStr);
			}

			Map pro_1 = new HashMap();
			pro_1.put("proName", proName.toString());
			pros.add(pro_1);
		}
		
		
		data.put("properties", pros);
		t.process(data, new OutputStreamWriter(fos));
		
		fos.flush();
		fos.close();
	}
}

package com.ailk.sqlutil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import com.ai.ipaas.bc.common.auto.GenFinishSql;
import com.ailk.util.InReadUtil;

/**
 * 根据SQL生成相应的SqlBuffer代码
 * @author Administrator
 *
 */
public class BuildSqlBufferForCisf {

	public static void main(String[] args) throws Exception
	{
//		InputStream input = System.in;
//		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		
//		String sql = "";
//		char endFlag = 59;
//		byte[] rtn = InReadUtil.readLine(baos, input, endFlag);
//		
//		sql = new String(rtn);
//		//sql = sql.toUpperCase();
//		input.close();
		
//		System.out.println("/*");
//		System.out.println(sql);
//		System.out.println("*/");
		
//		GenFinishSql.run("UM_OFFER_CHA", "SUBSCRIBER_INS_ID", "SUBSCRIBER_INS_ID");
		//AmPaymentMethod  AM_PAYMENT_METHOD
		String sql = GenFinishSql.run("TF_F_USER_PRODUCT", "SUBSCRIBER_INS_ID", new String[] {"INST_ID","PARTITION_ID"});
		
		BufferedReader br = new BufferedReader(new StringReader(sql));
		String line = br.readLine();
//		String line = GenFinishSql.run("UM_RES", "SUBSCRIBER_INS_ID", "SUBSCRIBER_INS_ID");
		
		System.out.println("StringBuilder sql = new StringBuilder(1000);");
//		System.out.println("");
		while (line != null) {
			System.out.println("sql.append(\"" + line + " \");");
			line = br.readLine();
		}
		System.out.println("");
		System.out.println("return sql.toString();");
		
	}
}

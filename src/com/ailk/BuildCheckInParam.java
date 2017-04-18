package com.ailk;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.ailk.util.InReadUtil;


public class BuildCheckInParam {

	public static void main(String[] args) throws Exception
	{
		checkParamForIBOSS();
	}
	
	/**
	 * 控制台里输入的格式为AAA,BBBB,CCCC
	 * 然后回车，则自动生成相应代码
	 * 自动生成入参校验的方法
	 * @author Administrator
	 *
	 */
	public static void checkParamForNormal() throws Exception
	{
		InputStream input = System.in;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		String cols = "";
		char endFlag = 13;
		byte[] rtn = InReadUtil.readLine(baos, input, endFlag);
		
		cols = new String(rtn);
		input.close();
		
		String[] arrCols = cols.split(",");
		int size = arrCols.length;
		for(int i = 0; i < size; i++)
		{
			String col = arrCols[i].trim();
			System.out.println("IDataUtil.chkParam(input, \""+col+"\");");
		}
	}
	
	/**
	 * 控制台里输入的格式为MobileNo,MaterialCode
	 * 然后回车，则自动生成相应代码
	 * 自动生成入参校验的方法
	 * @author Administrator
	 *
	 */
	public static void checkParamForIBOSS() throws Exception
	{
		InputStream input = System.in;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		String cols = "";
		char endFlag = 13;
		byte[] rtn = InReadUtil.readLine(baos, input, endFlag);
		
		cols = new String(rtn);
		input.close();
		
		String[] arrCols = cols.split(",");
		int size = arrCols.length;
		for(int i = 0; i < size; i++)
		{
			String col = arrCols[i].trim();
			if(checkStrAllIsUpper(col))
			{
				
			}
			else
			{
				char[] tmp = col.toCharArray();
				col = "";
				for(int j = 0; j < tmp.length; j++)
				{
					if(j == 0)
					{
						col += Character.toUpperCase(tmp[j]);
						continue;
					}
					boolean flag = Character.isUpperCase(tmp[j]);
					if(flag)
					{
						col += "_" + tmp[j];
					}
					else
					{
						col += Character.toUpperCase(tmp[j]);
					}
				}
			}
			
			System.out.println("IDataUtil.chkParam(input, \""+col+"\");");
		}		
//		InputStream input = System.in;
//		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		
//		String cols = "";
//		char endFlag = 13;
//		byte[] rtn = InReadUtil.readLine(baos, input, endFlag);
//		
//		cols = new String(rtn);
//		input.close();
//		
//		String[] arrCols = cols.split(",");
//		int size = arrCols.length;
//		for(int i = 0; i < size; i++)
//		{
//			String col = arrCols[i].trim();
//			System.out.println("IDataUtil.chkParam(input, \""+col+"\");");
//		}
	}
	
	private static boolean checkStrAllIsUpper(String str) throws Exception
	{
		boolean flag = true;
		char[] tmp = str.toCharArray();
		for(int i = 0; i < tmp.length; i++)
		{
			if(!Character.isUpperCase(tmp[i]))
			{
				flag = false;
				break;
			}
		}
		
		return flag;
	}
}

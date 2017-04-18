package excel;

import org.apache.commons.lang.StringUtils;

import com.ailk.util.InReadUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class GenArchSql {

	/**
	 * sadasdsadsa
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		String tableName = "TF_B_TRADE_DISCNT";
//		String partitionColumn = "SUBSCRIBER_INS_ID";
		String partitionColumn = "";
		
		Workbook rwb = null;
		
		InputStream in = new FileInputStream("H:\\J2EE\\HAIN\\aicrm5\\pdm\\新老表对应关系.xls");
		rwb = Workbook.getWorkbook(in);
		//得到新表名 start
		Sheet sheet = rwb.getSheet(0);
		
		String newTableName = sheet.getCell(1, getRow(sheet, tableName)).getContents();
		//得到新表名 end
		//得到原始SQL start
		InputStream input = System.in;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		String sql = "";
		char endFlag = 59;
		byte[] rtn = InReadUtil.readLine(baos, input, endFlag);
		sql = new String(rtn);
		//sql = sql.toUpperCase();
		input.close();
		//得到原始SQL end
		//进入表名对应的sheet
		Sheet tableSheet = rwb.getSheet(tableName);
		String[] arrSql = sql.split(",");
		StringBuffer newSql = new StringBuffer();
		System.out.println("1111111111");
		for(int i = 0, size = arrSql.length; i < size; i++)
		{
			String columnName = arrSql[i].trim();
//			System.out.println(columnName);
			String newColumnName = tableSheet.getCell(1, getRow(tableSheet, columnName)).getContents();
			if(!"删除".equals(newColumnName))
			{
				if(columnName.equals(newColumnName))
				{
					newSql.append(columnName).append(", ");
				}
				else
				{
					newSql.append(newColumnName).append(" ").append(columnName).append(", ");
				}
			}
			else
			{
				continue;
			}
			
			if((i+1)%3 == 0)
			{
				newSql.append("\n");
			}
		}

		if(StringUtils.isNotBlank(partitionColumn))
		{
			newSql.append("MOD(").append(partitionColumn).append(", 10000) PARTITION_ID");
		}
		newSql.append("\n");
		newSql.append("FROM ").append(newTableName).append("\n");
		newSql.append("WHERE ORDER_LINE_ID = MT.ORDER_LINE_ID");
		System.out.println(newSql.toString());
	}

	private static int getRow(Sheet sheet, String name) throws Exception
	{
		int row = 0;
		for(int i = 1, rows = sheet.getRows(); i <= rows; i++)
		{
			String content = sheet.getCell(0,i).getContents();
			if(name.equals(content))
			{
				row = i;
				break;
			}
		}
		
		return row;
	}
}

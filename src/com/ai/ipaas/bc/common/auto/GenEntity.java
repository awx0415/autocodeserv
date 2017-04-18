
package com.ai.ipaas.bc.common.auto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ailk.util.ArrayUtil;
import com.ailk.customvar.LowerFirstCharacter;
import com.ailk.customvar.UpperFirstCharacter;
import com.ailk.db.ConnectionFactory;
import com.ailk.db.DBHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 根据表自动生成相应的TradeData对象 使用到了tableTradeDataClass.ftl模板
 */
public class GenEntity
{

	public static void main(String[] args) throws Exception
	{
		GenEntity gen = new GenEntity();
		gen.genByDbUser("D:/file/", "UCR_JOUR1","com.ai.apaas.common.cisf.domain.product.entity", "OM_BANK_SUBSIGN");
	}
	
	public void genFile(String dirPath, Connection conn, String tableName, String packageName,String dbUser) throws Exception{
		Configuration cfg = new Configuration();
		String fileName = this.getFileName(tableName);
		

		cfg.setClassForTemplateLoading(GenEntity.class, "/");
		cfg.setSharedVariable("upperFC", new UpperFirstCharacter());
		cfg.setSharedVariable("lowerFC", new LowerFirstCharacter());
		Template t = cfg.getTemplate("freemarker/entity.ftl");


		Map data = new HashMap();
		data.put("package", packageName);//
		data.put("className", fileName);
		data.put("tableName", tableName);
		data.put("entityEnum", getSimpleTableName(tableName));

		//查询主键
		String pk = getTableSeqName(conn,tableName,dbUser);
		String setPk = "";
		if(StringUtils.isNotBlank(pk)){
		
            setPk = "set"+pk+"(SeqManager.get"+pk+"Seq());";
		}
        
		
		data.put("setPk", setPk);
		
		checkFileAndMkdir(dirPath + "/" + fileName + ".java");
		
		FileOutputStream fos = new FileOutputStream(dirPath + "/" + fileName + ".java");

		List pros = new ArrayList();
		String[] cols = DBHelper.getColumnNames(conn, tableName, true);
		for (int i = 0, size = cols.length; i < cols.length; i++)
		{
			StringBuilder proName = new StringBuilder();
			String colName = cols[i];
			String[] tmpArr = colName.split("_");
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
			pro_1.put("proType", String.class.getSimpleName());
			pro_1.put("proName", proName.toString());
			pro_1.put("colName", colName);
			pros.add(pro_1);
		}
		
		
		data.put("properties", pros);
		t.process(data, new OutputStreamWriter(fos));
		fos.flush();
		fos.close();
	}
	
	public String getFileName(String tableName){
		String temp = tableName.toLowerCase();
		String[] members = temp.split("_");
		String fileName = "";
		for(int i=1;i<members.length;i++){
			String member = members[i];
			fileName += (member.charAt(0)+"").toUpperCase()+member.substring(1);
		}
		return fileName+"Entity";
	}
	
	public void genByDbUser(String dirPath, String dbUser, String packageName,String tableName) throws Exception{
		Connection conn = ConnectionFactory.getConnection();
		List<String> tables =  null;
		if(tableName != null)
		    tables = DBHelper.getSingleTable(conn, dbUser,tableName);
		else
		    tables = DBHelper.getOrderTables(conn, dbUser);
		
		List tableenums = new ArrayList();
		List seqs = new ArrayList();
		for(String table : tables){
		    Map tableenum = new HashMap();
		    Map seq = new HashMap();
			// sys.out.println("gen "+table+" start");
			String domain = table.split("_")[0];
			String dirpathdesc = dirPath;
			if(domain.equals("AM") || domain.equals("UM") || domain.equals("CM")){
			    dirpathdesc = dirPath +domain+"/";
			}
			
			this.genFile(dirpathdesc, conn, table, packageName, dbUser);
			String orderTable = table;
			if(table.startsWith("UM_") || table.startsWith("CM_") || table.startsWith("AM_"))
			    orderTable ="OM_"+getSimpleTableName(table);
			tableenum.put("tablename", orderTable);
			tableenum.put("remarktable", DBHelper.getTableComment(conn, table));
			tableenum.put("enumname", getSimpleTableName(table));
			String seqName =getTableSeqName(conn,table,dbUser);
			if(StringUtils.isNotBlank(seqName)){
			    seq.put("seqName", seqName);
			}
			
			// sys.out.println("gen "+table + " complete!");
			tableenums.add(tableenum);
			if(!this.isMapExis(seqs, "seqName", seqName)){
			    seqs.add(seq);
			}
		}
		conn.close();
		createEnumTableFile(dirPath,tableenums );
//		createSeqManagerFile(dirPath,seqs);
		
	}
	
	public String getSimpleTableName(String tableName){
	    return tableName.substring(3);
	}
	
	public void createEnumTableFile(String file,List tableenums) throws IOException, TemplateException{
	    Configuration cfg = new Configuration();
        

        cfg.setClassForTemplateLoading(GenEntity.class, "/");
        cfg.setSharedVariable("upperFC", new UpperFirstCharacter());
        cfg.setSharedVariable("lowerFC", new LowerFirstCharacter());
        Template t = cfg.getTemplate("freemarker/enum.ftl");


        Map data = new HashMap();
        data.put("tableenums", tableenums);
        FileOutputStream fos = new FileOutputStream(file + "/" + "EntityEnum" + ".java");
        t.process(data, new OutputStreamWriter(fos));
        fos.flush();
        fos.close();
	}
	
	public void createSeqManagerFile(String file,List seqs) throws IOException, TemplateException{
        Configuration cfg = new Configuration();
        

        cfg.setClassForTemplateLoading(GenEntity.class, "/");
        cfg.setSharedVariable("upperFC", new UpperFirstCharacter());
        cfg.setSharedVariable("lowerFC", new LowerFirstCharacter());
        Template t = cfg.getTemplate("freemarker/seq.ftl");


        Map data = new HashMap();
        data.put("seqs", seqs);
        FileOutputStream fos = new FileOutputStream(file + "/" + "SeqManager" + ".java");
        t.process(data, new OutputStreamWriter(fos));
        fos.flush();
        fos.close();
    }
	
	public String getTableSeqName(Connection conn, String tableName, String dbUser) throws Exception{
	    String pk = DBHelper.getPk(conn, tableName, dbUser);
	    StringBuilder pkName = new StringBuilder();
        if(StringUtils.isNotBlank(pk)){
        
            String[] pkArr = pk.split("_");
            for (int j = 0, tmpArrsize = pkArr.length; j < tmpArrsize; j++)
            {
                String tmpStr = pkArr[j].toLowerCase();
                tmpStr = tmpStr.substring(0, 1).toUpperCase() + tmpStr.substring(1, tmpStr.length());
                
                pkName.append(tmpStr);
            }
            
            
        }
        
        return pkName.toString();
	}
	
	public boolean isMapExis(List<Map> list ,String key ,String value){
	    if(ArrayUtil.isEmpty(list))
	        return false;
	    
	    for(Map map : list ){
	       String valuetemp =  (String)map.get(key);
	       if(value.equals(valuetemp))
	           return true;
	    }
	    return false;
	}
	
	 /**
     * 校验文件存不存在， 不存在但不是目录，且截取之后目录不存在 则创建目录
     * @param
     * @param fileName
     * @throws Exception
     */
    public static void checkFileAndMkdir(String fileName) throws Exception
    {
        File f = new File(fileName);
        if (!f.exists() && !f.isDirectory()) {
            int index = fileName.lastIndexOf("\\");
            int index2 = fileName.lastIndexOf("/");
            String filePath = fileName.substring(0, index2 > index ? index2: index);
            File ff = new File(filePath);
            if (!ff.exists()) {
                ff.mkdirs();
            }
        }
    }

}

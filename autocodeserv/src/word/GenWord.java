
package word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import word.util.Util;

import com.ailk.util.ArrayUtil;
import com.ailk.customvar.LowerFirstCharacter;
import com.ailk.customvar.UpperFirstCharacter;
import com.ailk.db.ConnectionFactory;
import com.ailk.db.DBHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 */
public class GenWord
{

	public static void main(String[] args) throws Exception
	{
		GenWord gen = new GenWord();
		gen.genFile();
	}
	
	public void genFile() throws Exception{
		Configuration cfg = new Configuration();
		
		cfg.setClassForTemplateLoading(GenWord.class, "/");
		Template t = cfg.getTemplate("freemarker/word/templet.ftl");

		Map data = new HashMap();
		FileOutputStream fos = new FileOutputStream("F:/templet_test.xml");

		List pros = new ArrayList();
		String[] tabs = Util.getAllTable();
		for (int i = 0, size = tabs.length; i < size; i++)
		{
			String seqId = String.valueOf(i+1);
			if(seqId.length() < 6)
			{
				int addLength = 5-seqId.length();
				for(int j = 0; j < addLength; j++)
				{
					seqId = "0" + seqId;
				}
			}
			
			String tabName = tabs[i];
			tabName = tabName.trim();
			Map pro = new HashMap();
			pro.put("TABLE_NAME", tabName);
			pro.put("SEQ_ID", seqId.toString());
			pros.add(pro);
		}
		
		data.put("properties", pros);
		t.process(data, new OutputStreamWriter(fos));
		fos.flush();
		fos.close();
	}
}

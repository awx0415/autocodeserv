
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
public class GenWord4Menu
{

	public static void main(String[] args) throws Exception
	{
		GenWord4Menu gen = new GenWord4Menu();
		gen.genFile();
	}
	
	public void genFile() throws Exception{
		Configuration cfg = new Configuration();
		
		cfg.setClassForTemplateLoading(GenWord4Menu.class, "/");
		Template t = cfg.getTemplate("freemarker/word/menu.ftl");

		Map data = new HashMap();
		FileOutputStream fos = new FileOutputStream("H:/J2EE/HNAN/dev/aicrm5/veris-app/order/doc/request_doc/templet_menu_test.xml");

		List pros = new ArrayList();
		String[] tabs = getAllMenu();
		for (int i = 0, size = tabs.length; i < size; i++)
		{
			String seqId = String.valueOf(i+1);
			if(seqId.length() < 6)
			{
				int addLength = 5-seqId.length();
				for(int j = 0; j < addLength; j++)
				{
					if(j == addLength-1)
					{
						seqId = "1" + seqId;
					}
					else
					{
						seqId = "0" + seqId;
					}
					
				}
			}
			
			String tabName = tabs[i];
			tabName = tabName.trim();
			Map pro = new HashMap();
			pro.put("MENU_TITLE", tabName);
			pro.put("SEQ_ID", seqId.toString());
			pros.add(pro);
		}
		
		data.put("properties", pros);
		t.process(data, new OutputStreamWriter(fos));
		fos.flush();
		fos.close();
	}
	
	public static String[] getAllMenu() throws Exception
	{
		InputStream in = new FileInputStream("H:/J2EE/HNAN/dev/aicrm5/veris-app/order/doc/request_doc/menu.txt");
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

package word.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
 
public class Util {
	
	public static String[] getAllTable() throws Exception
	{
		InputStream in = new FileInputStream("F:\\alltables.txt");
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
	
	public static String[] getAllMenu() throws Exception
	{
		InputStream in = new FileInputStream("F:\\menu.txt");
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

package com.ailk.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InReadUtil {

	/***
	 * @param baos
	 * @param in
	 * @param endFlag
	 * @return
	 * @throws IOException
	 */
	public static byte[] readLine(ByteArrayOutputStream baos, InputStream in, char endFlag) throws IOException {
		byte[] rtn = null;

		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			if (endFlag == b[0]) {
				break;
			}

			baos.write(b, 0, 1);
		}

		rtn = baos.toByteArray();
		
		baos.reset();
		
		return rtn;
	}
	
	public static byte[] readLine(ByteArrayOutputStream baos, InputStream in) throws IOException {
		byte[] rtn = null;

		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			

			baos.write(b, 0, 1);
		}

		rtn = baos.toByteArray();
		
		baos.reset();
		
		return rtn;
	}
}

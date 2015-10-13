package com.zdzsoft.spider.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

	public static void transfer(InputStream input, OutputStream output) throws IOException {
		int len = input.available();
		byte[] data = new byte[len <= 0 ? 256 : len];
		len = input.read(data);
		while (len > 0) {
			output.write(data, 0, len);
			len = input.read(data);
		}
	}
}

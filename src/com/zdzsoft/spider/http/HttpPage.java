package com.zdzsoft.spider.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpPage {

	public static String getPage(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream input = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			System.out.println(response.getStatusLine());

			HttpEntity entity = response.getEntity();
			input = entity.getContent();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOUtil.transfer(input, output);
			String result = new String(output.toByteArray());
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}
	}
}

package com.zdzsoft.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zdzsoft.spider.http.HttpPage;
import com.zdzsoft.spider.parser.TableParser;

public class SpiderBase {
	protected HSSFWorkbook workbook = new HSSFWorkbook();

	public String getPage(String url) {
		String html = HttpPage.getPage(url);
		// System.out.println("Request url:" + url + "\r\n" + html);
		return html;
	}

	public String getPageUnEscape(String url) {
		String html = getPage(url);
		return unescape(html);
	}

	public void parseTable(String html, TableParser table) {
		if (table.parse()) {
			table.parseTable();
			System.out.println(table);
		}
	}

	public String unescape(String html) {
		String result = StringEscapeUtils.unescapeJava(html);
		return result;
	}

	public void addTableSheet(TableParser table) {
		HSSFSheet sheet = workbook.createSheet(table.getName());
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < table.getNodeSize(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(table.getNode(i).getName());
		}
		for (int j = 0; j < table.getRecordSize(); j++) {
			HSSFRow rec = sheet.createRow(j + 1);
			for (int i = 0; i < table.getNodeSize(); i++) {
				HSSFCell cell = rec.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(table.getRecord(j, i));
			}
		}
	}

	public boolean saveBook(String outputFile) {
		FileOutputStream fileOut = null;
		try {
			File file = new File(outputFile);
			file.delete();
			fileOut = new FileOutputStream(outputFile);
			workbook.write(fileOut);
			fileOut.flush();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public void delay(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
		}
	}

}

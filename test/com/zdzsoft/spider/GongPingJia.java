package com.zdzsoft.spider;

import com.zdzsoft.spider.parser.TableParser;

public class GongPingJia extends SpiderBase {

	private TableParser parseHotCarNode(String html) {
		TableParser parser = new TableParser("hot-car", "<div id=\"chooseModel-1th\" class=\"chooseModel\">", "</div>", html);
		parser.addNode("name", "data-slug=\"", "\" ");
		parser.addNode("title", "title=\"", "\">");
		if (parser.parse()) {
			parser.parseTable();
			System.out.println(parser);
		}
		return parser;
	}

	private TableParser parseCarNode(String html) {
		TableParser parser = new TableParser("car", "<div id=\"categories\">", "<div id=\"chooseModel-2th\" class=\"chooseModel hide\">", html);
		parser.addNode("name", "data-slug=\"", "\" ");
		parser.addNode("title", "title=\"", "\" ");
		if (parser.parse()) {
			parser.parseTable();
			System.out.println(parser);
		}
		return parser;
	}

	private TableParser parseCarDetail(TableParser table) {
		TableParser detailTable = new TableParser(table.getName() + "-data", "{\"status\":", "}]}");
		detailTable.addNode("mum", "\"mum\": \"", "\"");
		detailTable.addNode("slug", "\"slug\": \"", "\"");
		detailTable.addNode("name", "\"name\": \"", "\"");
		for (int i = 0; i < table.getRecordSize(); i++) {
			String brand = table.getRecord(i, 0);
			String json = getPageUnEscape("http://bj.gongpingjia.com/meta-data/model-query/?brand=" + brand);
			System.out.println("brand:" + brand + "  json:" + json);
			if (detailTable.parse(json)) {
				detailTable.parseTable();
			}
			delay(5);
		}
		return detailTable;
	}

	public static void main(String[] args) {
		GongPingJia m = new GongPingJia();
		String html = m.getPage("http://bj.gongpingjia.com/");
		TableParser hotcar = m.parseHotCarNode(html);
		TableParser car = m.parseCarNode(html);
		TableParser hotcar_data = m.parseCarDetail(hotcar);
		TableParser car_data = m.parseCarDetail(car);
		m.addTableSheet(hotcar);
		m.addTableSheet(hotcar_data);
		m.addTableSheet(car);
		m.addTableSheet(car_data);
		m.saveBook("test/car.xls");
	}

}

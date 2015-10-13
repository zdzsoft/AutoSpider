package com.zdzsoft.spider.parser;

import java.util.ArrayList;
import java.util.List;

public class TableParser extends NodeParser {
	private List<NodeParser> field = new ArrayList<NodeParser>();
	private List<String[]> record = new ArrayList<String[]>();

	public TableParser(String name) {
		super(name);
	}

	public TableParser(String name, String prefix, String sufix) {
		super(name, prefix, sufix);
	}

	public TableParser(String name, String prefix, String sufix, String html) {
		super(name, prefix, sufix, html);
	}

	public void parseTable() {
		parseTable(getContent());
	}

	public void parseTable(String content) {
		while (content != null && content.length() > 0) {
			String[] rec = new String[getNodeSize()];
			boolean flag = true;
			for (int i = 0; flag && i < field.size(); i++) {
				flag = field.get(i).parse(content);
				rec[i] = field.get(i).getContent();
				content = field.get(i).getHtml();
			}
			if (flag) {
				record.add(rec);
			} else {
				content = null;
			}
		}
	}

	public void addNode(NodeParser node) {
		field.add(node);
	}

	public void addNode(String name, String prefix, String sufix) {
		addNode(new NodeParser(name, prefix, sufix));
	}

	public NodeParser getNode(int pos) {
		return field.get(pos);
	}

	public NodeParser removeNode(int pos) {
		return field.remove(pos);
	}

	public int getNodeSize() {
		return field.size();
	}

	public void clear() {
		field.clear();
	}

	public List<String[]> getRecord() {
		return record;
	}

	public String[] getRecord(int pos) {
		return record.get(pos);
	}

	public String getRecord(int pos, int field) {
		return record.get(pos)[field];
	}

	public int getRecordSize() {
		return record.size();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < record.size(); i++) {
			for (int j = 0; j < field.size(); j++) {
				sb.append("  " + field.get(j).getName());
				sb.append("=" + record.get(i)[j]);
			}
			sb.append("\r\n");
		}
		return "Table[" + getName() + "]: " + record.size() + "\r\n" + sb.toString();
	}

}

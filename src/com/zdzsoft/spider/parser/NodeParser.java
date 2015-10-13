package com.zdzsoft.spider.parser;

public class NodeParser {
	private String name;
	private String prefix;
	private String sufix;
	private String html;
	private String content;

	public NodeParser(String name) {
		this.name = name;
	}

	public NodeParser(String name, String prefix, String sufix) {
		this.name = name;
		this.prefix = prefix;
		this.sufix = sufix;
	}

	public NodeParser(String name, String prefix, String sufix, String html) {
		this.name = name;
		this.prefix = prefix;
		this.sufix = sufix;
		this.html = html;
	}

	public boolean parse() {
		return parse(html);
	}

	public boolean parse(String html) {
		if (prefix != null && prefix.length() > 0) {
			int index = html.indexOf(prefix);
			if (index >= 0) {
				html = html.substring(index + prefix.length());
			} else {
				return false;
			}
		}
		this.html = html;
		if (sufix != null && sufix.length() > 0) {
			int index = html.indexOf(sufix);
			if (index >= 0) {
				this.html = html.substring(index + sufix.length());
				html = html.substring(0, index);
			} else {
				return false;
			}
		}
		this.content = html;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSufix() {
		return sufix;
	}

	public void setSufix(String sufix) {
		this.sufix = sufix;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

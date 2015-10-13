package com.zdzsoft.spider.parser;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class GPJParser {

	public static void parse(String gpj) {
		Parser parser = Parser.createParser(gpj, "gb2312");
		try {
			NodeIterator i = parser.elements();
			while(i.hasMoreNodes()) {
				Node node = i.nextNode();
				System.out.println(node.toHtml());
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}
}

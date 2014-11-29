package com.jayfulmath.designpattern.proxy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParse implements IXmlParse {

	private String xmlPath = null;
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory
			.newInstance();
	
	public HashMap<String, String> mNode = new HashMap<String, String>();
	
	public XmlParse(String path) {
		xmlPath = path;
		mNode.clear();
		init();
	}

	public void init() {
		Document doc = parse(xmlPath);
		Element rootElement = doc.getDocumentElement();
		// traverse child elements
		NodeList nodes = rootElement.getElementsByTagName("key");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				if(child.getAttribute("name") != null)
				{
					mNode.put(child.getAttribute("name"), child.getTextContent());
				}

			}
		}
	}

	// Load and parse XML file into DOM
	public Document parse(String filePath) {
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an XML file into a DOM tree
			document = builder.parse(new File(filePath));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	@Override
	public String parseXmlValue(String name) {
		String result = null;
		if(mNode!=null && mNode.size()>0)
		{
			if(mNode.containsKey(name))
			{
				result = mNode.get(name);
			}
		}
		return result;
	}

}

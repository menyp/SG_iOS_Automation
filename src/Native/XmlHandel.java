package Native;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHandel {

	public static void main(String[] args) {
	

	}
	
	
	public static String readAndroidXml (String nodeName, String langXml, String xmlPath) throws ParserConfigurationException, SAXException, IOException, InterruptedException{
		//Declare file path
		File xmlFile = new File (xmlPath + langXml);

		//Create instance for document builder factory
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
		//Get node list for XML
		NodeList nlXml = doc.getChildNodes();
		//get XML first node
		Node nXml = nlXml.item(0);
		//Assign Node element
		Element element = (Element)nXml;
		String nodeContent = element.getElementsByTagName(nodeName).item(0).getTextContent();

		return nodeContent;
		
	};

}

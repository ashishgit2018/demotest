package com.test.autothon.common;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Rahul_Goyal
 */

public class XmlUtils {

    private XmlUtils() {
    }

    /**
     * Replace the data in XML by passing request, tag and value
     *
     * @param request
     * @param tag
     * @param value
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     */
    public static String getXMLAndReplaceValue(String request, String tag, String value) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(request.getBytes()));

        Node n = document.getElementsByTagName(tag).item(0);
        n.setTextContent(value);

        StringWriter stringWriter = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

        String data1 = stringWriter.toString();
        return data1;
    }

    /**
     * Read XML request and  using tag parameter passed in the method read the value and return the value
     *
     * @param request
     * @param tag
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     */
    public static String getXMLAndReadValue(String request, String tag) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(request.getBytes()));

        Node n = document.getElementsByTagName(tag).item(0);
        String readValue = n.getTextContent();

        return readValue;
    }

    /**
     * This method is used to search any value from xml response file using xpath
     *
     * @param inputXML
     * @param xpathExpression
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws TransformerException
     */
    public static String getXMLDataUsingXpath(String inputXML, String xpathExpression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); //Added this line to correct the XML DTD
        DocumentBuilder builder = dbf.newDocumentBuilder();

        Document doc = builder.parse(new ByteArrayInputStream(inputXML.getBytes()));
        doc.getDocumentElement().normalize();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathExpression);
        String xmlData = (String) expr.evaluate(doc, XPathConstants.STRING);

        return xmlData;
    }
}

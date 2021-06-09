package com.github.nsorin.aramis.axml;


import com.github.nsorin.aramis.model.TextContent;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class AXMLReader {
    public TextContent readContent(File file) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setErrorHandler(new AXMLErrorHandler());
            Document document = documentBuilder.parse(file);
            return new TextContent(document.getElementsByTagName("text").item(0).getTextContent());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new AXMLReaderException();
        }
    }
}

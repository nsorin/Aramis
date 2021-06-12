package com.github.nsorin.aramis.axml;


import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class AXMLReader {

    public static final String TAG_TEXT = "text";

    public AXMLContent readContent(File file) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setErrorHandler(new AXMLErrorHandler());
            Document document = documentBuilder.parse(file);
            return new AXMLContent(document.getElementsByTagName(TAG_TEXT).item(0).getTextContent());
        } catch (Exception exception) {
            throw new InvalidAXMLException();
        }
    }
}

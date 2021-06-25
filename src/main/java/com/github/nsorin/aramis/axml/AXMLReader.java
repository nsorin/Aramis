package com.github.nsorin.aramis.axml;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class AXMLReader {

    private static final String TAG_TEXT = "text";
    private static final String TAG_TITLE = "title";
    private static final String TAG_AUTHOR = "author";

    public AXMLContent readContent(File file) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setErrorHandler(new AXMLErrorHandler());
            Document document = documentBuilder.parse(file);
            return new AXMLContent(
                    readElement(document, TAG_TEXT),
                    readElement(document, TAG_TITLE),
                    readElement(document, TAG_AUTHOR)
            );
        } catch (Exception exception) {
            throw new InvalidAXMLException();
        }
    }

    private String readElement(Document document, String tagTitle) {
        NodeList elements = document.getElementsByTagName(tagTitle);
        if (elements.getLength() == 0) {
            return null;
        }
        return elements.item(0).getTextContent();
    }
}

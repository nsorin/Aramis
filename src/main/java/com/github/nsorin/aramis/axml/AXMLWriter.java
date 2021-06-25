package com.github.nsorin.aramis.axml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class AXMLWriter {

    public static final String TAG_AXML = "axml";
    public static final String TAG_TEXT = "text";
    public static final String TAG_TITLE = "title";
    public static final String TAG_AUTHOR = "author";

    public void writeContent(File file, AXMLContent axmlContent) {
        try {
            Document document = createEmptyDocument();

            Element rootElement = document.createElement(TAG_AXML);

            addElementWithText(document, rootElement, TAG_TITLE, axmlContent.title());
            addElementWithText(document, rootElement, TAG_AUTHOR, axmlContent.author());
            addElementWithText(document, rootElement, TAG_TEXT, axmlContent.text());

            document.appendChild(rootElement);

            writeDocumentToFile(file, document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addElementWithText(Document document, Element rootElement, String tag, String text) {
        if (text != null) {
            Element textElement = document.createElement(tag);
            textElement.setTextContent(text);
            rootElement.appendChild(textElement);
        }
    }

    private Document createEmptyDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.newDocument();
    }

    private static void writeDocumentToFile(File file, Document document) throws FileNotFoundException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StreamResult result = new StreamResult(new PrintWriter(
                new FileOutputStream(file, false)));
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
}

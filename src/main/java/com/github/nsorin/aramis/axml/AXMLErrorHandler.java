package com.github.nsorin.aramis.axml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class AXMLErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) {
        throw new InvalidAXMLException();
    }

    @Override
    public void error(SAXParseException exception) {
        throw new InvalidAXMLException();
    }

    @Override
    public void fatalError(SAXParseException exception) {
        throw new InvalidAXMLException();
    }
}

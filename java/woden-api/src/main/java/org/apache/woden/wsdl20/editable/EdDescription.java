package org.apache.woden.wsdl20.editable;

import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;

import org.apache.woden.wsdl20.Description;

/**
 * Represents the editable Description component from the WSDL 2.0 Component
 * model
 * 
 * 
 */
public interface EdDescription extends Description {
    public EdBinding addBinding();

    public EdInterface addInterface();

    public EdService addService();

    public EdTypeDefinition addTypeDefinition();

    public void setDocumentBaseURI(URI documentBaseURI);

    public void setTargetNamespace(URI namespaceURI);

    /**
     * serialize WSDL content according to the provided SearalizationStrategy.
     * 
     * @param sink
     */
    public void serialize(OutputStream sink);

    public void serialize(Writer sink);

    public String getserializationStrategy();

    public void setserializationStrategy(String searalizationStrategy);
}

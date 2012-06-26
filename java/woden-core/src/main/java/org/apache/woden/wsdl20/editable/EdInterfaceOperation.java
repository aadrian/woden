package org.apache.woden.wsdl20.editable;

import java.net.URI;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.InterfaceOperation;

/**
 * Represents the editable InterfaceMessageReference component from the WSDL 2.0
 * Component model.
 * 
 * 
 */
public interface EdInterfaceOperation extends InterfaceOperation {

    public EdInterfaceFaultReference addInterfaceFaultReference();

    public EdInterfaceMessageReference addInterfaceMessageReference();

    public void addStyleURI(URI style);

    public void setMessageExchangePattern(URI mep);

    public void setName(QName name);

}

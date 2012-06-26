package org.apache.woden.wsdl20.editable;

import javax.xml.namespace.QName;

import org.apache.woden.wsdl20.Interface;
import org.apache.woden.wsdl20.Service;

/**
 * Represents the editable Service component from the WSDL 2.0 Component model.
 * 
 * 
 */
public interface EdService extends Service {

    public EdEndpoint addEndpoint();

    public void setName(QName name);

    public void setInterface(Interface interfaceComp);

}

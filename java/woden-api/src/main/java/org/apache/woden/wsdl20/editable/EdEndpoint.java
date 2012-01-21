package org.apache.woden.wsdl20.editable;

import java.net.URI;

import org.apache.woden.types.NCName;
import org.apache.woden.wsdl20.Binding;
import org.apache.woden.wsdl20.Endpoint;

/**
 * Represents the editable Endpoint component from the WSDL 2.0 Component model.
 * 
 */
public interface EdEndpoint extends Endpoint {

    public void setBinding(Binding bindingComp);

    public void setAddress(URI address);

    public void setName(NCName name);

}

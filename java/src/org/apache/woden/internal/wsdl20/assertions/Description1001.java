package org.apache.woden.internal.wsdl20.assertions;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;

import org.apache.woden.ErrorReporter;
import org.apache.woden.WSDLException;
import org.apache.woden.internal.ErrorLocatorImpl;
import org.apache.woden.wsdl20.validation.Assertion;
import org.apache.woden.wsdl20.validation.WodenContext;
import org.apache.woden.wsdl20.xml.DescriptionElement;

public class Description1001 implements Assertion {

    public String getId() {
        return "Description-1001".intern();
    }

    public void validate(Object target, WodenContext wodenCtx) throws WSDLException {
        DescriptionElement descElem = (DescriptionElement) target;
        URI tns = descElem.getTargetNamespace();
        
        try {
            URI resolvedUri = wodenCtx.getUriResolver().resolveURI(tns);
            URI uri = resolvedUri != null ? resolvedUri : tns;
            URL url = uri.toURL();
            Object o = url.getContent();
            if(o == null) {
                wodenCtx.getErrorReporter().reportError(
                        new ErrorLocatorImpl(), getId(), new Object[] {tns}, ErrorReporter.SEVERITY_WARNING);
            }
        } catch (FileNotFoundException e1) {
            wodenCtx.getErrorReporter().reportError(
                    new ErrorLocatorImpl(), getId(), new Object[] {tns}, ErrorReporter.SEVERITY_WARNING);
            
        } catch (WSDLException e2) {
            throw e2;
        } catch (Exception e) {
            // IOException
            // MalformedURLException
            throw new WSDLException(WSDLException.OTHER_ERROR,
                                    "Fatal error.",
                                    e);
        }
    }

}

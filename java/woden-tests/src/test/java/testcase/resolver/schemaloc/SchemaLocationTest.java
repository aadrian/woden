package testcase.resolver.schemaloc;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.woden.internal.resolver.DOMSchemaResolverAdapter;
import org.apache.woden.internal.resolver.SchemaResolverAdapter;
import org.apache.woden.internal.resolver.SimpleURIResolver;
import org.xml.sax.InputSource;

import testcase.extensions.foo.FooBindingExtensionsTest;

public class SchemaLocationTest extends TestCase {
    
    private SchemaResolverAdapter fResolver;
    private String fSchemaTns = "http://example.com/data";
    private String fWsdlJarPath = "testcase/resolver/schemaloc/resources/SchemaLocationTest.jar";
    private String fWsdlFilePath = "testcase/resolver/schemaloc/resources/SchemaLocationTest.wsdl";
    private String fWsdlFileName = "SchemaLocationTest.wsdl";
    private String fSchemaFilePath = "testcase/resolver/schemaloc/resources/SchemaLocationTest.xsd";
    private String fSchemaFileName = "SchemaLocationTest.xsd";
    private String fWsdlWebPath = "http://example.com/resources/SchemaLocationTest.wsdl";
    private String fSchemaWebPath = "http://example.com/resources/SchemaLocationTest.xsd";

    public static Test suite() {
        return new TestSuite(SchemaLocationTest.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        fResolver = new DOMSchemaResolverAdapter(new SimpleURIResolver(), null);
    }
    
    public void testRelativePath_File() {
        URL baseURL = getClass().getClassLoader().getResource(fWsdlFilePath);
        String baseURI = baseURL.toString();
        int i = baseURI.indexOf(fWsdlFileName);
        String contextPath = baseURI.substring(0,i);
        String expectedResult = contextPath + fSchemaFileName;
        
        InputSource is = fResolver.resolveEntity(fSchemaTns, fSchemaFileName, baseURI);
        String actualResult = is.getSystemId();
        assertEquals("schemaLocation relative file path not resolved correctly", expectedResult, actualResult);
    }

    public void testAbsolutePath_File() {
        URL baseURL = getClass().getClassLoader().getResource(fWsdlFilePath);
        String baseURI = baseURL.toString();
        URL schemaURL = getClass().getClassLoader().getResource(fSchemaFilePath);
        String absoluteSchemaPath = schemaURL.toString();
        
        InputSource is = fResolver.resolveEntity(fSchemaTns, absoluteSchemaPath, baseURI);
        String actualResult = is.getSystemId();
        assertEquals("schemaLocation absolute file path not resolved correctly", absoluteSchemaPath, actualResult);
    }

    public void testRelativePath_Jar() {
        URL baseURL = getClass().getClassLoader().getResource(fWsdlJarPath);
        String contextPath = "jar:" + baseURL.toString() + "!/META-INF/";
        String baseURI =  contextPath + fWsdlFileName;
        String expectedResult = contextPath + fSchemaFileName;
        
        InputSource is = fResolver.resolveEntity(fSchemaTns, fSchemaFileName, baseURI);
        String actualResult = is.getSystemId();
        assertEquals("schemaLocation relative path not resolved correctly for jar file", expectedResult, actualResult);
    }

    public void testAbsolutePath_Jar() {
        URL baseURL = getClass().getClassLoader().getResource(fWsdlJarPath);
        String contextPath = "jar:" + baseURL.toString() + "!/META-INF/";
        String baseURI =  contextPath + fWsdlFileName;
        String absoluteSchemaPath = contextPath + fSchemaFileName;
        
        InputSource is = fResolver.resolveEntity(fSchemaTns, absoluteSchemaPath, baseURI);
        String actualResult = is.getSystemId();
        assertEquals("schemaLocation absolute path not resolved correctly for jar file", absoluteSchemaPath, actualResult);
    }

    public void testRelativePath_Web() {
        String baseURI = fWsdlWebPath;
        int i = baseURI.indexOf(fWsdlFileName);
        String contextPath = baseURI.substring(0,i);
        String expectedResult = contextPath + fSchemaFileName;
        
        InputSource is = fResolver.resolveEntity(fSchemaTns, fSchemaFileName, baseURI);
        String actualResult = is.getSystemId();
        assertEquals("schemaLocation relative web path not resolved correctly", expectedResult, actualResult);
    }

    public void testAbsolutePath_Web() {
        String baseURI = fWsdlWebPath;
        String absoluteSchemaPath = fSchemaWebPath;
        
        InputSource is = fResolver.resolveEntity(fSchemaTns, absoluteSchemaPath, baseURI);
        String actualResult = is.getSystemId();
        assertEquals("schemaLocation absolute web path not resolved correctly", absoluteSchemaPath, actualResult);
    }

}

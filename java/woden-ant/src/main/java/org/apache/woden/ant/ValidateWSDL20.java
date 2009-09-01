/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package org.apache.woden.ant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.xml.namespace.QName;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.woden.WSDLException;
import org.apache.woden.WSDLFactory;
import org.apache.woden.WSDLReader;
import org.apache.woden.wsdl20.Binding;
import org.apache.woden.wsdl20.Description;
import org.apache.woden.wsdl20.ElementDeclaration;
import org.apache.woden.wsdl20.Interface;
import org.apache.woden.wsdl20.Service;
import org.apache.woden.wsdl20.TypeDefinition;

/**
 * This is an Ant task to validate WSDL 2.0 files.
 * 
 * Parameters: The <code>dir</code> attribute is a file directory for the
 * fileset of WSDL 2.0 files to validate. You specify files in include and
 * exclude using the usual Ant attributes and elements.
 * 
 * The <code>failonerror</code> attribute is a boolean flag with the usual
 * meaning.
 * 
 * The <code>verbose</code> attribute is a boolean flag that enables verbose
 * output.
 * 
 * The <code>cm</code> attribute is a boolean flag that enables Component
 * Model interchange format output</code>. The default is false.
 * 
 * The <code>cmdir</code> attribute is a file directory for the Component
 * Model interchange format output files. This is ignored if <code>cm</code>
 * is false. The default is to use the input directory as specified by the
 * <code>dir</code> attribute.
 * 
 * The <code>cmext</code> attribute is the file extension to use for the
 * Component Model interchange format output files. This is ignored if <code>cm</code>
 * is false. The default is <code>.wsdlcm</code>.
 * 
 * @author Arthur Ryman (ryman@ca.ibm.com)
 * 
 * TODO: add support writing out failed assertions in XML
 * 
 */

public class ValidateWSDL20 extends MatchingTask {

    // directory for fileset
    private File dir;

    // flag to enable failonerror
    private boolean failOnError = false;

    // flag to enable verbose output
    private boolean verbose = false;

    // flag to enable Component Model interchange format output
    private boolean cm = false;

    // directory for Component Model interchange format output
    private File cmdir;

    // extension for Component Model interchange format output
    private String cmext;

    // report output file 
    private File report;

    // report writer
    private Report reportWriter;

    // resolver catalog location
    private String catalog;
    
    // base URI for the catalog
    private String baseURI;

    // implementation name.
    private String implName;
    
    // default extension for Component Model interchange format output
    private static final String CMEXT_DEFAULT = ".wsdlcm";

    /**
     * Gets the input WSDL fileset directory.
     * 
     * @return the directory
     */
    public File getDir() {
        return this.dir;
    }

    /**
     * Sets the input WSDL fileset directory.
     * 
     * @param dir the directory
     */
    public void setDir(File dir) {
        this.dir = dir;
    }

    /**
     * Enables fail on error behavior.
     * 
     * @param failOnError the flag
     */
    public void setFailOnError(boolean failOnError) {
        this.failOnError = failOnError;
    }

    /**
     * Tests for fail on error behavior.
     * 
     * @return the flag
     */
    public boolean isFailOnError() {
        return failOnError;
    }

    /**
     * Enables verbose console output.
     * 
     * @param verbose the flag
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Tests for verbose console output.
     * 
     * @return the flag
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * Enables component model interchange format output.
     * 
     * @param cm the flag
     */
    public void setCm(boolean cm) {
        this.cm = cm;
    }

    /**
     * Tests for component model interchange formal output.
     * 
     * @return the flag
     */
    public boolean isCm() {
        return cm;
    }

    /**
     * Sets the directory for component model interchange formal output.
     *  
     * @param cmdir the directory
     */
    public void setCmdir(File cmdir) {
        this.cmdir = cmdir;
    }

    /**
     * Gets the directory for component model interchange formal output.
     * 
     * @return the directory
     */
    public File getCmdir() {
        return cmdir;
    }

    /**
     * Sets the component model interchange format file extension.
     * 
     * @param cmext the extension
     */
    public void setCmext(String cmext) {
        this.cmext = cmext;
    }

    /**
     * Gets the component model interchange format file extension.
     * 
     * @return the extension
     */
    public String getCmext() {
        return cmext;
    }

    /**
     * Gets the output report file.
     * 
     * @return the file
     */
    public File getReport() {
        return this.report;
    }

    /**
     * Sets the output report file.
     * 
     * @param report the file
     */
    public void setReport(File report) {
        this.report = report;
    }

    
    /**
     * Sets the implementation name to test against.
     * 
     * @param implName The name of the implementation.
     */
    public void setImplName(String implName) {
       this.implName = implName;
    }
    

    public void execute() throws BuildException {

        // check the cm input attributes and set defaults if necessary
        if (isCm()) {
            File cmdir = getCmdir();
            if (cmdir == null) {

                // default cmdir to dir
                cmdir = getDir();
                setCmdir(cmdir);
            }

            if (!cmdir.isDirectory()) {

                // cmdir MUST be a directory
                System.out.println("Invalid cm output directory: "
                        + cmdir.toString());

                // disable cm output
                setCm(false);
            }

            String cmext = getCmext();
            if (cmext == null) {

                // default cmext
                setCmext(CMEXT_DEFAULT);
            }
        }

        WSDLReader reader = null;

        // create a reader (with specified implementation if given).
        try {
           //If an implementation name is given use that to test against, else use the default one.
           if (implName != null && !implName.equals("")) {
               System.out.println("Using woden with implementation from " + implName);
               WSDLFactory factory = WSDLFactory.newInstance(implName);
               reader = factory.newWSDLReader();
           } else {
               System.out.println("Using default woden implementation.");
               WSDLFactory factory = WSDLFactory.newInstance();
               reader = factory.newWSDLReader();
           }

        } catch (WSDLException e) {

            // fail if unable to create a reader
            throw new BuildException(e);
        }

        // set the features but continue even if they are not supported yet
        try {
            // always enable WSDL 2.0 validation since this is a validation task
            reader.setFeature(WSDLReader.FEATURE_VALIDATION, true);

            // enable verbose output
            reader.setFeature(WSDLReader.FEATURE_VERBOSE, isVerbose());

            // enable continue on error (opposite of fail on error)
            reader.setFeature(WSDLReader.FEATURE_CONTINUE_ON_ERROR,
                    !isFailOnError());
        } catch (IllegalArgumentException e) {
            System.out.println("warning: " + e.getMessage());
        }

        if (isVerbose()) {
            System.out.println("File dir = " + dir.getAbsolutePath());

            Project project = getProject();
            File baseDir = project.getBaseDir();
            System.out.println("File baseDir = " + baseDir.getAbsolutePath());
        }

        DirectoryScanner directoryScanner = getDirectoryScanner(dir);
        String[] files = directoryScanner.getIncludedFiles();

        reportWriter = Report.openReport(report);
        reader.getErrorReporter().setErrorHandler(reportWriter);
        
        // use the same reader for all the WSDL files
        for (int i = 0; i < files.length; i++) {

            File file = new File(dir, files[i]);

            // make a URL for the file
            String wsdlLoc = file.toURI().toString();
            System.out.println("validating " + wsdlLoc);

            reportWriter.beginWsdl(wsdlLoc);
            try {
                // <-- the Description component
                Description descComp = reader.readWSDL(wsdlLoc);

                if (isCm()) {
                    // write the Component Model interchange format output file
                    writeCm(descComp, files[i]);
                }

                if (isVerbose()) {

                    writeVerbose(descComp);
                }

            } catch (Exception e) {
               //The test may still pass if there is an exception here.
                if (isFailOnError()) {

                    reportWriter.endWsdl();
                    reportWriter.closeReport();
                    throw new BuildException(e);
                }
            }
            reportWriter.endWsdl();
        }
        reportWriter.closeReport();
    }

    private void writeVerbose(Description descComp) {
        ElementDeclaration elementDeclarations[] = descComp
                .getElementDeclarations();
        System.out.println("There are " + elementDeclarations.length
                + " ElementDeclaration components.");

        for (int j = 0; j < elementDeclarations.length; j++) {

            ElementDeclaration elementDeclaration = elementDeclarations[j];

            QName name = elementDeclaration.getName();
            System.out
                    .println("ElementDeclaration[" + j + "] : name = " + name);
        }

        TypeDefinition typeDefinitions[] = descComp.getTypeDefinitions();
        System.out.println("There are " + typeDefinitions.length
                + " TypeDefinition components.");

        for (int j = 0; j < typeDefinitions.length; j++) {
            TypeDefinition typeDefinition = typeDefinitions[j];

            QName name = typeDefinition.getName();
            System.out.println("TypeDefinition[" + j + "] : name = " + name);
        }

        Interface interfaces[] = descComp.getInterfaces();
        System.out.println("There are " + interfaces.length
                + " Interface components.");

        for (int j = 0; j < interfaces.length; j++) {

            System.out.println("Interface[" + j + "] : name = "
                    + interfaces[j].getName());
        }

        Binding bindings[] = descComp.getBindings();
        System.out.println("There are " + bindings.length
                + " Binding components.");

        for (int j = 0; j < bindings.length; j++) {

            System.out.println("Binding[" + j + "] : name = "
                    + bindings[j].getName());
        }

        Service services[] = descComp.getServices();
        System.out.println("There are " + services.length
                + " Service components.");

        for (int j = 0; j < services.length; j++) {

            System.out.println("Service[" + j + "] : name = "
                    + services[j].getName());
        }
    }

    private void writeCm(Description descComp, String file) throws IOException {

        // replace the file extension

        int dot = file.lastIndexOf('.');
        String base = dot == -1 ? file : file.substring(0, dot);
        String ext = getCmext();
        String cmfilename = base + ext;
        File cmfile = new File(cmdir, cmfilename);
        File parent = cmfile.getParentFile();
        if (parent != null) {
            if (!parent.exists()) {
                boolean created = parent.mkdirs();
                if (!created) {
                    System.out.println("Unable to create directory: "
                            + parent.toString());
                }
            }
        }

        FileOutputStream fos = new FileOutputStream(cmfile);
        PrintWriter out = new PrintWriter(fos);
        WsdlCm wsdlCm = new WsdlCm(out);
        wsdlCm.write(descComp);
        out.close();
        fos.close();
    }
    
    /**
     * Sets the resolver catalog file.
     * 
     * @param catalog resolver catalog file URI
     */
    public void setCatalog(String catalog) {
        this.catalog = getURLFilePath(catalog);
        System.setProperty("org.apache.woden.resolver.simpleresolver.catalog", this.catalog);
    }
    
    /**
     * Sets the resolver base URI path.
     * 
     * @param baseURI base URI path
     */
    public void setBaseURI(String baseURI) {
        this.baseURI = getURLFilePath(baseURI);
        System.setProperty("org.apache.woden.resolver.simpleresolver.baseURIs", this.baseURI);
    }
    
    private String getURLFilePath(String localPath) {
        File localFile = new File(localPath);
        if (localFile.exists()) {
            try {
                return localFile.toURI().toURL().toString();
            } catch (MalformedURLException mue) {
                System.err.println("Got MalformedURLException trying to create a URL from " + localPath);
                return localPath;
            }
        } else {
            return localPath;
        }
    }
}

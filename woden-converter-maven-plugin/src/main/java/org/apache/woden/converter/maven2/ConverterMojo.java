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
package org.apache.woden.converter.maven2;

import java.io.IOException;

import javax.wsdl.WSDLException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.woden.tool.converter.Convert;

/**
 * <p>
 * This is used to invoke Woden WSDL converter as Maven2 plug-in.
 * Following meta data required  for this Mojo.
 * </p>
 * <p>
 * It is possible run this command using
 * "mvn woden:convert" command.
 * </p>
 * 
 * @goal convert
 * @execute phase="generate-sources"
 * @requiresDependencyResolution runtime  *
 * @description Woden Converter Maven plig-in
 * @author Sagara Gunathunga
 */
public class ConverterMojo extends AbstractMojo {

    /** 
     * File or URL of wsdl1.1 document.Also multiple
     * WSDL files can be specified as a comma separated list.
     * 
     * @parameter expression="${woden.converter.wsdl}"
     * @required
     */
    private String wsdl;

    /** 
     * New target namespace for WSDL2.0 document.
     * 
     * @parameter expression="${woden.converter.wsdl}" 
     */
    private String targetNS;

    /** 
     * Target directory for out put, default 
     * location is project build directory. 
     * 
     * @parameter expression="${woden.converter.wsdl}" default-value="${project.build.directory}"
     */
    private String targetDir;

    /** 
     * sourceDir directory for out put. 
     * 
     * @parameter expression="${woden.converter.wsdl}" 
     */
    private String sourceDir;

    /** 
     * verbose option. 
     * 
     * @parameter expression="${woden.converter.wsdl}"
     */
    private boolean verbose;

    /** 
     * Overwrite existing files. 
     * 
     * @parameter expression="${woden.converter.wsdl}"
     */
    private boolean overwrite;
    

    private Log log = getLog();

    /*
     * @see org.apache.maven.plugin.AbstractMojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (wsdl != null && !"".equals(wsdl)) {
            String[] wsdlFiles = wsdl.split(",");
            for (int i = 0; i < wsdlFiles.length; i++) {
                if (wsdlFiles[i] != null && !"".equals(wsdlFiles[i])) {
                    convertEachFile(wsdlFiles[i].trim());
                }
            }

        }
    }

    /**
     * Convert each WSDL1.1 file in to WSDL2.0 file.     * 
     */
    private void convertEachFile(String wsdlFile) throws MojoFailureException,
            MojoExecutionException {
        long startTime = System.currentTimeMillis();

        if (wsdl != null) {

            try {
                Convert convert = new Convert();
                convert.convertFile(targetNS, wsdlFile, targetDir, verbose, overwrite);
            } catch (WSDLException e) {
                throw new MojoExecutionException(e.getMessage(), e);
            } catch (IOException e) {
                throw new MojoExecutionException(e.getMessage(), e);
            }
        } else {
            log.info("No WSDL 1.1 document was specified (use the '-wsdl' " + "argument.)");
        }

        long endTime = System.currentTimeMillis();

        if (verbose) {
            log.info("Done.\n" + "Elapsed time: " + (endTime - startTime) + "ms");
        }

    }

}
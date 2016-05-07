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
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.woden.tool.converter.Convert;

/**
 * <p>
 * Woden Converter Maven: it is used to invoke Woden WSDL converter as Maven plugin.
 * Following meta data required for this Mojo.
 * </p>
 * <p>
 * It is possible run this command using
 * "mvn woden:convert" command.
 * </p>
 * 
 * @author Sagara Gunathunga
 */
@Mojo(name = "convert", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class ConverterMojo extends AbstractMojo {

    /** 
     * File or URL of wsdl1.1 document. Also multiple
     * WSDL files can be specified as a comma separated list.
     */
    @Parameter(property = "woden.converter.wsdl", required = true)
    private String wsdl;

    /** 
     * New target namespace for WSDL2.0 document.
     */
    @Parameter
    private String targetNS;

    /** 
     * Target directory for output, default 
     * location is project build directory. 
     */
    @Parameter(defaultValue = "${project.build.directory}")
    private String targetDir;

    /** 
     * sourceDir directory for output. 
     */
    @Parameter
    private String sourceDir;

    /** 
     * verbose option. 
     */
    @Parameter(property = "woden.converter.verbose")
    private boolean verbose;

    /** 
     * Overwrite existing files.
     */
    @Parameter(property = "woden.converter.overwrite")
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
     * Convert each WSDL1.1 file in to WSDL2.0 file.
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
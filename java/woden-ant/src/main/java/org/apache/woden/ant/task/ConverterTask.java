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

/*
 * This is an Ant task to convert WSDL 1.0 documents in to WSDL 2.0 files
 */
package org.apache.woden.ant.task;

import java.io.IOException;

import javax.wsdl.WSDLException;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.woden.tool.converter.Convert;


/**
 * @author Dilshan Edirisuriya
 *
 */


public class ConverterTask extends MatchingTask {

    private String wsdl;
    private String targetNS;
    private String targetDir;
    private String sourceDir;
    private boolean verbose;
    private boolean overwrite;
    /** logger. */
    private static final Log logger = LogFactory.getLog(ConverterTask.class);

    public void execute() throws BuildException {      
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
     *  
     */
    private void convertEachFile(String wsdlFile)  {
        long startTime = System.currentTimeMillis();

        if (wsdl != null) {

            try {
                Convert convert = new Convert();
                convert.convertFile(targetNS, wsdlFile, targetDir, verbose, overwrite);
            } catch (WSDLException e) {
                throw new BuildException(e.getMessage(), e);
            } catch (IOException e) {
                throw new BuildException(e.getMessage(), e);
            }
        } else {
            logger.info("No WSDL 1.1 document was specified (use the '-wsdl' " + "argument.)");
        }

        long endTime = System.currentTimeMillis();

        if (verbose) {
            logger.info("Done.\n" + "Elapsed time: " + (endTime - startTime) + "ms");
        }

    }

    public String getWsdl() {
        return wsdl;
    }

    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
    }

    public String getTargetNS() {
        return targetNS;
    }

    public void setTargetNS(String targetNS) {
        this.targetNS = targetNS;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

}

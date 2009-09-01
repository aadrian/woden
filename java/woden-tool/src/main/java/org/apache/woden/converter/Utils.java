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
package org.apache.woden.tool.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Matthew J. Duftler <duftler@us.ibm.com>
 */
public class Utils
{
  public static OutputStream getOutputStream(String root,
                                             String name,
                                             boolean overwrite,
                                             boolean verbose)
                                               throws IOException
  {
    if (root != null)
    {
      File directory = new File(root);

      if (!directory.exists())
      {
        if (!directory.mkdirs())
        {
          throw new IOException("Failed to create directory '" + root + "'.");
        }
        else if (verbose)
        {
          System.out.println("Created directory '" +
                             directory.getAbsolutePath() + "'.");
        }
      }
    }

    File file = new File(root, name);
    String absolutePath = file.getAbsolutePath();

    if (file.exists())
    {
      if (!overwrite)
      {
        throw new IOException("File '" + absolutePath + "' already exists. " +
                              "Please remove it or enable the " +
                              "-overwrite option.");
      }
      else
      {
        file.delete();

        if (verbose)
        {
          System.out.println("Deleted file '" + absolutePath + "'.");
        }
      }
    }

    if (verbose)
    {
      System.out.println("Created file '" + absolutePath + "'.");
    }

    return new FileOutputStream(absolutePath);
  }
}

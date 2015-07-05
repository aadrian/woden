#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements. See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership. The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License. You may obtain a copy of the License at
# 
# http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

from os import *
from os.path import *
from shutil import copyfile
from shutil import rmtree
from subprocess import call
from xml.etree.ElementTree import parse

woden_dir = realpath(join(dirname(__file__), ".."))
pom = parse(join(woden_dir, "pom.xml"))
release = pom.getroot().find("{http://maven.apache.org/POM/4.0.0}version").text
dist_root = join(woden_dir, "target", "dist")
dist_dir = join(dist_root, release)

if exists(dist_root):
    rmtree(dist_root)
call(["svn", "checkout", "https://dist.apache.org/repos/dist/dev/ws/woden/", dist_root])
mkdir(dist_dir)
for classifier in [ "dom", "src" ]:
    for format in [ "tar.bz2", "tar.gz", "zip" ]:
        for suffix in [ format, "%s.asc" % format, "%s.md5" % format, "%s.sha1" % format ]:
            file = "woden-" + release + "-" + classifier + "." + suffix
            copyfile(join(woden_dir, "woden-dist", "target", file), join(dist_dir, file))
call(["svn", "add", dist_dir])
if release.endswith("-SNAPSHOT"):
    print "Skipping commit because version is a snapshot."
else:
    call(["svn", "commit", dist_dir])
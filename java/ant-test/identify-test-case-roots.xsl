<?xml version="1.0" encoding="UTF-8"?>
<!--
 ! Licensed to the Apache Software Foundation (ASF) under one or more
 ! contributor license agreements.  See the NOTICE file distributed with
 ! this work for additional information regarding copyright ownership.
 ! The ASF licenses this file to You under the Apache License, Version 2.0
 ! (the "License"); you may not use this file except in compliance with
 ! the License.  You may obtain a copy of the License at
 !
 !      http://www.apache.org/licenses/LICENSE-2.0
 !
 ! Unless required by applicable law or agreed to in writing, software
 ! distributed under the License is distributed on an "AS IS" BASIS,
 ! WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ! See the License for the specific language governing permissions and
 ! limitations under the License.
 !-->
<!--
	Adds identifiers of the test case root documents to the validation report.
	
	ChangeLog:
	
	2006-11-15 Arthur Ryman <ryman@ca.ibm.com>
	- created
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns='http://www.w3.org/2006/06/wsdl/ValidationReport'
	xmlns:v='http://www.w3.org/2006/06/wsdl/ValidationReport'
	xmlns:t='http://www.w3.org/2006/02/wsdl/TestMetadata'
	exclude-result-prefixes="v t">

	<!-- Input Parameters -->

	<!-- $test-suite-dir is the test suite directory, 
		e.g. D:\workspaces\WSD2\woden\downloads\w3c -->
	<xsl:param name="test-suite-dir" />

	<!-- $test-suite-xml is the test-suite.xml file, 
		e.g. D:\workspaces\WSD2\woden\downloads\w3c\test-suite.xml -->
	<xsl:param name="test-suite-xml" />

	<!-- $Identifier-base is the base of the Identifier uri's, 
		e.g. http://dev.w3.org/cvsweb/2002/ws/desc/test-suite -->
	<xsl:param name="Identifier-base" />

	<!-- Global Variables -->

	<!-- $test-suite-dir-uri is the test suite directory uri, 
		e.g. file:/D:/workspaces/WSD2/woden/downloads/w3c -->
	<xsl:variable name="test-suite-dir-uri"
		select="concat('file:/',translate ($test-suite-dir ,'\','/'))" />

	<!-- $test-cases is the list of test case noded in test-suite.xml -->
	<xsl:variable name="test-cases"
		select="document($test-suite-xml)/test-suite/test-case" />

	<xsl:output method="xml" indent="yes" />

	<xsl:template match="v:wsdl">
		<wsdl>
			<xsl:call-template name="add-identifier" />
			<xsl:apply-templates />
		</wsdl>
	</xsl:template>

	<xsl:template match="*">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()">
		<xsl:value-of select="." />
	</xsl:template>

	<xsl:template name="add-identifier">

		<!-- $uri is WSDL file uri, 
			e.g. file:/D:/workspaces/WSD2/woden/downloads/w3c/documents/bad/Binding-1B/BadBinding.wsdl -->
		<xsl:variable name="uri" select="normalize-space(v:uri)" />

		<!--  $relative-uri is the uri of the WSDL file relative to the test suite directory, 
			e.g. /documents/bad/Binding-1B/BadBinding.wsdl -->
		<xsl:variable name="relative-uri"
			select="substring-after($uri,$test-suite-dir-uri)" />

		<!-- $Identifier-uri is the uri of the WSDL file based on the test case Identifier, 
			e.g. http://dev.w3.org/cvsweb/2002/ws/desc/test-suite/documents/bad/Binding-1B/BadBinding.wsdl -->
		<xsl:variable name="Identifier-uri"
			select="concat($Identifier-base,$relative-uri)" />

		<xsl:variable name="test-case"
			select="$test-cases[contains($uri,@id)]" />
		<xsl:if test="$test-case">

			<!-- $TestMetadata-xml is the TestMetadata.xml file for the test case, 
				e.g. documents/bad/Binding-1B/TestMetadata.xml -->
			<xsl:variable name="TestMetadata-xml"
				select="$test-case/@href" />

			<!-- $Identifier is the test case Identifier,
				e.g. http://dev.w3.org/cvsweb/2002/ws/desc/test-suite/documents/bad/Binding-1B -->
			<xsl:variable name="Identifier"
				select="normalize-space(document($TestMetadata-xml)/t:TestMetadata/t:Identifier)" />

			<!-- $Input-root> is the root WSDL file for the test case,
				e.g. BadBinding.wsdl -->
			<xsl:variable name="Input-root"
				select="normalize-space(document($TestMetadata-xml)/t:TestMetadata/t:Inputs/t:Input[@role='root'])" />

			<!-- $Identifier-root is the absolute uri of the test case root WSDL based on the Identifier,
				e.g. http://dev.w3.org/cvsweb/2002/ws/desc/test-suite/documents/bad/Binding-1B/BadBinding.wsdl -->
			<xsl:variable name="Identifier-root"
				select="concat($Identifier,'/',$Input-root)" />

			<!-- $relative-Identifier-root is the root WSDL relative to the absolute Identifier uri,
				e.g. /documents/bad/Binding-1B/BadBinding.wsdl -->
			<xsl:variable name="relative-Identifier-root"
				select="substring-after($Identifier-root,$Identifier-base)" />

			<!-- if this WSDL is a test case root then insert its Identifier -->
			<xsl:if test="($relative-uri=$relative-Identifier-root)">
				<identifier>
					<xsl:value-of select="$Identifier" />
				</identifier>
			</xsl:if>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>

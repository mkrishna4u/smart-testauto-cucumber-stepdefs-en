/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.data;

import static org.testng.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to XML file data reading.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartXmlDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartXmlDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to read the XML file contents and store into new variable called XML Object variable.
	 * 
	 * @param xmlDataRelativeFilePath - the relative path of the XML file with respect to project root directory.
	 * @param variableName - the name of the variable that stores the XML file contents.
	 */
	@When("read {string} XML file contents and store into {string} variable.")
	public void read_xml_file_contents_and_store_into_variable(String xmlDataRelativeFilePath, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		XmlDocumentReader reader = new XmlDocumentReader(new File(Locations.getProjectRootDir() + File.separator + xmlDataRelativeFilePath));
		scenarioContext.addParamValue(variableName, reader.getDocument());
	}
	
	/**
	 * Used to convert XML textual information into XML Object and store into variable to be referenced as XML Object variable.
	 * 
	 * @param xmlText - the plain XML text.
	 * @param variableName - the name of the variable that stores the converted XML text.
	 */
	@When("convert {string} XML text into XML object and store into {string} variable.")
	public void convert_xml_text_into_xml_object_and_store_into_variable(String xmlText, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		xmlText = scenarioContext.applyParamsValueOnText(xmlText);
		XmlDocumentReader reader = new XmlDocumentReader(xmlText);
		
		scenarioContext.addParamValue(variableName, reader.getDocument());
	}
	
	/**
	 * Used to convert textual info stored in xmlInputVariableName into XML object and the converted value is stored into a new variable.
	 * 
	 * @param xmlInputVariableName - the name of the variable that contains text in XML format.
	 * @param variableName - the name of the variable that stores the converted data.
	 */
	@When("convert {string} variable contents into XML object and store into {string} variable.")
	public void convert_variable_contents_into_xml_object_and_store_into_variable(String xmlInputVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String xmlInput = scenarioContext.getParamValueAsString(xmlInputVariableName);
		convert_xml_text_into_xml_object_and_store_into_variable(xmlInput, variableName);
	}
	
	/**
	 * Used to convert XML text specified as cucumber doc string into XML object and store the converted data into new variable.
	 * 
	 * @param variableName - the name of the variable that stores the converted data.
	 * @param xmlText - cucumber doc string that contains the XML text.
	 */
	@When("convert the following XML text into XML object and store into {string} variable:")
	public void convert_the_following_xml_text_into_xml_object_and_store_into_variable(String variableName, DocString xmlText) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String xmlTxt = xmlText.getContent();
		xmlTxt = scenarioContext.applyParamsValueOnText(xmlTxt);
		XmlDocumentReader reader = new XmlDocumentReader(xmlTxt);
		scenarioContext.addParamValue(variableName, reader.getDocument());
	}

	/**
	 * Reads XML parameter value from XML object.
	 * Note that the XML object information is read from 'variableName' variable.
	 * @param xmlPath - for format please {@link ParamPath} class.
	 *     {path: "xpath-here", valueType: "string"}
	 *     For more details on XPATH, please refer {@link https://www.w3.org/TR/1999/REC-xpath-19991116/}
	 *   </pre></blockquote>
	 * @param xmlObjRefVariable - the variable name from where the XML object is read.
	 * @param variableName - the variable name where the extracted info will be stored.
	 */
	@When("read {string} parameter value from XML object [XMLObjRefVariable={string}] and store into {string} variable.")
	public void read_parameter_value_from_xml_object_xml_obj_ref_variable_and_store_into_variable(String xmlPath, String xmlObjRefVariable, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
		Assert.assertNotNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
		assertTrue(xmlObjContext instanceof Document, "'" +xmlObjRefVariable + "' variable is not a XML object. It should be the instance of Document class.");
		
		Document xmlObj = (Document) xmlObjContext;
		XmlDocumentReader xmlReader = new XmlDocumentReader(xmlObj);
		ParamPath paramPath = JsonYamlUtil.parseParamPath(xmlPath);
		Object value = xmlReader.findAttributeOrTextValues(paramPath.getPath(), paramPath);
		scenarioContext.addParamValue(variableName, value);
	}
	
	/**
	 * Used to verify the parameter's values in XML object. Reads information from the data table. Data table format is given below:
	 * First row is always considered as header and data from second row onward is going to get read and validated.
	 * <blockquote><pre>
	 * | Parameter/XML Path                            | Operator           | Expected Information                                                                                               |
	 * | {path: "xpath", valueType: "string"}          | =                  | John Hopkins                                                                                                       |
	 * | {path: "xpath", valueType: "string-list"}     | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 * For supported operators @see org.uitnet.testing.smartfwk.api.core.validator.ValueMatchOperator enum.
	 * For expected information xml format please @see {@link ExpectedInfo}
	 * </pre></blockquote>
	 * 
	 * @param xmlObjRefVariable - the variable name where the XML object is stored.
	 * @param xmlParamInfo - input datatable contains the parameters for verification in the format given below:
	 * <blockquote><pre>
	 *   | Parameter Path / XML Path                     | Operator           | Expected Information                                                                                               |
	 *   | {path: "xpath", valueType: "string"}          | =                  | John Hopkins                                                                                                       |
	 *   | {path: "xpath", valueType: "string-list"}     | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 *    NOTE: Refer {@link https://www.w3.org/TR/1999/REC-xpath-19991116/} link to learn more on XML path.
	 *    NOTE: Refer ){@link ValueMatchOperator} to know what type of operators supported.
	 *    NOTE: For expected information JSON format please refer {@link ExpectedInfo}.
	 *  </pre></blockquote>
	 */
	@Then("verify the following parameters of XML object matches with the expected information as per the tabular info given below [XMLObjRefVariable={string}]:")
	public void verify_the_following_parameters_of_xml_object_matches_with_the_expected_information_as_per_the_tabular_info_given_below_xml_obj_ref_variable(String xmlObjRefVariable, DataTable xmlParamInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
		Assert.assertNotNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
		assertTrue(xmlObjContext instanceof Document, "'" +xmlObjRefVariable + "' variable is not a XML object. It should be the instance of Document class.");
		
		Document xmlObj = (Document) xmlObjContext;
		
		List<List<String>> rows = xmlParamInfo.asLists();
		List<String> row;
		String xmlPath, oper, expectedInfo;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			xmlPath = row.get(0); 
			oper = row.get(1);
			expectedInfo = row.get(2);
			
			xmlPath = scenarioContext.applyParamsValueOnText(xmlPath);
			expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
			
			// verify the actual value against the expected value.
			SmartDataValidator.validateXmlData(xmlObj, xmlPath, oper, expectedInfo);
		}
	}
	
	/**
	 * Used to add XML info as child at a particular XPATH location.
	 *
	 * @param xpath - xml path where to add specified xml info.
	 * @param xmlObjRefVariable - reference variable that hold XML object.
	 * @param xmlInfo - the XML info that need to be added in jsonObjRefVariable at the specified xpath location.
	 * 		xmlInfo can be specified in the following format:
	 *  <blockquote><pre>
	 *   <XmlInfo>
	 *   	add your XML data here...
	 *   </XmlInfo>
	 *  </pre></blockquote>
	 *  NOTE: In the xml data above XmlInfo is just root element. That element is not going to add in xml object.
	 * 		
	 */
	@Then("add the following XML info as child on {string} xpath of XML object [XMLObjRefVariable={string}]:")
	public void add_the_following_xml_info_as_child_on_xml_path_of_xml_object(String xpath, String xmlObjRefVariable, 
			 DocString xmlInfo) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }

	   String xmlInfoStr = xmlInfo.getContent();
	   if(StringUtil.isEmptyAfterTrim(xmlInfoStr)) {
	      return;
	   }

	   xmlInfoStr = scenarioContext.applyParamsValueOnText(xmlInfoStr);

	   Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
	   Assert.assertNotNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
	   assertTrue(xmlObjContext instanceof Document, "'" +xmlObjRefVariable + "' variable is not a XML object. It should be the instance of org.w3c.dom.Document class.");

	   Document xmlObj = (Document) xmlObjContext;

	   xpath = scenarioContext.applyParamsValueOnText(xpath);
	   XmlDocumentReader xmlInfoReader = new XmlDocumentReader(xmlInfoStr);
	   Document xmlInfoToAdd = xmlInfoReader.getDocument();

	   XPath xPath = XPathFactory.newInstance().newXPath();
	   NodeList mNodes = null;
	   
	   try {
		   mNodes = (NodeList) xPath.compile(xpath).evaluate(xmlObj, XPathConstants.NODE);
		   if(mNodes == null || mNodes.getLength() != 1) {
			   Assert.fail("' " + xpath + "' path should return only one element to add additional info under this node.");
		   }
	   } catch(Exception ex) {
		   Assert.fail("Failed to compile '" + xpath + "' xpath for the given XML object.", ex);
	   }
	   
	   Node mNode = (Node) mNodes.item(0);
	   NodeList aNodes =  xmlInfoToAdd.getChildNodes().item(0).getChildNodes();
	   Node aNode;
	   for(int i = 0; i < aNodes.getLength(); i++) {
		   aNode = aNodes.item(i);
		   if(aNode.getNodeType() == Node.ELEMENT_NODE) {
			   mNode.appendChild(xmlObj.adoptNode(aNode));
		   }
	   }
	   
	   scenarioContext.addParamValue(xmlObjRefVariable, xmlObj);
	}
	
	/**
	 * Used to remove XML info at a particular xml path location.
	 * 
	 * @param xmlObjRefVariable - the variable name that holds XML object.
	 * @param xmlPaths - the input data table that holds xmlPath information to be removed in the format given below:
	 * 	Sample table:
	 *  | XML Path                             |
	 *  | //users[@id = '12']                  |
	 *  | //departments[contains(@name, 'HR')] |
	 */
	@Then("remove the following xpaths from XML object [XMLObjRefVariable={string}]:")
	public void remove_the_xml_elements_identified_by_xpath_from_xml_object(String xmlObjRefVariable, DataTable xmlPaths) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }

	   Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
	   Assert.assertNotNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
	   assertTrue(xmlObjContext instanceof Document, "'" +xmlObjRefVariable + "' variable is not a XML object. It should be the instance of org.w3c.dom.Document class.");

	   Document xmlObj = (Document) xmlObjContext;
	   
	   List<List<String>> rows = xmlPaths.asLists();
	   
	   String xmlPath;
	   List<String> row;
	   NodeList mNodes = null;
	   
	   for(int i = 1; i < rows.size(); i++) {
		   row = rows.get(i);
		   xmlPath = row.get(0);
		   xmlPath = scenarioContext.applyParamsValueOnText(xmlPath);
		   
		   XPath xPath = XPathFactory.newInstance().newXPath();
		   
		   
		   try {
			   mNodes = (NodeList) xPath.compile(xmlPath).evaluate(xmlObj, XPathConstants.NODESET);
		   } catch(Exception ex) {
			   Assert.fail("Failed to compile '" + xmlPath + "' xpath for the given XML object.", ex);
		   }
		   
		   if(mNodes != null) {
			   Element mNode;
			   for(int j = 0; j < mNodes.getLength(); j++) {
				   mNode = (Element) mNodes.item(j);
				   if(mNode.getNodeType() == Node.ELEMENT_NODE) {
					   mNode.getParentNode().removeChild(mNode);
				   }
			   }
		   }
	   }
	   	   
	   scenarioContext.addParamValue(xmlObjRefVariable, xmlObj);
	}
	
	/**
	 * Used to update XML element's attributes and text information identified by xpath for the XML object.
	 * If attribute is missing in specified XML object then it will add the missing attribute.
	 *
	 * @param xpath - xml path where to update the attributes information.
	 * @param xmlObjRefVariable - reference variable that hold XML object.
	 * @param xmlInfo - the XML info that contains attributes information to be updated into XML object.
	 * 		xmlInfo can be specified in the following format:
	 *  <blockquote><pre>
	 *   	<XmlInfo attr1="attr1 value" attr2="attr2 value">text part here</XmlInfo>
	 *  </pre></blockquote>
	 *  NOTE-1: In the xml data above XmlInfo is just root element. That element is not going to add in xml object.
	 *  	Only the attributes and text part information will get updated into XML object on the specified xpath.
	 *  
	 * 	NOTE2-2: If Text part information is empty then it will not update text part information in XML object. To 
	 * 		set the text part information to empty you should set text part as [[EMPTY]].
	 */
	@Then("update the following attributes information and text part for XML elements identified by {string} xpath of XML object [XMLObjRefVariable={string}]:")
	public void update_the_following_attributes_information_and_text_part_for_xml_elements_identified_by_xpath_of_xml_object(String xpath, String xmlObjRefVariable, DocString xmlInfo) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }

	   String xmlInfoStr = xmlInfo.getContent();
	   if(StringUtil.isEmptyAfterTrim(xmlInfoStr)) {
	      return;
	   }

	   xmlInfoStr = scenarioContext.applyParamsValueOnText(xmlInfoStr);

	   Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
	   Assert.assertNotNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
	   assertTrue(xmlObjContext instanceof Document, "'" +xmlObjRefVariable + "' variable is not a XML object. It should be the instance of org.w3c.dom.Document class.");

	   Document xmlObj = (Document) xmlObjContext;

	   xpath = scenarioContext.applyParamsValueOnText(xpath);
	   XmlDocumentReader xmlInfoReader = new XmlDocumentReader(xmlInfoStr);
	   Document xmlInfoToAdd = xmlInfoReader.getDocument();

	   XPath xPath = XPathFactory.newInstance().newXPath();
	   NodeList mNodes = null;
	   
	   try {
		   mNodes = (NodeList) xPath.compile(xpath).evaluate(xmlObj, XPathConstants.NODESET);
		   if(mNodes == null || mNodes.getLength() < 1) {
			   return;
		   }
	   } catch(Exception ex) {
		   Assert.fail("Failed to compile '" + xpath + "' xpath for the given XML object.", ex);
	   }
	   
	   Element aNode =  XmlDocumentReader.getChildElement(xmlInfoToAdd, 0);
	   NamedNodeMap attrs = aNode.getAttributes();
	   
	   Node node;
	   Element mNode;
	   Attr attr;
	   for(int i = 0; i < mNodes.getLength(); i++) {
		   node =  mNodes.item(i);
		   if(node.getNodeType() != Node.ELEMENT_NODE) { continue; }
		  
		   mNode = (Element)node;
		   
		   if(attrs != null) {
			   for(int j = 0; j < attrs.getLength(); j++) {
				   attr = (Attr) attrs.item(j);
				   mNode.setAttribute(attr.getName(), attr.getValue());
			   }
		   }
		   
		   String textPart = StringUtil.trim(aNode.getTextContent());
		   
		   if(StringUtil.isEmptyNoTrim(textPart)) {
			   // do nothing
		   } else if("[[EMPTY]]".equals(textPart)) {
			   mNode.setTextContent("");
		   } else {
			   mNode.setTextContent(aNode.getTextContent()); 
		   }
	   }	   
	   
	   scenarioContext.addParamValue(xmlObjRefVariable, xmlObj);
	}
	
	/**
	 * Used to remove XML element's attributes information identified by xpath for the XML object.
	 * If attribute is missing in specified XML object then it will ignore that attribute.
	 *
	 * @param xpath - xml path where to remove the attributes information.
	 * @param xmlObjRefVariable - reference variable that hold XML object.
	 * @param xmlInfo - the XML info that contains attributes information to be removed from the XML object.
	 * 		xmlInfo can be specified in the following format:
	 *  <blockquote><pre>
	 *   	<Attributes attr1="attr1 value" attr2="attr2 value"/>
	 *  </pre></blockquote>
	 *  NOTE: In the xml data above XmlInfo is just root element. That element is not going to removed from xml object.
	 *  	From XmlInfo element only the attributes information is going to get retrieved and will get
	 *  	removed from the XML object on the specified xpath.
	 * 		
	 */
	@Then("remove the following attributes information for XML elements identified by {string} xpath of XML object [XMLObjRefVariable={string}]:")
	public void remove_the_following_attributes_information_for_xml_elements_identified_by_xpath_of_xml_object(String xpath, String xmlObjRefVariable, DocString xmlInfo) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }

	   String xmlInfoStr = xmlInfo.getContent();
	   if(StringUtil.isEmptyAfterTrim(xmlInfoStr)) {
	      return;
	   }

	   xmlInfoStr = scenarioContext.applyParamsValueOnText(xmlInfoStr);

	   Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
	   Assert.assertNotNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
	   assertTrue(xmlObjContext instanceof Document, "'" +xmlObjRefVariable + "' variable is not a XML object. It should be the instance of org.w3c.dom.Document class.");

	   Document xmlObj = (Document) xmlObjContext;

	   xpath = scenarioContext.applyParamsValueOnText(xpath);
	   XmlDocumentReader xmlInfoReader = new XmlDocumentReader(xmlInfoStr);
	   Document xmlInfoToAdd = xmlInfoReader.getDocument();

	   XPath xPath = XPathFactory.newInstance().newXPath();
	   NodeList mNodes = null;
	   
	   try {
		   mNodes = (NodeList) xPath.compile(xpath).evaluate(xmlObj, XPathConstants.NODESET);
		   if(mNodes == null || mNodes.getLength() < 1) {
			   return;
		   }
	   } catch(Exception ex) {
		   Assert.fail("Failed to compile '" + xpath + "' xpath for the given XML object.", ex);
	   }
	   
	   Element aNode =  XmlDocumentReader.getChildElement(xmlInfoToAdd, 0);
	   NamedNodeMap attrs = aNode.getAttributes();
	   
	   if(attrs == null || attrs.getLength() == 0) {
		   return;
	   }
	   
	   Element mNode;
	   Attr attr;
	   for(int i = 0; i < mNodes.getLength(); i++) {
		   mNode =  (Element) mNodes.item(i);
		   if(mNode.getNodeType() != Node.ELEMENT_NODE) { continue; }
		   
		   for(int j = 0; j < attrs.getLength(); j++) {
			   attr = (Attr) attrs.item(j);
			   if(mNode.hasAttribute(attr.getName())) {
				   mNode.removeAttribute(attr.getName());
			   }
		   }
	   }	   
	   
	   scenarioContext.addParamValue(xmlObjRefVariable, xmlObj);
	}
	
	/**
	 * Used to print XML object.
	 * 
	 * @param xmlObjRefVariable
	 */
	@Then("print XML object [XMLObjRefVariable={string}].")
	public void print_json_object(String xmlObjRefVariable) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);

		if (xmlObjContext == null) {
			scenarioContext.log(null);
			return;
		}
		
		Document xmlObj = (Document) xmlObjContext;
		
		DOMImplementationRegistry registry;
		try {
			registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSSerializer writer = impl.createLSSerializer();
			writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			LSOutput output = impl.createLSOutput();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			output.setByteStream(out);
			writer.write(xmlObj, output);
			String xmlStr = new String(out.toByteArray());
			
			out.close();
			
			scenarioContext.log(xmlStr);
		} catch (Exception e) {
			Assert.fail("Failed to print XML object.", e);
		}
		
	}
}

/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond
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

import java.io.File;
import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.w3c.dom.Document;

import dev.failsafe.internal.util.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Used to perform CRUD operations on XML text / objects.
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
		Assert.notNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
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
	 * | Parameter/XML Path                            | Operator           | Expected Information                                                                                               |
	 * | {path: "xpath", valueType: "string"}          | =                  | John Hopkins                                                                                                       |
	 * | {path: "xpath", valueType: "string-list"}     | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 * For supported operators @see org.uitnet.testing.smartfwk.api.core.validator.ValueMatchOperator enum.
	 * For expected information xml format please @see {@link ExpectedInfo}
	 * 
	 * @param xmlObjRefVariable - the variable name where the XML object is stored.
	 * @param xmlParamInfo - input datatable contains the parameters for verification in the format given below:
	 *   | Parameter Path / XML Path                     | Operator           | Expected Information                                                                                               |
	 *   | {path: "xpath", valueType: "string"}          | =                  | John Hopkins                                                                                                       |
	 *   | {path: "xpath", valueType: "string-list"}     | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 *    NOTE: Refer {@link https://www.w3.org/TR/1999/REC-xpath-19991116/} link to learn more on XML path.
	 *    NOTE: Refer ){@link ValueMatchOperator} to know what type of operators supported.
	 *    NOTE: For expected information JSON format please refer {@link ExpectedInfo}.
	 */
	@Then("verify the following parameters of XML object matches with the expected information as per the tabular info given below [XMLObjRefVariable={string}]:")
	public void verify_the_following_parameters_of_xml_object_matches_with_the_expected_information_as_per_the_tabular_info_given_below_xml_obj_ref_variable(String xmlObjRefVariable, DataTable xmlParamInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object xmlObjContext = scenarioContext.getParamValue(xmlObjRefVariable);
		Assert.notNull(xmlObjContext, "'" +xmlObjRefVariable + "' variable does not have XML object. Found null.");
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
}

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
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

import com.jayway.jsonpath.DocumentContext;

import dev.failsafe.internal.util.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to YAML file data reading.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartYamlDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartYamlDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to read the YAML file contents and store into new variable called YAML Object variable.
	 * 
	 * @param yamlDataRelativeFilePath - the relative path of the YAML file with respect to project root directory.
	 * @param variableName - the name of the variable that stores the JSON file contents. 
	 */
	@When("read {string} YAML file contents and store into {string} variable.")
	public void read_yaml_file_contents_and_store_into_variable(String yamlDataRelativeFilePath, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		YamlDocumentReader reader = new YamlDocumentReader(new File(Locations.getProjectRootDir() + File.separator + yamlDataRelativeFilePath), false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	/**
	 * Used to convert YAML textual information into YAML Object and store into variable to be referenced as YAML Object variable.
	 * 
	 * @param yamlText - the plain YAML text.
	 * @param variableName - the name of the variable that stores the converted YAML text.
	 */
	@When("convert {string} YAML text into YAML object and store into {string} variable.")
	public void convert_yaml_text_into_yaml_object_and_store_into_variable(String yamlText, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		yamlText = scenarioContext.applyParamsValueOnText(yamlText);
		YamlDocumentReader reader = new YamlDocumentReader(yamlText, false);
		
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	/**
	 * Used to convert textual info stored in yamlInputVariableName into YAML object and the converted value is stored into a new variable.
	 * 
	 * @param yamlInputVariableName - the name of the variable that contains text in YAML format.
	 * @param variableName - the name of the variable that stores the converted data.
	 */
	@When("convert {string} variable contents into YAML object and store into {string} variable.")
	public void convert_variable_contents_into_yaml_object_and_store_into_variable(String yamlInputVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String yamlInput = scenarioContext.getParamValueAsString(yamlInputVariableName);
		convert_yaml_text_into_yaml_object_and_store_into_variable(yamlInput, variableName);
	}
	
	/**
	 * Used to convert YAML text specified as cucumber doc string into YAML object and store the converted data into new variable.
	 * 
	 * @param variableName - the name of the variable that stores the converted data.
	 * @param yamlText - cucumber doc string that contains the YAML text.
	 */
	@When("convert the following YAML text into YAML object and store into {string} variable:")
	public void convert_the_following_yaml_text_into_yaml_object_and_store_into_variable(String variableName, DocString yamlText) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String yamlTxt = yamlText.getContent();
		yamlTxt = scenarioContext.applyParamsValueOnText(yamlTxt);
		YamlDocumentReader reader = new YamlDocumentReader(yamlTxt, false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}

	/**
	 * Used to reads YAML parameter value from YAML object reference variable. 
	 * YAML parameter must be specified as JSON path to retrieve its value. 
	 * It stores the retrieved value into new variable.
	 * 
	 * @param yamlPath - the parameter path specified using JSON path syntax.
	 * 		Refer {@link https://github.com/json-path/JsonPath} link to learn more on JSON path.
	 * 
	 * @param yamlObjRefVariable - the variable name where the YAML object is stored.
	 * @param variableName - the name of the variable where the extracted info will be stored.
	 */
	@When("read {string} parameter value from YAML object [YAMLObjRefVariable={string}] and store into {string} variable.")
	public void read_parameter_value_from_yaml_object_yaml_obj_ref_variable_and_store_into_variable(String yamlPath, String yamlObjRefVariable, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object yamlObjContext = scenarioContext.getParamValue(yamlObjRefVariable);
		Assert.notNull(yamlObjContext, "'" +yamlObjRefVariable + "' variable does not have YAML object. Found null.");
		assertTrue(yamlObjContext instanceof DocumentContext, "'" +yamlObjRefVariable + "' variable is not a YAML object. It should be the instance of DocumentContext class.");
		
		DocumentContext yamlObj = (DocumentContext) yamlObjContext;
		Object value = yamlObj.read(yamlPath);
		scenarioContext.addParamValue(variableName, value);
	}
	
	/**
	 * Updates the YAML parameter's value into YAML object as per the tabular info provided by cucumber datatable.
	 * First row is always considered as the header and data from second row onward is going to get read
	 * and going to get applied into JSON object.
	 * 
	 * | Parameter Path / YAML Path                        | New Value                  |
	 * | $.name                                            | <New name Here>            |
	 * | { path: "$.jobTitles", valueType: "string-list" } | ["Accountant", "Operator"] |
	 * 
	 * 
	 * NOTE-1: YAML object is stored into variable.
	 * 
	 * @param yamlObjRefVariable - the variable name where the YAML object is stored.
	 * @param yamlParamInfo - cucumber data table in the format given below:
	 * 		| Parameter Path / YAML Path                        | New Value                  |
	 *      | $.name                                            | <New name Here>            |
	 *      | { path: "$.jobTitles", valueType: "string-list" } | ["Accountant", "Operator"] |
	 *      
	 *      NOTE: Refer {@link https://github.com/json-path/JsonPath} link to learn more on YAML/JSON path.
	 *      NOTE: New value can be of any type like: string, integer, long, double, float, string-list, numeric-list
	 *      	  for more details on new value type please refer: {@link ParamValueType}. As per JSON syntax, multiple
	 *            values can be specified within square brackets [].
	 *      
	 */
	@When("update the following parameters values into YAML object [YAMLObjRefVariable={string}]:")
	public void read_parameter_value_from_yaml_object_yaml_obj_ref_variable(String yamlObjRefVariable, DataTable yamlParamInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object yamlObjContext = scenarioContext.getParamValue(yamlObjRefVariable);
		Assert.notNull(yamlObjContext, "'" +yamlObjRefVariable + "' variable does not have YAML object. Found null.");
		assertTrue(yamlObjContext instanceof DocumentContext, "'" +yamlObjRefVariable + "' variable is not a YAML object. It should be the instance of DocumentContext class.");
		
		DocumentContext yamlObj = (DocumentContext) yamlObjContext;
		
		List<List<String>> rows = yamlParamInfo.asLists();
		List<String> row;
		String paramName, paramNewValue;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			paramName = row.get(0); // paramName
			paramNewValue = row.get(1); // paramnewValue
			
			paramName = scenarioContext.applyParamsValueOnText(paramName);
			paramNewValue = scenarioContext.applyParamsValueOnText(paramNewValue);
			
			ParamPath pPath = JsonYamlUtil.parseParamPath(paramName);
			Object parsedValue = JsonYamlUtil.convertTextToTypedValue(pPath.getPath(), pPath.getValueType(), paramNewValue);
			
			yamlObj.set(pPath.getPath(), parsedValue);
		}

		scenarioContext.addParamValue(yamlObjRefVariable, yamlObj);
	}
	
	/**
	 * Used to verify the parameter's values in YAML object. Reads information from the data table. Data table format is given below:
	 * First row is always considered as the header and data from second row onward is going to get read and validated.
	 * | Parameter/YAML Path        | Operator           | Expected Information                                                                                               |
	 * | $.name                     | =                  | John Hopkins                                                                                                       |
	 * | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 * @param yamlObjRefVariable - the variable name where the JSON object is stored.
	 * @param yamlParamInfo - cucumber datatable variable that stores the input parameter info for verification in the syntax below:
	 *   | Parameter/YAML Path        | Operator           | Expected Information                                                                                               |
	 *   | $.name                     | =                  | John Hopkins                                                                                                       |
	 *   | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 *   Refer {@link https://github.com/json-path/JsonPath} link to learn more on YAML path.
	 *   For supported operators {@link ValueMatchOperator} enum.
	 *   For expected information JSON format please refer {@link ExpectedInfo}.
	 */
	@Then("verify the following parameters of YAML object matches with the expected information as per the tabular info given below [YAMLObjRefVariable={string}]:")
	public void verify_the_following_parameters_of_yaml_object_matches_with_the_expected_information_as_per_the_tabular_info_given_below_yaml_obj_ref_variable(String yamlObjRefVariable, DataTable yamlParamInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object yamlObjContext = scenarioContext.getParamValue(yamlObjRefVariable);
		Assert.notNull(yamlObjContext, "'" +yamlObjRefVariable + "' variable does not have YAML object. Found null.");
		assertTrue(yamlObjContext instanceof DocumentContext, "'" +yamlObjRefVariable + "' variable is not a YAML object. It should be the instance of DocumentContext class.");
		
		DocumentContext yamlObj = (DocumentContext) yamlObjContext;
		
		List<List<String>> rows = yamlParamInfo.asLists();
		List<String> row;
		String yamlPath, oper, expectedInfo;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			yamlPath = row.get(0); 
			oper = row.get(1);
			expectedInfo = row.get(2);
			
			yamlPath = scenarioContext.applyParamsValueOnText(yamlPath);
			expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
			
			// verify the actual value against the expected value.
			SmartDataValidator.validateJsonOrYamlData(yamlObj, yamlPath, oper, expectedInfo);
		}
	}
}

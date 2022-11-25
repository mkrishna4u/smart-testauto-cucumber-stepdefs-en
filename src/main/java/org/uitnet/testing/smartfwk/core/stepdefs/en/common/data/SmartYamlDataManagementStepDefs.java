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
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

import com.jayway.jsonpath.DocumentContext;

import dev.failsafe.internal.util.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Used to perform CRUD operations on YAML text / objects.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartYamlDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartYamlDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	@When("read {string} YAML file contents and store into {string} variable.")
	public void read_yaml_file_contents_and_store_into_variable(String yamlDataRelativeFilePath, String variableName) {
		YamlDocumentReader reader = new YamlDocumentReader(new File(Locations.getProjectRootDir() + File.separator + yamlDataRelativeFilePath), false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	@When("convert {string} YAML text into YAML object and store into {string} variable.")
	public void convert_yaml_text_into_yaml_object_and_store_into_variable(String yamlText, String variableName) {
		yamlText = scenarioContext.applyParamsValueOnText(yamlText);
		YamlDocumentReader reader = new YamlDocumentReader(yamlText, false);
		
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	@When("convert {string} variable contents into YAML object and store into {string} variable.")
	public void convert_variable_contents_into_yaml_object_and_store_into_variable(String yamlInputVariableName, String variableName) {
		String yamlInput = scenarioContext.getParamValueAsString(yamlInputVariableName);
		convert_yaml_text_into_yaml_object_and_store_into_variable(yamlInput, variableName);
	}
	
	@When("convert the following YAML text into YAML object and store into {string} variable:")
	public void convert_the_following_yaml_text_into_yaml_object_and_store_into_variable(String variableName, DocString yamlText) {
		String yamlTxt = yamlText.getContent();
		yamlTxt = scenarioContext.applyParamsValueOnText(yamlTxt);
		YamlDocumentReader reader = new YamlDocumentReader(yamlTxt, false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}

	/**
	 * Reads YAML parameter value from YAML object.
	 * Note that the YAML object information is read from 'variableName' variable.
	 * @param yamlPath
	 * @param yamlObjRefVariable - variable name where the YAML object is stored.
	 * @param variableName - where the extracted info will be stored.
	 */
	@When("read {string} parameter value from YAML object [YAMLObjRefVariable={string}] and store into {string} variable.")
	public void read_parameter_value_from_yaml_object_yaml_obj_ref_variable_and_store_into_variable(String yamlPath, String yamlObjRefVariable, String variableName) {
		Object yamlObjContext = scenarioContext.getParamValue(yamlObjRefVariable);
		Assert.notNull(yamlObjContext, "'" +yamlObjRefVariable + "' variable does not have YAML object. Found null.");
		assertTrue(yamlObjContext instanceof DocumentContext, "'" +yamlObjRefVariable + "' variable is not a YAML object. It should be the instance of DocumentContext class.");
		
		DocumentContext yamlObj = (DocumentContext) yamlObjContext;
		Object value = yamlObj.read(yamlPath);
		scenarioContext.addParamValue(variableName, value);
	}
	
	/**
	 * Updates the YAML parameter's value into YAML object as per the tabular info given below:
	 * First row is always considered as header and data from second row onward is going to get read
	 * and going to get applied into YAML object.
	 * 
	 * | Parameter Path                                    | New Value                  |
	 * | $.name                                            | <New name Here>            |
	 * | { path: "$.jobTitles", valueType: "string-list" } | ["Accountant", "Operator"] |
	 * 
	 * 
	 * NOTE-1: YAML object is stored into variable.
	 * 
	 * @param yamlObjRefVariable - variable name where the YAML object is stored.
	 * @param yamlParamInfo - cucumber data table in the format given above.
	 */
	@When("update the following parameters values into YAML object [YAMLObjRefVariable={string}]:")
	public void read_parameter_value_from_yaml_object_yaml_obj_ref_variable(String yamlObjRefVariable, DataTable yamlParamInfo) {
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
	 * First row is always considered as header and data from second row onward is going to get read and validated.
	 * | Parameter/YAML Path        | Operator           | Expected Information                                                                                               |
	 * | $.name                     | =                  | John Hopkins                                                                                                       |
	 * | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 * For supported operators @see org.uitnet.testing.smartfwk.api.core.validator.ValueMatchOperator enum.
	 * For expected information yaml format please @see {@link ExpectedInfo}
	 * 
	 * @param yamlObjRefVariable - variable name where the YAML object is stored.
	 * @param yamlParamInfo - input parameter info for verification.
	 */
	@Then("verify the following parameters of YAML object matches with the expected information as per the tabular info given below [YAMLObjRefVariable={string}]:")
	public void verify_the_following_parameters_of_yaml_object_matches_with_the_expected_information_as_per_the_tabular_info_given_below_yaml_obj_ref_variable(String yamlObjRefVariable, DataTable yamlParamInfo) {
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

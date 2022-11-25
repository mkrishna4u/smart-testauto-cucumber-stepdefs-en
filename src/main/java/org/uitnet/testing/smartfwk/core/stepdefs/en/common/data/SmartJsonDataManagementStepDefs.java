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

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

import com.jayway.jsonpath.DocumentContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Used to perform CRUD operations on JSON text / objects.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartJsonDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartJsonDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	@When("read {string} JSON file contents and store into {string} variable.")
	public void read_json_file_contents_and_store_into_variable(String jsonDataRelativeFilePath, String variableName) {
		JsonDocumentReader reader = new JsonDocumentReader(new File(Locations.getProjectRootDir() + File.separator + jsonDataRelativeFilePath), false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	@When("convert {string} JSON text into JSON object and store into {string} variable.")
	public void convert_json_text_into_json_object_and_store_into_variable(String jsonText, String variableName) {
		jsonText = scenarioContext.applyParamsValueOnText(jsonText);
		JsonDocumentReader reader = new JsonDocumentReader(jsonText, false);
		
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	@When("convert {string} variable contents into JSON object and store into {string} variable.")
	public void convert_variable_contents_into_json_object_and_store_into_variable(String jsonInputVariableName, String variableName) {
		String jsonInput = scenarioContext.getParamValueAsString(jsonInputVariableName);
		convert_json_text_into_json_object_and_store_into_variable(jsonInput, variableName);
	}
	
	@When("convert the following JSON text into JSON object and store into {string} variable:")
	public void convert_the_following_json_text_into_json_object_and_store_into_variable(String variableName, DocString jsonText) {
		String jsonTxt = jsonText.getContent();
		jsonTxt = scenarioContext.applyParamsValueOnText(jsonTxt);
		JsonDocumentReader reader = new JsonDocumentReader(jsonTxt, false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}

	/**
	 * Reads JSON parameter value from JSON object.
	 * Note that the JSON object information is read from 'variableName' variable.
	 * @param jsonPath
	 * @param jsonObjRefVariable - variable name where the JSON object is stored.
	 * @param variableName - where the extracted info will be stored.
	 */
	@When("read {string} parameter value from JSON object [JSONObjRefVariable={string}] and store into {string} variable.")
	public void read_parameter_value_from_json_object_json_obj_ref_variable_and_store_into_variable(String jsonPath, String jsonObjRefVariable, String variableName) {
		Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
		Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
		assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");
		
		DocumentContext jsonObj = (DocumentContext) jsonObjContext;
		Object value = jsonObj.read(jsonPath);
		scenarioContext.addParamValue(variableName, value);
	}
	
	/**
	 * Updates the JSON parameter's value into JSON object as per the tabular info given below:
	 * First row is always considered as header and data from second row onward is going to get read
	 * and going to get applied into JSON object.
	 * 
	 * | Parameter Path                                    | New Value                  |
	 * | $.name                                            | <New name Here>            |
	 * | { path: "$.jobTitles", valueType: "string-list" } | ["Accountant", "Operator"] |
	 * 
	 * 
	 * NOTE-1: JSON object is stored into variable.
	 * 
	 * @param jsonObjRefVariable - variable name where the JSON object is stored.
	 * @param jsonParamInfo - cucumber data table in the format given above.
	 */
	@When("update the following parameters values into JSON object [JSONObjRefVariable={string}]:")
	public void read_parameter_value_from_json_object_json_obj_ref_variable(String jsonObjRefVariable, DataTable jsonParamInfo) {
		Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
		Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
		assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");
		
		DocumentContext jsonObj = (DocumentContext) jsonObjContext;
		
		List<List<String>> rows = jsonParamInfo.asLists();
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
			
			jsonObj.set(pPath.getPath(), parsedValue);
		}

		scenarioContext.addParamValue(jsonObjRefVariable, jsonObj);
	}
	
	/**
	 * Used to verify the parameter's values in JSON object. Reads information from the data table. Data table format is given below:
	 * First row is always considered as header and data from second row onward is going to get read and validated.
	 * | Parameter/JSON Path        | Operator           | Expected Information                                                                                               |
	 * | $.name                     | =                  | John Hopkins                                                                                                       |
	 * | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 * For supported operators @see org.uitnet.testing.smartfwk.api.core.validator.ValueMatchOperator enum.
	 * For expected information json format please @see {@link ExpectedInfo}
	 * 
	 * @param jsonObjRefVariable - variable name where the JSON object is stored.
	 * @param jsonParamInfo - input parameter info for verification.
	 */
	@Then("verify the following parameters of JSON object matches with the expected information as per the tabular info given below [JSONObjRefVariable={string}]:")
	public void verify_the_following_parameters_of_json_object_matches_with_the_expected_information_as_per_the_tabular_info_given_below_json_obj_ref_variable(String jsonObjRefVariable, DataTable jsonParamInfo) {
		Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
		Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
		assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");
		
		DocumentContext jsonObj = (DocumentContext) jsonObjContext;
		
		List<List<String>> rows = jsonParamInfo.asLists();
		List<String> row;
		String jsonPath, oper, expectedInfo;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			jsonPath = row.get(0); 
			oper = row.get(1);
			expectedInfo = row.get(2);
			
			jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
			expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
			
			// verify the actual value against the expected value.
			SmartDataValidator.validateJsonOrYamlData(jsonObj, jsonPath, oper, expectedInfo);
		}
	}
}

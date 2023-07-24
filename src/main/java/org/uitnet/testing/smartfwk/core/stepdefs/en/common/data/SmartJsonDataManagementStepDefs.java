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

import java.io.File;
import java.util.List;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jayway.jsonpath.DocumentContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to JSON file data reading and conversion of variable data into JSON format.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartJsonDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartJsonDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to read the JSON file contents and store into new variable called JSON Object variable.
	 * 
	 * @param jsonDataRelativeFilePath - the relative path of the JSON file with respect to project root directory.
	 * @param variableName - the name of the variable that stores the JSON file contents. 
	 */
	@When("read {string} JSON file contents and store into {string} variable.")
	public void read_json_file_contents_and_store_into_variable(String jsonDataRelativeFilePath, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		JsonDocumentReader reader = new JsonDocumentReader(new File(Locations.getProjectRootDir() + File.separator + jsonDataRelativeFilePath), false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	/**
	 * Used to convert JSON textual information into JSON Object and store into variable to be referenced as JSON Object variable.
	 * 
	 * @param jsonText - the plain JSON text.
	 * @param variableName - the name of the variable that stores the converted JSON text.
	 */
	@When("convert {string} JSON text into JSON object and store into {string} variable.")
	public void convert_json_text_into_json_object_and_store_into_variable(String jsonText, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		jsonText = scenarioContext.applyParamsValueOnText(jsonText);
		JsonDocumentReader reader = new JsonDocumentReader(jsonText, false);
		
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}
	
	/**
	 * Used to convert textual info stored in jsonInputVariableName into JSON object and the converted value is stored into a new variable.
	 * 
	 * @param jsonInputVariableName - the name of the variable that contains text in JSON format.
	 * @param variableName - the name of the variable that stores the converted data.
	 */
	@When("convert {string} variable contents into JSON object and store into {string} variable.")
	public void convert_variable_contents_into_json_object_and_store_into_variable(String jsonInputVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String jsonInput = scenarioContext.getParamValueAsString(jsonInputVariableName);
		convert_json_text_into_json_object_and_store_into_variable(jsonInput, variableName);
	}
	
	/**
	 * Used to convert JSON text specified as cucumber doc string into JSON object and store the converted data into new variable.
	 * 
	 * @param variableName - the name of the variable that stores the converted data.
	 * @param jsonText - cucumber doc string that contains the JSON text.
	 */
	@When("convert the following JSON text into JSON object and store into {string} variable:")
	public void convert_the_following_json_text_into_json_object_and_store_into_variable(String variableName, DocString jsonText) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String jsonTxt = jsonText.getContent();
		jsonTxt = scenarioContext.applyParamsValueOnText(jsonTxt);
		JsonDocumentReader reader = new JsonDocumentReader(jsonTxt, false);
		scenarioContext.addParamValue(variableName, reader.getDocumentContext());
	}

	/**
	 * Used to reads JSON parameter value from JSON object reference variable. 
	 * JSON parameter must be specified as JSON path to retrieve its value. 
	 * It stores the retrieved value into new variable.
	 * 
	 * @param jsonPath - the parameter path specified using JSON path syntax.
	 * 		Refer {@link https://github.com/json-path/JsonPath} link to learn more on JSON path.
	 * 
	 * @param jsonObjRefVariable - the variable name where the JSON object is stored.
	 * @param variableName - the name of the variable where the extracted info will be stored.
	 */
	@When("read {string} parameter value from JSON object [JSONObjRefVariable={string}] and store into {string} variable.")
	public void read_parameter_value_from_json_object_json_obj_ref_variable_and_store_into_variable(String jsonPath, String jsonObjRefVariable, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
		
		Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
		Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
		assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");
		
		DocumentContext jsonObj = (DocumentContext) jsonObjContext;
		Object value = jsonObj.read(jsonPath);
		scenarioContext.addParamValue(variableName, value);
	}
	
	/**
	 * Updates the JSON parameter's value into JSON object as per the tabular info provided by cucumber datatable.
	 * First row is always considered as the header and data from second row onward is going to get read
	 * and going to get applied into JSON object.
	 * <blockquote><pre>
	 * | Parameter Path / JSON Path                        | New Value                  |
	 * | $.name                                            | <New name Here>            |
	 * | { path: "$.jobTitles", valueType: "string-list" } | ["Accountant", "Operator"] |
	 * 
	 * 
	 * NOTE-1: JSON object is stored into variable.
	 * </pre></blockquote>
	 * 
	 * @param jsonObjRefVariable - the variable name where the JSON object is stored.
	 * @param jsonParamInfo - cucumber data table in the format given below:
	 * <blockquote><pre>
	 * 		| Parameter Path / JSON Path                        | New Value                  |
	 *      | $.name                                            | <New name Here>            |
	 *      | { path: "$.jobTitles", valueType: "string-list" } | ["Accountant", "Operator"] |
	 *      
	 *      NOTE: Refer {@link https://github.com/json-path/JsonPath} link to learn more on JSON path.
	 *      	JSON Way to specify JSON Path:
	 *   			{path: "$.myParam", valueType: "string"}
	 *          For more details on JSON Path parameter, please refer {@link ParamPath} class.
	 *          For more details n valueType, please refer {@link ParamValueType} enum.
	 *      NOTE: New value can be of any type like: string, integer, long, double, float, string-list, numeric-list
	 *      	  for more details on new value type please refer: {@link ParamValueType}. As per JSON syntax, multiple
	 *            values can be specified within square brackets [].
	 * </pre></blockquote>     
	 */
	@When("update the following parameters values into JSON object [JSONObjRefVariable={string}]:")
	public void read_parameter_value_from_json_object_json_obj_ref_variable(String jsonObjRefVariable, DataTable jsonParamInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
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
	 * First row is always considered as the header and data from second row onward is going to get read and validated.
	 * <blockquote><pre>
	 * | Parameter/JSON Path        | Operator           | Expected Information                                                                                               |
	 * | $.name                     | =                  | John Hopkins                                                                                                       |
	 * | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * </pre></blockquote>
	 * 
	 * @param jsonObjRefVariable - the variable name where the JSON object is stored.
	 * @param jsonParamInfo - cucumber datatable variable that stores the input parameter info for verification in the syntax below:
	 * <blockquote><pre>
	 *   | Parameter/JSON Path        | Operator           | Expected Information                                                                                               |
	 *   | $.name                     | =                  | John Hopkins                                                                                                       |
	 *   | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 *   Refer {@link https://github.com/json-path/JsonPath} link to learn more on JSON path.
	 *   		JSON Way to specify JSON Path:
	 *   			{path: "$.myParam", valueType: "string"}
	 *          For more details on JSON Path parameter, please refer {@link ParamPath} class.
	 *          For more details n valueType, please refer {@link ParamValueType} enum.
	 *   For supported operators refer {@link ValueMatchOperator}.
	 *   For expected information JSON format please refer {@link ExpectedInfo}.
	 * </pre></blockquote>
	 */
	@Then("verify the following parameters of JSON object matches with the expected information as per the tabular info given below [JSONObjRefVariable={string}]:")
	public void verify_the_following_parameters_of_json_object_matches_with_the_expected_information_as_per_the_tabular_info_given_below_json_obj_ref_variable(String jsonObjRefVariable, DataTable jsonParamInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
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
	
	/**
	 * Used to add JSON info at a particular JSON path.
	 *
	 * @param jsonPath - path where to add specified json info.
	 * @param jsonObjRefVariable - reference variable that hold JSON object.
	 * @param jsonInfo - the JSON info that need to be added in jsonObjRefVariable at the specified jsonPath.
	 */
	@Then("add the following JSON info on {string} JSON path of JSON object [JSONObjRefVariable={string}]:")
	public void add_the_following_json_info_on_json_path_of_json_object(String jsonPath, String jsonObjRefVariable, DocString jsonInfo) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }

	   String jsonInfoStr = jsonInfo.getContent();
	   if(StringUtil.isEmptyAfterTrim(jsonInfoStr)) {
	      return;
	   }

	   jsonInfoStr = scenarioContext.applyParamsValueOnText(jsonInfoStr);

	   Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
	   Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
	   assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");

	   DocumentContext jsonObj = (DocumentContext) jsonObjContext;

	   jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
	   JsonDocumentReader jsonInfoReader = new JsonDocumentReader(jsonInfoStr, false);
	   Object obj = jsonInfoReader.getDocumentContext().read("$");

	   jsonObj.add(jsonPath, obj);
	   scenarioContext.addParamValue(jsonObjRefVariable, jsonObj);
	}
	
	/**
	 * Used to remove JSON info at a particular JSON path.
	 * 
	 * @param jsonObjRefVariable - the variable name that holds JSON object.
	 * @param jsonPaths - the input data table that holds jsonPath information to be removed in the format given below:
	 * 	Sample table:
	 *  | JSON Path                       |
	 *  | $.users[1]                      |
	 *  | $.departments[?(@type == 'HR')] |
	 */
	@Then("remove the following JSON paths from JSON object [JSONObjRefVariable={string}]:")
	public void remove_the_following_json_paths_from_json_object(String jsonObjRefVariable, DataTable jsonPaths) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }
	   
	   Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
	   Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
	   assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");

	   DocumentContext jsonObj = (DocumentContext) jsonObjContext;

	   List<List<String>> rows = jsonPaths.asLists();
	   
	   String jsonPath;
	   List<String> row;
	   for(int i = 1; i < rows.size(); i++) {
		   row = rows.get(i);
		   jsonPath = row.get(0);
		   jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
		   
		   jsonObj.delete(jsonPath);
	   }
	   
	   scenarioContext.addParamValue(jsonObjRefVariable, jsonObj);
	}
	
	/**
	 * Used to update JSON document on a specified path and for a specific key.
	 * 
	 * @param key - the key name for that the value to be updated (that is present on jsonPath). 
	 * @param jsonPath - the JSON path to be updated.
	 * @param jsonObjRefVariable - the variable name that holds JSON object.
	 * @param value - value to be updated for the key.
	 */
	@Then("update {string} key of {string} JSON path in JSON object [JSONObjRefVariable={string}] with the following value:")
	public void update_key_of_json_path_in_json_object_with_the_following_value(String key, String jsonPath, String jsonObjRefVariable, DocString newValue) {
	   if(!scenarioContext.isLastConditionSetToTrue()) {
	      scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
	      return;
	   }
	   
	   jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
	   key = scenarioContext.applyParamsValueOnText(key);
	   
	   Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);
	   Assert.assertNotNull(jsonObjContext, "'" +jsonObjRefVariable + "' variable does not have JSON object. Found null.");
	   assertTrue(jsonObjContext instanceof DocumentContext, "'" +jsonObjRefVariable + "' variable is not a JSON object. It should be the instance of DocumentContext class.");

	   DocumentContext jsonObj = (DocumentContext) jsonObjContext;
	   
	   
	   String value = newValue.getContent();
	   value = scenarioContext.applyParamsValueOnText(value);
	   jsonObj.put(jsonPath, key, value);
	   
	   scenarioContext.addParamValue(jsonObjRefVariable, jsonObj);	   
	}
	
	/**
	 * Used to print JSON object contents.
	 * 
	 * @param jsonObjRefVariable - the variable name that holds JSON object.
	 */
	@Then("print JSON object [JSONObjRefVariable={string}].")
	public void print_json_object(String jsonObjRefVariable) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		Object jsonObjContext = scenarioContext.getParamValue(jsonObjRefVariable);

		if (jsonObjContext == null) {
			scenarioContext.log(null);
			return;
		}
		
		DocumentContext jsonObj = (DocumentContext) jsonObjContext;
		scenarioContext.log(jsonObj.jsonString());
	}
}

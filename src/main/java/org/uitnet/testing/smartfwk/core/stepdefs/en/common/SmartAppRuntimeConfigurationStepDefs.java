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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common;

import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.core.validator.PropertyValueType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Used to define the step definitions to retrieve the runtime configuration information.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartAppRuntimeConfigurationStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartAppRuntimeConfigurationStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to print the runtime configurations of STAS tool as JSON document.
	 */
	@Then("print STAS runtime configurations as JSON Object.")
	public void print_runtime_configurations_as_json() {
		DocumentContext configCtx = TestConfigManager.getInstance().getJsonContext();
		scenarioContext.log(configCtx.jsonString());
	}
	
	/**
	 * Used to read the STAS runtime information by specifying the property path in JSON configuration.
	 * To know full STAS runtime configuration as JSON document, Please use the following step to print
	 * runtime configuration.
	 * 
	 * 	"print runtime configurations as JSON Object."
	 * 
	 * @param propertyJsonPath - the JSON path for the property to retrieve its value as specified in propertyValueType argument.
	 * 		Example-1: To retrieve property value from TestConfig.yaml file use the following JSON path:
	 * 					1. $.hostPlatformType  -> This will return hostPlatformType value.
	 * 					2. $.additionalProps.<CustomPropertyName> -> return user defined property value.
	 * 		Example-2: To retrieve application specific property that are defined in AppConfig.yaml file (May be
	 * 					overridden by the environment property):
	 * 					1. $.appConfigs.MyApp.testPlatformType  -> Returns testPlatformType of MyApp application.
	 * 					2. $.appConfigs.MyApp.appLaunchUrl  -> Returns appLaunchUrl of MyApp application.
	 * 					3. $.appConfigs.MyApp.appWebBrowser  -> Returns appWebBrowser of MyApp application.
	 * 				Note: You should specify the correct configured application name.
	 * 		Example-3: To retrieve user profiles property information for a specific application, refer the example below:
	 * 					1. $.appConfigs.MyApp.userProfilesMap.MyUserProfileName.appLoginUserId
	 * 						-> returns the appLoginUserId property value.
	 * 					2. $.appConfigs.MyApp.userProfilesMap.MyUserProfileName.additionalProps.CustomPropertyName  
	 * 						-> returns the custom property value that is defined under additionalProps property.
	 * 				Note: You should specify the correct configured application name and user profile name.
	 * 		Example-4: To retrieve API config target server property information, refer the example below:
	 * 					1. $.appConfigs.MyApp.apiConfig.targetServersMap.TargetServerName.baseURL
	 * 						-> returns the base URL of target server.
	 * 					2. $.appConfigs.MyApp.apiConfig.targetServersMap.TargetServerName.additionalProps.CustomPropertyName   
	 * 						-> returns the custom property value that is defined under additionalProps property.
	 * 				Note: You should specify the correct configured application name and target server name.
	 * @param propertyValueType - the data type format you expect to return the value.
	 * 		Supported value: string, string-list, integer, integer-list, decimal, decimal-list, boolean, boolean-list
	 * 		For more information please refer {@link PropertyValueType} 
	 * @param variableName - the name of the variable in which the value will get stored.
	 */
	@When("read {string} property path as {string} from STAS runtime configuration and store into {string} variable.")
	public void read_property_path_from_testconfig_file_and_store_into_variable(String propertyJsonPath, String propertyValueType, String variableName) {
		DocumentContext configCtx = TestConfigManager.getInstance().getJsonContext();
		
		PropertyValueType valueType = PropertyValueType.valueOf2(propertyValueType);
		Object value = null;
		switch(valueType) {
		case STRING:
			value = configCtx.read(propertyJsonPath, String.class);
			break;
		case STRING_LIST:
			TypeRef<List<String>> typeRef1 = new TypeRef<List<String>>() {
			};
			value = configCtx.read(propertyJsonPath, typeRef1);
			break;
		case INTEGER:
			value = configCtx.read(propertyJsonPath, Long.class);
			break;
		case INTEGER_LIST:
			TypeRef<List<Long>> typeRef2 = new TypeRef<List<Long>>() {
			};
			value = configCtx.read(propertyJsonPath, typeRef2);
			break;
		case DECIMAL:
			value = configCtx.read(propertyJsonPath, Double.class);
			break;
		case DECIMAL_LIST:
			TypeRef<List<Double>> typeRef3 = new TypeRef<List<Double>>() {
			};
			value = configCtx.read(propertyJsonPath, typeRef3);
			break;
		case BOOLEAN:
			value = configCtx.read(propertyJsonPath, Boolean.class);
			break;
		case BOOLEAN_LIST:
			TypeRef<List<Boolean>> typeRef4 = new TypeRef<List<Boolean>>() {
			};
			value = configCtx.read(propertyJsonPath, typeRef4);
			break;
		}
		
		scenarioContext.addParamValue(variableName, value);
	}
}

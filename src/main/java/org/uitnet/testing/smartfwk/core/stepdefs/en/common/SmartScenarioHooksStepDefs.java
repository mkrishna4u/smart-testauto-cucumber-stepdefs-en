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

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioHooksExecuter;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Given;

/**
 * Lists steps definitions related to registration of pre-scenario and post scenario methods.
 * That can be used for data initialization or data cleanup work for the scenarios.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartScenarioHooksStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartScenarioHooksStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to call scenario hook / method as pre-condition or any time within the scenario so that this method can be used to
	 * perform certain tasks at the time of call. The hook should be defined under src/main/java/scenario_hooks/ directory. 
	 * The hook path is given below.
	 * 
	 * 		<AppName>.<ClassName>.<MethodName>
	 * 
	 * @param method - the qualified name of the method. the format will be as given below:
	 * 		<AppName>.<ClassName>.<MethodName>
	 * 		NOTE: All the registration classes should be present in src/main/java/scenario_hooks/ directory.
	 * 			Method signature will be as given below:
	 * 				public static methodName(SmartCucumberScenarioContext scenarioContext, String data) {
	 * 					// code here
	 * 				}
	 * 
	 * 		Example: myApp.UserCleanup.removeUser
	 * 
	 * @param actionName - the meaningful action name.
	 * @param methodData - the data that would be required in registered data. It is going to pass as string to method.
	 * 		
	 */
	@Given("call {string} scenario hook to {string} using the data given below:")
	public void call_scenario_hook_to_using_data_given_below(String method, String actionName, DocString methodData) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String methodDataAsStr = methodData.getContent().trim();
		methodDataAsStr = scenarioContext.applyParamsValueOnText(methodDataAsStr);
		
		SmartCucumberScenarioHooksExecuter.getInstance().executeHook(scenarioContext, method, methodDataAsStr);		
	}
	
	/**
	 * Used to register the hook / method in scenario / scenario outline as post-condition so that this method can be used to
	 * perform certain tasks after the execution of the scenario. Generally method can be
	 * registered anywhere in the scenario. But it is recommended to register methods in the beginning of the 
	 * scenario / scenario outline.
	 * 
	 * @param method - the name of the method. the format will be as given below:
	 * 		<AppName>.<ClassName>.<MethodName>
	 * 		NOTE: All the registration classes should be present in src/main/java/register/ directory.
	 * 			Method signature will be as given below:
	 * 				public static methodName(SmartCucumberScenarioContext scenarioContext, String data) {
	 * 					// code here
	 * 				}
	 * 
	 * 		Example: myApp.UserCleanup.removeUser
	 * 
	 * @param actionName - the meaningful action name.
	 * @param methodData - the data that would be required in registered data. It is going to pass as string to method.
	 * 		The registered method should use this data to perform the action.
	 */
	@Given("register {string} after scenario hook to {string} using the data given below:")
	public void register_post_scenario_hook_using_data(String method, String actionName, DocString methodData) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String methodDataAsStr = methodData.getContent().trim();
		methodDataAsStr = scenarioContext.applyParamsValueOnText(methodDataAsStr);
		
		scenarioContext.registerAfterScenarioHook(method, methodDataAsStr);		
	}
}

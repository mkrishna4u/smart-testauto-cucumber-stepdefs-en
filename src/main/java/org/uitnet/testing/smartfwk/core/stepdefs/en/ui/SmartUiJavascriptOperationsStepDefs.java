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
package org.uitnet.testing.smartfwk.core.stepdefs.en.ui;

import org.openqa.selenium.WebElement;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.en.When;


/**
 * Used to perform javascript based operations on web page elements like click(), focus(), scroll(...) etc.
 * The steps listed in this class are only applicable for Web User interface that runs on web browser.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiJavascriptOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiJavascriptOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to set javascript parameter value to specific value for page element / page object.
	 * This will work only for web application that runs on Web Browser.
	 * 
	 * @param paramName - name of the parameter name of the element that is going to get identified using javascript mechanism.
	 * @param paramValue - parameter value in string. If value is string then enclose in single quote (') else keep as as without single quote.
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param actionName - meaningful action name.
	 */
	@When("set {string} javascript parameter value to {string} for {string} page element to {string}.")
	public void set_javascript_parameter_value_to_page_element_to(String paramName, String paramValue, String po, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		paramName = scenarioContext.applyParamsValueOnText(paramName);
		paramValue = scenarioContext.applyParamsValueOnText(paramValue);
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
		
		PageObjectUtil.setJavascriptParamValueOfElement(scenarioContext.getActiveAppDriver(), 
				elem, paramName, paramValue);
	}
	
	/**
	 * Used to execute javascript function of the specified page element / page object.
	 * This will work only for web application that runs on Web Browser.
	 * 
	 * @param functionWithArgs - the function name along with argument details like:
	 * 		- scroll({top: 100, left: 100, behavior: 'smooth'})
	 *      - focus()
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param actionName - meaningful action name.
	 */
	@When("execute {string} javascript function of {string} page element to {string}.")
	public void execute_javascript_function_of_page_element_to(String functionWithArgs, String po, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		functionWithArgs = scenarioContext.applyParamsValueOnText(functionWithArgs);
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
		
		PageObjectUtil.executeJavascriptFunctionOfElement(scenarioContext.getActiveAppDriver(), 
				elem, functionWithArgs);
	}
	
	/**
	 * Used to execute javascript function of the specified page element / page object and stores the returned 
	 * output of the method into a specified variable.
	 * This will work only for web application that runs on Web Browser.
	 * 
	 * @param functionWithArgs - the function name along with argument details like:
	 * 		- scroll({top: 100, left: 100, behavior: 'smooth'})
	 *      - focus()
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param actionName - meaningful action name.
	 * @param variableName - the name of the variable where the return of the method is going to get stored.
	 */
	@When("execute {string} javascript function of {string} page element to {string} and store returned value into {string} variable.")
	public void execute_javascript_function_of_page_element_to(String functionWithArgs, String po, String actionName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		functionWithArgs = scenarioContext.applyParamsValueOnText(functionWithArgs);
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
		
		Object obj = PageObjectUtil.executeJavascriptFunctionOfElement(scenarioContext.getActiveAppDriver(), 
				elem, functionWithArgs);
		
		scenarioContext.addParamValue(variableName, obj);
	}
}

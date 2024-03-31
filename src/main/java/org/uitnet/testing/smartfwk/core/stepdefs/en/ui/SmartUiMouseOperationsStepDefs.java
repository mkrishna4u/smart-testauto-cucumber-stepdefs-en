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
package org.uitnet.testing.smartfwk.core.stepdefs.en.ui;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.api.core.support.ScrollbarType;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.PageScrollUtil;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;

/**
 * Lists step definitions related to mouse operations like click, double click, click and hold, release etc.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiMouseOperationsStepDefs {	
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiMouseOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to perform click operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "click on {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("click on {string} page object to {string}.")
	public void click_on_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("click", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Used to perform click operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("click on {string} page element to {string}.")
	public void click_on_page_element_1(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform click operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("click {string} page element to {string}.")
	public void click_on_page_element_2(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform click operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "click {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("click {string} page object to {string}.")
	public void click_on_page_element_3(String pageObject, String actionName) {
		click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform force click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "force click on {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("force click on {string} page object to {string}.")
	public void force_click_on_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("forceClick", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Used to perform force click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("force click on {string} page element to {string}.")
	public void force_click_on_page_element_1(String pageObject, String actionName) {
		force_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform force click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("force click {string} page element to {string}.")
	public void force_click_on_page_element_2(String pageObject, String actionName) {
		force_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform force click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "force click {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("force click {string} page object to {string}.")
	public void force_click_on_page_element_3(String pageObject, String actionName) {
		force_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform click operation on the specified page objects / page elements.
	 * 
	 * @param actionName - meaningful expected action name.
	 * @param pageObjects - a document string contains multiple page objects in an JSON array in the format given below:
	 * 
	 * 	['{name: 'myapp.XyzPO.poObject', maxTimeToWaitInSeconds: 6}', 'myapp.XyzPO.poObject1', 'myapp.XyzPO.poObject2']
	 * 
	 *   Each page object / page element can be specified in two way:
	 *   <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 *   </pre></blockquote>      
	 * 
	 */
	@When("click on the following page elements to {string}:")
	public void click_on_the_following_page_elements(String actionName, DocString pageObjects) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String pageObjectsAsStr = pageObjects.getContent();
		JsonDocumentReader jsonReader = new JsonDocumentReader(pageObjectsAsStr, true);
		List<String> poList = jsonReader.readValuesAsList("$");
		
		for(String po : poList) {
			click_on_page_element(po, actionName);
		}
	}
	
	/**
	 * Used to perform click operation on the specified page objects / page elements.
	 * 
	 * @param actionName - meaningful expected action name.
	 * @param pageObjects - a document string contains multiple page objects in an JSON array in the format given below:
	 * 
	 * 	['{name: 'myapp.XyzPO.poObject', maxTimeToWaitInSeconds: 6}', 'myapp.XyzPO.poObject1', 'myapp.XyzPO.poObject2']
	 * 
	 *   Each page object / page element can be specified in two way:
	 *   <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 *   </pre></blockquote>      
	 * 
	 * 
	 * @deprecated use "click on the following page elements to {string}:" test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("click on the following page objects to {string}:")
	public void click_on_the_following_page_objects(String actionName, DocString pageObjects) {
		click_on_the_following_page_elements(actionName, pageObjects);
	}
	
	/**
	 * Used to perform double click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "double click on {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("double click on {string} page object to {string}.")
	public void double_click_on_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("doubleClick", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Used to perform double click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("double click on {string} page element to {string}.")
	public void double_click_on_page_element_1(String pageObject, String actionName) {
		double_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform double click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("double click {string} page element to {string}.")
	public void double_click_on_page_element_2(String pageObject, String actionName) {
		double_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform double click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "double click {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("double click {string} page object to {string}.")
	public void double_click_on_page_element_3(String pageObject, String actionName) {
		double_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform right click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "right click on {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("right click on {string} page object to {string}.")
	public void right_click_on_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("rightClick", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Used to perform right click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("right click on {string} page element to {string}.")
	public void right_click_on_page_element_1(String pageObject, String actionName) {
		right_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform right click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("right click {string} page element to {string}.")
	public void right_click_on_page_element_2(String pageObject, String actionName) {
		right_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform right click operation on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "right click {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("right click {string} page object to {string}.")
	public void right_click_on_page_element_3(String pageObject, String actionName) {
		right_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform click and hold operation on the specified page object / page element.
	 * Generally it is used for drag and drop functionality.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "click and hold on {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("click and hold on {string} page object to {string}.")
	public void click_and_hold_on_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("clickAndHold", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Used to perform click and hold operation on the specified page object / page element.
	 * Generally it is used for drag and drop functionality.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("click and hold on {string} page element to {string}.")
	public void click_and_hold_on_page_element_1(String pageObject, String actionName) {
		click_and_hold_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform click and hold operation on the specified page object / page element.
	 * Generally it is used for drag and drop functionality.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("click and hold {string} page element to {string}.")
	public void click_and_hold_on_page_element_2(String pageObject, String actionName) {
		click_and_hold_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform click and hold operation on the specified page object / page element.
	 * Generally it is used for drag and drop functionality.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "click and hold {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("click and hold {string} page object to {string}.")
	public void click_and_hold_on_page_element_3(String pageObject, String actionName) {
		click_and_hold_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform mouse release operation on the specified page object / page element.
	 * Generally it is used for drag and drop functionality.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "release hold click from {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("release hold click from {string} page object to {string}.")
	public void release_hold_click_from_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("release", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	/**
	 * Used to perform mouse release operation on the specified page object / page element.
	 * Generally it is used for drag and drop functionality.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("release hold click from {string} page element to {string}.")
	public void release_hold_click_from_page_element_1(String pageObject, String actionName) {
		release_hold_click_from_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform drag and drop operations on the specified page object / page element.
	 *
	 * @param dragElemPageObject - the dragging element page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param dropElemPageObject - the dropping element page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 */
	@When("drag {string} page element and drop on {string} page element.")
	public void drag_and_drop_page_element(String dragElemPageObject, String dropElemPageObject) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		click_and_hold_on_page_element(dragElemPageObject, "");
		
		PageObjectInfo sourcePoInfo = PageObjectUtil.getPageObjectInfo(dragElemPageObject);
		PageObjectInfo targetPoInfo = PageObjectUtil.getPageObjectInfo(dropElemPageObject);
		
		WebElement sourceElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { sourcePoInfo.getMaxIterationsToLocateElements() }, sourcePoInfo,
				scenarioContext);
		
		WebElement targetElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { targetPoInfo.getMaxIterationsToLocateElements() }, targetPoInfo,
				scenarioContext);
		
		PageScrollUtil.mouseDragAndDrop(scenarioContext.getActiveAppDriver(), sourceElem, targetElem);
	}
	
	/**
	 * Used to perform mouse hoverover operations on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "mouse hoverover on {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("mouse hoverover on {string} page object to {string}.")
	public void mouse_hoverover_on_page_element(String pageObject, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("mouseHoverOver", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
	}
	
	/**
	 * Used to perform mouse hoverover operations on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("mouse hoverover on {string} page element to {string}.")
	public void mouse_hoverover_on_page_element_1(String pageObject, String actionName) {
		mouse_hoverover_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform mouse hoverover operations on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 */
	@When("mouse hoverover {string} page element to {string}.")
	public void mouse_hoverover_on_page_element_2(String pageObject, String actionName) {
		mouse_hoverover_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to perform mouse hoverover operations on the specified page object / page element.
	 *
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "mouse hoverover {string} page element to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("mouse hoverover {string} page object to {string}.")
	public void mouse_hoverover_on_page_element_3(String pageObject, String actionName) {
		mouse_hoverover_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Used to set the horizontal scrollbar thumb position to N pixels right from the left most location.
	 * 
	 * @param distanceInPixels - distance in pixel where the scrolling pointer is set. distance should be always positive number
	 * 		and relative to the scrollbar start location. 
	 * 
	 * @param scrollbarPageObject- the scrollbar page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * 
	 */
	@When("set horizontal scrollbar thumb position {int} pixels away from left [ScrollbarPageElement={string}].")
	public void set_horizontal_scrollbar_thumb_position_pixel_away_from_left(int distanceInPixels, String scrollbarPageObject) {
		PageObjectInfo sourcePoInfo = PageObjectUtil.getPageObjectInfo(scrollbarPageObject);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { sourcePoInfo.getMaxIterationsToLocateElements() }, sourcePoInfo,
				scenarioContext);
		
		PageScrollUtil.setScrollbarThumbgripLocation(scenarioContext.getActiveAppDriver(), elem, ScrollbarType.HORIZONTAL, distanceInPixels);
	}
	
	/**
	 * Used to set the horizontal scrollbar thumb position to N percent right from the left most location. Valid values are 0.0 to 100.0.
	 * 
	 * @param distanceInPercentage - distance in percentage where the scrolling pointer is set. distance should be always positive number
	 * 		and relative to the scrollbar start location. valid values are 0.0 to 100.0
	 * 
	 * @param scrollbarPageObject- the scrollbar page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * 
	 */
	@When("set horizontal scrollbar thumb position {double} percent away from left [ScrollbarPageElement={string}].")
	public void set_horizontal_scrollbar_thumb_position_percent_away_from_left(double distanceInPercentage, String scrollbarPageObject) {
		PageObjectInfo sourcePoInfo = PageObjectUtil.getPageObjectInfo(scrollbarPageObject);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { sourcePoInfo.getMaxIterationsToLocateElements() }, sourcePoInfo,
				scenarioContext);
		
		PageScrollUtil.setScrollbarThumbgripLocation(scenarioContext.getActiveAppDriver(), elem, ScrollbarType.HORIZONTAL, distanceInPercentage);
	}
	
	/**
	 * Used to set the vertical scrollbar thumb position to N pixels down from the top most location.
	 * 
	 * @param distanceInPixels - distance in pixel where the scrolling pointer is set. distance should be always positive number
	 * 		and relative to the scrollbar start location. 
	 * 
	 * @param scrollbarPageObject- the scrollbar page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * 
	 */
	@When("set vertical scrollbar thumb position {int} pixels away from top [ScrollbarPageElement={string}].")
	public void set_vertical_scrollbar_thumb_position_pixel_away_from_top(int distanceInPixels, String scrollbarPageObject) {
		PageObjectInfo sourcePoInfo = PageObjectUtil.getPageObjectInfo(scrollbarPageObject);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { sourcePoInfo.getMaxIterationsToLocateElements() }, sourcePoInfo,
				scenarioContext);
		
		PageScrollUtil.setScrollbarThumbgripLocation(scenarioContext.getActiveAppDriver(), elem, ScrollbarType.VERTICAL, distanceInPixels);
	}
	
	/**
	 * Used to set the vertical scrollbar thumb position to N percent down from the top most location. Valid values are 0.0 to 100.0.
	 * 
	 * @param distanceInPercentage - distance in percentage where the scrolling pointer is set. distance should be always positive number
	 * 		and relative to the scrollbar start location. valid values are 0.0 to 100.0
	 * 
	 * @param scrollbarPageObject- the scrollbar page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * 
	 */
	@When("set vertical scrollbar thumb position {double} percent away from top [ScrollbarPageElement={string}].")
	public void set_vertical_scrollbar_thumb_position_percent_away_from_top(double distanceInPercentage, String scrollbarPageObject) {
		PageObjectInfo sourcePoInfo = PageObjectUtil.getPageObjectInfo(scrollbarPageObject);
		
		WebElement elem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] { sourcePoInfo.getMaxIterationsToLocateElements() }, sourcePoInfo,
				scenarioContext);
		
		PageScrollUtil.setScrollbarThumbgripLocation(scenarioContext.getActiveAppDriver(), elem, ScrollbarType.VERTICAL, distanceInPercentage);
	}

}

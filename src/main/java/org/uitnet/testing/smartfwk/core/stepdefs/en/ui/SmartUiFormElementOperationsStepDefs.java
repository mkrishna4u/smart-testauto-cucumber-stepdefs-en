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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.common.MethodSignature;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.InputValue;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.WebElementUtil;
import org.uitnet.testing.smartfwk.validator.FieldValidator;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * Lists step definitions for UI / Web Page. Like enter information on UI page and submit that information.
 * Or extract and validate the UI control information.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiFormElementOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiFormElementOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to verify the web page title value.
	 * 
	 * @param titleName - the expected title name.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 * @param maxTimeTowaitInSeconds - the max time to wait in seconds to locate the element.
	 */
	@Then("verify the web page title is {string} with [TextMatchMechanism={string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_the_web_page_title_is_with(String titleName, String textMatchMechanism, Integer maxTimeTowaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String text = scenarioContext.getActiveAppDriver().getWebDriver().getTitle();
		
		TextMatchMechanism matchMechanism = TextMatchMechanism.valueOf2(textMatchMechanism);
		FieldValidator.validateFieldValueAsExpectedValue("Web page title", text, titleName, matchMechanism);
	}
	
	/**
	 * Used to verify the current active URL value.
	 * 
	 * @param url - the expected URL.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 * @param maxTimeTowaitInSeconds - the max time to wait in seconds to locate the element.
	 */
	@Then("verify the web page current URL is {string} with [TextMatchMechanism={string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_the_web_page_current_URL_is_with(String titleName, String textMatchMechanism, Integer maxTimeTowaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String text = scenarioContext.getActiveAppDriver().getWebDriver().getCurrentUrl();
		
		TextMatchMechanism matchMechanism = TextMatchMechanism.valueOf2(textMatchMechanism);
		FieldValidator.validateFieldValueAsExpectedValue("Web page current URL", text, titleName, matchMechanism);
	}
	
	/**
	 * Used to verify the focused element information by verifying one of its attribute value.
	 * 
	 * @param elementName - the name of the expected element.
	 * @param attributeName - the name of the attribute for the expected element.
	 * @param attributeValue - the value of the attribute specified in argument attributeName.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 * @param maxTimeTowaitInSeconds - the max time to wait in seconds to locate the element.
	 */
	@Then("verify the focused element has the following information [ElementName={string}, AttributeName={string}, AttributeValue={string}] with [TextMatchMechanism={string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_the_focused_element_has_the_following_information(String elementName, String attributeName, String attributeValue, String textMatchMechanism, int maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		int maxIterationsToLocateElements = maxTimeToWaitInSeconds <= 4 ? 2 : maxTimeToWaitInSeconds / 2;
		String actualElemName = "";
		String actualTextValue = "";
		try {
			SmartAppDriver appDriver = scenarioContext.getActiveAppDriver();
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement elem = appDriver.getWebDriver().switchTo().activeElement();
					if(elem == null) {
						Assert.fail("Focused/active element is not found.");
					}
					
					actualElemName = elem.getTagName();
					FieldValidator.validateFieldValueAsExpectedValue("Focused/active element name", actualElemName, elementName, TextMatchMechanism.exactMatchWithExpectedValue);
					
					actualTextValue = elem.getAttribute(attributeName);
					TextMatchMechanism matchMechanism = TextMatchMechanism.valueOf2(textMatchMechanism);
					FieldValidator.validateFieldValueAsExpectedValue("Focused/active element attribute value", actualTextValue, attributeValue, matchMechanism);
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to verify focused element (Name: " + actualElemName + ") attribute value[" +attributeName +"=" + attributeValue + "]. Actual value: " + actualTextValue + ".", th);
		}
		
	}
	
	/**
	 * Used to verify the focused element information by verifying the element text value.
	 * 
	 * @param elementName - the name of the expected element.
	 * @param textValue - the text part value of the element.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 * @param maxTimeTowaitInSeconds - the max time to wait in seconds to locate the element.
	 */
	@Then("verify the focused element has the following information [ElementName={string}, TextValue={string}] with [TextMatchMechanism={string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_the_focused_element_has_the_following_information(String elementName, String textValue, String textMatchMechanism, int maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		int maxIterationsToLocateElements = maxTimeToWaitInSeconds <= 4 ? 2 : maxTimeToWaitInSeconds / 2;
		String actualElemName = "";
		String actualTextValue = "";
		try {
			SmartAppDriver appDriver = scenarioContext.getActiveAppDriver();
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement elem = appDriver.getWebDriver().switchTo().activeElement();
					if(elem == null) {
						Assert.fail("Focused/active element is not found.");
					}
					
					actualElemName = elem.getTagName();
					FieldValidator.validateFieldValueAsExpectedValue("Focused/active element name", actualElemName, elementName, TextMatchMechanism.exactMatchWithExpectedValue);
					
					actualTextValue = elem.getText();
					TextMatchMechanism matchMechanism = TextMatchMechanism.valueOf2(textMatchMechanism);
					FieldValidator.validateFieldValueAsExpectedValue("Focused/active element text value", actualTextValue, textValue, matchMechanism);
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to verify focused element (Name: " + actualElemName + ") text value=" + textValue + ". Actual value: " + actualTextValue + ".", th);
		}
		
	}
	
	/**
	 * Used to verify that the specified page elements are visible on the UI page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * <blockquote><pre>
	 *       | Page Element                                                               | 
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPage.pageElement                                                  |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 * </pre></blockquote>
	 */
	@Then("verify that the following page elements are visible on {string}:")
	public void verify_that_the_following_page_elements_are_visible_1(String pageOrScreenName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateVisible", new String[] { Integer.TYPE.getTypeName()},
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
		}
	}

	/**
	 * Used to verify that the specified page element is visible on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>   
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify {string} page element is visible on {string}.")
	public void verify_that_the_page_element_is_visible(String pe, String pageOrScreenName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateVisible", new String[] { Integer.TYPE.getTypeName() },
				new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
				scenarioContext);
	}
	
	/**
	 * Used to verify that the specified page element is visible on the screen.
	 * 
	 * @param pe - Page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>   
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page element is visible on {string}.")
	public void verify_that_the_page_element_is_visible_3(String pe, String pageOrScreenName) {
		verify_that_the_page_element_is_visible(pe, pageOrScreenName);
	}
	
	/**
	 * Used to verify that the specified page elements are hidden on the page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * <blockquote><pre>
	 *       | Page Element                                                                |
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}}  | 
	 *       | myapp.XyzPage.pageElement                                                   |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 * </pre></blockquote>
	 */
	@Then("verify that the following page elements are hidden on {string}:")
	public void verify_that_the_following_page_elements_are_hidden(String pageOrScreenName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateHidden", new String[] { Integer.TYPE.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
		}
	}
		
	/**
	 * Used to verify that the specified page elements are not visible / hidden on the page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * <blockquote><pre>
	 *       | Page Element                                                                |
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}}  | 
	 *       | myapp.XyzPage.pageElement                                                   |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 * </pre></blockquote>
	 */
	@Then("verify that the following page elements are not visible on {string}:")
	public void verify_that_the_following_page_elements_are_hidden_3(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_hidden(pageOrScreenName, dataTable);
	}
	
	/**
	 * Retrieves the visibility of the specified page element and store yes / no value into variable based on element visibility.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param variableName - name of the variable where system will store the visibility. For visible element it will store "yes" else
	 * 			it will store "no".
	 * 
	 */
	@Then("get visibility of {string} page element and store into {string} variable.")
	public void get_visibility_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		Boolean visible = false;
		try {
			visible = (Boolean) PageObjectUtil.invokeValidatorMethod("isVisible", new String[] { Integer.TYPE.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
		} catch(Throwable th) {
			scenarioContext.log("Visibility is 'no' because: " + th.getMessage());
		}
		
		scenarioContext.addParamValue(variableName, visible ? "yes": "no");
	}
	
	/**
	 * Retrieves the presence of the specified page element and store yes / no value into variable based on element presence in DOM.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>     
	 * @param variableName - name of the variable where system will store the presence. For present element it will store "yes" else
	 * 			it will store "no".
	 */
	@Then("get presence of {string} page element and store into {string} variable.")
	public void get_presence_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		Boolean visible = false;
		try {
			visible = (Boolean) PageObjectUtil.invokeValidatorMethod("isPresent", new String[] { Integer.TYPE.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
		} catch(Throwable th) {
			scenarioContext.log("Presence is 'no' because: " + th.getMessage());
		}
		
		scenarioContext.addParamValue(variableName, visible ? "yes": "no");
	}
	
	
	/**
	 * Used to verify that the specified page element is hidden on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify {string} page element is hidden on {string}.")
	public void verify_page_element_is_hidden(String pe, String pageOrScreenName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateHidden", new String[] { Integer.TYPE.getTypeName() },
				new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
				scenarioContext);
	}
	
	/**
	 * Used to verify that the specified page element is hidden on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page element is hidden on {string}.")
	public void verify_that_the_page_element_is_hidden(String pe, String pageOrScreenName) {
		verify_page_element_is_hidden(pe, pageOrScreenName);
	}
	
	/**
	 * Used to verify that the specified page element is not visible on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify {string} page element is not visible on {string}.")
	public void verify_page_element_is_not_visible(String pe, String pageOrScreenName) {
		verify_page_element_is_hidden(pe, pageOrScreenName);
	}
		
	/**
	 * Used to verify that the specified page element is not visible on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page element is not visible on {string}.")
	public void verify_that_the_page_element_is_not_visible(String pe, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(pe, pageOrScreenName);
	}
	
	/**
	 * Used to verify that the specified page elements are disabled on the UI page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * <blockquote><pre>
	 *       | Page Element                                                               | 
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPage.pageElement                                                  |
	 *       
	 *    Where: the page element can be specified in two way:
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@Then("verify that the following page elements are disabled on {string}:")
	public void verify_that_the_following_page_elements_are_disabled(String pageOrScreenName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateDisabled", new String[] { Integer.TYPE.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
		}
	}
	
	/**
	 * Used to verify that the specified page element is disabled on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page element is disabled on {string}.")
	public void verify_that_the_page_element_is_disabled_1(String pe, String pageOrScreenName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateDisabled", new String[] { Integer.TYPE.getTypeName() },
				new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
				scenarioContext);
	}
	
	/**
	 * Used to verify that the specified page elements are enabled on the UI page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * <blockquote><pre>
	 *       | Page Element                                                               | 
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPage.pageElement                                                  |
	 *       
	 *    Where: the page element can be specified in two way:
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@Then("verify that the following page elements are enabled on {string}:")
	public void verify_that_the_following_page_elements_are_enabled_1(String pageOrScreenName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateEnabled", new String[] { Integer.TYPE.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
		}
	}

	/**
	 * Used to verify that the specified page element is enabled on the screen.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page element is enabled on {string}.")
	public void verify_that_the_page_element_is_enabled(String pe, String pageOrScreenName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateEnabled", new String[] { Integer.TYPE.getTypeName() },
				new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
				scenarioContext);
	}
	
	
	/**
	 * Used to type the text at a particular location on the editable page element like TextBox, TextArea elements.
	 * 
	 * @param inputValue - the text to be typed. This can be specified directly or in JSON format format.
	 * 		Direct way: "this is sample text"
	 *      JSON way: Refer {@link InputValue}. Example:
	 *        {value: "This is sample text", action: "type", typeSpeedMspc: 100, typeAfterClick: true}
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param location - the location where the specified text to be typed. valid values: start, end, replace
	 * 	If text is already existed in the control. 
	 * <blockquote><pre>
	 * 		A. location=start will append text in the beginning.
	 *      B. location=end will append text in the end.
	 *      C. location=replace will replace the existing text.
	 * </pre></blockquote>
	 */
	@When("type {string} text in {string} page element at {string} location.")
	public void type_text_in_page_element(String inputValue, String pe, String location) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		inputValue = scenarioContext.applyParamsValueOnText(inputValue);
		InputValue inputValueObj = JsonYamlUtil.parseInputValue(inputValue);
		PageObjectUtil.invokeValidatorMethod("typeText",
				new String[] { String.class.getTypeName(), NewTextLocation.class.getTypeName(), Integer.TYPE.getTypeName(), Boolean.TYPE.getTypeName(),  Integer.TYPE.getTypeName()},
				new Object[] { inputValueObj.getValue(), NewTextLocation.valueOf2(location), inputValueObj.getTypeSpeedMspc(), 
						inputValueObj.getTypeAfterClick(), peInfo.getMaxIterationsToLocateElements() }, peInfo,
				scenarioContext);
	}
		
	/**
	 * Used to type the text on the editable page element like TextBox, TextArea elements.
	 * This step will always replace the existing text with the new text.
	 * 
	 * @param inputValue - the text to be typed. This can be specified directly or in JSON format format.
	 * 		Direct way: "this is sample text"
	 *      JSON way: Refer {@link InputValue}. Example:
	 *        {value: "This is sample text", action: "type", typeSpeedMspc: 100, typeAfterClick: true }
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("type {string} text in {string} page element.")
	public void type_text_in_page_element_1(String inputValue, String pe) {
		type_text_in_page_element(inputValue, pe, "replace");
	}
	
	/**
	 * Used to type the text on the editable page element like TextBox, TextArea elements.
	 * This step will always replace the existing text with the new text.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param inputValue - the text to be typed. This can be specified directly or in JSON format format.
	 * 		Direct way: This is sample text
	 *      JSON way: Refer {@link InputValue}. Example:
	 *        {value: "This is sample text", action: "type", typeSpeedMspc: 100, typeAfterClick: true }
	 *
	 */
	@When("type the following text in {string} page element:")
	public void type_the_following_text_in_page_element_1(String pe, DocString textToType) {
		type_text_in_page_element_1(textToType.getContent(), pe);
	}
	
	/**
	 * Used to verify the text part of page element that matches with the specified text using specified TextMatchMechanism.
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 *  </pre></blockquote>     
	 * @param expectedText - the expected text that need to be matched.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@Then("verify that the text part of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_that_the_text_part_of_page_element_matches_text_where_textmatchmechanism_1(String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement elements = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				FieldValidator.validateFieldValueAsExpectedValue(peInfo.getPageObject().getName() + "->text", elements.getText(), expectedText,
						TextMatchMechanism.valueOf2(textMatchMechanism));
				break;
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	/**
	 * Used to verify the attribute value of page element that matches with the specified text using specified TextMatchMechanism.
	 * In HTML DOM the attribute and its value is specified inside start tag.
	 * <tag-name attr1="attr1 value" attr2="attr2 value">text-part</tag-name>  
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param expectedText - the expected text that need to be matched.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@Then("verify that {string} attribute value of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_that_attribute_value_of_page_element_matches_text_where_textmatchmechanism(String attributeName, String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement elements = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				FieldValidator.validateFieldValueAsExpectedValue(peInfo.getPageObject().getName() + "->" + attributeName, elements.getAttribute(attributeName), expectedText,
						TextMatchMechanism.valueOf2(textMatchMechanism));
				break;
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	/**
	 * Used to verify the value of page element that matches with the specified text using specified TextMatchMechanism.
	 * <blockquote><pre>
	 * In HTML DOM <input> tag is the one that generally has the value attribute (mostly hidden).
	 * <input name="username"/>
	 * <input type="text" name="username"/>
	 * For the above DOM element we can get the value using this step. Generally it is called TextBox on UI element.
	 * </pre></blockquote>
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param expectedText - the expected text that need to be matched.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@Then("verify that the value of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_value_of_page_element_matches_text_where_textmatchmechanism(String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		PageObjectUtil.invokeValidatorMethod(
				"validateValue", new String[]{String.class.getTypeName(), TextMatchMechanism.class.getTypeName(), int.class.getTypeName()}, new Object[]{expectedText, textMatchMechanism, 1}, peInfo, scenarioContext);
	}
		
	/**
	 * Used to retrieve the value of the input text specified by given page element and store the value into the variable.
	 *  
	 * @param pe- the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param variableName - the name of the variable.
	 */
	@Then("get input text value of {string} page element and store into {string} variable.")
	public void get_input_text_value_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
				"findElement", new String[]{int.class.getTypeName()}, new Object[]{peInfo.getMaxIterationsToLocateElements()}, peInfo, scenarioContext);
		String textValue = WebElementUtil.getInputTextValue(scenarioContext.getActiveAppDriver(), element);
		
		scenarioContext.addParamValue(variableName, textValue);
	}
	
	/**
	 * Used to retrieve the value of each input text element specified by given page element and store all the values into the variable.
	 *  
	 * @param pe- the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param variableName - the name of the variable.
	 */
	@SuppressWarnings("unchecked")
	@Then("get input text value of each element of {string} page element and store into {string} variable.")
	public void get_input_text_value_of_each_element_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				list.clear();				
				if(elements != null) {					
					for(WebElement elem : elements) {
						String textValue = WebElementUtil.getInputTextValue(scenarioContext.getActiveAppDriver(), elem);
						list.add(textValue);
					}
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, list);
	}
	
	/**
	 * Used to retrieve the text part of each element specified by given page element and store all the values into the variable.
	 * <blockquote><pre>
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * </pre></blockquote>
	 * 
	 * @param pe- the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param variableName - the name of the variable.
	 */
	@SuppressWarnings("unchecked")
	@Then("get text part of each element of {string} page element and store into {string} variable.")
	public void verify_text_part_of_each_element_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				list.clear();
				if(elements != null) {					
					for(WebElement elem : elements) {
						String textValue = elem.getText();
						list.add(textValue);
					}
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					break;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, list);
	}
	
	/**
	 * Used to retrieve the text part of specified page element and store the value into the variable.
	 * <blockquote><pre>
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * </pre></blockquote>
	 * 
	 * @param pe- the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param variableName - the name of the variable.
	 */
	@Then("get text part of {string} page element and store into {string} variable.")
	public void verify_text_part_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		String textValue = "";
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(element != null) {					
					textValue = element.getText();
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, textValue);
	}
	
	/**
	 * Used to retrieve the given attribute's value of each element specified by given page element and store all the values into the variable.
	 * <blockquote><pre>
	 * In HTML DOM the attribute and its value is specified inside start tag.
	 * <tag-name attr1="attr1 value" attr2="attr2 value">text-part</tag-name>  
	 * Here attr1 and attr2 are attribute names.
	 * </pre></blockquote>
	 * 
	 * @param attributeName - the attribute name.
	 * @param pe- the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param variableName - the name of the variable that will store the .
	 */
	@SuppressWarnings("unchecked")
	@Then("get {string} attribute value of each element of {string} page element and store into {string} variable.")
	public void get_attribute_value_of_each_element_of_page_element_and_store_into_variable(String attributeName, String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				list.clear();
				if(elements != null) {	
					for(WebElement elem : elements) {
						String textValue = elem.getAttribute(attributeName);
						list.add(textValue);
					}
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, list);
		
	}
	
	/**
	 * Used to retrieve the given attribute's value of specified page element and store value into the variable.
	 * <blockquote><pre>
	 * In HTML DOM the attribute and its value is specified inside start tag.
	 * <tag-name attr1="attr1 value" attr2="attr2 value">text-part</tag-name>  
	 * Here attr1 and attr2 are attribute names.
	 * </pre></blockquote>
	 * 
	 * @param attributeName - the attribute name.
	 * @param pe- the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param variableName - the name of the variable that will store the .
	 */
	@Then("get {string} attribute value of {string} page element and store into {string} variable.")
	public void get_attribute_value_of_page_element_and_store_into_variable(String attributeName, String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		String textValue = "";
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(element != null) {	
					textValue = element.getAttribute(attributeName);
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, textValue);
		
	}
	
	/**
	 * Used to verify the text part of each element specified by given page element matches with 
	 * the expected text using specified TextMatchMechanism.
	 * 
	 * <blockquote><pre>
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * </pre></blockquote>
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 *       
	 * @param expectedText - the expected text to be matched with each element's text part.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@SuppressWarnings("unchecked")
	@Then("verify text part of each element of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_text_part_of_each_element_of_page_element_matches_text_where_textmatchmechanism(String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(elements != null) {					
					for(WebElement elem : elements) {
						String textValue = elem.getText();
						FieldValidator.validateFieldValueAsExpectedValue(peInfo.getPageObject().getName() + "->Text", textValue, expectedText,
								TextMatchMechanism.valueOf2(textMatchMechanism));
					}
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	/**
	 * Used to verify the text part of specified page element matches with 
	 * the expected text using specified TextMatchMechanism.
	 * 
	 * <blockquote><pre>
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * </pre></blockquote>
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 *       
	 * @param expectedText - the expected text to be matched with each element's text part.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@Then("verify text part of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_text_part_of_page_element_matches_text_where_textmatchmechanism(String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(element != null) {					
					String textValue = element.getText();
					FieldValidator.validateFieldValueAsExpectedValue(peInfo.getPageObject().getName() + "->Text", textValue, expectedText,
							TextMatchMechanism.valueOf2(textMatchMechanism));
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	/**
	 * Used to verify the text part of the page element with the expected information using specified operator.
	 * 
	 * <blockquote><pre>
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * </pre></blockquote>
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param expectedInfo - the expected info. The syntax is a JSON syntax:
	 * 		{ev: <value-here>, valueType: "string", textMatchMechanism: "start-with-expected-value", n: 2, inOrder: "no", ignoreCase: "no"}
	 *    For expected info, refer {@link ExpectedInfo}
	 *    For valueType, refer {@link ParamValueType}
	 *    For textMatchMechanism, refer {@link TextMatchMechanism}
	 *    Or we can directly specify value like:
	 *    	"test value"
	 */
	@Then("verify text part of {string} page element {string} {string}.")
	public void verify_text_part_of_page_element(String pe, String operator, String expectedInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(element != null) {
					String textValue = element.getText();
					ExpectedInfo eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
					
					ParamPath pPath = new ParamPath(peInfo.getPageObject().getName() + "-Text", "string");
					
					ParameterValidator.validateParamValueAsExpectedInfo(true, pPath, textValue, operator, eInfo);
					
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}
	}
	
	/**
	 * Used to verify the text part of each element specified by page element with the expected information using specified operator.
	 * 
	 * <blockquote><pre>
	 * In HTML DOM the text part is the value between start tag and end tag.
	 * <tag-name>text-part</tag-name>  
	 * </pre></blockquote>
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param expectedInfo - the expected info. The syntax is a JSON syntax:
	 * 		{ev: <value-here>, valueType: "string", textMatchMechanism: "start-with-expected-value", n: 2, inOrder: "no", ignoreCase: "no"}
	 *    For expected info, refer {@link ExpectedInfo}
	 *    For valueType, refer {@link ParamValueType}
	 *    For textMatchMechanism, refer {@link TextMatchMechanism}
	 *    Or we can directly specify value like:
	 *    	"test value"
	 */
	@SuppressWarnings("unchecked")
	@Then("verify text part of each element of {string} page element {string} {string}.")
	public void verify_text_part_of_each_element_of_page_element(String pe, String operator, String expectedInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(elements != null) {	
					List<String> elems = new LinkedList<>();
					for(WebElement elem : elements) {
						String textValue = elem.getText();
						elems.add(textValue);
					}
					
					ExpectedInfo eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
					
					ParamPath pPath = new ParamPath(peInfo.getPageObject().getName() + "-Text", "string-list");
					
					ParameterValidator.validateParamValueAsExpectedInfo(true, pPath, elems, operator, eInfo);
					
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}
	}
		
	/**
	 * Used to verify the attribute value of each element specified by given page element matches with 
	 * the expected text using specified TextMatchMechanism.
	 * 
	 * <blockquote><pre>
	 * In HTML DOM the attribute and its value is specified inside start tag.
	 * <tag-name attr1="attr1 value" attr2="attr2 value">text-part</tag-name>  
	 * Here attr1 and attr2 are attribute names.
	 * </pre></blockquote>
	 * 
	 * @param attributeName - the name of the attribute.
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>    Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 *       
	 * @param expectedText - the expected text to be matched with each element's text part.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@SuppressWarnings("unchecked")
	@Then("verify {string} attribute value of each element of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_attribute_value_of_each_element_of_page_element_matches_text_where_textmatchmechanism(String attributeName, String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(elements != null) {	
					for(WebElement elem : elements) {
						String textValue = elem.getAttribute(attributeName);
						FieldValidator.validateFieldValueAsExpectedValue(peInfo.getPageObject().getName() + "->" + attributeName, textValue, expectedText,
								TextMatchMechanism.valueOf2(textMatchMechanism));
					}
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
	}
	
	/**
	 * Used to verify the attribute value of specified page element matches with 
	 * the expected text using specified TextMatchMechanism.
	 * 
	 * <blockquote><pre>
	 * In HTML DOM the attribute and its value is specified inside start tag.
	 * <tag-name attr1="attr1 value" attr2="attr2 value">text-part</tag-name>  
	 * Here attr1 and attr2 are attribute names.
	 * </pre></blockquote>
	 * 
	 * @param attributeName - the name of the attribute.
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>    Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 *       
	 * @param expectedText - the expected text to be matched with each element's text part.
	 * @param textMatchMechanism - the text match mechanism used to verify the actual value with expected value. 
	 * <blockquote><pre>
	 * 		For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
	 */
	@Then("verify {string} attribute value of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_attribute_value_of_page_element_matches_text_where_textmatchmechanism(String attributeName, String pe, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				
				if(element != null) {	
					String textValue = element.getAttribute(attributeName);
					FieldValidator.validateFieldValueAsExpectedValue(peInfo.getPageObject().getName() + "->" + attributeName, textValue, expectedText,
							TextMatchMechanism.valueOf2(textMatchMechanism));
					break;
				}
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
	}
	
	/**
	 * Used to call validator method of page element to perform a particular operation.
	 * 
	 * @param methodAsJson - Method signature example:
	 * <blockquote><pre>
	 * 		{name: "methodName", argsType: [int, String], argsValue: [15, "test string"]}
	 *      
	 *      We can call any method of validator class that is associated with the specified page element (pe).
	 * </pre></blockquote>      
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param operation - meaningful name of the operation.
	 */
	@When("call {string} validator method of {string} page element to {string}.")
	public void call_validator_method_of_page_object_to_1(String methodAsJson, String pe, String operation) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(methodAsJson, false);
		MethodSignature method = jsonReader.readValueAsObject("$", MethodSignature.class);
		
		PageObjectUtil.invokeValidatorMethod(method.getName(), method.getArgsType(),
				method.getArgsValue(), peInfo, scenarioContext);
	}
	
	/**
	 * Used to call validator method of page element to perform a particular operation and store the the output (return value) into the variable..
	 * 
	 * @param methodAsJson - Method signature example:
	 * <blockquote><pre>
	 * 		{name: "methodName", argsType: [int, String], argsValue: [15, "test string"]}
	 *      
	 *      We can call any method of validator class that is associated with the specified page element (pe).
	 * </pre></blockquote>      
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param operation - meaningful name of the operation.
	 * @param variableName - the variable name where the return info of validator method is saved.
	 */
	@When("call {string} validator method of {string} page element to {string} and store output to {string} variable.")
	public void call_validator_method_of_page_element_to_and_store_output_to_variable(String methodAsJson, String pe, String operation, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(methodAsJson, false);
		MethodSignature method = jsonReader.readValueAsObject("$", MethodSignature.class);
		
		Object output = PageObjectUtil.invokeValidatorMethod(method.getName(), method.getArgsType(),
				method.getArgsValue(), peInfo, scenarioContext);
		
		scenarioContext.addParamValue(variableName, output);
	}
		
	/**
	 * Used to validate the page element information when the screen is launched first time or after making the some changes.
	 * We can specify multiple elements along with expected information into datatable to verify UI element's value.
	 * 
	 * @param pageOrScreenName - the meaningful name of the screen.
	 * @param dataTable - the datatable that contains the page element along with expected information in the format given below:
	 * <blockquote><pre>
	 * 		| Page Element                                 | Operator      | Expected Information           |
	 *      | {name: "myapp.SamplePage.TextBox_UserName"}  | contains      | David                          |
	 *      | myapp.SamplePage.Textbox_Passsword           | =             | {ev: "test-password"}          |
	 *      
	 *    For supported page element info please refer {@link PageObject} class.
	 *    For supported operators, refer {@link ValueMatchOperator}.
	 *    For expected information please {@link ExpectedInfo}.
	 *    
	 *    Where - the page element can be specified in two way:
	 *       Direct way: myapp.XyzPage.pageElement
	 *       JSON way:  (Refer {@link PageObject}). Example:
	 *         {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *       Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@Then("verify the selected values of the following page elements on {string}:")
	public void validate_the_selected_values_of_the_following_page_elements(String pageOrScreenName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		
		String pe, operator, expectedInfo;
		PageObjectInfo peInfo;
		ExpectedInfo eInfo;
		List<String> row;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);		
			pe = row.get(0);
			operator = row.get(1);
			expectedInfo = row.get(2);
			expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
			
			peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
			
			PageObjectUtil.validateFormFieldData(peInfo, operator, eInfo, scenarioContext);
		}
	}
	
	/**
	 * Used to fill the form fields information. Field and value information can be listed in tabular form. See the dataTable info below.
	 *
	 * @param pageOrScreenName - the meaningful name of the screen.
	 * @param dataTable - the datatable that contains the page element along with it's input value in the format given below:
	 * <blockquote><pre>
	 *   | Page Element / Field Information             | Input value(s)                                                                             |
	 *   | {name: "myapp.SamplePage.Textbox_UserName"}  | David                                                                                      |
	 *   | myapp.SamplePage.Textbox_Passsword           | {value: "test-password"}                                                                   |
	 *   | myapp.SamplePage.Combobox_Domain             | {value: ["test1", "test2"], valueType: "string-list", selectingOptionMatchMechanism: "startsWithExpectedValue"}           |
	 *   | myapp.SamplePage.Checkbox_GenderMale         | checked                                                                                    |
	 *   | myapp.SamplePage.Radio_GenderMale            | selected                                                                                   |
	 *   | myapp.SamplePage.Label_OpenSection           | {valueType: "cmd-keys", value: ["ENTER"] }                                                 |
	 *   
	 *   For supported page element info please refer {@link PageObject} class.
	 *   For Input value(s) information please {@link InputValue} class.
	 *   
	 *   Where - the page element can be specified in two way:
	 *       Direct way: myapp.XyzPage.pageElement
	 *       JSON way:  (Refer {@link PageObject}). Example:
	 *         {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *       Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("fill the following form fields values present on {string}:")
	public void fill_the_following_form_fields_value_present_on_page(String pageOrScreenName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		
		String pe, inputValueStr;
		PageObjectInfo peInfo;
		InputValue inputValue;
		List<String> row;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);		
			pe = row.get(0);
			inputValueStr = row.get(1);
			inputValueStr = scenarioContext.applyParamsValueOnText(inputValueStr);
			
			peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			inputValue = JsonYamlUtil.parseInputValue(inputValueStr);
			
			PageObjectUtil.fillUiInputFieldInfo(peInfo, inputValue, scenarioContext);
		}
	}
	
	/**
	 * Used to fill the form fields information. Field and value information can be listed in tabular form. See the dataTable info below.
	 *
	 * @param pageOrScreenName - the meaningful name of the screen.
	 * @param dataTable - the datatable that contains the page element along with it's input value in the format given below:
	 * <blockquote><pre>
	 *   | Page Element / Field Information             | Input value(s)                                                                             |
	 *   | {name: "myapp.SamplePage.Textbox_UserName"}  | David                                                                                      |
	 *   | myapp.SamplePage.Textbox_Passsword           | {value: "test-password"}                                                                   |
	 *   | myapp.SamplePage.Combobox_Domain             | {value: ["test1", "test2"], valueType: "string-list", selectingOptionMatchMechanism: "startsWithExpectedValue"}           |
	 *   | myapp.SamplePage.Checkbox_GenderMale         | checked                                                                                    |
	 *   | myapp.SamplePage.Radio_GenderMale            | selected                                                                                   |
	 *   | myapp.SamplePage.Label_OpenSection           | {valueType: "cmd-keys", value: ["ENTER"] }                                                 |
	 *   
	 *   For supported page element info please refer {@link PageObject} class.
	 *   For Input value(s) information please {@link InputValue} class.
	 *   
	 *     Where - the page element can be specified in two way:
	 *       Direct way: myapp.XyzPage.pageElement
	 *       JSON way:  (Refer {@link PageObject}). Example:
	 *         {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *       Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("enter the following form fields information present on {string}:")
	public void fill_the_following_form_fields_value_present_on_page_1(String pageOrScreenName, DataTable dataTable) {
		fill_the_following_form_fields_value_present_on_page(pageOrScreenName, dataTable);
	}
	
	/**
	 * Used to extract options value of the Combobox element and store the extracted options into specified variable.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>    
	 * @param variableName - the name of the variable which stored the extracted options.
	 */
	@SuppressWarnings("unchecked")
	@Then("get dropdown options of {string} page element and store into {string} variable.")
	public void get_dropdown_options_of_page_element_and_store_into_variable(String pe, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				list = (List<String>) PageObjectUtil.invokeValidatorMethod(
						"getAvailableItems", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				break;
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}
		
		scenarioContext.addParamValue(variableName, list);
	}
	
	/**
	 * Used to verify the combobox page element dropdown options information with the expected information.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param expectedInfo - the expected info. The syntax is a JSON syntax:
	 * 		{ev: <value-here>, valueType: "string", textMatchMechanism: "start-with-expected-value", n: 2, inOrder: "no", ignoreCase: "no"}
	 *    For expected info, refer {@link ExpectedInfo}
	 *    For valueType, refer {@link ParamValueType}
	 *    For textMatchMechanism, refer {@link TextMatchMechanism}
	 *    Or we can directly specify value like:
	 *    	"test value"
	 */
	@SuppressWarnings("unchecked")
	@Then("verify dropdown options of {string} page element {string} {string}.")
	public void verify_dropdown_options_of_page_element(String pe, String operator, String expectedInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				list = (List<String>) PageObjectUtil.invokeValidatorMethod(
						"getAvailableItems", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				break;
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}
		
		expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
		ExpectedInfo eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
		
		ParamPath pPath = new ParamPath(peInfo.getPageObject().getName() + "-options", "string-list");
		
		ParameterValidator.validateParamValueAsExpectedInfo(true, pPath, list, operator, eInfo);
	}
	
	/**
	 * Used to verify whether the specified page element location is within the specified rectangular area.
	 * 
	 * @param pe - the page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPage.pageElement
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     Page element classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>  
	 * @param x1 - coordinate x1.
	 * @param y1 - coordinate y1.
	 * @param x2 - coordinate x2.
	 * @param y2 - coordinate y2.
	 */
	@Then("verify the location of {string} page element is within [x1={int}, y1={int}, x2={int}, y2={int}] rectangular area.")
	public void verify_page_element_is_within_rectangular_area(String pe, int x1, int y1, int x2, int y2) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
		Dimension origWindowSize = scenarioContext.getActiveAppDriver().getWebDriver().manage().window().getSize();
		String screenSize = origWindowSize.getWidth() + " x " + origWindowSize.getHeight();
		for(int i = 0; i <= peInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new String[]{int.class.getTypeName()}, new Object[]{0}, peInfo, scenarioContext);
				Rectangle rect = element.getRect();
				
				if(!(rect.x >= x1 && rect.y >= y1 &&  (rect.x + rect.getWidth()) <= x2 && (rect.y + rect.getHeight()) <= y2)) {
					Assert.fail("Screen Size [" + screenSize + "'] :> " + "Element is not within the specified coordinates {x1: " + x1 + ", y1: " + y1 + ", x2: " + x2 + ", y2: " + y2 + "}."
							+ " Actual Coordinates: {x1: " + rect.x + ", y1:" + rect.y + ", x2:" + (rect.x  + rect.width) 
							+ ", y2:" + (rect.y + rect.height) + "}.");
				}
				
				break;
			} catch (Throwable th) {
				if (i == peInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	/**
	 * Used to verify the location of the page elements/objects. System will automatically
	 * resize the web browser based on the specified screen size then after it will check the page element
	 * location within the specified surrounding rectangular area. Refer the argument details for more information.
	 *
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * <blockquote><pre>
	 *       | Screen Size | Page Element               | Surrounding Area Coordinates     |
	 *       | 800 x 600   | myapp.XyzPage.pageElement1 | {x1: 0, y1: 0, x2: 500, y2: 500} |
	 * 
	 *    Where: 
	 *    1. Screen Size is in the format 'width x height'
	 *    2. JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *    3. Surrounding Area Coordinates are the area specified by top left point (x1, y1) and bottom right point (x2, y2) within which
	 *    	 the element is expected to be present. All values should be in pixels. NOTE: Screen top left first point is (0, 0).
	 * </pre></blockquote>
	 * 
	 */
	@Then("verify the location of the following page elements on the screen:")
	public void verify_the_location_of_the_page_elements_on_the_screen(DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = dataTable.asLists();
		List<String> row;
		String screenSize;
		String pe;
		String coordinatesAsStr;
		PageObjectInfo peInfo;
		AreaCoordinates coordinates;
		Dimension origWindowSize = scenarioContext.getActiveAppDriver().getWebDriver().manage().window().getSize();
		try {
			String lastScreenSize = "";
			Map<String, String> peErrorMap = new LinkedHashMap<>();
			for(int i = 1; i < rows.size(); i++) {
				row = rows.get(i);
				screenSize = row.get(0);
				pe = row.get(1);
				coordinatesAsStr = row.get(2);
						
				if(!lastScreenSize.equals(screenSize)) {
					lastScreenSize = screenSize.toLowerCase();
					String[] ssWH = screenSize.split("x");
					scenarioContext.getActiveAppDriver().getWebDriver().manage().window()
					.setSize(new Dimension(Integer.valueOf(ssWH[0].trim()), Integer.valueOf(ssWH[1].trim())));
				}
				
				try {
					peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
					JsonDocumentReader r = new JsonDocumentReader(coordinatesAsStr, false);
					coordinates = r.readValueAsObject("$", AreaCoordinates.class);
				
					PageObjectUtil.invokeValidatorMethod(
						"validateElementPresentWithinArea", new String[]{AreaCoordinates.class.getTypeName(), int.class.getTypeName()}, 
						new Object[]{coordinates, peInfo.getMaxIterationsToLocateElements()}, peInfo, scenarioContext);
				} catch(Throwable th) {
					peErrorMap.put(pe, "Screen Size [" + screenSize + "'] :> " + th.getMessage());
				}
			}
			
			if(peErrorMap.size() > 0) {
				String failedMessage = "ERRORS: \n  ";
				for(Map.Entry<String, String> entry : peErrorMap.entrySet()) {
					failedMessage = failedMessage + entry.getKey() +  " :> " + entry.getValue() + "\n\n  ";
				}
				Assert.fail(failedMessage);
			}
		} finally {
			scenarioContext.getActiveAppDriver().getWebDriver().manage().window()
			.setSize(new Dimension(origWindowSize.width, origWindowSize.height));
		}
		
	}	
}

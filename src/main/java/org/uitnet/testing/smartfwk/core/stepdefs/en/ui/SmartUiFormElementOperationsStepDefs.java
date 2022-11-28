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

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.common.MethodSignature;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.InputValue;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.WebElementUtil;
import org.uitnet.testing.smartfwk.validator.FieldValidator;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * Step definitions for Home page.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiFormElementOperationsStepDefs {
	SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiFormElementOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to verify that the specified page objects are visible on the page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 *       | Page Object | 
	 *       | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPO.poObject |
	 *       
	 *    Where: JSON Syntax for page object (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 */
	@Then("verify that the following page objects are visible on {string}:")
	public void verify_that_the_following_page_elements_are_visible(String pageOrScreenName, DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po = row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateVisible", new Class<?>[] { Integer.TYPE },
					new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
					scenarioContext);
		}
	}
	
	/**
	 * Used to verify that the specified page elements are visible on the page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 *       | Page Element | 
	 *       | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPO.poObject |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 */
	@Then("verify that the following page elements are visible on {string}:")
	public void verify_that_the_following_page_elements_are_visible_1(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_visible(pageOrScreenName, dataTable);
	}

	/**
	 * Used to verify that the specified page element is visible on the screen.
	 * 
	 * @param po - Page Element can be specified in two way:
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify {string} page element is visible on {string}.")
	public void verify_that_the_page_element_is_visible(String po, String pageOrScreenName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateVisible", new Class<?>[] { Integer.TYPE },
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	/**
	 * Used to verify that the specified page object is visible on the screen.
	 * 
	 * @param po - Page Object can be specified in two way:
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify {string} page object is visible on {string}.")
	public void verify_that_the_page_element_is_visible_1(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_visible(po, pageOrScreenName);
	}
	
	/**
	 * Used to verify that the specified page object is visible on the screen.
	 * 
	 * @param po - Page object can be specified in two way:
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page object is visible on {string}.")
	public void verify_that_the_page_element_is_visible_2(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_visible(po, pageOrScreenName);
	}
	
	/**
	 * Used to verify that the specified page element is visible on the screen.
	 * 
	 * @param po - Page object can be specified in two way:
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     
	 * @param pageOrScreenName - the name of the page or screen.
	 */
	@Then("verify that {string} page element is visible on {string}.")
	public void verify_that_the_page_element_is_visible_3(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_visible(po, pageOrScreenName);
	}

	/**
	 * Used to verify that the specified page objects are hidden on the page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 *       | Page Object | 
	 *       | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPO.poObject |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 */
	@Then("verify that the following page objects are hidden on {string}:")
	public void verify_that_the_following_page_elements_are_hidden(String pageOrScreenName, DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po = row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateHidden", new Class<?>[] { Integer.TYPE },
					new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
					scenarioContext);
		}
	}
	
	/**
	 * Used to verify that the specified page elements are hidden on the page / screen.
	 * 
	 * @param pageOrScreenName - meaningful name of page or screen.
	 * @param dataTable - Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 *       | Page Element | 
	 *       | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPO.poObject |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 */
	@Then("verify that the following page elements are hidden on {string}:")
	public void verify_that_the_following_page_elements_are_hidden_1(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_hidden(pageOrScreenName, dataTable);
	}
	
	/**
	 * Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * | Page Element | 
	 * | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 * | myapp.XyzPO.poObject |
	 * 
	 * @param pageOrScreenName
	 * @param dataTable
	 */
	@Then("verify that the following page objects are not visible on {string}:")
	public void verify_that_the_following_page_elements_are_hidden_2(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_hidden(pageOrScreenName, dataTable);
	}
	
	/**
	 * Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * | Page Element | 
	 * | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 * | myapp.XyzPO.poObject |
	 * 
	 * @param pageOrScreenName
	 * @param dataTable
	 */
	@Then("verify that the following page elements are not visible on {string}:")
	public void verify_that_the_following_page_elements_are_hidden_3(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_hidden(pageOrScreenName, dataTable);
	}
	

	/**
	 * 
	 * @param po
	 * @param pageOrScreenName
	 */
	@Then("verify {string} page object is hidden on {string}.")
	public void verify_that_the_page_element_is_hidden(String po, String pageOrScreenName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateHidden", new Class<?>[] { Integer.TYPE },
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	@Then("verify {string} page element is hidden on {string}.")
	public void verify_that_the_page_element_is_hidden_1(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}
	
	@Then("verify that {string} page object is hidden on {string}.")
	public void verify_that_the_page_element_is_hidden_2(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}
	
	@Then("verify that {string} page element is hidden on {string}.")
	public void verify_that_the_page_element_is_hidden_3(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}
	
	@Then("verify {string} page object is not visible on {string}.")
	public void verify_that_the_page_element_is_hidden_4(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}
	
	@Then("verify {string} page element is not visible on {string}.")
	public void verify_that_the_page_element_is_hidden_5(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}
	
	@Then("verify that {string} page object is not visible on {string}.")
	public void verify_that_the_page_element_is_hidden_6(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}
	
	@Then("verify that {string} page element is not visible on {string}.")
	public void verify_that_the_page_element_is_hidden_7(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_hidden(po, pageOrScreenName);
	}

	/**
	 * Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * | Page Element | 
	 * | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 * | myapp.XyzPO.poObject |
	 * 
	 * @param pageOrScreenName
	 * @param dataTable
	 */
	@Then("verify that the following page objects are disabled on {string}:")
	public void verify_that_the_following_page_elements_are_disabled(String pageOrScreenName, DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po = row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateDisabled", new Class<?>[] { Integer.TYPE },
					new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
					scenarioContext);
		}
	}
	
	@Then("verify that the following page elements are disabled on {string}:")
	public void verify_that_the_following_page_elements_are_disabled_1(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_disabled(pageOrScreenName, dataTable);
	}

	/**
	 * 
	 * @param po
	 * @param pageOrScreenName
	 */
	@Then("verify that {string} page object is disabled on {string}.")
	public void verify_that_the_page_element_is_disabled(String po, String pageOrScreenName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateDisabled", new Class<?>[] { Integer.TYPE },
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	@Then("verify that {string} page element is disabled on {string}.")
	public void verify_that_the_page_element_is_disabled_1(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_disabled(po, pageOrScreenName);
	}

	/**
	 * Data table will have the following columns, first row will be ignored due to
	 * column header. example below: 
	 * | Page Element | 
	 * | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 * | myapp.XyzPO.poObject |
	 * 
	 * @param pageOrScreenName
	 * @param dataTable
	 */
	@Then("verify that the following page objects are enabled on {string}:")
	public void verify_that_the_following_page_elements_are_enabled(String pageOrScreenName, DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po = row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			PageObjectUtil.invokeValidatorMethod("validateEnabled", new Class<?>[] { Integer.TYPE },
					new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
					scenarioContext);
		}
	}
	
	@Then("verify that the following page elements are enabled on {string}:")
	public void verify_that_the_following_page_elements_are_enabled_1(String pageOrScreenName, DataTable dataTable) {
		verify_that_the_following_page_elements_are_enabled(pageOrScreenName, dataTable);
	}

	/**
	 *
	 * @param po
	 * @param pageOrScreenName
	 */
	@Then("verify that {string} page element is enabled on {string}.")
	public void verify_that_the_page_element_is_enabled(String po, String pageOrScreenName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("validateEnabled", new Class<?>[] { Integer.TYPE },
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	@Then("verify that {string} page object is enabled on {string}.")
	public void verify_that_the_page_element_is_enabled_1(String po, String pageOrScreenName) {
		verify_that_the_page_element_is_enabled(po, pageOrScreenName);
	}

	/**
	 * Used to type the text at a particular location.
	 * @param textToType
	 * @param po
	 * @param location - valid values: start, end, replace
	 */
	@When("type {string} text in {string} page object at {string} location.")
	public void type_text_in_page_element(String textToType, String po, String location) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		String txt = scenarioContext.applyParamsValueOnText(textToType);
		PageObjectUtil.invokeValidatorMethod("typeText",
				new Class<?>[] { String.class, NewTextLocation.class, Integer.TYPE },
				new Object[] { txt, NewTextLocation.valueOf(location), poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	@When("type {string} text in {string} page element at {string} location.")
	public void type_text_in_page_element_1(String textToType, String po, String location) {
		type_text_in_page_element(textToType, po);
	}
	
	/**
	 * Used to type the text by replacing the existing one.
	 * @param textToType
	 * @param po
	 */
	@When("type {string} text in {string} page object.")
	public void type_text_in_page_element(String textToType, String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		String txt = scenarioContext.applyParamsValueOnText(textToType);
		PageObjectUtil.invokeValidatorMethod("typeText",
				new Class<?>[] { String.class, NewTextLocation.class, Integer.TYPE },
				new Object[] { txt, NewTextLocation.replace, poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	@When("type {string} text in {string} page element.")
	public void type_text_in_page_element_1(String textToType, String po) {
		type_text_in_page_element(textToType, po);
	}

	@When("type the following text in {string} page object:")
	public void type_the_following_text_in_page_element(String po, DocString textToType) {
		String txt = scenarioContext.applyParamsValueOnText(textToType.getContent());
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		PageObjectUtil.invokeValidatorMethod("typeText",
				new Class<?>[] { String.class, NewTextLocation.class, Integer.TYPE }, new Object[] {
						txt, NewTextLocation.replace, poInfo.getMaxIterationsToLocateElements() },
				poInfo, scenarioContext);
	}
	
	@When("type the following text in {string} page element:")
	public void type_the_following_text_in_page_element_1(String po, DocString textToType) {
		type_the_following_text_in_page_element(po, textToType);
	}

	
	@Then("verify that the text part of {string} page object matches {string} text where TextMatchMechanism={string}.")
	public void verify_that_the_text_part_of_page_element_matches_text_where_textmatchmechanism(String po, String expectedText, String textMatchMechanism) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement elements = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new Class<?>[]{int.class}, new Object[]{1}, poInfo, scenarioContext);
				
				FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPageObject().getName() + "->text", elements.getText(), expectedText,
						TextMatchMechanism.valueOf(textMatchMechanism));
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	@Then("verify that the text part of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_that_the_text_part_of_page_element_matches_text_where_textmatchmechanism_1(String po, String expectedText, String textMatchMechanism) {
		verify_that_the_text_part_of_page_element_matches_text_where_textmatchmechanism(po, expectedText, textMatchMechanism);
	}
	
	@Then("verify that {string} attribute value of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_that_attribute_value_of_page_element_matches_text_where_textmatchmechanism(String attributeName, String po, String expectedText, String textMatchMechanism) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				WebElement elements = (WebElement) PageObjectUtil.invokeValidatorMethod(
						"findElement", new Class<?>[]{int.class}, new Object[]{1}, poInfo, scenarioContext);
				
				FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPageObject().getName() + "->" + attributeName, elements.getAttribute(attributeName), expectedText,
						TextMatchMechanism.valueOf(textMatchMechanism));
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	@Then("verify that the value of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_value_of_page_element_matches_text_where_textmatchmechanism(String po, String expectedText, String textMatchMechanism) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		PageObjectUtil.invokeValidatorMethod(
				"validateValue", new Class<?>[]{String.class, TextMatchMechanism.class, int.class}, new Object[]{expectedText, textMatchMechanism, 1}, poInfo, scenarioContext);
	}
	
	@Then("verify that the value of {string} page object matches {string} text where TextMatchMechanism={string}.")
	public void verify_value_of_page_element_matches_text_where_textmatchmechanism_1(String po, String expectedText, String textMatchMechanism) {
		verify_value_of_page_element_matches_text_where_textmatchmechanism(po, expectedText, textMatchMechanism);
	}
	
	@Then("get input text value of {string} page element and store into {string} variable.")
	public void get_input_text_value_of_page_element_and_store_into_variable(String po, String variableName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		WebElement element = (WebElement) PageObjectUtil.invokeValidatorMethod(
				"findElement", new Class<?>[]{int.class}, new Object[]{poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		String textValue = WebElementUtil.getInputTextValue(scenarioContext.getActiveAppDriver(), element);
		
		scenarioContext.addParamValue(variableName, textValue);
	}
	
	@Then("get input text value of {string} page object and store into {string} variable.")
	public void get_input_text_value_of_page_element_and_store_into_variable_1(String po, String variableName) {
		get_input_text_value_of_page_element_and_store_into_variable(po, variableName);
	}
	
	@SuppressWarnings("unchecked")
	@Then("get input text value of each element of {string} page element and store into {string} variable.")
	public void get_input_text_value_of_each_element_of_page_element_and_store_into_variable(String po, String variableName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new Class<?>[]{int.class}, new Object[]{0}, poInfo, scenarioContext);
				
				list.clear();				
				if(elements != null) {					
					for(WebElement elem : elements) {
						String textValue = WebElementUtil.getInputTextValue(scenarioContext.getActiveAppDriver(), elem);
						list.add(textValue);
					}
				}
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, list);
	}
	
	@Then("get input text value of of each element of {string} page object and store into {string} variable.")
	public void get_input_text_value_of_each_element_of_page_element_and_store_into_variable_1(String po, String variableName) {
		get_input_text_value_of_each_element_of_page_element_and_store_into_variable(po, variableName);
	}
	
	@SuppressWarnings("unchecked")
	@Then("get text part of each element of {string} page element and store into {string} variable.")
	public void verify_text_part_of_each_element_of_page_element_and_store_into_variable(String po, String variableName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new Class<?>[]{int.class}, new Object[]{0}, poInfo, scenarioContext);
				
				list.clear();
				if(elements != null) {					
					for(WebElement elem : elements) {
						String textValue = elem.getText();
						list.add(textValue);
					}
				}
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, list);
	}
	
	@Then("get text part of each element of {string} page object and store into {string} variable.")
	public void verify_text_part_of_each_element_of_page_element_and_store_into_variable_1(String po, String variableName) {
		verify_text_part_of_each_element_of_page_element_and_store_into_variable(po, variableName);
	}
	
	@SuppressWarnings("unchecked")
	@Then("get {string} attribute value of each element of {string} page element and store into {string} variable.")
	public void get_attribute_value_of_each_element_of_page_element_and_store_into_variable(String attributeName, String po, String variableName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		List<String> list = new LinkedList<>();
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new Class<?>[]{int.class}, new Object[]{1}, poInfo, scenarioContext);
				
				list.clear();
				if(elements != null) {	
					for(WebElement elem : elements) {
						String textValue = elem.getAttribute(attributeName);
						list.add(textValue);
					}
				}
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
		scenarioContext.addParamValue(variableName, list);
		
	}
	
	@Then("get {string} attribute value of each element of {string} page object and store into {string} variable.")
	public void get_attribute_value_of_each_element_of_page_element_and_store_into_variable_1(String attributeName, String po, String variableName) {
		get_attribute_value_of_each_element_of_page_element_and_store_into_variable(attributeName, po, variableName);
	}
	
	@SuppressWarnings("unchecked")
	@Then("verify text part of each element of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_text_part_of_each_element_of_page_element_matches_text_where_textmatchmechanism(String po, String expectedText, String textMatchMechanism) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new Class<?>[]{int.class}, new Object[]{1}, poInfo, scenarioContext);
				
				if(elements != null) {					
					for(WebElement elem : elements) {
						String textValue = elem.getText();
						FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPageObject().getName() + "->Text", textValue, expectedText,
								TextMatchMechanism.valueOf(textMatchMechanism));
					}
				}
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
	}
	
	@Then("verify text part of each element of {string} page object matches {string} text where TextMatchMechanism={string}.")
	public void verify_text_part_of_each_element_of_page_element_matches_text_where_textmatchmechanism_1(String po, String expectedText, String textMatchMechanism) {
		verify_text_part_of_each_element_of_page_element_matches_text_where_textmatchmechanism(po, expectedText, textMatchMechanism);
	}
	
	@SuppressWarnings("unchecked")
	@Then("verify {string} attribute value of each element of {string} page element matches {string} text where TextMatchMechanism={string}.")
	public void verify_attribute_value_of_each_element_of_page_element_matches_text_where_textmatchmechanism(String attributeName, String po, String expectedText, String textMatchMechanism) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		for(int i = 0; i <= poInfo.getMaxIterationsToLocateElements(); i++) {	
			try {
				List<WebElement> elements = (List<WebElement>) PageObjectUtil.invokeValidatorMethod(
						"findElements", new Class<?>[]{int.class}, new Object[]{1}, poInfo, scenarioContext);
				
				if(elements != null) {	
					for(WebElement elem : elements) {
						String textValue = elem.getAttribute(attributeName);
						FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPageObject().getName() + "->" + attributeName, textValue, expectedText,
								TextMatchMechanism.valueOf(textMatchMechanism));
					}
				}
			} catch (Throwable th) {
				if (i == poInfo.getMaxIterationsToLocateElements()) {
					throw th;
				}
			}
			scenarioContext.waitForSeconds(2);
		}
		
	}
	
	@Then("verify {string} attribute value of each element of {string} page object matches {string} text where TextMatchMechanism={string}.")
	public void verify_attribute_value_of_each_element_of_page_element_matches_text_where_textmatchmechanism_1(String attributeName, String po, String expectedText, String textMatchMechanism) {
		verify_attribute_value_of_each_element_of_page_element_matches_text_where_textmatchmechanism(attributeName, po, expectedText, textMatchMechanism);
	}
	
	/**
	 * Used to call validator method of page object to perform a particular operation.
	 * 
	 * @param methodsAsJson - Method signature format:
	 * 		{name: "methodName", argsType: [Integer.TYPE, String.class], argsValue: [15, "test string"]}
	 * @param po - page object
	 * @param operation - name of the operation
	 */
	@When("call {string} validator method of {string} page object to {string}.")
	public void call_validator_method_of_page_object_to(String methodsAsJson, String po, String operation) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(methodsAsJson, false);
		MethodSignature method = jsonReader.readValueAsObject("$", MethodSignature.class);
		
		PageObjectUtil.invokeValidatorMethod(method.getName(), method.getArgsType(),
				method.getArgsValue(), poInfo, scenarioContext);
	}
	
	@When("call {string} validator method of {string} page element to {string}.")
	public void call_validator_method_of_page_object_to_1(String methodsAsJson, String po, String operation) {
		call_validator_method_of_page_object_to(methodsAsJson, po, operation);
	}
	
	/**
	 * Used to call validator method of page object to perform a particular operation and stores in output variable name.
	 * 
	 * @param methodsAsJson - Method signature format:
	 * 		{name: "methodName", argsType: [Integer.TYPE, String.class], argsValue: [15, "test string"]}
	 * @param po - page object
	 * @param operation - name of the operation
	 * @param variableName - where the return info of validator method is saved.
	 */
	@When("call {string} validator method of {string} page object to {string} and store output to {string} variable.")
	public void call_validator_method_of_page_object_to_and_store_output_to_variable(String methodsAsJson, String po, String operation, String variableName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(methodsAsJson, false);
		MethodSignature method = jsonReader.readValueAsObject("$", MethodSignature.class);
		
		Object output = PageObjectUtil.invokeValidatorMethod(method.getName(), method.getArgsType(),
				method.getArgsValue(), poInfo, scenarioContext);
		
		scenarioContext.addParamValue(variableName, output);
	}
	
	@When("call {string} validator method of {string} page element to {string} and store output to {string} variable.")
	public void call_validator_method_of_page_object_to_and_store_output_to_variable_1(String methodsAsJson, String po, String operation, String variableName) {
		call_validator_method_of_page_object_to_and_store_output_to_variable(methodsAsJson, po, operation, variableName);
	}
	
	/**
	 * Used to validate the page element information when the screen is launch first time or after making the some changes.
	 * Data table format that contains the information:
	 * 
	 * | Page Object                          | Operator      | Expected Information           |
	 * | {name: "SamplePO.Textbox_UserName"}  | contains      | David                          |
	 * | SamplePO.Textbox_Passsword           | =             | {ev: "test-password"}          |
	 * 
	 * For supported page object info please refer @see PageObject class.
	 * For supported operators @see org.uitnet.testing.smartfwk.api.core.validator.ValueMatchOperator enum.
	 * For expected information please @see {@link ExpectedInfo}
	 * 
	 * @param pageOrScreenName
	 * @param dataTable
	 */
	@Then("verify the selected value\\(s) of the following page objects on {string}:")
	public void validate_the_selected_values_of_the_following_page_objects(String pageOrScreenName, DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		
		String po, operator, expectedInfo;
		PageObjectInfo poInfo;
		ExpectedInfo eInfo;
		List<String> row;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);		
			po = row.get(0);
			operator = row.get(1);
			expectedInfo = row.get(2);
			expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
			
			poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
			
			PageObjectUtil.validateFormFieldData(poInfo, operator, eInfo, scenarioContext);
		}
	}
	
	@Then("verify the selected value\\(s) of the following page elements on {string}:")
	public void validate_the_selected_values_of_the_following_page_objects_1(String pageOrScreenName, DataTable dataTable) {
		validate_the_selected_values_of_the_following_page_objects(pageOrScreenName, dataTable);
	}
	
	/**
	 * Used to fill the form fields information. Field and value information can be listed in tabular form as given below:
	 * 
	 * | Page Object / Field Info             | Input value(s)                                                                             |
	 * | {name: "SamplePO.Textbox_UserName"}  | David                                                                                      |
	 * | SamplePO.Textbox_Passsword           | {value: "test-password"}                                                                   |
	 * | SamplePO.Combobox_Domain             | {value: ["test1", "test2"], valueType: "string-list", selectingOptionMatchMechanism: "startsWithExpectedValue"}           |
	 * | SamplePO.Checkbox_GenderMale         | checked                                                                                    |
	 * | SamplePO.Radio_GenderMale            | selected                                                                                   |
	 * | SamplePO.Label_OpenSection           | {valueType: "cmd-keys", value: ["ENTER"] }                                                                           |
	 * 
	 * For supported page object info please refer @see PageObject class.
	 * For Input value(s) information please @see {@link InputValue} class
	 * 
	 * @param pageOrScreenName - name of the page / screen. info only.
	 * @param dataTable
	 */
	@When("fill the following form fields value\\(s) present on {string}:")
	public void fill_the_following_form_fields_value_present_on_page(String pageOrScreenName, DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		
		String po, inputValueStr;
		PageObjectInfo poInfo;
		InputValue inputValue;
		List<String> row;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);		
			po = row.get(0);
			inputValueStr = row.get(1);
			inputValueStr = scenarioContext.applyParamsValueOnText(inputValueStr);
			
			poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			inputValue = JsonYamlUtil.parseInputValue(inputValueStr);
			
			PageObjectUtil.fillUiInputFieldInfo(poInfo, inputValue, scenarioContext);
		}
	}
	
	@When("enter the following form fields information present on {string}:")
	public void fill_the_following_form_fields_value_present_on_page_1(String pageOrScreenName, DataTable dataTable) {
		fill_the_following_form_fields_value_present_on_page(pageOrScreenName, dataTable);
	}
	
}

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

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

/**
 * This class contains the basic steps definitions that are used to test
 * user interface 508 compliancy.
 * 
 * @author Madhav Krishna
 *
 */
public class Smart508CompliancyStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public Smart508CompliancyStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
		
	/**
	 * Used to verify the tab key navigation order on the specified page elements on a particular page or section.
	 * This step also checks the reverse focus order for the specified elements.
	 *  
	 * @param pageOrSectionName - the meaningful name of the page or section.
	 * @param focusSwitchIntervalInMillis - this interval is used to wait and check the focus is set on next element.
	 * @param dataTable - the datatable that contains the page elements. In this table order of the elements matter.
	 * 		Format of the datatable is given below:
	 * <blockquote><pre>
	 *       | Page Object / Page Element                                            | 
	 *       | {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPO.poObject                                                  |
	 *       
	 *    Where: JSON Syntax for page object (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 * </pre></blockquote>
	 * 
	 */
	@Then("verify the tab key navigation order for the following page elements is same as given below [pageOrSecionName={string}, focusSwitchIntervalInMillis: {int}]:")
	public void verify_the_tab_key_navigation_order_for_elements(String pageOrSectionName, int focusSwitchIntervalInMillis, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
						
		List<List<String>> rows = dataTable.asLists();
		
		Assert.assertTrue(rows.size() > 2, "Please specify atleast 2 page elements in the data table to verify tab key navigation.");
		
		SmartAppDriver appDriver = scenarioContext.getActiveAppDriver();
		
		List<String> row = null;
		List<String> rowNext = null;
		for (int i = 1; i < rows.size() - 1; i++) {
			row = rows.get(i);
			String po = row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			
			rowNext = rows.get(i+1);
			String poNext = rowNext.get(0); // Page object
			PageObjectInfo poInfoNext = PageObjectUtil.getPageObjectInfo(poNext, scenarioContext);
			
			if(i == 1) {
				PageObjectUtil.invokeValidatorMethod("sendCommandKeys", new String[] { Integer.TYPE.getTypeName(), CharSequence.class.getTypeName(), CharSequence.class.getTypeName() },
						new Object[] { poInfoNext.getMaxIterationsToLocateElements(), Keys.LEFT_SHIFT, Keys.TAB }, poInfoNext,
						scenarioContext);
				scenarioContext.waitForMilliSeconds(focusSwitchIntervalInMillis);
			}
			
			PageObjectUtil.invokeValidatorMethod("sendCommandKeys", new String[] { Integer.TYPE.getTypeName(), CharSequence.class.getTypeName() },
					new Object[] { poInfo.getMaxIterationsToLocateElements(), Keys.TAB }, poInfo,
					scenarioContext);
			scenarioContext.waitForMilliSeconds(focusSwitchIntervalInMillis);
			
			WebElement elem = appDriver.getWebDriver().switchTo().activeElement();
			if(elem == null) {
				Assert.fail("Tab key pressed at page element present in row " + (i+1) + ". Next focused/active element is not found.");
			} else {
				WebElement nextElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] { Integer.TYPE.getTypeName() },
						new Object[] { poInfoNext.getMaxIterationsToLocateElements() }, poInfoNext,
						scenarioContext);
				Assert.assertEquals(elem.getTagName(), nextElem.getTagName(), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("id"), nextElem.getAttribute("id"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("name"), nextElem.getAttribute("name"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("css"), nextElem.getAttribute("css"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("style"), nextElem.getAttribute("style"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getText(), nextElem.getText(), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
			}
		}
		
		for (int i = rows.size() - 1; i > 2; i--) {
			row = rows.get(i);
			String po = row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po, scenarioContext);
			
			rowNext = rows.get(i-1);
			String poNext = rowNext.get(0); // Page object
			PageObjectInfo poInfoNext = PageObjectUtil.getPageObjectInfo(poNext, scenarioContext);
			
			PageObjectUtil.invokeValidatorMethod("sendCommandKeys", new String[] { Integer.TYPE.getTypeName(), CharSequence.class.getTypeName(), CharSequence.class.getTypeName() },
					new Object[] { poInfo.getMaxIterationsToLocateElements(), Keys.LEFT_SHIFT, Keys.TAB }, poInfo,
					scenarioContext);
			scenarioContext.waitForMilliSeconds(focusSwitchIntervalInMillis);
			
			WebElement elem = appDriver.getWebDriver().switchTo().activeElement();
			if(elem == null) {
				Assert.fail("Shift+Tab key pressed at page element present in row " + (i) + ". Previous focused/active element is not found.");
			} else {
				WebElement nextElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] { Integer.TYPE.getTypeName() },
						new Object[] { poInfoNext.getMaxIterationsToLocateElements() }, poInfoNext,
						scenarioContext);
				Assert.assertEquals(elem.getTagName(), nextElem.getTagName(), "Page element at row " + (i-1) + " is not the previous focused element after shift+tab key is pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("id"), nextElem.getAttribute("id"), "Page element at row " + (i-1) + " is not the previous focused element after shift+tab key is pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("name"), nextElem.getAttribute("name"), "Page element at row " + (i-1) + " is not the previous focused element after shift+tab key is pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("css"), nextElem.getAttribute("css"), "Page element at row " + (i-1) + " is not the previous focused element after shift+tab key is pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getAttribute("style"), nextElem.getAttribute("style"), "Page element at row " + (i-1) + " is not the previous focused element after shift+tab key is pressed on row " + (i) + " element.");
				Assert.assertEquals(elem.getText(), nextElem.getText(), "Page element at row " + (i-1) + " is not the previous focused element after shift+tab key is pressed on row " + (i) + " element.");
			}
		}
	}
	
	
}

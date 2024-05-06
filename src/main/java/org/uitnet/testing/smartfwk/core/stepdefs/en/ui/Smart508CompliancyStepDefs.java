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

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.utils.ColorContrastUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.FontUtil;
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
	 *       | Page Element                                                               | 
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPage.pageElement                                                  |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
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
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			
			rowNext = rows.get(i+1);
			String peNext = rowNext.get(0); // Page element
			PageObjectInfo peInfoNext = PageObjectUtil.getPageObjectInfo(peNext, scenarioContext);
			
			if(i == 1) {
				PageObjectUtil.invokeValidatorMethod("sendCommandKeys", new String[] { Integer.TYPE.getTypeName(), CharSequence[].class.getTypeName() },
						new Object[] { peInfoNext.getMaxIterationsToLocateElements(), new CharSequence[] {Keys.LEFT_SHIFT, Keys.TAB} }, peInfoNext,
						scenarioContext);
				scenarioContext.waitForMilliSeconds(focusSwitchIntervalInMillis);
			}
			
			PageObjectUtil.invokeValidatorMethod("sendCommandKeys", new String[] { Integer.TYPE.getTypeName(), CharSequence[].class.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements(), new CharSequence[] {Keys.TAB} }, peInfo,
					scenarioContext);
			scenarioContext.waitForMilliSeconds(focusSwitchIntervalInMillis);
			
			WebElement elem = appDriver.getWebDriver().switchTo().activeElement();
			if(elem == null) {
				Assert.fail("Tab key pressed at page element present in row " + (i+1) + ". Next focused/active element is not found.");
			} else {
				WebElement nextElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] { Integer.TYPE.getTypeName() },
						new Object[] { peInfoNext.getMaxIterationsToLocateElements() }, peInfoNext,
						scenarioContext);
				Assert.assertEquals("tagName="+elem.getTagName(), "tagName="+nextElem.getTagName(), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals("id="+elem.getAttribute("id"), "id="+nextElem.getAttribute("id"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals("name="+elem.getAttribute("name"), "name="+nextElem.getAttribute("name"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals("css="+elem.getAttribute("css"), "css="+nextElem.getAttribute("css"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals("style="+elem.getAttribute("style"), "style="+nextElem.getAttribute("style"), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
				Assert.assertEquals("text="+elem.getText(), "text="+nextElem.getText(), "Page element at row " + (i + 1) + " is not the next focused element after tab key pressed on row " + (i) + " element.");
			}
		}
		
		for (int i = rows.size() - 1; i > 1; i--) {
			row = rows.get(i);
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			
			rowNext = rows.get(i-1);
			String peNext = rowNext.get(0); // Page element
			PageObjectInfo peInfoNext = PageObjectUtil.getPageObjectInfo(peNext, scenarioContext);
			
			PageObjectUtil.invokeValidatorMethod("sendCommandKeys", new String[] { Integer.TYPE.getTypeName(), CharSequence[].class.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements(), new CharSequence[] {Keys.LEFT_SHIFT, Keys.TAB} }, peInfo,
					scenarioContext);
			scenarioContext.waitForMilliSeconds(focusSwitchIntervalInMillis);
			
			WebElement elem = appDriver.getWebDriver().switchTo().activeElement();
			if(elem == null) {
				Assert.fail("Shift+Tab key pressed at page element present in row " + (i) + ". Previous focused/active element is not found.");
			} else {
				WebElement nextElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] { Integer.TYPE.getTypeName() },
						new Object[] { peInfoNext.getMaxIterationsToLocateElements() }, peInfoNext,
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
	
	/**
	 * Used to verify the color contrast ratio for the page elements that are specified in the data table.
	 * 
	 * Note 1: For 508 compliancy, it is recommended to have color contrast ratio 4.5 (where minFontWeight=400 and minFontSize=0.875 rem).
	 * Note 2: For 508 compliancy, it is recommended to have color contrast ratio 3 (where minFontWeight=500 and minFontSize=1 rem).
	 * Note 2: For 508 compliancy, it is recommended to have color contrast ratio 7 (where minFontWeight=200 and minFontSize=0.875 rem). 
	 * 
	 * @param minContrastRatio - minimum color contrast ratio which is equal to (foregroundColor/backgroundColor).
	 * @param minFontWeight - the minimum font weight for the page element for which this color contrast ratio is valid. 
	 * @param minFontSize - the minimum font size for the page element for which this color contrast ratio is valid.
	 * @param pageOrSectionName - the meaningful name of the page or section name where the page elements are present.
	 * @param dataTable - the data table that contains the page elements for which the color contrast ratio is going to get verified.
	 * 		Format of the datatable is given below:
	 * <blockquote><pre>
	 *       | Page Element                                                               | 
	 *       | {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {}} | 
	 *       | myapp.XyzPage.pageElement                                                  |
	 *       
	 *    Where: JSON Syntax for page element (Refer {@link PageObject}):
	 *       {name: "myapp.XyzPage.pageElement", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 * </pre></blockquote>
	 */
	@Then("verify color contrast ratio [expected minContrastRatio={double} for minFontWeight={int} and minFontSize={double} rem] for the following page elements present on {string}:")
	public void verify_color_contrast_ratio_for_page_elements(double minContrastRatio, int minFontWeight, double minFontSize, String pageOrSectionName, DataTable dataTable) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
			
		List<List<String>> rows = dataTable.asLists();
		
		List<String> row = null;
		WebElement webElem = null;
		List<String> invalidRows = new LinkedList<String>();
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String pe = row.get(0); // Page element
			PageObjectInfo peInfo = PageObjectUtil.getPageObjectInfo(pe, scenarioContext);
			
			
			webElem = (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] { Integer.TYPE.getTypeName() },
					new Object[] { peInfo.getMaxIterationsToLocateElements() }, peInfo,
					scenarioContext);
			
			String fgColor = webElem.getCssValue("color");
			String bgColor = webElem.getCssValue("background-color");
			int fontWeight = Integer.parseInt(webElem.getCssValue("font-weight"));
			double fontSizeInREM = FontUtil.computeFontSizeInREM(webElem.getCssValue("font-size"));
			
			double actualContrastRatio = ColorContrastUtil.calcContrastRatio(fgColor, bgColor);
			
			if(fontWeight >= minFontWeight && fontSizeInREM >= minFontSize) {				
				if(actualContrastRatio >= minContrastRatio) {
					// do nothing
				} else {
					invalidRows.add("{row: " + (i+1) + ", ratio: " + actualContrastRatio + ", fontWeight: " + fontWeight + ", fontSize: " + fontSizeInREM + "}");
				}
			} else {
				invalidRows.add("{row: " + (i+1) + ", ratio: " + actualContrastRatio + ", fontWeight: " + fontWeight + ", fontSize: " + fontSizeInREM + "}");
			}
			
		}
		
		if(invalidRows.size() > 0) {
			Assert.fail("Color contrast ratio (ForegroundColor/BackgroundColor) for the page elements presents on the following rows is invalid: \n"
					+ invalidRows + "\n   Expected minimum color contrast ratio is " + minContrastRatio + ".");
		}
				
	}	
}

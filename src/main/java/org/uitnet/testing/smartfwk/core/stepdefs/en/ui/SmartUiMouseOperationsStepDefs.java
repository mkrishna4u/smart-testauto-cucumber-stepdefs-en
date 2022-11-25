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

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.en.When;

/**
 * Step definitions for Home page.
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
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("click on {string} page object to {string}.")
	public void click_on_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("click", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	@When("click on {string} page element to {string}.")
	public void click_on_page_element_1(String pageObject, String actionName) {
		click_on_page_element(pageObject, actionName);
	}
	
	@When("click {string} page element to {string}.")
	public void click_on_page_element_2(String pageObject, String actionName) {
		click_on_page_element(pageObject, actionName);
	}
	
	@When("click {string} page object to {string}.")
	public void click_on_page_element_3(String pageObject, String actionName) {
		click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("force click on {string} page object to {string}.")
	public void force_click_on_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("forceClick", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	@When("force click on {string} page element to {string}.")
	public void force_click_on_page_element_1(String pageObject, String actionName) {
		force_click_on_page_element(pageObject, actionName);
	}
	
	@When("force click {string} page element to {string}.")
	public void force_click_on_page_element_2(String pageObject, String actionName) {
		force_click_on_page_element(pageObject, actionName);
	}
	
	@When("force click {string} page object to {string}.")
	public void force_click_on_page_element_3(String pageObject, String actionName) {
		force_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("double click on {string} page object to {string}.")
	public void double_click_on_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("doubleClick", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	@When("double click on {string} page element to {string}.")
	public void double_click_on_page_element_1(String pageObject, String actionName) {
		double_click_on_page_element(pageObject, actionName);
	}
	
	@When("double click {string} page element to {string}.")
	public void double_click_on_page_element_2(String pageObject, String actionName) {
		double_click_on_page_element(pageObject, actionName);
	}
	
	@When("double click {string} page object to {string}.")
	public void double_click_on_page_element_3(String pageObject, String actionName) {
		double_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("right click on {string} page object to {string}.")
	public void right_click_on_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("rightClick", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	@When("right click on {string} page element to {string}.")
	public void right_click_on_page_element_1(String pageObject, String actionName) {
		right_click_on_page_element(pageObject, actionName);
	}
	
	@When("right click {string} page element to {string}.")
	public void right_click_on_page_element_2(String pageObject, String actionName) {
		right_click_on_page_element(pageObject, actionName);
	}
	
	@When("right click {string} page object to {string}.")
	public void right_click_on_page_element_3(String pageObject, String actionName) {
		right_click_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("click and hold on {string} page object to {string}.")
	public void click_and_hold_on_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("clickAndHold", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	@When("click and hold on {string} page element to {string}.")
	public void click_and_hold_on_page_element_1(String pageObject, String actionName) {
		click_and_hold_on_page_element(pageObject, actionName);
	}
	
	@When("click and hold {string} page element to {string}.")
	public void click_and_hold_on_page_element_2(String pageObject, String actionName) {
		click_and_hold_on_page_element(pageObject, actionName);
	}
	
	@When("click and hold {string} page object to {string}.")
	public void click_and_hold_on_page_element_3(String pageObject, String actionName) {
		click_and_hold_on_page_element(pageObject, actionName);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("release hold click from {string} page object to {string}.")
	public void release_hold_click_from_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("release", new Class<?>[] {Integer.TYPE}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	@When("release hold click from {string} page element to {string}.")
	public void release_hold_click_from_page_element_1(String pageObject, String actionName) {
		release_hold_click_from_page_element(pageObject, actionName);
	}
	
	@When("drag {string} page element and drop on {string} page element.")
	public void drag_and_drop_page_element(String dragElemPageObject, String dropElemPageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(dragElemPageObject);
		PageObjectUtil.invokeValidatorMethod("release", new Class<?>[] {Integer.TYPE}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	{name="<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 6}
	 * 
	 * @param pageObject
	 * @param actionName
	 */
	@When("mouse hoverover on {string} page object to {string}.")
	public void mouse_hoverover_on_page_element(String pageObject, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("mouseHoverOver", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
	}
	
	@When("mouse hoverover on {string} page element to {string}.")
	public void mouse_hoverover_on_page_element_1(String pageObject, String actionName) {
		mouse_hoverover_on_page_element(pageObject, actionName);
	}
	
	@When("mouse hoverover {string} page element to {string}.")
	public void mouse_hoverover_on_page_element_2(String pageObject, String actionName) {
		mouse_hoverover_on_page_element(pageObject, actionName);
	}
	
	@When("mouse hoverover {string} page object to {string}.")
	public void mouse_hoverover_on_page_element_3(String pageObject, String actionName) {
		mouse_hoverover_on_page_element(pageObject, actionName);
	}

}

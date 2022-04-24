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
package org.uitnet.testing.smartfwk.core.defaults.stepdefs.en.ui;

import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.en.When;

/**
 * Step definitions for Home page.
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultUiMouseOperationsStepDefs {	
	private SmartCucumberUiScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public DefaultUiMouseOperationsStepDefs(SmartCucumberUiScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	"<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 * 
	 * @param pageObject
	 */
	@When("Click on {string} page object.")
	@When("Click on {string} element.")
	@When("Click {string} element.")
	@When("Click {string} page object.")
	public void click_on_element(String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("click", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	"<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 * 
	 * @param pageObject
	 */
	@When("Double click on {string} page object.")
	@When("Double click on {string} element.")
	@When("Double click {string} element.")
	@When("Double click {string} page object.")
	public void double_click_on_element(String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("doubleClick", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	"<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 * 
	 * @param pageObject
	 */
	@When("Right click on {string} page object.")
	@When("Right click on {string} element.")
	@When("Right click {string} element.")
	@When("Right click {string} page object.")
	public void right_click_on_element(String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("rightClick", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	"<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 * 
	 * @param pageObject
	 */
	@When("Click and hold on {string} page object.")
	@When("Click and hold on {string} element.")
	@When("Click and hold {string} element.")
	@When("Click and hold {string} page object.")
	public void click_and_hold_on_element(String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("clickAndHold", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
	}
	
	/**
	 * Page object should be specified using the format.
	 *   When POs are in ./src/main/page_objects/ directory: "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 *   When POs are in sub directory of ./src/main/page_objects/ directory: 
	 *   	"<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 6}"
	 * 
	 * @param pageObject
	 */
	@When("Release hold click from {string} page object.")
	@When("Release hold click from {string} element.")
	@When("Release hold click {string} element.")
	@When("Release hold click {string} page object.")
	public void release_hold_click_from_element(String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		PageObjectUtil.invokeValidatorMethod("release", new Class<?>[] {Integer.TYPE}, 
				new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}

}

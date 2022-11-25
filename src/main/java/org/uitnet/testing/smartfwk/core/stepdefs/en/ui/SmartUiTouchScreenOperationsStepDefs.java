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
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.TouchActionsUtil;

import io.cucumber.java.en.When;

/**
 * Step definitions for Home page.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiTouchScreenOperationsStepDefs {	
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiTouchScreenOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
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
	@When("tap on {string} page object for {int} milliseconds to {string}.")
	public void tap_on_page_element_for_milliseconds_to(String pageObject, int holdDurationInMs, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.tapOnElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, holdDurationInMs);
	}
	
	@When("tap on {string} page element for {int} milliseconds to {string}.")
	public void tap_on_page_element_for_milliseconds_to_1(String pageObject, int holdDurationInMs, String actionName) {
		tap_on_page_element_for_milliseconds_to(pageObject, holdDurationInMs, actionName);
	}
	
	@When("double tap on {string} page object using {int} milliseconds as pause duration to {string}.")
	public void double_tap_on_page_element_using_milliseconds_as_pause_duration_to(String pageObject, int pauseDurationInMs, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.doubleTapOnElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pauseDurationInMs);
	}
	
	@When("double tap on {string} page element using {int} milliseconds as pause duration to {string}.")
	public void double_tap_on_page_element_using_milliseconds_as_pause_duration_to_1(String pageObject, int pauseDurationInMs, String actionName) {
		double_tap_on_page_element_using_milliseconds_as_pause_duration_to(pageObject, pauseDurationInMs, actionName);
	}
	
	@When("tap {int} times on {string} page object using {int} milliseconds as pause duration to {string}.")
	public void tap_n_times_on_page_element_using_milliseconds_as_pause_duration_to(int nTimes, String pageObject, int pauseDurationInMs, String actionName) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.nTapsOnElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, nTimes, pauseDurationInMs);
	}
	
	@When("tap {int} times on {string} page element using {int} milliseconds as pause duration to {string}.")
	public void tap_n_times_on_page_element_using_milliseconds_as_pause_duration_to_1(int nTimes, String pageObject, int pauseDurationInMs, String actionName) {
		tap_n_times_on_page_element_using_milliseconds_as_pause_duration_to(nTimes, pageObject, pauseDurationInMs, actionName);
	}
	
	@When("swipe up {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_up_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeUpFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	@When("swipe up {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_up_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_up_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
	@When("swipe down {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_down_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeDownFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	@When("swipe down {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_down_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_down_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
	@When("swipe left {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_left_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeLeftFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	@When("swipe left {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_left_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_left_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
	@When("swipe right {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_right_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeRightFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	@When("swipe right {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_right_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_right_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
}

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

import org.openqa.selenium.WebElement;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.TouchActionsUtil;

import io.cucumber.java.en.When;

/**
 * Lists step definitions related to touch or touchpad operations like tap, double tap, swipe etc. 
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
	 * Used to perform touchpad tap (using single finger) operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param holdDurationInMs - the duration in milliseconds to hold the touch.
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "tap on {string} page element for {int} milliseconds to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("tap on {string} page object for {int} milliseconds to {string}.")
	public void tap_on_page_element_for_milliseconds_to(String pageObject, int holdDurationInMs, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.tapOnElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, holdDurationInMs);
	}
	
	/**
	 * Used to perform touchpad tap (using single finger) operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param holdDurationInMs - the duration in milliseconds to hold the touch.
	 * @param actionName - meaningful expected action name.
	 */	
	@When("tap on {string} page element for {int} milliseconds to {string}.")
	public void tap_on_page_element_for_milliseconds_to_1(String pageObject, int holdDurationInMs, String actionName) {
		tap_on_page_element_for_milliseconds_to(pageObject, holdDurationInMs, actionName);
	}
	
	/**
	 * Used to perform touchpad double tap (using single finger) operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param pauseDurationInMs - the pause duration in milliseconds between the taps.
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "double tap on {string} page element using {int} milliseconds as pause duration to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("double tap on {string} page object using {int} milliseconds as pause duration to {string}.")
	public void double_tap_on_page_element_using_milliseconds_as_pause_duration_to(String pageObject, int pauseDurationInMs, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.doubleTapOnElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pauseDurationInMs);
	}
	
	/**
	 * Used to perform touchpad double tap (using single finger) operation on the specified page object / page element.
	 * 
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param pauseDurationInMs - the pause duration in milliseconds between the taps.
	 * @param actionName - meaningful expected action name.
	 */
	@When("double tap on {string} page element using {int} milliseconds as pause duration to {string}.")
	public void double_tap_on_page_element_using_milliseconds_as_pause_duration_to_1(String pageObject, int pauseDurationInMs, String actionName) {
		double_tap_on_page_element_using_milliseconds_as_pause_duration_to(pageObject, pauseDurationInMs, actionName);
	}
	
	/**
	 * Used to perform touchpad tap (using single finger) n times on the specified page object / page element.
	 * 
	 * @param nTimes - numeric value / the number times to perform tap.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param pauseDurationInMs - the pause duration in milliseconds between the taps.
	 * @param actionName - meaningful expected action name.
	 * 
	 * @deprecated use "tap {int} times on {string} page element using {int} milliseconds as pause duration to {string}." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("tap {int} times on {string} page object using {int} milliseconds as pause duration to {string}.")
	public void tap_n_times_on_page_element_using_milliseconds_as_pause_duration_to(int nTimes, String pageObject, int pauseDurationInMs, String actionName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.nTapsOnElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, nTimes, pauseDurationInMs);
	}
	
	/**
	 * Used to perform touchpad tap (using single finger) n times on the specified page object / page element.
	 * 
	 * @param nTimes - numeric value / the number times to perform tap.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param pauseDurationInMs - the pause duration in milliseconds between the taps.
	 * @param actionName - meaningful expected action name.
	 */
	@When("tap {int} times on {string} page element using {int} milliseconds as pause duration to {string}.")
	public void tap_n_times_on_page_element_using_milliseconds_as_pause_duration_to_1(int nTimes, String pageObject, int pauseDurationInMs, String actionName) {
		tap_n_times_on_page_element_using_milliseconds_as_pause_duration_to(nTimes, pageObject, pauseDurationInMs, actionName);
	}
	
	/**
	 * Used to perform swipe up operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * 
	 * @deprecated use "swipe up {int} pixels in {int} milliseconds from {string} page element location." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("swipe up {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_up_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeUpFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	/**
	 * Used to perform swipe up operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * 
	 */
	@When("swipe up {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_up_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_up_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
	/**
	 * Used to perform swipe down operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * 
	 * 
	 * @deprecated use "swipe down {int} pixels in {int} milliseconds from {string} page element location." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("swipe down {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_down_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeDownFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	/**
	 * Used to perform swipe down operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * 
	 */
	@When("swipe down {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_down_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_down_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
	/**
	 * Used to perform swipe left operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * 
	 * 
	 * @deprecated use "swipe left {int} pixels in {int} milliseconds from {string} page element location." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("swipe left {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_left_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeLeftFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	/**
	 * Used to perform swipe left operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * 
	 */
	@When("swipe left {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_left_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_left_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
	/**
	 * Used to perform swipe right operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * 
	 * @deprecated use "swipe right {int} pixels in {int} milliseconds from {string} page element location." test step.
	 * 
	 */
	@Deprecated(since = "6.1.4", forRemoval = true)
	@When("swipe right {int} pixels in {int} milliseconds from {string} page object location.")
	public void swipe_right_pixels_in_milliseconds_from_page_object_location(int pixels, int durationInMs, String pageObject) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(pageObject);
		WebElement webElem =  (WebElement) PageObjectUtil.invokeValidatorMethod("findElement", new String[] {Integer.TYPE.getTypeName()}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, scenarioContext);
		scenarioContext.waitForSeconds(1);
		TouchActionsUtil.swipeRightFromElement(scenarioContext.getActiveAppDriver().getWebDriver(), webElem, pixels, durationInMs);
	}
	
	/**
	 * Used to perform swipe right operation using single finger on the specified page object / page element.
	 * 
	 * @param pixels - the number of pixels to swipe up.
	 * @param durationInMs - the duration in milliseconds that inform the system how long it should swipe.
	 * @param pageObject - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("swipe right {int} pixels in {int} milliseconds from {string} page element location.")
	public void swipe_right_pixels_in_milliseconds_from_page_object_location_1(int pixels, int durationInMs, String pageObject) {
		swipe_right_pixels_in_milliseconds_from_page_object_location(pixels, durationInMs, pageObject);
	}
	
}

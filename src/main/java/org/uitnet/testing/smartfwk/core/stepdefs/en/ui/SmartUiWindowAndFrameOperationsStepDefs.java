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

import java.util.Set;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;

import io.cucumber.java.en.When;

/**
 * List step definitions for web browser multiple windows handling and frame handling.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiWindowAndFrameOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiWindowAndFrameOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to switch to the new window by specifying the window handle name.
	 * 
	 * @param windowHandleName - the window handle name.
	 * <blockquote><pre>
	 *   NOTE: To get the window handle, we first have to run this step to get all available window 
	 *         handles. Then you can use the specific window handle to switch to a particular window.
	 *   </pre></blockquote>
	 */
	@When("switch to {string} window.")
	public void switch_to_window(String windowHandleName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Set<String> windowHandles = scenarioContext.getActiveAppDriver().getWebDriver().getWindowHandles();
		scenarioContext.log("AVAILABLE WINDOW HANDLE NAMES: " + windowHandles);
		scenarioContext.getActiveAppDriver().getWebDriver().switchTo().window(windowHandleName);
	}
	
	/**
	 * Used to focus to the new window by specifying the window handle name. This is same operation as
	 * window switching.
	 * 
	 * @param windowHandleName - the window handle name.
	 * <blockquote><pre>
	 *   NOTE: To get the window handle, we first have to run this step to get all available window 
	 *         handles. Then you can use the specific window handle to switch to a particular window.
	 *  </pre></blockquote>
	 */
	@When("focus {string} window.")
	public void switch_to_window_1(String windowHandleName) {
		switch_to_window(windowHandleName);
	}

	/**
	 * Used to default setting of the web driver. In this case if you already switched to different window 
	 * or frame then this step will switch back to parent window or parent frame. 
	 */
	@When("switch to default content.")
	public void switch_to_default_content() {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.getActiveAppDriver().getWebDriver().switchTo().defaultContent();
	}

	/**
	 * Used to switch to different frame of the DOM. So that web driver can access the page object / page elements
	 * of the switched frame.
	 * 
	 * @param frameNameOrId - the ID or the name of the frame.
	 * <blockquote><pre>
	 *   NOTE: If we give frameNameOrId = "parent" then it will switch the driver pointer to parent frame.
	 *   NOTE: If we give frameNameOrId = "INDEX:2" then it will switch the driver pointer to second frame.
	 *   Else it will use the value to treat as ID or Name of the frame.
	 *   </pre></blockquote>
	 */
	@When("switch to {string} frame.")
	public void switch_to_frame(String frameNameOrId) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if ("parent".equals(frameNameOrId)) {
			scenarioContext.getActiveAppDriver().getWebDriver().switchTo().parentFrame();
		} else if (frameNameOrId.startsWith("INDEX:")) {
			scenarioContext.getActiveAppDriver().getWebDriver().switchTo()
					.frame(Integer.valueOf(frameNameOrId.substring("INDEX:".length(), frameNameOrId.length()).trim()));
		} else {
			scenarioContext.getActiveAppDriver().getWebDriver().switchTo().frame(frameNameOrId);
		}
	}

	/**
	 * Used to switch to different frame of the DOM by specifying the index value. So that web driver can access the page object / page elements
	 * of the switched frame.
	 * 
	 * @param frameNumber - the frame index in the page.
	 */
	@When("switch to frame number {int}.")
	public void switch_to_frame_number(Integer frameNumber) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.getActiveAppDriver().getWebDriver().switchTo().frame(frameNumber);
	}

}

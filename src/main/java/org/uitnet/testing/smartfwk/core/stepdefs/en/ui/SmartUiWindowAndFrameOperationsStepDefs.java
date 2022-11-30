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
 * Step definitions for Home page.
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
	
	@When("focus {string} window.")
	public void switch_to_window_1(String windowHandleName) {
		switch_to_window(windowHandleName);
	}

	@When("switch to default content.")
	public void switch_to_default_content() {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.getActiveAppDriver().getWebDriver().switchTo().defaultContent();
	}

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

	@When("switch to frame number {int}.")
	public void switch_to_frame_number(Integer frameNumber) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.getActiveAppDriver().getWebDriver().switchTo().frame(frameNumber);
	}

}

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

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;

import io.cucumber.docstring.DocString;
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
	 * Used to switch to different frame of the DOM. So that web driver can access the page elements
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
	 * Used to switch to different frame of the DOM by specifying the index value. So that web driver can access the page elements
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
	
	/**
	 * Used to close the web browser window alert by clicking on OK, Reload, "Leave page", "Stay on page" or Cancel button.
	 * 
	 * @param buttonName - the name of the button to be clicked. Valid values: OK, Cancel
	 * @param  maxTimeToWaitInSeconds - max time to wait in seconds to perform this operation successfully.
	 */
	@When("click on {string} button to close the alert dialog [MaxTimeToWaitInSeconds={int}].")
	public void click_on_cancel_button_to_close_the_alert(String buttonName, int maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		WebDriver webDriver = scenarioContext.getActiveAppDriver().getWebDriver();
		int maxIters = (maxTimeToWaitInSeconds / 2 < 1) ? 0 : (maxTimeToWaitInSeconds / 2);
		
		for(int i = 0; i <= maxIters; i++) {	
			try {
				if("OK".equalsIgnoreCase(buttonName) || "Reload".equalsIgnoreCase(buttonName) || "Leave page".equalsIgnoreCase(buttonName)) {
					webDriver.switchTo().alert().accept();
				} else if("Cancel".equalsIgnoreCase(buttonName) || "Stay on page".equalsIgnoreCase(buttonName)) {
					webDriver.switchTo().alert().dismiss();
				} else {
					Assert.fail("'" + buttonName + "' button is not supported.");
				}
				break;
			} catch (Throwable th) {
				if (i == maxIters) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}	
	}
	
	/**
	 * Used to verify the contents of the alert dialog.
	 * 
	 * @param operator -  - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param expectedInfo - the expected info. The syntax is a JSON syntax:
	 * 		{ev: <value-here>, valueType: "string", textMatchMechanism: "start-with-expected-value", n: 2, inOrder: "no", ignoreCase: "no"}
	 *    For expected info, refer {@link ExpectedInfo}
	 *    For valueType, refer {@link ParamValueType}
	 *    For textMatchMechanism, refer {@link TextMatchMechanism}
	 *    Or we can directly specify value like:
	 *    	"test value"
	 * @param  maxTimeToWaitInSeconds - max time to wait in seconds to perform this operation successfully.
	 */
	@When("verify the alert dialog message {string} {string} [MaxTimeToWaitInSeconds={int}].")
	public void verify_the_alert_dialog_message_where(String operator, String expectedInfo, int maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		WebDriver webDriver = scenarioContext.getActiveAppDriver().getWebDriver();
		int maxIters = (maxTimeToWaitInSeconds / 2 < 1) ? 0 : (maxTimeToWaitInSeconds / 2);
		
		for(int i = 0; i <= maxIters; i++) {	
			try {
				String textValue = webDriver.switchTo().alert().getText();
				ExpectedInfo eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
				
				ParamPath pPath = new ParamPath("Alert-Text", "string");
				
				ParameterValidator.validateParamValueAsExpectedInfo(true, pPath, textValue, operator, eInfo);
				
				break;
			} catch (Throwable th) {
				if (i == maxIters) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}
		
	}
	
	/**
	 * Used to enter the text on the prompt / alert window.
	 * 
	 * @param buttonName - the name of the button to be clicked after entering the text.
	 * 		Valid values: OK, Cancel
	 * 
	 * @param text - the text that to be entered on prompt/alert dialog.
	 * @param  maxTimeToWaitInSeconds - max time to wait in seconds to perform this operation successfully.
	 */
	@When("enter the following text on alert dialog and click on {string} button [MaxTimeToWaitInSeconds={int}]:")
	public void verify_the_alert_dialog_message_where(String buttonName, int maxTimeToWaitInSeconds, DocString text) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		WebDriver webDriver = scenarioContext.getActiveAppDriver().getWebDriver();
		String text2 = text.getContent();
		text2 = scenarioContext.applyParamsValueOnText(text2);
		int maxIters = (maxTimeToWaitInSeconds / 2 < 1) ? 0 : (maxTimeToWaitInSeconds / 2);
		
		for(int i = 0; i <= maxIters; i++) {	
			try {
				webDriver.switchTo().alert().sendKeys(text2);
				if("OK".equalsIgnoreCase(buttonName)) {
					webDriver.switchTo().alert().accept();
				} else if("Cancel".equalsIgnoreCase(buttonName)) {
					webDriver.switchTo().alert().dismiss();
				} else {
					Assert.fail("'" + buttonName + "' button is not supported.");
				}
				
				break;
			} catch (Throwable th) {
				if (i == maxIters) {
					throw th;
				}
				scenarioContext.waitForSeconds(2);
			}
		}
	}

}

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

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class contains the login steps and the shared steps that can be used in
 * all the scenarios defined in cucumber feature file.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiBasicAppOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartUiBasicAppOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Properties are specified as json string at the same level as specified in AppDriver.yaml file.
	 * 
	 * @param browserType - refer {@link WebBrowserType}
	 * @param appName - configured application name
	 * @param jsonDocument - the parameters can be specified in JSON format as given in AppDriver.yaml file.
	 */
	@Given("set the following app driver properties for [AppName={string}, WebBrowser={string}]:")
	public void set_the_following_driver_properties_for_app_webbrowser(String appName, String browserType, DocString jsonDocument) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.overrideDriverProps(appName, browserType, jsonDocument.getContent());
	}
	
	/**
	 * Properties are specified as json string at the same level as specified in AppDriver.yaml file.
	 * 
	 * @param appName - configured application name
	 * @param jsonDocument - the parameters can be specified in JSON format as given in AppDriver.yaml file.
	 */
	@Given("set the following app driver properties for [AppName={string}]:")
	public void set_the_following_driver_properties_for_app_webbrowser2(String appName, DocString jsonDocument) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.overrideDriverProps(appName, WebBrowserType.notApplicable.getType(), jsonDocument.getContent());
	}


	/**
	 * Used to open / connect to configured application.
	 * 
	 * @param appName - configured application name
	 */
	@When("open {string} application.")
	public void connect_or_switch_to_application(String appName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.connectOrSwitch(appName);
	}
	
	/**
	 * Used to open / connect to configured application.
	 * 
	 * @param appName - configured application name
	 */
	@Given("{string} application is already opened.")
	public void connect_or_switch_to_application_2(String appName) {
		connect_or_switch_to_application(appName);
	}
	
	/**
	 * Used to open / connect to configured application.
	 * 
	 * @param appName - configured application name
	 */
	@Given("application {string} is already opened.")
	public void connect_or_switch_to_application_3(String appName) {
		connect_or_switch_to_application(appName);
	}
	
	/**
	 * Used to open / connect to configured application.
	 * 
	 * @param appName - configured application name
	 */
	@When("switch to {string} application.")
	public void connect_or_switch_to_application_4(String appName) {
		connect_or_switch_to_application(appName);
	}
	
	/**
	 * Used to open / connect to configured application.
	 * 
	 * @param appName - configured application name
	 */
	@When("connect to {string} application.")
	public void connect_or_switch_to_application_5(String appName) {
		connect_or_switch_to_application(appName);
	}
	
	/**
	 * Used to open / connect to configured application.
	 * 
	 * @param appName - configured application name
	 */
	@Given("already connected to {string} application.")
	public void connect_or_switch_to_application_6(String appName) {
		connect_or_switch_to_application(appName);
	}

	/**
	 * This definition is just for information purpose.
	 * No code needed here.
	 */
	@Then("connection to application is successful.")
	public void app_connected_or_switched_success() {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do nothing
	}
	
	/**
	 * This definition is just for information purpose.
	 * No code needed here.
	 */
	@Then("application switching is successful.")
	public void app_connected_or_switched_success_1() {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do nothing
	}
	
	/**
	 * This definition is just for information purpose.
	 * No code needed here.
	 */
	@Then("application is switched successfully.")
	public void app_connected_or_switched_success_2() {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do nothing
	}

	/**
	 * This is used to connect to application using user profile.
	 * Note: Application must be connected because this step definition
	 * will activate the user profile with the latest activated application.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@Given("user is logged in using {string} user profile.")
	public void user_login_using_user_profile(String userProfileName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.setActiveUserProfileOnActiveApp(userProfileName);
	}
	
	/**
	 * This is used to connect to application using user profile.
	 * Note: Application must be connected because this step definition
	 * will activate the user profile with the latest activated application.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@Given("user is already logged in using {string} user profile.")
	public void user_login_using_user_profile_1(String userProfileName) {
		user_login_using_user_profile(userProfileName);
	}
	
	/**
	 * This is used to connect to application using user profile.
	 * Note: Application must be connected because this step definition
	 * will activate the user profile with the latest activated application.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@When("user login using {string} user profile.")
	public void user_login_using_user_profile_2(String userProfileName) {
		user_login_using_user_profile(userProfileName);
	}
	
	/**
	 * This is used to connect to application using user profile.
	 * Note: Application must be connected because this step definition
	 * will activate the user profile with the latest activated application.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@When("login using {string} user profile.")
	public void user_login_using_user_profile_3(String userProfileName) {
		user_login_using_user_profile(userProfileName);
	}
	
	/**
	 * This is used to switch user profile on the activated application.
	 * Note: Application must be connected because this step definition
	 * will activate the user profile with the latest activated application.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@When("user switch to {string} user profile.")
	public void user_login_using_user_profile_4(String userProfileName) {
		user_login_using_user_profile(userProfileName);
	}
	
	/**
	 * This is used to switch user profile on the activated application.
	 * Note: Application must be connected because this step definition
	 * will activate the user profile with the latest activated application.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@When("switch to {string} user profile.")
	public void user_login_using_user_profile_5(String userProfileName) {
		user_login_using_user_profile(userProfileName);
	}
	
	/**
	 * This is used to activate application and user profile together.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 * @param appName - the name of the configured application that can be activated.
	 */
	@Given("user is already connected using {string} user profile on {string} application.")
	public void user_login_using_user_profile_on_application_1(String userProfileName, String appName) {
		user_login_using_user_profile_on_application(userProfileName, appName);
	}
	
	/**
	 * This is used to activate application and user profile together.
	 * 
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 * @param appName - the name of the configured application that can be activated.
	 */
	@Given("user is already logged in using {string} user profile on {string} application.")
	public void user_login_using_user_profile_on_application(String userProfileName, String appName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		connect_or_switch_to_application(appName);
		user_login_using_user_profile(userProfileName);
	}

	/**
	 * This is used to switch application and user profile together.
	 * 
	 * @param appName - the name of the configured application that can be activated.
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@When("switch to {string} application using {string} user profile.")
	public void switch_to_application_using_user_profile(String appName, String userProfileName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		connect_or_switch_to_application(appName);
		user_login_using_user_profile(userProfileName);
	}
	
	/**
	 * This is used to switch application and user profile together.
	 * 
	 * @param appName - the name of the configured application that can be activated.
	 * @param userProfileName - name of the user profile that can be activated on the application.
	 */
	@When("connect to {string} application using {string} user profile.")
	public void switch_to_application_using_user_profile_1(String appName, String userProfileName) {
		switch_to_application_using_user_profile(appName, userProfileName);
	}

	/**
	 * Used to open URL.
	 * 
	 * @param url - URL that need to be opened.
	 */
	@Given("URL {string} is already opened.")
	public void open_url(String url) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if (url.startsWith("file:///")) {
			String newUrl = "file:///" + Locations.getProjectRootDir().replace("\\", "/") + "/"
					+ url.split("file:///")[1].trim();
			scenarioContext.getActiveAppDriver().openURL(newUrl);
		} else {
			scenarioContext.getActiveAppDriver().openURL(url);
		}
	}
	
	/**
	 * Used to open URL.
	 * 
	 * @param url - URL that need to be opened.
	 */
	@Given("{string} URL is already opened.")
	public void open_url_1(String url) {
		open_url(url);
	}
	
	/**
	 * Used to open URL.
	 * 
	 * @param url - URL that need to be opened.
	 */
	@When("open {string} URL.")
	public void open_url_2(String url) {
		open_url(url);
	}

	/**
	 * Just for information to make scenario meaningful.
	 * @param url - url
	 */
	@Then("the {string} URL is displayed.")
	public void url_is_displayed(String url) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything
	}
	
	/**
	 * Just for information to make scenario meaningful.
	 * @param url
	 */
	@Then("{string} URL is displayed.")
	public void url_is_displayed_1(String url) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything
	}
	
	/**
	 * Just for information to make scenario meaningful.
	 * @param url
	 */
	@Then("the {string} URL is opened.")
	public void url_is_displayed_2(String url) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything
	}
	
	/**
	 * Just for information to make scenario meaningful.
	 * @param url
	 */
	@Then("{string} URL is opened.")
	public void url_is_displayed_3(String url) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything
	}
	
	/**
	 * Just for information to make scenario meaningful.
	 * @param pageName - the name of page or screen. Just for info.
	 */
	@Then("verify {string} page is displayed.")
	public void verify_the_page_is_displayed(String pageName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything.
	}

	/**
	 * Just for information to make scenario meaningful.
	 * @param pageName - the name of page or screen. Just for info.
	 */
	@Then("the {string} page is displayed.")
	public void the_page_is_displayed(String pageName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything
	}
	
	/**
	 * Just for information to make scenario meaningful.
	 * @param pageName - the name of page or screen. Just for info.
	 */
	@Then("{string} page is displayed.")
	public void the_page_is_displayed_1(String pageName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do not do anything
	}

	/**
	 * Validates whether the application is set as active application.
	 * @param appName - the name of the configured application.
	 */
	@Then("{string} application is switched successfully.")
	public void application_is_switched_successfully(String appName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveAppName(), appName,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(appName + " is not activated.");
		}
	}
	
	/**
	 * Validates whether the application is set as active application.
	 * @param appName - the name of the configured application.
	 */
	@Then("connected to {string} application successfully.")
	public void application_is_switched_successfully_1(String appName) {
		application_is_switched_successfully(appName);
	}
	
	/**
	 * Validates whether the application is set as active application.
	 * @param appName - the name of the configured application.
	 */
	@Then("{string} application is opened successfully.")
	public void application_is_switched_successfully_2(String appName) {
		application_is_switched_successfully(appName);
	}

	/**
	 * Validates whether the user profile is set as active profile on the active application.
	 * @param userProfile - the name of the user profile on the active application.
	 */
	@Then("{string} user profile is switched successfully.")
	public void user_profile_is_switched_successfully(String userProfile) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveUserProfileNameOfActiveApp(),
				userProfile, TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(userProfile + " is not activated.");
		}
	}
	
	/**
	 * Validates whether the user profile is set as active profile on the active application.
	 * @param userProfile - the name of the user profile on the active application.
	 */
	@Then("{string} user profile is activated successfully.")
	public void user_profile_is_switched_successfully_1(String userProfile) {
		user_profile_is_switched_successfully(userProfile);
	}

	/**
	 * Validates whether the user profile is set as active profile on the specified application.
	 * @param userProfile - the name of the user profile on the active application.
	 * @param appName - the name of the configured application.
	 */
	@Then("{string} user profile is switched successfully on {string} application.")
	public void user_profile_is_switched_successfully(String userProfile, String appName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveUserProfileName(appName), userProfile,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(userProfile + " is not activated.");
		}

		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveAppName(), appName,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(appName + " is not activated.");
		}
	}
	
	/**
	 * Validates whether the user profile is set as active profile on the specified application.
	 * @param userProfile - the name of the user profile on the active application.
	 * @param appName - the name of the configured application.
	 */
	@Then("{string} user profile is activated successfully on {string} application.")
	public void user_profile_is_switched_successfully_1(String userProfile, String appName) {
		user_profile_is_switched_successfully(userProfile);
	}

	/**
	 * Used to take screenshot while the scenario is running.
	 */
	@Then("take screenshot.")
	public void take_screenshot() {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if(scenarioContext.isUiScenario()) {
			scenarioContext.captureScreenshot("scenario-RUNNUNG");
		}
	}
	
	/**
	 * Used to take screenshot while the scenario is running and store screenshot filename into variable for feature use.
	 * @param variableName - name of the variable.
	 */
	@Then("take screenshot and store filename in {string} variable.")
	public void take_screenshot_and_store_filename_in_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		if(scenarioContext.isUiScenario()) {
			String fileName = scenarioContext.captureScreenshot("scenario-RUNNUNG");
			scenarioContext.addParamValue(variableName, fileName);
		}
	}
	
	/**
	 * Just for information to make scenario meaningful.
	 * @param action - the meaningful name of action.
	 * @param performed - meaningful performed information.
	 */
	@Then("{string} will be {string}.")
	public void action_will_be_performed(String action, String performed) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		// do nothing
	}
	
	/**
	 * Adds pause between the step.
	 * 
	 * @param waitNumSeconds - numeric value that represent how many minutes the pause should be added.
	 */
	@When("wait for {int} seconds.")
	public void wait_for_seconds(int waitNumSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.waitForSeconds(waitNumSeconds);
	}
}

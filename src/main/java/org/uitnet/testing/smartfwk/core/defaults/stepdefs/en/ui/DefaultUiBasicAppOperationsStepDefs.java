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

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
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
public class DefaultUiBasicAppOperationsStepDefs {
	private SmartCucumberUiScenarioContext scenarioContext;

	public DefaultUiBasicAppOperationsStepDefs(SmartCucumberUiScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	@Before
	public void beforeScenario(Scenario scenario) {
		scenarioContext.setScenario(scenario);
		scenarioContext.captureScreenshotWithScenarioStatus("scenario-STARTED");
	}

	@After
	public void afterScenario(Scenario scenario) {
		scenarioContext.captureScreenshotWithScenarioStatus("scenario-" + scenario.getStatus());
		if (scenarioContext.getTestConfigManager().isParallelMode()) {
			scenarioContext.close();
		} else {
			try {
				scenarioContext.closeAllChildWindows();
				scenarioContext.switchToDefaultContent();
			} catch (Exception | Error e) {
			}
		}
	}

	@When("Open {string} application.")
	@Given("{string} application is already opened.")
	@Given("Application {string} is already opened.")
	@When("Switch to {string} application.")
	@When("Connect to {string} application.")
	@Given("Connected to {string} application.")
	public void connect_or_switch_to_application(String appName) {
		scenarioContext.connectOrSwitch(appName);
	}

	@Then("Connection to application is successful.")
	@Then("Application switching is successful.")
	@Then("Application is switched successfully.")
	public void app_connected_or_switched_success() {
		// do nothing
	}

	@Given("User is logged in using {string} user profile.")
	@Given("User is already logged in using {string} user profile.")
	@When("User login using {string} user profile.")
	@When("Login using {string} user profile.")
	@When("User switch to {string} user profile.")
	@When("Switch to {string} user profile.")
	public void user_login_using_user_profile(String userProfileName) {
		scenarioContext.setActiveUserProfileOnActiveApp(userProfileName);
	}

	@Given("User profile {string} is already activated on {string} application.")
	@Given("{string} user profile is already activated on {string} application.")
	@When("Switch {string} user profile on {string} application.")
	@When("Switch to {string} user profile on {string} application.")
	@When("Activate {string} user profile on {string} application.")
	public void activate_user_profile_on_app(String userProfile, String appName) {
		scenarioContext.connectOrSwitch(appName, userProfile);
	}

	@Given("Switch to {string} application using {string} user profile.")
	@Given("Connect to {string} application using {string} user profile.")
	public void switch_to_application_using_user_profile(String appName, String userProfile) {
		connect_or_switch_to_application(appName);
		user_login_using_user_profile(userProfile);
	}

	@Given("URL {string} is already opened.")
	@Given("{string} URL is already opened.")
	@When("Open {string} URL.")
	public void open_url(String url) {
		if (url.startsWith("file:///")) {
			String newUrl = "file:///" + Locations.getProjectRootDir().replace("\\", "/") + "/"
					+ url.split("file:///")[1].trim();
			scenarioContext.getActiveAppDriver().openURL(newUrl);
		} else {
			scenarioContext.getActiveAppDriver().openURL(url);
		}
	}

	@Then("The {string} URL is displayed.")
	@Then("{string} URL is displayed.")
	@Then("The {string} URL is opened.")
	@Then("{string} URL is opened.")
	public void url_is_displayed(String url) {
		// do not do anything
	}

	@Then("Verify {string} page is displayed.")
	public void verify_the_page_is_displayed(String pageName) {
		// do not do anything.
	}

	@Then("The {string} page is displayed.")
	@Then("{string} page is displayed.")
	public void the_page_is_displayed(String pageName) {
		// do not do anything
	}

	@Then("{string} application is switched successfully.")
	@Then("Connected to {string} application successfully.")
	@Then("{string} application is opened successfully.")
	public void application_is_switched_successfully(String appName) {
		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveAppName(), appName,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(appName + " is not activated.");
		}
	}

	@Then("{string} user profile is switched successfully.")
	@Then("{string} user profile is activated successfully.")
	public void user_profile_is_switched_successfully(String userProfile) {
		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveUserProfileNameOfActiveApp(),
				userProfile, TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(userProfile + " is not activated.");
		}
	}

	@Then("{string} user profile is switched successfully on {string} application.")
	@Then("{string} user profile is activated successfully on {string} application.")
	public void user_profile_is_switched_successfully(String userProfile, String appName) {
		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveUserProfileName(appName), userProfile,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(userProfile + " is not activated.");
		}

		if (!StringUtil.isTextMatchedWithExpectedValue(scenarioContext.getActiveAppName(), appName,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(appName + " is not activated.");
		}
	}

	@Then("Take screenshot.")
	public void take_screenshot() {
		scenarioContext.captureScreenshot("scenario-RUNNUNG");
	}
}

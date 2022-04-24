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
public class DefaultUiKeyboardOperationsStepDefs {
	private SmartCucumberUiScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public DefaultUiKeyboardOperationsStepDefs(SmartCucumberUiScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Example: "ctrl a", "ctrl tab", "ctrl shift tab", "enter", "alt tab", "esc",
	 * "f12", "ctrl f4", "pagedown", "pageup", "home", "end", "insert", "numlock",
	 * "arrowup", "arrowdown", "arrowleft", "arrowright", "ctrl spacebar"
	 * 
	 * @param keys - space separated keys.
	 */
	@When("Press {string} key(s) on {string} element.")
	@When("Press {string} key(s) on {string} page object.")
	public void press_keys(String keys, String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);

		// TODO
	}
}

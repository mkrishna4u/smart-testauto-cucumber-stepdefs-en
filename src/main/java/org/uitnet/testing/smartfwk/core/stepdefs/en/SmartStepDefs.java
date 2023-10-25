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
package org.uitnet.testing.smartfwk.core.stepdefs.en;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioHooksExecuter;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Lists the global cucumber hooks. 
 * 
 * @author Madhav Krishna
 *
 */
public class SmartStepDefs {

	private SmartCucumberScenarioContext scenarioContext;

	public SmartStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * This method is called before each scenario.
	 * 
	 * @param scenario
	 */
	@Before
	public void beforeScenario(Scenario scenario) {
		this.scenarioContext.setScenario(scenario);
	}

	/**
	 * This method is called after each scenario.
	 * @param scenario
	 */
	@After
	public void afterScenario(Scenario scenario) {
		SmartCucumberScenarioHooksExecuter.getInstance().executeAfterScenarioHooks(scenarioContext);
		
		if(TestConfigManager.getInstance().getUseDefaultStepDefsHooks()) {
			scenarioContext.close(true);
		}
	}
}

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

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;

/**
 * This class contains the basic steps definitions that are used to test
 * user interface 508 compliancy.
 * 
 * @author Madhav Krishna
 *
 */
public class Smart508CompliancyStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public Smart508CompliancyStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
		
	/**
	 * Used to verify the tab key focus order on the specified page elements on a particular page or section.
	 * This step also checks the reverse focus order for the specified elements.
	 *  
	 * @param pageOrSectionName - the meaningful name of the page or section.
	 * @param pageElements - the datatable that contains the page elements.
	 */
	@Then("verify the tab key focus order for the following page elements is same as given below [pageOrSecionName={string}]:")
	public void verify_the_tab_key_order_for_elements(String pageOrSectionName, DataTable pageElements) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		throw new PendingException();
	}
	
	
}

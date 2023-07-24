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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.utils.MathUtil;

import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to set and clear conditions to run step conditionally.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartMathOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartMathOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to increment the variable value by the specified number.
	 * 
	 * @param variableName - the name of the variable that value going to get incremented.
	 * @param incrementBy - the number to be added to variable value.
	 */
	@When("increment {string} variable value by {string}.")
	public void increment_variable_value(String variableName, String incrementBy) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String varValue = scenarioContext.getParamValueAsString(variableName);
		String incBy = scenarioContext.getParamValueAsString(incrementBy);
		Object result = MathUtil.add(varValue, incBy);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to decrement the variable value by the specified number.
	 * 
	 * @param variableName - the name of the variable that value going to get decrement.
	 * @param decrementBy - the number to be subtracted from variable value.
	 */
	@When("decrement {string} variable value by {string}.")
	public void decrement_variable_value(String variableName, String decrementBy) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String varValue = scenarioContext.getParamValueAsString(variableName);
		String decBy = scenarioContext.getParamValueAsString(decrementBy);
		Object result = MathUtil.substract(varValue, decBy);
		scenarioContext.addParamValue(variableName, result);
	}
	

}

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
		
		String varValue = "" + scenarioContext.getParamValueNullAsParamName(variableName);
		String incBy = "" + scenarioContext.getParamValueNullAsParamName(incrementBy);
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
		
		String varValue = "" + scenarioContext.getParamValueNullAsParamName(variableName);
		String decBy = "" + scenarioContext.getParamValueNullAsParamName(decrementBy);
		Object result = MathUtil.substract(varValue, decBy);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to calculate the numeric value power. 
	 * 	Example: 10 to the power of 3 = 1000
	 * 
	 * @param number - the value or the variable value on which the power will be applied.
	 * @param power - power value.
	 * @param variableName - this variable stores the calculated value.
	 */
	@When("calculate {string} to the power {string} and store result into {string} variable.")
	public void calculate_power(String number, String power, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue = "" + scenarioContext.getParamValueNullAsParamName(number);
		String powerValue = "" + scenarioContext.getParamValueNullAsParamName(power);
		Object result = MathUtil.power(numValue, powerValue);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to convert value into absolute value. 
	 * 	Example: abs(-10.12) = 10.12
	 * 
	 * @param number - the value or the variable value to be converted to absolute value.
	 * @param variableName - this variable stores the calculated value.
	 */
	@When("convert {string} to absolute value and store result into {string} variable.")
	public void convert_to_absolute_value(String number, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue = "" + scenarioContext.getParamValueNullAsParamName(number);
		Object result = MathUtil.abs(numValue);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to calculate modulus of two numbers. 
	 * 	Example: 5 modulus 2 = 1
	 * 
	 * @param number1 - the first numeric number.
	 * @param number2 - the second numeric number.
	 * @param variableName - this variable stores the (number1 modulus number2) result.
	 */
	@When("calculate {string} modulus {string} and store result into {string} variable.")
	public void calculate_modulus(String number1, String number2, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(number2);
		Object result = MathUtil.modulus(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to add two numbers. 
	 * 	Example: 10 + 20 = 30
	 * 
	 * @param number1 - the first numeric number.
	 * @param number2 - the second numeric number.
	 * @param variableName - this variable stores the (number1 + number2) result.
	 */
	@When("add {string} and {string} values and store result into {string} variable.")
	public void add_two_numbers(String number1, String number2, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(number2);
		Object result = MathUtil.add(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to subtract one number from other. 
	 * 	Example: 20 - 15 = 5
	 * 
	 * @param subtrahend - the subtrahend value.
	 * @param number1 - the first numeric number.
	 * @param variableName - this variable stores the (number1 - subtrahend) result.
	 */
	@When("subtract {string} from {string} and store result into {string} variable.")
	public void sutract_number(String subtrahend, String number1, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(subtrahend);
		Object result = MathUtil.substract(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to multiply two numbers. 
	 * 	Example: 10 x 20 = 200
	 * 
	 * @param number1 - the first numeric number.
	 * @param number2 - the second numeric number.
	 * @param variableName - this variable stores the (number1 x number2) result.
	 */
	@When("multiply {string} and {string} values and store result into {string} variable.")
	public void multiple_two_numbers(String number1, String number2, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(number2);
		Object result = MathUtil.multiply(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to divide number1 by divisor. 
	 * 	Example: 20 / 5 = 4
	 * 
	 * @param number1 - the first numeric number.
	 * @param divisor - the divisor value.
	 * @param variableName - this variable stores the (number1 / divisor) result.
	 */
	@When("divide {string} by {string} and store result into {string} variable.")
	public void divide_number(String number1, String divisor, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(divisor);
		Object result = MathUtil.divide(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to find minimum of two numbers. 
	 * 	Example: min(20, 40) = 20
	 * 
	 * @param number1 - the first numeric number.
	 * @param number2 - the second numeric number.
	 * @param variableName - this variable stores the Min(number1,  number2) result.
	 */
	@When("find minimum value of {string} and {string} numbers and store result into {string} variable.")
	public void find_minimum_of_two_numbers(String number1, String number2, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(number2);
		Object result = MathUtil.min(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to find maximum of two numbers. 
	 * 	Example: max(20, 40) = 40
	 * 
	 * @param number1 - the first numeric number.
	 * @param number2 - the second numeric number.
	 * @param variableName - this variable stores the Max(number1,  number2) result.
	 */
	@When("find maximum value of {string} and {string} numbers and store result into {string} variable.")
	public void find_maximum_of_two_numbers(String number1, String number2, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue1 = "" + scenarioContext.getParamValueNullAsParamName(number1);
		String numValue2 = "" + scenarioContext.getParamValueNullAsParamName(number2);
		Object result = MathUtil.max(numValue1, numValue2);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to calculate floor value of the specified number. 
	 * 	Example: floor(10.567) = 10
	 * 
	 * @param number - the number.
	 * @param variableName - this variable stores the Floor(number) result.
	 */
	@When("calculate floor value of {string} number and store result into {string} variable.")
	public void calculate_floor(String number, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue = "" + scenarioContext.getParamValueNullAsParamName(number);
		Object result = MathUtil.floor(numValue);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to calculate ceil value of the specified number. 
	 * 	Example: floor(10.567) = 11
	 * 
	 * @param number - the number.
	 * @param variableName - this variable stores the Ceil(number) result.
	 */
	@When("calculate ceil value of {string} number and store result into {string} variable.")
	public void calculate_ceil(String number, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue = "" + scenarioContext.getParamValueNullAsParamName(number);
		Object result = MathUtil.ceil(numValue);
		scenarioContext.addParamValue(variableName, result);
	}
	
	/**
	 * Used to negate the specified number. 
	 * 	Example: negate(10.567) = -10.567
	 * 
	 * @param number - the number.
	 * @param variableName - this variable stores the Ceil(number) result.
	 */
	@When("negate {string} number and store result into {string} variable.")
	public void negate_number(String number, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String numValue = "" + scenarioContext.getParamValueNullAsParamName(number);
		Object result = MathUtil.negate(numValue);
		scenarioContext.addParamValue(variableName, result);
	}
}

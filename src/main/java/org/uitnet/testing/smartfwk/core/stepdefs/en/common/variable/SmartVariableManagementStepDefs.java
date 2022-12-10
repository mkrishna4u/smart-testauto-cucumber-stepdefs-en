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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.variable;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValue;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.DataMatchUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for variable and its value management.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartVariableManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartVariableManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to store null value into the variable.
	 * 
	 * @param variableName - the name of the variable in which the null value will get stored.
	 */
	@When("store null value into {string} variable.")
	public void store_null_value_into_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.addParamValue(variableName, null);		
	}
	
	/**
	 * Store empty list into the variable.
	 * 
	 * @param variableName - the name of the variable in which the empty list will get stored.
	 */
	@When("store empty list into {string} variable.")
	public void store_empty_list_into_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<Object> list = new LinkedList<>();
		scenarioContext.addParamValue(variableName, list);		
	}
	
	/**
	 * Used to print the value / contents of the variable in report file.
	 * 
	 * @param variableName - the name of the variable.
	 */
	@When("print {string} variable.")
	public void print_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.log(variableName);
	}
	
	/**
	 * Used to append the prefix text to each item present in list variable.
	 * 
	 * @param prefix - the prefix text that will be appended to each item present in the list variable.
	 * @param variableName - the name of the list variable.
	 */
	@SuppressWarnings("unchecked")
	@When("append {string} prefix to each value present in {string} list variable.")
	public void append_prefix_to_each_value_in_list_variable(String prefix, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<String> list = (List<String>) scenarioContext.getParamValue(variableName);
		if(list != null) {
			List<String> list2 = new LinkedList<>();
			for(String str : list) {
				list2.add(prefix + str);
			}
			scenarioContext.addParamValue(variableName, list2);
		}
	}
	
	/**
	 * Used to append prefix text to the value present in the specified variable.
	 * Note: It will convert the value of variable into String form and then prepend the prefix text
	 * into the variable value and convert the variable value into String/Text form.
	 * 
	 * @param prefix - the prefix text that will be prepended in the value of specified variable.
	 * @param variableName - the name of the variable.
	 */
	@When("append {string} prefix to the value of {string} variable.")
	public void append_prefix_to_the_value_of_variable(String prefix, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String str = scenarioContext.getParamValueAsString(variableName);
		if(str != null) {
			
			scenarioContext.addParamValue(variableName, prefix + str);
		}
	}
	
	/**
	 * Used to append the suffix text to each item present in list variable.
	 * 
	 * @param suffix - the suffix text that will be appended to each item present in the list variable.
	 * @param variableName - the name of the list variable.
	 */
	@SuppressWarnings("unchecked")
	@When("append {string} suffix to each value present in {string} list variable.")
	public void append_suffix_to_each_value_in_list_variable(String suffix, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<String> list = (List<String>) scenarioContext.getParamValue(variableName);
		if(list != null) {
			List<String> list2 = new LinkedList<>();
			for(String str : list) {
				list2.add(str + suffix);
			}
			scenarioContext.addParamValue(variableName, list2);
		}
	}
	
	/**
	 * Used to append suffix text to the value present in the specified variable.
	 * Note: It will convert the value of variable into String form and then append the suffix text
	 * into the variable value and convert the variable value into String/Text form.
	 * 
	 * @param suffix - the suffix text that will be appended in the value of specified variable.
	 * @param variableName - the name of the variable.
	 */
	@When("append {string} suffix to the value of {string} variable.")
	public void append_suffix_to_the_value_of_variable(String suffix, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String str = scenarioContext.getParamValueAsString(variableName);
		if(str != null) {
			scenarioContext.addParamValue(variableName, str + suffix);
		}
	}
	
	/**
	 * Used to append prefix and suffix text in each item present into list variable.
	 * 
	 * @param prefix - the prefix text to be appended.
	 * @param suffix - the suffix text to be appended.
	 * @param variableName - the name of the variable.
	 */
	@SuppressWarnings("unchecked")
	@When("append prefix={string} and suffix={string} to each value present in {string} list variable.")
	public void append_prefix_and_suffix_to_each_value_in_list_variable(String prefix, String suffix, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<String> list = (List<String>) scenarioContext.getParamValue(variableName);
		if(list != null) {
			List<String> list2 = new LinkedList<>();
			for(String str : list) {
				list2.add(prefix + str + suffix);
			}
			scenarioContext.addParamValue(variableName, list2);
		}
	}
	
	/**
	 * Used to append prefix and suffix text to the value present in the specified variable.
	 * Note: It will convert the value of variable into String form and then append the prefix and suffix text
	 * into the variable value and convert the variable value into String/Text form.
	 * 
	 * @param prefix - the prefix text that will be appended in the value of specified variable.
	 * @param suffix - the suffix text that will be appended in the value of specified variable.
	 * @param variableName - the name of the variable.
	 */
	@When("append prefix={string} and suffix={string} to the value of {string} variable.")
	public void append_prefix_and_suffix_to_the_value_of_variable(String prefix, String suffix, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String str = scenarioContext.getParamValueAsString(variableName);
		if(str != null) {
			scenarioContext.addParamValue(variableName, prefix + str + suffix);
		}
	}
	
	/**
	 * Used to convert the variable value into list using specified delimiter.
	 * Note: It will trim the value (means will remove the leading and trailing 
	 * whitespaces from the value) and store into the list.
	 * 
	 * @param variableName - the name of the variable that value will be converted into list form.
	 * @param delimiter - the delimiter to split the value.
	 * @param newVariableName - the new variable name that stores the converted value.
	 */
	@When("convert {string} variable value into list using delimiter={string} and store into {string} variable.")
	public void convert_variable_value_into_list_using_delimiter_and_store_into_variable(String variableName, String delimiter, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String str = scenarioContext.getParamValueAsString(variableName);
		if(!StringUtil.isEmptyAfterTrim(str)) {
			String[] arr = str.split(delimiter);
			List<String> list = new LinkedList<>();
			for(String elem : arr) {
				list.add(elem.trim());
			}
			scenarioContext.addParamValue(newVariableName, list);
		}
	}
	
	/**
	 * Used to convert the list variable value into plain text separated by the specified delimiter.
	 * And store into new variable.
	 * 
	 * @param variableName - the list variable name used to converts its contents into plain text.
	 * @param separator - the separator used to append all items of the list.
	 * @param newVariableName - the new variable name that stores the converted value.
	 */
	@SuppressWarnings("unchecked")
	@When("convert {string} list variable value into plain text using separator={string} and store into {string} variable.")
	public void convert_list_variable_value_into_plain_text_using_separator_and_store_into_variable(String variableName, String separator, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<String> list = (List<String>) scenarioContext.getParamValue(variableName);
		if(list != null) {
			String str = "";
			for(String elem : list) {
				if("".equals(str)) {
					str = elem;
				} else {
					str = str + separator + elem;
				}
			}
			scenarioContext.addParamValue(newVariableName, str);
		}
	}
	
	/**
	 * Used to convert the list variable value into plain text by appending prefix and suffix to 
	 * each item of the list. The value in plain text are separated by separator.
	 * 
	 * @param variableName - the list variable name.
	 * @param separator - the separated that will be used to append list items.
	 * @param valuePrefix - prefix text that will be prepended to each item of the list.
	 * @param valueSuffix - suffix text that will be appended to each item of the list.
	 * @param newVariableName - the new variable name that stores the converted value.
	 */
	@SuppressWarnings("unchecked")
	@When("convert {string} list variable value into plain text using separator={string}, valuePrefix={string}, valueSuffix={string} and store into {string} variable.")
	public void convert_list_variable_value_into_plain_text_using_separator_valueprefix_valuesuffix_and_store_into_variable(
			String variableName, String separator, 
			String valuePrefix, String valueSuffix, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<String> list = (List<String>) scenarioContext.getParamValue(variableName);
		if(list != null) {
			String str = "";
			for(String elem : list) {
				if("".equals(str)) {
					str = (elem == null) ? elem : valuePrefix + elem + valueSuffix;
				} else {
					str = (elem == null) ? str + separator + elem : str + separator + valuePrefix + elem + valueSuffix;
				}
			}
			scenarioContext.addParamValue(newVariableName, str);
		}
	}
	
	/**
	 * Used to convert the list variable value into plain text by appending prefix and suffix to 
	 * each item of the list. The value in plain text are separated by separator. 
	 * NOTE: If list variable is empty then it will return default value enclosed with provided prefix and suffix.
	 * 
	 * @param variableName - the list variable name.
	 * @param separator - the separated that will be used to append list items.
	 * @param valuePrefix - prefix text that will be prepended to each item of the list.
	 * @param valueSuffix - suffix text that will be appended to each item of the list.
	 * @param defaultValue - if list variable is empty then it will return default value enclosed with provided prefix and suffix.
	 * @param newVariableName - the new variable name that stores the converted value.
	 */
	@SuppressWarnings("unchecked")
	@When("convert {string} list variable value into plain text using separator={string}, valuePrefix={string}, valueSuffix={string}, default={string} and store into {string} variable.")
	public void convert_list_variable_value_into_plain_text_using_separator_valueprefix_valuesuffix_default_and_store_into_variable(String variableName, String separator, 
			String valuePrefix, String valueSuffix, String defaultValue, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<String> list = (List<String>) scenarioContext.getParamValue(variableName);
		if(list != null) {
			String str = "";
			for(String elem : list) {
				if("".equals(str)) {
					str = (elem == null) ? elem : valuePrefix + elem + valueSuffix;
				} else {
					str = (elem == null) ? str + separator + elem : str + separator + valuePrefix + elem + valueSuffix;
				}
			}
			if(StringUtil.isEmptyAfterTrim(str)) {
				list = new LinkedList<>();
				list.add(defaultValue);
				scenarioContext.addParamValue(variableName, list);
				scenarioContext.addParamValue(newVariableName, valuePrefix + defaultValue + valueSuffix);
			} else {
				scenarioContext.addParamValue(newVariableName, str);
			}
			
		}
	}
	
	/**
	 * Used to copy one variable value to new new variable.
	 * 
	 * @param variableName - the name of the source variable.
	 * @param newVariableName - the target variable name where the value will be copied.
	 */
	@SuppressWarnings("unchecked")
	@When("copy {string} variable value to {string} variable.")
	public void copy_variable_value_to_variable(String variableName, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object value = scenarioContext.getParamValue(variableName);
		if(value != null && value instanceof List) {
			List<Object> list = (List<Object>) value;
			List<Object> list2 = new LinkedList<>();
			for(Object elem : list) {
				list2.add(elem);
			}
			scenarioContext.addParamValue(newVariableName, list2);
		} else if(value != null && value instanceof Set) {
			Set<Object> list = (Set<Object>) value;
			Set<Object> list2 = new LinkedHashSet<>();
			for(Object elem : list) {
				list2.add(elem);
			}
			scenarioContext.addParamValue(newVariableName, list2);
		} else if(value != null && value instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) value;
			Map<Object, Object> map2 = new LinkedHashMap<>();
			for(Map.Entry<Object, Object> elem : map.entrySet()) {
				map2.put(elem.getKey(), elem.getValue());
			}
			scenarioContext.addParamValue(newVariableName, map2);
		}  else if(value != null && value.getClass().isArray()) {
			Object[] list = (Object[]) value;
			List<Object> list2 = new LinkedList<>();
			for(Object elem : list) {
				list2.add(elem);
			}
			scenarioContext.addParamValue(newVariableName, list2);
		} else {
			scenarioContext.addParamValue(newVariableName, value);
		}
	}
	
	/**
	 * Used to verify the variable value with expected value using the specified operator.
	 * 
	 * @param variableName1 - the name of the variable.
	 * @param operator - the operator used to verify the variable value with expected value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param expectedValue - the expected value. The syntax is a JSON syntax:
	 * 		{v: <value-here>, valueType: "string"}
	 *    For valueType, refer {@link ParamValueType}
	 *    Or we can directly specify value like:
	 *    	"test value"
	 * 
	 */
	@Then("verify valueOf\\({string}) variable {string} {string}.")
	public void verify_valueof_variable1_op_expected_value(String variableName1, String operator, String expectedValue) { 
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object avalue = scenarioContext.getParamValue(variableName1);
		Object evalue = expectedValue;
		ValueMatchOperator oper = ValueMatchOperator.valueOf2(operator);
		
		ParamValue pv1 = ObjectUtil.convertObjectToParamValue(avalue);
		ParamValue pv2 = ObjectUtil.convertObjectToParamValue(evalue);
		
		ObjectUtil.fixValueTypesInParamValueObjects(pv1, oper, pv2);
		
		ParamPath pPath = new ParamPath(variableName1, pv1.getValueTypeAsStr());
		
		ExpectedInfo eInfo = new ExpectedInfo();
		eInfo.setEv(pv2.getV());
		eInfo.setValueType(pv2.getValueType().getType());
		
		ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, pv1.getV(), operator, eInfo);
	}
	
	/**
	 * Used to verify variableName1 variable value with variableName2 value using specified operator.
	 * 
	 * @param variableName1 - the name of the variable.
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param variableName2 - the expected variable name that contains the value.
	 */
	@Then("verify valueOf\\({string}) variable {string} valueOf\\({string}) variable.")
	public void verify_valueof_variable1_op_valueof_variable2(String variableName1, String operator, String variableName2) { 
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object avalue = scenarioContext.getParamValue(variableName1);
		Object evalue = scenarioContext.getParamValue(variableName2);
		ValueMatchOperator oper = ValueMatchOperator.valueOf2(operator);
		
		ParamValue pv1 = ObjectUtil.convertObjectToParamValue(avalue);
		ParamValue pv2 = ObjectUtil.convertObjectToParamValue(evalue);
		
		ObjectUtil.fixValueTypesInParamValueObjects(pv1, oper, pv2);
		
		ParamPath pPath = new ParamPath(variableName1, pv1.getValueTypeAsStr());
		
		ExpectedInfo eInfo = new ExpectedInfo();
		eInfo.setEv(pv2.getV());
		eInfo.setValueType(pv2.getValueType().getType());
		
		ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, pv1.getV(), operator, eInfo);
	}
	
	/**
	 * Used to verify variableName1 variable value with variableName2 value using specified operator.
	 * In this step we can add some meaningful information.
	 * 
	 * @param variableName1 - the name of the variable.
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param variableName2 - the expected variable name that contains the value.
	 * @param extraInfo - extra info to provide to justification. 
	 */
	@Then("verify valueOf\\({string}) variable {string} valueOf\\({string}) variable to {string}.")
	public void verify_valueof_variable1_op_valueof_variable2_extrainfo(String variableName1, String operator, String variableName2, String extraInfo) { 
		verify_valueof_variable1_op_valueof_variable2(variableName1, operator, variableName2);
	}
	
	/**
	 * Used to verify the value of variableName1 variable value with variableName2 value using specified operator.
	 * In this step we can specify the TextMatchMechanism in case if the variable is list type. So system will
	 * match each item of the list with other variable value as per the specified TextMatchMechanism.
	 * 
	 * @param variableName1 - the name of the variable.
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param variableName2 - the expected variable name that contains the value.
	 * @param textMatchMechanism - the text match mechanism used to verify the value in list items. 
	 * 		For text match mechanism, refer {@link TextMatchMechanism} class.
	 */
	@Then("verify valueOf\\({string}) variable {string} valueOf\\({string}) variable [TextMatchMechanism={string}].")
	public void verify_valueof_variable1_op_valueof_variable2_textmatchmechanism(String variableName1, String operator, String variableName2, String textMatchMechanism) { 
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object avalue = scenarioContext.getParamValue(variableName1);
		Object evalue = scenarioContext.getParamValue(variableName2);
		ValueMatchOperator oper = ValueMatchOperator.valueOf2(operator);
				
		ParamValue pv1 = ObjectUtil.convertObjectToParamValue(avalue);
		ParamValue pv2 = ObjectUtil.convertObjectToParamValue(evalue);
		
		ObjectUtil.fixValueTypesInParamValueObjects(pv1, oper, pv2);
		
		ParamPath pPath = new ParamPath(variableName1, pv1.getValueTypeAsStr());
		
		ExpectedInfo eInfo = new ExpectedInfo();
		eInfo.setEv(pv2.getV());
		eInfo.setValueType(pv2.getValueType().getType());
		eInfo.setTextMatchMechanism(textMatchMechanism);
		
		ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, pv1.getV(), operator, eInfo);
	}
	
	/**
	 * Used to verify the value of variableName1 variable value with variableName2 value using specified operator.
	 * In this step we can specify the TextMatchMechanism in case if the variable is list type. So system will
	 * match each item of the list with other variable value as per the specified TextMatchMechanism.
	 * NOTE: In this step, we can also be able to specify extra meaningful information to make scenario meaningful.
	 * 
	 * @param variableName1 - the name of the variable.
	 * @param operator - the operator used to verify the variable value with expected variable value.
	 * 		For more details on operator, refer {@link ValueMatchOperator}
	 * @param variableName2 - the expected variable name that contains the value.
	 * @param textMatchMechanism - the text match mechanism used to verify the value in list items. 
	 * 		For text match mechanism, refer {@link TextMatchMechanism} class.
	 * @param extraInfo - extra info to provide to justify the 
	 */
	@Then("verify valueOf\\({string}) variable {string} valueOf\\({string}) variable [TextMatchMechanism={string}] to {string}.")
	public void verify_valueof_variable1_op_valueof_variable2_textmatchmechanism_extrainfo(String variableName1, String operator, String variableName2,
			String textMatchMechanism, String extraInfo) { 
		verify_valueof_variable1_op_valueof_variable2_textmatchmechanism(variableName1, operator, variableName2, textMatchMechanism);
	}
	
	/**
	 * Used to verify each element of the list variable contains the expected text as per the text match mechanism specified.
	 * 
	 * @param listVariableName - the name of the list variable that contains elements.
	 * @param expectedText - the expected text that can be used to match with each element of the list variable.
	 * @param textMatchMechanism  - the text match mechanism used to verify the value in list items. 
	 * 		For text match mechanism, refer {@link TextMatchMechanism} class.
	 */
	@SuppressWarnings("unchecked")
	@Then("verify each element of {string} list variable matches with {string} text where TextMatchMechanism={string}.")
	public void verify_each_element_of_list_variable_matches_with_text_where_text_match_mechanism(
			String listVariableName, String expectedText, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<Object> list = (List<Object>) scenarioContext.getParamValue(listVariableName);		
		expectedText = scenarioContext.applyParamsValueOnText(expectedText);
		TextMatchMechanism textMatchMechanismEnum = TextMatchMechanism.valueOf2(textMatchMechanism);
		
		if(list != null && !list.isEmpty()) {
			for(Object actualValue : list) {
				DataMatchUtil.validateTextValue(actualValue == null ? null : String.valueOf(actualValue), expectedText, textMatchMechanismEnum);
			}
		}
	}
	
	/**
	 * Calculates the length or size of the information present in variable and stores the calculated value into new variable.
	 * Note1: In case of List, Map, Set, Array variable: It returns count of number of elements present in that collection.
	 * Note2: In case of String, Integer, Long, Double, Float variable value: It returns the number of characters present.
	 * 
	 * @param variableName - this is an input variable that contains value(s).
	 * @param newVariableName - this stores the length or size of the info present in variableName.
	 */
	@SuppressWarnings("unchecked")
	@Then("get length/size of {string} variable and store into {string} variable.")
	public void get_element_count_of_list_variable_and_store_into_variable(
			String variableName, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object value = scenarioContext.getParamValue(variableName);
		
		if(value != null && value instanceof List) {
			List<Object> list = (List<Object>) value;
			scenarioContext.addParamValue(newVariableName, list == null ? 0 : list.size());
		} else if(value != null && value instanceof Set) {
			Set<Object> list = (Set<Object>) value;
			scenarioContext.addParamValue(newVariableName, list == null ? 0 : list.size());
		} else if(value != null && value instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) value;
			scenarioContext.addParamValue(newVariableName, map == null ? 0 : map.size());
		}  else if(value != null && value.getClass().isArray()) {
			Object[] list = (Object[]) value;
			scenarioContext.addParamValue(newVariableName, list == null ? 0 : list.length);
		} else {
			scenarioContext.addParamValue(newVariableName, value == null ? 0 : ("" + value).length());
		}
	}
	
	/**
	 * Used to split the value present in the specified variable using the specified separator and stores the nth index value into new variable.
	 * In case of List, Array or Set variable, it will split each item and store the nth index into the new list. 
	 * In case of Map variable, it will split the value of each key and will store the nth index into the new map against the same key.
	 *  
	 * @param variableName - could be any type like String, Integer, List, Array, Map or Set
	 * @param separator - used to separate the values.
	 * @param nthIndex - index starts with 0. So 0th index means first value from split.
	 * @param newVariableName - the variable name that will store the converted information.
	 * @param shouldTrim - if yes then after split leading and trailing whitespaces will get removed.
	 * 		valid values: yes, no
	 */
	@SuppressWarnings("unchecked")
	@Then("split valueOf\\({string}) variable using {string} separator and store index\\({int}) into {string} variable [ShouldTrim={string}].")
	public void split_valueof_variable_using_separator_and_store_index_into_variable(String variableName, String separator, int nthIndex,
			String newVariableName, String shouldTrim) { 
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object value = scenarioContext.getParamValue(variableName);
		if(shouldTrim != null && (shouldTrim.equalsIgnoreCase("yes") || shouldTrim.equalsIgnoreCase("true"))) {
			shouldTrim = "yes";
		} else {
			shouldTrim = "no";
		}
		if(value != null && value instanceof List) {
			List<Object> list = (List<Object>) value;
			List<Object> list2 = new LinkedList<>();
			for(Object elem : list) {
				if(elem != null) {
					String[] arr = ("" + elem).split(separator);
					if(arr.length > nthIndex) {
						list2.add(shouldTrim.equals("yes") ? arr[nthIndex].trim() : arr[nthIndex]);
					}
				} else {
					list2.add(elem);
				}
			}
			scenarioContext.addParamValue(newVariableName, list2);
		} else if(value != null && value instanceof Set) {
			Set<Object> list = (Set<Object>) value;
			Set<Object> list2 = new LinkedHashSet<>();
			for(Object elem : list) {
				if(elem != null) {
					String[] arr = ("" + elem).split(separator);
					if(arr.length > nthIndex) {
						list2.add(shouldTrim.equals("yes") ? arr[nthIndex].trim() : arr[nthIndex]);
					}
				} else {
					list2.add(elem);
				}
			}
			scenarioContext.addParamValue(newVariableName, list2);
		} else if(value != null && value instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) value;
			Map<Object, Object> map2 = new LinkedHashMap<>();
			for(Map.Entry<Object, Object> elem : map.entrySet()) {
				if(elem.getValue() != null) {
					String[] arr = ("" + elem.getValue()).split(separator);
					if(arr.length > nthIndex) {
						map2.put(elem.getKey(), shouldTrim.equals("yes") ? arr[nthIndex].trim() : arr[nthIndex]);
					}
				} else {
					map2.put(elem.getKey(), elem.getValue());
				}
				
			}
			for(Map.Entry<Object, Object> elem : map.entrySet()) {
				map2.put(elem.getKey(), elem.getValue());
			}
			scenarioContext.addParamValue(newVariableName, map2);
		}  else if(value != null && value.getClass().isArray()) {
			Object[] list = (Object[]) value;
			List<Object> list2 = new LinkedList<>();
			for(Object elem : list) {
				if(elem != null) {
					String[] arr = ("" + elem).split(separator);
					if(arr.length > nthIndex) {
						list2.add(shouldTrim.equals("yes") ? arr[nthIndex].trim() : arr[nthIndex]);
					}
				} else {
					list2.add(elem);
				}
			}
			scenarioContext.addParamValue(newVariableName, list2);
		} else {
			if(value != null) {
				String[] arr = ("" + value).split(separator);
				if(arr.length > nthIndex) {
					scenarioContext.addParamValue(newVariableName, shouldTrim.equals("yes") ? arr[nthIndex].trim() : arr[nthIndex]);
				}
			} else {
				scenarioContext.addParamValue(newVariableName, value);
			}
		}
	}
	
	/**
	 * Used to apply the existing variables value into the plain text data stored in inputVariableName.
	 * 
	 * @param inputVariableName - the variable name that contains input data.
	 * @param newVariableName - the variable that stored the updated data.
	 */
	@Then("apply existing variables value on the plain text data present in {string} variable and store into {string} variable.")
	public void apply_existing_variables_value_on_plain_text_data(String inputVariableName, String newVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String inputData = scenarioContext.getParamValueAsString(inputVariableName);
		inputData = scenarioContext.applyParamsValueOnText(inputData);
		scenarioContext.addParamValue(newVariableName, inputData);
	}
	
	/**
	 * Used to set the project root directory into the variable.
	 * 
	 * @param variableName - the name of the variable that stores the project root directory.
	 */
	@Then("get project root directory and store into {string} variable.")
	public void get_project_root_directory_and_store_into_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String projectRootDir = Locations.getProjectRootDir();
		scenarioContext.addParamValue(variableName, projectRootDir);
	}
}
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
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for variable management.
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
	
	@When("store null value into {string} variable.")
	public void store_null_value_into_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.addParamValue(variableName, null);		
	}
	
	@When("store empty list into {string} variable.")
	public void store_empty_list_into_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<Object> list = new LinkedList<>();
		scenarioContext.addParamValue(variableName, list);		
	}
	
	@When("print {string} variable.")
	public void print_variable(String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		scenarioContext.log(variableName);
	}
	
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
	
	@SuppressWarnings("unchecked")
	@When("convert {string} list variable value into plain text using delimiter={string} and store into {string} variable.")
	public void convert_list_variable_value_into_plain_text_using_delimiter_and_store_into_variable(String variableName, String delimiter, String newVariableName) {
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
					str = str + delimiter + elem;
				}
			}
			scenarioContext.addParamValue(newVariableName, str);
		}
	}
	
	@SuppressWarnings("unchecked")
	@When("convert {string} list variable value into plain text using delimiter={string}, valuePrefix={string}, valueSuffix={string} and store into {string} variable.")
	public void convert_list_variable_value_into_plain_text_using_delimiter_valueprefix_valuesuffix_and_store_into_variable(
			String variableName, String delimiter, 
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
					str = (elem == null) ? str + delimiter + elem : str + delimiter + valuePrefix + elem + valueSuffix;
				}
			}
			scenarioContext.addParamValue(newVariableName, str);
		}
	}
	
	/**
	 * 
	 * @param variableName
	 * @param delimiter
	 * @param valuePrefix
	 * @param valueSuffix
	 * @param defaultValue - if list variable is empty then it will return default value enclosed with provided prefix and suffix.
	 */
	@SuppressWarnings("unchecked")
	@When("convert {string} list variable value into plain text using delimiter={string}, valuePrefix={string}, valueSuffix={string}, default={string} and store into {string} variable.")
	public void convert_list_variable_value_into_plain_text_using_delimiter_valueprefix_valuesuffix_default_and_store_into_variable(String variableName, String delimiter, 
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
					str = (elem == null) ? str + delimiter + elem : str + delimiter + valuePrefix + elem + valueSuffix;
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
	 * Used to verify the variable value with expected value.
	 * 
	 * @param variableName1
	 * @param operator
	 * @param expectedValue
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
	 * Matches variableName1 variable value against variableName2 value using operator.
	 * @param variableName1
	 * @param operator - for operator please refer @see ValueMatchOperator class.
	 * @param variableName2
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
	 * Matches variableName1 variable value against variableName2 value using operator.
	 * @param variableName1
	 * @param operator - for operator please refer @see ValueMatchOperator class.
	 * @param variableName2
	 * @param extraInfo - extra info to provide to justify the 
	 */
	@Then("verify valueOf\\({string}) variable {string} valueOf\\({string}) variable to {string}.")
	public void verify_valueof_variable1_op_valueof_variable2_extrainfo(String variableName1, String operator, String variableName2, String extraInfo) { 
		verify_valueof_variable1_op_valueof_variable2(variableName1, operator, variableName2);
	}
	
	/**
	 * Matches variableName1 variable value against variableName2 value using operator.
	 * @param variableName1
	 * @param operator - for operator please refer @see ValueMatchOperator class.
	 * @param variableName2
	 * @param textMatchMechanism - for operator please refer @see TextMatchMechanism class.
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
	 * Matches variableName1 variable value against variableName2 value using operator.
	 * @param variableName1
	 * @param operator - for operator please refer @see ValueMatchOperator class.
	 * @param variableName2
	 * @param textMatchMechanism - for operator please refer @see TextMatchMechanism class.
	 * @param extraInfo - extra info to provide to justify the 
	 */
	@Then("verify valueOf\\({string}) variable {string} valueOf\\({string}) variable [TextMatchMechanism={string}] to {string}.")
	public void verify_valueof_variable1_op_valueof_variable2_textmatchmechanism_extrainfo(String variableName1, String operator, String variableName2,
			String textMatchMechanism, String extraInfo) { 
		verify_valueof_variable1_op_valueof_variable2_textmatchmechanism(variableName1, operator, variableName2, textMatchMechanism);
	}
	
	/**
	 * 
	 * @param variableName - could be any type like String, Integer, List, Array, Map or Set
	 * @param separator
	 * @param nthIndex - index starts with 0. So 0th index means first value from split.
	 * @param newVariableName
	 * @param shouldTrim - if yes then it leading and trailing whitespaces will get removed (after split).
	 */
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
}
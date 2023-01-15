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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.core.validator.MethodInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to set and clear conditions to run step conditionally.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartMethodOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartMethodOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to call any method of any class.
	 * 
	 * @param operationName - the meaningful name of the operation.
	 * @param variableName - the variable name in which the output of the method will be stored.
	 * @param method - the method can be specified in JSON format as given below:
	 * <blockquote><pre>
	 *   {className: "qualified-class-name", methodName: "method-name", argsType: [int, List, String],
	 *   	argsValue: [1, [2,4,5], "text here"], isStatic: true/false}
	 *   
	 *   NOTE-1: Method arguments value type could be as specified in {@link ParamValueType} link.
	 *           Argument's value must match with the specified argument type of the method. argsType is 
	 *           not mandatory when you do not have duplicate method names.
	 *   NOTE-2: No argument default class constructor must be present.
	 * </pre></blockquote> 
	 */
	@When("call the following method to {string} and store its return value into {string} variable:")
	public void call_the_following_method_to_and_store_its_return_value_into_variable(String operationName, String variableName, DocString method) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String methodInfoAsStr = method.getContent().trim();
		methodInfoAsStr = scenarioContext.applyParamsValueOnText(methodInfoAsStr);
		MethodInfo methodInfo = JsonYamlUtil.parseMethodInfo(methodInfoAsStr);
		
		try {
			Class<?> clazz = Class.forName(methodInfo.getClassName());
			Constructor<?> constructor = methodInfo.getIsStatic() ? null : ObjectUtil.findClassConstructor(clazz, null);
			Object object = methodInfo.getIsStatic() ? null : (constructor == null ? null : constructor.newInstance());
			
			Method m = null;
							
			if(methodInfo.getArgsType() == null || methodInfo.getArgsType().size() == 0) {
				m = ObjectUtil.findClassMethod(clazz, methodInfo.getMethodName(), methodInfo.getArgsValue().size());
			} else {
				m = ObjectUtil.findClassMethod(clazz, methodInfo.getMethodName(), 
						methodInfo.getArgsType().toArray(new String[methodInfo.getArgsType().size()]));
			}
			
			if(StringUtil.isEmptyAfterTrim(variableName)) {
				if(methodInfo.getArgsValue().size() > 0) {
					Object[] valueArr = methodInfo.getArgsValue().toArray(new Object[methodInfo.getArgsValue().size()]);
					ObjectUtil.invokeMethod(object, m, valueArr);
				} else {
					m.invoke(object);
				}
			} else {
				Object[] valueArr = methodInfo.getArgsValue().toArray(new Object[methodInfo.getArgsValue().size()]);
					
				Object returnV = methodInfo.getArgsValue().size() > 0 ? 
						ObjectUtil.invokeMethod(object, m, valueArr) : m.invoke(object);
				scenarioContext.addParamValue(variableName, returnV);
			}
		} catch(Exception e) {
			Assert.fail("Failed to execute method.", e);
		}
	}
	
	/**
	 * Used to call any method of any class. This step is for when you do not need return value of the method.
	 * 
	 * @param operationName - the meaningful name of the operation.
	 * @param method - the method can be specified in JSON format as given below:
	 * <blockquote><pre>
	 *   {className: "qualified-class-name", methodName: "method-name", argsType: [int.class, List.class, String.class],
	 *   	argsValue: [1, [2,4,5], "text here"], isStatic: true/false}
	 *   
	 *   For more details on method json params, refer {@link MethodInfo} class.
	 *   NOTE-1: Method arguments value type could be as specified in {@link ParamValueType} link.
	 *           Argument's value must match with the specified argument type of the method. argsType is 
	 *           not mandatory when you do not have duplicate method names.
	 *   NOTE-2: Default constructor (with No argument) in class must be present.
	 * </pre></blockquote> 
	 */
	@When("call the following method to {string}:")
	public void call_method_to(String operationName, DocString method) {
		call_the_following_method_to_and_store_its_return_value_into_variable(operationName, null, method);
	}

}

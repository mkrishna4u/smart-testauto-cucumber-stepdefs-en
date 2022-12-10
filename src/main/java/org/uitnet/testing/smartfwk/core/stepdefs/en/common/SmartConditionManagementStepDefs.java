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

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValue;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;

import io.cucumber.java.en.When;

/**
 * Used to set and clear conditions to run step selectively.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartConditionManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartConditionManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to set condition or start the conditional block, so that the below steps can run conditionally until the conditional block for this named condition is not end by the Step given below:
	 * 
	 *   And condition="condition-name"-End.
	 * 
	 * @param conditionName - the meaningful name of the condition.
	 * @param conditionLeftValue - the left value before the operator. You can also specify variable name.
	 * @param operator - the operator that can be used to match two values. For more details on operators please refer
	 * 		{@link ValueMatchOperator} class.
	 * @param conditionRightValue - the right value after the operator. You can also specify variable name.
	 */
	@When("condition={string}-Start> if [{string} {string} {string}] condition is true then execute the steps below.")
	public void set_condition(String conditionName, String conditionLeftValue, String operator, String conditionRightValue) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object avalue = scenarioContext.getParamValueNullAsParamName(conditionLeftValue);
		Object evalue = scenarioContext.getParamValueNullAsParamName(conditionRightValue);
		ValueMatchOperator oper = ValueMatchOperator.valueOf2(operator);

		ParamValue pv1 = ObjectUtil.convertObjectToParamValue(avalue);
		ParamValue pv2 = ObjectUtil.convertObjectToParamValue(evalue);

		ObjectUtil.fixValueTypesInParamValueObjects(pv1, oper, pv2);

		ParamPath pPath = new ParamPath(conditionLeftValue, pv1.getValueTypeAsStr());

		ExpectedInfo eInfo = new ExpectedInfo();
		eInfo.setEv(pv2.getV());
		eInfo.setValueType(pv2.getValueType().getType());

		try {
			ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, pv1.getV(), operator, eInfo);
			this.scenarioContext.setCondition(conditionName, true);
		} catch (Throwable th) {
			this.scenarioContext.setCondition(conditionName, false);
		}
	}
	
	/**
	 * Used to set condition or start the conditional block, so that the below steps can run conditionally until the conditional block for this named condition is not end by the Step given below:
	 * 
	 *   And condition="condition-name"-End.
	 * 
	 * @param conditionName - the meaningful name of the condition.
	 * @param conditionLeftValue - the left value before the operator. You can also specify variable name.
	 * @param operator - the operator that can be used to match two values. For more details on operators please refer
	 * 		{@link ValueMatchOperator} class.
	 * @param conditionRightValue - the right value after the operator. You can also specify variable name.
	 * @param textMatchMechanism - the text match mechanism that will be used to match the conditionRightValue with conditionLeftValue. 
	 * 		For more detail on the text match mechanism please refer {@link TextMatchMechanism}.
	 */
	@When("condition={string}-Start> if [{string} {string} {string} and TextMatchCondition={string}] condition is true then execute the steps below.")
	public void set_condition2(String conditionName, String conditionLeftValue, String operator, String conditionRightValue, String textMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Object avalue = scenarioContext.getParamValueNullAsParamName(conditionLeftValue);
		Object evalue = scenarioContext.getParamValueNullAsParamName(conditionRightValue);
		ValueMatchOperator oper = ValueMatchOperator.valueOf2(operator);

		ParamValue pv1 = ObjectUtil.convertObjectToParamValue(avalue);
		ParamValue pv2 = ObjectUtil.convertObjectToParamValue(evalue);

		ObjectUtil.fixValueTypesInParamValueObjects(pv1, oper, pv2);

		ParamPath pPath = new ParamPath(conditionLeftValue, pv1.getValueTypeAsStr());

		ExpectedInfo eInfo = new ExpectedInfo();
		eInfo.setEv(pv2.getV());
		eInfo.setValueType(pv2.getValueType().getType());
		eInfo.setTextMatchMechanism(textMatchMechanism);

		try {
			ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, pv1.getV(), operator, eInfo);
			this.scenarioContext.setCondition(conditionName, true);
		} catch (Throwable th) {
			this.scenarioContext.setCondition(conditionName, false);
		}
	}

	/**
	 * This step is used to end the named condition.
	 * 
	 * @param conditionName - the name of the condition that need to be end.
	 */
	@When("condition={string}-End.")
	public void unset_condition(String conditionName) {
		if(scenarioContext.isConditionSet(conditionName)) {
			this.scenarioContext.unsetCondition(conditionName);
		} else {
			if(!scenarioContext.isLastConditionSetToTrue()) {
				scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
				return;
			}
		}
	}

}

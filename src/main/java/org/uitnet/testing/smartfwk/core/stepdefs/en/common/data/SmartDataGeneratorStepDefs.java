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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.data;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.data.builder.TestDataBuilder;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions to generate test data automatically.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartDataGeneratorStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartDataGeneratorStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to generate the textual data based on the inputs provided and store the generated data into the variable. 
	 * 
	 * @param fieldName - field name or any meaningful name for that we would like to generate the data.
	 * @param variableName - used to store the generated data.
	 * @param jsonInput - the doc string that contains JSON data as input to generate the text data. 
	 *                     Syntax for this JSON input is given below:
	 * <blockquote><pre>
	 *   {length: 80, maxWordLength: 20, includeAlphabetsLower: true, includeAlphabetsUpper: true, 
	 *   alphabetsLower: "abcdefghijklmnopqrstuvwxyz", alphabetsUpper: true, 
	 *   alphabetsUpper: "ABCDEFGHIJKLMNOPQRSTUVWXYZ", includeNumbers: true, 
	 *   numbers: "1234567890", includeSpecialCharacters: true, 
	 *   specialCharacters: "`~!@#$%^&*()_-+={[]}\\|;:'\",<.>/?", includeNewLine: true, 
	 *   includeWhiteSpaces: true, includeLeadingWhiteSpace: true, includeTrailingWhiteSpace: true}
	 * </pre></blockquote>
	 * 
	 */
	@When("auto generate {string} data based on the JSON input given below and store into {string} variable:")
	public void auto_generate_data(String fieldName, String variableName, DocString jsonInput) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String jsonInputAsStr = jsonInput.getContent();
		jsonInputAsStr = scenarioContext.applyParamsValueOnText(jsonInputAsStr);
		
		JsonDocumentReader r = new JsonDocumentReader(jsonInputAsStr, false);
		TestDataBuilder autoValueInputs = r.readValueAsObject("$", TestDataBuilder.class);
		
		String genData = autoValueInputs.build();
		scenarioContext.addParamValue(variableName, genData);
	}
}

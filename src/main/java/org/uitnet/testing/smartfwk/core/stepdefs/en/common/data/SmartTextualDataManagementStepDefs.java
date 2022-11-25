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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.data;

import java.io.File;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.validator.FileContentsValidator;

import io.cucumber.java.en.When;

/**
 * Used to read different text files data.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartTextualDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartTextualDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	@When("read {string} file data as text and store into {string} variable.")
	public void read_file_data_as_text_and_store_into_variable(String relativeFilePath, String variableName) {
		FileContentsValidator fileContentValidator = new FileContentsValidator(
				Locations.getProjectRootDir() + File.separator + relativeFilePath, false);
		scenarioContext.addParamValue(variableName, fileContentValidator.extractFileContents());
	}
	
	@When("read {string} file data as text and store into {string} variable [OCRLanguage={string}, ShouldPrintOnConsole={string}].")
	public void read_file_data_as_text_and_store_into_variable(String relativeFilePath, String variableName, String ocrLanguage, String shouldPrintOnConsole) {
		FileContentsValidator fileContentValidator = new FileContentsValidator(
				Locations.getProjectRootDir() + File.separator + relativeFilePath,
				("yes".equalsIgnoreCase(shouldPrintOnConsole) || "true".equalsIgnoreCase(shouldPrintOnConsole)) ? true : false
				, ocrLanguage);
		scenarioContext.addParamValue(variableName, fileContentValidator.extractFileContents());
	}

}

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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.local_machine;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.local_machine.LocalMachineFileSystem;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.validator.DownloadedFileValidator;
import org.uitnet.testing.smartfwk.validator.FileContentsValidator;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartLocalFileManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartLocalFileManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	@When("remove {string} file from the local downloads directory.")
	public void remove_file_from_the_local_downloads_directory(String fileName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		LocalMachineFileSystem.deleteFiles(Locations.getProjectRootDir() + "/test-results/downloads", TextMatchMechanism.exactMatchWithExpectedValue, fileName);
	}
	
	@Then("verify that the {string} file with extension {string} is downloaded [MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_file_with_extension_is_downloaded_maxtimetowaitinseconds(String fileName, String fileExtension, Integer maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		DownloadedFileValidator.validateBrowserFileDownloaded(fileName, fileExtension, false, maxTimeToWaitInSeconds);
	}
	
	@Then("verify that the {string} file with extension {string} is present in downloads directory [MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_file_with_extension_is_downloaded_maxtimetowaitinseconds_1(String fileName, String fileExtension, Integer maxTimeToWaitInSeconds) {
		verify_that_the_file_with_extension_is_downloaded_maxtimetowaitinseconds(fileName, fileExtension, maxTimeToWaitInSeconds);
	}
	
	/**
	 * Used to validate the downloaded file contents.
	 * 
	 * @param fileNameWithExtension - relative file path to process.
	 * @param keywordDelimiter - keyword delimiter to to identify multiple keywords in keywords text.
	 * @param inOrder - valid values Yes, No. If yes then it will search keywords one after one. If No then it will search element in document but sequencing will not matter.
	 * @param shouldPrint - Will print the contents of the file on console.
	 * @param keywords - multiple keywords are separated by KeywordDelimiter.
	 */
	@Then("verify that the downloaded file {string} contains the following keywords [KeywordDelimiter={string}, InOrder={string}, ShouldPrint={string}]:")
	public void verify_that_the_downloaded_file_contains_the_following_text(String fileNameWithExtension, String keywordDelimiter, String inOrder, String shouldPrint, DocString keywords) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		FileContentsValidator fcValidator = new FileContentsValidator(Locations.getProjectRootDir() + "/test-results/downloads/" + fileNameWithExtension, 
	    		"Yes".equalsIgnoreCase(shouldPrint));	    
	    
	    String txt = scenarioContext.applyParamsValueOnText(keywords.getContent());
	    String[] arr = txt.split(keywordDelimiter);
	    for(int i = 0; i < arr.length; i++) {
	    	arr[i] = StringUtil.trimNullAsEmpty(arr[i]);
	    }
	    
	    if("Yes".equalsIgnoreCase(inOrder)) {
	    	fcValidator.validateAllKeywordsPresentInOrder(arr);
	    } else {
	    	fcValidator.validateAllKeywordsPresent(arr);
	    }
	}
}

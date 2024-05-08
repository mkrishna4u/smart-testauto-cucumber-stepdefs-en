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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.local_machine;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.common.command.SmartCommandExecuter;
import org.uitnet.testing.smartfwk.common.command.SyncCommandResult;
import org.uitnet.testing.smartfwk.local_machine.LocalMachineFileSystem;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.validator.DownloadedFileValidator;
import org.uitnet.testing.smartfwk.validator.FileContentsValidator;

import com.jayway.jsonpath.DocumentContext;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to local file management like file name validation, file contents validation etc.  
 * 
 * @author Madhav Krishna
 *
 */
public class SmartLocalFileManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartLocalFileManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to permanently delete specified file from the local downloads directory (test-results/downloads).
	 * 
	 * @param fileName - the relative file path (w.r.t. downloads/ directory) of the file to be deleted.
	 */
	@When("delete {string} file from the local downloads directory.")
	public void delete_file_from_the_local_downloads_directory(String fileName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileName = scenarioContext.applyParamsValueOnText(fileName);
		
		LocalMachineFileSystem.deleteFiles(Locations.getProjectRootDir() + "/test-results/downloads", TextMatchMechanism.exactMatchWithExpectedValue, fileName);
	}
	
	/**
	 * Used to permanently delete specified file from the specified directory on the local filesystem.
	 * 
	 * @param fileName - the relative file path (w.r.t. downloads/ directory) of the file to be deleted.
	 * @param localDirectory - the absolute path of the local directory from where the file need to be deleted.
	 */
	@When("delete {string} file from the following local directory:")
	public void delete_file_from_the_following_local_directory(String fileName, DocString localDirectory) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileName = scenarioContext.applyParamsValueOnText(fileName);
		String localDirectoryPath = scenarioContext.applyParamsValueOnText(localDirectory.getContent().trim());
		
		LocalMachineFileSystem.deleteFiles(localDirectoryPath, TextMatchMechanism.exactMatchWithExpectedValue, fileName);
	}
	
	/**
	 * Used to permanently delete files from the test-results/downloads directory based on the specified condition like FileNamePrefix, FileExtension and FileNameMatchMechanism.
	 * 
	 * @param fileNamePrefix - the file name prefix
	 * @param fileExtension - the file extension. like .xlsx, .csv etc.
	 * @param fileNameMatchMechanism - file name match mechanism to match with fileNamePrefix.
	 * 		For more details on fileNameMatchMechanism please refer {@link TextMatchMechanism}.
	 */
	@When("delete files from the local downloads directory where [FileNamePrefix:{string}, FileExtension:{string}, FileNameMatchMechanism:{string}].")
	public void delete_files_from_the_local_downloads_directory_where(String fileNamePrefix, String fileExtension, String fileNameMatchMechanism) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileNamePrefix = scenarioContext.applyParamsValueOnText(fileNamePrefix);
		
		LocalMachineFileSystem.deleteFiles(Locations.getProjectRootDir() + "/test-results/downloads", 
				TextMatchMechanism.valueOf2(fileNameMatchMechanism), fileNamePrefix, fileExtension);
	}
	
	/**
	 * Used to permanently delete files from the specified local directory based on the specified condition like FileNamePrefix, FileExtension and FileNameMatchMechanism.
	 * 
	 * @param fileNamePrefix - the file name prefix
	 * @param fileExtension - the file extension. like .xlsx, .csv etc.
	 * @param fileNameMatchMechanism - file name match mechanism to match with fileNamePrefix.
	 * 		For more details on fileNameMatchMechanism please refer {@link TextMatchMechanism}.
	 * @param localDirectory - the absolute path of the local directory from where the files need to be deleted.
	 */
	@When("delete files from the following local directory where [FileNamePrefix:{string}, FileExtension:{string}, FileNameMatchMechanism:{string}]:")
	public void delete_files_from_the_following_local_directory_where(String fileNamePrefix, String fileExtension, String fileNameMatchMechanism, DocString localDirectory) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileNamePrefix = scenarioContext.applyParamsValueOnText(fileNamePrefix);
		String localDirectoryPath = scenarioContext.applyParamsValueOnText(localDirectory.getContent().trim());
		
		LocalMachineFileSystem.deleteFiles(localDirectoryPath, 
				TextMatchMechanism.valueOf2(fileNameMatchMechanism), fileNamePrefix, fileExtension);
	}
	
	/**
	 * Used to find the latest file from the test-results/downloads directory based on the file matching criteria like FileNamePrefix, FileExtension and FileNameMatchingMechanism.
	 * 
	 * @param fileNamePrefix - the file name prefix
	 * @param fileExtension - the file extension. like .xlsx, .csv etc.
	 * @param fileNameMatchMechanism - file name match mechanism to match with fileNamePrefix.
	 * 		For more details on fileNameMatchMechanism please refer {@link TextMatchMechanism}.
	 * @param variableName - the name of the variable that contains the filename along with its extension.
	 */
	@Then("find latest filename from local downloads directory that matches with [FileNamePrefix:{string}, FileExtension:{string}, FileNameMatchMechanism:{string}] and store into {string} variable.")
	public void find_latest_filename_from_local_downloads_directory_that_matches_with_and_store_into_variable(String fileNamePrefix, String fileExtension, String fileNameMatchMechanism, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileNamePrefix = scenarioContext.applyParamsValueOnText(fileNamePrefix);
		
		List<String> files = LocalMachineFileSystem.listFiles(Locations.getProjectRootDir() + "/test-results/downloads", 
				TextMatchMechanism.valueOf2(fileNameMatchMechanism), fileNamePrefix, fileExtension);
		
		String latestFileName = "NOT-FOUND";
		if(files != null && files.size() > 0) {
			Collections.sort(files);
			latestFileName = files.get(files.size() - 1);
			latestFileName = latestFileName.substring(latestFileName.lastIndexOf(File.separator) + 1);
		}
		scenarioContext.addParamValue(variableName, latestFileName);
	}
	
	/**
	 * Used to find the latest file from the specified local directory based on the file matching criteria like FileNamePrefix, FileExtension and FileNameMatchingMechanism.
	 * 
	 * @param fileNamePrefix - the file name prefix
	 * @param fileExtension - the file extension. like .xlsx, .csv etc.
	 * @param fileNameMatchMechanism - file name match mechanism to match with fileNamePrefix.
	 * 		For more details on fileNameMatchMechanism please refer {@link TextMatchMechanism}.
	 * @param variableName - the name of the variable that contains the filename along with its extension.
	 * @param localDirectory - the absolute path of the directory to search files in it.
	 */
	@Then("find latest filename from the following local directory that matches with [FileNamePrefix:{string}, FileExtension:{string}, FileNameMatchMechanism:{string}] and store into {string} variable:")
	public void find_latest_filename_from_the_following_local_directory_that_matches_with_and_store_into_variable(String fileNamePrefix, String fileExtension, String fileNameMatchMechanism, String variableName, DocString localDirectory) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileNamePrefix = scenarioContext.applyParamsValueOnText(fileNamePrefix);
		String localDirectoryPath = scenarioContext.applyParamsValueOnText(localDirectory.getContent().trim());
		
		List<String> files = LocalMachineFileSystem.listFiles(localDirectoryPath, 
				TextMatchMechanism.valueOf2(fileNameMatchMechanism), fileNamePrefix, fileExtension);
		
		String latestFileName = "NOT-FOUND";
		if(files != null && files.size() > 0) {
			Collections.sort(files);
			latestFileName = files.get(files.size() - 1);
			latestFileName = latestFileName.substring(latestFileName.lastIndexOf(File.separator) + 1);
		}
		scenarioContext.addParamValue(variableName, latestFileName);
	}
	
	/**
	 * Used to verify that the file with the specified extension is downloaded into local downloads directory.
	 * 
	 * @param fileName - the name of the file without extension.
	 * @param fileExtension - the file extension.
	 * @param maxTimeToWaitInSeconds - the maximum time to wait until file appear in downloads directory.
	 */
	@Then("verify that the {string} file with extension {string} is downloaded [MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_file_with_extension_is_downloaded_maxtimetowaitinseconds(String fileName, String fileExtension, Integer maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileName = scenarioContext.applyParamsValueOnText(fileName);
		
		DownloadedFileValidator.validateBrowserFileDownloaded(fileName, fileExtension, false, maxTimeToWaitInSeconds);
	}
	
	/**
	 * Used to verify that the file with the specified extension is present in the local downloads directory.
	 * 
	 * @param fileName - the name of the file without extension.
	 * @param fileExtension - the file extension.
	 * @param maxTimeToWaitInSeconds - the maximum time to wait until file appear in downloads directory.
	 */
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
	 * @param shouldPrint - Will print the contents of the file on console. Valid values: Yes, No
	 * @param keywords - multiple keywords are separated by keywordDelimiter.
	 */
	@Then("verify that the downloaded file {string} contains the following keywords [KeywordDelimiter={string}, InOrder={string}, ShouldPrint={string}]:")
	public void verify_that_the_downloaded_file_contains_the_following_text(String fileNameWithExtension, String keywordDelimiter, String inOrder, String shouldPrint, DocString keywords) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		fileNameWithExtension = scenarioContext.applyParamsValueOnText(fileNameWithExtension);
		
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
	
	/**
	 * Used to validate the file contents that present in a specified directory.
	 * 
	 * @param absoluteFilePath - absolute file path.
	 * @param keywordDelimiter - keyword delimiter to to identify multiple keywords in keywords text.
	 * @param inOrder - valid values Yes, No. If yes then it will search keywords one after one. If No then it will search element in document but sequencing will not matter.
	 * @param shouldPrint - Will print the contents of the file on console. Valid values: Yes, No
	 * @param keywords - multiple keywords are separated by keywordDelimiter.
	 */
	@Then("verify the contents of {string} file that contains the following keywords [KeywordDelimiter={string}, InOrder={string}, ShouldPrint={string}]:")
	public void verify_the_contents_of_the_file_that_contains_the_following_text(String absoluteFilePath, String keywordDelimiter, String inOrder, String shouldPrint, DocString keywords) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		absoluteFilePath = scenarioContext.applyParamsValueOnText(absoluteFilePath);
		
		FileContentsValidator fcValidator = new FileContentsValidator(absoluteFilePath, 
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

	/**
	 * Used to execute the system command locally and stores the result / output into specified variable. Output 
	 * is stored as JSON object and that can be verified using JSON Object verification test step.
	 * 
	 * @param actionName - the meaningful name of the action.
	 * @param variableName - the name of the variable that stores the command output. It stores the whole output as JSON object.
	 * @param commandInfoAsJson - input command in the following JSON format (Format with sample data):
	 * 		{
	 * 			baseDirectory: "/home/test1/",			
	 * 			shellInfo: ["sh", "-c"],		
	 * 			commandName: "ls",
	 * 			commandArgs: ["-l"],
	 * 			timeoutInSeconds: 300
	 *      } 
	 * NOTE: baseDirectory is the directory in which the command will be executed.
	 * NOTE: When shellInfo is null or empty ([]) then system will automatically determine the shell information based on local platform.
	 * NOTE: when timeoutInSeconds value is null then it uses the value 300 seconds. Value 0 means indefinite wait until command is executed successfully.
	 * NOTE: The content of the output variable will be in the following JSON format (For more detail please refer {@link SyncCommandResult}
	 * 	{
	 *    cmdName: "",
	 *    process: {},
	 *    exitStatus: true / false,
	 *    outputData: "",
	 *    errorData: "",
	 *    exceptions: ["", ""]
	 *  }
	 *  
	 *  Accordingly, you can prepare JSON path to verify its contents using JSON validation test steps.
	 */
	@When("execute the following system command locally to {string} and store the result into {string} JSON variable:")
	public void execute_following_system_command_locally(String actionName, String variableName, DocString commandInfoAsJson) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String commandJson = commandInfoAsJson.getContent();
		commandJson = scenarioContext.applyParamsValueOnText(commandJson);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(commandJson, false);
		SmartInputCommand cmd = jsonReader.readValueAsObject("$", SmartInputCommand.class);
		
		SyncCommandResult result = SmartCommandExecuter.executeSync(cmd.getShellInfo(), cmd.getTimeoutInSeconds(), 
				cmd.getBaseDirectory(), cmd.getCommandName(), cmd.getCommandArgs());
		
		JsonDocumentReader jsonReader2 = new JsonDocumentReader();
		DocumentContext jsonCtx = jsonReader2.prepareDocumentContext(result);
		scenarioContext.addParamValue(variableName, jsonCtx);
	}
}

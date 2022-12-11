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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.remote_machine;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.remote_machine.AbstractRemoteMachineActionHandler;
import org.uitnet.testing.smartfwk.remote_machine.RemoteMachineManager;
import org.uitnet.testing.smartfwk.remote_machine.SmartRemoteMachineManager;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to remote file management like file name validation, file contents validation,
 * file upload, file download etc. 
 * 
 * @author Madhav Krishna
 *
 */
public class SmartRemoteFileManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartRemoteFileManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to remove the expected file(s) from specified remote directory on the remote server machine.
	 * 
	 * @param remoteDirectory - the absolute remote directory path where the file(s) are present.
	 * @param expectedFileName - the expected file names based on the FileNameMatchMechanism value that need to be deleted.
	 * @param fileNameMatchMechanism - the expected file name match mechanism. It is used to match the files on the remote directory.
	 * 		Please refer {@link TextMatchMechanism} to understand the value for fileNameMatchMechanism.
	 * @param appName - the name of the configured application.
	 * @param remoteMachineName - the name of the configured remote machine in the specified application. 
	 * 			Refer <b>test-config/apps-config/<app-name>/remote-machines-config/RemoteMachinesConfig.yaml</b> 
	 * 			file for the specific application to know the remote machine name.
	 */
	@When("remove expected file\\(s) [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] from remote machine [AppName={string}, RemoteMachineName:{string}].")
	public void remove_expected_files_from_remote_machine(String remoteDirectory, String expectedFileName, String fileNameMatchMechanism, String appName, String remoteMachineName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		RemoteMachineManager remoteMachineMgr = SmartRemoteMachineManager.getInstance();
		AbstractRemoteMachineActionHandler handler = remoteMachineMgr.getActionHandler(appName, remoteMachineName);
		handler.deleteFiles(remoteDirectory, TextMatchMechanism.valueOf2(fileNameMatchMechanism), expectedFileName);
	}
	
	/**
	 * Used to download the expected files from the remote server machine to local test-results/downloads/ directory.
	 * 
	 * @param remoteDirectory - the absolute remote directory path where the file is present.
	 * @param expectedFileName - the expected file names based on the FileNameMatchMechanism value that need to be downloaded.
	 * @param fileNameMatchMechanism - the expected file name match mechanism. It is used to match the files on the remote directory.
	 * 		Please refer {@link TextMatchMechanism} to understand the value for fileNameMatchMechanism.
	 * @param appName - the name of the configured application.
	 * @param remoteMachineName - the name of the configured remote machine in the specified application. 
	 * <blockquote><pre>
	 * 			Refer <b>test-config/apps-config/<app-name>/remote-machines-config/RemoteMachinesConfig.yaml</b> 
	 * 			file for the specific application to know the remote machine name.
	 * </pre></blockquote>
	 */
	@When("download expected remote file\\(s) [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] from remote machine [AppName={string}, RemoteMachineName:{string}].")
	public void download_expected_files_from_remote_machine(String remoteDirectory, String expectedFileName, String fileNameMatchMechanism, String appName, String remoteMachineName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		RemoteMachineManager remoteMachineMgr = SmartRemoteMachineManager.getInstance();
		AbstractRemoteMachineActionHandler handler = remoteMachineMgr.getActionHandler(appName, remoteMachineName);
		handler.downloadFiles(remoteDirectory, TextMatchMechanism.valueOf2(fileNameMatchMechanism), expectedFileName, 
				Locations.getProjectRootDir() + "/test-results/downloads/");
	}
	
	/**
	 * Used to upload file(s) on the remote server machine from the local machine directory.
	 * 
	 * @param localDirectory - the relative local directory path with respect to project root directory.
	 * @param expectedFileName - the expected file names based on the FileNameMatchMechanism value that need to be uploaded to remote server from local machine.
	 * @param fileNameMatchMechanism - the expected file name match mechanism. It is used to match the files on the local directory.
	 * <blockquote><pre>
	 * 		Please refer {@link TextMatchMechanism} to understand the value for fileNameMatchMechanism.
	 * </pre></blockquote>
	 * @param appName - the name of the configured application.
	 * @param remoteMachineName - the name of the configured remote machine in the specified application. 
	 * <blockquote><pre>
	 * 		Refer <b>test-config/apps-config/<app-name>/remote-machines-config/RemoteMachinesConfig.yaml</b> 
	 * 			file for the specific application to know the remote machine name.
	 * </pre></blockquote>
	 * @param remoteDirectory - the absolute remote directory path where the file(s) will be uploaded.
	 */
	@When("upload local file\\(s) [LocalDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] on remote machine [AppName={string}, RemoteMachineName:{string}, RemoteDirectory={string}].")
	public void upload_expected_files_on_remote_machine(String localDirectory, String expectedFileName, String fileNameMatchMechanism, String appName, String remoteMachineName, String remoteDirectory) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		RemoteMachineManager remoteMachineMgr = SmartRemoteMachineManager.getInstance();
		AbstractRemoteMachineActionHandler handler = remoteMachineMgr.getActionHandler(appName, remoteMachineName);
		handler.uploadFiles(Locations.getProjectRootDir() + localDirectory, TextMatchMechanism.valueOf2(fileNameMatchMechanism), expectedFileName, 
				remoteDirectory);
	}
	
	/**
	 * Used to verify whether the expected file(s) are uploaded on remote server machine.
	 * 
	 * @param remoteDirectory - the absolute remote directory path where the file(s) will be present after upload.
	 * @param expectedFileName - the expected file names based on the FileNameMatchMechanism value are used for verification on remote server machine.
	 * @param fileNameMatchMechanism - the expected file name match mechanism. It is used to match the files on the remote directory.
	 * <blockquote><pre>
	 * 		Please refer {@link TextMatchMechanism} to understand the value for fileNameMatchMechanism.
	 * </pre></blockquote>
	 * @param appName - the name of the configured application.
	 * @param remoteMachineName - the name of the configured remote machine in the specified application. 
	 * <blockquote><pre>
	 * 			Refer <b>test-config/apps-config/<app-name>/remote-machines-config/RemoteMachinesConfig.yaml</b> 
	 * 			file for the specific application to know the remote machine name.
	 * </pre></blockquote>
	 * @param maxTimeToWaitInSeconds - this is the maximum wait time to check whether all the expected file(s) are uploaded on remote server machine.
	 */
	@Then("verify that the expected files [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] "
			+ "are uploaded on remote machine [AppName={string}, RemoteMachineName:{string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_expected_files_are_present_on_remote_machine(String remoteDirectory, String expectedFileName, 
			String fileNameMatchMechanism, String appName, String remoteMachineName, Integer maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		RemoteMachineManager remoteMachineMgr = SmartRemoteMachineManager.getInstance();
		AbstractRemoteMachineActionHandler handler = remoteMachineMgr.getActionHandler(appName, remoteMachineName);
		handler.validateFileExists(remoteDirectory, TextMatchMechanism.valueOf2(fileNameMatchMechanism), expectedFileName, maxTimeToWaitInSeconds);
	}
	
	/**
	 * Used to verify whether the expected file(s) are present on remote server machine.
	 * 
	 * @param remoteDirectory - the absolute remote directory path where the file(s) will be present.
	 * @param expectedFileName - the expected file names based on the FileNameMatchMechanism value are used for verification on remote server machine.
	 * @param fileNameMatchMechanism - the expected file name match mechanism. It is used to match the files on the remote directory.
	 * <blockquote><pre>
	 * 		Please refer {@link TextMatchMechanism} to understand the value for fileNameMatchMechanism.
	 * </pre></blockquote>
	 * @param appName - the name of the configured application.
	 * @param remoteMachineName - the name of the configured remote machine in the specified application. 
	 * <blockquote><pre>
	 * 			Refer <b>test-config/apps-config/<app-name>/remote-machines-config/RemoteMachinesConfig.yaml</b> 
	 * 			file for the specific application to know the remote machine name.
	 * </pre></blockquote>
	 * @param maxTimeToWaitInSeconds - this is the maximum wait time to check whether all the expected file(s) are present on remote server machine.
	 */
	@Then("verify that the expected files [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] "
			+ "are present on remote machine [AppName={string}, RemoteMachineName:{string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_expected_files_are_present_on_remote_machine_1(String remoteDirectory, String expectedFileName, 
			String fileNameMatchMechanism, String appName, String remoteMachineName, Integer maxTimeToWaitInSeconds) {
		verify_that_the_expected_files_are_present_on_remote_machine(remoteDirectory, expectedFileName, fileNameMatchMechanism, appName, remoteMachineName, maxTimeToWaitInSeconds);
	}
	
	/**
	 * Used to verify whether the expected folders/directories are present on remote server machine.
	 * 
	 * @param remoteDirectory - the absolute remote base directory path where the expected directories will be present.
	 * @param expectedFolderName - the expected folder names based on the FileNameMatchMechanism value are used for verification on remote server machine.
	 * @param folderNameMatchMechanism - the expected file name match mechanism. It is used to match the files on the remote directory.
	 * <blockquote><pre>
	 * 		Please refer {@link TextMatchMechanism} to understand the value for fileNameMatchMechanism.
	 * </pre></blockquote>
	 * @param appName - the name of the configured application.
	 * @param remoteMachineName - the name of the configured remote machine in the specified application. 
	 * <blockquote><pre>
	 * 			Refer <b>test-config/apps-config/<app-name>/remote-machines-config/RemoteMachinesConfig.yaml</b> 
	 * 			file for the specific application to know the remote machine name.
	 * </pre></blockquote>
	 * @param maxTimeToWaitInSeconds
	 */
	@Then("verify that the expected directories [RemoteDirectory={string}, ExpectedFolderName={string}, FolderNameMatchMechanism={string}] "
			+ "are present on remote machine [AppName={string}, RemoteMachineName:{string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_expected_directory_are_present_on_remote_machine(String remoteDirectory, String expectedFolderName, 
			String folderNameMatchMechanism, String appName, String remoteMachineName, Integer maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		RemoteMachineManager remoteMachineMgr = SmartRemoteMachineManager.getInstance();
		AbstractRemoteMachineActionHandler handler = remoteMachineMgr.getActionHandler(appName, remoteMachineName);
		handler.validateFolderExists(remoteDirectory, TextMatchMechanism.valueOf2(folderNameMatchMechanism), expectedFolderName, maxTimeToWaitInSeconds);
	}
}

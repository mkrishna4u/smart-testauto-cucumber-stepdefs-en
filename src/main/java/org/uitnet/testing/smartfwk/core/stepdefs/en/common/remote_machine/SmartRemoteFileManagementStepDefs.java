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
 * 
 * @author Madhav Krishna
 *
 */
public class SmartRemoteFileManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartRemoteFileManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

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
	
	@When("download expected remote file(s) [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] from remote machine [AppName={string}, RemoteMachineName:{string}].")
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
	
	@When("upload local file(s) [LocalDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] on remote machine [AppName={string}, RemoteMachineName:{string}, RemoteDirectory={string}].")
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
	
	@Then("verify that the expected file [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] "
			+ "is uploaded on remote machine [AppName={string}, RemoteMachineName:{string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_expected_file_is_present_on_remote_machine(String remoteDirectory, String expectedFileName, 
			String fileNameMatchMechanism, String appName, String remoteMachineName, Integer maxTimeToWaitInSeconds) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		RemoteMachineManager remoteMachineMgr = SmartRemoteMachineManager.getInstance();
		AbstractRemoteMachineActionHandler handler = remoteMachineMgr.getActionHandler(appName, remoteMachineName);
		handler.validateFileExists(remoteDirectory, TextMatchMechanism.valueOf2(fileNameMatchMechanism), expectedFileName, maxTimeToWaitInSeconds);
	}
	
	@Then("verify that the expected file [RemoteDirectory={string}, ExpectedFileName={string}, FileNameMatchMechanism={string}] "
			+ "is present on remote machine [AppName={string}, RemoteMachineName:{string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_expected_file_is_present_on_remote_machine_1(String remoteDirectory, String expectedFileName, 
			String fileNameMatchMechanism, String appName, String remoteMachineName, Integer maxTimeToWaitInSeconds) {
		verify_that_the_expected_file_is_present_on_remote_machine(remoteDirectory, expectedFileName, fileNameMatchMechanism, appName, remoteMachineName, maxTimeToWaitInSeconds);
	}
	
	@Then("verify that the expected directory [RemoteDirectory={string}, ExpectedFolderName={string}, FolderNameMatchMechanism={string}] "
			+ "is present on remote machine [AppName={string}, RemoteMachineName:{string}, MaxTimeToWaitInSeconds={int}].")
	public void verify_that_the_expected_directory_is_present_on_remote_machine(String remoteDirectory, String expectedFolderName, 
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

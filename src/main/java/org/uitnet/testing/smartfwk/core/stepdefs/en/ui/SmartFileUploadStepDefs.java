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
package org.uitnet.testing.smartfwk.core.stepdefs.en.ui;

import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions to perform uploaded file operation using UI.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartFileUploadStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartFileUploadStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to upload single / multiple files using specified page element.
	 * 
	 * @param files - relative path in JSON array format like
	 * <blockquote><pre>
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * </pre></blockquote>
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("upload {string} files using {string} page element.")
	public void upload_files_using_page_element(String files, String po) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(files, false);
		List<String> fileList = jsonReader.readValuesAsList("$");
		
		PageObjectUtil.invokeValidatorMethod("selectFiles", new String[] { ItemList.class.getTypeName(), Integer.TYPE.getTypeName() },
				new Object[] { new ItemList<>(fileList), poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
	
	/**
	 * Used to upload single / multiple files using specified page object.
	 * 
	 * @param files - relative path in JSON array format like
	 * <blockquote><pre>
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * </pre></blockquote>
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("upload {string} files using {string} page object.")
	public void upload_files_using_page_element_2(String files, String po) {
		upload_files_using_page_element(files, po);
	}
	
	/**
	 * Used to select single / multiple files using specified page element (using file dialog).
	 * 
	 * @param files - relative path in JSON array format like
	 * <blockquote><pre>
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * </pre></blockquote>
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("select {string} files using {string} page element.")
	public void select_files_using_page_element(String files, String po) {
		upload_files_using_page_element(files, po);
	}
	
	/**
	 * Used to select single / multiple files using specified page object (using file dialog).
	 * 
	 * @param files - relative path in JSON array format like
	 * <blockquote><pre>
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * </pre></blockquote>
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 */
	@When("select {string} files using {string} page object.")
	public void select_files_using_page_object(String files, String po) {
		upload_files_using_page_element(files, po);
	}
	
	/**
	 * Used to select single / multiple files using specified page element (using file dialog).
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param files - DocString that contains relative paths in JSON array format like
	 * <blockquote><pre>
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * </pre></blockquote>
	 */
	@When("select the following files using {string} page element:")
	public void select_the_following_files_using_page_element(String po, DocString files) {
		upload_files_using_page_element(files.getContent(), po);
	}
	
	/**
	 * Used to select single / multiple files using specified page object (using file dialog).
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>
	 * @param files - DocString that contains relative paths in JSON array format like
	 * <blockquote><pre>
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * </pre></blockquote>
	 */
	@When("select the following files using {string} page object:")
	public void select_the_following_files_using_page_object(String po, DocString files) {
		upload_files_using_page_element(files.getContent(), po);
	}
}

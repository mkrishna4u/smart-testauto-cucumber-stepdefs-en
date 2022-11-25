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
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.en.When;

/**
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
	 * Used to upload single / multiple files using page object.
	 * 
	 * @param files - relative path in JSON array format like
	 * 		["test-data/uploads/sample1.pdf", "test-data/uploads/sample2.pdf"]
	 * @param po - page object used to upload the files.
	 */
	@When("upload {string} file\\(s) using {string} page element.")
	@When("upload {string} file\\(s) using {string} page object.")
	public void upload_file_using_page_element_(String files, String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(files, false);
		List<String> fileList = jsonReader.readValuesAsList("$");
		
		PageObjectUtil.invokeValidatorMethod("uploadFiles", new Class<?>[] { List.class, Integer.TYPE },
				new Object[] { fileList, poInfo.getMaxIterationsToLocateElements() }, poInfo,
				scenarioContext);
	}
}

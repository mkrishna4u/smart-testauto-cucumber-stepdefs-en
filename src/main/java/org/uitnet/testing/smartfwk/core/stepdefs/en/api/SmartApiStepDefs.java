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
package org.uitnet.testing.smartfwk.core.stepdefs.en.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.AbstractApiActionHandler;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.HttpMultipartRequest;
import org.uitnet.testing.smartfwk.api.core.support.HttpRequest;
import org.uitnet.testing.smartfwk.api.core.support.HttpResponse;
import org.uitnet.testing.smartfwk.api.core.support.MultipartData;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.XMLDocumentUtil;
import org.w3c.dom.Document;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Used to perform CRUD operations on JSON text / objects.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartApiStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartApiStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to send HTTP PUT or POST request using the textual info present into requestBody. Request body
	 * can be specified as doc string.
	 * 
	 * @param appName
	 * @param targetServer
	 * @param targetURL
	 * @param userProfile
	 * @param contentType
	 * @param accept
	 * @param requestVariableName
	 * @param responseVariableName
	 * @param requestBody
	 */
	@When("make HTTP PUT request using the following request body on target server [AppName={string}, TargetServer={string}, "
			+ "TargetURL={string}] using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_put_request_using_the_following_request_body_on(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept, 
			String requestVariableName, String responseVariableName, DocString requestBody) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String reqTxt = requestBody.getContent();
		reqTxt = scenarioContext.applyParamsValueOnText(reqTxt);
		
		//JsonDocumentReader reqDoc = new JsonDocumentReader(reqTxt);

		HttpRequest httpRequest = new HttpRequest().setPayload(reqTxt);
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		scenarioContext.addParamValue(requestVariableName, reqTxt);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP POST request using the following request body on target server [AppName={string}, TargetServer={string}, "
			+ "TargetURL={string}] using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_following_request_body_on_1(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept, 
			String requestVariableName, String responseVariableName, DocString requestBody) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String reqTxt = requestBody.getContent();
		reqTxt = scenarioContext.applyParamsValueOnText(reqTxt);
		
		//JsonDocumentReader reqDoc = new JsonDocumentReader(reqTxt);

		HttpRequest httpRequest = new HttpRequest().setPayload(reqTxt);
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		scenarioContext.addParamValue(requestVariableName, reqTxt);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to upload the single/multiple files on the target server. FilesDetails is presented in the following format:
	 * 
	 * | Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 * | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes/no                | test-data/uploads/Sample.pdf                                 |
	 * 
	 * 
	 * @param appName
	 * @param targetServer
	 * @param targetURL
	 * @param userProfile
	 * @param contentType
	 * @param accept
	 * @param requestVariableName
	 * @param responseVariableName
	 * @param filesDetails
	 */
	@When("make HTTP PUT request to upload the following files on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_put_request_to_upload_the_following_files_on(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept, 
			String requestVariableName, String responseVariableName, DataTable filesDetails) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpMultipartRequest httpRequest = new HttpMultipartRequest();
		httpRequest.setContentType(contentType);
		httpRequest.setResponseContentType(accept);
		
		List<List<String>> rows = filesDetails.asLists();
		List<String> row;
		String partName, fileName, fileContentType, applyVariables, filePath;
		MultipartData multipartInfo;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			partName = row.get(0);
			fileName = row.get(1);
			fileContentType = row.get(2);
			applyVariables = row.get(3);
			filePath = Locations.getProjectRootDir() + File.separator + row.get(4);
			
			if(applyVariables.trim().equalsIgnoreCase("yes") || applyVariables.trim().equalsIgnoreCase("true")) {
				File f = new File(filePath);
				String fileContents = null;
				try {
					fileContents = Files.readString(f.toPath());
				} catch (IOException e) {
					Assert.fail("Failed to read '" + filePath + "' file.", e);
				}
				fileContents = scenarioContext.applyParamsValueOnText(fileContents);
				multipartInfo = new MultipartData(partName, fileName, fileContentType, fileContents.getBytes());
			} else {
				multipartInfo = new MultipartData(partName, fileName, fileContentType, filePath);
			}
			
			httpRequest.addPart(multipartInfo);
		}
		
		scenarioContext.addParamValue(requestVariableName, httpRequest);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpUploadFormFiles(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP POST request to upload the following files on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_put_request_to_upload_the_following_files_on_1(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept, 
			String requestVariableName, String responseVariableName, DataTable filesDetails) {
		make_http_post_put_request_to_upload_the_following_files_on(appName, targetServer, targetURL, userProfile, contentType, accept, requestVariableName, responseVariableName, filesDetails);
	}
	
	/**
	 * Used to make HTTP POST request using the information present in request template file.
	 * 
	 * @param appName
	 * @param targetServer
	 * @param targetURL
	 * @param userProfile
	 * @param contentType
	 * @param accept
	 * @param requestVariableName
	 * @param responseVariableName
	 * @param requestTemplateFile - the relative path of the request template file (relative to project directory)
	 */
	@When("make HTTP PUT request using the contents of the following template file on target server [AppName={string}, TargetServer={string}, "
			+ "TargetURL={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_contents_of_the_following_template_file_on(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept,
			String requestVariableName, String responseVariableName, DocString requestTemplateFile) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String templateFilePath = requestTemplateFile.getContent().trim();
		templateFilePath = scenarioContext.applyParamsValueOnText(templateFilePath);
		
		String contents = null;
		try {
		    contents = Files.readString(new File(templateFilePath).toPath());
		    contents = scenarioContext.applyParamsValueOnText(contents);
		} catch(Exception ex) {
			Assert.fail("Failed to read '" + templateFilePath + "' file contents.", ex);
		}
		
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP POST request using the contents of the following template file on target server [AppName={string}, "
			+ "TargetServer={string}, TargetURL={string}] using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_contents_of_the_following_template_file_on_1(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept,
			String requestVariableName, String responseVariableName, DocString requestTemplateFile) {
		make_http_post_request_using_the_contents_of_the_following_template_file_on(appName, targetServer, targetURL, userProfile, contentType, accept, requestVariableName, responseVariableName, requestTemplateFile);
	}
	
	@When("make HTTP PUT request by setting {string} JSON object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_put_request_by_setting_json_object_on(String jsonObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String accept, String requestVariableName, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String contents = null;
		try {
			DocumentContext jsonDoc = (DocumentContext) scenarioContext.getParamValue(jsonObjVariableName);
			contents = jsonDoc.jsonString();
		} catch(Exception ex) {
			Assert.fail("Failed to read JSON document from '" + jsonObjVariableName + "' JSON object variable.", ex);
		}
		
		String contentType = "application/json";
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP POST request by setting {string} JSON object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_put_request_by_setting_json_object_on_1(String jsonObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String accept, String requestVariableName, String responseVariableName) {
		make_http_post_put_request_by_setting_json_object_on(jsonObjVariableName, appName, targetServer, targetURL, userProfile, accept, requestVariableName, responseVariableName);
	}
	
	@When("make HTTP PUT request by setting {string} XML object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_put_request_by_setting_xml_object_on(String xmlObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String accept, String requestVariableName, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String contents = null;
		try {
			Document xmlDoc = (Document) scenarioContext.getParamValue(xmlObjVariableName);
			contents = XMLDocumentUtil.convertDocumentToString(xmlDoc);
		} catch(Exception ex) {
			Assert.fail("Failed to read XML document from '" + xmlObjVariableName + "' XML object variable.", ex);
		}
		
		String contentType = "application/xml";
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP POST request by setting {string} XML object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_put_request_by_setting_xml_object_on_1(String xmlObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String accept, String requestVariableName, String responseVariableName) {
		make_http_post_put_request_by_setting_xml_object_on(xmlObjVariableName, appName, targetServer, targetURL, userProfile, accept, requestVariableName, responseVariableName);
	}
	
	@When("make HTTP GET request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}] with header info [Accept={string}] and variable info [RespVar={string}].")
	public void make_http_get_request_on(String appName,
			String targetServer, String targetURL, String userProfile, String accept, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setResponseContentType(accept);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		HttpResponse httpResponse = scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile).httpGet(targetURL, accept, null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP DELETE request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}] with header info [Accept={string}] and variable info [RespVar={string}].")
	public void make_http_delete_request_on(String appName,
			String targetServer, String targetURL, String userProfile, String accept, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setResponseContentType(accept);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpDelete(targetURL, accept, null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@When("make HTTP HEAD request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}].")
	public void make_http_head_request_on(String appName,
			String targetServer, String targetURL, String userProfile, String accept, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setResponseContentType(accept);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpHead(targetURL, null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	@Then("verify {string} HTTP response contains HTTPStatusCode={int}.")
	public void verify_http_response_contains_http_status_code(String responseVariableName, int httpStatusCode) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(responseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		httpResponse.getValidator().validateExpectedResponseCode(httpStatusCode);
	}
	
	@Then("verify {string} HTTP response contains ContentType={string}.")
	public void verify_http_response_contains_content_type(String responseVariableName, String contentType) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(responseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		httpResponse.getValidator().validateContentType(contentType);
	}
	
	/**
	 * Verifies the HTTP response header information as per the information presented in the following DataTable:
	 * 
	 * | Header Name         | Expected Value            | Text Match Mechanism          |
	 * | Content-Type        | application/json          | icExactMatchWithExpectedValue |
	 * | Access-Token        | tttttttt                  | exactMatchWithExpectedValue   |
	 * 
	 * @param responseVariableName
	 * @param expectedHeaderInfo
	 */
	@Then("verify {string} HTTP response contains following header information:")
	public void verify_http_response_variable_contains_http_status_code(String responseVariableName, DataTable expectedHeaderInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(responseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		List<List<String>> rows = expectedHeaderInfo.asLists();
		List<String> row;
		String headerName, expectedValue, textMatchMechanism;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			headerName = row.get(0);
			expectedValue = row.get(1);
			textMatchMechanism = row.get(2);
			
			if(StringUtil.isEmptyAfterTrim(textMatchMechanism)) {
				textMatchMechanism = TextMatchMechanism.exactMatchWithExpectedValue.name();
			}
			
			httpResponse.getValidator().validateExpectedHeaderValue(headerName, expectedValue, TextMatchMechanism.valueOf(textMatchMechanism));
		}
	}
	
	/**
	 * Used to verify the body/payload contents of HTTP Response. KeywordsInfo should be specified in JSON format as given below:
	 * 
	 * { keywords: ["1", "2"], inOrder: yes/no }
	 * 
	 * @param httpResponseVariableName
	 * @param keywordsInfo
	 */
	@Then("verify body of {string} HTTP response contains following keywords in its contents:")
	public void verify_body_of_http_response_contains_following_keywords_in_its_contents(String httpResponseVariableName, DocString keywordsInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(httpResponseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		String jsonStr = keywordsInfo.getContent();
		
		DocumentContext jsonObj = new JsonDocumentReader(jsonStr, false).getDocumentContext();
		String inOrder = JsonYamlUtil.readNoException("$.inOrder", String.class, jsonObj);
		List<String> keywords = JsonYamlUtil.readNoException("$.keywords", new TypeRef<List<String>>() {}, jsonObj);
		if(keywords == null || keywords.size() == 0) {
			Assert.fail("No expected keywords found to match in response body.");
		}
		
		if("true".equalsIgnoreCase(inOrder) || "yes".equalsIgnoreCase(inOrder)) {
			httpResponse.getValidator().validateBodyContainsKeywords(keywords, true);
		} else {
			httpResponse.getValidator().validateBodyContainsKeywords(keywords, false);
		}
	}
	
	/**
	 * Verifies the HTTP response contains the JSON data with the following expected parameter's information:
	 * 
	 * | Parameter/JSON Path        | Operator           | Expected Information                                                                                               |
	 * | $.name                     | =                  | John Hopkins                                                                                                       |
	 * | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 
	 * For more info on JSON Path, please refer @see (@link https://github.com/json-path/JsonPath}
	 * For more details on Operators, please refer @see ValueMatchOperator enum.
	 * For more details on Expected Information please refer @see ExpectedInfo class.
	 * 
	 * @param responseVariableName
	 * @param expectedParamsInfo
	 */
	@Then("verify {string} HTTP response contains JSON data with the following expected params information:")
	public void verify_http_response_contains_json_data_with_the_following_expected_params_information(String responseVariableName, DataTable expectedParamsInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(responseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		httpResponse.getValidator().validateResponseNotEmpty();
				
		DocumentContext jsonObj = new JsonDocumentReader(httpResponse.getPayload(), false).getDocumentContext();
		
		List<List<String>> rows = expectedParamsInfo.asLists();
		List<String> row;
		String jsonPath, oper, expectedInfo;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			jsonPath = row.get(0); 
			oper = row.get(1);
			expectedInfo = row.get(2);
			
			jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
			expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);
			
			// verify the actual value against the expected value.
			SmartDataValidator.validateJsonOrYamlData(jsonObj, jsonPath, oper, expectedInfo);
		}
	}
	
	/**
	 * Verifies the HTTP response contains the XML data with the following expected xpath information:
	 * 
	 * | Parameter/XPATH                                                   | Operator     | Expected Info                   |
	 * | //user[@name='test-name']                                         | present      |                                 |
	 * | //user[@name='unknown']                                           | not-present  |                                 |
	 * | {path: "count(//user[@name='unknown'])", valueType: "integer"}    | =            | { ev: 50, valueType: 'integer'} |
	 * | {path: "count(//user[@name='unknown'])", valueType: "integer"}    | >            | { ev: 5, valueType: 'integer'}  |
	 * 
	 * For more details on parameter/xpath, please refer @see ParamPath class.
	 * For more details on Operators, please refer @see ValueMatchOperator enum.
	 * For more details on Expected Information please refer @see ExpectedInfo class.
	 * 
	 * @param responseVariableName
	 * @param expectedParamsInfo
	 */
	@Then("verify {string} HTTP response contains XML data with the following expected params information:")
	public void verify_http_response_contains_xml_data_with_the_following_expected_params_information(String responseVariableName, DataTable expectedParamsInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(responseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		httpResponse.getValidator().validateResponseNotEmpty();
				
		Document xmlObj = new XmlDocumentReader(httpResponse.getPayload()).getDocument();
		
		List<List<String>> rows = expectedParamsInfo.asLists();
		List<String> row;
		String xmlPath, operator, expectedInfo;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			xmlPath = row.get(0); 
			operator = row.get(1); 
			expectedInfo = row.get(2);
			
			xmlPath = scenarioContext.applyParamsValueOnText(xmlPath);
			
			// verify the actual value against the expected value.
			SmartDataValidator.validateXmlData(xmlObj, xmlPath, operator, expectedInfo);
		}
	}
	
	/**
	 * Verifies the downloaded file name match with the expected information given in the following format:
	 * 
	 * {expectedFileName: "Sample.pdf", textMatchMechanism: "startsWithExpectedValue", deleteFile: true/false}
	 * 
	 * @param httpResponseVariableName
	 * @param expectedFileInfo
	 */
	@Then("verify downloaded file as part of {string} HTTP response contains following expected name:")
	public void verify_downloaded_file_as_part_of_http_response_contains_following_expected_name(String httpResponseVariableName, DocString expectedFileInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(httpResponseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		String jsonStr = expectedFileInfo.getContent();
		
		DocumentContext jsonObj = new JsonDocumentReader(jsonStr, false).getDocumentContext();
		String textMatchMechanism = JsonYamlUtil.readNoException("$.textMatchMechanism", String.class, jsonObj);
		boolean deleteFile = JsonYamlUtil.readNoException("$.deleteFile", boolean.class, jsonObj);
		String expectedFileName = JsonYamlUtil.readNoException("$.expectedFileName", String.class, jsonObj);
		if(StringUtil.isEmptyAfterTrim(expectedFileName)) {
			Assert.fail("Expected filename can not be empty.");
		}
		
		httpResponse.getValidator().validateFileDownloaded(expectedFileName, TextMatchMechanism.valueOf(textMatchMechanism), deleteFile);
	}
	
	/**
	 * Used to verify the contents of the downloaded file. KeywordsInfo should be specified in JSON format as given below:
	 * 
	 * { keywords: ["1", "2"], inOrder: yes/no }
	 * 
	 * @param httpResponseVariableName
	 * @param keywordsInfo
	 */
	@Then("verify downloaded file as part of {string} HTTP response contains following keywords in its contents:")
	public void verify_downloaded_file_as_part_of_http_response_contains_following_keywords_in_its_contents(String httpResponseVariableName, DocString keywordsInfo) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(httpResponseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		String jsonStr = keywordsInfo.getContent();
		
		DocumentContext jsonObj = new JsonDocumentReader(jsonStr, false).getDocumentContext();
		String inOrder = JsonYamlUtil.readNoException("$.inOrder", String.class, jsonObj);
		List<String> keywords = JsonYamlUtil.readNoException("$.keywords", new TypeRef<List<String>>() {}, jsonObj);
		if(keywords == null || keywords.size() == 0) {
			Assert.fail("No expected keywords found to match in file.");
		}
		
		if("true".equalsIgnoreCase(inOrder) || "yes".equalsIgnoreCase(inOrder)) {
			httpResponse.getValidator().validateDownloadedFileContainsAllKeywordsInOrder(false, keywords.toArray(new String[keywords.size()]));
		} else {
			httpResponse.getValidator().validateDownloadedFileContainsAllKeywords(false, keywords.toArray(new String[keywords.size()]));
		}
	}
	
	@Then("retrieve {string} JSON path value from the payload of {string} HTTP response and store into {string} variable.")
	public void retrive_json_path_value_from_the_payload_of_http_response_and_store_into_variable(String jsonPath, String httpResponseVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpResponse httpResponse = (HttpResponse) scenarioContext.getParamValue(httpResponseVariableName);
		Assert.assertNotNull(httpResponse, "HTTP response not found.");
		
		httpResponse.getValidator().validateResponseNotEmpty();
				
		DocumentContext jsonObj = new JsonDocumentReader(httpResponse.getPayload(), false).getDocumentContext();
		Object value = jsonObj.read(jsonPath);
		scenarioContext.addParamValue(variableName, value);
	}
}

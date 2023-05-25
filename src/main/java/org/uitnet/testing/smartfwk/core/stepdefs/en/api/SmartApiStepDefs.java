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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.AbstractApiActionHandler;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.HttpMultipartRequest;
import org.uitnet.testing.smartfwk.api.core.support.HttpRequest;
import org.uitnet.testing.smartfwk.api.core.support.HttpResponse;
import org.uitnet.testing.smartfwk.api.core.support.MultipartData;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
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
 * Lists steps definitions related to API testing automation.
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
	 * Used to prepare HTTP Request Header information by setting the specified parameters' value.
	 * This information can be stored in a variable and this variable can be used in another steps.
	 * 
	 * @param variableName - the name of the variable that stores the parameter's information.
	 * @param headerParameters - the data table to specify the parameters information in the format given below:
	 * 	| Parameter Name | Parameter Value  |
	 *  | Content Type   | application/json |
	 *  | Accept         | application/json |
	 *  
	 *  Where parameter name and parameter values are pure string data.
	 * 
	 */
	@When("prepare HTTP request header by setting the followings parameters and store into {string} variable:")
	public void prepare_http_request_header_by_setting_the_following_parameters_and_store_into_variable(String variableName, DataTable headerParameters) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		List<List<String>> rows = headerParameters.asLists();
		String paramName, paramValue;
		List<String> row;
		
		Map<String, String> params = new LinkedHashMap<>();
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			paramName = scenarioContext.applyParamsValueOnText(row.get(0));
			paramValue = scenarioContext.applyParamsValueOnText(row.get(1));
			params.put(paramName, paramValue);
		}
		
		scenarioContext.addParamValue(variableName, params);
	}
	
	/**
	 * Used to send HTTP PUT request using the textual info present into requestBody. Request body
	 * can be specified as doc string.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestBody - the actual contents that are going to send in HTTP request body / payload.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP PUT request using the following request body on target server [AppName={string}, TargetServer={string}, "
			+ "TargetURL={string}] using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_put_request_using_the_following_request_body_on_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, 
			String requestVariableName, String responseVariableName, DocString requestBody) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String reqTxt = requestBody.getContent();
		reqTxt = scenarioContext.applyParamsValueOnText(reqTxt);
		
		HttpRequest httpRequest = new HttpRequest().setPayload(reqTxt);
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);		

		scenarioContext.addParamValue(requestVariableName, reqTxt);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to send HTTP PUT request using the textual info present into requestBody. Request body
	 * can be specified as doc string.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestBody - the actual contents that are going to send in HTTP request body / payload.
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
		
		HttpRequest httpRequest = new HttpRequest().setPayload(reqTxt);
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		scenarioContext.addParamValue(requestVariableName, reqTxt);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to send HTTP POST request using the textual info present into requestBody. Request body
	 * can be specified as doc string.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestBody - the actual contents that are going to send in HTTP request body / payload.
	 */
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
	 * Used to send HTTP POST request using the textual info present into requestBody. Request body
	 * can be specified as doc string.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestBody - the actual contents that are going to send in HTTP request body / payload.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP POST request using the following request body on target server [AppName={string}, TargetServer={string}, "
			+ "TargetURL={string}] using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_following_request_body_on_1_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, 
			String requestVariableName, String responseVariableName, DocString requestBody) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String reqTxt = requestBody.getContent();
		reqTxt = scenarioContext.applyParamsValueOnText(reqTxt);
		
		HttpRequest httpRequest = new HttpRequest().setPayload(reqTxt);
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);	

		scenarioContext.addParamValue(requestVariableName, reqTxt);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to upload the single/multiple files on the target server using HTTP PUT. FilesDetails can be specified in the following format in the cucumber feature file:
	 * <blockquote><pre>
	 * | Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 * | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes/no                | test-data/uploads/Sample.pdf                                 |
	 * </pre></blockquote>
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param filesDetails  the cucumber data table to specify uploading file details in the format given below:
	 *      <blockquote><pre>
	 * 		| Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 *      | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes                   | test-data/uploads/Sample.pdf                                 |
	 *      | SampleFile2.pdf| SampleFile2.pdf    | application/pdf      | no                    | test-data/uploads/Sample2.pdf                                |
	 *       </pre></blockquote>
	 *       
	 *       Where "Apply Variable" column value denotes whether you would like to apply variable's information in file contents or not. Valid values are: yes, no
	 */
	@When("make HTTP PUT request to upload the following files on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_put_request_to_upload_the_following_files_on(String appName,
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
		HttpResponse httpResponse = handler.httpUploadFormFiles(targetURL, httpRequest, null, null, true);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to upload the single/multiple files on the target server using HTTP PUT. FilesDetails can be specified in the following format in the cucumber feature file:
	 * <blockquote><pre>
	 * | Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 * | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes/no                | test-data/uploads/Sample.pdf                                 |
	 * </pre></blockquote>
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param filesDetails  the cucumber data table to specify uploading file details in the format given below:
	 *      <blockquote><pre>
	 * 		| Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 *      | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes                   | test-data/uploads/Sample.pdf                                 |
	 *      | SampleFile2.pdf| SampleFile2.pdf    | application/pdf      | no                    | test-data/uploads/Sample2.pdf                                |
	 *       </pre></blockquote>
	 *       
	 *      Where "Apply Variable" column value denotes whether you would like to apply variable's information in file contents or not. Valid values are: yes, no
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP PUT request to upload the following files on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_put_request_to_upload_the_following_files_on_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, 
			String requestVariableName, String responseVariableName, DataTable filesDetails) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		
		HttpMultipartRequest httpRequest = new HttpMultipartRequest();
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);
		
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
		HttpResponse httpResponse = handler.httpUploadFormFiles(targetURL, httpRequest, null, null, true);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to upload the single/multiple files on the target server using HTTP POST. FilesDetails can be specified in the following format in the cucumber feature file:
	 * <blockquote><pre>
	 * | Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 * | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes/no                | test-data/uploads/Sample.pdf                                 |
	 * </pre></blockquote>
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param filesDetails - the cucumber data table to specify uploading file details in the format given below:
	 *   <blockquote><pre>
	 * 		| Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 *      | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes                   | test-data/uploads/Sample.pdf                                 |
	 *      | SampleFile2.pdf| SampleFile2.pdf    | application/pdf      | no                    | test-data/uploads/Sample2.pdf                                |
	 *   </pre></blockquote>
	 * 
	 *   Where "Apply Variable" column value denotes whether you would like to apply variable's information in file contents or not. Valid values are: yes, no
	 */
	@When("make HTTP POST request to upload the following files on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_put_request_to_upload_the_following_files_on_1(String appName,
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
		HttpResponse httpResponse = handler.httpUploadFormFiles(targetURL, httpRequest, null, null, false);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to upload the single/multiple files on the target server using HTTP POST. FilesDetails can be specified in the following format in the cucumber feature file:
	 * <blockquote><pre>
	 * | Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 * | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes/no                | test-data/uploads/Sample.pdf                                 |
	 * </pre></blockquote>
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param filesDetails - the cucumber data table to specify uploading file details in the format given below:
	 *   <blockquote><pre>
	 * 		| Part Name      | File Name          | Content Type         | Apply Variables       | Relative File Path (Relative to project directory)           |
	 *      | SampleFile.pdf | SampleFile.pdf     | application/pdf      | yes                   | test-data/uploads/Sample.pdf                                 |
	 *      | SampleFile2.pdf| SampleFile2.pdf    | application/pdf      | no                    | test-data/uploads/Sample2.pdf                                |
	 *   </pre></blockquote>
	 * 
	 *   Where "Apply Variable" column value denotes whether you would like to apply variable's information in file contents or not. Valid values are: yes, no
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP POST request to upload the following files on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_put_request_to_upload_the_following_files_on_1_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, 
			String requestVariableName, String responseVariableName, DataTable filesDetails) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpMultipartRequest httpRequest = new HttpMultipartRequest();
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);
		
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
		HttpResponse httpResponse = handler.httpUploadFormFiles(targetURL, httpRequest, null, null, false);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to make HTTP PUT request using the information present in request template file.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestTemplateFile - the relative path of the request template file (relative to project directory).
	 * <blockquote><pre>
	 * 		Example: test-data/update-user-request.json
	 * </pre></blockquote>
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
	
	/**
	 * Used to make HTTP PUT request using the information present in request template file.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestTemplateFile - the relative path of the request template file (relative to project directory).
	 * <blockquote><pre>
	 * 		Example: test-data/update-user-request.json
	 * </pre></blockquote>
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP PUT request using the contents of the following template file on target server [AppName={string}, TargetServer={string}, "
			+ "TargetURL={string}] with header info [RequestHeaderReferenceVariable={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_contents_of_the_following_template_file_on_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable,
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
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);	

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * Used to make HTTP POST request using the information present in request template file.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestTemplateFile - the relative path of the request template file (relative to project directory).
	 * <blockquote><pre>
	 * 		Example: test-data/update-user-request.json
	 * </pre></blockquote>
	 */
	@When("make HTTP POST request using the contents of the following template file on target server [AppName={string}, "
			+ "TargetServer={string}, TargetURL={string}] using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_contents_of_the_following_template_file_on_1(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept,
			String requestVariableName, String responseVariableName, DocString requestTemplateFile) {
		make_http_post_request_using_the_contents_of_the_following_template_file_on(appName, targetServer, targetURL, userProfile, contentType, accept, requestVariableName, responseVariableName, requestTemplateFile);
	}
	
	/**
	 * Used to make HTTP POST request using the information present in request template file.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param requestTemplateFile - the relative path of the request template file (relative to project directory).
	 * <blockquote><pre>
	 * 		Example: test-data/update-user-request.json
	 * </pre></blockquote>
	 */
	@When("make HTTP POST request using the contents of the following template file on target server [AppName={string}, "
			+ "TargetServer={string}, TargetURL={string}] using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] "
			+ "and variable info [ReqVar={string}, RespVar={string}]:")
	public void make_http_post_request_using_the_contents_of_the_following_template_file_on_1_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable,
			String requestVariableName, String responseVariableName, DocString requestTemplateFile) {
		make_http_post_request_using_the_contents_of_the_following_template_file_on_using_req_header_ref_variable(appName, targetServer, targetURL, userProfile, 
				requestHeaderReferenceVariable, requestVariableName, responseVariableName, requestTemplateFile);
	}
	
	/**
	 * This step is used to make HTTP PUT API call by specifying the variable name that contains JSON object.
	 * 
	 * @param jsonObjVariableName - the name of the variable that contains JSON document prepared using the read JSON file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@When("make HTTP PUT request by setting {string} JSON object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_put_request_by_setting_json_object_on(String jsonObjVariableName, String appName,
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
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to make HTTP PUT API call by specifying the variable name that contains JSON object.
	 * 
	 * @param jsonObjVariableName - the name of the variable that contains JSON document prepared using the read JSON file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP PUT request by setting {string} JSON object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_put_request_by_setting_json_object_on_using_req_header_ref_variable(String jsonObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String requestVariableName, String responseVariableName) {
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
		
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to make HTTP POST API call by specifying the variable name that contains JSON object.
	 * 
	 * @param jsonObjVariableName - the name of the variable that contains JSON document prepared using the read JSON file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@When("make HTTP POST request by setting {string} JSON object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_request_by_setting_json_object_on_1(String jsonObjVariableName, String appName,
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
	
	/**
	 * This step is used to make HTTP POST API call by specifying the variable name that contains JSON object.
	 * 
	 * @param jsonObjVariableName - the name of the variable that contains JSON document prepared using the read JSON file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP POST API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP POST request by setting {string} JSON object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_request_by_setting_json_object_on_1_using_req_header_ref_variable(String jsonObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String requestVariableName, String responseVariableName) {
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
		
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);	

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to make HTTP PUT API call by specifying the variable name that contains XML object.
	 * 
	 * @param xmlObjVariableName - the name of the variable that contains XML document prepared using the read XML file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@When("make HTTP PUT request by setting {string} XML object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_put_request_by_setting_xml_object_on(String xmlObjVariableName, String appName,
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
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to make HTTP PUT API call by specifying the variable name that contains XML object.
	 * 
	 * @param xmlObjVariableName - the name of the variable that contains XML document prepared using the read XML file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP PUT request by setting {string} XML object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_put_request_by_setting_xml_object_on_using_req_header_ref_variable(String xmlObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String requestVariableName, String responseVariableName) {
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
		
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPut(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to make HTTP POST API call by specifying the variable name that contains XML object.
	 * 
	 * @param xmlObjVariableName - the name of the variable that contains XML document prepared using the read XML file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@When("make HTTP POST request by setting {string} XML object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [Accept={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_request_by_setting_xml_object_on_1(String xmlObjVariableName, String appName,
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
	
	/**
	 * This step is used to make HTTP POST API call by specifying the variable name that contains XML object.
	 * 
	 * @param xmlObjVariableName - the name of the variable that contains XML document prepared using the read XML file step.
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP PUT API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param requestVariableName - the variable name that stores the HTTPRequest information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP POST request by setting {string} XML object on target server [AppName={string}, TargetServer={string}, TargetURL={string}] "
			+ "using [UserProfile={string}] with header info [RequestHeaderReferenceVariable={string}] and variable info [ReqVar={string}, RespVar={string}].")
	public void make_http_post_request_by_setting_xml_object_on_1_using_req_header_ref_variable(String xmlObjVariableName, String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String requestVariableName, String responseVariableName) {
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
		
		HttpRequest httpRequest = new HttpRequest().setPayload(contents);
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);	

		scenarioContext.addParamValue(requestVariableName, contents);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpPost(targetURL, httpRequest, null, null);
		
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to perform HTTP GET API call.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP GET API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
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
		HttpResponse httpResponse = scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile).httpGet(targetURL, httpRequest.getHeaders(), null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to perform HTTP GET API call.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP GET API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP GET request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}] "
			+ "with header info [RequestHeaderReferenceVariable={string}] and variable info [RespVar={string}].")
	public void make_http_get_request_on_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		HttpResponse httpResponse = scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile)
				.httpGet(targetURL, httpRequest.getHeaders(), null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to perform HTTP DELETE API call.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP DELETE API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
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
		HttpResponse httpResponse = handler.httpDelete(targetURL, httpRequest.getHeaders(), null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to perform HTTP DELETE API call.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP DELETE API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP DELETE request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}] "
			+ "with header info [RequestHeaderReferenceVariable={string}] and variable info [RespVar={string}].")
	public void make_http_delete_request_on_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpDelete(targetURL, httpRequest.getHeaders(), null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to perform HTTP HEAD API call.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP HEAD API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param contentType - content type / MediaType of the data present into request body / payload.
	 * @param accept - the content type / MediaType expected in HTTP response body.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@When("make HTTP HEAD request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}] with header info [ContentType={string}, Accept={string}] and variable info [RespVar={string}].")
	public void make_http_head_request_on(String appName,
			String targetServer, String targetURL, String userProfile, String contentType, String accept, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setPayloadType(contentType);
		httpRequest.setResponseContentType(accept);

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpHead(targetURL, httpRequest.getHeaders(), null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to perform HTTP HEAD API call.
	 * 
	 * @param appName - the configured application name.
	 * @param targetServer - it is an API Target Server Name that is configured in ApiConfig.yaml file.
	 * @param targetURL - the target URL where to make HTTP HEAD API call.
	 * @param userProfile - the name of the user profile that is configured in AppConfig.yaml file that will be used to login on target server.
	 * @param requestHeaderReferenceVariable - request header reference variable that contains HTTP request header parameter information.
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 */
	@SuppressWarnings("unchecked")
	@When("make HTTP HEAD request on target server [AppName={string}, TargetServer={string}, TargetURL={string}] using [UserProfile={string}]"
			+ " with header info [RequestHeaderReferenceVariable={string}] and variable info [RespVar={string}].")
	public void make_http_head_request_on_using_req_header_ref_variable(String appName,
			String targetServer, String targetURL, String userProfile, String requestHeaderReferenceVariable, String responseVariableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		HttpRequest httpRequest = new HttpRequest();
		Map<String, String> params = (Map<String, String>) scenarioContext.getParamValue(requestHeaderReferenceVariable);
		httpRequest.getHeaders().putAll(params);	

		targetURL = scenarioContext.applyParamsValueOnText(targetURL);
		AbstractApiActionHandler handler =  scenarioContext.getApiTestManager().getActionHandler(appName, targetServer, userProfile);
		HttpResponse httpResponse = handler.httpHead(targetURL, httpRequest.getHeaders(), null, null);
		scenarioContext.addParamValue(responseVariableName, httpResponse);
	}
	
	/**
	 * This step is used to verify HTTP Status code in HTTP Response.
	 * 
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param httpStatusCode - HTTP Response Code, for more details refer io.netty.handler.codec.http.HttpResponseStatus class. 
	 */
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
	
	/**
	 * This step is used to verify the header information: Content-Type
	 * 
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param contentType - the content type header value in HTTP response.
	 */
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
	 * Verifies the HTTP response header information.
	 * 
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param expectedHeaderInfo - the expected header information in HTTP response in the format given below (specified as DataTable in Cucumber):
	 *   <blockquote><pre>
	 * 	  | Header Name         | Expected Value            | Text Match Mechanism          |
	 *    | Content-Type        | application/json          | icExactMatchWithExpectedValue |
	 *    | Access-Token        | tttttttt                  | exactMatchWithExpectedValue   |
	 *   </pre></blockquote>
	 *   
	 *   Where:
	 *   	Expected Value: It is a string value
	 *   	Text Match Mechanism: the text match mechanism used to verify the header actual value with expected value.
	 *   			For text match mechanism valid values, refer {@link TextMatchMechanism} class.
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
			
			httpResponse.getValidator().validateExpectedHeaderValue(headerName, expectedValue, TextMatchMechanism.valueOf2(textMatchMechanism));
		}
	}
	
	/**
	 * Used to verify the body/payload contents of HTTP Response. 
	 * 
	 * @param httpResponseVariableName - the variable name that stores the HTTPResponse information.
	 * @param keywordsInfo - the keywords that need to verified in HTTP response body. The syntax for expecting keywords is given below:
	 *   <blockquote><pre>
	 *  		{ keywords: ["1", "2"], inOrder: yes/no }
	 *   </pre></blockquote>
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
	 * Verifies the HTTP response contains the JSON data with the expected parameter's information.
	 * 
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param expectedParamsInfo - the expected information that need to be verified in JSON based HTTP response. The DataTable syntax for specifying the expected information is given below:
	 * <blockquote><pre>
	 * 	 | Parameter/JSON Path        | Operator           | Expected Information                                                                                               |
	 *   | $.name                     | =                  | John Hopkins                                                                                                       |
	 *   | $.jobTitles                | contains           | {ev: ["Cable operator", "Accountant"], valueType: "string-list", inOrder: "yes", ignoreCase: "no", textMatchMechanism: "exactMatchWithExpectedValue"} |
	 * 	
	 *   For more info on JSON Path, please refer @see (@link https://github.com/json-path/JsonPath}
	 *   For more details on Operators, please refer @see {@link ValueMatchOperator} enum.
	 *   For more details on Expected Information please refer @see {@link ExpectedInfo} class.
	 *   For text match mechanism valid values, refer {@link TextMatchMechanism} class.
	 * </pre></blockquote>
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
	 * Verifies the HTTP response contains the XML data with the expected XPATH information.
	 * 
	 * @param responseVariableName - the variable name that stores the HTTPResponse information.
	 * @param expectedParamsInfo - the expected information that need to be verified in XML based HTTP response. The DataTable syntax for specifying the expected information is given below:
	 * <blockquote><pre>
	 *   | Parameter/XPATH                                                   | Operator     | Expected Info                   |
	 *   | //user[@name='test-name']                                         | present      |                                 |
	 *   | //user[@name='unknown']                                           | not-present  |                                 |
	 *   | {path: "count(//user[@name='unknown'])", valueType: "integer"}    | =            | { ev: 50, valueType: 'integer'} |
	 *   | {path: "count(//user[@name='unknown'])", valueType: "integer"}    | >            | { ev: 5, valueType: 'integer'}  |
	 *   | {path: "//user[@name='test-name']/@state", valueType: "string"}   | =            | { ev: "Virginia", valueType: 'string'}  |
	 * 
	 *   For more details on parameter/xpath, please refer @see {@link ParamPath} class.
	 *   For more details on Operators, please refer @see {@link ValueMatchOperator} enum.
	 *   For more details on Expected Information please refer @see {@link ExpectedInfo} class.
	 * </pre></blockquote>
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
	 * 
	 * 
	 * @param httpResponseVariableName - the variable name that stores the HTTPResponse information.
	 * @param expectedFileInfo - the expected file name of the downloaded file. File is downloaded into test-results/downloads directory. 
	 *  <blockquote><pre>
	 * 	  Syntax for specifying the expected file information is given below:
	 * 
	 *    {expectedFileName: "Sample.pdf", textMatchMechanism: "startsWithExpectedValue", deleteFile: true/false}
	 *  </pre></blockquote>
	 *  
	 *    For text match mechanism valid values, refer {@link TextMatchMechanism} class.
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
		
		httpResponse.getValidator().validateFileDownloaded(expectedFileName, TextMatchMechanism.valueOf2(textMatchMechanism), deleteFile);
	}
	
	/**
	 * Used to verify the contents of the downloaded file based on the keywords match mechanism.
	 * 
	 * @param httpResponseVariableName - the variable name that stores the HTTPResponse information.
	 * @param keywordsInfo - the keywords that need to be matched in the downloaded file. The syntax for specifying the keywords is given below:
	 * <blockquote><pre>
	 * 		{ keywords: ["1", "2"], inOrder: yes/no }
	 * </pre></blockquote>
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
	
	/**
	 * Used to retrieve the JSON path value from the HTTP response payload / body and store into a variable. 
	 * So that we can pass the variable's information in other steps.
	 * 
	 * @param jsonPath - the JSON path used to retrieve the parameter value from the JSON based HTTP response.
	 * 		For more info on JSON Path, please refer @see (@link https://github.com/json-path/JsonPath}
	 * @param httpResponseVariableName - the variable name that stores the HTTPResponse information.
	 * @param variableName - the name of the variable in which we want to store retrieved information.
	 */
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

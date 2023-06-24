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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.data.messaging;

import java.util.List;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.SmartRegistry;
import org.uitnet.testing.smartfwk.api.core.support.MessageBucketSequenceNumberGenerator;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.SmartDataValidator;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.messaging.AbstractMessageHandler;
import org.uitnet.testing.smartfwk.messaging.MessageContentType;
import org.uitnet.testing.smartfwk.messaging.MessageInfo;

import com.jayway.jsonpath.DocumentContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to message management to perform message
 * send, get and verify operations.
 * 
 * @author Madhav Krishna
 *
 */
public class SmarMessagingManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmarMessagingManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to send text messages to messaging server by sending message to message handler.
	 * 
	 * @param messageHandlerName - the name of the configured message handler.
	 * @param textMessage - the text message that you want to send to the messaging server.
	 */
	@When("send the following text message to {string} message handler:")
	public void send_the_following_text_message_to_message_handler(String messageHandlerName, DocString textMessage) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		String textMessageAsStr = textMessage.getContent();
		textMessageAsStr = scenarioContext.applyParamsValueOnText(textMessageAsStr);

		AbstractMessageHandler messageHandler = SmartRegistry.getMessageHandlerManager()
				.getMessageHandler(messageHandlerName);

		try {
			messageHandler.sendMessage(textMessageAsStr, MessageContentType.STRING, null);
		} catch (Exception ex) {
			Assert.fail("Failed to send text message to '" + messageHandlerName + "' message handler.", ex);
		}

	}

	/**
	 * This is a flexible step definition to send any type of message to message handler so that it can sent to the proper messaging service.
	 * 
	 * @param messageContentType - the content type of the message like json, xml, string, file, byte_array etc.
	 * 		For more details, please refer {@link MessageContentType} link.
	 * 
	 * @param otherContentType - if messageContentType = other then you can specify other message content type. 
	 * 		NOTE: Other message content value will be sent as byte array.
	 * 
	 * @param messageHandlerName - the name of the message handler using that message will be delivered to target.
	 * @param message - the message details based on the message content type. 
	 * 		If messageContentType = binary_file then message should contain the file path, system will send its contents as byte array.
	 * 		If messageContentType = text_file then message should contain the file path, system will send its contents as string.
	 */
	@When("send the following message with [MessageContentType={string}, OtherContentType={string}] to {string} message handler:")
	public void send_the_following_message_with(String messageContentType, String otherContentType,
			String messageHandlerName, DocString message) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		String messageAsStr = message.getContent();
		messageAsStr = scenarioContext.applyParamsValueOnText(messageAsStr);

		MessageContentType contentType = MessageContentType.valueOf2(messageContentType);
		AbstractMessageHandler messageHandler = SmartRegistry.getMessageHandlerManager()
				.getMessageHandler(messageHandlerName);

		try {
			messageHandler.sendMessage(messageAsStr, contentType, otherContentType);
		} catch (Exception ex) {
			Assert.fail("Failed to send message to '" + messageHandlerName + "' message handler.", ex);
		}

	}
	
	/**
	 * Use to start message collection from the message handler and store the received messages into bucket 
	 * variable so that the message contents can be verified in future steps.
	 * 
	 * @param messageHandlerName - the name of the configured message handler.
	 * @param bucketVariableName - the bucket variable name that stores the received messages.
	 */
	@When("start message collection from {string} message handler and store into {string} bucket variable.")
	public void start_message_collection_from_message_handler(String messageHandlerName, String bucketVariableName) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		String bucketId = prepareBucketId(bucketVariableName);
		scenarioContext.addParamValue(bucketVariableName, bucketId);
		AbstractMessageHandler messageHandler = SmartRegistry.getMessageHandlerManager()
				.getMessageHandler(messageHandlerName);
		messageHandler.startMessageRecorder(bucketId);
	}

	/**
	 * Used to retrieve all the messages that are collected so far from the bucket variable. Note to get the messages, message collection
	 * must be started before using this step.
	 * 
	 * @param messageHandlerName - the name of the configured message handler.
	 * @param bucketVariableReference - the name of the bucket variable that stores the collected message.
	 * 		NOTE: this variable is created when we use step to start message collection.
	 * @param variableName - the name of the variable that stores the retrieved messages as JSON object.
	 */
	@Then("get messages from [MessageHandler={string}, BucketVariableRef={string}] bucket as JSON object and store into {string} variable.")
	public void get_messages_from_bucket_as_json_and_store_into_variable(String messageHandlerName,
			String bucketVariableReference, String variableName) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		String bucketId = scenarioContext.getParamValueAsString(bucketVariableReference);

		AbstractMessageHandler messageHandler = SmartRegistry.getMessageHandlerManager()
				.getMessageHandler(messageHandlerName);

		DocumentContext recordedMessagesContext = messageHandler.getRecordedMessagesAsJsonObject(bucketId);
		scenarioContext.addParamValue(variableName, recordedMessagesContext);
	}
	
	/**
	 * Used to verify the number of messages inside the message bucket meets the expected value.
	 * 
	 * @param messageHandlerName - the name of the message handler.
	 * @param bucketVariableReference - the name of the bucket variable that stores the collected message.
	 * 		NOTE: this variable is created when we use step to start message collection.
	 * @param operator - the operator to verify the actual value with expected value. 
	 * 		Valid operators: =, !=, >, >=, <, <=
	 * @param expectedSize - the expected number of messages in the bucket.
	 * @param maxTimeToWaitInSeconds - the time to wait until actual size meets the expected size.
	 */
	@Then("verify the size of [MessageHandler={string}, BucketVariableRef={string}] message bucket {string} {int} [MaxTimeToWaitInSeconds={int}].")
	public void verify_the_bucket(String messageHandlerName,
			String bucketVariableReference, String operator, int expectedSize, Integer maxTimeToWaitInSeconds) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String bucketId = scenarioContext.getParamValueAsString(bucketVariableReference);
		if (bucketId == null) {
			Assert.fail("Message collector not started for '" + bucketVariableReference + "' bucket. "
					+ "To start message collection to store messages into bucket, please use 'start message collection from...' step.");
		}

		AbstractMessageHandler messageHandler = SmartRegistry.getMessageHandlerManager()
				.getMessageHandler(messageHandlerName);

		int numIterations = (maxTimeToWaitInSeconds == 0 || maxTimeToWaitInSeconds <= 2) ? 1
				: maxTimeToWaitInSeconds / 2;

		DocumentContext recordedMessagesContext;
		ValueMatchOperator op = ValueMatchOperator.valueOf2(operator);
		
		for (int i = 1; i <= numIterations; i++) {
			try {
				recordedMessagesContext = messageHandler.getRecordedMessagesAsJsonObject(bucketId);
				int actualSize = recordedMessagesContext.read("$.length()");
				switch(op) {
				case EQUAL_TO: {
					if(actualSize != expectedSize) {
						Assert.fail("Actual size (" + actualSize + ") should be same as expected size (" + expectedSize + ").");
					}
					break;
				}
				case NOT_EQUAL_TO: {
					if(actualSize == expectedSize) {
						Assert.fail("Actual size (" + actualSize + ") should not be same as expected size (" + expectedSize + ").");
					}
					break;
				}
				case GREATER_THAN: {
					if(actualSize <= expectedSize) {
						Assert.fail("Actual size (" + actualSize + ") should be greter than the expected size (" + expectedSize + ").");
					}
					break;
				}
				case GREATER_THAN_EQUAL_TO: {
					if(actualSize < expectedSize) {
						Assert.fail("Actual size (" + actualSize + ") should be greater than or equal to the expected size (" + expectedSize + ").");
					}
					break;
				}
				case LESS_THAN: {
					if(actualSize >= expectedSize) {
						Assert.fail("Actual size (" + actualSize + ") should be less than the expected size (" + expectedSize + ").");
					}
					break;
				}
				case LESS_THAN_EQUAL_TO: {
					if(actualSize > expectedSize) {
						Assert.fail("Actual size (" + actualSize + ") should be less than or equal to the expected size (" + expectedSize + ").");
					}
					break;
				}
				default:
					Assert.fail("Operatoe '" + op.getOperator() + "' is not supported.");
					break;
				}
			} catch (Throwable th) {
				if (i == numIterations) {
					Assert.fail("Failed to meet all expected crietrias. Reason: " + th.getMessage());
				} else {
					scenarioContext.waitForSeconds(2);
				}
			}
		}
		
	}

	/**
	 * Used to verify the contents of the message bucket until all the criteria are met. If any criteria is failed then 
	 * it will retry every 2 seconds until the the timeout happened.
	 * 
	 * @param messageHandlerName - the name of the configured message handler.
	 * @param bucketVariableReference - the name of the bucket variable that stores the collected message.
	 * @param maxTimeToWaitInSeconds - the time to wait until all expected criteria meet the expectations.
	 * @param expectedCriterias - Criteria that must be meet. Criteria can be specified in Data Table format as given below:
	 *  <blockquote><pre>
	 *   | Parameter/JSON Path        | Operator           | Expected Information  |
	 *   | $.[*].message              | contains           | Hello World!!         |
	 *   
	 *   Refer {@link https://github.com/json-path/JsonPath} link to learn more on JSON path. 
	 *   	Consider {@link MessageInfo} class as record in JSON array. JSON path should be prepared on array of {@link MessageInfo} class.
	 *   For supported operators refer {@link ValueMatchOperator}.
	 *   For expected information JSON format please refer {@link ExpectedInfo}.
	 * </pre></blockquote>
	 * 		
	 */
	@Then("verify [MessageHandler={string}, BucketVariableRef={string}] message bucket meets the following criteria [MaxTimeToWaitInSeconds={int}]:")
	public void verify_message_bucket_meets_the_following_criteria(String messageHandlerName,
			String bucketVariableReference, Integer maxTimeToWaitInSeconds, DataTable expectedCriterias) {
		if (!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\""
					+ scenarioContext.getLastConditionName() + "\".");
			return;
		}

		String bucketId = scenarioContext.getParamValueAsString(bucketVariableReference);
		if (bucketId == null) {
			Assert.fail("Message collector not started for '" + bucketVariableReference + "' bucket. "
					+ "To start message collection to store messages into bucket, please use 'start message collection from...' step.");
		}

		AbstractMessageHandler messageHandler = SmartRegistry.getMessageHandlerManager()
				.getMessageHandler(messageHandlerName);

		int numIterations = (maxTimeToWaitInSeconds == 0 || maxTimeToWaitInSeconds <= 2) ? 1
				: maxTimeToWaitInSeconds / 2;

		DocumentContext recordedMessagesContext;
		List<List<String>> rows = expectedCriterias.asLists();
		List<String> row;
		String jsonPath, oper, expectedInfo;

		for (int i = 1; i <= numIterations; i++) {
			try {
				recordedMessagesContext = messageHandler.getRecordedMessagesAsJsonObject(bucketId);

				for (int j = 1; j < rows.size(); j++) {
					row = rows.get(j);
					jsonPath = row.get(0);
					oper = row.get(1);
					expectedInfo = row.get(2);

					jsonPath = scenarioContext.applyParamsValueOnText(jsonPath);
					expectedInfo = scenarioContext.applyParamsValueOnText(expectedInfo);

					// verify the actual value against the expected value.
					SmartDataValidator.validateJsonOrYamlData(recordedMessagesContext, jsonPath, oper, expectedInfo);
				}

			} catch (Throwable th) {
				if (i == numIterations) {
					Assert.fail("Failed to meet all expected crietrias. Reason: " + th.getMessage());
				} else {
					scenarioContext.waitForSeconds(2);
				}
			}
		}
	}

	/**
	 * Used to prepare message bucket ID.
	 * 
	 * @param bucketVariable - the name of the bucket variable.
	 * @return - the unique generated bucket ID.
	 */
	private String prepareBucketId(String bucketVariable) {
		MessageBucketSequenceNumberGenerator seqGen = MessageBucketSequenceNumberGenerator.getInstance();
		return bucketVariable + "-#-" + seqGen.next();
	}
}
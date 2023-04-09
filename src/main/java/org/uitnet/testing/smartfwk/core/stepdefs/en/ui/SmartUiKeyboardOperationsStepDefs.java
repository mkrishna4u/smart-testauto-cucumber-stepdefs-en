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

import org.openqa.selenium.Keys;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.en.When;

/**
 * Lists step definitions related to keyboard commands on the UI page objects / page elements.
 * 
 * Example: 
 * <blockquote><pre>
 *   > ["CONTROL", "A"] to select all text in a specified page object / page element.
 *   > ["CONTROL", "C"] to copy selected text in clipboard.
 * </pre></blockquote>
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiKeyboardOperationsStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartUiKeyboardOperationsStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to perform single / multiple keys operations. like copy, paste, tab and function key operations.
	 * <blockquote><pre>
	 * To specify multiple keys, format is given below:
	 * 
	 * ["CONTROL", "SHIFT", "A"]
	 * 
	 * It will always fire keydown on all keys other than last specified key. Also it will fire keypress event on last key.
	 * If there is only one key then it will fire keypress event.
	 * 
	 * Refer {@link Keys} enum for more information on key name.
	 * </pre></blockquote>
	 * 
	 * @param keys - comma separated keys within square bracket.
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param operation - meaningful expected operation name.
	 * 
	 */
	@When("use {string} keys on {string} page element to {string}.")
	public void use_keys_on_page_object_to_perform_operation(String keys, String po, String operation) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(keys, false);
		List<String> keyList = jsonReader.readValuesAsList("$");
		String[] keysChord = PageObjectUtil.prepareKeysChord(keyList);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", new String[] {},
				new Object[] { }, poInfo, scenarioContext);
		
		domObjectValidator.sendKeys(poInfo.getMaxIterationsToLocateElements(), Keys.chord(keysChord));
	}
	
	/**
	 * Used to perform single / multiple keys operations. like copy, paste, tab and function key operations.
	 * <blockquote><pre>
	 * To specify multiple keys, format is given below:
	 * 
	 * ["CONTROL", "SHIFT", "A"]
	 * 
	 * It will always fire keydown on all keys other than last specified key. Also it will fire keypress event on last key.
	 * If there is only one key then it will fire keypress event.
	 * 
	 * Refer {@link Keys} enum for more information on key name.
	 * </pre></blockquote>
	 * 
	 * @param keys - comma separated keys within square bracket.
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param operation - meaningful expected operation name.
	 * 
	 */
	@When("use {string} keys on {string} page object to {string}.")
	public void use_keys_on_page_object_to_perform_operation_1(String keys, String po, String operation) {
		use_keys_on_page_object_to_perform_operation(keys, po, operation);
	}
		
	/**
	 * Used to perform keys and mouse operations together like "CONTROL + click" etc.
	 * <blockquote><pre>
	 * To specify multiple keys, format is given below:
	 * 
	 * ["CONTROL", "SHIFT"]
	 * 
	 * It will always fire keydown on all keys and then the mouse event. 
	 * 
	 * Refer {@link Keys} enum for more information on key name.
	 * 
	 * NOTE: At the end of this step it will release all the keydown operations.
	 * </pre></blockquote>
	 * 
	 * @param keys - comma separated keys within square bracket.
	 * @param mouseEvent - supported mouseEvents: click, rightClick, doubleClick.
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param operation - meaningful expected operation name.
	 * 
	 */
	@When("use {string} keys and {string} mouse event together on {string} page element to {string}.")
	public void use_keys_and_mouse_event_together_on_page_object_to(String keys, String mouseEvent, String po, String operation) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		
		JsonDocumentReader jsonReader = new JsonDocumentReader(keys, false);
		List<String> keyList = jsonReader.readValuesAsList("$");
				
		for(int i = 0; i < keyList.size(); i++) {
			try {
				String key = keyList.get(i);
				Keys selKey = PageObjectUtil.findSeleniumKeysByName(key);
				if(selKey == null) { continue; }
				if(i == (keyList.size() - 1)) {
					//keydown event
					PageObjectUtil.invokeValidatorMethod("performKeyDown", new String[] { Keys.class.getTypeName(), Integer.TYPE.getTypeName() },
						new Object[] { selKey, poInfo.getMaxIterationsToLocateElements() }, poInfo,
						scenarioContext);
					
					// mouse event
					PageObjectUtil.invokeValidatorMethod(mouseEvent, new String[] { Integer.TYPE.getTypeName() },
							new Object[] { poInfo.getMaxIterationsToLocateElements() }, poInfo,
							scenarioContext);
				} else {
					//keydown event
					PageObjectUtil.invokeValidatorMethod("performKeyDown", new String[] { Keys.class.getTypeName(), Integer.TYPE.getTypeName() },
						new Object[] { selKey, poInfo.getMaxIterationsToLocateElements() }, poInfo,
						scenarioContext);
				}
			} catch(Throwable th) {
				// release all keys
				for(int j = i; j >= 0; j--) {
					String key = keyList.get(j);
					Keys selKey = PageObjectUtil.findSeleniumKeysByName(key);
					if(selKey == null) { continue; }
					if(j < (keyList.size() - 1)) {
						PageObjectUtil.invokeValidatorMethod("performKeyUp", new String[] { Keys.class.getTypeName(), Integer.TYPE.getTypeName() },
							new Object[] { selKey, poInfo.getMaxIterationsToLocateElements() }, poInfo,
							scenarioContext);
					} 
				}
				break;
			}
		}
	}
	
	/**
	 * Used to perform keys and mouse operations together like "CONTROL + click" etc.
	 * <blockquote><pre>
	 * To specify multiple keys, format is given below:
	 * 
	 * ["CONTROL", "SHIFT"]
	 * 
	 * It will always fire keydown on all keys and then the mouse event. 
	 * 
	 * Refer {@link Keys} enum for more information on key name.
	 * 
	 * NOTE: At the end of this step it will release all the keydown operations.
	 * </pre></blockquote>
	 * 
	 * @param keys - comma separated keys within square bracket.
	 * @param mouseEvent - supported mouseEvents: click, rightClick, doubleClick.
	 * @param po - the page object / page element can be specified in two way:
	 * <blockquote><pre>
	 *     Direct way: myapp.XyzPO.poObject
	 *     JSON way:  (Refer {@link PageObject}). Example:
	 *       {name: "myapp.XyzPO.poObject", maxTimeToWaitInSeconds: 6, params: {param1: "param1Value", param2: "param2Value"}}
	 *     PO classes are present in ./src/main/page_objects/ directory.
	 * </pre></blockquote>      
	 * @param operation - meaningful expected operation name.
	 * 
	 */
	@When("use {string} keys and {string} mouse event together on {string} page object to {string}.")
	public void use_keys_and_mouse_event_together_on_page_object_to_1(String keys, String mouseEvent, String po, String operation) {
		use_keys_and_mouse_event_together_on_page_object_to(keys, mouseEvent, po, operation);
	}
	
	
	
}

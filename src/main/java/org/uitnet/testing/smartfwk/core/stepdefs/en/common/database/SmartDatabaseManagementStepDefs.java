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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.database;

import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.SmartRegistry;
import org.uitnet.testing.smartfwk.database.AbstractDatabaseActionHandler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;

/**
 * Step definitions for database management to perform CRUD operations.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartDatabaseManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	/**
	 * Constructor
	 */
	public SmartDatabaseManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	@When("get first entry of {string} parameter from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Get Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		DocumentContext dbResults = dbActionHandler.getDataAsJsonDocument(tableOrEntityName, queryTxt);
		String variableValue = dbResults.read("$[0]", String.class);
		scenarioContext.addParamValue(variableName, variableValue);
		scenarioContext.log(variableName + ": " + variableValue);
	}
	
	@When("get {string} from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	@When("get first entry of {string} column from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_2(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	@When("get {string} from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_3(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_first_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	@When("get last entry of {string} parameter from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_last_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Get Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		DocumentContext dbResults = dbActionHandler.getDataAsJsonDocument(tableOrEntityName, queryTxt);
		List<String> variableValues = dbResults.read("$", new TypeRef<List<String>>() {});
		if(variableValues != null && variableValues.size() > 0) {
			String val = variableValues.get(variableValues.size() - 1);
			scenarioContext.addParamValue(variableName, val);
			scenarioContext.log(variableName + ": " + val);
		} else {
			scenarioContext.addParamValue(variableName, null);
			scenarioContext.log(variableName + ": " + null);
		}
	}
	
	@When("get last entry of {string} column from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_last_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_last_entry_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	@When("get all entries of {string} parameter from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_all_entries_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Get Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		DocumentContext dbResults = dbActionHandler.getDataAsJsonDocument(tableOrEntityName, queryTxt);
		List<String> variableValue = dbResults.read("$", new TypeRef<List<String>>() {});
		scenarioContext.addParamValue(variableName, variableValue);
		scenarioContext.log(variableName + ": " + variableValue);
	}
	
	@When("get all entries of {string} column from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_all_entries_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_all_entries_for_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	@When("get {string} entity data as JSON document using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_entity_data_as_json_document_using_query_below_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Get Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		DocumentContext dbResults = dbActionHandler.getDataAsJsonDocument(tableOrEntityName, queryTxt);
		scenarioContext.addParamValue(variableName, dbResults);
	}
	
	@When("get {string} table data as JSON document using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_entity_data_as_json_document_using_query_below_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_entity_data_as_json_document_using_query_below_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	@When("update {string} entity data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void update_entity_data_using_query_below_app_name_database_profile_name(
			String paramOrColumnName, String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Update Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		dbActionHandler.updateData(tableOrEntityName, queryTxt);
	}
	
	@When("update {string} table data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void update_entity_data_using_query_below_app_name_database_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		update_entity_data_using_query_below_app_name_database_profile_name(paramOrColumnName, tableOrEntityName, appName, databaseProfileName, query);
	}
	
	@When("delete {string} entity data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void delete_entity_data_using_query_below_app_name_database_profile_name(
			String paramOrColumnName, String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Delete Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		dbActionHandler.deleteData(tableOrEntityName, queryTxt);
	}
	
	@When("delete {string} table data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void delete_entity_data_using_query_below_app_name_database_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		delete_entity_data_using_query_below_app_name_database_profile_name(paramOrColumnName, tableOrEntityName, appName, databaseProfileName, query);
	}

	@When("insert new data into {string} entity using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void insert_new_data_into_entity_using_query_below_app_name_database_profile_name(
			String paramOrColumnName, String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Insert Data Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		dbActionHandler.insertData(tableOrEntityName, queryTxt);
	}
	
	@When("insert new data into {string} table using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void insert_new_data_into_entity_using_query_below_app_name_database_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		insert_new_data_into_entity_using_query_below_app_name_database_profile_name(paramOrColumnName, tableOrEntityName, appName, databaseProfileName, query);
	}
}
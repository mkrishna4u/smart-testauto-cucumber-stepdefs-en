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
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to database management to perform CRUD operations on database.
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
	
	/**
	 * Used to get first entry of the specified parameter from the specified entity using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the parameter.
	 * @param tableOrEntityName - the name of the entity.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get first entry of {string} parameter from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
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
	
	/**
	 * Used to get first entry of the specified parameter from the specified entity using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the parameter.
	 * @param tableOrEntityName - the name of the entity.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get {string} from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to get first entry of the specified column from the specified table using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the column.
	 * @param tableOrEntityName - the name of the table.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get first entry of {string} column from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_2(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to get first entry of the specified column from the specified table using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the column.
	 * @param tableOrEntityName - the name of the table.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get {string} from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_3(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_first_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to get last entry of the specified parameter from the specified entity using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the parameter.
	 * @param tableOrEntityName - the name of the entity.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get last entry of {string} parameter from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_last_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
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
	
	/**
	 * Used to get last entry of the specified column from the specified table using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the column.
	 * @param tableOrEntityName - the name of the table.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get last entry of {string} column from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_last_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_last_entry_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to get all entries of the specified parameter from the specified entity using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the parameter.
	 * @param tableOrEntityName - the name of the entity.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get all entries of {string} parameter from {string} entity using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_all_entries_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
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
	
	/**
	 * Used to get all entries of the specified column from the specified table using the specified query.
	 * And stores the retrieved data into new variable name. 
	 * 
	 * @param paramOrColumnName - the name of the column.
	 * @param tableOrEntityName - the name of the table.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get all entries of {string} column from {string} table using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_all_entries_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name_1(
			String paramOrColumnName, String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_all_entries_of_parameter_from_entity_and_store_into_variable_app_name_db_profile_name(paramOrColumnName, tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to get the entity data as JSON document using the specified query and store into new variable.
	 * 
	 * @param tableOrEntityName - the name of the entity.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get {string} entity data as JSON document using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_entity_data_as_json_document_using_query_below_and_store_into_variable_app_name_db_profile_name(
			String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Get Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		DocumentContext dbResults = dbActionHandler.getDataAsJsonDocument(tableOrEntityName, queryTxt);
		scenarioContext.addParamValue(variableName, dbResults);
	}
	
	/**
	 * Used to get the table data as JSON document using the specified query and store into new variable.
	 * 
	 * @param tableOrEntityName - the name of the table.
	 * @param variableName - the name of the variable where the retrieved information will be stored.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to retrieve the information.
	 */
	@When("get {string} table data as JSON document using query below and store into {string} variable."
			+ " Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void get_entity_data_as_json_document_using_query_below_and_store_into_variable_app_name_db_profile_name_1(
			String tableOrEntityName, String variableName, String appName, String databaseProfileName, DocString query) {
		get_entity_data_as_json_document_using_query_below_and_store_into_variable_app_name_db_profile_name(tableOrEntityName, variableName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to update entity data using the specified query.
	 * 
	 * @param tableOrEntityName - the name of the entity.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to update the information. Multiple queries can be separated using separator: ${new;}
	 */
	@When("update {string} entity data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void update_entity_data_using_query_below_app_name_database_profile_name(
			String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Update Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		
		String[] queries = queryTxt.split("\\$\\{new;}");
		for(String q : queries) {
			if(StringUtil.isEmptyAfterTrim(q)) { continue; }
			dbActionHandler.updateData(tableOrEntityName, q);
		}
	}
	
	/**
	 * Used to update table data using the specified query.
	 * 
	 * @param tableOrEntityName - the name of the table.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to update the information. Multiple queries can be separated using separator: ${new;}
	 */
	@When("update {string} table data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void update_entity_data_using_query_below_app_name_database_profile_name_1(
			String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		update_entity_data_using_query_below_app_name_database_profile_name(tableOrEntityName, appName, databaseProfileName, query);
	}
	
	/**
	 * Used to delete the entity data using the specified query.
	 * 
	 * @param tableOrEntityName - the name of the entity.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to delete the information. Multiple queries can be separated using separator: ${new;}
	 */
	@When("delete {string} entity data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void delete_entity_data_using_query_below_app_name_database_profile_name(
			String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Delete Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		
		String[] queries = queryTxt.split("\\$\\{new;}");
		for(String q : queries) {
			if(StringUtil.isEmptyAfterTrim(q)) { continue; }
			dbActionHandler.deleteData(tableOrEntityName, q);
		}
	}
	
	/**
	 * Used to delete the table data using the specified query.
	 * 
	 * @param tableOrEntityName - the name of the table.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to delete the information. Multiple queries can be separated using separator: ${new;}
	 */
	@When("delete {string} table data using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void delete_entity_data_using_query_below_app_name_database_profile_name_1(
			String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		delete_entity_data_using_query_below_app_name_database_profile_name(tableOrEntityName, appName, databaseProfileName, query);
	}

	/**
	 * Used to insert new data into the entity using the specified query.
	 * 
	 * @param tableOrEntityName - the name of the entity.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to insert the information. Multiple queries can be separated using separator: ${new;}
	 */
	@When("insert new data into {string} entity using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void insert_new_data_into_entity_using_query_below_app_name_database_profile_name(
			String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		String queryTxt = query.getContent();
		queryTxt = scenarioContext.applyParamsValueOnText(queryTxt);
		
		scenarioContext.log("Insert Data Query: " + queryTxt);

		AbstractDatabaseActionHandler dbActionHandler = SmartRegistry.getDatabaseManager()
				.getDatabaseActionHandler(appName, databaseProfileName);
		
		String[] queries = queryTxt.split("\\$\\{new;}");
		for(String q : queries) {
			if(StringUtil.isEmptyAfterTrim(q)) { continue; }
			dbActionHandler.insertData(tableOrEntityName, q);
		}
		
	}
	
	/**
	 * Used to insert new data into the table using the specified query.
	 * 
	 * @param tableOrEntityName - the name of the table.
	 * @param appName - the name of the configured application.
	 * @param databaseProfileName - the name of the database profile name (that is configured in the system).
	 * @param query - query to insert the information. Multiple queries can be separated using separator: ${new;}
	 */
	@When("insert new data into {string} table using query below. Target DB Info [AppName={string}, DatabaseProfileName={string}]:")
	public void insert_new_data_into_entity_using_query_below_app_name_database_profile_name_1(
			String tableOrEntityName, String appName, String databaseProfileName, DocString query) {
		insert_new_data_into_entity_using_query_below_app_name_database_profile_name(tableOrEntityName, appName, databaseProfileName, query);
	}
}
/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.data;

import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.file.reader.CSVFileReader;
import org.uitnet.testing.smartfwk.ui.core.file.reader.support.Table;

import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to CSV file data reading.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCsvDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartCsvDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to read the CSV file data into tabular form and store into variable. By default it uses the comma separated values from the CSV file.
	 * 
	 * @param relativeCSVFilePath - the relative path of the CSV file with respect to project root directory.
	 * @param variableName - the name of the variable that stores the CSV file contents. 
	 */
	@When("read {string} CSV file into tabular form and store into {string} CSV variable.")
	public void read_csv_file_into_tabular_form_and_store_inot_variable(String relativeCSVFilePath, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = CSVFileReader.getData(relativeCSVFilePath);
		scenarioContext.addParamValue(variableName, tabularData);
	}
	
	/**
	 * Used to read the CSV file data into tabular form and store into variable. Here we can specify Data Delimiter, 
	 * Quote characters and whether we want to remote leading and trailing whitespace characters.
	 * 
	 * @param relativeCSVFilePath - the relative path of the CSV file with respect to project root directory.
	 * @param variableName - the name of the variable that stores the CSV file contents. 
	 * @param dataDelimiter - the data delimiter in CSV file.
	 * @param quoteChar - the quote character.
	 * @param trimData - yes - trim the data, no - do not trim data. Data trimming means removing leading and trailing whitespaces. 
	 */
	@When("read {string} CSV file into tabular form and store into {string} CSV variable [DataDelimiter={string}, QuoteChar={string}, TrimData={string}].")
	public void read_sheet_data_of_excel_file_into_tabular_form_and_store_inot_variable(String relativeCSVFilePath, String variableName, char dataDelimiter, char quoteChar, String trimData) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = CSVFileReader.getData(relativeCSVFilePath, dataDelimiter, quoteChar, 
				("yes".equalsIgnoreCase(trimData) || "true".equalsIgnoreCase(trimData)) ? true : false);
		scenarioContext.addParamValue(variableName, tabularData);
	}
	
	/**
	 * Used to read specific column data from tabular data present in CSV variable.
	 * 
	 * @param columnName - the name of the column to read the data. 
	 * @param CSVVariableName - the name of the variable that contains CSV file data into tabular form.
	 * @param variableName - the name of the variable to store the column data.
	 */
	@When("read {string} column data from {string} CSV variable and store into {string} variable.")
	public void read_column_data_from_csv_variable_and_store_into_variable(String columnName, String CSVVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colData = tabularData.getColumnData(columnName);
		
		scenarioContext.addParamValue(variableName, colData);
	}
	
	/**
	 * Used to read specific column data of nth row from tabular data present in CSV variable.
	 * 
	 * @param nthRow - the row number for that the data to be read.
	 * @param columnName- the name of the column to read the data. 
	 * @param CSVVariableName - the name of the variable that contains CSV file data into tabular form.
	 * @param variableName - the name of the variable to store the column data.
	 */
	@When("read {int} row of {string} column data from {string} CSV variable and store into {string} variable.")
	public void read_nth_row_of_column_data_from_csv_variable_and_store_into_variable(int nthRow, String columnName, String CSVVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colData = tabularData.getColumnData(columnName);
		
		scenarioContext.addParamValue(variableName, colData.get(nthRow));
	}
	
	/**
	 * Used to read all column names from the contents present in CSV variable and store this into a new variable.
	 * 
	 * @param CSVVariableName - the name of the variable that contains CSV file data into tabular form.
	 * @param variableName - the name of the variable to store the column data.
	 */
	@When("read all column names from {string} CSV variable and store into {string} variable.")
	public void read_all_column_names_from_csv_variable_and_store_into_variable(String CSVVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colNames = tabularData.getColumnNames();
		
		scenarioContext.addParamValue(variableName, colNames);
	}
	
	/**
	 * Used to read column count from the contents present in CSV variable and store into new variable.
	 * 
	 * @param CSVVariableName - the name of the variable that contains CSV file data into tabular form.
	 * @param variableName - the name of the variable to store the column data.
	 */
	@When("read column count from {string} CSV variable and store into {string} variable.")
	public void read_column_count_from_csv_variable_and_store_into_variable(String CSVVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colNames = tabularData.getColumnNames();
		
		scenarioContext.addParamValue(variableName, colNames.size());
	}
	
	/**
	 * Used to read the row count from contents present in CSV variable and store into new variable.
	 * 
	 * @param CSVVariableName - the name of the variable that contains CSV file data into tabular form.
	 * @param variableName - the name of the variable to store the column data.
	 */
	@When("read row count from {string} CSV variable and store into {string} variable.")
	public void read_row_count_from_csv_variable_and_store_into_variable(String CSVVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		scenarioContext.addParamValue(variableName, tabularData.getRowCount());
	}
	
}

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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common.data;

import java.util.List;

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.file.reader.CSVFileReader;
import org.uitnet.testing.smartfwk.ui.core.file.reader.support.Table;

import io.cucumber.java.en.When;

/**
 * Used to perform CRUD operations on CSV files.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCsvDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartCsvDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	@When("read {string} CSV file into tabular form and store into {string} CSV variable.")
	public void read_csv_file_into_tabular_form_and_store_inot_variable(String relativeCSVFilePath, String variableName) {
		Table tabularData = CSVFileReader.getData(relativeCSVFilePath);
		scenarioContext.addParamValue(variableName, tabularData);
	}
	
	@When("read {string} CSV file into tabular form and store into {string} CSV variable [DataDelimiter={string}, QuoteChar={string}, TrimData={string}].")
	public void read_sheet_data_of_excel_file_into_tabular_form_and_store_inot_variable(String relativeCSVFilePath, String variableName, char dataDelimiter, char quoteChar, String trimData) {
		Table tabularData = CSVFileReader.getData(relativeCSVFilePath, dataDelimiter, quoteChar, 
				("yes".equalsIgnoreCase(trimData) || "true".equalsIgnoreCase(trimData)) ? true : false);
		scenarioContext.addParamValue(variableName, tabularData);
	}
	
	@When("read {string} column data from {string} CSV variable and store into {string} variable.")
	public void read_column_data_from_csv_variable_and_store_into_variable(String columnName, String CSVVariableName, String variableName) {
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colData = tabularData.getColumnData(columnName);
		
		scenarioContext.addParamValue(variableName, colData);
	}
	
	@When("read {int} row of {string} column data from {string} CSV variable and store into {string} variable.")
	public void read_nth_row_of_column_data_from_csv_variable_and_store_into_variable(int nthRow, String columnName, String CSVVariableName, String variableName) {
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colData = tabularData.getColumnData(columnName);
		
		scenarioContext.addParamValue(variableName, colData.get(nthRow));
	}
	
	@When("read all column names from {string} CSV variable and store into {string} variable.")
	public void read_all_column_names_from_csv_variable_and_store_into_variable(String CSVVariableName, String variableName) {
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colNames = tabularData.getColumnNames();
		
		scenarioContext.addParamValue(variableName, colNames);
	}
	
	@When("read column count from {string} CSV variable and store into {string} variable.")
	public void read_column_count_from_csv_variable_and_store_into_variable(String CSVVariableName, String variableName) {
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		List<String> colNames = tabularData.getColumnNames();
		
		scenarioContext.addParamValue(variableName, colNames.size());
	}
	
	@When("read row count from {string} CSV variable and store into {string} variable.")
	public void read_row_count_from_csv_variable_and_store_into_variable(String CSVVariableName, String variableName) {
		Table tabularData = (Table) scenarioContext.getParamValue(CSVVariableName);
		
		scenarioContext.addParamValue(variableName, tabularData.getRowCount());
	}
	
}

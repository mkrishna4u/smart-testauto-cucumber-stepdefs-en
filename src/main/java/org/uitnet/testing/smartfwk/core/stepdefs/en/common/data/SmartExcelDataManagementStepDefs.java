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
import org.uitnet.testing.smartfwk.ui.core.file.reader.ExcelFileReader;
import org.uitnet.testing.smartfwk.ui.core.file.reader.support.Table;

import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to Excel file data reading.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartExcelDataManagementStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartExcelDataManagementStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	/**
	 * Used to read the Excel file data into tabular form for a specific sheet and store into variable.
	 * 
	 * @param sheetName - the name of the sheet name in Excel file.
	 * @param relativeExcelFilePath - the relative path of the Excel file with respect to project root directory.
	 * @param variableName - the name of the variable that stores the excel sheet  contents. 
	 */
	@When("read {string} sheet data of {string} excel file into tabular form and store into {string} excelsheet variable.")
	public void read_sheet_data_of_excel_file_into_tabular_form_and_store_inot_variable(String sheetName, String relativeExcelFilePath, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = ExcelFileReader.getSheetData(relativeExcelFilePath, sheetName);
		scenarioContext.addParamValue(variableName, tabularData);
	}
	
	/**
	 * Used to read the specific column data of the excelsheet and store into new variable.
	 * 
	 * @param columnName - the name of the column to read data from excelsheet.
	 * @param excelsheetVariableName - the name of the variable name that contains excelsheet data.
	 * @param variableName - the name of the variable that stores the column data.
	 */
	@When("read {string} column data from {string} excelsheet variable and store into {string} variable.")
	public void read_column_data_from_excelsheet_variable_and_store_into_variable(String columnName, String excelsheetVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(excelsheetVariableName);
		
		List<String> colData = tabularData.getColumnData(columnName);
		
		scenarioContext.addParamValue(variableName, colData);
	}
	
	/**
	 * Used to read the nth row of specified column data that is present in excelsheet variable. it stores the read data into
	 * new variable.
	 * 
	 * @param nthRow - nth row of the column data.
	 * @param columnName - the name of the column for that the data to be read.
	 * @param excelsheetVariableName - the name of the variable name that contains excelsheet data.
	 * @param variableName - the name of the variable that stores the nth row data of the specified column.
	 */
	@When("read {int} row of {string} column data from {string} excelsheet variable and store into {string} variable.")
	public void read_nth_row_of_column_data_from_excelsheet_variable_and_store_into_variable(int nthRow, String columnName, String excelsheetVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(excelsheetVariableName);
		
		List<String> colData = tabularData.getColumnData(columnName);
		
		scenarioContext.addParamValue(variableName, colData.get(nthRow));
	}
	
	/**
	 * Used to read all column names that are present in the value of excelsheet variable and stores into new variable.
	 * 
	 * @param excelsheetVariableName - the name of the variable name that contains excelsheet data.
	 * @param variableName - the name of the variable that stores the column names.
	 */
	@When("read all column names from {string} excelsheet variable and store into {string} variable.")
	public void read_all_column_names_from_excelsheet_variable_and_store_into_variable(String excelsheetVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(excelsheetVariableName);
		
		List<String> colNames = tabularData.getColumnNames();
		
		scenarioContext.addParamValue(variableName, colNames);
	}
	
	/**
	 * Used to read column count from the value of excelsheet variable and store into new variable.
	 * 
	 * @param excelsheetVariableName - the name of the variable name that contains excelsheet data.
	 * @param variableName - the name of the variable that stores the column count.
	 */
	@When("read column count from {string} excelsheet variable and store into {string} variable.")
	public void read_column_count_from_excelsheet_variable_and_store_into_variable(String excelsheetVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(excelsheetVariableName);
		
		List<String> colNames = tabularData.getColumnNames();
		
		scenarioContext.addParamValue(variableName, colNames.size());
	}
	
	/**
	 * Used to read the row count from the value of excelsheet variable and store into new variable.
	 * 
	 * @param excelsheetVariableName - the name of the variable name that contains excelsheet data.
	 * @param variableName - the name of the variable that stores the row count. Note: It does not count the header row.
	 */
	@When("read row count from {string} excelsheet variable and store into {string} variable.")
	public void read_row_count_from_excelsheet_variable_and_store_into_variable(String excelsheetVariableName, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		Table tabularData = (Table) scenarioContext.getParamValue(excelsheetVariableName);
		
		scenarioContext.addParamValue(variableName, tabularData.getRowCount());
	}
	
}

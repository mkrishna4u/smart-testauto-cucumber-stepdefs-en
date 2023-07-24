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
package org.uitnet.testing.smartfwk.core.stepdefs.en.common;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.common.DateTimeUnit;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Lists steps definitions related to date and time management.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartDateTimeManipulationStepDefs {
	private SmartCucumberScenarioContext scenarioContext;

	public SmartDateTimeManipulationStepDefs(SmartCucumberScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

	/**
	 * Used to return the system timestamp (data and time) in a specific format and in the specified TimeZone.
	 * 
	 * @param timestampFormat - the timestamp format like "MM/dd/yyyy HH:mm:ss Z"
	 * 		For more details on timestamp format, please refer {@link SimpleDateFormat}
	 * @param timeZone - the time zone in for we want to get the timestamp. Refer {@link ZoneId} for TimeZone information.
	 * 	For example: 
	 * 		1. For America EST timezone: America/New_York
	 * 			or you can use: -05:00
	 * 		2. For India IST Timezone: Asia/Kolkata
	 * 			or you can use: +05:30
	 * @param variableName - the variable name that stores the timestamp.
	 */
	@Then("get the local system timestamp in {string} format using [TimeZone={string}] and store into {string} variable.")
	public void get_the_local_system_timestamp_in_format_and_store_into_variable(String timestampFormat, String timeZone, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		timestampFormat = scenarioContext.applyParamsValueOnText(timestampFormat);
		timeZone = scenarioContext.applyParamsValueOnText(timeZone);
		
		TimeZone tzone = TimeZone.getTimeZone(timeZone);
		Date date = Calendar.getInstance(tzone).getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
		sdf.setTimeZone(tzone);
		String formattedDate = sdf.format(date);
		
		scenarioContext.addParamValue(variableName, formattedDate);
		
	}
	
	/**
	 * Used to return the current timestamp (data and time) in a specific format and in the specified TimeZone.
	 * 
	 * @param timestampFormat - the timestamp format like "MM/dd/yyyy HH:mm:ss Z"
	 * 		For more details on timestamp format, please refer {@link SimpleDateFormat}
	 * @param timeZone - the time zone in for we want to get the timestamp. Refer {@link ZoneId} for TimeZone information.
	 * 	For example: 
	 * 		1. For America EST timezone: America/New_York
	 * 			or you can use: -05:00
	 * 		2. For India IST Timezone: Asia/Kolkata
	 * 			or you can use: +05:30
	 * @param variableName - the variable name that stores the timestamp.
	 */
	@Then("get current timestamp in {string} format using [TimeZone={string}] and store into {string} variable.")
	public void get_current_timestamp_in_format_and_store_into_variable(String timestampFormat, String timeZone, String variableName) {
		get_the_local_system_timestamp_in_format_and_store_into_variable(timestampFormat, timeZone, variableName);		
	}
	
	/**
	 * Used to subtract days, hours, minutes and seconds from the specified timestamp that is given in a specific format.
	 * 
	 * @param n - n number of units like 3. This parameter value should always be integer or variable.
	 * @param dateTimeUnit - the date time unit valid values are: years, months, weeks, days, hours, minutes, seconds, milliseconds.
	 * @param timestampAsStr - the timestamp as string in the format as specified in timestampFormat.
	 * @param timestampFormat - the timestamp format like "MM/dd/yyyy HH:mm:ss Z"
	 * 		For more details on timestamp format, please refer {@link SimpleDateFormat}
	 * @param timeZone - the time zone in for we want to get the timestamp. Refer {@link ZoneId} for TimeZone information.
	 * 	For example: 
	 * 		1. For America EST timezone: America/New_York
	 * 			or you can use: -05:00
	 * 		2. For India IST Timezone: Asia/Kolkata
	 * 			or you can use: +05:30
	 * @param variableName - the variable name that stores the timestamp.
	 */
	@When("subtract {string} {string} from timestamp [TimestampAsStr={string}, Format={string}, TimeZone={string}] and store into {string} variable.")
	public void subtract_from_timestamp_and_store_into_variable(String n, String dateTimeUnit, String timestampAsStr, String timestampFormat, String timeZone, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		int n2 = Integer.valueOf(scenarioContext.applyParamsValueOnText(n));
		dateTimeUnit = scenarioContext.applyParamsValueOnText(dateTimeUnit);
		DateTimeUnit dtUnit = DateTimeUnit.valueOf2(dateTimeUnit);
		timestampAsStr = scenarioContext.applyParamsValueOnText(timestampAsStr);
		timestampFormat = scenarioContext.applyParamsValueOnText(timestampFormat);
		timeZone = scenarioContext.applyParamsValueOnText(timeZone);
		TimeZone tzone = TimeZone.getTimeZone(timeZone);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
			sdf.setTimeZone(tzone);
			Date date = sdf.parse(timestampAsStr);
			
			Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        
	        switch(dtUnit) {
	        case years: 
	        	cal.add(Calendar.YEAR, (0 - n2));
	        	break;
	        case months: 
	        	cal.add(Calendar.MONTH, (0 - n2));
	        	break;
	        case weeks: 
	        	cal.add(Calendar.DATE, (0 - n2*7));
	        	break;
	        case days: 
	        	cal.add(Calendar.DATE, (0 - n2));
	        	break;
	        case hours: 
	        	cal.add(Calendar.HOUR, (0 - n2));
	        	break;
	        case minutes: 
	        	cal.add(Calendar.MINUTE, (0 - n2));
	        	break;
	        case seconds: 
	        	cal.add(Calendar.SECOND, (0 - n2));
	        	break;
	        case milliseconds: 
	        	cal.add(Calendar.MILLISECOND, (0 - n2));
	        	break;
	        default: 
	        	break;
	        }
			
	        String fDate = sdf.format(cal.getTime());
	        
			scenarioContext.addParamValue(variableName, fDate);
		} catch(Exception ex) {
			Assert.fail("Failed to parse '" + timestampAsStr + "' date using '" + timestampFormat + "' format.", ex);
		}
	}
	
	/**
	 * Used to add days, hours, minutes or seconds to the specified timestamp that is given in a specific format.
	 * 
	 * @param n - n number of units like 3. This parameter value should always be integer or variable.
	 * @param dateTimeUnit - the date time unit valid values are: years, months, weeks, days, hours, minutes, seconds, milliseconds.
	 * @param timestampAsStr - the timestamp as string in the format as specified in timestampFormat. Its format should be same as timestampFormat.
	 * @param timestampFormat - the timestamp format like "MM/dd/yyyy HH:mm:ss Z"
	 * 		For more details on timestamp format, please refer {@link SimpleDateFormat}
	 * @param timeZone - the time zone in for we want to get the timestamp. Refer {@link ZoneId} for TimeZone information.
	 * 	For example: 
	 * 		1. For America EST timezone: America/New_York
	 * 			or you can use: -05:00
	 * 		2. For India IST Timezone: Asia/Kolkata
	 * 			or you can use: +05:30
	 * @param variableName - the variable name that stores the timestamp.
	 */
	@When("add {string} {string} to timestamp [TimestampAsStr={string}, Format={string}, TimeZone={string}] and store into {string} variable.")
	public void add_to_timestamp_and_store_into_variable(String n, String dateTimeUnit, String timestampAsStr, String timestampFormat, String timeZone, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		int n2 = Integer.valueOf(scenarioContext.applyParamsValueOnText(n));
		dateTimeUnit = scenarioContext.applyParamsValueOnText(dateTimeUnit);
		DateTimeUnit dtUnit = DateTimeUnit.valueOf2(dateTimeUnit);
		timestampAsStr = scenarioContext.applyParamsValueOnText(timestampAsStr);
		timestampFormat = scenarioContext.applyParamsValueOnText(timestampFormat);
		timeZone = scenarioContext.applyParamsValueOnText(timeZone);
		TimeZone tzone = TimeZone.getTimeZone(timeZone);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
			sdf.setTimeZone(tzone);
			Date date = sdf.parse(timestampAsStr);
			
			Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        
	        switch(dtUnit) {
	        case years: 
	        	cal.add(Calendar.YEAR, n2);
	        	break;
	        case months: 
	        	cal.add(Calendar.MONTH, n2);
	        	break;
	        case weeks: 
	        	cal.add(Calendar.DATE, n2*7);
	        	break;
	        case days: 
	        	cal.add(Calendar.DATE, n2);
	        	break;
	        case hours: 
	        	cal.add(Calendar.HOUR, n2);
	        	break;
	        case minutes: 
	        	cal.add(Calendar.MINUTE, n2);
	        	break;
	        case seconds: 
	        	cal.add(Calendar.SECOND, n2);
	        	break;
	        case milliseconds: 
	        	cal.add(Calendar.MILLISECOND, n2);
	        	break;
	        default: 
	        	break;
	        }
	        
	        String fDate = sdf.format(cal.getTime());
	        
			scenarioContext.addParamValue(variableName, fDate);
		} catch(Exception ex) {
			Assert.fail("Failed to parse '" + timestampAsStr + "' date using '" + timestampFormat + "' format.", ex);
		}
	}

	/**
	 * Used to subtract timestampAsStr1 from timestampAsStr2. Both timestamp should be in the same format as specified by 
	 * timestampFormat and same timeZone. It returns the result into different time units like years, months, weeks, days, 
	 * hours, minutes, seconds and milliseconds.
	 * 
	 * @param timestampAsStr1 - the timestamp that is going to be subtracted from timestampAsStr2. It's format should be same as timestampFormat.
	 * @param timestampAsStr2 - the timestamp from where the timestampAsStr1 is going to get sustracted. It's format should be same as timestampFormat.
	 * @param timestampFormat - the timestamp format like "MM/dd/yyyy HH:mm:ss Z"
	 * 		For more details on timestamp format, please refer {@link SimpleDateFormat}
	 * @param timeZone - the time zone in for we want to get the timestamp. Refer {@link ZoneId} for TimeZone information.
	 * 	For example: 
	 * 		1. For America EST timezone: America/New_York
	 * 			or you can use: -05:00
	 * 		2. For India IST Timezone: Asia/Kolkata
	 * 			or you can use: +05:30
	 * @param dateTimeUnit - valid values for dateTimeUnits are: years, months, weeks, days, hours, minutes, seconds and milliseconds,
	 * 			abs-years, abs-months, abs-weeks, abs-days, abs-hours, abs-minutes, abs-seconds and abs-milliseconds.
	 * 			Where abs stands for absolute value.
	 * @param variableName - the variable name that stores the timestamp.
	 *
	 */
	@When("subtract {string} timestamp from {string} timestamp [Format:{string}, TimeZone={string}] and store difference as {string} into {string} variable.")
	public void subtract_timestamp1_from_timestamp2_and_store_difference_as_into_variable(String timestampAsStr1, String timestampAsStr2, String timestampFormat, String timeZone, String dateTimeUnit, String variableName) {
		if(!scenarioContext.isLastConditionSetToTrue()) {
			scenarioContext.log("This step is not executed due to false value of condition=\"" + scenarioContext.getLastConditionName() + "\".");
			return;
		}
		
		timestampAsStr1 = scenarioContext.applyParamsValueOnText(timestampAsStr1);
		timestampAsStr2 = scenarioContext.applyParamsValueOnText(timestampAsStr2);
		timestampFormat = scenarioContext.applyParamsValueOnText(timestampFormat);
		dateTimeUnit = scenarioContext.applyParamsValueOnText(dateTimeUnit);
		timeZone = scenarioContext.applyParamsValueOnText(timeZone);
		TimeZone tzone = TimeZone.getTimeZone(timeZone);
		DateTimeUnit dtUnit = null;
		if(dateTimeUnit.startsWith("abs-")) {
			dtUnit = DateTimeUnit.valueOf2(dateTimeUnit.substring(4));
		} else {
			dtUnit = DateTimeUnit.valueOf2(dateTimeUnit);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
		sdf.setTimeZone(tzone);
		Date date1 = null;
		try {
			
			date1 = sdf.parse(timestampAsStr1);
		} catch(Exception ex) {
			Assert.fail("Failed to parse '" + timestampAsStr1 + "' date using '" + timestampFormat + "' format.", ex);
		}
		
		Date date2 = null;
		try {
			date2 = sdf.parse(timestampAsStr2);
		} catch(Exception ex) {
			Assert.fail("Failed to parse '" + timestampAsStr2 + "' date using '" + timestampFormat + "' format.", ex);
		}
		
		long diffInMillies = dateTimeUnit.startsWith("abs-") ?  Math.abs(date2.getTime() - date1.getTime()) :
			(date2.getTime() - date1.getTime());
	    long diff = 0l;
	    switch(dtUnit) {
        case years: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 365;
        	break;
        case months: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 30;
        	break;
        case weeks: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 7;
        	break;
        case days: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        	break;
        case hours: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) * 24;
        	break;
        case minutes: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) * 24 * 60;
        	break;
        case seconds: 
        	diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) * 24 * 60 * 60;
        	break;
        case milliseconds: 
        	diff  = diffInMillies;
        	break;
        default: 
        	break;
        }
	    
	    scenarioContext.addParamValue(variableName, diff);
	}
}

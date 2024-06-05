package com.qa.controller;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.qa.keywords.KeywordAction;
import com.qa.keywords.KeywordEnum;

import com.qa.utils.ReportConfigUtil;
import com.qa.utils.XLUtil;

public class TestStepExecutor extends ReportConfigUtil {

	public static WebElement StepElement;

	private static Logger Logger1 = LogManager.getLogger(TestStepExecutor.class); // We have to add this statememt for
																					// every
	// class whose logger1s we want to record.

	/*
	 * Method 1: Execution Controller method : This method will execute steps of a
	 * specific Test case
	 */
	public boolean executeSteps(String TC_ID) throws Exception {

		// Initialize Step status as true By default.
		boolean TestStepStatus = true;
		Logger1.info("Initialized TestStepStatus var to true");

		System.out.println("Execution started for " + TC_ID);
		Logger1.info("execution started for " + TC_ID);

		// Fetch number of steps in TC_ID sheet.
		int NumberSteps = XLUtil.getRowCount(TestSuiteFile, TC_ID);
		Logger1.info("fetched number of steps in +" + TC_ID + "sheet =" + NumberSteps);

		// Loop to iterate over all steps of TC_ID and execute each one by one.
		for (int i = 1; i <= NumberSteps; i++) {

			/*
			 * Get test step ID for entire test-case sheet i.e. column 1 value starting from
			 * row2 as row1 is headers or column names.
			 */
			String testStepId = XLUtil.getCellData(TestSuiteFile, TC_ID, i, 0);
			Logger1.info("fetched step ID :" + testStepId);

			/* Fetch entire row corresponding to test step ID. */
			Map<String, String> FetchedTestStepData = XLUtil.getDataForStep(testStepId, TC_ID);
			Logger1.info("fetched test step data value for entire test step :" + FetchedTestStepData);

			/*
			 * We are majorly concerned about 4 columns of TC_ID sheet i.e. Keyword,
			 * LocatorType, Locator Value, TestDataField.
			 */
			String StepKeyword = FetchedTestStepData.get("Keyword");
			Logger1.info("fetched keyword col of test step :" + StepKeyword);
			String LocatorType = FetchedTestStepData.get("LocatorType");
			Logger1.info("fetched Locator type col of test step :" + LocatorType);
			String LocatorVal = FetchedTestStepData.get("LocatorValue");
			Logger1.info("fetched Locator value col of test step :" + LocatorVal);
			String TestData = FetchedTestStepData.get("TestDataField");
			Logger1.info("fetched test data field col of test step :" + TestData);

			Thread.sleep(3000);
			/*
			 * Using GetWebElement method, we'll get web-element based on LocatorType and
			 * LocatorVal
			 */
			StepElement = GetWebElement(LocatorType, LocatorVal);
			Logger1.info("initialized step element :" + StepElement);

			Thread.sleep(3000);
			/*
			 * Method to perform action on WebvElement based on keyword and testData as
			 * argument for the same if any.
			 */
			TestStepStatus = PerformStepAction(StepElement, StepKeyword, TestData);
			Logger1.info("initialized test step status :" + TestStepStatus);

			System.out.println("Test step status =" + TestStepStatus);

			if (TestStepStatus == false) {
				System.out.println("Test step status final inside if =" + TestStepStatus);
				Logger1.info("returned test step status :" + TestStepStatus);
				return TestStepStatus;

			}
		}

		System.out.println("Execution ended for " + TC_ID);
		System.out.println("Test step status final=" + TestStepStatus);
		Logger1.info("returned test step status :" + TestStepStatus);
		return TestStepStatus;
	}

	/*
	 * METHOD 2: This method will return full fledged WebElement that can be used to
	 * perform action on .
	 */
	public static WebElement GetWebElement(String LocatorType, String LocatorVal) {

		if (LocatorType.equalsIgnoreCase("XPATH")) {
			try {
				Logger1.info("found element for locator value using locator type : XPATh and value :" + LocatorVal);
				return driver.findElement(By.xpath(LocatorVal));
			} catch (Exception e) {
				Logger1.info("Element not found using locator type : XPATh and value :" + LocatorVal);
				return null;
			}
		} else if (LocatorType.equalsIgnoreCase("CSS")) {

			try {
				Logger1.info("found element for locator value using locator type : CSS and value :" + LocatorVal);
				return driver.findElement(By.cssSelector(LocatorVal));
			} catch (Exception e) {
				Logger1.info("Element Not found for locator value using locator type : CSS and value :" + LocatorVal);
				return null;
			}

		} else if (LocatorType.equalsIgnoreCase("LinkText")) {

			try {
				Logger1.info("found element for locator value using locator type : LinkText and value :" + LocatorVal);
				return driver.findElement(By.linkText(LocatorVal));
			} catch (Exception e) {
				Logger1.info(
						"Element Not found for locator value using locator type : LinkText and value :" + LocatorVal);
				return null;
			}

		} else if (LocatorType.equalsIgnoreCase("ID")) {

			try {
				Logger1.info("found element for locator value using locator type : ID and value :" + LocatorVal);
				return driver.findElement(By.id(LocatorVal));
			} catch (Exception e) {
				Logger1.info("found element for locator value using locator type : ID and value :" + LocatorVal);
				return null;
			}

		} else {
			System.out.println("Invalid locator type");
			Logger1.info("Invalid locaator type used :" + LocatorType);
			return null;
		}

	}

	/*
	 * METHOD 3: This method will perform action based on keyword's action and
	 * returns test step status -Pass/Fail.
	 */
	public boolean PerformStepAction(WebElement element, String StepKeyword, String TestData) {

		KeywordAction action = new KeywordAction();
		KeywordEnum enumval = KeywordEnum.valueOf(StepKeyword);

		// System.out.println("Splitted test data array 1 :"+AttributeType+" and 2 is
		// :"+AttributeVal);
		switch (enumval) {
		case NAVIGATE:
			try {
				action.Navigate(TestData);
				test.info("Navigates to " + TestData);
				Logger1.info("navigates to " + TestData);
				return true;
			} catch (Exception e) {
				test.info("Issue with navigating to " + TestData);
				Logger1.info("Not able to navigates to " + TestData);
				return false;
			}

		case CHECKPRESENCE:
			try {
				action.CheckPresence(element);
				test.info(element + " is present on webpage");
				Logger1.info(element + " is present on webpage");
				return true;
			} catch (Exception e) {
				test.info(element + " is NOT present on webpage");
				Logger1.info(element + " is NOT present on webpage");
				return false;
			}
		case CLICKBUTTON:

			try {
				action.ClickButton(element);
				test.info(element + " is clicked on webpage");
				Logger1.info(element + " is Clicked on webpage");
				return true;
			} catch (Exception e) {
				test.info(element + " is NOT clicked on webpage");
				Logger1.info(element + " is NOT Clicked on webpage");
				return false;
			}

		case SELECTRADIO:

			try {
				action.SelectRadioButton(element);
				test.info(element + " is selected on webpage");
				Logger1.info(element + " is Selected on webpage");
				return true;
			} catch (Exception e) {
				test.info(element + " is NOT selected on webpage");
				Logger1.info(element + " is NOT Selected on webpage");
				return false;
			}

		case SELECTCHECKBOX:

			try {
				action.SelectCheckBox(element);
				test.info(element + " is selected on webpage");
				Logger1.info(element + " is Selected on webpage");
				return true;
			} catch (Exception e) {
				test.info(element + " is NOT selected on webpage");
				Logger1.info(element + " is NOT Selected on webpage");
				return false;
			}

		case ENTERVAL:
			try {
				action.EnterVal(element, TestData);
				test.info(TestData + " is entered into the webelement on webpage");
				Logger1.info(TestData + " is entered into the webelement on webpage");
				return true;
			} catch (Exception e) {
				test.info(TestData + " is NOT entered into the webelement on webpage");
				Logger1.info(TestData + " is NOT entered into the webelement on webpage");
				return false;
			}

		case VALIDATEMESSAGE:
			try {

				System.out.println("Element :" + element);
				System.out.println("Test data :" + TestData);

				action.ValidateMessage(element, TestData);
				test.info(TestData + " displayed on webpage for " + element);
				Logger1.info(TestData + " displayed on webpage for " + element);
				return true;
			} catch (Exception e) {
				test.info(TestData + " is NOT displayed on webpage for " + element);
				Logger1.info(TestData + " is NOT displayed on webpage for " + element);
				return false;
			}

		case VALIDATEATTRIBUTE:

			String[] splittedTestData = TestData.split("=");
			String AttributeType = splittedTestData[0];
			String AttributeVal = splittedTestData[1];

			try {

				System.out.println(splittedTestData[0] + ", " + splittedTestData[1]);
				action.ValidateAttribute(element, AttributeType, AttributeVal);
				test.info(element + "is having attribute of type :" + AttributeType + " and attribute value of :"
						+ AttributeVal);
				Logger1.info(element + "is having attribute of type :" + AttributeType + " and attribute value of :"
						+ AttributeVal);
				return true;
			} catch (Exception e) {
				test.info(element + "is NOT having attribute of type :" + AttributeType + " and attribute value of :"
						+ AttributeVal);
				Logger1.info(element + "is NOT having attribute of type :" + AttributeType + " and attribute value of :"
						+ AttributeVal);
				return false;
			}

		case VALIDATEVALUE:

			try {

				System.out.println(TestData);
				action.ValidateValue(element, TestData);
				test.info(element + "is having value :" + TestData);
				Logger1.info(element + "is having value :" + TestData);
				return true;
			} catch (Exception e) {
				test.info(element + "is NOT having value as :" + TestData);
				Logger1.info(element + "is NOT having value as :" + TestData);
				return false;
			}

		case VALIDATESTATE:

			try {

				System.out.println(TestData);
				action.ValidateState(element, TestData);
				test.info(element + "is having state :" + TestData);
				Logger1.info(element + "is having state :" + TestData);
				return true;
			} catch (Exception e) {
				test.info(element + "is NOT having state as :" + TestData);
				Logger1.info(element + "is NOT having state as :" + TestData);
				return false;
			}

		case VALIDATENUM:

			String[] splittedTestData1 = TestData.split(",");
			String SearchVal = splittedTestData1[0];
			String SearchNumber = splittedTestData1[1];

			try {

				System.out.println(TestData);
				action.ValidateNum(SearchNumber, SearchVal);
				test.info("element with value as :" + SearchVal + " is displayed " + SearchNumber
						+ " number of times as part of output.");
				Logger1.info("element with value as :" + SearchVal + " is displayed " + SearchNumber
						+ " number of times as part of output.");
				return true;
			} catch (Exception e) {
				test.info(element + "is NOT having appropriate testdata :" + TestData);
				Logger1.info(element + "\"is NOT having appropriate testdata :" + TestData);
				return false;
			}

		default:
			System.out.println("Invalid keyword used in test case." + StepKeyword);
			test.info("Invalid keyword used in Test step" + StepKeyword);
			Logger1.info(StepKeyword + " is invalid keywword");
			return false;

		}

	}

}

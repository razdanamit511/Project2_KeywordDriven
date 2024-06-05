package com.qa.controller;

import java.lang.reflect.Method;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.qa.utils.ReportConfigUtil;
import com.qa.utils.XLUtil;

public class TestSuiteExecutor extends ReportConfigUtil {
	
	/* We have to add this statement for every class whose logs we want to record. */
	private static Logger logger1 = LogManager.getLogger(TestSuiteExecutor.class); 
	
	                 /*  ----------------     Method1: This method will decide what test case to execute or what not from TestSuite excel --------- */
	@Test
	public void TestClassExecutionSetup(Method m, ITestContext context) throws Exception {
		
		// Number of Test-Case in Test-Suite for execution
		int NumberRowsTestSuite = XLUtil.getRowCount(TestSuiteFile, "ExecutionHandler");
		logger1.info("Number of testcases in test suite is :"+NumberRowsTestSuite);
		boolean TestCaseStatus = false;
		
		// Loop to traverse over all test cases inside Test Suite excel > Sheet1 (Execution Handler)
		for (int i = 1; i <= NumberRowsTestSuite; i++) {

			String TC_ID = XLUtil.getCellData(TestSuiteFile, "ExecutionHandler", i, 0);
			logger1.info("test case ID :"+TC_ID);
			System.out.println("test case ID :"+TC_ID);
			
			String TC_DESC = XLUtil.getCellData(TestSuiteFile, "ExecutionHandler", i, 1);
			System.out.println("TC DESC :"+TC_DESC);
			logger1.info("test case Description :"+TC_DESC);
			
			String TC_RUNMODE = XLUtil.getCellData(TestSuiteFile, "ExecutionHandler", i, 2);
			logger1.info("test case Run_modE :"+TC_RUNMODE);

			// Create test in Extent Report for every TC_ID you are required to execute.
			test = report.createTest(TC_ID, TC_DESC);
			logger1.info("Created extent test for :"+TC_ID+" and description :"+TC_DESC);
			
			// If TC_RUNMODE == Y, then we want to execute that
			if (TC_RUNMODE.equalsIgnoreCase("Y")) {

				// Method using which we'll execute steps mentioned inside sheet with name =
				// TC_ID
				TestStepExecutor executer = new TestStepExecutor();
		
				TestCaseStatus = executer.executeSteps(TC_ID);
				
				System.out.println("Test Case final status testsuitexecutor=" + TestCaseStatus);
				logger1.info("Test caase execution status of "+TC_ID+" is : "+TestCaseStatus);
				
				if (TestCaseStatus == true) {
					System.out.println("Here inside Testcase true");
				
					/* add pass log into extent report file */
					test.pass("Test got passed....!!!!");
					logger1.info("Marked "+TC_ID+" as passed....");
					
				}

				/* Capture screenshot and add the ss for failed tests into Report at Test Level */
				else if (TestCaseStatus == false) {
					
					System.out.println("Here inside Testcase false");
					
					/* Add fail test log */
					test.fail("Test got failed ..!!!!!");
					logger1.info("Marked "+TC_ID+" as failed....");
					
					/* Add ss at Test Level of Extent Report */
					test.addScreenCaptureFromBase64String(CaptureSS());
					logger1.info("Add failed screenshot for "+TC_ID+" in extent report");
				}

			}
			// If TC_RUNMODE !=Y, then we want to skip that or not execute the same
			else {
				
				System.out.println("TC_ID =" + TC_ID + "execution skipped.....");
				
				test.skip("Test got skipped !!!!");
				logger1.info("Marked "+TC_ID+" as skipped.....");
			}

		}

	}

}

package com.qa.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.base.BaseTest;

import jdk.internal.org.jline.utils.Log;

public class ReportConfigUtil extends CommonUtils {


	private static Logger logger1 = LogManager.getLogger(ReportConfigUtil.class);

	
	@BeforeSuite                                       /*-----  REPORT INITIALIZATION ------ */
	public void Suite_StartUp() {

		System.out.println("before suite started.....");

		/* Object of ExtentReports */
		report = new ExtentReports();
		logger1.info("Creates object of extent report");
		                              
                                     // Dynamic report name generation with timestamp
		reportName = new SimpleDateFormat("dd-MM-yyy_hh.mm.ss").format(new Date()); // returns date with time i.e. timestamp
		logger1.info("Initailized report name object");
        
		/* Object of ExtentReporter implementation classes Ex: ExtentSparkReporter */
		reporter = new ExtentSparkReporter(
				"C:\\Users\\razda\\eclipse-workspace\\Projects\\Project2_KeywordDriven\\Reports\\" + reportName + ".html");
		logger1.info("Creates object of extent reporter implementation classes : ExtentSparkReporter class ");
		
        report.attachReporter(reporter);
        logger1.info("linkes extent report obj to extent reporter implementation class ");
		
	    /* Add environment info that are common to all tests - OS and Java-version */
		report.setSystemInfo("OS", System.getProperty("os.name"));
		logger1.info("Added OS to report's sysstem info section");
	    
		report.setSystemInfo("JAVA VERSION", System.getProperty("java.version"));
		logger1.info("Added JAVA VERSION to report's system info section");
	    
		System.out.println("Before Suite done");

	}


	
	@AfterSuite                                       /* ---- REPORT GENERATION ---- */
	public void Suite_TearDown() throws IOException {
		
		System.out.println("after suite started.....");
		
		/* Flush report and generated report file */
		report.flush();
		logger1.info("flushes report config to a report file and generates the same");
		
	    /* Find generated report and open it as URI */
		Desktop.getDesktop().browse(
				new File("C:\\Users\\razda\\eclipse-workspace\\Projects\\Project2_KeywordDriven\\Reports\\" + reportName + ".html")
						.toURI());
		logger1.info("Opens generated extent report in browser's URI");

		System.out.println("After Suite done");

	}

}

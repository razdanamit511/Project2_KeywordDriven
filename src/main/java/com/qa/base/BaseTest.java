package com.qa.base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.utils.ReadConfigUtil;

public class BaseTest {

	// Variables
	public static WebDriver driver;

	/* Creation of ExtentReports object */
	public static ExtentReports report;

	/* Creation of ExtentReporter object */
	public static ExtentSparkReporter reporter;

	/* Creation of ExtentTest object */
	public static ExtentTest test;

	/* Creation of reportName object */
	public static String reportName;

	/*Creation of ReadConfigUtil object */
	public static ReadConfigUtil prop = new ReadConfigUtil();

	/* Declare TestSuiteFile var */
	public String TestSuiteFile = prop.getTestSuiteFilePath();

	/* We have to add this statement for every class whose logs we want to record. */
	private static Logger logger1 = LogManager.getLogger(BaseTest.class); 
	
	
	@Parameters("browser")
	@BeforeTest // Driver initialization based on browser param of <Test>
	public void Setup(String str) {
		System.out.println("Before test started.....");
		String baseurl = prop.getApplicationUrl();

		switch (str) {
		case "chrome":
			driver = new ChromeDriver();

			driver.get(baseurl);
			break;
			
		case "edge":
			driver = new EdgeDriver();
			driver.get(baseurl);
			break;
			
		case "firefox":
			driver = new FirefoxDriver();
			driver.get(baseurl);
			break;
			
		default:
			System.out.println("Wrong input for browser name");
			break;
		}
		System.out.println("Before test stopped");
	}

	@AfterTest // Driver closure.
	public void TearDown() {
		System.out.println("After test started.....");

		driver.quit();

	}

}

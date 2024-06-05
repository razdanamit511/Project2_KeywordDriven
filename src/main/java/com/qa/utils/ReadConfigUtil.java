package com.qa.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfigUtil {

	Properties pro;

	/* Constructor */
	public ReadConfigUtil() {

		try {

			FileInputStream fis = new FileInputStream(
					"C:\\Users\\razda\\eclipse-workspace\\Projects\\Project2_KeywordDriven\\src\\main\\java\\com\\qa\\config\\config.properties");
			pro = new Properties();
			pro.load(fis);

		} catch (Exception e) {

			System.out.println("Exception is :" + e.getMessage());

		}

	}

	// Method1
	public String getApplicationUrl() {
		return pro.getProperty("baseurl");
	}

	// Method2
	public String getBrowserName() {
		return pro.getProperty("browser");
	}

	// Method3
	public String getEnvironementName() {
		return pro.getProperty("environmentName");
	}

	// Method4
	public String getprojectName() {
		return pro.getProperty("projectName");
	}

	// Method5
	public String getTestDataFilePath() {
		return pro.getProperty("TestDataExcel");
	}

	// Method6
	public String getTestSuiteFilePath() {
		return pro.getProperty("TestSuiteExcel");
	}

}

package com.qa.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.BaseTest;

public class CommonUtils extends BaseTest{

	/* Method1: To capture screenshot */
	public static String CaptureSS() {
		/* Method for capturing screenshot */
		TakesScreenshot tc = (TakesScreenshot) driver;
		String base64SSVal = tc.getScreenshotAs(OutputType.BASE64);

		System.out.println("SS Saved successfully");
		return base64SSVal;
	}
	
}

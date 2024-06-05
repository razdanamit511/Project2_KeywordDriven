package com.qa.keywords;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qa.base.BaseTest;
import com.qa.controller.TestStepExecutor;

/* -------------------------- Keyword to Action mapping class ----------------------------*/

/* NOTE: This class will have method corresponding to every keyword defined in  TestSuite > Test case sheet > Test step.  */
public class KeywordAction extends BaseTest {

	private static Logger Logger1 = LogManager.getLogger(KeywordAction.class); // We have to add this statement for
																				// every
	// class whose logger1s we want to record.

	/* Method 1: Method corresponding to "CHECKPRESENCE" keyword */
	public void CheckPresence(WebElement element) {
		element.isDisplayed();
		Logger1.info("executing checkpresnece method of KeywordAction class for webelement :" + element);
	}

	/* Method 2: Method corresponding to "CLICKBUTTON" keyword */
	public void ClickButton(WebElement element) {
		element.click();
		Logger1.info("executed checkpresence method of KeywordAction class for webelement :" + element);
	}

	/* Method 3: Method corresponding to "SELECTRADIO" keyword */
	public void SelectRadioButton(WebElement RadioButton) {
		RadioButton.click();
		Logger1.info("executed select radio button method of KeywordAction class for webelement :" + RadioButton);
	}

	/* Method 4: Method corresponding to "ENTERVAL" keyword */
	public void EnterVal(WebElement element, String val) {
		element.sendKeys(val);
		Logger1.info(
				"executed enter val method of KeywordAction class for webelement :" + element + " with value :" + val);
	}

	/* Method 5: Method corresponding to "SELECTCHECKBOX" keyword */
	public void SelectCheckBox(WebElement Checkbox) {
		Checkbox.click();
		Logger1.info("executed select checkbox  method of KeywordAction class for webelement :" + Checkbox);
	}

	/* Method 6: Method corresponding to "NAVIGATE" keyword */
	public void Navigate(String URL) {
		driver.get(URL);
		Logger1.info("executed Navigate method of KeywordAction class for URL :" + URL);
	}

	/* Method 7: Method corresponding to "VALIDATEMESSAGE" keyword */
	public void ValidateMessage(WebElement Element, String message) {
		Assert.assertEquals(Element.getText(), message);
		Logger1.info("Message got displayed as :" + message + " for element :" + Element);
	}

	/* Method 7: Method corresponding to "VALIDATEMESSAGE" keyword */
	public void ValidateAttribute(WebElement Element, String AttributeType, String AttributeVal) {
		Assert.assertEquals(Element.getAttribute(AttributeType), AttributeVal);
		Logger1.info(Element + " has Attribute :" + AttributeType + " and attribute value :" + AttributeVal);
	}
	
	/* Method 8: Method corresponding to "VALIDATEVALUE" keyword */
	public void ValidateValue(WebElement Element, String Value) {
		Assert.assertEquals(Element.getText(), Value);
		Logger1.info(Element + " has value :" + Value );
	}
	
	/* Method 9: Method corresponding to "VALIDATESTATE" keyword */
	public void ValidateState(WebElement Element, String State) {
		
		if(State.equalsIgnoreCase("TRUE")) {
		    Assert.assertEquals(Element.isEnabled(), true);
		    Logger1.info(Element + " has state :" + State );
		}else if(State.equalsIgnoreCase("FALSE")) {
		    Assert.assertEquals(Element.isEnabled(), false);
		    Logger1.info(Element + " has state :" + State );
		}
		
	}
	
	
	/* Method 10: Method corresponding to "VALIDATENUM" keyword */
	public void ValidateNum(String Number, String value) {
		
		int num=Integer.valueOf(Number);

		int numElements = 0;
		List<WebElement> listElements = driver.findElements(By.tagName("a"));
		for (int i = 0; i < listElements.size(); i++) {

			System.out.println("Link text :" + listElements.get(i).getText());
			if (listElements.get(i).getText().endsWith(value)) {
				numElements=numElements+1;
			}
		}

		    Assert.assertEquals(num, numElements);
		    Logger1.info("Element has :" + num +"number of elements as part of output.");
		
	}
}

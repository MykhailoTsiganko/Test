package com.epam.lab.pageob.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TopEditMenu {
	private static Logger logger = Logger.getLogger(NavigationMenu.class);
	
	@FindBy(xpath = "//div[@class='ar9 T-I-J3 J-J5-Ji']")
	private List<WebElement> deleteButtons;

	public TopEditMenu(WebDriver driver) {
		logger.info("TopEditMenu constructor");
		PageFactory.initElements(driver, this);
	}
	
	public void delete() {
		logger.info("delete menthod");
		int i = 0;
		for(WebElement deleteButton : deleteButtons) {
			if(deleteButton.isDisplayed()) {
				deleteButton.click();
				logger.info(i);
			}
			logger.info(i);
			i++;
		}
	}

}

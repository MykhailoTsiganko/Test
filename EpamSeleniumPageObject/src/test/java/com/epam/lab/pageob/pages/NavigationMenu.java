package com.epam.lab.pageob.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationMenu {
	private static Logger logger = Logger.getLogger(NavigationMenu.class);
	private WebDriver driver;

	@FindBy
	private WebElement inboxItem;

	@FindBy(css = "span.CJ")
	private WebElement moreItem;

	@FindBy(css = "div.TN.GLujEb.aHS-bns")
	private WebElement importantItem;

	public NavigationMenu(WebDriver driver) {
		logger.info("NavigationMenu constructor");
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectImportent() {
		logger.info("selectImportent method");
		moreItem.click();
		importantItem.click();
	}

}

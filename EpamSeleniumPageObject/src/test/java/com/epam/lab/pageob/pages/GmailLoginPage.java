package com.epam.lab.pageob.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailLoginPage {
	public static final String LOGIN_PAGE_URL = "https://mail.google.com/";
	
	private Logger logger = Logger.getLogger(GmailLoginPage.class);

	private WebDriver driver;

	@FindBy(id = "identifierId")
	private WebElement loginInput;

	@FindBy(xpath = "//*[@class='ZFr60d CeoRYc']/..")
	private WebElement nextButton;

	@FindBy(xpath = "//*[@id=\"password\"]/div[1]/div/div[1]/input")
	private WebElement passwordInput;

	@FindBy(xpath = "//*[@id='passwordNext']/div[2]")
	private WebElement submitButton;

	@FindBy(xpath = "//*[@id=\"view_container\"]//button")
	private WebElement forgotEmailLink;

	@FindBy(xpath = "//*[@id='ow162']")
	private WebElement moreOprionsLink;

	public GmailLoginPage(WebDriver driver) {
		logger.info("GmailLoginPage constructor");
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public GmailLoginPage open() {
		driver.get(LOGIN_PAGE_URL);
		return this;
	}

	public GmailMainPage loginsAs(String login, String password) {
		logger.info("loginAs method");
		loginInput.sendKeys(login);
		nextButton.click();
		passwordInput.sendKeys(password);
		waitForVisibility(submitButton);
		Actions action = new Actions(driver);
		action.moveToElement(submitButton).click().build().perform();
		return new GmailMainPage(driver);
	}
	
	private void waitForVisibility(WebElement element) throws Error {
		new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
	}
	
	void waitForLoad() {
	   
	new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
		.executeScript("return document.readyState").equals("complete"));
	}
}

package com.epam.lab.pageob;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.lab.pageob.pages.GmailLoginPage;
import com.epam.lab.pageob.pages.GmailMainPage;
import com.epam.lab.pajeob.model.Message;

public class GmailTest {
	public static final String CHROME_DRIVER_SRC = "resources/chromedriver.exe";
	public static final String DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
	public static final String PASSWORD = "Selenium1";
	public static final String LOGIN = "SelTestTsyganko@gmail.com";
	

	public static Logger logger = Logger.getLogger(GmailTest.class);

	private WebDriver driver;
	private GmailMainPage mainPage;
	private List<Message> markedMessagesModelsList;

	@BeforeClass
	public void setUp() {
		logger.info("setUp");
		System.setProperty(DRIVER_PROPERTY_NAME, CHROME_DRIVER_SRC);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void login() {
		GmailLoginPage loginPage = new GmailLoginPage(driver);
		mainPage = loginPage.open().loginsAs(LOGIN, PASSWORD);
	}

	@Test(priority = 2)
	public  void markMessagesTo() {
		markedMessagesModelsList = mainPage.markMessagesAsImportant(3);
	}
	
	@Test(priority = 3)
	public void chekIfMessagesWhereMarkedImportant() {
		mainPage.navigationMenu().selectImportent();
		assertTrue(mainPage.getMessagesModels().containsAll(markedMessagesModelsList));
	}
	
	@Test(priority = 4) 
	public void markRecentlyAdedToImportant(){
		mainPage.markMessages(markedMessagesModelsList);
	}
	
	@Test(priority =5)
	public void deleteMarcked() {
		mainPage.topEditMenu().delete();
	}
	
	@Test(priority = 6)
	public void chekIfDeleted() {
		List<Message> mainPageMessages = mainPage.getMessagesModels();
		for(Message messageModel: this.markedMessagesModelsList) {
			assertFalse(mainPageMessages.contains(messageModel));
		}
	}
	
	@AfterClass
	public void close() {
		driver.close();
	}
	
	
}

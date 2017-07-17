package com.epam.lab.pageob;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.lab.pajeob.model.Message;

import junit.framework.Assert;

public class GmailTestOld {
	public static final String CHROME_DRIVER_SRC = "resources/chromedriver.exe";
	public static final String DRIVER_PROPERTY_NAME = "webdriver.chrome.driver";
	public static final String MESSAGE_SELECTOR = "tr.zA";
	public static final String MESSAGE_IMPORTENCE_VALUE_SELECTOR = "td.WA.xY div.pG";
	public static final String MESSAGES_TABLE_SELECTOR = "table.F.cf.zt";
	public static final String MESSAGES_SENDER_SELECTOR = "div.yW span";
	public static final String MESSAGES_TOPIC_SELECTOR = "span.bog";
	public static final String MESSAGES_DATE_SELECTOR = ".xW.xY span";
	public static final String MORE_SELECTOR = "span.CJ";
	public static final String IMPORTANT_SELECTOR = "div.TN.GLujEb.aHS-bns";
	public static final String CHECKBOX_SELECTOR = "td.oZ-x3";
	public static final String DELETE_BUTTON_XPATH = "//div[@class='ar9 T-I-J3 J-J5-Ji']";
	public static final String PASSWORD = "Selenium1";
	public static final String LOGIN = "SelTestTsyganko@gmail.com";
	public static final int MESSAGES_TO_MARK = 3;

	public static Logger logger = Logger.getLogger(GmailTestOld.class);

	private WebDriver driver;
	private List<Message> messageList;
	private List<WebElement> importentList;

	@BeforeClass
	public void setUp() {
		System.setProperty(DRIVER_PROPERTY_NAME, CHROME_DRIVER_SRC);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.get("https://mail.google.com/");
		messageList = new ArrayList<>();
	}

	@Test(priority = 1)
	public void login() {
		WebElement loginInput = driver.findElement(By.tagName("input"));
		loginInput.sendKeys(LOGIN);
		loginInput.submit();
		WebElement summitButton = driver.findElement(By.id("identifierNext"));
		summitButton.click();
		new WebDriverWait(driver, 10)
				.until((dr) -> dr.findElement(By.id("profileIdentifier")).getText().contains(LOGIN.toLowerCase()));
		WebElement passwordInput = driver.findElement(By.name("password"));
		passwordInput.sendKeys(PASSWORD);
		summitButton = new WebDriverWait(driver, 10).until((dr) -> dr.findElement(By.id("passwordNext")));
		summitButton.click();
	}

	@Test(priority = 2)
	public void markImportant() throws InterruptedException {
		logger.info("markImportant");
		int markedNumber = 0;
		List<WebElement> allMessagesList = driver.findElements(By.cssSelector(MESSAGE_SELECTOR));
		for (WebElement element : allMessagesList) {
			if (element.findElement(By.cssSelector(MESSAGE_IMPORTENCE_VALUE_SELECTOR)).getAttribute("aria-label")
					.equals("Not important")) {
				element.findElement(By.cssSelector(MESSAGE_IMPORTENCE_VALUE_SELECTOR)).click();
				messageList.add(getMessageFromTag(element));
				markedNumber++;
			}
			if (markedNumber >= MESSAGES_TO_MARK) {
				break;
			}
		}
	}

	@Test(priority = 3)
	public void verifyImportent() {
		logger.info("verifyImportent");
		WebElement moreItem = driver.findElement(By.cssSelector(MORE_SELECTOR));
		moreItem.click();
		WebElement importentItem = driver.findElement(By.cssSelector(IMPORTANT_SELECTOR));
		importentItem.click();
		importentList = driver.findElements(By.cssSelector(MESSAGE_SELECTOR));
		List<Message> list = getMessagesModelsFromWebElements(importentList);
		list.forEach(System.out::println);
		this.messageList.forEach(System.out::println);
		Assert.assertTrue(list.containsAll(this.messageList));

	}

	@Test(priority = 4)
	public void markRecentImportant() {
		logger.info("markRecentImportant");
		for (WebElement element : importentList) {
			if (messageList.contains(getMessageFromTag(element))) {
				WebElement subElement = element.findElement(By.cssSelector(CHECKBOX_SELECTOR));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", subElement);
			}
		}
	}

	@Test(priority = 5)
	public void deleteMarckedElements() {
		logger.info("deleteMarckedElements");
		WebElement buttonElement = driver.findElements(By.xpath(DELETE_BUTTON_XPATH)).get(1);
		buttonElement.click();
	}

	@Test(priority = 6)
	public void chekIfDeleted() {
		logger.info("chekIfDeleted");
		this.importentList = driver.findElements(By.cssSelector(MESSAGE_SELECTOR));
		List<Message> messagesModelList = getMessagesModelsFromWebElements(importentList);
		messagesModelList.retainAll(this.messageList);
		assertTrue(messagesModelList.size() == 0);
	}

	@AfterClass
	public void close() {
		logger.info("close");
		driver.quit();
	}

	private List<Message> getMessagesModelsFromWebElements(List<WebElement> list) {
		List<Message> modelList = new ArrayList<>();
		for (WebElement element : list) {
			modelList.add(getMessageFromTag(element));
		}
		return modelList;
	}

	private Message getMessageFromTag(WebElement elem) {
		return new Message(
				elem.findElement(By.cssSelector(MESSAGES_SENDER_SELECTOR)).getAttribute("textContent").trim(),
				elem.findElement(By.cssSelector(MESSAGES_TOPIC_SELECTOR)).getAttribute("textContent").trim(),
				elem.findElement(By.cssSelector(MESSAGES_DATE_SELECTOR)).getAttribute("aria-label").trim());
	}

}

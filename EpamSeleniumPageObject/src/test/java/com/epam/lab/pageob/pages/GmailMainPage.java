package com.epam.lab.pageob.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.epam.lab.pajeob.model.Message;

public class GmailMainPage {
	public static final String MESSAGES_SENDER_SELECTOR = "div.yW span";
	public static final String MESSAGES_TOPIC_SELECTOR = "span.bog";
	public static final String MESSAGES_DATE_SELECTOR = ".xW.xY span";
	public static final String MESSAGE_IMPORTENCE_VALUE_XPATH = ".//*[@class='pG']/div[2]";
	public static final String CHECKBOX_XPATH = ".//td[@class='oZ-x3 xY']";
	public static final String MESSAGE_IMPORTENCE_VALUE_SELECTOR = "td.WA.xY div.pG";


	private static Logger logger = Logger.getLogger(GmailMainPage.class);
	private WebDriver driver;
	private TopEditMenu topEditMenu;
	private NavigationMenu navigationMenu;

	@FindBy(css = "tr.zA")
	private List<WebElement> messagesBox;

	public GmailMainPage(WebDriver driver) {
		logger.info("GmailMainPage");
		this.driver = driver;
		PageFactory.initElements(driver, this);
		navigationMenu = new NavigationMenu(driver);
		topEditMenu = new TopEditMenu(driver);
	}

	public NavigationMenu navigationMenu() {
		return navigationMenu;
	}

	public TopEditMenu topEditMenu() {
		return topEditMenu;

	}

	public void printMessageBox() {
		for (WebElement element : messagesBox) {
			System.out.println(getMessageFromTag(element));
		}

	}

	public List<Message> markMessagesAsImportant(int number) {
		logger.info("markMessagesAsImportant method");
		List<Message> messageModelList = new ArrayList<>(number);
		int markedNumber = 0;
		for (WebElement element : messagesBox) {
			if (element.findElement(By.xpath(MESSAGE_IMPORTENCE_VALUE_XPATH)).getAttribute("class").equals("pH-A7 a9q")) {
				element.findElement(By.cssSelector(MESSAGE_IMPORTENCE_VALUE_SELECTOR)).click();
				messageModelList.add(getMessageFromTag(element));
				markedNumber++;
			}
			if (markedNumber >= number) {
				break;
			}
		}
		return messageModelList;
	}

	public GmailMainPage markMessages(List<Message> messageModelList) {
		logger.info("markMessages");
		logger.info(messageModelList.toString());
		int messageMarked = 0;
		for (WebElement element : messagesBox) {
			if (messageModelList.contains(getMessageFromTag(element))) {
				logger.info(getMessageFromTag(element));
				WebElement subElement = element.findElement(By.xpath(CHECKBOX_XPATH));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", subElement);
				messageMarked++;
			}
			if (messageMarked >= messageModelList.size()) {
				break;
			}
		}
		return this;
	}

	public List<Message> getMessagesModels() {
		logger.info("getMessagesModels");
		List<Message> messageModelList = new ArrayList<>(messagesBox.size());
		for (WebElement element : messagesBox) {
			messageModelList.add(getMessageFromTag(element));
		}
		return messageModelList;
	}

	private Message getMessageFromTag(WebElement elem) {
		return new Message(
				elem.findElement(By.cssSelector(MESSAGES_SENDER_SELECTOR)).getAttribute("textContent").trim(),
				elem.findElement(By.cssSelector(MESSAGES_TOPIC_SELECTOR)).getAttribute("textContent").trim(),
				elem.findElement(By.cssSelector(MESSAGES_DATE_SELECTOR)).getAttribute("aria-label").trim());
	}
}

package com.epam.lab.pageob;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.epam.lab.pageob.pages.GmailLoginPage;

public class Main {

	public static void main(String[] args) {
		System.setProperty(GmailTest.DRIVER_PROPERTY_NAME, GmailTest.CHROME_DRIVER_SRC);
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.get("https://mail.google.com/");
		
		GmailLoginPage loginPage = new GmailLoginPage(driver);
		loginPage.loginsAs(GmailTest.LOGIN,GmailTest.PASSWORD);

	}

}

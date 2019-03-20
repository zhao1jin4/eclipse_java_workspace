package testng;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;

public class TestNGAndSelenium {
	WebDriver driver = new FirefoxDriver();

	@BeforeTest
	public void init()// TestNG可以结合Selenium一起用
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.baidu.com");
		driver.manage().window().maximize();
	}

	@AfterTest
	public void destory() {
		driver.close();
	}

	@Test
	public void testOper() {
		WebElement search_text = driver.findElement(By.id("kw"));
		WebElement search_button = driver.findElement(By.id("su"));

		search_text.sendKeys("Java");
		search_text.clear();
		search_text.sendKeys("Selenium");
		// search_button.click();//二选一
		search_text.submit();// 同输入回车
	}
}

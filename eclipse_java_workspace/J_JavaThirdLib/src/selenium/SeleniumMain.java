package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
public class SeleniumMain {

	public static void main(String[] args) throws Exception {
		
		 
		//对chrome 放在PATH环境 ,也要加webdriver.chrome.driver 系统属性
		//java  -Dwebdriver.chrome.driver=D:\Program\seleniumDriver\chromedriver.exe
		WebDriver driver = new ChromeDriver();//OK

		//WebDriver driver = new  FirefoxDriver();//OK firefox命令 要在PATH中可以找到
		//WebDriver driver = new EdgeDriver(); //OK
		//WebDriver driver = new PhantomJSDriver();//OK 用com.codeborne 组的 phantomjsdriver-1.4.4.jar
		//WebDriver driver = new SafariDriver();//未测试
		//WebDriver driver = new InternetExplorerDriver();//NO 报连接不上端口
		 
		driver.get("https://www.baidu.com/");
		String title = driver.getTitle();
		System.out.println(title);
		
		//http://www.testclass.net/selenium_java/elements
		
//		queryElement(driver);//自己的查元素
//		browserOper(driver);//自己的浏览器操作
//		manualInteract(driver);//自己的页面交互
	    waitAjax(driver);//等待,为ajax使用
		
		System.out.println("after 5 second quit");
		Thread.sleep(5000);
		driver.close();


	}
	public static void queryElement(WebDriver driver) {
		WebElement element=driver.findElement(By.id("head")); //document.getELementById()
		System.out.println("id="+element.getTagName());
		
		element=driver.findElement(By.name("ie")); //document.getELementByNames()
		System.out.println("name="+element.getTagName());
		
		element=driver.findElement(By.className("fm")); //document.getElementsByClassName()
		System.out.println("class="+element.getTagName());
		
		element=driver.findElement(By.tagName("b"));//document.getELementsByName()
		System.out.println("tag="+element.getTagName());
		
		
		element=driver.findElement(By.xpath("//form[@id='form']/input[@type='hidden' and @name='tn']"));
		System.out.println("xpath="+element.getTagName()); 
		
		//css找不到？？？
		//element=driver.findElement(By.cssSelector("span.tools>div#mCon")); 
//		element=driver.findElement(By.cssSelector("html > body > form > span#mHolder"));
//		element=driver.findElement(By.cssSelector("html > body > form > span[id=mHolder]"));
//		System.out.println("css="+element.getTagName()); 
		
		element=driver.findElement(By.linkText("新闻"));
		System.out.println("linkText="+element.getTagName()); 
		
		element=driver.findElement(By.partialLinkText("地"));
		System.out.println("partialLinkText="+element.getTagName()); 
		
	    WebElement size = driver.findElement(By.id("kw"));
	    System.out.println("getSize="+size.getSize());

	    WebElement text = driver.findElement(By.id("cp"));
	    System.out.println("getText="+text.getText());
	    
	    WebElement ty = driver.findElement(By.id("kw"));
	    System.out.println("getAttribute="+ty.getAttribute("type"));
	    
	    WebElement display = driver.findElement(By.id("kw"));
	    System.out.println("isDisplayed="+display.isDisplayed());
				
	}
	public static void browserOper(WebDriver driver) throws Exception {

		driver.manage().window().maximize();
	    Thread.sleep(2000);

	    driver.get("https://m.baidu.cn");
	    driver.manage().window().setSize(new Dimension(480, 800));
	    Thread.sleep(2000);
	    
	    driver.get("https://www.baidu.com/");
	    driver.findElement(By.linkText("新闻")).click();//单击
	    System.out.printf("news url  %s \n", driver.getCurrentUrl());
		 
	    driver.navigate().back();// 后退
	    System.out.printf("back to %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	   
	    driver.navigate().forward(); //前进
	    System.out.printf("forward to %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	    
	    driver.navigate().refresh();//刷新
	    System.out.printf("refresh page \n" );
 
	}
	public static void manualInteract(WebDriver driver) throws Exception {
	    driver.get("https://www.baidu.com/");

	    WebElement search_text = driver.findElement(By.id("kw"));
	    WebElement search_button = driver.findElement(By.id("su"));

	    search_text.sendKeys("Java");
	    search_text.clear();
	    search_text.sendKeys("Selenium");
	    //search_button.click();//二选一
	    search_text.submit();//同输入回车
	    
	    
	    //鼠标操作
	    WebElement search_setting = driver.findElement(By.linkText("设置"));
	    Actions action = new Actions(driver);
	    action.clickAndHold(search_setting).perform();
/*
		 // 鼠标右键点击指定的元素
		 action.contextClick(driver.findElement(By.id("element"))).perform();
	
		 // 鼠标右键点击指定的元素
		 action.doubleClick(driver.findElement(By.id("element"))).perform();
	
		 // 鼠标拖拽动作， 将 source 元素拖放到 target 元素的位置。
		 WebElement source = driver.findElement(By.name("element"));
		 WebElement target = driver.findElement(By.name("element"));
		 action.dragAndDrop(source,target).perform();
	
		 // 释放鼠标
		 action.release().perform();
	  */  
	    
	    
	    //键盘操作
	    WebElement input = driver.findElement(By.id("kw"));
	    
	    //删除多输入的一个
	    input.sendKeys(Keys.BACK_SPACE);
	    Thread.sleep(2000);

	    //输入空格键+“教程”
	    input.sendKeys(Keys.SPACE);
	    input.sendKeys("教程");
	    Thread.sleep(2000);

	    //ctrl+a 全选输入框内容
	    input.sendKeys(Keys.CONTROL,"a");
	    Thread.sleep(2000);

	    //ctrl+x 剪切输入框内容
	    input.sendKeys(Keys.CONTROL,"x");
	    Thread.sleep(2000);

	    //ctrl+v 粘贴内容到输入框
	    input.sendKeys(Keys.CONTROL,"v");
	    Thread.sleep(2000);

	    //通过回车键盘来代替点击操作
	    input.sendKeys(Keys.ENTER);
	    Thread.sleep(2000);

	     //获取第一条搜索结果的标题
	     WebElement result = driver.findElement(By.xpath("//div[@id='content_left']/div/h3/a"));
	     System.out.println(result.getText());

	}
	
	public static void waitAjax(WebDriver driver) throws Exception 
	{
		 driver.get("https://www.baidu.com");
		//显式等待， 针对某个元素等待
		 
	 	//超时秒数，间隔秒数
	    WebDriverWait wait = new WebDriverWait(driver,10,1);//要selenium-support-3.141.59.jar

	    wait.until(new ExpectedCondition<WebElement>(){
	      @Override
	      public WebElement apply(WebDriver text) {
	            return text.findElement(By.id("kw"));
	          }
	    }).sendKeys("selenium");

	    driver.findElement(By.id("su")).click();
	    
	    
	    
	    //页面加载超时时间设置为 5s
	    driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
	    driver.get("https://www.baidu.com/");

	    //定位对象时给 10s 的时间, 如果 10s 内还定位不到则抛出异常
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.findElement(By.id("kw")).sendKeys("selenium");

	    //异步脚本的超时时间设置成 3s
	    driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
	    
	    Thread.sleep(2000);
	}
}

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
		
		 
		//��chrome ����PATH���� ,ҲҪ��webdriver.chrome.driver ϵͳ����
		//java  -Dwebdriver.chrome.driver=D:\Program\seleniumDriver\chromedriver.exe
		WebDriver driver = new ChromeDriver();//OK

		//WebDriver driver = new  FirefoxDriver();//OK firefox���� Ҫ��PATH�п����ҵ�
		//WebDriver driver = new EdgeDriver(); //OK
		//WebDriver driver = new PhantomJSDriver();//OK ��com.codeborne ��� phantomjsdriver-1.4.4.jar
		//WebDriver driver = new SafariDriver();//δ����
		//WebDriver driver = new InternetExplorerDriver();//NO �����Ӳ��϶˿�
		 
		driver.get("https://www.baidu.com/");
		String title = driver.getTitle();
		System.out.println(title);
		
		//http://www.testclass.net/selenium_java/elements
		
//		queryElement(driver);//�Լ��Ĳ�Ԫ��
//		browserOper(driver);//�Լ������������
//		manualInteract(driver);//�Լ���ҳ�潻��
	    waitAjax(driver);//�ȴ�,Ϊajaxʹ��
		
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
		
		//css�Ҳ���������
		//element=driver.findElement(By.cssSelector("span.tools>div#mCon")); 
//		element=driver.findElement(By.cssSelector("html > body > form > span#mHolder"));
//		element=driver.findElement(By.cssSelector("html > body > form > span[id=mHolder]"));
//		System.out.println("css="+element.getTagName()); 
		
		element=driver.findElement(By.linkText("����"));
		System.out.println("linkText="+element.getTagName()); 
		
		element=driver.findElement(By.partialLinkText("��"));
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
	    driver.findElement(By.linkText("����")).click();//����
	    System.out.printf("news url  %s \n", driver.getCurrentUrl());
		 
	    driver.navigate().back();// ����
	    System.out.printf("back to %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	   
	    driver.navigate().forward(); //ǰ��
	    System.out.printf("forward to %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	    
	    driver.navigate().refresh();//ˢ��
	    System.out.printf("refresh page \n" );
 
	}
	public static void manualInteract(WebDriver driver) throws Exception {
	    driver.get("https://www.baidu.com/");

	    WebElement search_text = driver.findElement(By.id("kw"));
	    WebElement search_button = driver.findElement(By.id("su"));

	    search_text.sendKeys("Java");
	    search_text.clear();
	    search_text.sendKeys("Selenium");
	    //search_button.click();//��ѡһ
	    search_text.submit();//ͬ����س�
	    
	    
	    //������
	    WebElement search_setting = driver.findElement(By.linkText("����"));
	    Actions action = new Actions(driver);
	    action.clickAndHold(search_setting).perform();
/*
		 // ����Ҽ����ָ����Ԫ��
		 action.contextClick(driver.findElement(By.id("element"))).perform();
	
		 // ����Ҽ����ָ����Ԫ��
		 action.doubleClick(driver.findElement(By.id("element"))).perform();
	
		 // �����ק������ �� source Ԫ���Ϸŵ� target Ԫ�ص�λ�á�
		 WebElement source = driver.findElement(By.name("element"));
		 WebElement target = driver.findElement(By.name("element"));
		 action.dragAndDrop(source,target).perform();
	
		 // �ͷ����
		 action.release().perform();
	  */  
	    
	    
	    //���̲���
	    WebElement input = driver.findElement(By.id("kw"));
	    
	    //ɾ���������һ��
	    input.sendKeys(Keys.BACK_SPACE);
	    Thread.sleep(2000);

	    //����ո��+���̡̳�
	    input.sendKeys(Keys.SPACE);
	    input.sendKeys("�̳�");
	    Thread.sleep(2000);

	    //ctrl+a ȫѡ���������
	    input.sendKeys(Keys.CONTROL,"a");
	    Thread.sleep(2000);

	    //ctrl+x �������������
	    input.sendKeys(Keys.CONTROL,"x");
	    Thread.sleep(2000);

	    //ctrl+v ճ�����ݵ������
	    input.sendKeys(Keys.CONTROL,"v");
	    Thread.sleep(2000);

	    //ͨ���س�����������������
	    input.sendKeys(Keys.ENTER);
	    Thread.sleep(2000);

	     //��ȡ��һ����������ı���
	     WebElement result = driver.findElement(By.xpath("//div[@id='content_left']/div/h3/a"));
	     System.out.println(result.getText());

	}
	
	public static void waitAjax(WebDriver driver) throws Exception 
	{
		 driver.get("https://www.baidu.com");
		//��ʽ�ȴ��� ���ĳ��Ԫ�صȴ�
		 
	 	//��ʱ�������������
	    WebDriverWait wait = new WebDriverWait(driver,10,1);//Ҫselenium-support-3.141.59.jar

	    wait.until(new ExpectedCondition<WebElement>(){
	      @Override
	      public WebElement apply(WebDriver text) {
	            return text.findElement(By.id("kw"));
	          }
	    }).sendKeys("selenium");

	    driver.findElement(By.id("su")).click();
	    
	    
	    
	    //ҳ����س�ʱʱ������Ϊ 5s
	    driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
	    driver.get("https://www.baidu.com/");

	    //��λ����ʱ�� 10s ��ʱ��, ��� 10s �ڻ���λ�������׳��쳣
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.findElement(By.id("kw")).sendKeys("selenium");

	    //�첽�ű��ĳ�ʱʱ�����ó� 3s
	    driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
	    
	    Thread.sleep(2000);
	}
}

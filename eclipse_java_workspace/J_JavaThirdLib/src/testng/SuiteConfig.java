package testng;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;

public class SuiteConfig {

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("@BeforeSuite0");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println(" @AfterSuite5");
	}

	@BeforeGroups // û�ã�����
	public void beforeGroups() {
		System.out.println("@BeforeGroups_SuiteConfig");
	}

	@AfterGroups // û�ã�����
	public void afterGroups() {
		System.out.println("@AfterGroups_SuiteConfig");
	}

}
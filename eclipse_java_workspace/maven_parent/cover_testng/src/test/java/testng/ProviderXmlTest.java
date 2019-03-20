package testng;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


//只能用testng_provider.xml运行
public class ProviderXmlTest {
    @Test(dataProvider = "dataProvider1", groups = {"groupA"})
    public void test1(int number) {
        Assert.assertEquals(number, 1);
    }
    @Test(dataProvider = "dataProvider1", groups = "groupB")
    public void test2(int number) {
        Assert.assertEquals(number, 2);
    }
    @DataProvider(name = "dataProvider1")
    public Object[][] provideData(ITestContext context) {//一个provide为多个测试方法，参数是ITestContext
        Object[][] result = null;
        System.out.println(context.getName());//providerTest
        for (String group : context.getIncludedGroups()) { //<groups> 下的 <run>下的 <include
            System.out.println("group : " + group);
            if ("groupA".equals(group)) {
                result = new Object[][] { { 1 } };
                break;
            }
        }
        if (result == null) {
            result = new Object[][] { { 2 } };
        }
        return result;
    }
}
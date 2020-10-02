package testng;

import org.testng.annotations.Test;

@Test(groups = "group1")//类级别的组
public class Group1 {

    public void react() {
        System.out.println("group1_react");
    }

    public void vue() {
        System.out.println("group1_vue");
    }

}
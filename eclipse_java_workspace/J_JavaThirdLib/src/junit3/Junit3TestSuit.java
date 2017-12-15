package junit3;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class Junit3TestSuit{

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(MyTestJUnit3.class);
        return suite;
    }
    
    
    //use eclipse,junit 4 ,not need main method
//    public static void main (String[] args) 
//    {
//    	//junit.textui.TestRunner.run (suite());
//    	junit.swingui.TestRunner.run(Junit3TestSuit.class);//only junit3
//    }
}
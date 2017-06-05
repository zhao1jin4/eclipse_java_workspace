/*
 * Created on Mar 2, 2006
 *
 */
package chapter3.aop.aspect;

/**
 * 采用AOP手法重构的的业务逻辑
 *
 * 现在只具有纯粹业务逻辑方法的BusinessLogic
 * 才是真正的核心关注点
 */
public class BusinessLogicCoreConcern implements BusinessLogic {

  public void businessMethod1() {
    doCoreBusiness1();
  }

  public void businessMethod2() {
    doCoreBusiness2();
  }

  /**
   * 执行核心业务逻辑1
   */
  private void doCoreBusiness1() {
    System.out.println("Doing CoreBusiness1");
  }

  /**
   * 执行核心业务逻辑2
   */
  private void doCoreBusiness2() {
    System.out.println("Doing CoreBusiness2");
  }
}

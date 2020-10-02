package jpa.collections;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectionSimpleTest{

  private static EntityManagerFactory entityManagerFactory = null;
  private EntityManager entityManager = null;

  @BeforeAll
  public static void setUpBeforeClass() throws Exception {
    entityManagerFactory = Persistence.createEntityManagerFactory("MyJPA");
  }

  @BeforeEach
  public void setUp() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
  }

  @Test
  public void persist() throws Exception {
    Employee employee = new Employee();
    employee.setId(12L);
    employee.setName("lisi");

    employee.getColors().add("red");
    employee.getColors().add("green");
    entityManager.getTransaction().begin();
    entityManager.persist(employee);
    entityManager.getTransaction().commit();
  }

  @Test //测试成功
  public void find() throws Exception {
    persist();
    entityManager.clear();

    Employee employee = entityManager.find(Employee.class, 12L);
    System.out.println(employee.getName());
    System.out.println(employee.getColors());

  }

  @Test
  public void query() throws Exception {
    persist();
    Query query = entityManager.createQuery("select count(o) from Employee o");
    Long count = (Long) query.getSingleResult();
    System.out.println(count);
  }

  // public Employee(String name) {
  // this.name = name;
  // }
  @Test
  public void query1() throws Exception {
    persist();
    Query query = entityManager.createQuery("select new Employee(o.name) from Employee o");
    List<Employee> employees = query.getResultList();
    for (Employee employee : employees) {
      System.out.println(employee.getName());
    }
  }

  @Test
  public void query2() throws Exception {
    persist();
    Query query = entityManager.createQuery("select o from Employee o where o.name=?1").setParameter(1, "张三");
    List<Employee> employees = query.getResultList();
    for (Employee employee : employees) {
      System.out.println(employee.getName());
    }
  }

  @Test
  public void query3() throws Exception {
    persist();
    Query query = entityManager.createQuery("select o from Employee o where o.name=:name").setParameter("name", "张三");
    List<Employee> employees = query.getResultList();
    for (Employee employee : employees) {
      System.out.println(employee.getName());
    }
  }

  @Test
  public void query4() throws Exception {
    persist();
    Query query = entityManager.createNamedQuery("getAll");
    List<Employee> employees = query.getResultList();
    for (Employee employee : employees) {
      System.out.println(employee.getName());
    }
  }

  @Test
  public void query5() throws Exception {
    persist();
    Query query = entityManager.createNativeQuery("select * from emp", Employee.class);
    List<Employee> employees = query.getResultList();
    for (Employee employee : employees) {
      System.out.println(employee.getName());
    }
  }

  @AfterEach
  public void tearDown() throws Exception {
    if (entityManager != null && entityManager.isOpen())
      entityManager.close();
  }

  @AfterAll
  public static void tearDownAfterClass() throws Exception {
    if (entityManagerFactory != null && entityManagerFactory.isOpen())
      entityManagerFactory.close();
  }

} 
package jpa_school;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.LockModeType;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.annotations.Formula;
import org.hibernate.cfg.Environment;


public class JPASchoolMainApp 
{
	public static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception 
	{
		EntityManagerFactory  emf = Persistence.createEntityManagerFactory("MyJPA");
		 
		//Map<String,Object> map=emf.getProperties();//就是在persistence.xml中配置的属性，这里应该可以put
		 EntityManager em = emf.createEntityManager();
	     em.getTransaction().begin();
		
	     
	     //如没有配置<provider>会查所有classpath下(在hibernate-core-5.4.17.Final.jar中)的META-INF/services/javaxpersitence.spi.PersistenceProvider文件,有写Provider实现类,可复制
	     
	     
		//save(em);
		//find_reference(em);//reference?????
		JPSQL_query(em);
		//criteria(em);//栈溢出
		//one2Many(em);//lazy,fetch
		//one2One(em);//???
		//many2Many(em);//reverse,cascade
		//lock(em);
		//blobCreate(em);
		//blobRead(em);
		//extendObject(em);
		//many2ManyWithScore(em);
	     
	     if(em.isOpen())
	     {
	    	  em.getTransaction().commit();
	          em.close();
	     } 
	}
	
	public static void save(EntityManager manager) throws Exception 
	{
		Teacher t=new Teacher();
		//t.setId(1001);//使用了Auto就不会指定了
		t.setName("teacher1");

		Student lisi =new Student ();
		//lisi.setId(2001);
		lisi.setName("lisi");
		lisi.setBirthday(format.parse("1985-01-20"));
		lisi.setTeacher(t);
		lisi.setGender(Gender.MALE);
		lisi.setIsLeague(Boolean.TRUE);
		
		Student wang =new Student ();
		//wang.setId(2002);
		wang.setName("wang");
		wang.setTeacher(t);
		wang.setBirthday(format.parse("1989-05-18"));

		Set<Student> students=new HashSet<Student>();
		students.add(lisi);
		students.add(wang);
		t.setStudents(students);
		
		manager.persist(t);
		manager.persist(lisi);
		manager.persist(wang);
		
	}
	public static void find_reference(EntityManager em)  
	{
		Student lisi= em.find(Student.class, 1); //相当于Hibernate的get
		System.out.println("使用JAP find的学生:"+lisi.getName());
		
		Student wang= em.getReference(Student.class, 2);//相当于Hibernate的load 
		em.close();//为什么后面还能使用????????
		System.out.println("使用JAP getReference的学生:"+wang.getName());
		
		
	}
	public static void JPSQL_query(EntityManager em) 
	{
		
		//Query query=em.createQuery("from Student s where s.name=?",Student.class);//JPSqL
		//query.setParameter(1, "lisi");//JPA都是setParameter,JPA和JDBC一样是1开始的,而Hibernate是0开始的,也可在?后加1,?1表示这个位置就是1

		
		Query query=em.createQuery("from Student s where s.name=?2",Student.class); 
		query.setParameter(2, "lisi");
		List<Student> list=query.getResultList();//JPA是getResultList
		for (Student o :list)
		{
			System.out.println("查询学生是:"+o.getName()+",age:"+o.getAge()+",birthday:"+o.getBirthday());
			//@Formula,@Temporal
		}
		
		query=em.createQuery("from Student s where s.name=:myname",Student.class);
		query.setParameter("myname", "lisi");
		list=query.getResultList();//JPA是getResultList
		for (Student o :list)
		{
			System.out.println("查询学生是:"+o.getName()+",age:"+o.getAge()+",birthday:"+o.getBirthday());
		}
		
		//@NamedQuery JPQL
		query=em.createNamedQuery("getStudentByTeacher");
		query.setParameter("teacher_id", 1);
		query.setFirstResult(0);
		query.setMaxResults(20);
		list=query.getResultList();
		for (Object o :list)
		{
			System.out.println("ByTeacher_id命名查询学生是:"+o);
		}
		
		//JPQL ,cross join
		query=em.createQuery("from Student s where s.teacher.name=:t_name");//字串参数, cross join
		query.setParameter("t_name" , "teacher1");
		list=query.getResultList();
		for (Object o :list)
		{
			System.out.println("teacher1老师的学生有:"+((Student)o).getName());
		}
		//批量更新可使用 update 
		query=em.createQuery("update Student s set s.name=:my_name where s.id=?1");
		query.setParameter("my_name" , "学生XYZ");
		query.setParameter(1 ,1);//可以混合使用?1和:标签做参数 
		query.executeUpdate();
		
		//SQL
		Query sqlQuery=em.createNativeQuery("select count(*) from JPA_STUDENT s,JPA_TEACHER t where s.teacher_id=t.id and t.name=:t_name ");
		sqlQuery.setParameter("t_name" , "teacher1");
		Object count=sqlQuery.getSingleResult();//JAP是getSingleResult
		//这里返回类型为BigInteger ,可能是老版本或者有配置返回为BigDecimal(  createNativeQuery  的 count(*) )
		System.out.println("JPQL Query查询teacher1老师的学生数:"+count);
		
		sqlQuery=em.createNativeQuery("select  * from JPA_TEACHER ",Teacher.class);
		list=sqlQuery.getResultList();
		sqlQuery.setFirstResult(2);
		sqlQuery.setMaxResults(20);
		for (Object o :list)
		{
			System.out.println("JPQL Query 老师有:"+((Teacher)o).getName());//返回Object[]
		}
		
		
		
//		//Hibernate 4.1 JPA 还不支持,@NamedNativeQuery  SQL ,
//		query=em.createNamedQuery("getStudentPhotoByName");
//		query.setParameter("stu_name", "lisi");
//		byte[]buffer=(byte[])query.getSingleResult();
//		System.out.println("学生照片大小是:"+buffer.length);
	}
	public static void criteria(EntityManager em) //栈溢出
	{
		CriteriaBuilder builder=em.getCriteriaBuilder();
		CriteriaQuery<Student> query=builder.createQuery(Student.class);
		
		Root<Student> root=query.from(Student.class);
		
		Path<Set<String>> names =root.get("name");//get方法的doc有示例代码 
		Expression<Boolean> restriction=builder.and(builder.equal(names,"lisi") , builder.upper(root.get("gender")).in(Gender.MALE,Gender.FEMALE));
		
		query.select(root)
		.where(restriction)  
		.orderBy(builder.desc(root.get("birthday")));
		
		 
		List<Student> res=em.createQuery(query).getResultList();
		System.out.println(res);//Teacher.toString,Student.toString两个相互一直调用 ，栈溢出,和cascade没关系 
	}
	public static void one2Many(EntityManager em) //lazy,fetch
	{
		Student wang=(Student)em.find(Student.class, 1);
		wang.setGender(Gender.FEMALE);
		em.refresh(wang);//重新从DB取新记录,CascadeType.REFRESH
		System.out.println(wang.getTeacher().getName());
	}
	public static void one2One(EntityManager em) throws Exception  
	{
		//目前只是增加一个外键列的方式,主外键如何??????????
		Address addr=new Address();
		//addr.setId(3); //如使用主外键方式 ,两个ID值必须相同,因是foreign 这里不指定
		addr.setCity("Shanghai");
		addr.setStreet("张江路");
		
		Student lisi=new Student();
//		lisi.setId(9090);//AUTO方式
		lisi.setName("lisi__");
		lisi.setBirthday(format.parse("2013-04-03"));
		lisi.setAddr(addr);
		addr.setStu(lisi);
		
		
		em.persist(lisi);//要先保存student才有ID
		//session.save(addr);//加了cascade,也因foreign
		
		//-----------query
		em.flush();
		em.clear();
 
//		int id=lisi.getId();
// 		Student stu=(Student)em.find(Student.class,id );//查主, fetch=FetchType.LAZY,也会查两次???
//		System.out.println(stu.getAddr().getCity());
		//---
		int id=addr.getId();
		Address addr1=(Address)em.find(Address.class,id );//查从,会关联主,会多查一次???
		System.out.println(addr1.getStu().getName());
	}
	public static void many2Many(EntityManager em) throws Exception //reverse,cascade
	{
		
		Student lisi =new Student ();
//		lisi.setId(2001);
		lisi.setName("lisi");
		lisi.setBirthday(format.parse("1990-08-09"));
		
		Student wang =new Student ();
//		wang.setId(2002);
		wang.setName("wang");
		wang.setBirthday(format.parse("1985-02-09"));
		
		Course english =new  Course();
//		english.setId(3001);
		english.setName("english");
		
		Course chinese =new  Course();
//		chinese.setId(3002);
		chinese.setName("chinese");

	
		
		Set<Course> courses=new HashSet<>();
		courses.add(english);
		courses.add(chinese);

		Set<Student> students=new HashSet<>();
		students.add(lisi);
		students.add(wang);
		
		//双向保存
		em.persist(lisi);//Student是主方
		em.persist(wang);
//		session.save(english);
//		session.save(chinese);
		
		
		lisi.setCourses(courses);
		wang.setCourses(courses);
		//如多对多是双向关联,两端都是inverse="false",就会报错,因多对多,是向表中插记录,双向关联就会插两次,就会复合主键 重复错误
//		english.setStudents(students);
//		chinese.setStudents(students);
		
		//-----------query
		em.flush();//保存
		em.clear();
		
		Student stu=(Student)em.find(Student.class, lisi.getId());
		Set<Course> allCourses=stu.getCourses();
		for(Course c : allCourses)
		{
			System.out.println(c.getName());
		}
	}
	public static void many2ManyWithScore(EntityManager em)//not test?????
	{
		Student lisi =new Student ();
//		lisi.setId(2001);
		lisi.setName("lisi");
		
		Student wang =new Student ();
//		wang.setId(2002);
		wang.setName("wang");
		
		Course english =new  Course();
//		english.setId(3001);
		english.setName("english");
		
		Course chinese =new  Course();
//		chinese.setId(3002);
		chinese.setName("chinese");

		Score  lisi_english=new Score();
//		lisi_english.setId(10);
		lisi_english.setScore(98.5f);
		lisi_english.setStudent(lisi);
		lisi_english.setCourse(english);
		
		Score  lisi_chinese=new Score();
//		lisi_chinese.setId(11);
		lisi_chinese.setScore(78.5f);
		lisi_chinese.setStudent(lisi);
		lisi_chinese.setCourse(chinese);
		
		
		Score  wang_english=new Score();
//		wang_english.setId(12);
		wang_english.setScore(56f);
		wang_english.setStudent(wang);
		wang_english.setCourse(english);
		
		Score  wang_chinese=new Score();
//		wang_chinese.setId(13);
		wang_chinese.setScore(70f);
		wang_chinese.setStudent(wang);
		wang_chinese.setCourse(chinese);
		
		
		em.persist(lisi );
		em.persist(wang );
		em.persist(english );
		em.persist(chinese );
	
		//--score
		em.persist(lisi_english );
		em.persist(lisi_chinese );
		em.persist(wang_english );
		em.persist(wang_chinese );
		
		//----
//		Set<Score> lisi_scores=new HashSet<>();
//		lisi_scores.add(lisi_english);
//		lisi_scores.add(lisi_chinese);
//		
//		
//		Set<Score> wang_scores=new HashSet<>();
//		wang_scores.add(wang_english);
//		wang_scores.add(wang_chinese);
//		
//		lisi.setScores(lisi_scores); 
//		wang.setScores(wang_scores); 
//		em.persist(lisi );
//		em.persist(wang );
		//--
		
	}
	public static void lock(EntityManager em) 
	{
		Student lisi=(Student)em.find(Student.class, 2);
		em.refresh(lisi, LockModeType.PESSIMISTIC_WRITE);//悲观锁,会加select ... for upate
		lisi.setGender(Gender.FEMALE);
		//-------------
		em.clear();
		Teacher teacher1001=(Teacher)em.find(Teacher.class, 1);  //乐观锁 ,对应有@version ,会加ver=?
		teacher1001.setName("teacher Wang");
	}
	public static void blobCreate(EntityManager em) throws Exception 
	{
		File file=new File("C:/temp/Water.jpg");
		InputStream input =new FileInputStream(file);
		long len=file.length();
		byte[] buffer=new byte[(int)len];
		input.read(buffer);
		input.close();
		
		Student lisi =new Student ();
//		lisi.setId(2001);//AUTO主不用了
		lisi.setName("lisi");
		lisi.setPhoto(buffer);
		
		lisi.setRemark("这是一个非常大的Clob文本,可以是文件上传的方式来做");
		em.persist(lisi);
		
	}
	public static void blobRead(EntityManager em) throws Exception 
	{
		
		//大字段不应全部查出,为什么使用createNativeQuery报错(Oracle DB)????????
//		Query query=em.createNativeQuery("select photo from jpa_student where name='lisi')");
//		query.setParameter(1, "lisi");
		
		Query query=em.createQuery("select photo From Student where name='lisi'");
		byte[]outBytes=(byte[])query.getSingleResult();
		FileOutputStream output=new FileOutputStream(new File("D:/temp/dbimage.jpg"));
		output.write(outBytes);
		output.close();
		System.out.println("done!!!,wirte to D:/temp/");
	}
	public static void extendObject(EntityManager em) throws Exception 
	{
		Student commonStu=new Student();
//		commonStu.setId(2004);
		commonStu.setBirthday(format.parse("1990-08-09"));
		commonStu.setName("common student");
		
		LeaderStudent leaderStu=new LeaderStudent();
//		leaderStu.setId(2005);
		leaderStu.setBirthday(format.parse("1990-08-09"));
		leaderStu.setName("leader student");
		leaderStu.setJobPosition("班长");
		
		GoodStudent goodStu=new GoodStudent();
//		goodStu.setId(2006);
		goodStu.setBirthday(format.parse("1990-08-09"));
		goodStu.setName("good student");
		goodStu.setJoinDate(format.parse("2010-10-18"));
		
		em.persist(commonStu);
		em.persist(leaderStu);
		em.persist(goodStu);

		
		//--------查
		em.flush();
		em.clear();
		
//		//每种方式,查询的生成的SQL,比较复杂
		Student getLeader=(Student)em.find(Student.class,leaderStu.getId());//如果是多张表的试,查询会连接所有的子类表
		System.out.println(getLeader.getClass().getName());
	}
	/*
	public static void jta(EntityManager em) throws Exception 
	{
		
	}
	*/
	

}

package classloader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

/**
* �Զ����������
*/
public class ClassLoaderTest extends ClassLoader {

   private static String MyClasspath = new String("");

   private static ArrayList loadClassList = new ArrayList();

   private static Hashtable loadClassHashTable = new Hashtable();

   public ClassLoaderTest() {

   }

   /**
    * �����Զ���ļ����� ����1��·����
    */
   public ClassLoaderTest(String MyClasspath) {
       if (!MyClasspath.endsWith("\\")) {
           MyClasspath = MyClasspath + "\\";
       }
       this.MyClasspath = MyClasspath;
   }

   /**
    * ���ü���·�� ����1��·����
    */
   public void SetMyClasspath(String MyClasspath) {
       if (!MyClasspath.endsWith("\\")) {
           MyClasspath = MyClasspath + "\\";
       }
       this.MyClasspath = MyClasspath;
   }

   /**
    * �����ಢ���� ����1���ļ��� �� loadClass() ����
    */
   public Class findClass(String name) {
       byte[] classData = null;
       try {
           classData = loadClassData(name);
       } catch (IOException e) {
           e.printStackTrace();
       }
       Class c = defineClass(name, classData, 0, classData.length);
       loadClassHashTable.put(name, c);
       System.out.println("����" + name + "��ɹ���");
       return c;
   }

   /**
    * ��ȡ�ļ��ֽ��� ����1���ļ��� �� findClass() ����
    */
   private byte[] loadClassData(String name) throws IOException {
       String filePath = searchFile(MyClasspath, name + ".class");
       System.out.println(filePath);
       if (!(filePath == null || filePath == "")) {
           FileInputStream inFile = new FileInputStream(filePath);
           byte[] classData = new byte[inFile.available()];
           inFile.read(classData);
           inFile.close();
           return classData;
       }
       System.out.println("��ȡ�ֽ���ʧ�ܡ�");
       return null;
   }

   /**
    * ������ ����1���ֽ������� ����2������
    */
   public Class loadClass(byte[] classData, String className)
           throws ClassNotFoundException {
       Class c = defineClass(className, classData, 0, classData.length);
       loadClassHashTable.put(className, c);
       System.out.println("����" + className + "��ɹ���");
       return c;
   }

   /**
    * ������ ����1������
    */
   public Class loadClass(String name) throws ClassNotFoundException {
       return loadClass(name, false);
   }

   /**
    * ������ ����1������ ����2���Ƿ���������
    */
   protected Class loadClass(String name, boolean resolve)
           throws ClassNotFoundException {
       byte[] classData = null;
       Class temp = null;
       // ����ϵͳ��ֱ��װ�ز���¼�󷵻�
       if (name.startsWith("java.")) {
           temp = findSystemClass(name); // ���ø��෽��
           loadClassHashTable.put(name, temp);
           System.out.println("loadClass: SystemLoading " + name);
           return temp;
       }

       try {
           temp = findLoadedClass(name);// here need try again it is how to
           // work
           if (temp != null) {
               System.out.println(name + " have loaded");
               return (temp);
           }
           try {
               temp = findClass(name);
           } catch (Exception fnfe) {
           }
           if (temp == null) {
               temp = findSystemClass(name);
           }
           if (resolve && (temp != null)) {
               resolveClass(temp);
           }
           return temp;
       } catch (Exception e) {
           throw new ClassNotFoundException(e.toString());
       }
   }

   /**
    * ������ ����1��Ҫ���ص����� ����2�������ڵ�jar����
    */
   protected Class loadClass(String className, String jarName)
           throws ClassNotFoundException {
       String jarPath = searchFile(MyClasspath, jarName + ".jar");
       JarInputStream in = null;
       if (!(jarPath == null || jarPath == "")) {
           try {
               in = new JarInputStream(new FileInputStream(jarPath));
               JarEntry entry;
               while ((entry = in.getNextJarEntry()) != null) {
                   // System.out.println(entry.getName());
                   String outFileName = entry.getName().substring(
                           entry.getName().lastIndexOf("/") + 1,
                           entry.getName().length());
                   // System.out.println(outFileName);
                   if (outFileName.equals(className + ".class")) {
                       if (entry.getSize() == -1) {
                           System.out.println("�����޷���ȡ���ļ���");
                           return null;
                       }
                       byte[] classData = new byte[(int) entry.getSize()];
                       in.read(classData);
                       return loadClass(classData, className);
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       } else {
           System.out.println("don't find the " + jarName + ".jar");
           return null;
       }
       return null;
   }

   /**
    * ��ѯ�ļ� ����1��·���� ����2���ļ���
    */
   public String searchFile(String classpath, String fileName) {
       File f = new File(classpath + fileName);
       // ���Դ�·������ʾ���ļ��Ƿ���һ����׼�ļ�
       if (f.isFile()) {
           return f.getPath();
       } else {
           // �����ɴ˳���·��������ʾ��Ŀ¼�е��ļ���Ŀ¼������������ַ�������
           String objects[] = new File(classpath).list();
           for (int i = 0; i < objects.length; i++) {
               // ���Դ˳���·������ʾ���ļ��Ƿ���һ��Ŀ¼
               if (new File(classpath + f.separator + objects[i])
                       .isDirectory()) {
                   // ����������separator����ϵͳ�йص�Ĭ�����Ʒָ���
                   return searchFile(classpath + f.separator + objects[i]
                           + f.separator, fileName);
               }
           }
       }
       System.out.println("û���ҵ��ļ���" + fileName);
       return null;
   };

   public static void main(String[] args) {
       ClassLoaderTest cl = new ClassLoaderTest("e:\\test");
       try {
           // ��tools.jar���м���ContentSigner��
           cl.loadClass("ContentSigner", "tools");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
   }
}

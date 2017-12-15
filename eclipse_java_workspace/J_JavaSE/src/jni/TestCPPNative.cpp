#include "jni_TestJavaNative.h"//又使用了jni.h,在JDK安装目录的include下,还要一个jni_mod.h在win32目录
#include <iostream>
#include <string>
#include <algorithm>
using namespace std;
JNIEXPORT void JNICALL Java_jni_TestJavaNative_sayHello(JNIEnv *env, jobject obj) //从头文件中复制的,加参数名
{
	jclass hello_clazz=env->GetObjectClass(obj);
	//属性操作,OK
	jfieldID fieldID_prop=env->GetFieldID(hello_clazz,"property","I");
	jint val=env->GetIntField(obj,fieldID_prop);
	cout<<"CPP side GetIntField property is:"<<val<<endl;
	cout<<"CPP side ++val is:"<<++val<<endl;
 	env->SetIntField(obj,fieldID_prop,++val);
	
	//构造Java中的类,OK
	jclass class_date=env->FindClass("java/util/Date");
	jmethodID method_date=env->GetMethodID(class_date,"<init>","()V");//构造方法是<init>,签名返回总是V,真实代码没有void
	jobject now_obj=env->NewObject(class_date,method_date);//如果有参数加后面
	//数组操作...
	
	//方法调用,OK  
	jmethodID methodID_func=env->GetMethodID(hello_clazz,"function","(ILjava/util/Date;[IC)Z");//boolean 是Z,对应public boolean function (int foo,Date date,int[] arr,char c)
	//使用javap生成签名, javap -s -private jni.TestJavaNative  
	jboolean res=env->CallBooleanMethod(obj,methodID_func,20L,now_obj,NULL,L't');//20L,Java中的int对应C/C++中的long型,java中的字符是Unicode两个字节,C/C++中使用宽字符
	cout<<"CPP side  CallBooleanMethod  function return is:"<<res<<endl; //显示不正常??
	//public String function (int foo,Date date,int[] arr,char c)
	
	
	//调用指定父类中的方法,OK  
	jfieldID fieldID_person=env->GetFieldID(hello_clazz,"person","Ljni/Parent;");
	jobject person_obj=env->GetObjectField(obj,fieldID_person);//
	jclass parent_clazz=env->FindClass("jni/Parent");
	jmethodID methodID_doSomething=env->GetMethodID(parent_clazz,"doSomething","()V");
	env->CallVoidMethod(person_obj,methodID_doSomething);
	cout<<"CPP side  CallBooleanMethod child function"<<endl; 
	env->CallNonvirtualVoidMethod(person_obj,parent_clazz,methodID_doSomething);//调用指定父类中的方法,而不是被覆盖的子类的方法
	cout<<"CPP side  CallBooleanMethod parent function "<<endl; 		
	
	//static属性操作
	jfieldID fieldID_staticProp=env->GetStaticFieldID(hello_clazz,"USERNAME","Ljava/lang/String;");
	jstring username_str=(jstring)env->GetStaticObjectField(hello_clazz,fieldID_staticProp);//参数是jclass,可以把jobject强转为jstring
	cout<<"CPP side GetStaticObjectField USERNAME is:"<<username_str<<endl;//返回字串 结果不对???
	//-字串操作,方式1
	//const jchar * jstr =env->GetStringChars(username_str,NULL);
	//wstring wstr((const wchar_t*)jstr);
	//env->ReleaseStringChars(username_str,jstr);// ReleaseStringChars 与 GetStringChars 成对使用
	//-字串操作,方式2
	//const jchar * jstr =env->GetStringCritical(username_str,NULL);
	//wstring wstr((const wchar_t*)jstr);
	//env->ReleaseStringCritical(username_str,jstr);//ReleaseStringCritical 与 GetStringCritical 成对使用(显示有点不正常????),这两个函数期间,绝对不能呼叫JNI其它函数,不要出现中断操作,会使垃圾回收器停止动作
	
	//-字串操作,方式3,把java中字符串内容拷贝到时C/C++字符数组中
	jsize len=env->GetStringLength(username_str);//还有一种GetStringUTFLength
	jchar * buffer=new jchar[len+1];
	buffer[len]=L'\0';
	env->GetStringRegion(username_str,0,len,buffer);//不用Release
	//GetStringUTFRegion //UTF-8 以 '/0' 结尾
	wstring wstr((const wchar_t*)buffer);
	cout<<"CPP side GetStringRegion copyed is:"<<buffer<<endl;//显示不正常????,jchar 是unsigned short
	delete[] buffer;
	
	std::reverse(wstr.begin(),wstr.end());//倒序排序
	jstring copyed_str=env->NewString((jchar*)wstr.c_str(),(jsize)wstr.size());//还有一种NewStringUTF
	env->SetStaticObjectField(hello_clazz,fieldID_staticProp,copyed_str);//设置字串OK

	//-数组属性操作
	jfieldID fieldID_arrays=env->GetFieldID(hello_clazz,"arrays","[I");
	jintArray array_obj=(jintArray)env->GetObjectField(obj,fieldID_arrays);
	jint* ints=env->GetIntArrayElements(array_obj,NULL);
	len=env->GetArrayLength(array_obj);
	std::sort(ints,ints+len);
	env->ReleaseIntArrayElements(array_obj,ints,0);
		//0				对Java的数组进行更新,释放C/C++ 数组
		//JNI_COMMIT	对Java的数组进行更新,不释放C/C++ 数组
		//JNI_ABORT		对Java的数组不进行更新,释放C/C++ 数组


	//--------------以下报错

	/*
	//构造Java中int[]
		 int  localArrayCopy[] = {3,4,5};

		//方式一
		jintArray intArrayParam = env->NewIntArray(3);
	 	env->SetIntArrayRegion(intArrayParam,0,3,(jint *)localArrayCopy);
		//方式二
		jobjectArray  objArrayParam = env->NewObjectArray( 3, env->FindClass("java/lang/Integer"), 0);
		for(int i=0;i<3;i++)
		{
			env->SetObjectArrayElement(objArrayParam,i,(jobject)localArrayCopy[i]);
		}
	*/

	/* 
	//构造Java中String
	jclass class_string=env->FindClass("java/lang/String");
	jmethodID method_string=env->GetMethodID(class_date,"<init>","([C)V");//使用char[]建立String
	jobject string_object=env->AllocObject(class_string);//还没有初始化,null,很少用AllocObject
	jcharArray str_args=env->NewCharArray(5);
	env->SetCharArrayRegion(str_args,0,4,(jchar*)L"我的字串");//要强转
	env->CallNonVirtualVoidMethod(string_object,class_string,method_string,str_args);//编译报错???
	
	env->SetStaticObjectField(hello_clazz,fieldID_staticProp,string_object);
	  */
	
	/* 
	//static方法调用
	jmethodID methodID_staticFunc=env->GetMethodID(hello_clazz,"staticFunc","(IDC)Ljava/lang/String;");//public static String staticFunc(int i,double d,char c)
	jvalue * params=new jvalue[3];
	params[0].i=2L;//jint i
	params[1].d=3.14;//jdouble d
	params[2].c=L'A';//jchar c
	cout<<"1"<<endl;
	jobject static_res=env->CallStaticObjectMethodA(hello_clazz,methodID_staticFunc,params);//报错?????
	cout<<"CallBooleanMethodA has invoded成功:"<<static_res<<endl;
	delete params;
 	*/
	
	
}

	
/*

VC2010 设置环境变量
path=C:\Program Files\Microsoft Visual Studio 10.0\Common7\IDE;C:\Program Files\Microsoft Visual Studio 10.0\VC\bin;
lib=C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib;C:\Program Files\Microsoft Visual Studio 10.0\VC\lib
include=C:\Program Files\Microsoft SDKs\Windows\v7.0A\Include;C:\Program Files\Microsoft Visual Studio 10.0\VC\include

测试OK
cl /I "D:\program\Java\jdk1.7.0_05\include\win32" /I "D:\program\Java\jdk1.7.0_05\include" /c  TestCPPNative.cpp
生成dll
link  TestCPPNative.obj  /DLL /LIBPATH:"D:\program\Java\jdk1.7.0_05\lib"
*/

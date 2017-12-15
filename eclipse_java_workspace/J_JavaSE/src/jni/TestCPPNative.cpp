#include "jni_TestJavaNative.h"//��ʹ����jni.h,��JDK��װĿ¼��include��,��Ҫһ��jni_mod.h��win32Ŀ¼
#include <iostream>
#include <string>
#include <algorithm>
using namespace std;
JNIEXPORT void JNICALL Java_jni_TestJavaNative_sayHello(JNIEnv *env, jobject obj) //��ͷ�ļ��и��Ƶ�,�Ӳ�����
{
	jclass hello_clazz=env->GetObjectClass(obj);
	//���Բ���,OK
	jfieldID fieldID_prop=env->GetFieldID(hello_clazz,"property","I");
	jint val=env->GetIntField(obj,fieldID_prop);
	cout<<"CPP side GetIntField property is:"<<val<<endl;
	cout<<"CPP side ++val is:"<<++val<<endl;
 	env->SetIntField(obj,fieldID_prop,++val);
	
	//����Java�е���,OK
	jclass class_date=env->FindClass("java/util/Date");
	jmethodID method_date=env->GetMethodID(class_date,"<init>","()V");//���췽����<init>,ǩ����������V,��ʵ����û��void
	jobject now_obj=env->NewObject(class_date,method_date);//����в����Ӻ���
	//�������...
	
	//��������,OK  
	jmethodID methodID_func=env->GetMethodID(hello_clazz,"function","(ILjava/util/Date;[IC)Z");//boolean ��Z,��Ӧpublic boolean function (int foo,Date date,int[] arr,char c)
	//ʹ��javap����ǩ��, javap -s -private jni.TestJavaNative  
	jboolean res=env->CallBooleanMethod(obj,methodID_func,20L,now_obj,NULL,L't');//20L,Java�е�int��ӦC/C++�е�long��,java�е��ַ���Unicode�����ֽ�,C/C++��ʹ�ÿ��ַ�
	cout<<"CPP side  CallBooleanMethod  function return is:"<<res<<endl; //��ʾ������??
	//public String function (int foo,Date date,int[] arr,char c)
	
	
	//����ָ�������еķ���,OK  
	jfieldID fieldID_person=env->GetFieldID(hello_clazz,"person","Ljni/Parent;");
	jobject person_obj=env->GetObjectField(obj,fieldID_person);//
	jclass parent_clazz=env->FindClass("jni/Parent");
	jmethodID methodID_doSomething=env->GetMethodID(parent_clazz,"doSomething","()V");
	env->CallVoidMethod(person_obj,methodID_doSomething);
	cout<<"CPP side  CallBooleanMethod child function"<<endl; 
	env->CallNonvirtualVoidMethod(person_obj,parent_clazz,methodID_doSomething);//����ָ�������еķ���,�����Ǳ����ǵ�����ķ���
	cout<<"CPP side  CallBooleanMethod parent function "<<endl; 		
	
	//static���Բ���
	jfieldID fieldID_staticProp=env->GetStaticFieldID(hello_clazz,"USERNAME","Ljava/lang/String;");
	jstring username_str=(jstring)env->GetStaticObjectField(hello_clazz,fieldID_staticProp);//������jclass,���԰�jobjectǿתΪjstring
	cout<<"CPP side GetStaticObjectField USERNAME is:"<<username_str<<endl;//�����ִ� �������???
	//-�ִ�����,��ʽ1
	//const jchar * jstr =env->GetStringChars(username_str,NULL);
	//wstring wstr((const wchar_t*)jstr);
	//env->ReleaseStringChars(username_str,jstr);// ReleaseStringChars �� GetStringChars �ɶ�ʹ��
	//-�ִ�����,��ʽ2
	//const jchar * jstr =env->GetStringCritical(username_str,NULL);
	//wstring wstr((const wchar_t*)jstr);
	//env->ReleaseStringCritical(username_str,jstr);//ReleaseStringCritical �� GetStringCritical �ɶ�ʹ��(��ʾ�е㲻����????),�����������ڼ�,���Բ��ܺ���JNI��������,��Ҫ�����жϲ���,��ʹ����������ֹͣ����
	
	//-�ִ�����,��ʽ3,��java���ַ������ݿ�����ʱC/C++�ַ�������
	jsize len=env->GetStringLength(username_str);//����һ��GetStringUTFLength
	jchar * buffer=new jchar[len+1];
	buffer[len]=L'\0';
	env->GetStringRegion(username_str,0,len,buffer);//����Release
	//GetStringUTFRegion //UTF-8 �� '/0' ��β
	wstring wstr((const wchar_t*)buffer);
	cout<<"CPP side GetStringRegion copyed is:"<<buffer<<endl;//��ʾ������????,jchar ��unsigned short
	delete[] buffer;
	
	std::reverse(wstr.begin(),wstr.end());//��������
	jstring copyed_str=env->NewString((jchar*)wstr.c_str(),(jsize)wstr.size());//����һ��NewStringUTF
	env->SetStaticObjectField(hello_clazz,fieldID_staticProp,copyed_str);//�����ִ�OK

	//-�������Բ���
	jfieldID fieldID_arrays=env->GetFieldID(hello_clazz,"arrays","[I");
	jintArray array_obj=(jintArray)env->GetObjectField(obj,fieldID_arrays);
	jint* ints=env->GetIntArrayElements(array_obj,NULL);
	len=env->GetArrayLength(array_obj);
	std::sort(ints,ints+len);
	env->ReleaseIntArrayElements(array_obj,ints,0);
		//0				��Java��������и���,�ͷ�C/C++ ����
		//JNI_COMMIT	��Java��������и���,���ͷ�C/C++ ����
		//JNI_ABORT		��Java�����鲻���и���,�ͷ�C/C++ ����


	//--------------���±���

	/*
	//����Java��int[]
		 int  localArrayCopy[] = {3,4,5};

		//��ʽһ
		jintArray intArrayParam = env->NewIntArray(3);
	 	env->SetIntArrayRegion(intArrayParam,0,3,(jint *)localArrayCopy);
		//��ʽ��
		jobjectArray  objArrayParam = env->NewObjectArray( 3, env->FindClass("java/lang/Integer"), 0);
		for(int i=0;i<3;i++)
		{
			env->SetObjectArrayElement(objArrayParam,i,(jobject)localArrayCopy[i]);
		}
	*/

	/* 
	//����Java��String
	jclass class_string=env->FindClass("java/lang/String");
	jmethodID method_string=env->GetMethodID(class_date,"<init>","([C)V");//ʹ��char[]����String
	jobject string_object=env->AllocObject(class_string);//��û�г�ʼ��,null,������AllocObject
	jcharArray str_args=env->NewCharArray(5);
	env->SetCharArrayRegion(str_args,0,4,(jchar*)L"�ҵ��ִ�");//Ҫǿת
	env->CallNonVirtualVoidMethod(string_object,class_string,method_string,str_args);//���뱨��???
	
	env->SetStaticObjectField(hello_clazz,fieldID_staticProp,string_object);
	  */
	
	/* 
	//static��������
	jmethodID methodID_staticFunc=env->GetMethodID(hello_clazz,"staticFunc","(IDC)Ljava/lang/String;");//public static String staticFunc(int i,double d,char c)
	jvalue * params=new jvalue[3];
	params[0].i=2L;//jint i
	params[1].d=3.14;//jdouble d
	params[2].c=L'A';//jchar c
	cout<<"1"<<endl;
	jobject static_res=env->CallStaticObjectMethodA(hello_clazz,methodID_staticFunc,params);//����?????
	cout<<"CallBooleanMethodA has invoded�ɹ�:"<<static_res<<endl;
	delete params;
 	*/
	
	
}

	
/*

VC2010 ���û�������
path=C:\Program Files\Microsoft Visual Studio 10.0\Common7\IDE;C:\Program Files\Microsoft Visual Studio 10.0\VC\bin;
lib=C:\Program Files\Microsoft SDKs\Windows\v7.0A\Lib;C:\Program Files\Microsoft Visual Studio 10.0\VC\lib
include=C:\Program Files\Microsoft SDKs\Windows\v7.0A\Include;C:\Program Files\Microsoft Visual Studio 10.0\VC\include

����OK
cl /I "D:\program\Java\jdk1.7.0_05\include\win32" /I "D:\program\Java\jdk1.7.0_05\include" /c  TestCPPNative.cpp
����dll
link  TestCPPNative.obj  /DLL /LIBPATH:"D:\program\Java\jdk1.7.0_05\lib"
*/

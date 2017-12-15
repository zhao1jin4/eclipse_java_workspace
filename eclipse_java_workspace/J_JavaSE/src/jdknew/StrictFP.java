package jdknew;

public class StrictFP 
{

}

//strictfp不能放在接口方法前,也不能放在构造函数前
//可应用于类、接口或方法,让你的浮点运算更加精确
/*
interface A {
    strictfp void f(); 
}
class FpDemo2 {
    strictfp FpDemo2() {}
}
*/
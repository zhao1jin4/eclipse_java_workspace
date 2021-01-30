package jdk15_new;

//---preview   关键字 sealed(密封)  permits
//只有permits关键字后面的类， 能够实现这个接口,必须和封闭类处于同一模块（module）或者包空间（package）里
public abstract sealed class Shape   permits Circle, Rectangle 
{
 
}
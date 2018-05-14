class Rational(n: Int, d: Int) extends Ordered[Rational]  // Ordered  ,自动实现了 > ,<,<=,>=
 {
    def compare(that: Rational) = (this.numer * that.denom) - ( that.numer * this.denom)
  
  
   require(d != 0)  //Predef 包中
   private val g = gcd(n.abs, d.abs) 
   val numer = n / g 
   val denom = d / g 
   def this(n: Int) = this(n, 1) 
   
   def +(that: Rational): Rational = new Rational( numer * that.denom + that.numer * denom, denom * that.denom ) 
   def +(i: Int): Rational = new Rational(numer + i * denom, denom) 
   def -(that: Rational): Rational = new Rational( numer * that.denom - that.numer * denom, denom * that.denom ) 
   def -(i: Int): Rational = new Rational(numer - i* denom, denom) 
   def *(that: Rational): Rational = new Rational(numer * that.numer, denom * that.denom) 
   def *(i: Int): Rational = new Rational(numer * i, denom) 
   def /(that: Rational): Rational = new Rational(numer * that.denom, denom * that.numer) 
   def /(i: Int): Rational = new Rational(numer, denom * i)
   override  def toString = numer+"/"+denom 
   private  def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b) 
   
 }




object MainImplicit  {  
  def testImplicitParam(implicit name2:String)
  {
    println(name2)
  }
  implicit val name1="the implicit name!!" //不根据变量名字
  

  def main(args: Array[String]): Unit =
  {
        implicit def intToRational(x: Int) = new Rational(x)  //自动把整数转换为Rational分数的隐式转换
        val r = new Rational(2,3)
       println( 2*r )
       println(  r > new Rational(3,4) )
       testImplicitParam
       
     }
 }
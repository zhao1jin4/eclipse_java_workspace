
object Calc {
    //最大公约数
    def gcd(x: Long, y: Long): Long = if (y == 0) x else gcd(y, x % y)
    def main(args:Array[String]):Unit=
    {
       println( gcd(20,5))
       println( gcd(8,20))
       
    }
}
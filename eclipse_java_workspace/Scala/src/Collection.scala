import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting

object MainCollection{
  def main(args:Array[String]):Unit=
  {
    
    
    var changeBuffer=ArrayBuffer [Int]()
    changeBuffer+=1
    changeBuffer+=2
    changeBuffer++=Array(3,4,5,6)
    changeBuffer.insert(0, 100,101)
    println(changeBuffer)
    changeBuffer.remove(0)
     println(changeBuffer)
    var  array= changeBuffer.toArray
    
    for(i <-0 until (array.length,2)) //2步长
      print(array(i)+",")
   println("max="+array.max)       
   println("sum="+array.sum)
   Sorting.quickSort(array);
    
   println(array.mkString("---", ",","==="))
    
   
  var addArray= for(i <-array ) yield  i+10
   println(addArray.mkString( "," ))
     
  }
}
import access.{_}  //同 import access._
import java.{sql => S} //名称S引用了java.sql包
import java.util.{Map => _, _} //引用了除Map之外的所有

package outpkg {
  package inpkg { //像C# 格式

    
    
    class Navigator { //class和package 同级

    }
    package threepkg {
      class NavigatorSuite {
        private   var num=10;
        private [this] val nav = new Navigator()  
        private [threepkg]  val nav1 = new three1pkg.Lanch()  //可仿问 本类所在包 的 同级包
        protected val navtop = new _root_.three1pkg.Lanch()//如和外部文件包名同名,加_root_ 跳到顶级包开始找
        
        def theScope
        {
          num+=10
          nav.getClass()
        }
      }
      
      
      class TestScop
      {
         val x=new NavigatorSuite()
         x.nav1 //相于当于同包的
         // x.nav //访问不到
         // x.navtop //访问不到，protected只可子类，不能同包
      }
        
         
    }
    package three1pkg
    {
      
      class Lanch extends threepkg.NavigatorSuite
      {
         var x=this.navtop.name
         var name="in test"
        
         
        
      }
    }
    
    
    
    //所在package要和目录匹配
    object PackageTest 
    {
      def main(args: Array[String]) 
      {
        import test.FrenchDate  //import可放在def中
        
        
        
        println("like C#")
         
      }
    }
    
    
    
  }
}



 
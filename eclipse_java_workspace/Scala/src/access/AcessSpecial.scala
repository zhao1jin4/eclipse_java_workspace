package access {
  package navigation {
    private[access] class Navigator { //类Navigator被标记为private[access]就是说这个类对包含在access包里的所有的类和对象可见。
      protected[navigation] def useStarChart() {} 
      class LegOfJourney {
        private[Navigator] val distance = 100
      }
      private[this] var speed = 200 //this 表示只在类内部使用
    }
  }
  package launch {
    import navigation._
    object Vehicle {
      private[launch] val guide = new Navigator
    }
  }
}
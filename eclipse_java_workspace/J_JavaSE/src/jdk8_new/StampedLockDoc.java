package jdk8_new;

import java.util.concurrent.locks.StampedLock;

//JDK doc
class StampedLockDoc {
   private double x, y;
   
   
   //ReentrantReadWriteLock  沒有任何读写锁时，才可以取得写入锁  (悲观)
   
   //（StampedLock 三种模式（写，读，乐观读）
   //乐观读模式，也就是若读的操作很多，写的操作很少的情况下
   private final StampedLock sl = new StampedLock();

   void move(double deltaX, double deltaY) { // an exclusively locked method
     long stamp = sl.writeLock();
     try {
       x += deltaX;
       y += deltaY;
     } finally {
       sl.unlockWrite(stamp);
     }
   }

   double distanceFromOrigin() { // A read-only method
     long stamp = sl.tryOptimisticRead();//乐观读  , 后面用validate方法 
     double currentX = x, currentY = y;
     if (!sl.validate(stamp)) {//validate返回true,表示这段时没有独占的修改
        stamp = sl.readLock();//如果有独占锁修改,使用读悲观锁 
        try {
          currentX = x;
          currentY = y;
        } finally {
           sl.unlockRead(stamp);
        }
     }
     return Math.sqrt(currentX * currentX + currentY * currentY);
   }

   void moveIfAtOrigin(double newX, double newY) { // upgrade
     // Could instead start with optimistic, not read mode
     long stamp = sl.readLock();
     try {
       while (x == 0.0 && y == 0.0) {
         long ws = sl.tryConvertToWriteLock(stamp); //锁升级
         if (ws != 0L) {//失败返回0
           stamp = ws;
           x = newX;
           y = newY;
           break;
         }
         else {
           sl.unlockRead(stamp);
           stamp = sl.writeLock();
         }
       }
     } finally {
       sl.unlock(stamp);
     }
   }
 }



package jdk8_new;

import java.util.concurrent.locks.StampedLock;

//JDK doc
class StampedLockDoc {
   private double x, y;
   
   
   //ReentrantReadWriteLock  �]���κζ�д��ʱ���ſ���ȡ��д����  (����)
   
   //��StampedLock ����ģʽ��д�������ֹ۶���
   //�ֹ۶�ģʽ��Ҳ���������Ĳ����ܶ࣬д�Ĳ������ٵ������
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
     long stamp = sl.tryOptimisticRead();//�ֹ۶�  , ������validate���� 
     double currentX = x, currentY = y;
     if (!sl.validate(stamp)) {//validate����true,��ʾ���ʱû�ж�ռ���޸�
        stamp = sl.readLock();//����ж�ռ���޸�,ʹ�ö������� 
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
         long ws = sl.tryConvertToWriteLock(stamp); //������
         if (ws != 0L) {//ʧ�ܷ���0
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



package org.zh.visitor.inventory;

abstract class Equipment
{
   public abstract void accept(Visitor vis); 

   public abstract double price();
}

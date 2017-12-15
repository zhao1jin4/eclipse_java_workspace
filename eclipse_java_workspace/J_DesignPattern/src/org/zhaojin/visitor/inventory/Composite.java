package org.zhaojin.visitor.inventory;

import java.util.Vector;
// Composite 模式
abstract class Composite extends Equipment
{
    /**
     * @directed
     * @link aggregation
     * @clientCardinality 1 
     */
	private Vector parts = new Vector(10);

	public Composite()
    {
    }
	
	// Composite 模式
    public void add(Equipment equip) 
    {
		parts.add(equip);
    }

	public double price()
    {
		double total=0;

		for (int i=0; i < parts.size(); i++)
        {
         	total += ((Equipment) parts.get(i)).price();
        }

        return total;
    }

	public void accept(Visitor v)
    {
		for (int i=0; i < parts.size(); i++)
        {
			((Equipment) parts.get(i)).accept(v);
        }
	}
}

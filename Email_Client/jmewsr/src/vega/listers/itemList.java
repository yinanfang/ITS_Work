package vega.listers;

import com.independentsoft.exchange.Item;

public class itemList extends list
{
	
	public itemList (  )
	{
		
	}
	
	public Item getItemEntry ( )
	{
		return (Item) this.itr.next();
	}
}

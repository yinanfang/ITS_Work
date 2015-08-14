package vega.listers;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class list
{
	private ArrayList<Object> al = new ArrayList<Object>();
	protected Iterator<Object> itr = this.al.iterator();
	
	public list ( )
	{
		
	}
	
	public void addEntry ( Object element )
	{
		this.al.add(element);
		this.itr = this.al.iterator();
	}
	
	public boolean hasNext ( )
	{
		return this.itr.hasNext();
	}
	
	public void reset ( )
	{
		this.itr = this.al.iterator();
	}
	
	public int getSize ( )
	{
		return this.al.size();
	}
}

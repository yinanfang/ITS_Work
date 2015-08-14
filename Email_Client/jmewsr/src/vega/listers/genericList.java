package vega.listers;

public class genericList extends list
{
	public genericList (  )
	{
		
	}
	
	public String getListEntry ( )
	{
		return (String) this.itr.next();
	}
}

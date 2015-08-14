
public class soapMessage
{
	private String soapMessageString = "";
	
	public soapMessage ( )
	{
		
	}
	
	public void setValue ( String key, String value )
	{
		this.soapMessageString.replaceAll(key, value);
	}
	
	public String getSoapMessage ( )
	{
		return this.soapMessageString;
	}
}

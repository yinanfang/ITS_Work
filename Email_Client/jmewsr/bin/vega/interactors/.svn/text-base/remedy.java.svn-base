package vega.interactors;
import vega.config.configuration;

import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.WebService;


public abstract class remedy
{
	private configuration conf = new configuration();
	private String ArUser = conf.getARUser();//"jxvega";
	private String ArPassword = conf.getARPassword();//"tropic@l20";
	private String ArServer = conf.getARHost();//"remedy04.isis.unc.edu";
	private int ArPort = Integer.valueOf(conf.getARPort());//32913;//32860;
	protected ARServerUser context = null;//new ARServerUser(ArUser, ArPassword, "","",ArServer,ArPort);
	
	public remedy ( )
	{
		
	}
	
	protected void connect ( )
	{
		this.context = new ARServerUser(this.ArUser, this.ArPassword, "","",this.ArServer);//,this.ArPort);
	}
	
}

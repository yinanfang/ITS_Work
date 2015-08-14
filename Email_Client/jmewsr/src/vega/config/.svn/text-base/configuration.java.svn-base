package vega.config;

import java.util.HashMap;

import vega.controllers.sanity;
import vega.fileIO.filereader;

public class configuration 
{
	private HashMap<String, String> confMap = new HashMap<String, String>();
	
	public configuration ( )
	{
		filereader fr = new filereader();
		String configFile = sanity.configFile;
		this.confMap = fr.getLineMap(configFile);
	}
	
	public String getARUser ( )
	{
		String ARUser = (String) this.confMap.get("aruser");
		return ARUser;
	}
	
	public String getARPassword ( )
	{
		String ARUser = (String) this.confMap.get("arpassword");
		return ARUser;
	}
	
	public String getARHost ( )
	{
		String ARUser = (String) this.confMap.get("arhost");
		return ARUser;
	}
	
	public String getARPort ( )
	{
		String ARUser = (String) this.confMap.get("arport");
		return ARUser;
	}
	
	public String getEWSHost ( )
	{
		String ARUser = (String) this.confMap.get("ewshost");
		return ARUser;
	}
	
	public String getEWSUser ( )
	{
		String ARUser = (String) this.confMap.get("ewsuser");
		return ARUser;
	}
	
	public String getEWSPassword ( )
	{
		String ARUser = (String) this.confMap.get("ewspassword");
		return ARUser;
	}
	
	public String getLogLevel ( )
	{
		String logLevel = (String) this.confMap.get("loglevel");
		return logLevel;
	}
	
	public String getLogFile ( )
	{
		String logfile = (String) this.confMap.get("logfile");
		return logfile;
	}
	
	public String getAllowedEmailListFile ( )
	{
		String allowedEmailsFile = (String) this.confMap.get("allowedemailsfile");
		return allowedEmailsFile;
	}
	
	public String getSpamListFile ( )
	{
		String spamListFile = (String) this.confMap.get("spamlistfile");
		return spamListFile;
	}
	
	public boolean getAllowMoveMessage ( )
	{
		String allowMoveMessage = (String) this.confMap.get("allowmsgmove");
		if ( allowMoveMessage.equalsIgnoreCase("true") ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getSendToRemedyAdmin ( )
	{
		String sendtoremedyadmin = (String) this.confMap.get("sendtoremedyadmin");
		if ( sendtoremedyadmin != null ) {
			if ( sendtoremedyadmin.equalsIgnoreCase("true") ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean getAllowUpdates ( )
	{
		String allowupdates = (String) this.confMap.get("allowupdates");
		if ( allowupdates != null ) {
			if ( allowupdates.equalsIgnoreCase("true") ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

package vega.interactors;

import java.util.ArrayList;
import java.util.List;
import vega.logging.*;
import vega.ticket.ticketEntry;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.SortInfo;

public class remedyLdap extends remedy
{
	private String emailFrom = null;
	private List<Entry> entryList = null;
	private logger logger = new logger();
	
	public remedyLdap(String emailFrom)
	{
		this.emailFrom = emailFrom;
		setResults("EMAIL");
	}
	
	public boolean foundUser()
	{
		if(entryList.isEmpty()) {
			logger.log("DEBUG", "Email was not found in remedy ldap with email: "+this.emailFrom);
			setResults("NAME");
			if(entryList.isEmpty()) {
				return false;
			} else {
				return true;
			}
			//return false; //No user found
		} else {
			logger.log("DEBUG", "Email was found in remedy ldap with email: "+this.emailFrom);
			return true; //User found
		}
	}
	
	public ticketEntry setCustomerData(ticketEntry te)
	{
		if(foundUser()) {
			te.setPid(getPid());
			te.setLastName(getLastName());
			te.setFirstName(getFirstName());
			te.setMiddleInitial(getMiddleInitial());
			te.setPhone(getCampusPhone());
	        te.setOnyen(getOneyn());
	        te.setDepartment(getDept());
	        te.setLocation(getLocation());
		}
		return te;
	}
	public String getFirstName()
	{
		return entryList.get(0).get(536870913).toString();
	}
	
	public String getMiddleInitial()
	{
		if(entryList.get(0).get(536870915).toString() == null) {
			return "@";
		} else {
			return entryList.get(0).get(536870915).toString();
		}
	}
	
	public String getLastName()
	{
		return entryList.get(0).get(536870914).toString();
	}
	
	public String getOneyn()
	{
		return entryList.get(0).get(536870926).toString();
	}
	
	public String getPid()
	{
		return entryList.get(0).get(536870923).toString();
	}
	
	public String getDept()
	{
		return entryList.get(0).get(536870917).toString();
	}
	
	public String getTypeOfPerson()
	{
		return entryList.get(0).get(536870922).toString();
	}
	
	public String getCampusPhone()
	{
		if(entryList.get(0).get(536870918).toString() == null) {
			return "x";
		} else {
			return entryList.get(0).get(536870918).toString();
		}
	}
	
	public String getLocation()
	{
		return entryList.get(0).get(536870916).toString();
	}
	
	private void setResults ( String searchType )
	{
		logger.log("DEBUG", "Begin Query for RemedyLDAP form with email: "+this.emailFrom);
		connect();
		
		List<Field> fields = null;
		QualifierInfo qual = null;
		try {
			fields = this.context.getListFieldObjects("Remedy Directory LDAP");
		} catch (ARException e) {
			logger.log("ERROR", "setResults getListFieldObjects ARException");
			e.printStackTrace();
			return;
		}
		try {
			if (searchType.equalsIgnoreCase("EMAIL")) {
				qual = this.context.parseQualification("\'Email_Id\' = \""+this.emailFrom+"\"", fields, null, 536870920);
			} else {
				String exchangeOnyen = getExchangeSenderOnyen();
				if(exchangeOnyen == null) {
					return;
				}
				qual = this.context.parseQualification("\'Onyen\' = \""+exchangeOnyen+"\"", fields, null, 536870926);
			}
		} catch (ARException e) {
			logger.log("ERROR", "setResults parseQualifications ARException");
			e.printStackTrace();
			return;
		}
		
		//First name, Middle Initial, Last Name, Onyen, PID, Dept., Type (Staff, etc), Campus Phone, location
		int[] fieldIds = {536870913, 536870915, 536870914, 536870926, 536870923, 536870917, 536870922, 536870918, 536870916};
		OutputInteger nMatches = new OutputInteger();
		List<SortInfo> sortOrder = new ArrayList<SortInfo>();
		sortOrder.add(new SortInfo(2, Constants.AR_SORT_DESCENDING));
		try {
			this.entryList = context.getListEntryObjects("Remedy Directory LDAP", qual, 0, Constants.AR_NO_MAX_LIST_RETRIEVE, sortOrder, fieldIds, true, nMatches);
		} catch (ARException e) {
			logger.log("ERROR", "setResults getListEntryObjects ARException");
			e.printStackTrace();
			return;
		}
		logger.log("DEBUG", "End Query for RemedyLDAP form with email: "+this.emailFrom);
	}
	
	private String getExchangeSenderOnyen()
	{
		String exchangeOnyen = null;
		ews ews = new ews();
        String resolvedName = ews.getExchangeNameForEmailAddress(this.emailFrom);
        if ( resolvedName != null ) {
        	if(resolvedName.indexOf("(") > 0 & resolvedName.indexOf(")") > 0) {
        		//String[] tempNameArray = resolvedName.split("\\(");
        		//resolvedName = tempNameArray[0].trim();
        		resolvedName = resolvedName.substring(resolvedName.indexOf("(")+1, resolvedName.indexOf(")"));
        	}
        	exchangeOnyen = resolvedName;
        }
		return exchangeOnyen;
	}

}

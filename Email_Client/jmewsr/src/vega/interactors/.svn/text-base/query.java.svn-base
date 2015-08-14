package vega.interactors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import vega.listers.emailList;
import vega.logging.*;
import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Field;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.SortInfo;


public class query extends remedy
{
	private String emailTo = null;
	private List<Entry> entryList = null;
	private logger logger = new logger();
	
	public query ( )
	{

	}
	
	public boolean hasEmailIn ( )
	{
		if ( this.entryList.size() > 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getOrgGroup ( )
	{
		return entryList.get(0).get(536870914).toString();
	}
	
	public String getStatus ( )
	{
		return entryList.get(0).get(536871035).toString();
	}
	
	public String getSendEmailToClient ( )
	{
		return entryList.get(0).get(536870923).toString();
	}
	
	public String getFollowUpEmail ( )
	{
		return entryList.get(0).get(536871083).toString();
	}
	
	public String getAffeliation  ( )
	{
		return entryList.get(0).get(536870969).toString();
	}
	
	public String getLastName ( )
	{
		return entryList.get(0).get(536870918).toString();
	}
	
	public String getFirstName ( )
	{
		return entryList.get(0).get(536870926).toString();
	}
	
	public String getMiddleInitial ( )
	{
		return entryList.get(0).get(536870966).toString();
	}
	
	public String getPoc ( )
	{
		return entryList.get(0).get(536870924).toString();
	}
	
	public String getPid ( )
	{
		return entryList.get(0).get(536870917).toString();
	}
	
	public String getPhone ( )
	{
		return entryList.get(0).get(536870936).toString();
	}
	
	public String getEmail ( )
	{
		return entryList.get(0).get(536870950).toString();
	}
	
	public String getShortDescr ( )
	{
		return entryList.get(0).get(536870937).toString();
	}
	
	public String getCategory ( )
	{
		return entryList.get(0).get(536871242).toString();
	}
	
	public String getClientProbDescr ( )
	{
		return entryList.get(0).get(536870955).toString();
	}
	
	public String getTopicAffected ( )
	{
		return entryList.get(0).get(536870939).toString();
	}
	
	public String getItemAffected ( )
	{
		return entryList.get(0).get(536870932).toString();
	}
	
	public String getGroupAssigned ( )
	{
		return entryList.get(0).get(536870929).toString();
	}
	
	public String getSeverity ( )
	{
		return entryList.get(0).get(536871069).toString();
	}
	
	public String getPersonAssigned ( )
	{
		return entryList.get(0).get(536870931).toString();
	}
	
	public String getSysCreatorOrgGroupId ( )
	{
		return entryList.get(0).get(536870952).toString();
	}
	public void setByEmailTo ( String emailTo )
	{
		this.emailTo = emailTo.toLowerCase();
		setResults();
	}
	
	private void setResults ( )
	{
		logger.log("DEBUG", "Begin Query for EmailIn form with email: "+this.emailTo);
		connect();
		
		List<Field> fields = null;
		QualifierInfo qual = null;
		try {
			fields = this.context.getListFieldObjects("UNC-RS-Email-In");
		} catch (ARException e) {
			logger.log("ERROR", "setResults getListFieldObjects ARException");
			e.printStackTrace();
			return;
		}
		try {
			qual = this.context.parseQualification("\'Email Identification\' = \""+this.emailTo+"\"", fields, null, 536870913);
		} catch (ARException e) {
			logger.log("ERROR", "setResults parseQualifications ARException");
			e.printStackTrace();
			return;
		}
		
		int[] fieldIds = {536870913, 536870914, 536871035, 536870923, 536871083, 536870969, 536870918, 536870926, 536870966, 536870924, 536870917, 536870936, 536870950, 536870937, 536871242, 536870955, 536870939, 536870932, 536870929, 536871069, 536870931, 536870952};
		OutputInteger nMatches = new OutputInteger();
		List<SortInfo> sortOrder = new ArrayList<SortInfo>();
		sortOrder.add(new SortInfo(2, Constants.AR_SORT_DESCENDING));
		try {
			this.entryList = context.getListEntryObjects("UNC-RS-Email-In", qual, 0, Constants.AR_NO_MAX_LIST_RETRIEVE, sortOrder, fieldIds, true, nMatches);
			//System.out.println("Query returned " + nMatches);
			//if ( nMatches.intValue() > 0 ) {
				//System.out.println("Request ID\t\tShortDescription");
				//System.out.println(entryList.get(i).getEntryId()+"\t\t"+entryList.get(i).get(536870929));
				//entryList.get(0).get(536870929).toString();
			//}
		} catch (ARException e) {
			logger.log("ERROR", "setResults getListEntryObjects ARException");
			e.printStackTrace();
			return;
		}
		logger.log("DEBUG", "End Query for EmailIn form with email: "+this.emailTo);
	}
	
	public emailList getAllEmailInEmails ( )
	{
		emailList el = new emailList();
		connect();
		
		List<Field> fields = null;
		QualifierInfo qual = null;
		try {
			fields = this.context.getListFieldObjects("UNC-RS-Email-In");
		} catch (ARException e) {
			e.printStackTrace();
		}
		try {
			qual = this.context.parseQualification("\'Email Identification\' = \""+this.emailTo+"\"", fields, null, 536870913);
		} catch (ARException e) {
			e.printStackTrace();
		}
		
		int[] fieldIds = {536870913};
		OutputInteger nMatches = new OutputInteger();
		List<SortInfo> sortOrder = new ArrayList<SortInfo>();
		sortOrder.add(new SortInfo(2, Constants.AR_SORT_DESCENDING));
		try {
			this.entryList = context.getListEntryObjects("UNC-RS-Email-In", qual, 0, Constants.AR_NO_MAX_LIST_RETRIEVE, sortOrder, fieldIds, true, nMatches);
			Iterator<Entry> itr = this.entryList.iterator();
			while ( itr.hasNext() ) {
				el.addEntry(itr.next().get(536870913).toString());
			}
		} catch (ARException e) {
			e.printStackTrace();
		}
		
		return el;
	}
}

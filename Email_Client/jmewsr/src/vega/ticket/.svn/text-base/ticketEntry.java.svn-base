package vega.ticket;

import com.bmc.arsys.api.*;
import com.independentsoft.exchange.ItemId;

public class ticketEntry
{
	private String sysintreqnum = null;
	private String status = null;
	private String affeliation = null;
	private String pid = null;
	private String lastName = null;
	private String firstName = null;
	private String middleInitial = null;
	private String department = null;
	private String onyen = null;
	private String poc = null;
	private String phone = null;
	private String email = null;
	private String location = null;
	private String building = null;
	private String additionalInfo = null;
	private String groupAssigned = null;
	private String shortDescr = null;
	private String clientProbDescr = null;
	private String personAssigned = null;
	private String category = null;
	private String topicAffected = null;
	private String itemAffected = null;
	private String severity = null;
	private String worklog = "";
	private ItemId messageId = null;
	private boolean sysperllookup = false;
	private String sysCreatorOrgGroupId = null;
	
	private Entry newEntry = new Entry();
	
	public ticketEntry ( )
	{
		
	}
	
	/*****************************
           SETTER METHODS
	 ****************************/
	public void setEntry ( Entry entry )
	{
		this.newEntry = entry;
	}
	public void setSysintreqnum ( String entryId )
	{
		this.sysintreqnum = entryId;
		this.newEntry.put(536870988, new Value(entryId));
	}
	
	public void setStatus ( String status )
	{
		this.status = status;
		this.newEntry.put(536871035, new Value(status));
	}
	public void setAffeliation ( String affeliation )
	{
		this.affeliation = affeliation;
		this.newEntry.put(536870969, new Value(affeliation));
	}
	
	public void setPid ( String pid )
	{
		this.pid = pid;
		this.newEntry.put(536870917, new Value(pid));
	}
	public void setLastName ( String lastName )
	{
		this.lastName = lastName;
		this.newEntry.put(536870918, new Value(lastName));
	}
	public void setFirstName ( String firstName )
	{
		this.firstName = firstName;
		this.newEntry.put(536870926, new Value(firstName));
	}
	public void setMiddleInitial ( String middleInitial )
	{
		this.middleInitial = middleInitial;
		this.newEntry.put(536870966, new Value(middleInitial));
		
	}
	public void setDepartment ( String department )
	{
		this.department = department;
		this.newEntry.put(536870963, new Value(department));
		
	}
	public void setOnyen ( String onyen )
	{
		this.onyen = onyen;
		this.newEntry.put(536871172, new Value(onyen));
	}
	public void setPoc ( String poc )
	{
		this.poc = poc;
		this.newEntry.put(536870924, new Value(poc));
	}
	public void setPhone ( String phone )
	{
		this.phone = phone;
		this.newEntry.put(536870936, new Value(phone));
	}
	public void setEmail ( String email )
	{
		this.email = email;
		this.newEntry.put(536870950, new Value(email));
	}
	public void setLocation ( String location )
	{
		this.location = location;
		this.newEntry.put(536870948, new Value(location));
	}
	public void setBuilding ( String building )
	{
		this.building = building;
		this.newEntry.put(536871240, new Value(building));
	}
	public void setAdditionalInfo ( String additionalInfo )
	{
		this.additionalInfo = additionalInfo;
		this.newEntry.put(536870938, new Value(additionalInfo));
	}
	public void setGroupAssigned ( String groupAssigned )
	{
		this.groupAssigned = groupAssigned;
		this.newEntry.put(536870929, new Value(groupAssigned));
	}
	public void setShortDescr ( String shortDescr )
	{
		this.shortDescr = shortDescr;
		this.newEntry.put(536870937, new Value(shortDescr));
	}
	public void setClientProbDescr ( String clientProbDescr )
	{
		this.clientProbDescr = clientProbDescr;
		this.newEntry.put(536870955, new Value(clientProbDescr));
	}
	public void setPersonAssigned ( String personAssigned )
	{
		this.personAssigned = personAssigned;
		this.newEntry.put(536870931, new Value(personAssigned));
	}
	public void setCategory ( String category )
	{
		this.category = category;
		newEntry.put(536871242, new Value(category));
	}
	public void setTopicAffected ( String topicAffected )
	{
		this.topicAffected = topicAffected;
		newEntry.put(536870939, new Value(topicAffected));
	}
	public void setItemAffected ( String itemAffected )
	{
		this.itemAffected = itemAffected;
		newEntry.put(536870932, new Value(itemAffected));
	}
	public void setSeverity ( String severity )
	{
		this.severity = severity;
		this.newEntry.put(536871069, new Value(severity));
	}
	public void setWorklog ( String worklog )
	{
		this.worklog = worklog;
		this.newEntry.put(536870923, new Value(worklog));
	}
	public void setMessageId ( ItemId id )
	{
		this.messageId = id;
	}
	public void setSysPerlLookup ( boolean value )
	{
		this.sysperllookup = value;
		if ( value == true ) {
			//this.newEntry.put(536871267, new Value("Email Template+Onyen-Lookup"));
			this.newEntry.put(536871267, new Value("Email Template-Email-In Lookup"));
			this.newEntry.put(536871191, new Value("Email-In"));
		}
	}
	public void setSysCreatorOrgGroupId ( String value )
	{
		this.sysCreatorOrgGroupId = value;
		this.newEntry.put(536870952, new Value(value));
	}
	/*****************************
	       GETTER METHODS
	 ****************************/
	public Entry getEntry ( )
	{
		return this.newEntry;
	}
	public String getSysintreqnum ( )
	{
		return this.sysintreqnum;
	}
	public String getStatus ( )
	{
		return this.status;
	}
	public String getAffeliation ( )
	{
		return this.affeliation;
	}
	public String getPid ( )
	{
		return this.pid;
	}
	public String getLastName ( )
	{
		return this.lastName;
	}
	public String getFirstName ( )
	{
		return this.firstName;
	}
	public String getMiddleInitial ( )
	{
		return this.middleInitial;
	}
	public String getDepartment ( )
	{
		return this.department;
	}
	public String getOnyen ( )
	{
		return this.onyen;
	}
	public String getPoc ( )
	{
		return this.poc;
	}
	public String getPhone ( )
	{
		return this.phone;
	}
	public String getEmail ( )
	{
		return this.email;
	}
	public String getLocation ( )
	{
		return this.location;
	}
	public String getBuilding ( )
	{
		return this.building;
	}
	public String getAdditionalInfo ( )
	{
		return this.additionalInfo;
	}
	public String getGroupAssigned ( )
	{
		return this.groupAssigned;
	}
	public String getShortDescr ( )
	{
		return this.shortDescr;
	}
	public String getClientProbDescr ( )
	{
		return this.clientProbDescr;
	}
	public String getPersonAssigned ( )
	{
		return this.personAssigned;
	}
	public String getCategory ( )
	{
		return this.category;
	}
	public String getTopicAffected ( )
	{
		return this.topicAffected;
	}
	public String getItemAffected ( )
	{
		return this.itemAffected;
	}
	public String getSeverity ( )
	{
		return this.severity;
	}
	public String getWorklog ( )
	{
		return this.worklog;
	}
	public ItemId getMessageId ( )
	{
		return this.messageId;
	}
	public boolean getSysPerlLookup ( )
	{
		return this.sysperllookup;
	}
	public String getSysCreatorOrgGroupId ( )
	{
		return this.sysCreatorOrgGroupId;
	}
}

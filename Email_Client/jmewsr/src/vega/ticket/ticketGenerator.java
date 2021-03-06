package vega.ticket;

import java.util.List;
import java.util.ListIterator;

import vega.config.configuration;
import vega.fileIO.filereader;
import vega.interactors.ews;
import vega.interactors.query;
import vega.interactors.remedyLdap;
import vega.listers.*;
import vega.logging.*;

import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Item;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.MapiPropertyTag;
import com.independentsoft.exchange.Message;


public class ticketGenerator
{
	private logger logger = new logger();
	
	public ticketGenerator ( )
	{
		
	}
	
	private int ticketNumExtractor ( String subject )
	{
		subject = subject.toUpperCase();
		int ticketIndex = subject.indexOf("TICKET");
		subject = subject.substring(ticketIndex, subject.length());
		String ticket = subject.replaceAll("[^0-9]", "");
		if ( ! ticket.equals("") && ticket.length() == 7) {  //previously, this was causing an error if the value was < 7, indexoutofbounds
			ticket = ticket.substring(0, 7);
			int ticketNum = Integer.valueOf(ticket);
			return ticketNum;
		} else {
			logger.log("INFO", "\"Ticket\" was detected in the subject line, but there is no valid ticket number present.");
			return 0;
		}
	}
	
	private String resolveDisplayName ( String email )
	{
		ews ews = new ews();
        String resolvedEmailAddress = ews.getEmailAddressForDisplayName(email);
        if ( resolvedEmailAddress != null ) {
        	email = resolvedEmailAddress;
        }
		return email;
	}
	
	private void messageDataTest ( Message message )
	{
		ews ews = new ews();
		ews.getEmailAddressForDisplayToV2(message);
	}
	
	private void getMessageInfo ( Message message )
	{
		ews ews = new ews();
		ews.getEmailAddressForDisplayToV2(message);
	}
	
	private String getForwardedEmailFrom ( Message message )
	{
		String[] headerInfo = null;
		String fromName = null;
		String msgBody = message.getBodyPlainText();
		if ( msgBody.indexOf("From:") > -1 || msgBody.indexOf("from:") > -1 ) {
			headerInfo = msgBody.split(":");
			//Search for where "From" is
			for ( int i = 0; i < headerInfo.length; i++ ) {
				if ( headerInfo[i].contains("From") || headerInfo[i].contains("from") ) {
					//Safe to assume that the next element will have the From address mixed with either "Sent" or "To" or "Reply-To" or "Date"
					for ( int j = i+1; j < headerInfo.length; j++ ) {
						if ( headerInfo[j].contains("Subject") || headerInfo[j].contains("Sent") || headerInfo[j].contains("To") || headerInfo[j].contains("Reply-To") || headerInfo[j].contains("Date") || headerInfo[j].contains("CC") ) {
							String fromLine = headerInfo[j];
							//fromLine.substring(fromLine.indexOf("From")+3);
							fromLine = fromLine.replaceAll("Sent", "");
							fromLine = fromLine.replaceAll("To", "");
							fromLine = fromLine.replaceAll("Reply-To", "");
							fromLine = fromLine.replaceAll("Date", "");
							fromLine = fromLine.replaceAll("CC", "");
							fromLine = fromLine.replaceAll("\"", "");
							fromLine = fromLine.replaceAll("Subject", "");
							
							if ( fromLine.contains("<") ) {
								fromLine = fromLine.substring(0, fromLine.indexOf("<"));
							}
							if ( fromLine.contains("[") ) {
								fromLine = fromLine.substring(0, fromLine.indexOf("["));
							}
							fromName = fromLine;
							break;
						}
					}
				}
			}
		}
		
		return fromName;
	}
	
	public String[] lastResortEmailResolution ( Item item )
	{
		ews ews = new ews();
		String filename = ews.downloadMessage(item);
		filereader fr = new filereader();
		String toLine = fr.getMatchingLine(filename, "To:");
		String ccLine = fr.getMatchingLine(filename, "CC:");
		String[] toLineArray = toLine.split(">");
		//String[] ccLineArray = null;
		String[] allEmailsArray;
		
		if ( ccLine != null ) {
			String[] ccLineArray = ccLine.split(">");
			int toEmailCount = toLineArray.length + ccLineArray.length;
			allEmailsArray = new String[toEmailCount];
			System.arraycopy(toLineArray, 0, allEmailsArray, 0, toLineArray.length);
			System.arraycopy(ccLineArray, 0, allEmailsArray, toLineArray.length, ccLineArray.length);
		} else {
			int toEmailCount = toLineArray.length;
			allEmailsArray = new String[toEmailCount];
			System.arraycopy(toLineArray, 0, allEmailsArray, 0, toLineArray.length);
		}
		
		//Determine true size of all emails array
		int emailsCount = 0;
		for ( int i = 0; i < allEmailsArray.length; i++ ) {
			if ( allEmailsArray[i].contains("@") ) {
				emailsCount++;
			}
		}
		//Remove any entries that do not contain "@" and save the rest
		String[] emailArray = new String[emailsCount];
		int emailArrayPosition = 0;
		for ( int i = 0; i < allEmailsArray.length; i++ ) {
			if ( allEmailsArray[i].contains("@") ) {
				emailArray[emailArrayPosition] = allEmailsArray[i];
				emailArrayPosition++;
			}
		}
		//Clean up emails in the emailArray
		for ( int i = 0; i < emailArray.length; i++ ) {
			emailArray[i] = emailArray[i].substring(emailArray[i].indexOf("<")+1, emailArray[i].length());
		}
		return emailArray;
	}
	
	public String[] getRecipients ( Item item )
	{
		logger.log("DEBUG", "Retrieving recipients from eml file");
		ews ews = new ews();
		logger.log("DEBUG", "Begin downloading message as eml file");
		String filename = ews.downloadMessage(item);
		logger.log("DEBUG", "End downloading message as eml file");
		filereader fr = new filereader();
		//String recipientLine = fr.getConcatSubsequentLines(filename, "From:", "MIME-Version:", "Message-ID:"); //This line works, but trying a new range
		String recipientLine = fr.getConcatSubsequentLinesMultiExclude(filename, "Received:", "MIME-Version: ", "From:", "Return-Path:", "Reply-To:", "Message-ID:"); //added space after "MIME-Version:"
		logger.log("DEBUG", "RAW Recipient Line: "+recipientLine);
		String[] recipientLineArray = recipientLine.split(">");
		String[] allEmailsArray;
		
		//Determine true size of all emails array
		int emailsCount = 0;
		for ( int i = 0; i < recipientLineArray.length; i++ ) {
			if ( recipientLineArray[i].contains("@") ) {
				emailsCount++;
			}
		}
				
		//Remove any entries that do not contain "@" and save the rest
		String[] emailArray = new String[emailsCount];
		int emailArrayPosition = 0;
		for ( int i = 0; i < recipientLineArray.length; i++ ) {
			if ( recipientLineArray[i].contains("@") ) {
				emailArray[emailArrayPosition] = recipientLineArray[i];
				emailArrayPosition++;
			}
		}
		//Clean up emails in the emailArray
		for ( int i = 0; i < emailArray.length; i++ ) {
			emailArray[i] = emailArray[i].substring(emailArray[i].indexOf("<")+1, emailArray[i].length());
		}
		
		//Remove large emails
		//Determine true size of all emails array
		logger.log("DEBUG", "Begin removing email non-relevant email addresses (greater than 40 characters in length)");
		emailsCount = 0;
		for ( int i = 0; i < emailArray.length; i++ ) {
			if ( emailArray[i].length() <= 40 ) {
				emailsCount++;
			}
		}
		
		String[] cleanEmailArray = new String[emailsCount];
		emailArrayPosition = 0;
		for ( int i = 0; i < emailArray.length; i++ ) {
			if ( emailArray[i].length() <= 40 ) {
				logger.log("DEBUG", "Keeping: "+emailArray[i]);
				cleanEmailArray[emailArrayPosition] = emailArray[i];
				emailArrayPosition++;
			} else {
				logger.log("DEBUG", "Removing: "+emailArray[i]);
			}
		}
		logger.log("DEBUG", "End removing email non-relevant email addresses (greater than 40 characters in length)");
		
		//Remove duplicate emails from list
		logger.log("DEBUG", "Begin removing duplicate email addresses from list");
		String[] cleanUniqueEmailAddressTemp = new String[cleanEmailArray.length];
		int cueaPos = 0;
		for ( int i = 0; i < cleanEmailArray.length; i++ ) {
			boolean writeFlag = true;
			for ( int j = 0; j < cleanEmailArray.length; j++ ) {
				if ( cleanEmailArray[i].equals(cleanUniqueEmailAddressTemp[j]) ) {
					writeFlag = false;
				}
			}
			if ( writeFlag == true ) {
				cleanUniqueEmailAddressTemp[cueaPos] = cleanEmailArray[i];
				cueaPos++;
			}
		}
		//Resize the cleanUniqueEmailAddress array
		String[] cleanUniqueEmailAddress = new String[cueaPos];
		for ( int i = 0; i < cueaPos; i++ ) {
			cleanUniqueEmailAddress[i] = cleanUniqueEmailAddressTemp[i];
		}
		logger.log("DEBUG", "End removing duplicate email addresses from list");
		logger.log("DEBUG", "End retrieving recipients from eml file");
		//return cleanEmailArray;
		return cleanUniqueEmailAddress;
	}
	public String getFromAddress ( String filename )
	{
		logger.log("DEBUG", "Retrieving from address");
		filereader fr = new filereader();
		String fromLine = fr.getMatchingLine(filename, "From:");
		String fromAddress = fromLine.substring(fromLine.indexOf("<")+1, fromLine.length()-1);
		logger.log("DEBUG", "End retrieving from address");
		return fromAddress;
	}
	
	public String isForwardedFrom ( String filename )
	{
		logger.log("DEBUG", "Checking if message is a forward");
		filereader fr = new filereader();
		String line = fr.getLineBetweenKeys(filename,"MIME-Version:", "From:");
		String fromAddress = null;
		if ( line.contains("@") ) {
			if (line.contains("<") ) {
				fromAddress = line.substring(line.indexOf("<")+1, line.indexOf(">"));
			}
			
			if ( line.contains("[") ) {
				fromAddress = line.substring(line.indexOf("[")+1, line.indexOf("]"));
			}
			
			if ( fromAddress.contains("mailto:") ) {
				fromAddress = fromAddress.replaceFirst("mailto:", "");
			}
		}
		logger.log("DEBUG", "End checking if message is a forward");
		return fromAddress;

	}
	
	public String isForwardedFromLine ( String filename )
	{
		logger.log("DEBUG", "Checking if message is a forward (line only method)");
		filereader fr = new filereader();
		String line = fr.getTargetLineBetweenKeys(filename,"MIME-Version: ", "From:"); //Added space after the ":"
		String fromAddress = null;
		if ( line != null ) {
			line = line.substring(line.indexOf("From:")+1, line.length()); //Remove any problematic characters such as "<" from the start of the line.
			if ( line.contains("@") ) {
				if (line.contains("<") && line.contains(">") ) {
					fromAddress = line.substring(line.indexOf("<")+1, line.indexOf(">"));
				}
				
				if ( line.contains("[") ) {
					fromAddress = line.substring(line.indexOf("[")+1, line.indexOf("]"));
				}
				
				if ( fromAddress != null ) {
					if ( fromAddress.contains("mailto:") ) {
						fromAddress = fromAddress.replaceFirst("mailto:", "");
					}
				}
			} else if ( !line.contains("@") && line.contains("From:") ) {
				fromAddress = line.substring(line.indexOf(":")+2, line.length());
				//Remove any characters that may be appended to the name.
				if ( fromAddress.indexOf("=") > -1 ) {
					logger.log("DEBUG", "Detected an odd character and removing it");
					fromAddress = fromAddress.substring(0,fromAddress.indexOf("="));
				}
			} else {
				fromAddress = null; //do nothing
			}
		}
		logger.log("DEBUG", "End checking if message is a forward (line only method)");
		//Prevent resolving of null fromAddress
		if ( fromAddress != null ) {
			fromAddress = resolveDisplayName(fromAddress);
		}
		//fromAddress = resolveDisplayName(fromAddress);
		return fromAddress;
	}
	
	public ticketList createTicketEntries ( itemList il )//messageList ml )
	{
		logger.log("DEBUG", "Begin creating ticket entries");
		configuration conf = new configuration();
		ticketList tl = new ticketList();
		
		logger.log("DEBUG", "Begin looping through message list");
    	while ( il.hasNext() ) {
    		logger.log("DEBUG", "Begin Retrieving message from message list");
    		Item item = il.getItemEntry();
    		ews ews = new ews();
    		Message message = ews.getMessageForItem(item);
    		//getMessageInfo(message);
    		logger.log("DEBUG", "End Retrieving message from message list");
    		//Conditions to create ticket? Update ticket?
            
            //query query = new query(message.getDisplayTo());
    		query query = new query();
            //String emailTo = message.getToRecipients().get(0).getEmailAddress().toString();
    		
    		//New emailTo Gathering
    		//String[] emailToArray = message.getDisplayTo().replaceAll(" ", "").split(";");
    		/*String[] emailToArray = message.getDisplayTo().split(";");
    		//String[] emailCCArray = null;
    		String[] allEmailsArray = null;
    		if ( ! message.getDisplayCc().isEmpty() ) {
    			//emailCCArray = message.getDisplayCc().replaceAll(" ", "").split(";");
    			emailCCArray = message.getDisplayCc().split(";");
    		}
    		if ( ! message.getDisplayCc().isEmpty() ) {
    			allEmailsArray = new String[emailToArray.length+emailCCArray.length];
    		} else {
    			allEmailsArray = new String[emailToArray.length];
    		}
    		System.arraycopy(emailToArray, 0, allEmailsArray, 0, emailToArray.length);
    		if ( ! message.getDisplayCc().isEmpty() ) {
    			System.arraycopy(emailCCArray, 0, allEmailsArray, emailToArray.length, emailCCArray.length);
    		}*/
    		String[] allEmailsArray = getRecipients(item); // New method of obtaining email to addresses
    		
    		logger.log("DEBUG", "Begin Show Emails Extrated");
    		for ( int i = 0; i < allEmailsArray.length; i++ ) {
    			logger.log("DEBUG", allEmailsArray[i].trim());
    		}
    		logger.log("DEBUG", "End Show Emails Extracted");
    		//End new emailTo Gathering
    		
            String subject = message.getSubject().toLowerCase();
            String entryId = null;
            if ( subject.contains("ticket") ) {
            	logger.log("DEBUG", "Message contains ticket key word in subject");
            	//subject = subject.replaceAll(" ", "");
            	//subject = subject.replaceAll("update", "");
            	//entryId = subject.replaceAll("ticket", "");
            	entryId = String.valueOf(ticketNumExtractor(subject));
            	String padding = "";
            	for ( int j = 0; j < 15-entryId.length(); j++ ) {
            		padding = padding +"0";
            	}
            	entryId = padding + entryId;
            	logger.log("DEBUG", "Ticket extracted and padded from subject line: "+entryId);
            	if ( Integer.valueOf(entryId) == 0 ) {
            		entryId = null;
            		logger.log("DEBUG", "Will not use update logic.");
            	}
            }
            for ( int i = 0; i < allEmailsArray.length; i++ ) {
	            String emailTo = allEmailsArray[i].trim();//message.getDisplayTo();
	            /*if ( subject.contains("ticket") ) {
	            	logger.log("DEBUG", "Message contains ticket key word in subject");
	            	//subject = subject.replaceAll(" ", "");
	            	//subject = subject.replaceAll("update", "");
	            	//entryId = subject.replaceAll("ticket", "");
	            	entryId = String.valueOf(ticketNumExtractor(subject));
	            	String padding = "";
	            	for ( int j = 0; j < 15-entryId.length(); j++ ) {
	            		padding = padding +"0";
	            	}
	            	entryId = padding + entryId;
	            	logger.log("DEBUG", "Ticket extracted and padded from subject line: "+entryId);
	            }*/
	            logger.log("DEBUG", "Begin get info from EmailIn form");
	            //Resolve displayTo to email address
	            emailTo = resolveDisplayName(emailTo);
	            //End Resolving displayTo to email address
	            //Does "resolved" email to address have an "@" symbol?
	            if ( !emailTo.contains("@") ) {
	            	logger.log("INFO", "Resolved email to address is not a valid email address");
	            } else {
	            	//Download eml and parse for email to address
	            	//lastResortEmailResolution(message.getItemId());
	            }
	            //End checking "resolved email to address for "@" symbol
	            query.setByEmailTo(emailTo);
	            logger.log("DEBUG", "Check email has EmailIn with email: "+emailTo);
	            if (entryId == null && query.hasEmailIn() ) {
	            	logger.log("DEBUG", "Email: "+emailTo+" has an EmailIn form");
		            //Test that query has stuff in it before proceeding
	            	logger.log("DEBUG", "Begin creating ticket entry");
	            	ticketEntry te = new ticketEntry();
	            	te.setStatus(query.getStatus());
		            te.setAffeliation(query.getAffeliation());
		    		te.setPoc(query.getPoc());
		    		//Resolve name
		    		String fromEmailAddress = getFromAddress("message.eml");
		    		//String forwardedFrom = isForwardedFrom("message.eml");
		    		String forwardedFrom = isForwardedFromLine("message.eml");
		    		if ( forwardedFrom != null ) {
		    			logger.log("INFO", "Email is forwarded from: "+forwardedFrom+" Using forwarded from address");
		    			fromEmailAddress = forwardedFrom;
		    			fromEmailAddress = resolveDisplayName(fromEmailAddress);
		    			logger.log("INFO", "End setting forwarded fromEmailAddress");
		    		} else {
		    			logger.log("DEBUG", "Message is not a forward");
		    		}
		    		//String fromEmailAddress = resolveDisplayName(message.getExtendedProperty(MapiPropertyTag.PR_SENT_REPRESENTING_EMAIL_ADDRESS).getValue());
		    		te.setEmail(fromEmailAddress);
	    			remedyLdap rldap = null;
		    		if (fromEmailAddress.indexOf("email.unc.edu") < 0) { //If does not contain email.unc.edu, do a remedy ldap look up
		    			rldap = new remedyLdap(fromEmailAddress);
		    			te = rldap.setCustomerData(te);
		    		}
		    		if ( rldap == null ) {
		    			te.setPid(query.getPid());
			    		te.setLastName(query.getLastName());
			    		te.setFirstName(query.getFirstName());
			    		te.setMiddleInitial(query.getMiddleInitial());
			    		te.setPhone(query.getPhone());
		    		}
		    		if ( rldap != null ) {
		    			if (rldap.foundUser() == false) {
		    				te.setPid(query.getPid());
				    		te.setLastName(query.getLastName());
				    		te.setFirstName(query.getFirstName());
				    		te.setMiddleInitial(query.getMiddleInitial());
				    		te.setPhone(query.getPhone());
		    			}
		    		}
		    		//if ( forwardedEmailFrom != null ) {
		    			//Resolve name
		    			//String forwardedEmailFromAddress = resolveDisplayTo(forwardedEmailFrom);
		    			//te.setEmail(forwardedEmailFromAddress);
		    		//} else {
		    		//End has forwarded email from
		    			//te.setEmail(query.getEmail());
		    		//}
		    		if ( conf.getSendToRemedyAdmin() ) {
		    			logger.log("INFO", "Setting group assigned to REMEDY-ADMIN");
		    			te.setGroupAssigned("REMEDY-ADMIN");
		    		} else {
		    			logger.log("INFO", "Setting group assigned to "+query.getGroupAssigned());
		    			te.setGroupAssigned(query.getGroupAssigned());
		    		}
		    		if ( query.getShortDescr().equalsIgnoreCase("Blank") ) {
		    			logger.log("INFO", "No Short Description found. Using the email's subject");
		    			te.setShortDescr(subject); // Use email's subject if the short description is "Blank"
		    		} else {
		    			logger.log("INFO", "A Short Description was found. Using the email-in's Short Description");
		    			te.setShortDescr(query.getShortDescr());
		    		}
		    		
		    		if ( query.getClientProbDescr() == null ) {
		    			logger.log("INFO", "No Client Problem Description found. Using the email's subject");
		    			te.setClientProbDescr(subject); // Use email's subject if client problem description is empty
		    		} else {
		    			logger.log("INFO", "A Client Problem Description was found. Using the email-in's Client Problem Description");
		    			te.setClientProbDescr(query.getClientProbDescr());
		    		}
		    		te.setCategory(query.getCategory());
		    		te.setPersonAssigned(query.getPersonAssigned());
		    		te.setTopicAffected(query.getTopicAffected());
			    	te.setItemAffected(query.getItemAffected());
		    		te.setSeverity(query.getSeverity());
		    		//te.setWorklog(message.getBodyPlainText());
		    		te.setWorklog(message.getBodyPlainText());
		    		te.setSysintreqnum(entryId);
		    		te.setSysCreatorOrgGroupId(query.getSysCreatorOrgGroupId());
		    		//te.setMessageId((ItemId)message.getItemId());
		    		//System.out.println(message.getItemId().getId());
		    		//System.out.println((ItemId)message.getItemId());
		    		te.setMessageId(message.getItemId());
		    		te.setSysPerlLookup(true); //Sets customer info automatically
		    		logger.log("DEBUG", "End creating ticket entry");
		    		logger.log("DEBUG", "Begin adding ticket entry to ticket list");
		    		tl.addEntry(te);
		    		logger.log("DEBUG", "End adding ticket entry to ticket list");
		    		logger.log("INFO", "No longer necessary to check email-in's for recipients");
		    		break;
	            } else if ( entryId != null ) { //Potentially need to modify this to check and make sure it is the proper length || ticket exists
	            	logger.log("DEBUG", "Begin creating update ticket entry");
	            	ticketEntry te = new ticketEntry();
	            	te.setSysintreqnum(entryId);
	            	te.setMessageId(message.getItemId());
	            	te.setWorklog(message.getBodyPlainText());
	            	tl.addEntry(te);
	            	entryId = null; // Don't want the same ticket updated twice.
		    		logger.log("DEBUG", "End adding update ticket entry to ticket list");
		    		break; //No need to check all the recipients for an update ticket
	            } else {
	            	logger.log("DEBUG", "Email: "+ emailTo+" has no EmailIn form");
	            	if ( i == allEmailsArray.length -1 ) { //If we are at the last recipient and no email in form has been found, then move to not processed
	            		if ( conf.getAllowMoveMessage() ) {
	            			logger.log("INFO", "Reached the final recipient for the message and no email-in form has been found. Moving to not processed");
	            			ews.moveMessage(message.getItemId(), "Not-Processed");
						} else {
							logger.log("INFO", "not allowing messages to be moved");
						}
	            	}
	            }
	            logger.log("DEBUG", "End get info from EmailIn form");
            }
    	}
    	logger.log("DEBUG", "End looping through message list");
    	
    	return tl;
	}
}

package vega.interactors;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vega.config.configuration;
import vega.fileIO.filereader;
import vega.listers.genericList;
import vega.listers.itemList;
import vega.listers.messageList;
import vega.logging.logger;

import com.independentsoft.exchange.Attachment;
import com.independentsoft.exchange.AttachmentInfo;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.Contact;
import com.independentsoft.exchange.ContactPropertyPath;
import com.independentsoft.exchange.ContainmentComparison;
import com.independentsoft.exchange.ContainmentMode;
import com.independentsoft.exchange.Contains;
import com.independentsoft.exchange.FileAttachment;
import com.independentsoft.exchange.FindFolderResponse;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Item;
import com.independentsoft.exchange.ItemAttachment;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.MapiPropertyTag;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.PropertyPath;
import com.independentsoft.exchange.Resolution;
import com.independentsoft.exchange.ResolveNamesResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.ShapeType;
import com.independentsoft.exchange.StandardFolder;

public class ews
{
	private Service service;
	private logger logger = new logger();
	
	public ews ( )
	{
		this.setService();
	}
	
	private void setService ( )
	{
		configuration conf = new configuration();
		
		logger.log("DEBUG", "Setting service to "+conf.getEWSHost()+" with user "+conf.getEWSUser());
		service = new Service(conf.getEWSHost(), conf.getEWSUser(), conf.getEWSPassword());
		//service = new Service("https://outlook.unc.edu/ews/Exchange.asmx", "its_remedy.svc@ad.unc.edu", "!Jan13-812");
		//service = new Service("https://outlook.unc.edu/ews/Exchange.asmx", "jxvega", "tropic@l20");
	}
	
	public Contains setSubjectFilter ( )
	{
		Contains restriction = new Contains(MessagePropertyPath.SUBJECT, "Test", ContainmentMode.PREFIXED, ContainmentComparison.IGNORE_CASE);
		return restriction;
	}
	
	public Contains setFromFilter ( String emailTo )
	{
		Contains restriction = new Contains(MessagePropertyPath.TO_RECIPIENTS, emailTo, ContainmentMode.PREFIXED, ContainmentComparison.IGNORE_CASE);
		return restriction;
	}
	
	public FindItemResponse getUnreadInboxItems ( )
	{
		logger.log("DEBUG", "Retrieving unread inbox items");
		 IsEqualTo restriction = new IsEqualTo(MessagePropertyPath.IS_READ, false);
         FindItemResponse response = null;
		try {
			response = service.findItem(StandardFolder.INBOX, MessagePropertyPath.getAllPropertyPaths(), restriction);
		} catch (ServiceException e) {
			logger.log("ERROR", "getUnreadInboxItems ServiceException");
			//e.printStackTrace();
		}
		logger.log("DEBUG", "End retrieving unread inbox items");
         return response;
	}
	
	//This method is purely to strip the inbox of any and all attachments.
	public void stripInboxAttachments()
	{
		logger.log("DEBUG","Checking for attachments");
		IsEqualTo restriction = new IsEqualTo(MessagePropertyPath.HAS_ATTACHMENTS, true);
    	ItemShape itemShape = new ItemShape(ShapeType.ID);
    	FindItemResponse response = null;
    	try
    	{
    		response = service.findItem(StandardFolder.INBOX, itemShape, restriction);
    		for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Message)
                {
                    ItemId itemId = response.getItems().get(i).getItemId();
                    Message message = service.getMessage(itemId);
                    List<AttachmentInfo> attachmentsInfo = message.getAttachments();
                    for (int j = 0; j < attachmentsInfo.size(); j++)
                    {
                    	ItemId noAttachedItem = service.deleteAttachment(attachmentsInfo.get(j).getId());
                    	itemId = noAttachedItem;
                    	logger.log("DEBUG", "Stripped attachment " + j + " of " + attachmentsInfo.size());
                    }
                }
            }    		
    	}
    	catch(ServiceException e)
    	{
    		logger.log("ERROR", "Error stripping attachments" + e);
    	}
	}
	
	public FindItemResponse getAllInboxItems ( )
	{
		logger.log("DEBUG", "Retrieving all inbox items");
		
		List<PropertyPath> propertyPaths = new ArrayList<PropertyPath>();
		propertyPaths.add(MessagePropertyPath.BCC_RECIPIENTS);
		propertyPaths.add(MessagePropertyPath.BODY_RTF);
		propertyPaths.add(MessagePropertyPath.CC_RECIPIENTS);
		propertyPaths.add(MessagePropertyPath.DISPLAY_CC);
		propertyPaths.add(MessagePropertyPath.DISPLAY_TO);
		propertyPaths.add(MessagePropertyPath.FROM);
		propertyPaths.add(MessagePropertyPath.IN_REPLY_TO);
		propertyPaths.add(MessagePropertyPath.INTERNET_MESSAGE_HEADERS);
		propertyPaths.add(MessagePropertyPath.INTERNET_MESSAGE_ID);
		propertyPaths.add(MessagePropertyPath.IS_RESEND);
		propertyPaths.add(MessagePropertyPath.ITEM_ID);
		propertyPaths.add(MessagePropertyPath.MESSAGE_FLAGS);
		propertyPaths.add(MessagePropertyPath.RECEIVED_BY);
		propertyPaths.add(MessagePropertyPath.RECEIVED_REPRESENTING);
		propertyPaths.add(MessagePropertyPath.SENDER);
		propertyPaths.add(MessagePropertyPath.TO_RECIPIENTS);
		propertyPaths.add(MessagePropertyPath.ATTACHMENTS);
		propertyPaths.add(MessagePropertyPath.BODY_PLAIN_TEXT);
		propertyPaths.add(MessagePropertyPath.DISPLAY_NAME);
		propertyPaths.add(MessagePropertyPath.HAS_ATTACHMENTS);
		propertyPaths.add(MessagePropertyPath.SUBJECT);
	
		propertyPaths.add(MapiPropertyTag.PR_SENDER_EMAIL_ADDRESS);
		propertyPaths.add(MapiPropertyTag.PR_SENT_REPRESENTING_EMAIL_ADDRESS);
		//propertyPaths.add(MapiPropertyTag.PR_EMAIL_ADDRESS);
		propertyPaths.add(MapiPropertyTag.PR_CONTACT_EMAIL_ADDRESSES);
		propertyPaths.add(MapiPropertyTag.PR_RECEIVED_BY_EMAIL_ADDRESS);
		propertyPaths.add(MapiPropertyTag.PR_BODY);
		propertyPaths.add(MapiPropertyTag.PR_BODY_CRC);
		propertyPaths.add(MapiPropertyTag.PR_HTML);
		propertyPaths.add(MapiPropertyTag.PR_DISPLAY_TO);
		propertyPaths.add(MapiPropertyTag.PR_DISPLAY_NAME);
		propertyPaths.add(MapiPropertyTag.PR_ADDRESS_BOOK_DISPLAY_NAME);
		//propertyPaths.add(MapiPropertyTag.PR_DETAILS_TABLE);
		propertyPaths.add(MapiPropertyTag.PR_EMAIL_ADDRESS);
		propertyPaths.add(MapiPropertyTag.PR_EMS_AB_DISPLAY_NAME_PRINTABLE);
		//propertyPaths.add(MapiPropertyTag.PR_DEF_CREATE_MAILUSER);
		propertyPaths.add(MapiPropertyTag.PR_DISCLOSURE_OF_RECIPIENTS);
		propertyPaths.add(MapiPropertyTag.PR_DELEGATES_DISPLAY_NAMES);
		propertyPaths.add(MapiPropertyTag.PR_DELIVER_TO_DN);
		propertyPaths.add(MapiPropertyTag.PR_DELIVERY_POINT);
		propertyPaths.add(MapiPropertyTag.PR_END_P1_RECIP);
		propertyPaths.add(MapiPropertyTag.PR_END_MESSAGE);
		propertyPaths.add(MapiPropertyTag.PR_END_RECIP);
		//propertyPaths.add(MapiPropertyTag.PR_MESSAGE_RECIPIENTS);
		propertyPaths.add(MapiPropertyTag.PR_ORIGINAL_SENDER_EMAIL_ADDRESS);
		propertyPaths.add(MapiPropertyTag.PR_ORIGINALLY_INTENDED_RECIP_ADDRTYPE);
		propertyPaths.add(MapiPropertyTag.PR_SMTP_ADDRESS);
		//propertyPaths.add(MapiPropertyTag.PR_X400_ENVELOPE_TYPE);
		propertyPaths.add(MapiPropertyTag.PR_EMS_AB_DISPLAY_NAME_PRINTABLE);
		propertyPaths.add(MapiPropertyTag.PR_TRANSPORT_MESSAGE_HEADERS);
		propertyPaths.add(MapiPropertyTag.PR_IDENTITY_DISPLAY);
		propertyPaths.add(MapiPropertyTag.PR_ENTRYID);
		propertyPaths.add(MapiPropertyTag.PR_ADDRTYPE);
		propertyPaths.add(MapiPropertyTag.PR_RESPONSIBILITY);
		propertyPaths.add(MapiPropertyTag.PR_ADDRBOOK_FOR_LOCAL_SITE_ENTRYID);
		propertyPaths.add(MapiPropertyTag.PR_ADDRESS_BOOK_ENTRYID);
		
		
        FindItemResponse response = null;
		try {
			ItemShape itemShape = new ItemShape(ShapeType.ID);
			//response = service.findItem(StandardFolder.INBOX, MessagePropertyPath.getAllPropertyPaths());
			response = service.findItem(StandardFolder.INBOX, propertyPaths);
		} catch (ServiceException e) {
			logger.log("ERROR", "getAllInboxItems ServiceException");
			//e.printStackTrace();
		}
		logger.log("DEBUG", "End retrieving all inbox items");
        return response;
	}

	public FindItemResponse getInboxItemsForDownload ( )
	{
		FindItemResponse response = null;
		logger.log("DEBUG", "Retrieving inbox items for download");
		ItemShape itemShape = new ItemShape(ShapeType.ID);
        try {
			response = service.findItem(StandardFolder.INBOX, itemShape);
		} catch (ServiceException e) {
			logger.log("ERROR", "getInboxItemsForDownload ServiceException");
			e.printStackTrace();
		}
        
        return response;
	}
	
	public String downloadMessage ( Item item )
	{
		String filename = null;
		try {
			Message message = getMessageForItem(item);
			String mimeContent = message.getMimeContent().getText();
			Charset charset = Charset.forName("UTF-8");
            ByteBuffer byteBuffer = charset.encode(mimeContent);
            byte[] buffer = new byte[byteBuffer.limit()];
            System.arraycopy(byteBuffer.array(), 0, buffer, 0, buffer.length);
            filename = "message.eml";
            FileOutputStream fileStream = new FileOutputStream(filename);
            fileStream.write(buffer);
            fileStream.close();
		} catch (FileNotFoundException e) {
			logger.log("ERROR", "downloadMessage FileNotFoundException");
			//e.printStackTrace();
		} catch (IOException e) {
			logger.log("ERROR", "downloadMessage IOException");
			//e.printStackTrace();
		}
		
		return filename;
	}
	
	public Message getMessageForItem ( Item item )
	{
		Message message = null;
		ItemId itemId = item.getItemId();
		try {
			message = service.getMessage(itemId);
		} catch (ServiceException e) {
			logger.log("ERROR", "getMessageForITem ServiceException");
			//e.printStackTrace();
		}
		
		return message;
	}
	/*public String getInboxMessagesForDownload ( )
	{
		String filename = null;
		FindItemResponse response = null;
		logger.log("DEBUG", "Retrieving inbox messages for download");
		ItemShape itemShape = new ItemShape(ShapeType.ID);
        try {
			response = service.findItem(StandardFolder.INBOX, itemShape);
			for ( int i = 0; i < response.getItems().size(); i++ ) {
				ItemId itemId = response.getItems().get(i).getItemId();
				Message msg = service.getMessage(itemId);
				String mimeContent = msg.getMimeContent().getText();
	            Charset charset = Charset.forName("UTF-8");
	            ByteBuffer byteBuffer = charset.encode(mimeContent);
	            byte[] buffer = new byte[byteBuffer.limit()];
	            System.arraycopy(byteBuffer.array(), 0, buffer, 0, buffer.length);
	            filename = "message.eml";
	            FileOutputStream fileStream = new FileOutputStream(filename);
	            fileStream.write(buffer);
	            fileStream.close();
			}
		} catch (ServiceException e) {
			logger.log("ERROR", "getInboxMessagesForDownload ServiceException");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.log("ERROR", "getInboxMessagesForDownload FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			logger.log("ERROR", "getInboxMessagesForDownload IOException");
			e.printStackTrace();
		}
        logger.log("DEBUG", "End retrieving all messages for download");
        return filename;
	}*/
	
	public FindItemResponse getInboxItems ( Contains restriction )
	{
		FindItemResponse response = null;
		try {
			response = service.findItem(StandardFolder.INBOX, MessagePropertyPath.getAllPropertyPaths(), restriction);
		} catch (ServiceException e) {
			logger.log("ERROR", "getInboxItems ServiceException");
			//System.out.println(e.getMessage());
            //System.out.println(e.getXmlMessage());
            e.printStackTrace();
		}
		
		return response;
	}
	
	public itemList getItems ( FindItemResponse response )
	{
		logger.log("DEBUG", "Retrieving messages");
		itemList il = new itemList();
		logger.log("DEBUG", "There are "+response.getItems().size()+" messages");
		for (int i = 0; i < response.getItems().size(); i++)
        {
			if (response.getItems().get(i) instanceof Message)
            {
				Item item = response.getItems().get(i);
                //Message message = (Message) item;
                Message message = getMessageForItem(item);
                //Remove this
                /*if ( message.getDisplayTo().equals("physicsremedy@unc.edu") ) {
                	ml.addEntry(message);
                }*/
                
                if ( canSaveEmail(message.getDisplayTo()) ) {
                	//Prevent more than 20 of the same messages from beig in the list
                	/*if ( il.getSize() > 0 ) {
	                	il.reset();
	                	while ( il.hasNext() ) {
	                		Message msg = getMessageForItem(il.getItemEntry());
	                		String displayTo = message.getDisplayTo();
	                		String tempSubject = msg.getSubject();
	                		String subject = message.getSubject();
	                		String tempContent = msg.getBodyPlainText();
	                		String content = message.getBodyPlainText();
	                		if ( ! tempSubject.equals(subject) || ! tempContent.equals(content) ) {
	                			logger.log("INFO", "Message is unique");
	                			il.addEntry(item);
	                			logger.log("DEBUG", "Message added to message list");
	                		} else {
	                			logger.log("INFO", "Message with\n\nsubject:\n"+msg.getSubject()+"\n\nContent: \n"+msg.getBodyPlainText()+"\n\n is already in the message list. Not including this one.");
	                		}
	                	}
                	} else { //End prevention*/
                		il.addEntry(item);
                		logger.log("DEBUG", "Message added to message list");
                	//}
                }
                //End remove this
                //ml.addEntry(message);
            }
        }
		logger.log("DEBUG", "End retrieving messages");
		
		return il;
	}
	
	public messageList getMessages ( FindItemResponse response )
	{
		logger.log("DEBUG", "Retrieving messages");
		messageList ml = new messageList();
		logger.log("DEBUG", "There are "+response.getItems().size()+" messages");
		for (int i = 0; i < response.getItems().size(); i++)
        {
            if (response.getItems().get(i) instanceof Message)
            {
                Message message = (Message) response.getItems().get(i);
                //Remove this
                /*if ( message.getDisplayTo().equals("physicsremedy@unc.edu") ) {
                	ml.addEntry(message);
                }*/
                
                if ( canSaveEmail(message.getDisplayTo()) ) {
                	ml.addEntry(message);
                	logger.log("DEBUG", "Message added to message list");
                }
                //End remove this
                //ml.addEntry(message);
            }
        }
		logger.log("DEBUG", "End retrieving messages");
		
		return ml;
	}
	
	public ItemId moveMessage ( ItemId id, String toFolder )
	{
		FindItemResponse firesponse = null;
		try {
			firesponse = service.findItem(StandardFolder.INBOX);
		} catch (ServiceException e2) {
			logger.log("ERROR", "moveMessage error with finding inbox");
			//e2.printStackTrace();
		}
		
		//find the item id to move
		Item item = null;
		for ( int i = 0; i < firesponse.getItems().size(); i++ ) {
			String tempId = firesponse.getItems().get(i).getItemId().getId();
			if ( id.getId().equals(tempId) ) {
				item = firesponse.getItems().get(i);
				break;
			}
		}
		//find processed folder
		FolderId folderId = null;
		FindFolderResponse findFolderResponse = null;
		ItemInfoResponse ifresponse = null;
		try {
			findFolderResponse = service.findFolder(StandardFolder.MAILBOX_ROOT);
		} catch (ServiceException e1) {
			logger.log("ERROR", "moveMessage error with Finding folder");
			//e1.printStackTrace();
		}
		
		for ( int i = 0; i < findFolderResponse.getFolders().size(); i++ ) {
			if ( findFolderResponse.getFolders().get(i).getDisplayName().equals(toFolder) ) {
				folderId = findFolderResponse.getFolders().get(i).getFolderId();
				break;
			}
		}
		try {
			Message message = new Message();
			message.setSubject(item.getSubject());
			message.setBody(new Body(item.getBodyPlainText()));
			Message oldMessage = (Message)item; //Can't get the original display to address without casting the item to Message.
			message.getToRecipients().add(new Mailbox(oldMessage.getDisplayTo())); //change this
			ifresponse = service.moveItem(id, folderId);
		} catch ( ServiceException e ) {
			logger.log("ERROR", "moveMessage error with moving item");
			//System.out.println(e.getMessage());
		}
		
		ItemId newItemId = ifresponse.getItems().get(0).getItemId();
		
		return newItemId;
	}
	
	public String getEmailAddressForDisplayName ( String name )
	{
		String emailToAddress = null;
		try
        {
			logger.log("DEBUG", "Retrieving email address for display to: "+name);
            ResolveNamesResponse response = service.resolveNames(name);
            for (int i = 0; i < response.getResolutions().size(); i++)
            {
                Resolution resolution = response.getResolutions().get(i);
                if (resolution != null && resolution.getMailbox() != null)
                {
                    Mailbox mailbox = resolution.getMailbox();
                    logger.log("DEBUG", "Resovling within mailbox logic");
                    logger.log("DEBUG", "Resolved Name for "+name+" is: "+mailbox.getName());
                    logger.log("DEBUG", "Resolved Email Address for "+name+" is: "+mailbox.getEmailAddress());
                    emailToAddress = mailbox.getEmailAddress();
                }
                
                if (resolution != null && resolution.getContact() != null)
                {
                    Contact contact = resolution.getContact();
                    logger.log("DEBUG", "Resolving within Contact logic");
                    logger.log("DEBUG", "Resolved Name for "+name+" is: "+contact.getGivenName());
                    logger.log("DEBUG", "Resolved Email Address for "+name+" is: "+contact.getEmail1Address());
                    emailToAddress = contact.getEmail1Address();
                }
            }
        }

        catch (ServiceException e)
        {
            logger.log("INFO", "Failed to retrieve email address for display to: "+name);
            //e.printStackTrace();
        }
		return emailToAddress;
	}
	
	public String getExchangeNameForEmailAddress ( String email )
	{
		String exchangeName = null;
		try
        {
			logger.log("DEBUG", "Retrieving exchange name for email: "+email);
            ResolveNamesResponse response = service.resolveNames(email);
            for (int i = 0; i < response.getResolutions().size(); i++)
            {
                Resolution resolution = response.getResolutions().get(i);
                if (resolution != null && resolution.getMailbox() != null)
                {
                    Mailbox mailbox = resolution.getMailbox();
                    logger.log("DEBUG", "Resolving within mailbox logic");
                    logger.log("DEBUG", "Resolved Name for "+email+" is: "+mailbox.getName());
                    //logger.log("DEBUG", "Resolved Email Address for "+email+" is: "+mailbox.getEmailAddress());
                    exchangeName = mailbox.getName();
                }
                
                if (resolution != null && resolution.getContact() != null)
                {
                    Contact contact = resolution.getContact();
                    logger.log("DEBUG", "Resolving within Contact logic");
                    logger.log("DEBUG", "Resolved Name for "+email+" is: "+contact.getGivenName());
                    //logger.log("DEBUG", "Resolved Email Address for "+email+" is: "+contact.getEmail1Address());
                    exchangeName = contact.getGivenName();
                }
            }
        }

        catch (ServiceException e)
        {
            logger.log("INFO", "Failed to retrieve email address for display to: "+email);
            //e.printStackTrace();
        }
		return exchangeName;
	}

	public void getEmailAddressForDisplayToV2 ( Message message )
	{
		try {
			/*
			System.out.println("Subject = " + message.getSubject());
			System.out.println("ReceivedTime = " + message.getReceivedTime());
			System.out.println("SentTime = " + message.getSentTime());
			System.out.println("HasAttachments = " + message.hasAttachments());
			System.out.println("IsRead = " + message.isRead());
			System.out.println(message.getExtendedProperty(MapiPropertyTag.PR_BODY_CRC));
			if ( message.getExtendedProperty(MapiPropertyTag.PR_SENT_REPRESENTING_EMAIL_ADDRESS) != null ) {
				System.out.println("From email = " + message.getExtendedProperty(MapiPropertyTag.PR_SENT_REPRESENTING_EMAIL_ADDRESS).getValue());
			}
			if ( message.getExtendedProperty(MapiPropertyTag.PR_CONTACT_EMAIL_ADDRESSES) != null ) {
				System.out.println("To email = " + message.getExtendedProperty(MapiPropertyTag.PR_CONTACT_EMAIL_ADDRESSES).getValue());
			}
			if ( message.getExtendedProperty(MapiPropertyTag.PR_RECEIVED_BY_EMAIL_ADDRESS) != null ) {
				System.out.println("To = " + message.getExtendedProperty(MapiPropertyTag.PR_RECEIVED_BY_EMAIL_ADDRESS).getValue());
			}
			if ( message.getExtendedProperty(MapiPropertyTag.PR_SENDER_EMAIL_ADDRESS) != null ) {
				System.out.println("To = " + message.getExtendedProperty(MapiPropertyTag.PR_SENDER_EMAIL_ADDRESS).getValue());
			}
			if (message.getExtendedProperty(MapiPropertyTag.PR_BODY)!= null ) {
				System.out.println("Plain body = " + message.getExtendedProperty(MapiPropertyTag.PR_BODY).getValue());
			}
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DISPLAY_TO) != null) {
				System.out.println("Display To = " +message.getExtendedProperty(MapiPropertyTag.PR_DISPLAY_TO));
			}
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DISPLAY_NAME) != null ) {
				System.out.println("Display Name = " +message.getExtendedProperty(MapiPropertyTag.PR_DISPLAY_NAME));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ADDRESS_BOOK_DISPLAY_NAME) != null ) {
				System.out.println("Address book display name =" +message.getExtendedProperty(MapiPropertyTag.PR_ADDRESS_BOOK_DISPLAY_NAME).getValue());
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DETAILS_TABLE) != null ) {
				System.out.println("Details Table = "+message.getExtendedProperty(MapiPropertyTag.PR_DETAILS_TABLE));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_EMAIL_ADDRESS) != null ) {
				System.out.println("PR Email Address = "+message.getExtendedProperty(MapiPropertyTag.PR_EMAIL_ADDRESS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_EMS_AB_DISPLAY_NAME_PRINTABLE) != null ) {
				System.out.println("EMS AP DISPLAY NAME PRINTABLE = "+message.getExtendedProperty(MapiPropertyTag.PR_EMS_AB_DISPLAY_NAME_PRINTABLE));
			}
			
			/*if ( message.getExtendedProperty(MapiPropertyTag.PR_DEF_CREATE_MAILUSER) != null ) {
				System.out.println("DEF CREATE MAILUSER = "+message.getExtendedProperty(MapiPropertyTag.PR_DEF_CREATE_MAILUSER));
			}*/
			/*
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DISCLOSURE_OF_RECIPIENTS) != null ) {
				System.out.println("A = "+message.getExtendedProperty(MapiPropertyTag.PR_DISCLOSURE_OF_RECIPIENTS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DELEGATES_DISPLAY_NAMES) != null ) {
				System.out.println("B = "+message.getExtendedProperty(MapiPropertyTag.PR_DELEGATES_DISPLAY_NAMES));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DELIVER_TO_DN) != null ) {
				System.out.println("C = "+message.getExtendedProperty(MapiPropertyTag.PR_DELIVER_TO_DN));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_DELIVERY_POINT) != null ) {
				System.out.println("D = "+message.getExtendedProperty(MapiPropertyTag.PR_DELIVERY_POINT));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_END_P1_RECIP) != null ) {
				System.out.println("E = "+message.getExtendedProperty(MapiPropertyTag.PR_END_P1_RECIP));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_END_MESSAGE) != null ) {
				System.out.println("F = "+message.getExtendedProperty(MapiPropertyTag.PR_END_MESSAGE));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_END_RECIP) != null ) {
				System.out.println("G = "+message.getExtendedProperty(MapiPropertyTag.PR_END_RECIP));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_MESSAGE_RECIPIENTS) != null ) {
				System.out.println("H = "+message.getExtendedProperty(MapiPropertyTag.PR_MESSAGE_RECIPIENTS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ORIGINAL_SENDER_EMAIL_ADDRESS) != null ) {
				System.out.println("I = "+message.getExtendedProperty(MapiPropertyTag.PR_ORIGINAL_SENDER_EMAIL_ADDRESS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ORIGINALLY_INTENDED_RECIP_ADDRTYPE) != null ) {
				System.out.println("J = "+message.getExtendedProperty(MapiPropertyTag.PR_ORIGINALLY_INTENDED_RECIP_ADDRTYPE));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ORIGINAL_SENDER_EMAIL_ADDRESS) != null ) {
				System.out.println("K = "+message.getExtendedProperty(MapiPropertyTag.PR_ORIGINAL_SENDER_EMAIL_ADDRESS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ORIGINALLY_INTENDED_RECIP_ADDRTYPE) != null ) {
				System.out.println("L = "+message.getExtendedProperty(MapiPropertyTag.PR_ORIGINALLY_INTENDED_RECIP_ADDRTYPE));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_SMTP_ADDRESS) != null ) {
				System.out.println("M = "+message.getExtendedProperty(MapiPropertyTag.PR_SMTP_ADDRESS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_EMS_AB_DISPLAY_NAME_PRINTABLE) != null ) {
				System.out.println("N = "+message.getExtendedProperty(MapiPropertyTag.PR_EMS_AB_DISPLAY_NAME_PRINTABLE));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_TRANSPORT_MESSAGE_HEADERS) != null ) {
				System.out.println("O = "+message.getExtendedProperty(MapiPropertyTag.PR_TRANSPORT_MESSAGE_HEADERS));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_IDENTITY_DISPLAY) != null ) {
				System.out.println("P = "+message.getExtendedProperty(MapiPropertyTag.PR_IDENTITY_DISPLAY));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ENTRYID) != null ) {
				System.out.println("Q = "+message.getExtendedProperty(MapiPropertyTag.PR_ENTRYID));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ADDRTYPE) != null ) {
				System.out.println("R = "+message.getExtendedProperty(MapiPropertyTag.PR_ADDRTYPE));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_RESPONSIBILITY) != null ) {
				System.out.println("S = "+message.getExtendedProperty(MapiPropertyTag.PR_RESPONSIBILITY));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ADDRBOOK_FOR_LOCAL_SITE_ENTRYID) != null ) {
				System.out.println("T = "+message.getExtendedProperty(MapiPropertyTag.PR_ADDRBOOK_FOR_LOCAL_SITE_ENTRYID));
			}
			
			if ( message.getExtendedProperty(MapiPropertyTag.PR_ADDRESS_BOOK_ENTRYID) != null ) {
				System.out.println("U = "+message.getExtendedProperty(MapiPropertyTag.PR_ADDRESS_BOOK_ENTRYID));
			}
			*/
			
            /*
			Object props = MessagePropertyPath.getAllPropertyPaths();
			String displayName = message.getDisplayName();
			String displayTo = message.getDisplayTo();
			Object rcpts = message.getToRecipients();
			
			Item item = (Item)message;
			item.getExtendedProperty(MapiPropertyTag.PR_MESSAGE_RECIPIENTS);
			*/
			
			System.exit(-1);
			
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		//System.exit(-1);
	}
	
	private boolean canSaveEmail ( String emailTo )
	{
		if ( isAllowedEmailTo(emailTo) && !isSpamListed(emailTo) ) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isAllowedEmailTo ( String emailTo )
	{
		boolean returnValue = true;
		
		filereader fr = new filereader();
		configuration conf = new configuration();
		
		String allowedEmailListFile = conf.getAllowedEmailListFile();
		
		if ( allowedEmailListFile != null ) {
			logger.log("INFO", "allowed email list is in use");
			logger.log("DEBUG", "Begin checking if email: "+emailTo+" is in allowed list");
			
			genericList allowedEmails = fr.getLineList(allowedEmailListFile);
			
			boolean emailFound = false;
			
			while ( allowedEmails.hasNext() ) {
				if ( emailTo.equalsIgnoreCase(allowedEmails.getListEntry()) ) {
					logger.log("DEBUG", "Email: "+emailTo+" is in allowed list");
					//logger.log("DEBUG", "End checking if email: "+emailTo+" is in allowed list");
					emailFound = true;
				}
			}
			
			if ( !emailFound ) {
				logger.log("DEBUG", "Email: "+emailTo+" is not in allowed list");
			}
			
			logger.log("DEBUG", "End checking if email: "+emailTo+" is in allowed list");
			returnValue = emailFound;
		}
		
		if ( allowedEmailListFile == null ) {
			logger.log("INFO", "allowed email list is not in use");
			returnValue = true;
		}
		
		return returnValue;
	}
	
	private boolean isSpamListed ( String emailTo )
	{
		boolean returnValue = false;
		
		filereader fr = new filereader();
		configuration conf = new configuration();
		
		String spamListFile = conf.getSpamListFile();
		
		if ( spamListFile != null ) {
			logger.log("INFO", "spam email list is in use");
			logger.log("DEBUG", "Begin checking if email: "+emailTo+" is in spam list");
			
			genericList spamEmails = fr.getLineList(spamListFile);
			
			boolean emailFound = false;
			
			while ( spamEmails.hasNext() ) {
				if ( emailTo.equalsIgnoreCase(spamEmails.getListEntry()) ) {
					logger.log("DEBUG", "Email: "+emailTo+" is in spam list");
					//logger.log("DEBUG", "End checking if email: "+emailTo+" is in allowed list");
					emailFound = true;
				}
			}
			
			if ( !emailFound ) {
				logger.log("DEBUG", "Email: "+emailTo+" is not in spam list");
			}
			
			logger.log("DEBUG", "End checking if email: "+emailTo+" is in spam list");
			returnValue = emailFound;
		}
		
		if ( spamListFile == null ) {
			logger.log("INFO", "spam email list is not in use");
			returnValue = false;
		}
		
		return returnValue;
	}
}

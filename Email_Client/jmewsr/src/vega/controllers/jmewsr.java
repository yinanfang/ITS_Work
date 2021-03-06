package vega.controllers;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import vega.interactors.ews;
import vega.interactors.ticketer;
import vega.listers.entryIdList;
import vega.listers.itemList;
import vega.listers.messageList;
import vega.listers.ticketList;
import vega.logging.logger;
import vega.ticket.ticketGenerator;

import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.ShapeType;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.Service;

public class jmewsr
{
    public static void main(String[] args)
    {
    	Logger.getRootLogger().setLevel(Level.OFF);
    	sanity sanity = new sanity();
    	sanity.check(args);
    	
    	logger logger = new logger();
    	
    	logger.log("INFO", "BEGIN JMEWSR");
    	logger.log("INFO", "Begin Message/Item List Generation Phase");
        //messageList ml = generateMessageList();
        itemList il = generateItemsList();
        logger.log("INFO", "End Message/Item List Generation Phase");
        logger.log("INFO", "Begin Ticket List Generation Phase");
        //ticketList tl = generateTicketList(ml);
        ticketList tl = generateTicketList(il);
        logger.log("INFO", "End Ticket List Generation Phase");
        logger.log("INFO", "Begin Ticket Generation Phase");
        entryIdList eil = generateTickets(tl);
        logger.log("INFO", "End Ticket Generation Phase");
        logger.log("INFO", "Begin ticket (entry id) dump");
        while ( eil.hasNext() ) {
    		logger.log("INFO", eil.getEntryId());
        	//System.out.println(eil.getEntryId());
    	}
        logger.log("INFO", "End ticket (entry id) dump");
        logger.log("INFO", "END JMEWSR");
    }
    
    private static messageList generateMessageList ( )
    {
    	ews ews = new ews();
    	FindItemResponse unreadInboxItems = ews.getAllInboxItems();//ews.getUnreadInboxItems();
    	messageList ml = ews.getMessages(unreadInboxItems);
    	
    	return ml;
    }
    
    private static itemList generateItemsList ( )
    {
    	ews ews = new ews();
    	ews.stripInboxAttachments();
    	FindItemResponse allInboxItems = ews.getInboxItemsForDownload();
    	itemList il = ews.getItems(allInboxItems);    	
    	return il;
    }
    
    /*private static ticketList generateTicketList ( messageList ml )
    {
    	ticketGenerator tg = new ticketGenerator();
    	ticketList tl = tg.createTicketEntries(ml);
    	
    	return tl;
    }*/
    
    private static ticketList generateTicketList ( itemList il )
    {
    	ticketGenerator tg = new ticketGenerator();
    	ticketList tl = tg.createTicketEntries(il);
    	
    	return tl;
    }
    
    private static entryIdList generateTickets ( ticketList tl )
    {
    	ticketer ticketer = new ticketer();
    	entryIdList eil = ticketer.delegator(tl);
    	
    	return eil;
    }    
}
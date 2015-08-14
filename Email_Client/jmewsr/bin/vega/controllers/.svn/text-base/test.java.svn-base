package vega.controllers;

import vega.interactors.ews;
import vega.interactors.ticketer;
import vega.listers.entryIdList;
import vega.listers.itemList;
import vega.listers.messageList;
import vega.listers.ticketList;
import vega.logging.logger;
import vega.ticket.ticketGenerator;

import com.independentsoft.exchange.FindItemResponse;

public class test
{
	public static logger logger = new logger();
	
    public static void main(String[] args)
    {
    	//EWS will create ticket objects
    	//When EWS finishes, each ticket goes through the process of having a soap message object created and sent to the soap object
    	logger.log("INFO", "BEGIN JMEWSR");
    	logger.log("INFO", "Begin Message List Generation Phase");
        //messageList ml = generateMessageList();
    	itemList il = generateMessageList();
        logger.log("INFO", "End Message List Generation Phase");
        logger.log("INFO", "Begin Ticket List Generation Phase");
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
    
    private static itemList generateMessageList ( )
    {
    	ews ews = new ews();
    	FindItemResponse unreadInboxItems = ews.getInboxItemsForDownload();
    	itemList il = ews.getItems(unreadInboxItems);
    	
    	return il;
    }
    
    private static ticketList generateTicketList ( itemList il )
    {
    	ticketGenerator tg = new ticketGenerator();
    	//ticketList tl = tg.createTicketEntries(ml);
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
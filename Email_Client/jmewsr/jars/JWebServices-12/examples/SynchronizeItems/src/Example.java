import java.io.IOException;
import java.util.List;

import com.independentsoft.exchange.Item;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ReadFlagChange;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.SyncItemsResponse;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            //Initial synchronization. It will retrieve all existing items from the specified folder 
            SyncItemsResponse response = service.syncItems(StandardFolder.INBOX);

            //Keep synchronization state in order to use it in next synchronizations
            String state = response.getState();

            List<Item> allExistingItems = response.getCreatedItems();

            System.out.println("Wait until new messages arrive or create/mark read/delete messages in the Inbox folder and press ENTER");
            System.in.read();

            //Synchronize items again but now include the state parameter in order to find changes from last synchronization
            SyncItemsResponse newResponse = service.syncItems(StandardFolder.INBOX, state);

            List<Item> newItems = newResponse.getCreatedItems();
            List<ItemId> deletedItems = newResponse.getDeletedItems();
            List<Item> updatedItems = newResponse.getUpdatedItems();
            List<ReadFlagChange> readFlagChangedItems = newResponse.getReadFlagChangedItems();

            //Display new items
            for (int i = 0; i < newItems.size(); i++)
            {
                System.out.println("New = " + newItems.get(i).getSubject());
                System.out.println("New ItemId= " + newItems.get(i).getItemId());
            }

            //Display deleted items
            for (int i = 0; i < deletedItems.size(); i++)
            {
                System.out.println("Deleted ItemId = " + deletedItems.get(i));
            }

            //Display updated items
            for (int i = 0; i < updatedItems.size(); i++)
            {
                System.out.println("Updated = " + updatedItems.get(i).getSubject());
                System.out.println("Updated ItemId = " + updatedItems.get(i).getItemId());
            }

            //Display items marked read/unread
            for (int i = 0; i < readFlagChangedItems.size(); i++)
            {
                System.out.println("Marked read/unread = " + readFlagChangedItems.get(i).getItemId());
                System.out.println("IsRead = " + readFlagChangedItems.get(i).isRead());
            }

        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

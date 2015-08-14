import java.util.List;

import com.independentsoft.exchange.Folder;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.SyncFoldersResponse;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            //Initial synchronization. It will recursively retrieve all existing folders from the mailbox 
            SyncFoldersResponse response = service.syncFolders(StandardFolder.MAILBOX_ROOT);

            //Keep synchronization state in order to use it in next synchronizations
            String state = response.getState();

            List<Folder> allExistingFolders = response.getCreatedFolders();

            //Create new folder
            FolderId folderId = service.createFolder("test", StandardFolder.MAILBOX_ROOT);

            //Synchronize folders again but now include the state parameter in order to find changes from last synchronization
            SyncFoldersResponse newResponse = service.syncFolders(StandardFolder.MAILBOX_ROOT, state);

            List<Folder> newFolders = newResponse.getCreatedFolders();
            List<FolderId> deletedFolders = newResponse.getDeletedFolders();
            List<Folder> updatedFolders = newResponse.getUpdatedFolders();

            //Display new created folders
            for (int i = 0; i < newFolders.size(); i++)
            {
                System.out.println(newFolders.get(i).getDisplayName());
                System.out.println(newFolders.get(i).getFolderId());
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

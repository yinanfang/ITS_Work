import com.independentsoft.exchange.FindFolderResponse;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            FolderId backupFolderId = service.createFolder("Backup", StandardFolder.MAILBOX_ROOT);

            FindFolderResponse findFolderResponse = service.findFolder(StandardFolder.DRAFTS);

            for (int i = 0; i < findFolderResponse.getFolders().size(); i++)
            {
                FolderId currentFolderId = findFolderResponse.getFolders().get(i).getFolderId();

                FolderId newFolderId = service.copyFolder(currentFolderId, backupFolderId);
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

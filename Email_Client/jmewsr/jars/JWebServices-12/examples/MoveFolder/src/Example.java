import com.independentsoft.exchange.FindFolderResponse;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(FolderPropertyPath.DISPLAY_NAME, "Test1");

            FindFolderResponse findFolderResponse = service.findFolder(StandardFolder.MAILBOX_ROOT, restriction);

            for (int i = 0; i < findFolderResponse.getFolders().size(); i++)
            {
                FolderId currentFolderId = findFolderResponse.getFolders().get(i).getFolderId();

                FolderId newFolderId = service.moveFolder(currentFolderId, StandardFolder.DRAFTS);
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

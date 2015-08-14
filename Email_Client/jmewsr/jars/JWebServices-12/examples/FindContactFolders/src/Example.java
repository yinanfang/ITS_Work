import com.independentsoft.exchange.FolderClass;
import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.FolderQueryTraversal;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.FindFolderResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(FolderPropertyPath.FOLDER_CLASS, FolderClass.CONTACT_FOLDER);

            FindFolderResponse findFolderResponse = service.findFolder(StandardFolder.MAILBOX_ROOT, restriction, FolderQueryTraversal.DEEP);

            for (int i = 0; i < findFolderResponse.getFolders().size(); i++)
            {
                System.out.println(findFolderResponse.getFolders().get(i).getDisplayName());
                System.out.println(findFolderResponse.getFolders().get(i).getFolderId());
                System.out.println("----------------------------------------------------------");
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

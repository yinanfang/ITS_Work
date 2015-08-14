import com.independentsoft.exchange.Folder;
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

            Folder folder1 = new Folder("Test1");
            Folder folder2 = new Folder("Test2");

            //Create a folder in the mailbox root
            FolderId folder1Id = service.createFolder(folder1, StandardFolder.MAILBOX_ROOT);

            //Create a subfolder of the Test1
            FolderId folder2Id = service.createFolder(folder2, folder1Id);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

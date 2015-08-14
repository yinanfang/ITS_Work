import com.independentsoft.exchange.DeleteType;
import com.independentsoft.exchange.Folder;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.Response;
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

            FolderId folder1Id = service.createFolder(folder1, StandardFolder.DRAFTS);
            FolderId folder2Id = service.createFolder(folder2, StandardFolder.DRAFTS);

            Response response1 = service.deleteFolder(folder1Id); //hard delete
            Response response2 = service.deleteFolder(folder2Id, DeleteType.MOVE_TO_DELETED_ITEMS);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

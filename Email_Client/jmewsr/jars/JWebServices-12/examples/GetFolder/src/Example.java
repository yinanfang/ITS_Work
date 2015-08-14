import java.util.ArrayList;
import java.util.List;

import com.independentsoft.exchange.Folder;
import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.PropertyPath;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.ShapeType;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            //Get all folder properties
            Folder inboxFolder1 = service.getFolder(StandardFolder.INBOX);

            //Get only FolderId property of the Inbox folder
            Folder inboxFolder2 = service.getFolder(StandardFolder.INBOX, ShapeType.ID);

            List<PropertyPath> propertyPaths = new ArrayList<PropertyPath>();
            propertyPaths.add(FolderPropertyPath.DISPLAY_NAME);
            propertyPaths.add(FolderPropertyPath.FOLDER_CLASS);
            propertyPaths.add(FolderPropertyPath.COMMENT);

            //Get specified properties
            Folder inboxFolder3 = service.getFolder(StandardFolder.INBOX, propertyPaths);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

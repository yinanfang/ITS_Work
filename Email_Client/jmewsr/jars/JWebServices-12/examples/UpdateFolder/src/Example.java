import com.independentsoft.exchange.Folder;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.MapiPropertyType;
import com.independentsoft.exchange.Property;
import com.independentsoft.exchange.PropertyName;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.ShapeType;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.StandardPropertySet;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            PropertyName myCustomPropertyName = new PropertyName("myproperty", StandardPropertySet.PUBLIC_STRINGS, MapiPropertyType.STRING);

            Property myCustomProperty = new Property(myCustomPropertyName, "value1");
            Property commentProperty = new Property(FolderPropertyPath.COMMENT, "Folder description text.");

            //Get only FolderId property of the Inbox folder
            Folder inboxFolder = service.getFolder(StandardFolder.INBOX, ShapeType.ID);

            FolderId newFolderId1 = service.updateFolder(inboxFolder.getFolderId(), myCustomProperty);
            FolderId newFolderId2 = service.updateFolder(inboxFolder.getFolderId(), commentProperty);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

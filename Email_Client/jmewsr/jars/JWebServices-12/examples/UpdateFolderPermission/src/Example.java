import com.independentsoft.exchange.Folder;
import com.independentsoft.exchange.FolderChange;
import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.Permission;
import com.independentsoft.exchange.PermissionLevel;
import com.independentsoft.exchange.PermissionSet;
import com.independentsoft.exchange.Property;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.UserId;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            UserId user = new UserId("John@mydomain.com");

            Permission permission1 = new Permission();
            permission1.setUserId(user);

            permission1.setLevel(PermissionLevel.CUSTOM);
            permission1.setCanCreateItems(true);
            permission1.setCanCreateSubFolders(true);

            PermissionSet permissionSet = new PermissionSet();
            permissionSet.getPermissions().add(permission1);

            Property permissionSetProperty = new Property(FolderPropertyPath.PERMISSION_SET, permissionSet);

            Folder contactsFolder = service.getFolder(StandardFolder.CONTACTS);

            FolderChange change = new FolderChange(contactsFolder.getFolderId(), permissionSetProperty);

            service.updateFolder(change);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

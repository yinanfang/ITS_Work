import java.util.Enumeration;
import java.util.Hashtable;

import com.independentsoft.exchange.FindFolderResponse;
import com.independentsoft.exchange.Folder;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    private static Hashtable<String, Folder> folderTable = new Hashtable<String, Folder>();
    private static Service service = null;

    public static void main(String[] args)
    {
        try
        {
            service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Folder publicFolderRoot = service.getFolder(StandardFolder.PUBLIC_FOLDERS_ROOT);

            searchPublicFolder(publicFolderRoot.getFolderId());

            Enumeration<String> keys = folderTable.keys();

            while (keys.hasMoreElements())
            {
                String folderId = keys.nextElement();

                Folder folder = folderTable.get(folderId);

                String folderPath = getFolderPath(folder);

                System.out.println(folder.getDisplayName());
                System.out.println(folder.getFolderClass());
                System.out.println(folder.getCreationTime());
                System.out.println(folderPath);
                System.out.println("--------------------------------------------------------------------");
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }

    private static void searchPublicFolder(FolderId folderId) throws ServiceException
    {
        FindFolderResponse findFolderResponse = service.findFolder(folderId, FolderPropertyPath.getAllPropertyPaths());

        for (int i = 0; i < findFolderResponse.getFolders().size(); i++)
        {
            Folder folder = findFolderResponse.getFolders().get(i);

            folderTable.put(folder.getFolderId().getId(), folder);

            if (folder.hasSubFolders())
            {
                searchPublicFolder(folder.getFolderId());
            }
        }
    }

    private static String getFolderPath(Folder folder)
    {
        String path = "";

        path = path + "/" + folder.getDisplayName();

        String parentId = folder.getParentId().getId();

        if (folderTable.get(parentId) != null)
        {
            Folder parentFolder = folderTable.get(parentId);

            return getFolderPath(parentFolder) + path;
        }

        return path;
    }
}

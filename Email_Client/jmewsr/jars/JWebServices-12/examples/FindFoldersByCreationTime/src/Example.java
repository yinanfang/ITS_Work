import java.util.Calendar;
import java.util.Date;

import com.independentsoft.exchange.FolderPropertyPath;
import com.independentsoft.exchange.IsGreaterThanOrEqualTo;
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

            Calendar localCalendar = Calendar.getInstance();
            localCalendar.add(Calendar.HOUR, -1);

            Date time = localCalendar.getTime();

            IsGreaterThanOrEqualTo restriction = new IsGreaterThanOrEqualTo(FolderPropertyPath.CREATION_TIME, time);

            FindFolderResponse findFolderResponse = service.findFolder(StandardFolder.MAILBOX_ROOT, FolderPropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < findFolderResponse.getFolders().size(); i++)
            {
                System.out.println(findFolderResponse.getFolders().get(i).getDisplayName());
                System.out.println(findFolderResponse.getFolders().get(i).getFolderClass());
                System.out.println(findFolderResponse.getFolders().get(i).getCreationTime());
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

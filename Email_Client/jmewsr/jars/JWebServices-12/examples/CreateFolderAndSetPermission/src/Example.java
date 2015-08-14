import com.independentsoft.exchange.CalendarFolder;
import com.independentsoft.exchange.CalendarPermission;
import com.independentsoft.exchange.CalendarPermissionLevel;
import com.independentsoft.exchange.CalendarPermissionSet;
import com.independentsoft.exchange.FolderId;
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

            CalendarPermission permission1 = new CalendarPermission();
            permission1.setUserId(new UserId("John@mydomain.com"));
            permission1.setLevel(CalendarPermissionLevel.AUTHOR);

            CalendarPermissionSet permissionSet = new CalendarPermissionSet();
            permissionSet.getPermissions().add(permission1);

            CalendarFolder calFolder = new CalendarFolder("TestCalendar");
            calFolder.setCalendarPermissionSet(permissionSet);

            FolderId folder1Id = service.createFolder(calFolder, StandardFolder.CALENDAR);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

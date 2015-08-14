import java.util.List;

import com.independentsoft.exchange.DelegateResponse;
import com.independentsoft.exchange.DelegateUser;
import com.independentsoft.exchange.DelegateUserResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Mailbox mailbox = new Mailbox("username@mydomain.com");

            DelegateResponse response = service.getDelegate(mailbox, true);

            List<DelegateUserResponse> delegateUserResponse = response.getDelegateUserResponses();

            for (int i = 0; i < delegateUserResponse.size(); i++)
            {
                DelegateUser user = delegateUserResponse.get(i).getDelegateUser();

                System.out.println("User = " + user.getUserId());
                System.out.println("CalendarFolderPermissionLevel = " + user.getCalendarFolderPermissionLevel());
                System.out.println("ContactsFolderPermissionLevel = " + user.getContactsFolderPermissionLevel());
                System.out.println("InboxFolderPermissionLevel = " + user.getInboxFolderPermissionLevel());
                System.out.println("JournalFolderPermissionLevel = " + user.getJournalFolderPermissionLevel());
                System.out.println("NotesFolderPermissionLevel = " + user.getNotesFolderPermissionLevel());
                System.out.println("ReceiveCopiesOfMeetingMessages = " + user.getReceiveCopiesOfMeetingMessages());
                System.out.println("TasksFolderPermissionLevel = " + user.getTasksFolderPermissionLevel());
                System.out.println("ViewPrivateItems = " + user.getViewPrivateItems());
                System.out.println("------------------------------------------------------------------------");
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

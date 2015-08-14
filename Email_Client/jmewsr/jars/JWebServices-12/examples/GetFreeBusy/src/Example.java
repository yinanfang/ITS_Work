import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.independentsoft.exchange.AvailabilityResponse;
import com.independentsoft.exchange.FreeBusyView;
import com.independentsoft.exchange.FreeBusyViewOptions;
import com.independentsoft.exchange.FreeBusyViewType;
import com.independentsoft.exchange.MailboxData;
import com.independentsoft.exchange.SerializableTimeZone;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardTimeZone;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2011-05-20 00:00:00");
            Date endTime = dateFormat.parse("2011-05-30 00:00:00");

            MailboxData mailbox = new MailboxData("John@mydomain.com");
            SerializableTimeZone timeZone = new SerializableTimeZone(StandardTimeZone.BERLIN);
            FreeBusyViewOptions freeBusyOptions = new FreeBusyViewOptions(startTime, endTime, FreeBusyViewType.DETAILED_MERGED);

            AvailabilityResponse response = service.getAvailability(mailbox, timeZone, freeBusyOptions);

            for (int i = 0; i < response.getFreeBusyResponses().size(); i++)
            {
                FreeBusyView freeBusyView = response.getFreeBusyResponses().get(i).getFreeBusyView();

                for (int j = 0; j < freeBusyView.getCalendarEvents().size(); j++)
                {
                    System.out.println("Subject = " + freeBusyView.getCalendarEvents().get(j).getSubject());
                    System.out.println("Location = " + freeBusyView.getCalendarEvents().get(j).getLocation());
                    System.out.println("StartTime = " + freeBusyView.getCalendarEvents().get(j).getStartTime());
                    System.out.println("EndTime = " + freeBusyView.getCalendarEvents().get(j).getEndTime());
                    System.out.println("-----------------------------------------------------");
                }
            }

        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}

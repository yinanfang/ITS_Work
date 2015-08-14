import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.Attendee;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {

        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2011-05-25 16:00:00");
            Date endTime = dateFormat.parse("2011-05-25 18:00:00");

            Appointment appointment = new Appointment();
            appointment.setSubject("Test");
            appointment.setBody(new Body("Body text."));
            appointment.setStartTime(startTime);
            appointment.setEndTime(endTime);
            appointment.setLocation("Room 123");
            appointment.setReminderIsSet(true);
            appointment.setReminderMinutesBeforeStart(30);
            appointment.getRequiredAttendees().add(new Attendee("John@mydomain.com"));
            appointment.getOptionalAttendees().add(new Attendee("Mark@mydomain.com"));

            ItemId itemId = service.sendMeetingRequest(appointment);
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

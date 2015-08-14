import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.DailyRecurrencePattern;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.NumberedRecurrenceRange;
import com.independentsoft.exchange.Recurrence;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardTimeZone;
import com.independentsoft.exchange.TimeZone;

public class Example {

    public static void main(String[] args)
    {

        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2011-02-20 16:00:00");
            Date endTime = dateFormat.parse("2011-02-20 18:00:00");

            //Daily recurrence next 10 days
            DailyRecurrencePattern pattern = new DailyRecurrencePattern(1);
            NumberedRecurrenceRange range = new NumberedRecurrenceRange(startTime, 10);

            Recurrence recurrence = new Recurrence();
            recurrence.setPattern(pattern);
            recurrence.setRange(range);

            Appointment appointment = new Appointment();
            appointment.setSubject("Daily recurrence next 10 days");
            appointment.setBody(new Body("Body text"));
            appointment.setStartTime(startTime);
            appointment.setEndTime(endTime);
            appointment.setRecurrence(recurrence);
            appointment.setMeetingTimeZone(new TimeZone(StandardTimeZone.BERLIN));

            ItemId itemId = service.createItem(appointment);
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

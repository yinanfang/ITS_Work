import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.DayOfWeekIndex;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Month;
import com.independentsoft.exchange.NoEndRecurrenceRange;
import com.independentsoft.exchange.Recurrence;
import com.independentsoft.exchange.DayOfWeek;
import com.independentsoft.exchange.RelativeYearlyRecurrencePattern;
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
            Date startTime = dateFormat.parse("2011-09-01 16:00:00");
            Date endTime = dateFormat.parse("2011-09-01 18:00:00");

            //Yearly recurrence. The first Monday in September.
            List<DayOfWeek> days = new ArrayList<DayOfWeek>();
            days.add(DayOfWeek.MONDAY);

            RelativeYearlyRecurrencePattern pattern = new RelativeYearlyRecurrencePattern(Month.SEPTEMBER, days, DayOfWeekIndex.FIRST);
            NoEndRecurrenceRange range = new NoEndRecurrenceRange(startTime);

            Recurrence recurrence = new Recurrence();
            recurrence.setPattern(pattern);
            recurrence.setRange(range);

            Appointment appointment = new Appointment();
            appointment.setSubject("The first monday in september");
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

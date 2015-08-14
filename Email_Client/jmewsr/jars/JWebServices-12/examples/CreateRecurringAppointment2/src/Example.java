import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.DayOfWeek;
import com.independentsoft.exchange.EndDateRecurrenceRange;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Recurrence;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardTimeZone;
import com.independentsoft.exchange.TimeZone;
import com.independentsoft.exchange.WeeklyRecurrencePattern;

public class Example {

    public static void main(String[] args)
    {

        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date patternStartTime = dateFormat.parse("2011-03-01 00:00:00");
            Date patternEndTime = dateFormat.parse("2011-06-01 00:00:00");

            Date startTime = dateFormat.parse("2011-03-01 10:00:00");
            Date endTime = dateFormat.parse("2011-03-01 11:00:00");

            //Weekly recurrence. Monday and Tuesday every second week next 3 months.
            List<DayOfWeek> days = new ArrayList<DayOfWeek>();
            days.add(DayOfWeek.MONDAY);
            days.add(DayOfWeek.TUESDAY);

            WeeklyRecurrencePattern pattern = new WeeklyRecurrencePattern(2, days);
            EndDateRecurrenceRange range = new EndDateRecurrenceRange(patternStartTime, patternEndTime);

            Recurrence recurrence = new Recurrence();
            recurrence.setPattern(pattern);
            recurrence.setRange(range);

            Appointment appointment = new Appointment();
            appointment.setSubject("Every second week, monday and tuesday");
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

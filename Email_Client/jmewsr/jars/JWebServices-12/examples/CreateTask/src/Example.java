import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.independentsoft.exchange.Task;
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
            Date startTime = dateFormat.parse("2011-04-25 10:00:00");
            Date endTime = dateFormat.parse("2011-04-29 10:00:00");

            Task task = new Task();
            task.setSubject("Test");
            task.setBody(new Body("Body text."));
            task.setOwner("My Name");
            task.setStartDate(startTime);
            task.setDueDate(endTime);
            task.setReminderIsSet(true);
            task.setReminderDueBy(startTime);

            ItemId itemId = service.createItem(task);
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

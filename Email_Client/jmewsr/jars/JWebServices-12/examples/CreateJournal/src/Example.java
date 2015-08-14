import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.independentsoft.exchange.Journal;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2011-05-25 16:00:00");
            Date endTime = dateFormat.parse("2011-05-25 16:15:00");

            Journal journal = new Journal();
            journal.setSubject("Phone Call");
            journal.setBody(new Body("Body text"));
            journal.setType("Phone call");
            journal.setTypeDescription("Phone call");
            journal.getCompanies().add("Independentsoft");
            journal.setStartTime(startTime);
            journal.setEndTime(endTime);

            ItemId itemId = service.createItem(journal, StandardFolder.JOURNAL);
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

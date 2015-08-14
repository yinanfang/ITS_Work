import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.AppointmentPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Property;
import com.independentsoft.exchange.SendMeetingOption;
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

            Date newStartTime = dateFormat.parse("2011-05-25 17:00:00");
            Date newEndTime = dateFormat.parse("2011-05-25 18:00:00");

            Property startTimeProperty = new Property(AppointmentPropertyPath.START_TIME, newStartTime);
            Property endTimeProperty = new Property(AppointmentPropertyPath.END_TIME, newEndTime);

            List<Property> properties = new ArrayList<Property>();
            properties.add(startTimeProperty);
            properties.add(endTimeProperty);

            IsEqualTo restriction = new IsEqualTo(AppointmentPropertyPath.SUBJECT, "Meeting");

            FindItemResponse findItemResponse = service.findItem(StandardFolder.CALENDAR, restriction);

            if (findItemResponse.getItems().size() == 1)
            {
                ItemId itemId = findItemResponse.getItems().get(0).getItemId();

                itemId = service.updateItem(itemId, properties, SendMeetingOption.SEND_TO_ALL_AND_SAVE_COPY);
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

import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.AppointmentPropertyPath;
import com.independentsoft.exchange.Attendee;
import com.independentsoft.exchange.ContainmentComparison;
import com.independentsoft.exchange.ContainmentMode;
import com.independentsoft.exchange.Contains;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.ItemChange;
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

            Contains restriction = new Contains(AppointmentPropertyPath.SUBJECT, "test", ContainmentMode.EXACT_PHRASE, ContainmentComparison.IGNORE_CASE);

            FindItemResponse response = service.findItem(StandardFolder.CALENDAR, restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Appointment)
                {
                    Property requiredAttendees = new Property(AppointmentPropertyPath.REQUIRED_ATTENDEES, new Attendee("Mark@mydomain.com"));

                    ItemChange itemChange = new ItemChange();
                    itemChange.setItemId(response.getItems().get(i).getItemId());
                    itemChange.getPropertiesToAppend().add(requiredAttendees);

                    ItemId newItemId = service.updateItem(itemChange, SendMeetingOption.SEND_TO_ALL);
                }
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

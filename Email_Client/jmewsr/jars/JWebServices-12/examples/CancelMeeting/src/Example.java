import com.independentsoft.exchange.AppointmentPropertyPath;
import com.independentsoft.exchange.CancelItem;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(AppointmentPropertyPath.SUBJECT, "Meeting");

            FindItemResponse findItemResponse = service.findItem(StandardFolder.CALENDAR, restriction);

            if (findItemResponse.getItems().size() == 1)
            {
                ItemId itemId = findItemResponse.getItems().get(0).getItemId();

                CancelItem cancelItem = new CancelItem(itemId);

                ItemInfoResponse response = service.cancelMeetingRequest(cancelItem);
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

import com.independentsoft.exchange.AcceptItem;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Item;
import com.independentsoft.exchange.ItemClass;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.ItemPropertyPath;
import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.ShapeType;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            ItemShape itemShape = new ItemShape(ShapeType.ID);
            IsEqualTo restriction = new IsEqualTo(ItemPropertyPath.ITEM_CLASS, ItemClass.MEETING_REQUEST);

            FindItemResponse findItemResponse = service.findItem(StandardFolder.INBOX, itemShape, restriction);

            for (Item item : findItemResponse.getItems())
            {
                AcceptItem acceptItem = new AcceptItem(item.getItemId());

                ItemInfoResponse response = service.acceptMeetingRequest(acceptItem);
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

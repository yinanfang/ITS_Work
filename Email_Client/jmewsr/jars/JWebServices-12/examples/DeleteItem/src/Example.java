import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.DeleteType;
import com.independentsoft.exchange.IsLessThan;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Response;
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

            Calendar localCalendar = Calendar.getInstance();
            localCalendar.add(Calendar.MONTH, -1);

            Date time = localCalendar.getTime();

            IsLessThan restriction = new IsLessThan(MessagePropertyPath.CREATED_TIME, time);

            FindItemResponse draftsItems = service.findItem(StandardFolder.DRAFTS, restriction);

            for (int i = 0; i < draftsItems.getItems().size(); i++)
            {
                Response response = service.deleteItem(draftsItems.getItems().get(i).getItemId(), DeleteType.MOVE_TO_DELETED_ITEMS);
            }

            //Empty DeletedItems folder

            ItemShape itemShape = new ItemShape(ShapeType.ID);
            FindItemResponse deletedItems = service.findItem(StandardFolder.DELETED_ITEMS, itemShape);

            List<ItemId> itemIds = new ArrayList<ItemId>();

            for (int i = 0; i < deletedItems.getItems().size(); i++)
            {
                itemIds.add(deletedItems.getItems().get(i).getItemId());
            }

            List<Response> responses = service.deleteItem(itemIds, DeleteType.HARD_DELETE);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

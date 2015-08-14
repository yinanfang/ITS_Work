import java.io.IOException;
import java.text.ParseException;

import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.FindItemResponse;
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

            FindItemResponse response = service.findItem(StandardFolder.DRAFTS, itemShape);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                com.independentsoft.msg.Message msgFile = service.getMessageFile(response.getItems().get(i).getItemId());
                msgFile.save("c:\\test\\message" + i + ".msg", true);
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}

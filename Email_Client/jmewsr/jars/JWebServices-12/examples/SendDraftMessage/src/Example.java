import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.Property;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Message message = new Message();
            message.setSubject("Test");
            message.setBody(new Body("Body text."));

            //create message in the Drafts folder
            ItemId itemId1 = service.createItem(message);

            Mailbox john = new Mailbox("John@mydomain.com", "John Smith");
            Property toProperty = new Property(MessagePropertyPath.TO_RECIPIENTS, john);

            //Update message.
            ItemId itemId2 = service.updateItem(itemId1, toProperty);

            //Send message
            ItemInfoResponse response = service.send(itemId2);
            ;
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

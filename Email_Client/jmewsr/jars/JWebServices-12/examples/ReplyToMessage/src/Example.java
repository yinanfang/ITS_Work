import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.ReplyItem;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(MessagePropertyPath.SUBJECT, "Test");

            FindItemResponse findItemResponse = service.findItem(StandardFolder.INBOX, restriction);

            for (int i = 0; i < findItemResponse.getItems().size(); i++)
            {
                ItemId currentMessageId = findItemResponse.getItems().get(i).getItemId();

                Body replyBody = new Body("This is reply message body text.");

                ReplyItem replyItem = new ReplyItem(currentMessageId);
                replyItem.setNewBody(replyBody);

                ItemInfoResponse response = service.reply(replyItem);
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

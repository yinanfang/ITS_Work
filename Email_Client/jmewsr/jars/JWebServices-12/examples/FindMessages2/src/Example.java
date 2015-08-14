import com.independentsoft.exchange.ContainmentComparison;
import com.independentsoft.exchange.ContainmentMode;
import com.independentsoft.exchange.Contains;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Contains restriction = new Contains(MessagePropertyPath.SUBJECT, "test", ContainmentMode.PREFIXED, ContainmentComparison.IGNORE_CASE);

            FindItemResponse response = service.findItem(StandardFolder.INBOX, MessagePropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Message)
                {
                    Message message = (Message) response.getItems().get(i);

                    System.out.println("Subject = " + message.getSubject());
                    System.out.println("ReceivedTime = " + message.getReceivedTime());

                    if (message.getFrom() != null)
                    {
                        System.out.println("From = " + message.getFrom().getName());
                    }

                    System.out.println("Body Preview = " + message.getBodyPlainText());
                    System.out.println("----------------------------------------------------------------");
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

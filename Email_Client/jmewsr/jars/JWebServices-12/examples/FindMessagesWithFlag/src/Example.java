import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.MessagePropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Or;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction1 = new IsEqualTo(MessagePropertyPath.FLAG_STATUS, "1"); //FlagStatus.Complete
            IsEqualTo restriction2 = new IsEqualTo(MessagePropertyPath.FLAG_STATUS, "2"); //FlagStatus.Marked

            Or restriction3 = new Or(restriction1, restriction2);

            FindItemResponse response = service.findItem(StandardFolder.INBOX, MessagePropertyPath.getAllPropertyPaths(), restriction3);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Message)
                {
                    Message message = (Message) response.getItems().get(i);

                    System.out.println("Subject = " + message.getSubject());
                    System.out.println("FlagStatus = " + message.getFlagStatus());
                    System.out.println("FlagIcon = " + message.getFlagIcon());
                    System.out.println("FlagCompleteTime = " + message.getFlagCompleteTime());
                    System.out.println("FlagRequest = " + message.getFlagRequest());
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

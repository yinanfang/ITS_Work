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

            IsEqualTo restriction1 = new IsEqualTo(MessagePropertyPath.LAST_VERB_EXECUTED, "102"); //LastVerbExecuted.REPLY_TO_SENDER
            IsEqualTo restriction2 = new IsEqualTo(MessagePropertyPath.LAST_VERB_EXECUTED, "103"); //LastVerbExecuted.REPLY_TO_ALL
            IsEqualTo restriction3 = new IsEqualTo(MessagePropertyPath.LAST_VERB_EXECUTED, "104"); //LastVerbExecuted.FORWARD

            Or restriction4 = new Or(restriction1, restriction2, restriction3);

            FindItemResponse response = service.findItem(StandardFolder.INBOX, MessagePropertyPath.getAllPropertyPaths(), restriction4);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Message)
                {
                    Message message = (Message) response.getItems().get(i);

                    System.out.println("Subject = " + message.getSubject());
                    System.out.println("LastModifiedTime = " + message.getLastModifiedTime());
                    System.out.println("LastVerbExecuted = " + message.getLastVerbExecuted());
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

import com.independentsoft.exchange.UnifiedMessagingService;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            UnifiedMessagingService unifiedMessagingService = new UnifiedMessagingService("https://myserver/UnifiedMessaging/service.asmx", "username", "password");

            String response = unifiedMessagingService.playOnPhone("AAAAAGsd2rbQLVtLobUGbrq/9IUHAEX2ikn/L8JJtI5WHI0FAW8AAAFXHhsAACxVpEl+KVVLl957wp//x6UAGAetcDUAAA==", "12345");

            System.out.println(response);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

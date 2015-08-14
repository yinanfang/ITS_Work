import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.MimeContent;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            BufferedReader in = new BufferedReader(new FileReader("c:\\test\\message1.eml"));
            StringBuffer stringBuffer = new StringBuffer();

            String line = null;

            while ((line = in.readLine()) != null)
            {
                stringBuffer.append(line);
                stringBuffer.append("\r\n");
            }

            in.close();

            String mimeContentText = stringBuffer.toString();

            MimeContent mimeContent = new MimeContent(mimeContentText);
            Message message = new Message(mimeContent);

            ItemId itemId = service.createItem(message, StandardFolder.DRAFTS);
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
    }
}

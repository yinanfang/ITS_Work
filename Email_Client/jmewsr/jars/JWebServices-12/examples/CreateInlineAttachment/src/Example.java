import java.io.IOException;

import com.independentsoft.exchange.AttachmentId;
import com.independentsoft.exchange.BodyType;
import com.independentsoft.exchange.FileAttachment;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.Body;
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
            message.setHideAttachments(true);
            message.setBody(new Body("<html><body>Here is a message with an inline attachment:<br><img widht=\"640\" height=\"480\" id=\"Picture1\" src=\"cid:picture.jpg@123456\"></img></body></html>", BodyType.HTML));

            ItemId itemId = service.createItem(message);

            FileAttachment fileAttachment = new FileAttachment("c:\\test\\picture.jpg");
            fileAttachment.setContentId("picture.jpg@123456");

            AttachmentId attachmentId = service.createAttachment(fileAttachment, itemId);
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

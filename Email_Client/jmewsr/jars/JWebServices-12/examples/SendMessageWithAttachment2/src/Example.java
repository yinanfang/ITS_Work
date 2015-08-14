import com.independentsoft.exchange.AttachmentId;
import com.independentsoft.exchange.ItemAttachment;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemInfoResponse;
import com.independentsoft.exchange.Mailbox;
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
            message.setBody(new Body("Body text."));
            message.getToRecipients().add(new Mailbox("John@mydomain.com"));

            ItemId messageId = service.createItem(message);

            //Attach message
            Message attachedMessage = new Message();
            attachedMessage.setSubject("MyAttachment");
            attachedMessage.setBody(new Body("Attached message body text."));

            ItemAttachment attachment = new ItemAttachment("MyAttachment", attachedMessage);
            AttachmentId attachmentId = service.createAttachment(attachment, messageId);

            //Update messageId
            messageId.setChangeKey(attachmentId.getRootItemChangeKey());

            //Send message
            ItemInfoResponse response = service.send(messageId);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

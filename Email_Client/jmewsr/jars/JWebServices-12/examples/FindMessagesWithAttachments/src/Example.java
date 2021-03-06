import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.independentsoft.exchange.Attachment;
import com.independentsoft.exchange.AttachmentInfo;
import com.independentsoft.exchange.FileAttachment;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Item;
import com.independentsoft.exchange.ItemAttachment;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.MessagePropertyPath;
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

            IsEqualTo restriction = new IsEqualTo(MessagePropertyPath.HAS_ATTACHMENTS, true);

            ItemShape itemShape = new ItemShape(ShapeType.ID);

            FindItemResponse response = service.findItem(StandardFolder.INBOX, itemShape, restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Message)
                {
                    ItemId itemId = response.getItems().get(i).getItemId();

                    Message message = service.getMessage(itemId);

                    List<AttachmentInfo> attachmentsInfo = message.getAttachments();

                    for (int j = 0; j < attachmentsInfo.size(); j++)
                    {
                        Attachment attachment = service.getAttachment(attachmentsInfo.get(j).getId());

                        if (attachment instanceof FileAttachment)
                        {
                            FileAttachment fileAttachment = (FileAttachment) attachment;

                            byte[] buffer = fileAttachment.getContent();
                            String fileName = fileAttachment.getName();

                            FileOutputStream fileStream = new FileOutputStream("c:\\test\\" + fileName);
                            fileStream.write(buffer);
                            fileStream.close();

                        }
                        else if (attachment instanceof ItemAttachment)
                        {
                            ItemAttachment itemAttachment = (ItemAttachment) attachment;

                            String name = itemAttachment.getName();
                            Item attachedItem = itemAttachment.getItem();
                        }
                    }
                }
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
    }
}

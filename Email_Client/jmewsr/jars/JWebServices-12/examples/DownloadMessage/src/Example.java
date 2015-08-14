import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ItemShape;
import com.independentsoft.exchange.Message;
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

            ItemShape itemShape = new ItemShape(ShapeType.ID);

            FindItemResponse response = service.findItem(StandardFolder.INBOX, itemShape);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                ItemId itemId = response.getItems().get(i).getItemId();

                Message message = service.getMessage(itemId);

                String mimeContent = message.getMimeContent().getText();

                Charset charset = Charset.forName("UTF-8");

                ByteBuffer byteBuffer = charset.encode(mimeContent);

                byte[] buffer = new byte[byteBuffer.limit()];

                System.arraycopy(byteBuffer.array(), 0, buffer, 0, buffer.length);

                String fileName = "c:\\test\\message" + i + ".eml";

                FileOutputStream fileStream = new FileOutputStream(fileName);
                fileStream.write(buffer);
                fileStream.close();
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

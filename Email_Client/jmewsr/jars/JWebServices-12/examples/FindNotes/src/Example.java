import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemClass;
import com.independentsoft.exchange.Note;
import com.independentsoft.exchange.NotePropertyPath;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(NotePropertyPath.ITEM_CLASS, ItemClass.NOTE);

            FindItemResponse response = service.findItem(StandardFolder.NOTES, NotePropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                Note note = (Note) response.getItems().get(i);

                System.out.println("Subject = " + note.getSubject());
                System.out.println("Width = " + note.getWidth());
                System.out.println("Height = " + note.getHeight());
                System.out.println("Left = " + note.getLeft());
                System.out.println("Top = " + note.getTop());
                System.out.println("Color = " + note.getColor());
                System.out.println("IconColor = " + note.getIconColor());
                System.out.println("Body Preview = " + note.getBodyPlainText());
                System.out.println("----------------------------------------------------------------");
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

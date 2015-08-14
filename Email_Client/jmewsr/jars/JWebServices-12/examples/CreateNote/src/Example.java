import com.independentsoft.exchange.Note;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.NoteColor;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Note note = new Note();
            note.setSubject("My test note");
            note.setBody(new Body("My test note"));
            note.setColor(NoteColor.GREEN);
            note.setIconColor(NoteColor.GREEN);
            note.setHeight(200);
            note.setWidth(300);
            note.setLeft(400);
            note.setTop(200);

            ItemId itemId = service.createItem(note, StandardFolder.NOTES);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

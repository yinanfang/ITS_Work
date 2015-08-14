import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemClass;
import com.independentsoft.exchange.Journal;
import com.independentsoft.exchange.JournalPropertyPath;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(JournalPropertyPath.ITEM_CLASS, ItemClass.JOURNAL);

            FindItemResponse response = service.findItem(StandardFolder.JOURNAL, JournalPropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                Journal journal = (Journal) response.getItems().get(i);

                System.out.println("Subject = " + journal.getSubject());
                System.out.println("Type = " + journal.getType());
                System.out.println("TypeDescription = " + journal.getTypeDescription());
                System.out.println("StartTime = " + journal.getStartTime());
                System.out.println("EndTime = " + journal.getEndTime());
                System.out.println("Body Preview = " + journal.getBodyPlainText());
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

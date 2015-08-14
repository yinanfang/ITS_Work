import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.AppointmentPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.StandardFolderId;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Mailbox johnMailbox = new Mailbox("John@mydomain.com");

            StandardFolderId johnCalendarFolder = new StandardFolderId(StandardFolder.CALENDAR, johnMailbox);

            FindItemResponse response = service.findItem(johnCalendarFolder, AppointmentPropertyPath.getAllPropertyPaths());

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Appointment)
                {
                    Appointment appointment = (Appointment) response.getItems().get(i);

                    System.out.println("Subject = " + appointment.getSubject());
                    System.out.println("StartTime = " + appointment.getStartTime());
                    System.out.println("EndTime = " + appointment.getEndTime());
                    System.out.println("Body Preview = " + appointment.getBodyPlainText());
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

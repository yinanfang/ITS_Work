import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.And;
import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.AppointmentPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Property;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {

        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date oldStartTime = dateFormat.parse("2011-04-20 15:00:00");
            Date oldEndTime = dateFormat.parse("2011-04-20 16:00:00");

            Date newStartTime = dateFormat.parse("2011-04-20 17:00:00");
            Date newEndTime = dateFormat.parse("2011-04-20 18:00:00");

            IsEqualTo restriction1 = new IsEqualTo(AppointmentPropertyPath.START_TIME, oldStartTime);
            IsEqualTo restriction2 = new IsEqualTo(AppointmentPropertyPath.END_TIME, oldEndTime);
            And restriction3 = new And(restriction1, restriction2);

            FindItemResponse response = service.findItem(StandardFolder.CALENDAR, AppointmentPropertyPath.getAllPropertyPaths(), restriction3);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Appointment)
                {
                    ItemId itemId = response.getItems().get(i).getItemId();

                    Property startTimeProperty = new Property(AppointmentPropertyPath.START_TIME, newStartTime);
                    Property endTimeProperty = new Property(AppointmentPropertyPath.END_TIME, newEndTime);

                    List<Property> properties = new ArrayList<Property>();
                    properties.add(startTimeProperty);
                    properties.add(endTimeProperty);

                    itemId = service.updateItem(itemId, properties);
                }
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}

import java.util.List;

import com.independentsoft.exchange.GetServerTimeZonesResponse;
import com.independentsoft.exchange.Period;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.TimeZoneDefinition;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            GetServerTimeZonesResponse response = service.getServerTimeZones();
            List<TimeZoneDefinition> timeZoneDefinitions = response.getTimeZoneDefinitions();

            for (TimeZoneDefinition timeZoneDefinition : timeZoneDefinitions)
            {
                System.out.println("Time Zone Id=" + timeZoneDefinition.getId());
                System.out.println("Time Zone Name=" + timeZoneDefinition.getName());

                for (Period period : timeZoneDefinition.getPeriods())
                {
                    System.out.println("Period Id=" + period.getId());
                    System.out.println("Period Name=" + period.getName());
                    System.out.println("Period Duration=" + period.getBias());
                }

                System.out.println("-----------------------------------------------");
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

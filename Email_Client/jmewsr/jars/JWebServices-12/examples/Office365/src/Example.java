import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.AutodiscoverService;
import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.GetUserSettingsResponse;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.UserResponse;
import com.independentsoft.exchange.UserSettingName;
import com.independentsoft.exchange.Service;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            AutodiscoverService autodiscoverService = new AutodiscoverService("https://autodiscover-s.outlook.com/Autodiscover/Autodiscover.xml", "test@independentsoft.onmicrosoft.com", "password");

            List<UserSettingName> settingNames = new ArrayList<UserSettingName>();

            settingNames.add(UserSettingName.EXTERNAL_EWS_URL);

            GetUserSettingsResponse response = autodiscoverService.getUserSettings("test@independentsoft.onmicrosoft.com", settingNames);

            if (response.getUserResponses().size() > 0)
            {
                UserResponse userResponse = response.getUserResponses().get(0);

                if(userResponse.getUserSettings().size() == 1)
                {
                	String externalEwsUrl = userResponse.getUserSettings().get(0).getValue();
                	
                    System.out.println("ExternalEwsUrl = " + externalEwsUrl);
                    
                    Service service = new Service(externalEwsUrl, "test@independentsoft.onmicrosoft.com", "password");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startTime = dateFormat.parse("2011-02-25 16:00:00");
                    Date endTime = dateFormat.parse("2011-02-25 18:00:00");

                    Appointment appointment = new Appointment();
                    appointment.setSubject("Test");
                    appointment.setBody(new Body("Body text."));
                    appointment.setStartTime(startTime);
                    appointment.setEndTime(endTime);
                    appointment.setLocation("My Office");
                    appointment.setReminderIsSet(true);
                    appointment.setReminderMinutesBeforeStart(30);

                    ItemId itemId = service.createItem(appointment);
                }
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

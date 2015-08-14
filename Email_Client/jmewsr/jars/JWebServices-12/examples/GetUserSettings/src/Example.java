import java.util.ArrayList;
import java.util.List;

import com.independentsoft.exchange.AutodiscoverException;
import com.independentsoft.exchange.AutodiscoverService;
import com.independentsoft.exchange.GetUserSettingsResponse;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.UserResponse;
import com.independentsoft.exchange.UserSetting;
import com.independentsoft.exchange.UserSettingName;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            AutodiscoverService autodiscoverService = new AutodiscoverService("https://myserver/autodiscover/autodiscover.xml", "username", "password");

            List<UserSettingName> settingNames = new ArrayList<UserSettingName>();

            settingNames.add(UserSettingName.CAS_VERSION);
            settingNames.add(UserSettingName.EWS_SUPPORTED_SCHEMAS);
            settingNames.add(UserSettingName.EXTERNAL_EWS_URL);
            settingNames.add(UserSettingName.EXTERNAL_MAILBOX_SERVER);
            settingNames.add(UserSettingName.PUBLIC_FOLDER_SERVER);
            settingNames.add(UserSettingName.USER_DEPLOYMENT_ID);
            settingNames.add(UserSettingName.USER_DISPLAY_NAME);
            settingNames.add(UserSettingName.USER_DN);

            GetUserSettingsResponse response = autodiscoverService.getUserSettings("John@mydomain.com", settingNames);

            if (response.getUserResponses().size() > 0)
            {
                UserResponse userResponse = response.getUserResponses().get(0);

                for (UserSetting userSetting : userResponse.getUserSettings())
                {
                    System.out.println(userSetting.getName() + " = " + userSetting.getValue());
                }
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (AutodiscoverException e)
        {
            e.printStackTrace();
        }
    }
}

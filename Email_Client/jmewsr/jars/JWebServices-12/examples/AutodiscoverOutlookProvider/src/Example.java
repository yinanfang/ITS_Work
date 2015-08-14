import java.util.List;

import com.independentsoft.exchange.Account;
import com.independentsoft.exchange.AutodiscoverException;
import com.independentsoft.exchange.AutodiscoverService;
import com.independentsoft.exchange.ExchangeProtocol;
import com.independentsoft.exchange.OutlookProvider;
import com.independentsoft.exchange.OutlookWebAccessUrl;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.User;
import com.independentsoft.exchange.WebProtocol;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            AutodiscoverService autodiscoverService = new AutodiscoverService("https://myserver/autodiscover/autodiscover.xml", "username", "password");

            OutlookProvider outlookProvider = autodiscoverService.autodiscoverOutlookProvider("John@mydomain.com");

            if (outlookProvider.getUser() != null)
            {
                User user = outlookProvider.getUser();

                System.out.println("DisplayName = " + user.getDisplayName());
                System.out.println("DeploymentId = " + user.getDeploymentId());
                System.out.println("LegacyDN = " + user.getLegacyDN());
            }

            if (outlookProvider.getAccount() != null)
            {
                Account account = outlookProvider.getAccount();

                ExchangeProtocol exchangeProtocol = account.getExchangeProtocol();
                WebProtocol webProtocol = account.getWebProtocol();

                if (exchangeProtocol != null)
                {
                    System.out.println("Server = " + exchangeProtocol.getServer());
                    System.out.println("ServerDN = " + exchangeProtocol.getServerDN());
                    System.out.println("ServerVersion = " + exchangeProtocol.getServerVersion());
                    System.out.println("ActiveDirectory = " + exchangeProtocol.getActiveDirectory());
                    System.out.println("AuthenticationPackage = " + exchangeProtocol.getAuthenticationPackage());
                    System.out.println("MailboxDatabaseLegacyDN = " + exchangeProtocol.getMailboxDatabaseLegacyDN());
                    System.out.println("AvailabilityServiceUrl = " + exchangeProtocol.getAvailabilityServiceUrl());
                    System.out.println("ExchangeWebServiceUrl = " + exchangeProtocol.getExchangeWebServiceUrl());
                    System.out.println("OfflineAddressBookUrl = " + exchangeProtocol.getOfflineAddressBookUrl());
                    System.out.println("OutOfOfficeUrl = " + exchangeProtocol.getOutOfOfficeUrl());
                    System.out.println("UnifiedMessagingServiceUrl = " + exchangeProtocol.getUnifiedMessagingServiceUrl());
                }

                if (webProtocol != null)
                {
                    if (webProtocol.getInternalAccess() != null)
                    {
                        List<OutlookWebAccessUrl> owaUrls = webProtocol.getInternalAccess().getOutlookWebAccessUrls();

                        for (int i = 0; i < owaUrls.size(); i++)
                        {
                            System.out.println("OWA Url = " + owaUrls.get(i).getUrl());

                            for (int j = 0; j < owaUrls.get(i).getAuthenticationMethods().size(); j++)
                            {
                                System.out.println("OWA Authentication = " + owaUrls.get(i).getAuthenticationMethods().get(j));
                            }
                        }
                    }

                    if (webProtocol.getExternalAccess() != null)
                    {
                        List<OutlookWebAccessUrl> owaUrls = webProtocol.getExternalAccess().getOutlookWebAccessUrls();

                        for (int i = 0; i < owaUrls.size(); i++)
                        {
                            System.out.println("OWA Url = " + owaUrls.get(i).getUrl());

                            for (int j = 0; j < owaUrls.get(i).getAuthenticationMethods().size(); j++)
                            {
                                System.out.println("OWA Authentication = " + owaUrls.get(i).getAuthenticationMethods().get(j));
                            }
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
        catch (AutodiscoverException e)
        {
            e.printStackTrace();
        }
    }
}

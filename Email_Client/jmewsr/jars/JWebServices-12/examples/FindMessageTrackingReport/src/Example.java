import com.independentsoft.exchange.FindMessageTrackingReportResponse;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.MessageTrackingScope;
import com.independentsoft.exchange.MessageTrackingSearchResult;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Mailbox mailbox = new Mailbox("John@mydomain.com");

            FindMessageTrackingReportResponse response = service.findMessageTrackingReport(MessageTrackingScope.ORGANIZATION, "mydomain.com", mailbox);

            for (MessageTrackingSearchResult result : response.getMessageTrackingSearchResults())
            {
                System.out.println("Subject=" + result.getSubject());

                if (result.getSender() != null)
                {
                    System.out.println("Sender=" + result.getSender().getEmailAddress());
                }

                if (result.getSender() != null)
                {
                    System.out.println("PurportedSender=" + result.getPurportedSender().getEmailAddress());
                }

                System.out.println("PreviousHopServer=" + result.getPreviousHopServer());
                System.out.println("MessageTrackingReportId=" + result.getMessageTrackingReportId());
                System.out.println("SubmittedTime=" + result.getSubmittedTime());

                for (Mailbox recipient : result.getRecipients())
                {
                    System.out.println("Recipient=" + recipient.getEmailAddress());
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

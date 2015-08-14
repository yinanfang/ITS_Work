import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.independentsoft.exchange.AvailabilityResponse;
import com.independentsoft.exchange.MailboxData;
import com.independentsoft.exchange.SerializableTimeZone;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardTimeZone;
import com.independentsoft.exchange.Suggestion;
import com.independentsoft.exchange.SuggestionDay;
import com.independentsoft.exchange.SuggestionsViewOptions;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2011-05-20 00:00:00");
            Date endTime = dateFormat.parse("2011-05-22 00:00:00");

            List<MailboxData> mailboxes = new ArrayList<MailboxData>();
            mailboxes.add(new MailboxData("John@mydomain.com"));
            mailboxes.add(new MailboxData("Mark@mydomain.com"));
            mailboxes.add(new MailboxData("Peter@mydomain.com"));

            SerializableTimeZone timeZone = new SerializableTimeZone(StandardTimeZone.BERLIN);
            SuggestionsViewOptions suggestionsViewOptions = new SuggestionsViewOptions(startTime, endTime, 60);

            AvailabilityResponse response = service.getAvailability(mailboxes, timeZone, suggestionsViewOptions);

            if (response.getSuggestionsResponse() != null)
            {
                List<SuggestionDay> suggestionDays = response.getSuggestionsResponse().getSuggestionDays();

                for (int i = 0; i < suggestionDays.size(); i++)
                {
                    System.out.println("Suggestion Day = " + suggestionDays.get(i).getDate());

                    for (int j = 0; j < suggestionDays.get(i).getSuggestions().size(); j++)
                    {
                        Suggestion suggestion = suggestionDays.get(i).getSuggestions().get(j);

                        System.out.println("MeetingTime = " + suggestion.getMeetingTime());
                        System.out.println("Quality = " + suggestion.getQuality());
                    }

                    System.out.println("---------------------------------------------------------");
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

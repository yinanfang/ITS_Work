import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.independentsoft.exchange.OutOfOffice;
import com.independentsoft.exchange.OutOfOfficeState;
import com.independentsoft.exchange.ReplyBody;
import com.independentsoft.exchange.Response;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.TimeDuration;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2011-05-20 00:00:00");
            Date endTime = dateFormat.parse("2011-05-30 00:00:00");

            TimeDuration duration = new TimeDuration(startTime, endTime);
            OutOfOffice oof = new OutOfOffice();

            ReplyBody replyBody = new ReplyBody("I am out of office until " + endTime);

            oof.setState(OutOfOfficeState.SCHEDULED);
            oof.setDuration(duration);
            oof.setInternalReply(replyBody);
            oof.setExternalReply(replyBody);

            Response response = service.setOutOfOffice(oof, "John@mydomain.com");
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

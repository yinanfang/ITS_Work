import java.util.Calendar;
import java.util.Date;

import com.independentsoft.exchange.IsGreaterThanOrEqualTo;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.Post;
import com.independentsoft.exchange.PostPropertyPath;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            Calendar localCalendar = Calendar.getInstance();
            localCalendar.add(Calendar.MONTH, -1);

            Date time = localCalendar.getTime();

            IsGreaterThanOrEqualTo restriction = new IsGreaterThanOrEqualTo(PostPropertyPath.POSTED_TIME, time);

            FindItemResponse response = service.findItem(StandardFolder.DRAFTS, PostPropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Post)
                {
                    Post post = (Post) response.getItems().get(i);

                    System.out.println("Subject = " + post.getSubject());
                    System.out.println("PostedTime = " + post.getPostedTime());
                    System.out.println("Body Preview = " + post.getBodyPlainText());
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

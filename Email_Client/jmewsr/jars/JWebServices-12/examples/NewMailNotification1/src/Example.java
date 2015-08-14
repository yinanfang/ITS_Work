import com.independentsoft.exchange.EventType;
import com.independentsoft.exchange.GetEventsResponse;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Message;
import com.independentsoft.exchange.NewMailEvent;
import com.independentsoft.exchange.Notification;
import com.independentsoft.exchange.PullSubscription;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;
import com.independentsoft.exchange.SubscribeResponse;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            String newWatermark = null;

            PullSubscription subscription = new PullSubscription(StandardFolder.INBOX, EventType.NEW_MAIL);

            //initial subscribe
            SubscribeResponse subscribeResponse = service.subscribe(subscription);

            while (true)
            {
                //wait 60 seconds
                Thread.currentThread().join(60000);

                GetEventsResponse eventsResponse = service.getEvents(subscribeResponse);

                Notification notification = eventsResponse.getNotification();

                for (int i = 0; i < notification.getEvents().size(); i++)
                {
                    newWatermark = notification.getEvents().get(i).getWatermark();

                    if (notification.getEvents().get(i) instanceof NewMailEvent)
                    {
                        NewMailEvent newMailEvent = (NewMailEvent) notification.getEvents().get(i);

                        ItemId itemId = (ItemId) newMailEvent.getId();

                        Message message = service.getMessage(itemId);

                        System.out.println(message.getSubject());
                        System.out.println(message.getReceivedTime());
                    }
                }

                //resubscribe with new watermark 
                subscription.setWatermark(newWatermark);
                subscribeResponse = service.subscribe(subscription);
            }
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

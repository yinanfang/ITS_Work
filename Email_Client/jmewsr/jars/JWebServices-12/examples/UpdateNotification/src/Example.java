import com.independentsoft.exchange.CreatedEvent;
import com.independentsoft.exchange.EventType;
import com.independentsoft.exchange.FolderId;
import com.independentsoft.exchange.GetEventsResponse;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.ModifiedEvent;
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

            PullSubscription subscription = new PullSubscription(StandardFolder.CALENDAR);
            subscription.getEventTypes().add(EventType.CREATED);
            subscription.getEventTypes().add(EventType.MODIFIED);

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

                    if (notification.getEvents().get(i) instanceof CreatedEvent)
                    {
                        CreatedEvent createdEvent = (CreatedEvent) notification.getEvents().get(i);

                        if (createdEvent.getId() instanceof ItemId)
                        {
                            ItemId itemId = (ItemId) createdEvent.getId();
                            System.out.println("Created item = " + itemId);
                        }
                        else if (createdEvent.getId() instanceof FolderId)
                        {
                            FolderId folderId = (FolderId) createdEvent.getId();
                            System.out.println("Created folder = " + folderId);
                        }
                    }
                    else if (notification.getEvents().get(i) instanceof ModifiedEvent)
                    {
                        ModifiedEvent modifiedEvent = (ModifiedEvent) notification.getEvents().get(i);

                        if (modifiedEvent.getId() instanceof ItemId)
                        {
                            ItemId itemId = (ItemId) modifiedEvent.getId();
                            System.out.println("Modified item = " + itemId);
                        }
                        else if (modifiedEvent.getId() instanceof FolderId)
                        {
                            FolderId folderId = (FolderId) modifiedEvent.getId();
                            System.out.println("Modified folder = " + folderId);
                        }
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

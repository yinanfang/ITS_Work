import java.util.List;

import com.independentsoft.exchange.ContactPropertyPath;
import com.independentsoft.exchange.DistributionList;
import com.independentsoft.exchange.ExpandDistributionListResponse;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.ItemClass;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(ContactPropertyPath.ITEM_CLASS, ItemClass.DISTRIBUTION_LIST);

            FindItemResponse response = service.findItem(StandardFolder.CONTACTS, restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                ItemId itemId = response.getItems().get(i).getItemId();

                ExpandDistributionListResponse listResponse = service.expandDistributionList(itemId);
                List<Mailbox> mailboxes = listResponse.getMailboxes();

                for (int j = 0; j < mailboxes.size(); j++)
                {
                    System.out.println("Member = " + mailboxes.get(j).getEmailAddress());
                }

                System.out.println("---------------------------------------------------------------");
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

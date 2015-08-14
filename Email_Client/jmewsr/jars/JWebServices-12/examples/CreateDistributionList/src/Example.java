import com.independentsoft.exchange.Body;
import com.independentsoft.exchange.DistributionList;
import com.independentsoft.exchange.DistributionListMember;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Mailbox;
import com.independentsoft.exchange.RequestServerVersion;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            service.setRequestServerVersion(RequestServerVersion.EXCHANGE_2010);

            DistributionListMember member1 = new DistributionListMember(new Mailbox("John@mydomain.com"));
            DistributionListMember member2 = new DistributionListMember(new Mailbox("Mark@mydomain.com"));

            DistributionList list = new DistributionList();
            list.setDisplayName("Test");
            list.setSubject("Test");
            list.setBody(new Body("Body text."));
            list.getMembers().add(member1);
            list.getMembers().add(member2);

            ItemId itemId = service.createItem(list);
        }
        catch (ServiceException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getXmlMessage());

            e.printStackTrace();
        }
    }
}

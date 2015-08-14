import com.independentsoft.exchange.Item;
import com.independentsoft.exchange.ItemAttachment;
import com.independentsoft.exchange.ItemClass;
import com.independentsoft.exchange.ItemId;
import com.independentsoft.exchange.Task;
import com.independentsoft.exchange.TaskPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsEqualTo;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

public class Example {

    public static void main(String[] args)
    {
        try
        {
            Service service = new Service("https://myserver/ews/Exchange.asmx", "username", "password");

            IsEqualTo restriction = new IsEqualTo(TaskPropertyPath.ITEM_CLASS, ItemClass.TASK_REQUEST);

            FindItemResponse response = service.findItem(StandardFolder.TASKS, TaskPropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Task)
                {
                    ItemId taskRequestId = response.getItems().get(i).getItemId();

                    Item item = service.getItem(taskRequestId);

                    ItemAttachment itemAttachment = (ItemAttachment) service.getAttachment(item.getAttachments().get(0).getId());

                    Task task = (Task) itemAttachment.getItem();

                    System.out.println("Subject = " + task.getSubject());
                    System.out.println("StartDate = " + task.getStartDate());
                    System.out.println("DueDate = " + task.getDueDate());
                    System.out.println("Owner = " + task.getOwner());
                    System.out.println("Body Preview = " + task.getBodyPlainText());
                    System.out.println("----------------------------------------------------------------");

                    //Accept task and create new task in the Tasks folder
                    ItemId taskId = service.createItem(task, StandardFolder.TASKS);

                    //Delete TaskRequest from the Inbox
                    service.deleteItem(taskRequestId);

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

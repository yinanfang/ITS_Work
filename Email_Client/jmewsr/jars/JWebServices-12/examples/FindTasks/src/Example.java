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

            IsEqualTo restriction = new IsEqualTo(TaskPropertyPath.IS_COMPLETE, true);

            FindItemResponse response = service.findItem(StandardFolder.TASKS, TaskPropertyPath.getAllPropertyPaths(), restriction);

            for (int i = 0; i < response.getItems().size(); i++)
            {
                if (response.getItems().get(i) instanceof Task)
                {
                    Task task = (Task) response.getItems().get(i);

                    System.out.println("Subject = " + task.getSubject());
                    System.out.println("StartDate = " + task.getStartDate());
                    System.out.println("DueDate = " + task.getDueDate());
                    System.out.println("Owner = " + task.getOwner());
                    System.out.println("Body Preview = " + task.getBodyPlainText());
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

import java.util.*;
import org.joda.time.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
class myThread extends Thread
{
    private volatile boolean isShutdown = true;

    public void shutdown()
    {
        this.isShutdown = false;
        interrupt();
    }

    @Override
    public void run()
    {
        while (this.isShutdown)
        {
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            System.out.println("Hello World! I'm " + this.getName() + ". The time is " + time.format(formatter)+"sec");
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                System.out.println("Stop " + this.getName());
            }
        }
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = null;
        boolean isRunning = true;
        HashMap<String, myThread> threadsGroup = new HashMap<String, myThread>();
        try
        {
            sc = new Scanner(System.in);
            while (isRunning)
            {
                System.out.println("\nHere are your options:\na - Create a new thread\nb - Stop a given thread (e.g. \"b 2\" kills thread 2)\nc - Stop all threads and exit this program\n");
                String input = sc.nextLine();

//                for (String index : threadsGroup.keySet())
//                {
//                    System.out.println(index);
//                }
                switch (input)
                {
                    case "a" :
                   {
                        myThread newThread = new myThread();
                        threadsGroup.put(newThread.getName().substring(7), newThread);
                        newThread.start();
                       break;
                    }
                    case "b" :
                    {
                        System.out.println("please input thread number");
                        String index=sc.nextLine();
                        if (!threadsGroup.containsKey(index))
                        {
                            System.out.println("Invalid Input!\n");
                        }
                        else
                        {
                            threadsGroup.get(index).shutdown();
                            threadsGroup.remove(index);
                        }
                        break;
                    }
                    case "c":
                    {
                        isRunning = false;
                        for (String index : threadsGroup.keySet())
                        {
                            threadsGroup.get(index).shutdown();
                        }
                        threadsGroup = null;
                        break;
                    }
                    default:
                    {
                        System.out.println("Invalid Input!\n");
                        break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (sc != null)
            {
                sc.close();
                sc = null;
            }
        }
    }
}
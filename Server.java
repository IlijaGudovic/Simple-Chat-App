import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.*;

public class Server extends Thread{

    public static final int port = 9000;

    private ArrayList<ServerThread> conections;

    ServerSocket ss;
    
    @Override
    public void run()
    {

        conections = new ArrayList<ServerThread>();

        try 
        {
            ss = new ServerSocket(port);
            System.out.println("Server is running...");

            while (true)
            {
                Socket sock = ss.accept();

                print("Client accepted: " + (((InetSocketAddress)sock.getRemoteSocketAddress()).getAddress()).toString().replace("/",""));
                ServerThread newConection = new ServerThread(sock, this);
                conections.add(newConection);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + " Server Error Line " + new Throwable().getStackTrace()[0].getLineNumber());
        }
    }

    public void closeServer()
    {
        try 
        {
            ss.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void print(String massage)
    {
        for (ServerThread conection : conections)
        {
            System.out.println(massage);
            conection.broadcast(massage);
        }
    }


}

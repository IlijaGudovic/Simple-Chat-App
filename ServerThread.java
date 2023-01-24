import java.io.*;import java.net.*;

public class ServerThread extends Thread
{

    private Socket sock;

    private BufferedReader in;
    private PrintWriter out;

    Server server;

    public ServerThread(Socket sock, Server server)
    {
        this.sock = sock;
        this.server = server;

        try
        {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter( new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

            start();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    } 

    public void broadcast(String request)
    {
        try
        {
            out.println("[Server]: " +  request);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + " Sercer Socked Error Line " + new Throwable().getStackTrace()[0].getLineNumber());
        }
    }

    public void closeConection()
    {
        try
        {
            in.close();
            out.close();
            sock.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + " Sercer Socked Error Line " + new Throwable().getStackTrace()[0].getLineNumber());
        }
            
    }

    public void run()
    {
        try
        {
            out.println("New Client Joined");

            String massage;
            while((massage = in.readLine()) != null)
            {
                if (server != null)
                {
                    server.print(massage);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}

import java.io.*; import java.net.*;

import javax.swing.*;

import java.awt.*;

public class Client extends JFrame
{

    private JTextField inputText;
    private JTextArea displayText;

    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    Inbox inbox;
    String hostName;

    public Client(String ip)
    {

        System.out.println(ip);
        hostName = ip;

        setTitle("Client");
        setSize(340, 520);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {

                inbox = null;
                
                try
                {
                    System.out.println("Client Closed");
                    out.println("Client Closed");

                    inbox.stop = true;

                    in.close();
                    out.close();
                    sock.close();

                } catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
                
                setVisible(false);
                //System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints position = new GridBagConstraints();

        position.ipadx = 0;
        position.ipady = 0;
        inputText = new JTextField("Massage", 16);
        panel.add(inputText, position);

        position.ipadx = 1;
        position.ipady = 0;
        panel.add(new JPanel(), position);

        position.ipadx = 2;
        position.ipady = 0;
        JButton button = new JButton("Send");
        button.addActionListener(e -> {sendMassage();});
        panel.add(button, position);

        add(panel);

        displayText = new JTextArea(18, 24);
        add(displayText);

        setVisible(true);

        startConection();

        inbox = new Inbox();
    }

    private void sendMassage()
    {
        out.println(inputText.getText());
    }

    public void startConection()
    {

        try 
        {
            //InetAddress addr = InetAddress.getByName("127.0.0.1");
            System.out.println(hostName);
            sock = new Socket(InetAddress.getByName(hostName), 9000);

            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out =new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

            String response = in.readLine();
            System.out.println("[Server]: " + response);

        }
        catch (IOException e)
        {
            displayText.append(e.getMessage() + " Client Error Line " + new Throwable().getStackTrace()[0].getLineNumber());
        }
        
    }

    public class Inbox extends Thread
    {

        public Inbox()
        {
            start();
        }

        public boolean stop;

        public void run()
        {

            try
            {
                String inMassage;
                while((inMassage = in.readLine()) != null || !stop)
                {
                    displayText.append(inMassage + "\n");
                }
            }
            catch (IOException e)
            {
                displayText.append(e.getMessage() + " Client Error Line " + new Throwable().getStackTrace()[0].getLineNumber() + "\n");
            }
            
        }
        
    }
    
}

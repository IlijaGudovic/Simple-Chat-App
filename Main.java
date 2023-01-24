import javax.swing.*;
import java.awt.*;

public class Main extends JFrame 
{

    JTextField ipAdressText;

    public static void main(String[] args)
    {
        new Main();
    }

    public Main()
    {
        setTitle("Main");
        setSize(340, 520);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton serverButton = new JButton("Start Server");
        JButton clientButton = new JButton("Start Client");
        
        serverButton.addActionListener(e ->
        {
            Server sr = new Server();
            sr.start();
        });

        clientButton.addActionListener(e ->
        {
            new Client(ipAdressText.getText());
        });

        add(serverButton);
        add(clientButton);

        ipAdressText = new JTextField("127.0.0.1", 16);
        add(ipAdressText);

        setVisible(true);
    }

}

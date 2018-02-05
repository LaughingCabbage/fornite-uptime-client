import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    private JButton button1;
    private JPanel MainPanel;
    private JFormattedTextField StatusTextField;
    public static final String HostURL = "https://lightswitch-public-service-prod06.ol.epicgames.com/lightswitch/api/service/bulk/status?serviceId=Fortnite";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fortnite Uptime Tracker");
        frame.setContentPane(new Client().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(400,150);

    }

    public class Status<T>{
        public T status;
    }

    public Client() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World!");
            }
        });
        //Initialize the text field with server status
        pollServerStatus();

    }

    public void pollServerStatus(){
        //we only know the up status as of now, assume any status that's not an error
        //is downtime.
        StatusTextField.setText(getUptimeStatus(HostURL));
        switch(StatusTextField.getText()){
            case "UP":
                StatusTextField.setBackground(Color.GREEN);
                StatusTextField.setDisabledTextColor(Color.BLACK);
                break;
            case "ERROR":
                StatusTextField.setBackground(Color.ORANGE);
                StatusTextField.setDisabledTextColor(Color.WHITE);
                StatusTextField.setText("ERROR");
                break;

            default:
                StatusTextField.setBackground(Color.RED);
                StatusTextField.setDisabledTextColor(Color.WHITE);
                StatusTextField.setText("DOWN");
        }
    }

    private static String getUptimeStatus(String sURL) {

        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            // Convert to a JSON object
            JsonParser jp = new JsonParser(); //from gson
            InputStreamReader inputReader = new InputStreamReader((InputStream) request.getContent());
            JsonElement json = jp.parse(inputReader); //Convert the input stream to a json element
            //extract json into abstract class and return
            Status []responseArray = new GsonBuilder().create().fromJson(json, Status[].class);
            return responseArray[0].status.toString();

        } catch (MalformedURLException e) {
            //url failed
            return "ERROR";
        } catch (IOException e) {
            //open connection failed
            return "ERROR";
        }
    }
}

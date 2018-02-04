package com.kevingentile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
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

    public Client() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World!");
            }
        });
        //Initialize the text field with server status
        StatusTextField.setText(getUptimeStatus(HostURL));
    }

    private static String getUptimeStatus(String sURL) {
        //TODO create model for and deserialize status object http://www.baeldung.com/gson-deserialization-guide
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            InputStreamReader inputReader = new InputStreamReader((InputStream) request.getContent());
            JsonElement root = jp.parse(inputReader); //Convert the input stream to a json element
            JsonElement rootElement = root.getAsJsonArray(); //May be an array, may be an object.
            //JsonObject statusObject = rootArray.get(0);
            //System.out.println(rootArray.get(0).getAsString());
            //zipcode = rootobj.get("zip_code").getAsString(); //just grab the zipcode}
            return "";
        } catch (MalformedURLException e) {
            //url failed
            return "bad URL";
        } catch (IOException e) {
            //open connection failed
            return "bad connection";
        }
    }
}

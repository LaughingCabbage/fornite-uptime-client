package com.uptimetracker;

import com.google.gson.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    private JButton setAlarmButton;
    private boolean isAlarmSet = false;
    private JPanel MainPanel;
    private JFormattedTextField StatusTextField;
    private JButton cancelAlarmButton;
    private static final String HOST_URL = "https://lightswitch-public-service-prod06.ol.epicgames.com/lightswitch/api/service/bulk/status?serviceId=Fortnite";
    private Timer pollingTimer;
    private static final int TIMER_DELAY = 30000; // 30 seconds
    private Clip alarmClip;

    //TODO add tests
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fortnite Uptime Tracker");
        frame.setContentPane(new Client().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(400,150);

    }

    private Client() {
        loadAudio();
        setAlarmButton.addActionListener(e -> {
            enableAlarm();
        });
        cancelAlarmButton.addActionListener(e ->{
            disableAlarm();
        });
        //initialize server status polling timer
        pollingTimer = new Timer(TIMER_DELAY, e -> {
            pollServerStatus();
            System.out.println("polled server stats in timer");
        });
        pollingTimer.setRepeats(true);
        pollingTimer.start();

        //Initialize with server status
        pollServerStatus();

    }

    private void enableAlarm(){
        isAlarmSet = true;
        setAlarmButton.setEnabled(false);
        cancelAlarmButton.setEnabled(true);
    }

    private void disableAlarm(){
        isAlarmSet = false;
        setAlarmButton.setEnabled(true);
        cancelAlarmButton.setEnabled(false);
        alarmClip.stop();
    }

    private class Status<T>{
        T status;
    }

    private void pollServerStatus() {
        //we only know the up status as of now, assume any status that's not an error
        //is downtime.
        StatusTextField.setText(getUptimeStatus(HOST_URL));

        switch (StatusTextField.getText()) {
            case "UP":
                StatusTextField.setBackground(Color.GREEN);
                StatusTextField.setDisabledTextColor(Color.BLACK);
                if (isAlarmSet) {
                    //trigger alarm
                    triggerAlarm();
                }
                setAlarmButton.setEnabled(false);
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
                //enable alarm while server is down
                if (!isAlarmSet) {
                    setAlarmButton.setEnabled(true);
                }
        }
    }

    private void triggerAlarm(){
        alarmClip.loop(Clip.LOOP_CONTINUOUSLY);
        JOptionPane.showMessageDialog(null, "Fortnite is online!");
        disableAlarm();
    }

    private void loadAudio(){
        try {
            URL url = this.getClass().getClassLoader().getResource("alarm.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            alarmClip = AudioSystem.getClip();
            alarmClip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
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

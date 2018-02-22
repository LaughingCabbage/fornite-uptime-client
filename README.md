# fortnite-uptime-client
![Fortnite Uptime Tracker](https://github.com/LaughingCabbage/fornite-uptime-client/raw/master/release/demo.PNG)


This tool simply polls the Fornite server for a status.
During downtime, you can set an alarm that will sound when the servers come back online.

# Installation

If you don't have it, you'll need a copy of the [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

The download can be found in the release folder or just click below:


[Download Uptime Tracker](https://github.com/LaughingCabbage/fornite-uptime-client/raw/master/release/fortnite-uptime-client.jar)

# About

This project is my first attempt at anything Java related, and I didn't want to spend too much time tweaking code.
I used Google's [Gson](https://github.com/google/gson) library to parse JSON from the status server, and I used Java's swing library
to create a user interface. The program polls the server every 30 seconds for a status. If the status is "DOWN" the set alarm button is enabled.
Once the alarm is set and the program detects an "UP" status, an alarm will sound to let you know! 

# Contact

    kgentile@protonmail.com
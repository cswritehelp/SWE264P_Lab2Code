/**
 * @(#)ClientOutput.java
 *
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 *
 */


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;


/**
 * This class represents a client log component which is responsible for logging text messages
 * displayed onto the console upon receiving show events. Show events are expected to carry a
 * <code>String</code> object as its parameter that is to be displayed. This component need to
 * subscribe to those events to receive them, which is done at the time of creation.
 *
 * @author xxx
 * @version 1.0
 */
public class ClientLog implements Observer {
    static final String logFile = "Log..txt";
    /**
     * Constructs a client log component. A new client log component subscribes to show events
     * at the time of creation.
     */
    public ClientLog() {
        // Subscribe to SHOW event.
        EventBus.subscribeTo(EventBus.EV_SHOW, this);
    }

    /**
     * Event handler of this client log component. On receiving a show event, the attached
     * <code>String</code> object is appended to the log file.
     *
     * @param event an event object. (caution: not to be directly referenced)
     * @param param a parameter object of the event. (to be cast to appropriate data type)
     */
    public void update(Observable event, Object param) {
        // Save the event parameter (a string) to the log file.
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(logFile, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println((String) param);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

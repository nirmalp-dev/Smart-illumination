package com.example.nirma.smartillumination;

import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MqttSender extends MqttManager  {
     void sendMessage(String msg)
    {
       // String clientId = MqttClient.generateClientId();
        Log.i("Sender","in sender");
        MqttAndroidClient client =
                new MqttAndroidClient(MqttSender.this, "tcp://broker.hivemq.com:1883",
                        MainActivity.clientId);

        String topic = "DeSmilItLegends";
        Log.i("Sender","connected sender");
        String payload = msg;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
}

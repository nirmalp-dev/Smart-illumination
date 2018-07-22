package com.example.nirma.smartillumination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttManager extends AppCompatActivity {
    String clientId = MqttClient.generateClientId();

    MqttAndroidClient client =
            new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                    clientId);
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mqtt_manager);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            IMqttToken token = client.connect(options);
            Log.i("msg","connection established");
        }
        catch (MqttException e) {
            Log.i("error","connection failed");
            e.printStackTrace();
        }
    }
}

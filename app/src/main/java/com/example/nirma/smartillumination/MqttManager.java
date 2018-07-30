package com.example.nirma.smartillumination;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttManager extends AppCompatActivity {
    String clientId = MqttClient.generateClientId();

    Button dashboard,publish;
    EditText payload;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mqtt_manager);
            payload=(EditText)findViewById(R.id.payload);
            publish=(Button)findViewById(R.id.publish);
            dashboard=(Button)findViewById(R.id.dashboard);
            String clientId = MqttClient.generateClientId();
            MqttAndroidClient client =
                    new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                            clientId);

            try {
                IMqttToken token = client.connect();
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // We are connected
                        Log.d("con", "onSuccess");

                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Log.d("con", "onFailure");

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            IMqttToken token = client.connect(options);
            Log.i("msg","connection established");
            Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT);

            dashboard.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(i);
                }
            });
            publish.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String msg = payload.getText().toString();
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    MainActivity m =new MainActivity();
                    m.sendMessage(msg);
//                    MqttSender sender = new MqttSender();
//                    sender.sendMessage(msg);
                }
            });
        }
        catch (MqttException e) {
            Log.i("error","connection failed");
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }
}

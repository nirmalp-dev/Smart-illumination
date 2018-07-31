package com.example.nirma.smartillumination;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MqttManager extends AppCompatActivity {
    String clientId = MqttClient.generateClientId();

    Button subscribe,publish;
    EditText payload;
    ListView msgList;
    ArrayList<String> msgArray=new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mqtt_manager);
            payload=(EditText)findViewById(R.id.payload);
            publish=(Button)findViewById(R.id.publish);
            subscribe=(Button)findViewById(R.id.subscribe);

            adapter = new ArrayAdapter<String>(this, R.layout.msg_listview, msgArray);

            final ListView listView = (ListView) findViewById(R.id.msg_list);
            listView.setAdapter(adapter);
            final ArrayAdapter tempAdapter;

            final MqttAndroidClient client =
                    new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                            MainActivity.clientId);

            try {
                IMqttToken token = client.connect();
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // We are connected
                        Log.d("msg", "onSuccess");

                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Log.d("msg", "onFailure");

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
            subscribe.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String topic = "Smart-Illumination";
                    int qos = 1;
                    try {
                        IMqttToken subToken = client.subscribe(topic, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.i("conn","subscribed");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Log.i("conn","failed to subscribe");
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });
            publish.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String msg = payload.getText().toString();

                    String topic = "Smart-Illumination";
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = msg.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish(topic, message);
                        payload.setText("");
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }
                   // Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                }
            });

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.i("error","connection loast");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Toast.makeText(getApplicationContext(),message.toString(),Toast.LENGTH_SHORT).show();
                    msgArray.add(message.toString());
                    adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.msg_listview, msgArray);
                    listView.setAdapter(adapter);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.i("notify" , "msg reached");
                }
            });
        }
        catch (Exception e) {
            Log.i("error","connection failed");
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }
}

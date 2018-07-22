package com.example.nirma.smartillumination;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button on,off,ad,msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        on=(Button)findViewById(R.id.on);
        off=(Button)findViewById(R.id.off);
        ad=(Button)findViewById(R.id.adjust);
        msg=(Button)findViewById(R.id.msg);

        ad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Adjust.class);
                startActivity(i);
            }
        });
        on.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Light will be turned ON",Toast.LENGTH_SHORT).show();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Light will be turned OFF",Toast.LENGTH_SHORT).show();
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MqttManager.class);
                startActivity(i);
            }
        });

    }
}

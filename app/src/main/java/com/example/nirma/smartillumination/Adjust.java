package com.example.nirma.smartillumination;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class Adjust extends Activity {
    Button man,auto;
    SeekBar sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust);
        man=(Button)findViewById(R.id.manual);
        auto=(Button)findViewById(R.id.auto);
        sb=(SeekBar) findViewById(R.id.seekBar);
        sb.setVisibility(View.INVISIBLE);
        man.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(sb.getVisibility() == View.INVISIBLE)
                    sb.setVisibility(View.VISIBLE);
                else
                    sb.setVisibility(View.INVISIBLE);
            }
        });
        auto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Light will be adjusted automatically",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

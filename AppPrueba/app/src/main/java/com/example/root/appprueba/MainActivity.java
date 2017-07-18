package com.example.root.appprueba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch luzSwitch, puertaSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        luzSwitch = (Switch) findViewById(R.id.switchLuces);
        puertaSwitch = (Switch) findViewById(R.id.switchPuertas);

        luzSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println(" luz haha");
                }else{
                    System.out.println("luz hoho");
                }

            }
        });

        puertaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    System.out.println(" puerta haha");
                }else{
                    System.out.println("puerta hoho");
                }

            }
        });
    }




}

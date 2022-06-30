package com.example.avoid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class rip extends AppCompatActivity {
    Button rec;
    String ip_store [];
    WifiManager wifiManager;
    String ip_address;
    boolean isthere=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rip);
        ActionBar act=getSupportActionBar();
        act.hide();
        rec=(Button) findViewById(R.id.receiver) ;

        ip_store =new String[1000];
        ip_store[0]="192.168.1.4";
        ip_store[1]="192.168.1.6";
        ip_store[2]="192.168.1.7";
        ip_store[3]="223.185.51.149";
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        ip_address = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        for(int i=0;i<1000;i++){
            if(ip_address.equals(ip_store[i])){
                isthere=true;
                break;
            }
        }

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isthere){
                    Toast.makeText(rip.this, "Not allowed", Toast.LENGTH_SHORT).show();
                }

                else{
                Intent i=new Intent(rip.this,screenmsg.class);
                startActivity(i);}
            }
        });

    }
}
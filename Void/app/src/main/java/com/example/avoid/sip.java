package com.example.avoid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;

import static android.text.format.Formatter.formatIpAddress;
import static java.lang.Integer.valueOf;

public class sip extends AppCompatActivity {
    EditText ip,port;
    TextView txt;
    String sender_ip,sender_port;
    Button connect;
    WifiManager wifiManager;
    private String ip_store [] [];
    private Boolean isthere=false;
    private String portp="";
    String ipAddress;
    Socket socket;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sipport);
        ActionBar act=getSupportActionBar();
        act.hide();
        ip_store =new String[1000] [1000];
        for(int i=0;i<1000;i++){
            for(int j=0;j<1000;j++){
                ip_store[i][j]="-1";
            }
        }
        ip_store[0][0]="192.168.1.4";
        ip_store[0][1]="192.168.1.7";
        ip_store[0][1]="192.168.1.7";
        ip_store[1][0]="192.168.1.7";
        ip_store[1][1]="192.168.1.4";



        ip=(EditText) findViewById(R.id.ip);
        port=(EditText) findViewById(R.id.port);
        connect=(Button) findViewById(R.id.connect);



        txt =(TextView) findViewById(R.id.textView);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        txt.setText(ipAddress);

        data=sender_ip;


    }






    public void cnct(View view){
        sender_ip =ip.getText().toString();
        sender_port =port.getText().toString();
        data=sender_ip;
        int pos=0;
        for(int i=0;i<1000;i++){
            if(ip_store[i][0]==ipAddress){
                pos=i;
                break;
            }
        }
        Log.d("POS",Integer.toString(pos));
        Log.d("Pui",data);
        int j=0;
        while(j!=1000){
            if(ip_store[pos][j].equals(data)){
                isthere=true;
                break;
            }
            isthere=false;
            j++;
        }

        portp=sender_port;
        Log.d("POSssaa",Integer.toString(j));
        Log.d("Pogo",ip_store[pos][1]);
        Log.d("bool",Boolean.toString(isthere));
//       if(portp=="" || data==""){
//           Toast.makeText(this, "Enter data", Toast.LENGTH_SHORT).show();
//       }
        if(ip.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter ip", Toast.LENGTH_SHORT).show();
        }

        else if(port.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter port", Toast.LENGTH_SHORT).show();
        }

      else if(!isthere){
           Toast.makeText(this, "Not allowed", Toast.LENGTH_SHORT).show();
       }

       else{
//           Intent i=new Intent(sip.this,screenmsg.class);
//           startActivity(i);
           Thread clientThread = new Thread(new
                   ClientThread());
            clientThread.start();
//           Handler handler=new Handler();
//           handler.post(new Runnable() {
//               @Override
//               public void run() {
//                   Intent i=new Intent(sip.this,screenmsg.class);
//                   clientThread.start();
//                   startActivity(i);
//               }
//           });





       }

    }





    public class ClientThread implements Runnable
    {
        public void run()
        {
            try
            {
//                while(true){
                String k="kk";
                    Log.d("lol",k);
;                    InetAddress serverAddr =
                            InetAddress.getByName(data);
                   socket = new Socket(serverAddr, valueOf(portp));
                    Log.d("AAAAA","AA");
                    if(socket.isConnected()){
                        Log.d("AAAAAb","AAb");
//                        Handler handler=new Handler();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {

                        socket.close();

                        Thread.sleep(100);
                                Intent i=new Intent(sip.this,screenmsg.class);
                                startActivity(i);
//                            }
//                        });
                    }
                    else{
                        ip.setText(socket.isConnected()==true?"True":"False");
                    }

                    //
//                    create client socket
/*******************************************
 setup i/p streams
 ******************************************/

//                }
            }
            catch (Exception e)
            {}
        }
    }




}
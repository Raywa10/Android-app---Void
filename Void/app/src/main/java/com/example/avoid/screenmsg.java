package com.example.avoid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class screenmsg extends AppCompatActivity {
    private boolean isText=true;
    private String path="";
    EditText msg;
    ImageView send;
    TextView Hist;
    WifiManager wifiManager;
    String ip_address;
    Socket socket,sockets;
    String str;
    String fin="";
    ServerSocket serverSocket;
    Socket client;
    int port=10000;
    int port1=10000;
    Handler handler=new Handler();
    Scanner scanner=new Scanner(System.in);
    Thread recthread = new Thread(new
            receivemsg());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagescreen);
        ActionBar act=getSupportActionBar();
        act.hide();
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        ip_address = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        ip_address="192.168.1.7";
        send=(ImageView) findViewById(R.id.sendbut);
        msg=(EditText) findViewById(R.id.message);
        Hist=(TextView) findViewById(R.id.hist);

//        recthread.start();
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("clicked","pole");
//                String s=msg.getText().toString();
//                if(s.isEmpty()){
//
//                }
//                else{
//                    Thread sentThread = new Thread(new sentMessage());
//                    sentThread.start();
//                }
//            }
//        });

    }

    public void snd2(){
        Log.d("snd2","yes");
    Thread sndFile=new Thread(new sendFile());
    sndFile.start();

    }

    public void snd(View view){
        String s=msg.getText().toString();
        if(!isText && !s.isEmpty())
        {  Log.d("Istext","yes");
            snd2();
            return;
        }
        Log.d("clicked","pole");

        if(s.isEmpty()){

        }
        else{
//            Toast.makeText(this, "BSDK", Toast.LENGTH_SHORT).show();
            Thread sentThread = new Thread(new sentMessage());
//            sentThread.start();

        }


    }

    Intent myFile;
    public void open_Directory(View view){
        myFile=new Intent(Intent.ACTION_GET_CONTENT);
        myFile.setType("*/*");
        startActivityForResult(myFile,10);
    }
    public void furtherSendingTask(){
        isText=false;//send file not text
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    path = data.getData().getPath();
                    msg.setText(new File(Environment.getExternalStorageDirectory(),"test.jpg").getAbsolutePath());
                    furtherSendingTask();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class receivemsg implements Runnable
    {
        public void run()
        {
            try
            {
                while(true)
                {
                    Log.d("inthread","idhr");
                    serverSocket = new ServerSocket(port);
                    Log.d("inthread","idhr");
                    client = serverSocket.accept();

/*******************************************
 setup i/p streams
 ******************************************/
                    Log.d("receiving","received");
                    DataInputStream in = new
                            DataInputStream(client.getInputStream());
                    BufferedReader br=new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    Log.d("message is",br.readLine());
                    while ((line = br.readLine()) != null)
                    {
                        fin=fin+"Receiver: "+line+"\n";
                        Log.d("msg",fin);
                        handler.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Hist.setText(fin);
                            }
                        });
                    }
                    in.close();
                    serverSocket.close();
                    client.close();
                    Thread.sleep(100);
                }
            }
            catch (Exception e)
            {}
        }
    }










//    public class receivemsg implements Runnable
//    {
//        public void run()
//        {
//            try
//            {
//                while(true)
//                {
//                    InetAddress serverAddr =
//                            InetAddress.getByName(ip_address);
//                    socket = new Socket(serverAddr, port); //
///*******************************************
// setup i/p streams
// ******************************************/
//                    DataInputStream in = new
//                            DataInputStream(socket.getInputStream());
//                    String line = null;
//                    while ((line = in.readLine()) != null)
//                    {
//                        fin=fin+"receiver"+line+"\n";
//                        handler.post(new Runnable()
//                        {
//                            @Override
//                            public void run()
//                            {
//                                Hist.setText(fin);
//                            }
//                        });
//                    }
//                    in.close();
//                    socket.close();
//                    Thread.sleep(100);
//                }
//            }
//            catch (Exception e)
//            {}
//        }
//    }
public class sendFile implements Runnable{

    @Override
    public void run() {
        try {
//            InetAddress serverAddr =
//                    InetAddress.getByName("192.168.1.7");
            sockets=new Socket("192.168.1.4",port1);
//                client = serverSocket.accept();
                Log.d("Socketaccepted", "yes");
                File file = new File(Environment.getExternalStorageDirectory(),"test.jpg");

                byte[] bytes = new byte[(int) file.length()];
            Log.d("Fileaccepted", file.getAbsolutePath().toString());
                FileInputStream fis = new FileInputStream(file);
            Log.d("Filea", "yes");
//                String kumawat = extractname(path);
                BufferedInputStream bis;

                bis = new BufferedInputStream(fis);
            Log.d("buffer", "yes");
                bis.read(bytes, 0, bytes.length);
            Log.d("byteread", "yes");

                OutputStream oos = sockets.getOutputStream();
                oos.write(bytes, 0, bytes.length);
                Log.d("Writeen", "yes");
                oos.flush();
                sockets.close();
                isText = true;

        }
        catch(Exception e){}
    }
}


public String extractname(String path){

        String ans="";
        for(int i=path.length()-1;i>=0;i--){
            if(path.charAt(i)=='/')
                break;

            ans=path.charAt(i)+ans;

        }
    return ans;

}




    class sentMessage implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                Log.d("Inthread","idhr");
                InetAddress serverAddr =
                        InetAddress.getByName(ip_address);
                socket = new Socket(serverAddr, port);
                DataOutputStream os = new
                        DataOutputStream(socket.getOutputStream());
                Log.d("Inthread1","idhraa0");
                str = msg.getText().toString();

                str = "\n"+str + "\n";
                fin=fin+"Sender:"+str;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Hist.setText(fin);
                        msg.setText("");
                    }
                });
                Log.d("Int","sended");
                os.writeBytes(str);
                Log.d("msgwas",str);
                os.flush();
                os.close();
                socket.close();
                Thread.sleep(100);
            }
            catch(IOException | InterruptedException e)
            {

            }


        }
    }

}


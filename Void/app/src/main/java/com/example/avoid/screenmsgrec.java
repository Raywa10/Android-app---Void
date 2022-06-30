package com.example.avoid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class screenmsgrec extends AppCompatActivity {
    EditText msg;
    ImageView send;
    TextView Hist;
    WifiManager wifiManager;
    String ip_address;
    String str;
    String fin="";
    ServerSocket serverSocket,ss;
    Socket client;
    Socket socket,socketf;
    int port=10000;
    int port1=10000;
    Scanner scanner=new Scanner(System.in);
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenmsgrec);
        ActionBar act=getSupportActionBar();
        act.hide();
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        ip_address = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        send=(ImageView) findViewById(R.id.sendbut);
        msg=(EditText) findViewById(R.id.message);
        Hist=(TextView) findViewById(R.id.hist);
//        Thread ead = new Thread(new
//                receivemsg1());
//        ead.start();

        Thread recfile=new Thread((new receiveFile()));
        recfile.start();




    }

    public void snd(View view){

        Log.d("clicked","pole");
        String s=msg.getText().toString();
        if(s.isEmpty()){

        }
        else{
            Toast.makeText(this, "BSDK", Toast.LENGTH_SHORT).show();
            Thread sentThread = new Thread(new sentMessage1());
//            sentThread.start();
        }


    }



    public class receiveFile implements Runnable{

        @Override
        public void run() {
            try{     ss=new ServerSocket(port1);
                while(true){


                    socketf=ss.accept();
//                Log.d("inthread","idhr");
//                client = serverSocket.accept();
                Log.d("socket created","yes");
//                    DataInputStream in = new
//                            DataInputStream(socket.getInputStream());
//                    BufferedReader br=new BufferedReader(new InputStreamReader(in));
                    String pt="test.jpg";
                    Log.d("path retreived",pt);
                    Log.d("Filerec",pt);
                    File file = new File(Environment.getExternalStorageDirectory(),pt);

                    byte[] bytes=new byte[6022386];
                    Log.d("Fileos",file.getAbsolutePath().toString());
                    InputStream ois = socketf.getInputStream();
                    Log.d("is",file.getAbsolutePath().toString());

                    FileOutputStream fos =new FileOutputStream(new File(getFilesDir(),"test.jpg"));
                    Log.d("fos",file.getAbsolutePath().toString());
                    BufferedOutputStream bos=new BufferedOutputStream(fos);
                    Log.d("bos",file.getAbsolutePath().toString());
                   int bytesRead= ois.read(bytes,0,bytes.length);
                    Log.d("read1",file.getAbsolutePath().toString());
                   int current =bytesRead;

                   do{
                       bytesRead=ois.read(bytes,current,(bytes.length-current));
                       if(bytesRead>=0){
                           current+=bytesRead;
                       }
                   }while(bytesRead>-1);
                    Log.d("loope",file.getAbsolutePath().toString());
                    bos.write(bytes,0,current);
                    Log.d("File written on rec bef","yes");
                   bos.flush();
                   bos.close();
//                    fos = new FileOutputStream(file);
//                    fos.write(bytes);
                    Log.d("File written on rec","yes");

//                    in.close();
              socketf.close();




            }
            }
            catch(Exception e){}
        }
    }


    public class receivemsg1 implements Runnable
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
                        fin=fin+"Sender: "+line+"\n";
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



//    class sentMessage1 implements Runnable
//    {
//        @Override
//        public void run()
//        {
//            try
//            {
//                Log.d("Inthread","idhr");
//                serverSocket = new ServerSocket(port);
//
//                client = serverSocket.accept();
//                DataOutputStream os = new
//                        DataOutputStream(client.getOutputStream());
//                Log.d("Inthread1","idhraao");
//                str = msg.getText().toString();
//                str = str + "\n";
//                fin=fin+"Receiver:"+str;
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Hist.setText(fin);
//                        msg.setText("");
//                    }
//                });
//                os.writeBytes(str);
//                os.flush();
//                os.close();
//                serverSocket.close();
//                client.close();
//                Thread.sleep(100);
//            }
//            catch(IOException | InterruptedException e)
//            {
//
//            }
//
//
//        }
//    }



    class sentMessage1 implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                Log.d("Inthread","idhr");
                InetAddress serverAddr =
                        InetAddress.getByName("192.168.1.4");
                socket = new Socket(serverAddr, port);
                DataOutputStream os = new
                        DataOutputStream(socket.getOutputStream());
                Log.d("Inthread1","idhraa0");
                str = msg.getText().toString();

                str = "\n"+str + "\n";
                fin=fin+"Receiver:"+str;

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
package com.example.avoid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.net.ServerSocket;
import java.net.Socket;

public class waiting extends AppCompatActivity {
    ServerSocket serverSocket;
    Socket client;
    Handler handler = new Handler();
    int serverport = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        ActionBar act=getSupportActionBar();
        act.hide();

        Thread serverThread = new Thread(new serverThread());
        serverThread.start();
    }


    public class serverThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
//                while(true) {
                    serverSocket = new ServerSocket(serverport);
                    Socket client = serverSocket.accept();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(waiting.this, "Connected", Toast.LENGTH_SHORT).show();
                serverSocket.close();
                client.close();
                Thread.sleep(100);
                            Intent intent = new Intent(waiting.this, screenmsgrec.class);
                            startActivity(intent);
//                        }
//                    });
/*******************************************
 setup i/p streams
 ******************************************/
//                }   }


                }
            catch (Exception e)
            {
            }
        }
    }

}




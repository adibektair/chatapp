package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class ChatBoxActivity extends AppCompatActivity {


    private Socket socket;
    private String Nickname ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

// get the nickame of the user



        Nickname = (String)getIntent().getExtras().getString(MainActivity.NICKNAME);

//connect you socket client to the server

        try {


//if you are using a phone device you should connect to same local network as your laptop and disable your pubic firewall as well

            socket = IO.socket("http://localhost:3000");

            //create connection

            socket.connect();

            if(socket.connected()){
                Toast.makeText(ChatBoxActivity.this, "connected", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ChatBoxActivity.this, "NOT connected", Toast.LENGTH_SHORT).show();
            }
// emit the event join along side with the nickname

            socket.emit("join",Nickname);


        } catch (URISyntaxException e) {
            e.printStackTrace();

        }


        socket.on("userjoinedthechat", new Emitter.Listener()

        {
            @Override
            public void call ( final Object...args){
                runOnUiThread(new Runnable() {
                    @Override
                    public
                    void run() {
                        String data = (String) args[0];
                        Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }




}


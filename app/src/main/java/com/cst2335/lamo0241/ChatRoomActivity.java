package com.cst2335.lamo0241;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {
    ArrayList<Chat> chatArrayList = new ArrayList<>();
    Boolean delete = false;
    int pos;
    int id;

    ChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Button sendbutton = findViewById(R.id.sendbutton);

        Button recievebutton = findViewById(R.id.recievebutton);
        EditText chattext = findViewById(R.id.chattext);
        ListView chatlist = findViewById(R.id.chatlist);
        SQLite db = new SQLite(this);

        db.getWritableDatabase();

        adapter = new ChatAdapter(this, R.id.chatlist, chatArrayList);



            try {
                chatArrayList = db.getChat();
                for (int x = 0; x < (chatArrayList.size()); x++) {
                    chatArrayList.get(x).setPosition(x);
                }
            } catch (Exception e) {
            }
            adapter.notifyDataSetChanged();


        chatlist.setAdapter(adapter);

        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setTitle(R.string.delete);
        alertbuilder.setPositiveButton("Positive", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete = true;
                if(delete){
                    long index = chatArrayList.get(pos).getID();
                    Log.e("INT INDEX IS", String.valueOf(index));
                    chatArrayList.remove(pos);
                    db.removeChat(index);
                }
                adapter.notifyDataSetChanged();
            }
        });
        alertbuilder.setNegativeButton("Negative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete = false;
                if(delete){
                    chatArrayList.remove(pos);
                }
                adapter.notifyDataSetChanged();
            }
        });


        chatlist.setOnItemLongClickListener((parent, view, position,id) -> {
            alertbuilder.setMessage("Selected row is " + position + " with ID " + chatArrayList.get(position).getID());

            Log.e(ACTIVITY_SERVICE, "Pressed item " + position);
            AlertDialog alert = alertbuilder.create();
            alert.show();
            pos = position;
            adapter.notifyDataSetChanged();
            return true;
        });


        sendbutton.setOnClickListener(click -> {
            Chat sentChat = new Chat(chattext.getText().toString(), true);
            db.addChat(sentChat);
            chatArrayList.add(sentChat);
            adapter.notifyDataSetChanged();
            chattext.setText("");
        });

        recievebutton.setOnClickListener(click -> {
            Chat sentChat = new Chat(chattext.getText().toString(), false);
            db.addChat(sentChat);
            chatArrayList.add(sentChat);
            adapter.notifyDataSetChanged();
            chattext.setText("");
        });


    }


     private class ChatAdapter extends BaseAdapter{
        private Context context;
        int resource;
        ArrayList<Chat> chatlog;
        public ChatAdapter(Context context, int resource, ArrayList<Chat> arrayList) {
            this.context = context;
            this.resource = resource;
            this.chatlog = arrayList;
        }

        @Override
        public int getCount() {
            return chatArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return chatArrayList.get(position);
        }

        @Override
        public long getItemId(int id) {
            return chatArrayList.get(id).getID();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String text = chatArrayList.get(position).getText();
            LayoutInflater inflater = LayoutInflater.from(context);
            if(chatArrayList.get(position).getSend()) {
                convertView = inflater.inflate(R.layout.list_send, parent, false);
            }
            else {
                convertView = inflater.inflate(R.layout.list_recieve, parent, false);
            }
            TextView sendtext = convertView.findViewById(R.id.sendtext);
            sendtext.setText(text);
            return convertView;
        }

    }
}

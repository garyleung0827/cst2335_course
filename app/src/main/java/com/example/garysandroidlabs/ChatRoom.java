package com.example.garysandroidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garysandroidlabs.databinding.ActivityChatRoomBinding;
import com.example.garysandroidlabs.databinding.ReceiveMessageBinding;
import com.example.garysandroidlabs.databinding.SentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ChatRoomViewModel chatModel;

    ChatMessageDAO mDAO;

    private RecyclerView.Adapter myAdapter;

    Toolbar theToolbar;

    //inner class for MyRowHolder
    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(click ->{
                int position= getAbsoluteAdapterPosition();

                ChatMessage selected = messages.get(position);
                chatModel.selectedMessage.postValue(selected);
               /* AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

                builder.setMessage("Do you want to delete the message:" + messageText.getText())
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) ->{} )
                        .setPositiveButton("Yes",((dialog, cl) ->{
                            ChatMessage m  = messages.get(position);
                            mDAO.deleteMessage(m);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(messageText, "deleted message #"+position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", (cl2) ->{
                                        messages.add(position, m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        } ))
                        .create().show();*/
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

    ArrayList<ChatMessage> messages = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID ==R.id.menu_item_1 && binding.fragmentLocation != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

            ChatMessage m = chatModel.selectedMessage.getValue();

            long fragNo = m.id;
            TextView messageText = binding.fragmentLocation.findViewById(R.id.message_frag);



            builder.setMessage("Do you want to delete the message:" + messageText.getText().toString())
                    .setTitle("Question:")
                    .setNegativeButton("No", (dialog, cl) ->{} )
                    .setPositiveButton("Yes",((dialog, cl) ->{
                        mDAO.deleteMessage(m);
                        messages.remove(m);// not working
                        myAdapter.notifyDataSetChanged();
                        Snackbar.make(messageText, "deleted message #"+fragNo, Snackbar.LENGTH_LONG)
                                .setAction("Undo", (cl2) ->{
                                    messages.add(m);
                                    myAdapter.notifyDataSetChanged();
                                })
                                .show();
                    } ))
                    .create().show();



        }
        else if(itemID == R.id.menu_item_about) {
            Toast.makeText(this,"Version 1.0, created by Kin Man Leung",Toast.LENGTH_LONG).show();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        theToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(theToolbar);

        //create database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").allowMainThreadQueries().build();
        //create dao
        mDAO = db.cmDAO();

        //create chat view model
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        //get all messages from the database at the begining
        if(messages == null){
            chatModel.messages.setValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(new Runnable() {
                @Override
                public void run() {
                    messages.addAll(mDAO.getAllMessages());
                    runOnUiThread(()->binding.recycleView.setAdapter(myAdapter));
                }
            });
            //chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            //Create chat object
            ChatMessage obj = new ChatMessage(binding.textInput.getText().toString(),currentDateandTime,1);
            mDAO.insertMessage(obj);
            obj.id= mDAO.insertMessage(obj);
            messages.add(obj);

            //notify insert
            myAdapter.notifyDataSetChanged();
            //clear the previous text:
            binding.textInput.setText("");
        });
        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            //Create chat object
            ChatMessage obj = new ChatMessage(binding.textInput.getText().toString(),currentDateandTime,0);
            mDAO.insertMessage(obj);
            obj.id= mDAO.insertMessage(obj);
            //Toast.makeText(this,obj.getId(),Toast.LENGTH_LONG).show();
            messages.add(obj);

            //notify insert
            myAdapter.notifyItemInserted(messages.size()-1);
            //clear the previous text:
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>(){
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

                if(viewType==0) {
                    SentMessageBinding bindingSend = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(bindingSend.getRoot());
                }
                else{
                    ReceiveMessageBinding bindingReceive = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(bindingReceive.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position){

                holder.messageText.setText("");
                holder.timeText.setText("");

                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount(){
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                ChatMessage obj = messages.get(position);
                if(obj.isSentButton() == 0){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        chatModel.selectedMessage.observe(this, (newMessageValue) ->{
            if(newMessageValue != null) {
                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
                tx.replace(R.id.fragment_location, chatFragment);
                tx.addToBackStack("new entry");
                tx.commit();

            }

        });
    }
}
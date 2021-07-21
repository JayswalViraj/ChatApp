package com.example.chatzup.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatzup.Models.MessageModel;
import com.example.chatzup.R;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String recID;




    int SENDER_VIEW_TYPE = 1;
    int RECIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recID) {
        this.messageModels = messageModels;
        this.context = context;
        this.recID = recID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == SENDER_VIEW_TYPE) {


            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new senderViewVolder(view);
        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver, parent, false);
            return new ReciverViewVolder(view);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;

        } else {

            return RECIVER_VIEW_TYPE;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        String sender= FirebaseAuth.getInstance().getUid()+recID;
                        database.getReference().child("chats").child(sender)
                                .child(messageModel.getMessageID())
                                .setValue(null);


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).show();




                return false;
            }
        });

        if (holder.getClass() == senderViewVolder.class) {

            ((senderViewVolder) holder).senderMsg.setText(messageModel.getMesaage());
        } else {

            ((ReciverViewVolder) holder).reciverMsg.setText(messageModel.getMesaage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReciverViewVolder extends RecyclerView.ViewHolder {


        TextView reciverMsg, reciverTime;

        public ReciverViewVolder(@NonNull View itemView) {
            super(itemView);

            reciverMsg = itemView.findViewById(R.id.recivertext);
            reciverTime = itemView.findViewById(R.id.receverTime);


        }
    }


    public class senderViewVolder extends RecyclerView.ViewHolder {


        TextView senderMsg, senderTime;

        public senderViewVolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.sendertext);
            senderTime = itemView.findViewById(R.id.sendertime);

        }
    }
}



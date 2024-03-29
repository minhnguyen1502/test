package com.example.t4_contact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4_contact.adapter.ContactsAdapter;
import com.example.t4_contact.db.DatabaseHelper;
import com.example.t4_contact.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        recyclerView = findViewById(R.id.recycler_view_contacts);
        db = new DatabaseHelper(this);

        contactArrayList.addAll(db.getAllContacts());
        contactsAdapter = new ContactsAdapter(this,contactArrayList,MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContacts(false,null,-1);
            }
        });
    }
    public void addAndEditContacts(final boolean isUpdated,final Contact contact, final int position){
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact,null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);
        contactTitle.setText(!isUpdated ? "Add new contact" : "Edit contact");
        if(isUpdated && contact != null){
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(TextUtils.isEmpty(newContact.getText().toString())){
                            Toast.makeText(MainActivity.this,"Please enter a name", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            dialogInterface.dismiss();
                        }
                        if(isUpdated && contact != null){
                            UpdateContact(newContact.getText().toString(),contactEmail.getText().toString(),position);
                        }else {
                            CreateContact(newContact.getText().toString(),contactEmail.getText().toString());
                        }
                    }
                })
                .setNegativeButton(isUpdated ? "Delete" : "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(isUpdated){
                            DeleteContact(contact,position);
                        }else {
                            dialogInterface.cancel();
                        }
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void DeleteContact(Contact contact, int position){
        contactArrayList.remove(position);
        db.deleteContact(contact);
        contactsAdapter.notifyDataSetChanged();
    }
    private void UpdateContact(String name, String email, int position){
        Contact contact = contactArrayList.get(position);
        contact.setEmail(email);
        contact.setName(name);
        db.updateContact(contact);
        contactArrayList.set(position,contact);
        contactsAdapter.notifyDataSetChanged();

    }
    private void CreateContact(String name, String email){
        long id = db.insertContact(name,email);
        Contact contact = db.getContact(id);
        if(contact != null){
            contactArrayList.add(0,contact);
            contactsAdapter.notifyDataSetChanged();
        }
    }
}
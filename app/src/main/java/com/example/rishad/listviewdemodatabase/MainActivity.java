package com.example.rishad.listviewdemodatabase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    private EditText nameEditText, phonenumberEditText,idEditText;
    private Button saveButton,showButton,updateButton,deleteButton;
    InfoDetails infoDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        //find the view
        nameEditText =(EditText) findViewById(R.id.nameEditTextId);
        phonenumberEditText =(EditText) findViewById(R.id.phoneEditTextId);
        idEditText =(EditText) findViewById(R.id.idEditTextId);
        saveButton =(Button) findViewById(R.id.saveButtonId);
        showButton =(Button) findViewById(R.id.showButtonId);
        updateButton = (Button) findViewById(R.id.updateButtonId);
        deleteButton =(Button) findViewById(R.id.deleteButtonId);

        // button listener
        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        // object create in info details
        infoDetails = new InfoDetails();
    }

    @Override
    public void onClick(View v) {
        // find the user input value
        String name = nameEditText.getText().toString();
        String phone = phonenumberEditText.getText().toString();
        String id = idEditText.getText().toString();

        infoDetails.setName(name);
        infoDetails.setPhoneNumber(phone);

        if(v.getId()==R.id.saveButtonId){
            // check null value
            if(name.equals("") && phone.equals("")){
                Toast.makeText(getApplicationContext(),"Enter All Data.",Toast.LENGTH_LONG).show();

            }else {
              Long rowId=  databaseHelper.save(infoDetails);

              if(rowId > -1){
                  Toast.makeText(getApplicationContext(),"Data is inserted Successfully.",Toast.LENGTH_SHORT).show();
                  nameEditText.setText("");
                  phonenumberEditText.setText("");

              }else {
                  Toast.makeText(getApplicationContext(),"Data is not inserted Successfully.",Toast.LENGTH_SHORT).show();
              }
            }

        } else if(v.getId()==R.id.showButtonId){

            Intent intent = new Intent(this,ListDataActivity.class);
            startActivity(intent);

        }else if(v.getId()==R.id.updateButtonId){
           boolean updateDate= databaseHelper.updateData(id,name,phone);

           if(updateDate==true){
               Toast.makeText(getApplicationContext(),"Update Successfull",Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(getApplicationContext(),"Update fail",Toast.LENGTH_SHORT).show();
           }


        }else if(v.getId()==R.id.deleteButtonId){

            int dltData=databaseHelper.deleteData(id);

            if(dltData<0){
                Toast.makeText(getApplicationContext(),"Data is not deleted",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Data  deleted",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

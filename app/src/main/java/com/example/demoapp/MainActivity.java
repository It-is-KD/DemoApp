package com.example.demoapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,editId;
    Button btnAddData,btnviewAll,btndelete,btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        editName=(EditText) findViewById(R.id.editText_name);
        editSurname=(EditText) findViewById(R.id.editText_surname);
        editMarks=(EditText) findViewById(R.id.editText_marks);
        btnAddData=(Button) findViewById(R.id.button_add);
        btnviewAll=(Button) findViewById(R.id.button_view);
        btnUpdate=(Button) findViewById(R.id.button_update);
        btndelete=(Button) findViewById(R.id.button_delete);
        editId=(EditText) findViewById(R.id.editText_id);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted=myDb.insertData(editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
                if(isInserted==true)
                {
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(), "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=myDb.getAllData();
                if(res.getCount()==0) {
                    //show message
                    showMessage("Error", "No Data Found");
                    return;
                }
                StringBuffer stringBuffer=new StringBuffer();
                while (res.moveToNext())
                {
                    stringBuffer.append("ID:"+res.getString(0)+"\n");
                    stringBuffer.append("Name:"+res.getString(1)+"\n");
                    stringBuffer.append("Surname:"+res.getString(2)+"\n");
                    stringBuffer.append("Marks:"+res.getString(3)+"\n\n");
                }
                showMessage("Data",stringBuffer.toString());
            }


        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows=myDb.deleteData(editId.getText().toString());
                if(deletedRows>0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=myDb.updateData(editId.getText().toString(),editName.getText().toString(), editSurname.getText().toString(),editMarks.getText().toString());

                if(isUpdate==true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
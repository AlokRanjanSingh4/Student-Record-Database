package com.example.sqlitedatabase;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText editTextID, editTextName, editTextEmail, editTextCC;
    Button buttonAdd, buttonDelete, buttonUpdate, buttonGetData, buttonViewAll, buttonDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        editTextID = findViewById(R.id.editText_id);
        editTextCC = findViewById(R.id.editText_CC);
        editTextEmail = findViewById(R.id.editText_email);
        editTextName = findViewById(R.id.editText_name);

        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        buttonGetData = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonViewAll = findViewById(R.id.button_viewAll);

        addData();
        getData();
        viewAllData();
        updateData();
        deleteData();
//        deleteAllData();

    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void addData() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean insertData = databaseHelper.insertData(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextCC.getText().toString());
                if (insertData) {
                    Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString();

                if (id.equals(String.valueOf(""))) {
                    editTextID.setError("Enter ID");
                    return;
                }

                Cursor cursor = databaseHelper.getData(id);
                String data = null;

                if (cursor.moveToNext()) {
                    data = "ID: " + cursor.getString(0) + "\n" +
                            "Name: " + cursor.getString(1) + "\n" +
                            "Email: " + cursor.getString(2) + "\n" +
                            "Course Count: " + cursor.getString(3);

                    showMessage("Info : ", data);
                }

                else {
                    showMessage("No Info ", "Found");
                }
            }
        });
    }

    private void viewAllData() {
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getAllData();

                if (cursor.getCount() == 0) {
                    showMessage("No Entry Yet ", "Show");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("ID: " + cursor.getString(0) + "\n");
                    stringBuffer.append("Name: " + cursor.getString(1) + "\n");
                    stringBuffer.append("Email: " + cursor.getString(2) + "\n");
                    stringBuffer.append("Course Count: " + cursor.getString(3) + "\n\n");
                }
                showMessage("All Data:", stringBuffer.toString());
            }
        });
    }

    private void updateData() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = databaseHelper.updateData(editTextID.getText().toString(),
                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextCC.getText().toString());

                String id = editTextID.getText().toString();

                if (id.equals(String.valueOf(""))) {
                    editTextID.setError("Enter the ID");
                    return;
                }

                if (isUpdate) {
                    Toast.makeText(MainActivity.this, " Update Successfully ", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(MainActivity.this, " Something Went Wrong ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteData() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer delete = databaseHelper.deleteData(editTextID.getText().toString());

                if (delete > 0) {
                    Toast.makeText(MainActivity.this, " Delete Successfully ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, " Something Went Wrong ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//
//    private void deleteAllData() {
//        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor delete = databaseHelper.deleteAllData();
//                Toast.makeText(MainActivity.this, " All Data Deleted Successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}

package com.example.panino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactsPage extends AppCompatActivity {

    EditText e1, e2, e3;

    Button save;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_page);

        e1=findViewById(R.id.et_text);
        e2=findViewById(R.id.et_text1);
        e3=findViewById(R.id.et_text2);
        save=findViewById(R.id.saveid);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num1=e1.getText().toString();
                String num2=e3.getText().toString();
                String num3=e2.getText().toString();

                if(num1.isEmpty() && num2.isEmpty() && num3.isEmpty()) {
                    Toast.makeText(ContactsPage.this, "Fields are empty!!", Toast.LENGTH_SHORT).show();
                }
                else if(num1.isEmpty()) {
                    e1.setError("Please enter emergency contact number");
                    e1.requestFocus();
                }
                else if (num2.isEmpty()) {
                    e3.setError("Please enter emergency contact number");
                    e3.requestFocus();
                }
                else if (num3.isEmpty()) {
                    e2.setError("Please enter emergency contact number");
                    e2.requestFocus();
                }
                else {
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }



                sharedpreferences = getSharedPreferences("num", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString("n1", num1);
                editor.putString("n2", num2);
                editor.putString("n3", num3);
                editor.apply();

            }
        });


    }
}
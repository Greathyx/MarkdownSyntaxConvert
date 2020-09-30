package com.example.markdownsyntaxconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button formatButton;
    private EditText inputTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formatButton = (Button) findViewById(R.id.button);
        inputTextField = (EditText) findViewById(R.id.editTextTextMultiLine);

        formatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("input_text",inputTextField.getText().toString());
                startActivity(intent);
            }
        });
    }
}
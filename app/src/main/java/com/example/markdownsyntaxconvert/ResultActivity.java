package com.example.markdownsyntaxconvert;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private Button backButton;
    private TextView resultLabel;
    private TextView table_unit00;
    private TextView table_unit01;
    private TextView table_unit02;
    private TextView table_unit10;
    private TextView table_unit11;
    private TextView table_unit12;
    private TextView table_unit20;
    private TextView table_unit21;
    private TextView table_unit22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        backButton = (Button) findViewById(R.id.button3);
        resultLabel = (TextView) findViewById(R.id.textView3);
        table_unit00 = (TextView) findViewById(R.id.textView00);
        table_unit01 = (TextView) findViewById(R.id.textView01);
        table_unit02 = (TextView) findViewById(R.id.textView02);
        table_unit10 = (TextView) findViewById(R.id.textView10);
        table_unit11 = (TextView) findViewById(R.id.textView11);
        table_unit12 = (TextView) findViewById(R.id.textView12);
        table_unit20 = (TextView) findViewById(R.id.textView20);
        table_unit21 = (TextView) findViewById(R.id.textView21);
        table_unit22 = (TextView) findViewById(R.id.textView22);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // push out the last activity (back to the previous view)
            }
        });

        Intent intent = getIntent();
        String input_text = intent.getStringExtra("input_text");
        if (input_text != null)
            this.formatStr(input_text);
    }

    private void formatStr(String input) {
        String[] input_arr = input.trim().split(" ");

        for (int i = 0; i < input_arr.length; i++) {
            String output_unit = "";
            boolean bold = false;
            boolean italics = false;

            // the case of bold
            if (input_arr[i].startsWith("**") && input_arr[i].endsWith("**")) {
                bold = true;
                output_unit = input_arr[i].substring(2, input_arr[i].length() - 2);
                resultLabel.setVisibility(View.VISIBLE);
            }
            // the case of italics
            else if (input_arr[i].startsWith("_") && input_arr[i].endsWith("_")) {
                italics = true;
                output_unit = input_arr[i].substring(1, input_arr[i].length() - 1);
                resultLabel.setVisibility(View.VISIBLE);
            }
            // the case of location alignment
            else if (input_arr[i].contains("\n")) {
                this.handleAlignment(input_arr[i]);
            }
            else {
                output_unit = input_arr[i];
                resultLabel.setVisibility(View.VISIBLE);
            }

            // give back the space to the word
            if (i != input_arr.length - 1)
                output_unit += " ";

            SpannableString span = new SpannableString(output_unit);

            if (bold) {
                span.setSpan(new StyleSpan(Typeface.BOLD), 0, output_unit.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                resultLabel.append(span); // put text
            }
            else if (italics) {
                span.setSpan(new StyleSpan(Typeface.ITALIC), 0, output_unit.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                resultLabel.append(span); // put text
            }
            // handle the case of <br>
            else if (output_unit.contains("<br>")) {
                String[] output_arr = output_unit.split("<br>");
                StringBuilder sb = new StringBuilder();

                for (int j = 0; j < output_arr.length; j++) {
                    if (j == 0)
                        sb.append(output_arr[j]);
                    else
                        sb.append("\n").append(output_arr[j]);
                }
                output_unit = sb.toString();
                resultLabel.append(output_unit); // put text
            }
            else
                resultLabel.append(output_unit); // put text
        }
    }

    private void handleAlignment(String input) {
        String[] table_content_arr = input.split("\n");
        for (String str : table_content_arr) {
            if (str.contains(">")) {
                String[] coordinate_and_content = str.split(">");
                String text  = coordinate_and_content[1];
                String[] coordinate = coordinate_and_content[0].substring(1).split(",");

                if (Double.parseDouble(coordinate[0]) == 0) {
                    if (Double.parseDouble(coordinate[1]) == 0) {
//                    table_unit00.setVisibility(View.VISIBLE);
                        table_unit00.setText(text);
                    }
                    else if (Double.parseDouble(coordinate[1]) == 0.5) {
//                    table_unit01.setVisibility(View.VISIBLE);
                        table_unit10.setText(text);
                    }
                    else if (Double.parseDouble(coordinate[1]) == 1) {
//                    table_unit02.setVisibility(View.VISIBLE);
                        table_unit20.setText(text);
                    }
                }
                else if (Double.parseDouble(coordinate[0]) == 0.5) {
                    if (Double.parseDouble(coordinate[1]) == 0) {
//                    table_unit10.setVisibility(View.VISIBLE);
                        table_unit01.setText(text);
                    }
                    else if (Double.parseDouble(coordinate[1]) == 0.5) {
//                    table_unit11.setVisibility(View.VISIBLE);
                        table_unit11.setText(text);
                    }
                    else if (Double.parseDouble(coordinate[1]) == 1) {
//                    table_unit12.setVisibility(View.VISIBLE);
                        table_unit21.setText(text);
                    }
                }
                else if (Double.parseDouble(coordinate[0]) == 1) {
                    if (Double.parseDouble(coordinate[1]) == 0) {
//                    table_unit20.setVisibility(View.VISIBLE);
                        table_unit02.setText(text);
                    }
                    else if (Double.parseDouble(coordinate[1]) == 0.5) {
//                    table_unit21.setVisibility(View.VISIBLE);
                        table_unit12.setText(text);
                    }
                    else if (Double.parseDouble(coordinate[1]) == 1) {
//                    table_unit22.setVisibility(View.VISIBLE);
                        table_unit22.setText(text);
                    }
                }
            }
            else {
                this.formatStr(str);
            }
        }
    }
}

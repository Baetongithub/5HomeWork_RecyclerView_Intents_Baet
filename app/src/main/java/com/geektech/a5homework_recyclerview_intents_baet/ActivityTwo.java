package com.geektech.a5homework_recyclerview_intents_baet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

    private TextView textView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private EditText etText;
    public static String KEY2 = "key2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        textView = findViewById(R.id.txtViewActivityTwo);

        textView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityTwo.this,
                        R.style.Theme_AppCompat_Dialog_Alert, dateSetListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                Log.d("ActivityTwo", "onDateSet: mm/dd/yyy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                textView.setText(date);

            }
        };

        init();
        Intent intent = getIntent();
        if (intent != null) {
            Title title = (Title) intent.getSerializableExtra(MainActivity.KEY);
            if (title != null) {
                etText.setText(title.name);

            }
        }
    }

    private void init() {
        etText = findViewById(R.id.etText);
        Button bttnResult = findViewById(R.id.bttnResult);
        bttnResult.setOnClickListener(v -> {

            String date = textView.getText().toString();
            Intent setDateIntent = new Intent(ActivityTwo.this, MainActivity.class);
            setDateIntent.putExtra(Intent.EXTRA_TEXT, date);
            startActivity(setDateIntent);

            Intent intent = new Intent();
            Title title = new Title(etText.getText().toString());
            intent.putExtra(KEY2, title);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
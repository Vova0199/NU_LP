package com.nulp.vp.labs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText et_name, et_surname, et_email, et_phone, et_password, et_password_conf;
    private Button btn_submit;
    private Pattern p_name, p_email, p_phone, p_password;
    private Boolean name, surname, email, phone, password, password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        validation();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = validate_field_to_save(et_name, p_name);
                surname = validate_field_to_save(et_surname, p_name);
                email = validate_field_to_save(et_email, p_email);
                phone = validate_field_to_save(et_phone, p_phone);
                password = validate_field_to_save(et_password, p_password);
                password_confirm = validate_field_to_save(et_password_conf, p_password);

                if (name && surname && email && phone && password && password_confirm) {
                    Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please fill out the correct fields", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validate_field_to_save(EditText et_field, Pattern regex) {
        return et_field.getText().toString().matches(String.valueOf(regex));
    }

    private void validation() {
        p_name = Pattern.compile("[A-Z][a-z ,.'-]+$");
        p_email = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        p_phone = Pattern.compile("\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})");
        p_password = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}$");


        validate_field(et_name, p_name);
        validate_field(et_surname, p_name);
        validate_field(et_email, p_email);
        validate_field(et_phone, p_phone);
        validate_field(et_password, p_password);
        validate_field(et_password_conf, p_password);
    }


    private void validate_field(final EditText et_field, final Pattern pattern) {
        et_field.addTextChangedListener(new TextWatcher() {

            private CharSequence mText;

            private boolean isValid(CharSequence s) {
                return pattern.matcher(s).matches();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValid(s)) {
                    et_field.setBackgroundResource(R.drawable.norm);

                } else et_field.setBackgroundResource(R.drawable.error);

                if (!et_password.getText().toString().equals(et_password_conf.getText().toString()) || et_password_conf.getText().toString().equals("")) {
                    et_password_conf.setBackgroundResource(R.drawable.error);
                    et_password.setBackgroundResource(R.drawable.error);
                } else {
                    et_password_conf.setBackgroundResource(R.drawable.norm);
                    et_password.setBackgroundResource(R.drawable.norm);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        et_name = findViewById(R.id.et_name);
        et_surname = findViewById(R.id.et_last_name);
        btn_submit = findViewById(R.id.btn_submit);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_password_conf = findViewById(R.id.et_password_confr);
    }
}

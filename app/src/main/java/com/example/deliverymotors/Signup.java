package com.example.deliverymotors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    CircleImageView img_profile;
    EditText edit_name, edit_email, edit_phone, edit_pass, edit_ssn, edit_address;
    Button but_uploadImgId, but_signup;
    TextView txt_login;
    private Bitmap bitmap, profbitmap;
    int num;
    Intent intent;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        img_profile = findViewById(R.id.img_profile);
        edit_name = findViewById(R.id.edit_name);
        edit_email = findViewById(R.id.edit_email);
        edit_phone = findViewById(R.id.edit_phone);
        edit_pass = findViewById(R.id.edit_pass);
        edit_ssn = findViewById(R.id.edit_ssn);
        edit_address = findViewById(R.id.edit_address);
        but_uploadImgId = findViewById(R.id.but_uploadImgId);
        but_signup = findViewById(R.id.but_signup);
        txt_login = findViewById(R.id.txt_login);

        intent=getIntent();
        userType=intent.getStringExtra("type");


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Signup.this, Login.class);
                i.putExtra("type",userType);
                startActivity(i);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 0;
                chooseFile();
            }
        });
        but_uploadImgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = 1;
                chooseFile();
            }
        });
        but_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_name.getText().toString().trim();
                String email = edit_email.getText().toString().trim();
                String phone = edit_phone.getText().toString().trim();
                String pass = edit_pass.getText().toString().trim();
                String address = edit_address.getText().toString().trim();
                String ssn = edit_ssn.getText().toString().trim();
                SignUp(name,phone,email,pass,address,ssn);

            }
        });
    }

    private void SignUp(final String name, final String phone, final String mail, final String pass, final String address, final String ssn) {

        if (name.isEmpty()) {
            edit_name.setError("Name is required");
            edit_name.requestFocus();
        }
        if (phone.isEmpty()) {
            edit_phone.setError("phone number is required");
            edit_phone.requestFocus();
        }

        if (mail.isEmpty()) {
            edit_email.setError("Email is required ");
            edit_email.requestFocus();
        }
        if (!mail.contains("@")) {
            edit_email.setError("it's not Email ");
            edit_email.requestFocus();
        }

        if (pass.isEmpty()) {
            edit_pass.setError("Password is required");
            edit_pass.requestFocus();
        }
        if (pass.length() < 8) {
            edit_pass.setError("Password must be at least 8 characters");
            edit_pass.requestFocus();
        }
        if (address.isEmpty()) {
            edit_address.setError("Address is required");
            edit_address.requestFocus();
        }
        if (ssn.isEmpty()) {
            edit_ssn.setError("National Id is required");
            edit_ssn.requestFocus();
        }
        if (ssn.length() != 14) {
            edit_ssn.setError("National should be 14 numbers");
            edit_ssn.requestFocus();
        }
        if (profbitmap == null) {
            img_profile.requestFocus();
            Toast.makeText(this, "Profile Image is required", Toast.LENGTH_SHORT).show();
        }
        if (bitmap == null) {
            but_uploadImgId.requestFocus();
            Toast.makeText(this, "National Id Image is required", Toast.LENGTH_SHORT).show();
        }
        if (name.isEmpty()||phone.isEmpty()||mail.isEmpty()||!mail.contains("@")||pass.isEmpty()||pass.length() < 8||address.isEmpty()||ssn.isEmpty()||ssn.length() != 14||profbitmap == null||bitmap == null){
            return;
        }

        Intent i=new Intent(Signup.this,Verificationcode.class);
        i.putExtra("name",name);
        i.putExtra("phone_number",phone);
        i.putExtra("email",mail);
        i.putExtra("password",pass);
        i.putExtra("address",address);
        i.putExtra("ssn",ssn);
        i.putExtra("type",userType);
      //  i.putExtra("profileImg",profbitmap);
      //  i.putExtra("ssnImg",bitmap);
        startActivity(i);

    }


    //Get Image
    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                if (num == 0) {
                    profbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    img_profile.setImageBitmap(profbitmap);
                } else if (num == 1) {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

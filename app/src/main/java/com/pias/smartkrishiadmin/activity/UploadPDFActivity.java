package com.pias.smartkrishiadmin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pias.smartkrishiadmin.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadPDFActivity extends AppCompatActivity {
    private ImageView AddStudentImage;
    private EditText AddStudentName, AddStudentRoll, AddStudentResult, studentFatherName;
    private Spinner AddStudentCategory;
    private Button AddStudentBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;

    private String category;
    private ProgressDialog progressDialog;

    private String name, roll, result, father;

    private String downloadUrl = "";

    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdfactivity);

        /*student image view*/
        AddStudentImage = findViewById(R.id.AddStudentImage);

        /*student information*/
        AddStudentName = findViewById(R.id.AddStudentName);
        AddStudentRoll = findViewById(R.id.AddStudentRoll);
        AddStudentResult = findViewById(R.id.AddStudentResult);
        studentFatherName = findViewById(R.id.studentFatherName);

        /*button & category*/
        AddStudentBtn = findViewById(R.id.AddStudentBtn);
        AddStudentCategory = findViewById(R.id.AddStudentCategory);

        progressDialog = new ProgressDialog(this);

        // firebase
        reference = FirebaseDatabase.getInstance().getReference().child("AgricultureInfo");
        storageReference = FirebaseStorage.getInstance().getReference();


        String[] items = new String[]{"Select Category", "ফল-মূল চাষ","আলু চাষাবাদ","ধান বিষয়ক", "ছাদে বা টবে চাষ", "শাক-সবজি চাষ", "রোগ বালাই ও প্রতিকার", "ঔষধি গাছ", "ফুল চাষ", "সমন্বিত চাষ", "সম্ভাবনাময় ও অন্যান্য চাষ", "সার বিষয়ক তথ্য"};
        AddStudentCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
        // eikhane amader change ache AddStudentCategory


        AddStudentCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // eikhane change ache AddStudentCategory
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = AddStudentCategory.getSelectedItem().toString(); // eikhane change ache AddStudentCategory
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        AddStudentImage.setOnClickListener(new View.OnClickListener() { // eikhane Change ache AddStudentImage hobe
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        AddStudentBtn.setOnClickListener(new View.OnClickListener() { // eikhane change ache AddStudentBtn
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }


    private void checkValidation() {
        name = AddStudentName.getText().toString().trim();
        roll = AddStudentRoll.getText().toString().trim();
        father = studentFatherName.getText().toString().trim();
        result = AddStudentResult.getText().toString().trim();

        if (name.isEmpty()) {
            AddStudentName.setError("Empty");
            AddStudentName.requestFocus();
        } else if (roll.isEmpty()) {
            AddStudentRoll.setError("Empty");
            AddStudentRoll.requestFocus();
        } else if (result.isEmpty()) {
            AddStudentResult.setError("Empty");
            AddStudentResult.requestFocus();
        } else if (category.equals("Select Category")) {
            Toast.makeText(this, "Please provides teacher category", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            insertData();
        } else {
            progressDialog.setMessage("Uploading Details");
            progressDialog.show();
            uploadImage();
        }

    }


    // #######################################################################################################
    // ##################################### Upload image Code ###############################################
    // #####################################################################################################

    private void uploadImage() {

        progressDialog.setMessage("Uploading");
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] finalimage = baos.toByteArray();


        final StorageReference filepath;
        filepath = storageReference.child("AgricultureInfo").child(finalimage + ".png");
        final UploadTask uploadTask = filepath.putBytes(finalimage);
        uploadTask.addOnCompleteListener(UploadPDFActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() { // change AddStudent
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(UploadPDFActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show(); // change AddStudent
                }
            }
        });
    }


    // #######################################################################################################
    // ##################################### Insert Data Code ###############################################
    // #####################################################################################################

    private void insertData() {
        // amader eikhane edittext theke data amra database e soriye dissi
        dbRef = reference.child(category); // dont change this
        final String uniquekey = dbRef.push().getKey();

        // #########################
        // change korte hobe eikhane
        AgricultureData studentData = new AgricultureData(name, roll, result, father, downloadUrl, uniquekey);
        dbRef.child(uniquekey).setValue(studentData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(UploadPDFActivity.this, "সফল ভাবে আপলোড হয়েছে", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadPDFActivity.this, "আপলোড সফল হয়নি", Toast.LENGTH_SHORT).show();
            }
        });

    }


    // #######################################################################################################
    // ##################################### Open Gallay Code ###############################################
    // #####################################################################################################
    private void openGallery() {

        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            AddStudentImage.setImageBitmap(bitmap);
        }

    }
}

package com.pias.smartkrishiadmin.AllCultivateAndFarming.fish;

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
import com.pias.smartkrishiadmin.AllCultivateAndFarming.agricultureinformation.AgricultureData;
import com.pias.smartkrishiadmin.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FishPDFActivity extends AppCompatActivity {
    private ImageView AddAgricultureImage;
    private EditText nameET, pdfET;
    private Spinner AddCategory;
    private Button AddBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;
    private ProgressDialog progressDialog;
    private String name, pdf;
    private String downloadUrl = "";
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_pdfactivity);

        /*title*/
        getSupportActionBar().setTitle("মাছ চাষের তথ্য আপলোড করুন");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*init views*/
        AddAgricultureImage= findViewById(R.id.AddAgricultureImage);
        nameET = findViewById(R.id.nameET);
        pdfET = findViewById(R.id.pdfET);
        AddBtn = findViewById(R.id.AddBtn);
        AddCategory = findViewById(R.id.AddCategory);
        progressDialog = new ProgressDialog(this);

        /*firebase database path*/
        reference = FirebaseDatabase.getInstance().getReference().child("Fish");
        storageReference = FirebaseStorage.getInstance().getReference();

        /*category name*/
        String[] items = new String[]{"Select Category","দেশীয় মাছ চাষ", "একক ও মিশ্র মাছ চাষ", "পোনা, খাদ্য ও পুকুর ব্যবস্থাপনা", "চিংড়ি চাষ", "মাছ চাষের র্পুনাঙ্গ ব্যবস্থাপত্র", "মাছের রোগব্যাধি ও প্রতিকার", "হ্যাচারী, পোনা ও প্রজনন তথ্য"};
        AddCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
        AddCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = AddCategory.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        AddAgricultureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }
    private void checkValidation() {
        /**/
        name = nameET.getText().toString().trim();
        pdf = pdfET.getText().toString().trim();

        if (name.isEmpty()) {
            nameET.setError("পূরণ করুন");
            nameET.requestFocus();
        } else if (pdf.isEmpty()) {
            pdfET.setError("পূরণ করুন");
            pdfET.requestFocus();
        } else if (category.equals("Select Category")) {
            Toast.makeText(this, "ক্যাটেগরি সিলেক্ট করুন", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            insertData();
        } else {
            progressDialog.setMessage("তথ্য আপলোড হচ্ছে");
            progressDialog.show();
            uploadImage();
        }

    }

    // #######################################################################################################
    // ##################################### Upload image Code ###############################################
    // #####################################################################################################

    private void uploadImage() {

        progressDialog.setMessage("আপলোডিং");
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] finalimage = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("Fish").child(finalimage + ".png");
        final UploadTask uploadTask = filepath.putBytes(finalimage);
        uploadTask.addOnCompleteListener(com.pias.smartkrishiadmin.AllCultivateAndFarming.fish.FishPDFActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() { // change AddStudent
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
                    Toast.makeText(com.pias.smartkrishiadmin.AllCultivateAndFarming.fish.FishPDFActivity.this, "আপলোড সফল হয়নি", Toast.LENGTH_SHORT).show(); // change AddStudent
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
        AgricultureData studentData = new AgricultureData(name, pdf, downloadUrl, uniquekey);
        dbRef.child(uniquekey).setValue(studentData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(com.pias.smartkrishiadmin.AllCultivateAndFarming.fish.FishPDFActivity.this, "সফল ভাবে আপলোড হয়েছে", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(com.pias.smartkrishiadmin.AllCultivateAndFarming.fish.FishPDFActivity.this, "আপলোড সফল হয়নি", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // #######################################################################################################
    // ##################################### Open Gallery Code ###############################################
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
            AddAgricultureImage.setImageBitmap(bitmap);
            Toast.makeText(this, "ছবি যুক্ত হয়েছে", Toast.LENGTH_SHORT).show();
        }
    }
}


package com.example.sam.womenintechconnect;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.xml.transform.Result;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class FileFragment extends Fragment {
    Button upload;
    Button select;
    TextView selectFile;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Activity context;
    Uri pdfUri; //url meant for local storage
    ProgressDialog progressDialog;
    private Uri filepath;
    private int IMAGE_PICK_REQUEST2=123;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_files,container,false);

        upload=(Button) view.findViewById(R.id.btn_upload);
        select=(Button) view.findViewById(R.id.btn_select);
        selectFile=(TextView) view.findViewById(R.id.tv_selectedfile);
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        context=getActivity();

        //select file

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mimeTypes =
                        {"application/pdf",
                                };

                Intent document = new Intent(Intent.ACTION_GET_CONTENT);
                document.addCategory(Intent.CATEGORY_OPENABLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    document.setType(mimeTypes[0]);
                    document.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                } else {
                    String mimeTypesStr = "";
                    for (String mimeType : mimeTypes) {
                        mimeTypesStr += mimeType + "|";
                    }
                    document.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                }
                startActivityForResult(Intent.createChooser(document,"ChooseFile"), IMAGE_PICK_REQUEST2);
            }
        });
        //select file to be uploaded

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Selected file",Toast.LENGTH_SHORT).show();




            }
        });
//upload file
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Upload file",Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_PICK_REQUEST2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath=data.getData();
            Toast.makeText(getContext(),""+filepath,Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(getContext(),"no file",Toast.LENGTH_LONG).show();
        }

    }
}

package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import com.google.zxing.Result;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView myScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myScanner = new ZXingScannerView(this);
        setContentView(myScanner);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 101);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        myScanner.setResultHandler(this);
        myScanner.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        myScanner.stopCamera();
    }
    @Override
    public void handleResult(Result result) {
        myScanner.stopCameraPreview();
        Intent go = new Intent(this, Product.class);
        go.putExtra("result", result.getText());
        startActivity(go);
    }
}

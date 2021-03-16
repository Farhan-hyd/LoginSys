package com.example.mfrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.MacAddress;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText UserID,Password;
    Button Register;
    private Database database;
    FirebaseDatabase fdb;
    DatabaseReference ref;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        UserID = (EditText) findViewById(R.id.UserID);
        Password = (EditText) findViewById(R.id.Password);
        Register = (Button) findViewById(R.id.Register);
        Register.setOnClickListener(this);

        database = new Database();
        fdb = FirebaseDatabase.getInstance();
        ref = fdb.getReference().child("Database");

    }

    public String getMacAddress(){
        try{
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            String stringMac = "";
            for(NetworkInterface networkInterface : networkInterfaceList)
            {
                if(networkInterface.getName().equalsIgnoreCase("wlon0"));
                {
                    for(int i = 0 ;i <networkInterface.getHardwareAddress().length; i++){
                        String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i]& 0xFF);
                        if(stringMacByte.length() == 1)
                        {
                            stringMacByte = "0" +stringMacByte;
                        }
                        stringMac = stringMac + stringMacByte.toUpperCase() + ":";
                    }
                    break;
                }
            }
            return stringMac;
        }catch (SocketException e) {
            e.printStackTrace();
        }
        return  "0";
    }

    private String MACaddress = getMacAddress() ;

//    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//    WifiInfo wInfo = wifiManager.getConnectionInfo();
//    String MACaddress = wInfo.getMacAddress();

    @Override
    public void onClick(View view) {
        database.setUserID(UserID.getText().toString());
        database.setPassword(Password.getText().toString());
        database.setMACaddress(MACaddress);

        if(UserID != null){
            if(Password == null){
                Toast.makeText(SignUp.this,"UserId is null",Toast.LENGTH_LONG);
                finish();
            }else{
        ref.child(database.getUserID()).setValue(database).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(SignUp.this,"SignUp Sucessfull", Toast.LENGTH_LONG).show();
                Intent login = new Intent(SignUp.this,MainActivity.class);
                startActivity(login);
                finish();
            }else{
                Toast.makeText(SignUp.this,"Signup Unsucessfull", Toast.LENGTH_LONG).show();
            }
        });}}else{
            Toast.makeText(SignUp.this,"UserId is null",Toast.LENGTH_LONG);
            finish();
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        this.finish();
    }

}
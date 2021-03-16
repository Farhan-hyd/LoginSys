package com.example.mfrid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.MacAddress;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText UserID,Password;
    Button Login,Register;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserID = (EditText) findViewById(R.id.UserID);
        Password = (EditText) findViewById(R.id.Password);
        Login = (Button) findViewById(R.id.Login);
        Login.setOnClickListener(this::btnLoginClick);

        Register = (Button) findViewById(R.id.Register);
        Register.setOnClickListener(this::btnRegister_Click);

        ref = FirebaseDatabase.getInstance().getReference().child("Database");

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

//    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//    WifiInfo wInfo = wifiManager.getConnectionInfo();
//    String MACaddress = wInfo.getMacAddress();

    String MACaddress = getMacAddress() ;

    public void btnLoginClick(View view){
        String UID = UserID.getText().toString();
        String PASS = Password.getText().toString();

        if(ref.child(UID) != null){
        ref.child(UID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Database database = snapshot.getValue(Database.class);
                if(PASS.equals(database.getPassword())){
                    if(MACaddress.equals(database.getMACaddress())){
                        Toast.makeText(MainActivity.this,"login Successful",Toast.LENGTH_LONG).show();

                        Intent start = new Intent(MainActivity.this,start.class);
                        startActivity(start);
                        finish();

                    }else{
                        Toast.makeText(MainActivity.this,"Unknown Device",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Invalid User",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });}else{
            Intent Login = new Intent(MainActivity.this,SignUp.class);
            startActivity(Login);
            finish();
            Toast.makeText(MainActivity.this,"User doesn't Exist",Toast.LENGTH_SHORT).show();
        }
    }

    public void btnRegister_Click(View view){
        Intent signUp = new Intent(MainActivity.this,SignUp.class);
        startActivity(signUp);
        finish();
    }

}
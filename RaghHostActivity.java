package com.example.Grozziie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RaghHostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView quantityProductPage, quantityProductPage_speed;
    SeekBar seekBar;
    String value_spinner;

    TextView progressbarsechk;
    TextView connectedornot;
    String geeet;

    /////bitmap data
    Uri imageuri;
    int flag = 0;
    BluetoothSocket m5ocket;
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    ImageView imageposit;

    Button printimageA;

    TextView printtimer;
    Spinner papertype;
    String color_detect;
    String selectcategory;
    String wight;
    String height;
    //connected or not
    FirebaseFirestore firebaseFirestore;
    int flag1 = 0;


    TextView macaddress, bluename111;
    RelativeLayout printcommand;
    public static Bitmap bitmapdataMe;
    public static String myheight;
    public static String mywidth;
    public static String myprintercategory;
    String deviceId;
    String bluetoothName;
    UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile, username;

    UserSession2 scason2;
    private HashMap<String, String> user2;
    private String name2, email2, photo2, mobile2, username2;
    private static final int REQUEST_ENABLE_BT = 1;

    private static final int REQUEST_LOCATION_PERMISSION = 2;

    // Create a BroadcastReceiver for ACTION_FOUND
// Create a BroadcastReceiver for ACTION_FOUND

    //for bluetooth device

    BluetoothAdapter bluetoothAdapter;
    private BluetoothDeviceAdapter deviceAdapter;
    private ListView listView;
    private BroadcastReceiver discoveryReceiver;
    int REQUEST_BLUETOOTH_SCAN_PERMISSION = 4;
    BottomSheetDialog bottomSheetDialog11;
    TextView macdetails;
    EditText edittextquantityProductPage;
    int totalPrintCommands2 = 0;
    private static final int REQUEST_PERMISSIONS = 103;
    String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN, // Use the correct permission string
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT
    };

    List<String> permissionsToRequest = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragh_host);
        macdetails = findViewById(R.id.macdetails);
        macaddresssname=findViewById(R.id.macaddresssname);
        checkingallpermission();
        // innit();
        edittextquantityProductPage=findViewById(R.id.edittextquantityProductPage);
        bottomSheetDialog11 = new BottomSheetDialog(RaghHostActivity.this);
        bottomSheetDialog11.setContentView(R.layout.secondd);

        connectedornot = findViewById(R.id.connectedornot);
        imageposit = findViewById(R.id.imageposit);
        session = new UserSession(RaghHostActivity.this);
        scason2 = new UserSession2(RaghHostActivity.this);
        //default value

        getValues();
        // Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
        if (mobile == null || TextUtils.isEmpty(mobile)) {
            String sessionname = "THT";
            String sessionmobile = "01:01:01:01:01";
            String sessionphoto = "1";
            String sessionemail = "14";
            ZoneId z = ZoneId.of("Asia/Dhaka");
            LocalDate today = LocalDate.now(z);
            String sessionusername = "" + today;
            session.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto, sessionusername);
        } else {
            //  Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
        }


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        deviceId = "abc@gmail.com";
        macaddress = findViewById(R.id.macaddress);
        bluename111 = findViewById(R.id.bluename111);
        bluename111.setText("" + mobile);
        printcommand = findViewById(R.id.printcommand);

        //  intttt();


        try {
            Intent intent = getIntent();
            myheight = intent.getStringExtra("myheight");
            mywidth = intent.getStringExtra("mywidth");
            myprintercategory = "esc";
            byte[] bitmapData = getIntent().getByteArrayExtra("bitmapData");
            if (bitmapData != null) {
                bitmapdataMe = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
                imageposit.setImageBitmap(bitmapdataMe);
//            // Use the bitmap as needed

            }
            //  Toast.makeText(this, ""+myheight+""+mywidth+""+bitmapdataMe, Toast.LENGTH_SHORT).show();
            selectcategory = intent.getStringExtra("myCategory");
            wight = intent.getStringExtra("wigth");
            height = intent.getStringExtra("height");
            if (TextUtils.isEmpty(selectcategory)) {
                selectcategory = "esc";
            } else {
                selectcategory = selectcategory;

            }
            //width
            if (TextUtils.isEmpty(wight)) {
                wight = "384";
            } else {
                wight = wight;

            }
            //height
            if (TextUtils.isEmpty(height)) {
                height = "384";
            } else {
                height = height;

            }

        } catch (Exception e) {

        }


        papertype = findViewById(R.id.papertype);
        papertype.setOnItemSelectedListener(this);
        quantityProductPage_speed = findViewById(R.id.quantityProductPage_speed);

        String[] textSizes = getResources().getStringArray(R.array.colorplace);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_row, listofconstract);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        papertype.setAdapter(adapter);

        printtimer = findViewById(R.id.printtimer);
        quantityProductPage = findViewById(R.id.quantityProductPage);
        progressbarsechk = findViewById(R.id.progressbarsechk);
        quantityProductPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityProductPage.setVisibility(View.GONE);
                edittextquantityProductPage.setVisibility(View.VISIBLE);
                edittextquantityProductPage.setText(""+quantityProductPage.getText().toString());
            }
        });

        seekBar = findViewById(R.id.seekBar);

        RelativeLayout closedialouge = findViewById(R.id.closedialouge);
        closedialouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                try {

                    if (isConnected==true)
                    {
                        m5ocket.close();
                        isConnected = false;
                    }
                    else{

                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });
        progressbarsechk = findViewById(R.id.progressbarsechk);
        progressbarsechk.setText("14");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progressbarsechk.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //   Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
        //for all
        getValues();
        if ((TextUtils.isEmpty(email) || email.toString() == null) && (TextUtils.isEmpty(photo) || photo.toString() == null)) {
            seekBar.setProgress(14);
        } else {
            seekBar.setProgress(Integer.parseInt(email));
            progressbarsechk.setText("" + email);
            quantityProductPage_speed.setText(photo);
        }


        //for devices


        RelativeLayout closedialouge1 = (RelativeLayout) bottomSheetDialog11.findViewById(R.id.closedialouge__1);
        deviceAdapter = new BluetoothDeviceAdapter(this, new ArrayList<>());
        listView = (ListView) bottomSheetDialog11.findViewById(R.id.list_view);
        listView.setAdapter(deviceAdapter);

//new code
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN},
                    REQUEST_CODE_PERMISSIONS);
        } else {
            checkdeviceandadd();
        }

        closedialouge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog11.dismiss();
            }
        });


        RelativeLayout searchagain = (RelativeLayout) bottomSheetDialog11.findViewById(R.id.searchagain);
        searchagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bluetoothAdapter.isEnabled()) {
                    // Clear the current list of devices
                    deviceAdapter.clear();

                    // Register a BroadcastReceiver to receive the Bluetooth device discovery results
                    BroadcastReceiver receiver = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                                if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                                    if (device.getName() != null && device.getAddress()!=null) {
                                        boolean isExsist = false;
                                        String deviceAddress = device.getAddress();
                                        for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                            BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                            if (existingDevice != null && existingDevice.getAddress() != null &&
                                                    existingDevice.getAddress().equals(deviceAddress)) {
                                                isExsist = true;
                                                break;
                                            }
                                        }
                                        if (!isExsist)
                                        {
                                            deviceAdapter.add(device);
                                            deviceAdapter.notifyDataSetChanged();
                                            //Toast.makeText(context, ""+device, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                                else {
                                    if (device.getName() != null && device.getAddress()!=null) {
                                        boolean isExsist = false;
                                        String deviceAddress = device.getAddress();
                                        for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                            BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                            if (existingDevice != null && existingDevice.getAddress() != null &&
                                                    existingDevice.getAddress().equals(deviceAddress)) {
                                                isExsist = true;
                                                break;
                                            }
                                        }
                                        if (!isExsist)
                                        {
                                            deviceAdapter.add(device);
                                            deviceAdapter.notifyDataSetChanged();
                                            //Toast.makeText(context, ""+device, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }

                            }
                        }
                    };

                    // Register the BroadcastReceiver for the ACTION_FOUND intent

                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(receiver, filter);

                    // Start device discovery
                    bluetoothAdapter.startDiscovery();
                } else {
                    Toast.makeText(RaghHostActivity.this, "Bluetooth is not enabled", Toast.LENGTH_SHORT).show();
                }

                Dialog mDialouge = new Dialog(RaghHostActivity.this);
                mDialouge.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialouge.setContentView(R.layout.initilizeallinformation);
                mDialouge.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  if(listView.le)

                        mDialouge.dismiss();
                        int itemCount = deviceAdapter.getCount();
                        Toasty.success(getApplicationContext(), "Done", Toasty.LENGTH_SHORT, true).show();


                    }
                }, 3500);


                mDialouge.create();
                ;
                mDialouge.show();
            }
        });

        // Initialize Bluetooth adapter and device adapter

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }




        ///////
        RelativeLayout relagoo = findViewById(R.id.relagoo);
        relagoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                    }
                    else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION1);
                    }
                    else {
                        showing();
                    }
                }
                else {
                    Toast.makeText(RaghHostActivity.this, "hhhh", Toast.LENGTH_SHORT).show();
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                    }
                    else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION1);
                    }
                    else {
                        showing();
                    }
                }

            }
        });
//for devices
        //if connected or not
        getValues();



        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        ////print  Section
        printcommand = findViewById(R.id.printcommand);
        //print Section
        printcommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();
                if ((TextUtils.isEmpty(name) || name.toString() == null) && (TextUtils.isEmpty(mobile) || mobile.toString() == null)) {
                    Toast.makeText(RaghHostActivity.this, "No printer Connected.", Toast.LENGTH_SHORT).show();
                } else {
                    if ((name.toString().equals("THT")|| name.toString().contains("THT")) && ( mobile.toString().equals("01:01:01:01:01"))) {
                        Toast.makeText(RaghHostActivity.this, "No printer Connected.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String BlueMac = mobile;


                        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
                        mBluetoothAdapter = mBluetoothManager.getAdapter();
                        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
                        ///Toasty.info(getApplicationContext(),"Please active bluetooth"+mBluetoothAdapter.isEnabled(),Toasty.LENGTH_SHORT,true).show();
                        if (!mBluetoothAdapter.isEnabled()) {
                            Toasty.info(getApplicationContext(), "Please active bluetooth", Toasty.LENGTH_SHORT, true).show();
                            android.app.AlertDialog.Builder mybuilder = new android.app.AlertDialog.Builder(RaghHostActivity.this);
                            mybuilder.setTitle("Confirmation")
                                    .setMessage("Do you want to active bluetooth");
                            mybuilder.setPositiveButton("Not Now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Right Now", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                        mBluetoothAdapter.enable();
                                        Toasty.info(getApplicationContext(), "Bluetooth is active now.", Toasty.LENGTH_SHORT, true).show();
                                    } else {
                                        mBluetoothAdapter.enable();
                                        Toasty.info(getApplicationContext(), "Bluetooth is active now.", Toasty.LENGTH_SHORT, true).show();
                                    }

                                }
                            }).create().show();

                            return;
                        } else {
                            if (selectcategory.toString().toLowerCase().toString().equals("cpcl")) {
                                Toasty.info(getApplicationContext(), "Bluetooth Device : " + bluename111.getText().toString() + "\n" +
                                        "Mac Address CC: " + BlueMac, Toasty.LENGTH_SHORT, true).show();
                                String sessionname = quantityProductPage_speed.getText().toString();
                                String sessionmobile = progressbarsechk.getText().toString();
                                String sessionphoto = "photo";
                                String sessionemail = "email";
                                ZoneId z = ZoneId.of("Asia/Dhaka");
                                LocalDate today = LocalDate.now(z);
                                String sessionusername = "" + today;
                                scason2.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto, sessionusername);
                                int height = Integer.parseInt(myheight) * 8;
                                int width = Integer.parseInt(mywidth) * 8;
                                printImage1(BlueMac, width, height);
                            } else if (selectcategory.toString().toLowerCase().toString().equals("esc")) {
                                int height = Integer.parseInt(myheight) * 8;
                                int width = Integer.parseInt(mywidth) * 8;
                                getValues();

                                String sessionname = name;
                                String sessionmobile = mobile;
                                String sessionphoto = "" + quantityProductPage_speed.getText().toString();
                                String sessionemail = "" + progressbarsechk.getText().toString();
                                ZoneId z = ZoneId.of("Asia/Dhaka");
                                LocalDate today = LocalDate.now(z);
                                String sessionusername = "" + today;
                                session.createLoginSession(sessionname, sessionemail, sessionmobile, sessionphoto, sessionusername);

                                Toasty.info(getApplicationContext(), "Bluetooth Device : " + name + "\n" +
                                        "Mac Address : " + BlueMac, Toasty.LENGTH_SHORT, true).show();
                                int n = Integer.parseInt(edittextquantityProductPage.getText().toString())+1;
                                String check_mac = macaddresssname.getText().toString();
                                Log.e("Mac and Blue",""+check_mac+""+BlueMac);

                                if (check_mac.equals("12"))
                                {
                                    isConnected=false;
                                    printImage2(BlueMac, width, height);

                                    Log.e("dolon12",""+totalPrintCommands2);
                                    macaddresssname.setText(""+BlueMac);
                                }
                                else if(check_mac.equals(BlueMac))
                                {
                                    if (totalPrintCommands2==0)
                                    {
                                        totalPrintCommands2=122;
                                        isConnected=false;
                                        printImage2(BlueMac, width, height);


                                        Log.e("dolon01",""+totalPrintCommands2);

                                    }
                                    else {
                                        isConnected=true;
                                        printImage2(BlueMac, width, height);

                                        Log.e("dolon2000",""+totalPrintCommands2);
                                    }


                                }
                                else {
                                    isConnected=false;
                                    printImage2(BlueMac, width, height);
                                    //totalPrintCommands2++;
                                    Log.e("dolon114",""+totalPrintCommands2);
                                    macaddresssname.setText(""+BlueMac);
                                }

                               /*
                                if (isConnected==true)
                                {
                                    try {
                                        m5ocket.close();
                                        isConnected=false;

                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }

                                printImage2(BlueMac, width, height);
                                totalPrintCommands2++;
                                Log.e("dolon1",""+totalPrintCommands2);
                                */

                               /*
                                if(totalPrintCommands2%2==0)
                                {
                                    if (isConnected==true)
                                    {
                                       try {
                                           m5ocket.close();
                                           isConnected=false;
                                       }catch (Exception e)
                                       {
                                           e.printStackTrace();
                                       }
                                    }
                                    printImage2(BlueMac, width, height);
                                    totalPrintCommands2++;
                                    Log.e("dolon1",""+totalPrintCommands2);
                                }
                                else{
                                    if (isConnected==true)
                                    {
                                        try {
                                            m5ocket.close();
                                            isConnected=false;
                                        }catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                    printImage3(BlueMac, width, height);
                                    totalPrintCommands2++;
                                    Log.e("dolon2",""+totalPrintCommands2);
                                }
                                */


                            }

                        }
                    }

                }

            }
        });
    }

    private void checkingallpermission() {
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            String[] permissionsArray = permissionsToRequest.toArray(new String[0]);
            requestPermissions(permissionsArray, REQUEST_PERMISSIONS);
        } else {
            //Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();

            innit();
        }
    }

    private void checkdeviceandadd() {
        discoveryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (device.getName() != null && device.getAddress()!=null) {
                            boolean isExsist = false;
                            String deviceAddress = device.getAddress();
                            for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                if (existingDevice != null && existingDevice.getAddress() != null &&
                                        existingDevice.getAddress().equals(deviceAddress)) {
                                    isExsist = true;
                                    break;
                                }
                            }
                            if (!isExsist)
                            {
                                deviceAdapter.add(device);
                                deviceAdapter.notifyDataSetChanged();
                            }

                        }
                    } else {
                        if (device.getName() != null && device.getAddress()!=null) {
                            boolean isExsist = false;
                            String deviceAddress = device.getAddress();
                            for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                if (existingDevice != null && existingDevice.getAddress() != null &&
                                        existingDevice.getAddress().equals(deviceAddress)) {
                                    isExsist = true;
                                    break;
                                }
                            }
                            if (!isExsist)
                            {
                                deviceAdapter.add(device);
                                deviceAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
            }
        };
    }

    private void showing() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // Permissions are not granted, so request them
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS);
        } else {
            deviceAdapter = new BluetoothDeviceAdapter(RaghHostActivity.this, new ArrayList<>());
            deviceAdapter = new BluetoothDeviceAdapter(RaghHostActivity.this, new ArrayList<>());
            listView = (ListView) bottomSheetDialog11.findViewById(R.id.list_view);
            listView.setAdapter(deviceAdapter);
            startDiscovery();
            bottomSheetDialog11.show();
        }

    }

    private void printImage4(String blueMac, int width, int height, int totalPrintCommands21111) {
        Log.e("Function",""+totalPrintCommands21111);
    }

    TextView macaddresssname;
    private void innit()
    {
        session = new UserSession(RaghHostActivity.this);
        scason2 = new UserSession2(RaghHostActivity.this);
        connectedornot = findViewById(R.id.connectedornot);
        getValues();

        BluetoothAdapter bluetoothAdapterq=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapterq == null) {
            // Device doesn't support Bluetooth
            return;
        }

        if (!bluetoothAdapterq.isEnabled()) {

            ///backup

           /*
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            */

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {

                // BLUETOOTH_CONNECT permission has not been granted.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.BLUETOOTH_CONNECT)) {

                    // Provide an additional rationale to the user if the permission was not granted
                    // and the user would benefit from additional context for the use of the permission.
                    // For example if the user has previously denied the permission.
                    // TODO: Display a SnackBar/AlertDialog here with the rationale.

                    // Request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                            REQUEST_BLUETOOTH_CONNECT);

                }
                else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION);
                }
                else {

                    // BLUETOOTH_CONNECT permission has not been granted yet. Request it directly.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                            REQUEST_BLUETOOTH_CONNECT);
                }
            } else {

                // BLUETOOTH_CONNECT permissions are already granted.
                // You can now initiate a Bluetooth connection here.
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

        }

        if (bluetoothAdapterq.isEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                }
                else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION);
                }

                else {
                    session = new UserSession(RaghHostActivity.this);
                    scason2 = new UserSession2(RaghHostActivity.this);
                    getValues();
                    connectedornot = findViewById(R.id.connectedornot);
                    //Toast.makeText(this, "Tiki"+mobile, Toast.LENGTH_SHORT).show();
                    connectedornot = findViewById(R.id.connectedornot);
                    //   Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                    intttt("mobile.toString()");
                    // Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                //Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
            }
        }

        if (bluetoothAdapterq.isEnabled()) {
            if (ContextCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(RaghHostActivity.this,
                        new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                        REQUEST_BLUETOOTH_CONNECT_PERMISSION);
            }
            else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION);
            }
            else {
                // Permission already granted, proceed with the operation
                // Start your Bluetooth operation or intent here
                if (!bluetoothAdapterq.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    // Toast.makeText(this, "ddddd", Toast.LENGTH_SHORT).show();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                } else {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                            != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                    != PackageManager.PERMISSION_GRANTED) {

                        // Permissions are not granted, request them
                        ActivityCompat.requestPermissions(this,
                                new String[]{
                                        Manifest.permission.BLUETOOTH_CONNECT,
                                        Manifest.permission.BLUETOOTH_SCAN
                                },
                                REQUEST_BLUETOOTH_PERMISSIONS);
                    }
                    else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION);
                    }
                    else {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Permission is not granted, request it
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                                    REQUEST_BLUETOOTH_SCAN_PERMISSION);
                        }
                        else if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_SCAN_PERMISSION);
                        }
                        else {
                            session = new UserSession(RaghHostActivity.this);
                            scason2 = new UserSession2(RaghHostActivity.this);
                            getValues();
                            connectedornot = findViewById(R.id.connectedornot);
                            //Toast.makeText(this, "Tiki"+mobile, Toast.LENGTH_SHORT).show();
                            connectedornot = findViewById(R.id.connectedornot);
                            // Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                            intttt(mobile.toString());

                            // connectedornot.setText("Not Connected");
                            // connectedornot.setTextColor(Color.parseColor("#FF0000"));
                            //  Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                            //connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                            if (count==0)
                            {
                                //allpaireddevice();
                                //  getValues();
                                //Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                                count++;
                            }
                            else {}

                            // startDiscovery();
                            //allpaireddevice();
                            // Permission already granted, proceed with the Bluetooth scanning operation
                        }

                        // Permissions already granted, proceed with the Bluetooth scanning operation
                    }
                    //
                }
            }
        } else {

        }

    }
    private static final int REQUEST_ENABLE_BT1 = 1;
    private static final int REQUEST_BLUETOOTH_CONNECT = 2;
    int count =0;
    private void alldevice() {
        //Toast.makeText(this, "TTT"+mobile, Toast.LENGTH_SHORT).show();

        deviceAdapter = new BluetoothDeviceAdapter(this, new ArrayList<>());
        listView.setAdapter(deviceAdapter);
        discoveryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (device.getName() != null && device.getAddress()!=null) {
                            boolean deviceExists = false;
                            for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                if (existingDevice != null && existingDevice.getAddress() != null) {
                                    if (existingDevice.getAddress().equals(device.getAddress())) {
                                        deviceExists = true;
                                        break;
                                    }
                                }
                            }

                            if (!deviceExists) {
                                //  Toast.makeText(RaghHostActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                                deviceAdapter.add(device);
                                deviceAdapter.notifyDataSetChanged();
//                                Toast.makeText(context, "" + device, Toast.LENGTH_SHORT).show();
                            }

//                            deviceAdapter.add(device);
//                            deviceAdapter.notifyDataSetChanged();
//                           Toast.makeText(context, ""+device, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (device.getName() != null) {
                            boolean deviceExists = false;
                            for (int i = 0; i < deviceAdapter.getCount(); i++) {
                                BluetoothDevice existingDevice = deviceAdapter.getItem(i);
                                if (existingDevice != null && existingDevice.getAddress() != null) {
                                    if (existingDevice.getAddress().equals(device.getAddress())) {
                                        deviceExists = true;
                                        break;
                                    }
                                }
                            }

                            if (!deviceExists) {
                                // Toast.makeText(RaghHostActivity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                                deviceAdapter.add(device);
                                deviceAdapter.notifyDataSetChanged();
//                                Toast.makeText(context, "" + device, Toast.LENGTH_SHORT).show();
                            }

                        }
                        // Toast.makeText(RaghHostActivity.this, "" + device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    private void startDiscovery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(discoveryReceiver, filter);
            alldevice();
            //  allpaireddevice();
        } else {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(discoveryReceiver, filter);
            alldevice();

            //allpaireddevice();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister BroadcastReceiver
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
        }
        else {
            if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
        }


        //   unregisterReceiver(discoveryReceiver);
    }

    int REQUEST_BLUETOOTH_CONNECT_PERMISSION = 2;
    int REQUEST_BLUETOOTH_PERMISSIONS = 3;
    int REQUEST_CODE_PERMISSIONS = 36;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                // All requested permissions are granted, proceed to the innit() function
                innit();
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    innit();
                }
            }
        }
        else  if (requestCode==REQUEST_CODE_PERMISSIONS)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                checkdeviceandadd();
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    checkdeviceandadd();
                }
            }
        }
        else  if(requestCode==REQUEST_BLUETOOTH_CONNECT)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // Bluetooth-related task you need to do.

                // Since Bluetooth permission is granted, you can request to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                // TODO: Disable the Bluetooth feature or inform the user about the lack of permission.
            }
        }
        else if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                // startDiscovery();

                if (ContextCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(RaghHostActivity.this,
                            new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                            REQUEST_BLUETOOTH_CONNECT_PERMISSION);
                    // Toast.makeText(this, "Locatiodddn", Toast.LENGTH_SHORT).show();


                } else {
                    // Permission already granted, proceed with the operation
                    // Start your Bluetooth operation or intent here
                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                    } else {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                                != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                        != PackageManager.PERMISSION_GRANTED) {

                            // Permissions are not granted, request them
                            ActivityCompat.requestPermissions(this,
                                    new String[]{
                                            Manifest.permission.BLUETOOTH_CONNECT,
                                            Manifest.permission.BLUETOOTH_SCAN
                                    },
                                    REQUEST_BLUETOOTH_PERMISSIONS);
                        } else {
                            startDiscovery();
                            //Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                                    != PackageManager.PERMISSION_GRANTED) {

                                // Permission is not granted, request it
                                ActivityCompat.requestPermissions(this,
                                        new String[]{Manifest.permission.BLUETOOTH_SCAN},
                                        REQUEST_BLUETOOTH_SCAN_PERMISSION);
                            } else {
                                startDiscovery();
                                //  Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();
                                //  /(this, "hhhh", Toast.LENGTH_SHORT).show();
                                //alldevice();

                                // Permission already granted, proceed with the Bluetooth scanning operation
                            }

                            // Permissions already granted, proceed with the Bluetooth scanning operation
                        }
                        //
                    }
                }
            }
        } else if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the Bluetooth operation or intent
                alldevice();
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    alldevice();

                }
            }
        } else if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                alldevice();

                // Permissions granted, proceed with the Bluetooth scanning operation
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    alldevice();

                }
            }
        } else if (requestCode == REQUEST_BLUETOOTH_SCAN_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alldevice();
                // Permission granted, proceed with the Bluetooth scanning operation
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    alldevice();

                }
            }
        }
        else if (requestCode == REQUEST_BLUETOOTH_SCAN_PERMISSION1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alldevice();
                showing();
                // Permission granted, proceed with the Bluetooth scanning operation
            } else {
                // Permission denied, handle accordingly (e.g., show an explanation or disable functionality)
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    alldevice();
                    showing();
                }
            }
        }
    }

    int  REQUEST_BLUETOOTH_SCAN_PERMISSION1 = 56;




    public void decrement(View view) {
        int value = Integer.parseInt(edittextquantityProductPage.getText().toString());
        if (value==1) {
            Toasty.info(getApplicationContext(),"It is the lowest value.Print Copy value is not decrement now.", Toast.LENGTH_SHORT,true).show();
            //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value-1;
            quantityProductPage.setText(""+value);
            edittextquantityProductPage.setText(""+value);
        }
    }

    public void increment(View view) {
        int value = Integer.parseInt(edittextquantityProductPage.getText().toString());
        if (value==99) {
            Toasty.warning(getApplicationContext(),"It is the highest value. Print Copy value is not increment now.", Toast.LENGTH_SHORT,true).show();
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value+1;
            quantityProductPage.setText(""+value);
            edittextquantityProductPage.setText(""+value);
        }
    }


    //print section
    Uri bitmapUri;
    Bitmap mainimageBitmap;

    int PICK=12;
    boolean request=false;
    CountDownTimer countDownTimer,countDownTimer1;
    String hexString = "0x0";
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.papertype)
        {
            color_detect = parent.getItemAtPosition(position).toString();
            int decimalNumber = Integer.parseInt(color_detect);
            if(decimalNumber<=15)
            {
                String  firststring = "0x0";
                String second=  convertDecimalToHex(decimalNumber);
                hexString = firststring+""+second;
            }
            else{
                String  firststring = "0x";
                String second=  convertDecimalToHex(decimalNumber);
                hexString = firststring+""+second;
            }

            //Toast.makeText(this, ""+hexString, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void decrement_speed(View view) {
        int value = Integer.parseInt(quantityProductPage_speed.getText().toString());
        if (value==1) {
            Toasty.info(getApplicationContext(),"It is the lowest value.Print speed value is not decrement now.", Toast.LENGTH_SHORT,true).show();
            //Toast.makeText(this, "It is the lowest value.Print speed value is not decrement now.", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value-1;
            quantityProductPage_speed.setText(""+value);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(MyDevicesActivity.DevicesBitmap!=null)
//            MyDevicesActivity.DevicesBitmap=bitmapdataMe;
    }

    public void increment_speed(View view) {
        int value = Integer.parseInt(quantityProductPage_speed.getText().toString());
        if (value==6) {
            Toasty.warning(getApplicationContext(),"It is the highest value. Print Speed value is not increment now.", Toast.LENGTH_SHORT,true).show();
            //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{
            value=value+1;
            quantityProductPage_speed.setText(""+value);
        }
    }

    public void dialougeccc(View view) {
        finish();
    }
    ////bitmap and printing


    private  byte[]  BitmapToRGBbyteA(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList =new ArrayList<Byte>();
        int height = 1080;
        if(bitmapOrg.getHeight()>height)
        {
            height=1080;
        }
        else
        {
            height=bitmapOrg.getHeight();
        }
        int width =384;
        int R = 0, B = 0, G = 0;
        //int pixles;
        int []pixels = new int[width * height];
        int x = 0, y = 0;
        Byte[] Gray_Send;
        //bitSet = new BitSet();
        try {

            bitmapOrg.getPixels(pixels, 0, width, 0, 0, width, height);
            int alpha = 0xFF << 24;
            //int []i_G=new int[7];
            int []i_G=new int[13];
            int Send_Gray=0x00;
            int StartInt=0;
            char  StartWords=' ';

            int k=0;
            int Send_i=0;
            int mathFlag=0;
            for(int i = 0; i < height; i++)
            {

                k=0;
                Send_i=0;
                for (int j = 0; j <width; j++)
                {
                    int grey = pixels[width * i + j];
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);


//==================================
                    if(grey>128)
                    {
                        //bufImage[j]=0x00;
                        mathFlag=0;

                    }
                    else
                    {
                        //bufImage[j]=0x01;
                        mathFlag=1;
                    }
                    k++;
                    if(k==1)
                    {
                        Send_i=0;
                        Send_i=Send_i+128*mathFlag;//mathFlag|0x80
                    }
                    else if(k==2)
                    {
                        Send_i=Send_i+64*mathFlag;//mathFlag|0x40
                    }
                    else if(k==3)
                    {
                        Send_i=Send_i+32*mathFlag;//mathFlag|0x20
                    }
                    else if(k==4)
                    {
                        Send_i=Send_i+16*mathFlag;//mathFlag|0x10
                    }
                    else if(k==5)
                    {
                        Send_i=Send_i+8*mathFlag;//mathFlag|0x08
                    }
                    else if(k==6)
                    {
                        Send_i=Send_i+4*mathFlag;//mathFlag|0x04
                    }
                    else if(k==7)
                    {
                        Send_i=Send_i+2*mathFlag;//mathFlag|0x02
                    }
                    else if(k==8)
                    {
                        Send_i=Send_i+1*mathFlag;//mathFlag|0x01
                        Gray_ArrayList.add((byte)Send_i);

                        Send_i=0;
                        k=0;
                    }

                }
                int aBc=0;

            }

            byte[] sss=new byte[Gray_ArrayList.size()];
            Gray_Send=new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for(int xx=0;xx<Gray_Send.length;xx++){
                sss[xx]=Gray_Send[xx];
            }
            return  sss;
        } catch (Exception e) {

        }
        return null;
    }
    int bitmapHeight = 1080;
    OutputStream os = null;

    @Override
    public void onBackPressed() {
        finish();

        try {
            if (isConnected==true)
            {
                m5ocket.close();
                isConnected = false;
            }
            else{

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private byte[] getAngenlSendDateCpcl(Bitmap img,int PrintSpeed,int PrintDensity)
    {
        int width = img.getWidth();
        //获取位图的宽
        if(width>832)
            width=832;
        int height = img.getHeight();
        if(height>1080)//获取位图的高
            height=1080;
        int PrintSpeedNow=PrintSpeed;
        int PrintDensityNow=PrintDensity;
        int []pixels = new int[width * height];	//通过位图的大小创建像素点数组

        Byte[]Gray_Send;
        ArrayList<Byte> Gray_Arraylist;
        Gray_Arraylist=new ArrayList<Byte>();
        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        int []i_G=new int[13];
        int lineFlag=1;
        int []lineTimes=new int[7];//01234
        int lineMax=120;
        int lineFor=3;
        int []lineStart=new int[7];
        int []lineEnd=new int[7];


        int Send_Gray=0x00;
        int StartInt=0;
        char  StartWords=' ';
        StartInt=0x21;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x32;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x32;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);


        int qw=1000%10;
        //求百位
        int bw=height/100%10;
        //求十位
        int sw=height/10%10;
        //求个位
        int gw=height%10;
        if(qw!=0)
        {
            StartInt=qw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=bw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=sw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=gw+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        else
        {
            if(bw!=0)
            {
                StartInt=bw+48;
                Gray_Arraylist.add((byte)StartInt);
                StartInt=sw+48;
                Gray_Arraylist.add((byte)StartInt);
                StartInt=gw+48;
                Gray_Arraylist.add((byte)StartInt);
            }
            else
            {
                StartInt=sw+48;
                Gray_Arraylist.add((byte)StartInt);
                StartInt=gw+48;
                Gray_Arraylist.add((byte)StartInt);
            }

        }

        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x31;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x50;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x57;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        int WqwX=width/1000%10;
        //求百位
        int WbwX=width/100%10;
        //求十位
        int WswX=width/10%10;
        //求个位
        int WgwX=width%10;

        if(WbwX!=0)
        {
            StartInt=WbwX+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=WswX+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=WgwX+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        else
        {
            StartInt=WswX+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=WgwX+48;
            Gray_Arraylist.add((byte)StartInt);
        }

        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        //=========DENSITY================
        //DENSITY

        StartWords='D';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='E';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='N';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='S';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='I';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='T';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='Y';
        Gray_Arraylist.add((byte)StartWords);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        //求十位
        int DensitySw=PrintDensityNow/10%10;
        //求个位
        int DensityGw=PrintDensityNow%10;
        if(DensitySw!=0)
        {

            StartInt=DensitySw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=DensityGw+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        else
        {

            StartInt=DensityGw+48;
            Gray_Arraylist.add((byte)StartInt);
        }

        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        //==========SPEED==============================================
        //SPEED
        StartWords='S';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='P';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='E';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='E';
        Gray_Arraylist.add((byte)StartWords);
        StartWords='D';
        Gray_Arraylist.add((byte)StartWords);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        //求十位
        int SpeedSw=PrintSpeedNow/10%10;
        //求个位
        int SpeedGw=PrintSpeedNow%10;
        if(SpeedSw!=0)
        {

            StartInt=SpeedSw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=SpeedGw+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        else
        {

            StartInt=SpeedGw+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x43;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x47;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);



        //====================
        int Wqw=width/8/1000%10;
        //求百位
        int Wbw=width/8/100%10;
        //求十位
        int Wsw=width/8/10%10;
        //求个位
        int Wgw=width/8%10;

        if(Wbw!=0)
        {
            StartInt=Wbw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=Wsw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=Wgw+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        else
        {
            StartInt=Wsw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=Wgw+48;
            Gray_Arraylist.add((byte)StartInt);
        }


        //=========================


        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);


        if(qw!=0)
        {
            StartInt=qw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=bw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=sw+48;
            Gray_Arraylist.add((byte)StartInt);
            StartInt=gw+48;
            Gray_Arraylist.add((byte)StartInt);
        }
        else
        {
            if(bw!=0)
            {
                StartInt=bw+48;
                Gray_Arraylist.add((byte)StartInt);
                StartInt=sw+48;
                Gray_Arraylist.add((byte)StartInt);
                StartInt=gw+48;
                Gray_Arraylist.add((byte)StartInt);
            }
            else
            {
                StartInt=sw+48;
                Gray_Arraylist.add((byte)StartInt);
                StartInt=gw+48;
                Gray_Arraylist.add((byte)StartInt);
            }

        }

        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;//CG
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);

        int k=0;
        int Send_i=0;
        int mathFlag=0;
        int l_k=0;
        int l_n=0;
        int l_m=0;
        int lineFlagOK=0;
        int lineFlagOK_A=0;
        int lineFor_A=0;
        int j_x=0;
        int lineAdd=0;
        int lineAddMax=0;
        int line_i=0;
        int []lineNowAdd=new int[width];
        int []lineHang=new int[width];
        for(int i = 0; i < height; i++)
        {
            k=0;
            Send_i=0;
            for (int j = 0; j <width; j++)
            {
                int grey = pixels[width * i + j];
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);

                if(grey>Integer.parseInt(color_detect))
                {
                    mathFlag=0;

                }
                else
                {
                    //bufImage[j]=0x01;
                    mathFlag=1;
                }

                k++;
                if(k==1)
                {
                    Send_i=0;
                    Send_i=Send_i+128*mathFlag;//mathFlag|0x80
                }
                else if(k==2)
                {
                    Send_i=Send_i+64*mathFlag;//mathFlag|0x40
                }
                else if(k==3)
                {
                    Send_i=Send_i+32*mathFlag;//mathFlag|0x20
                }
                else if(k==4)
                {
                    Send_i=Send_i+16*mathFlag;//mathFlag|0x10
                }
                else if(k==5)
                {
                    Send_i=Send_i+8*mathFlag;//mathFlag|0x08
                }
                else if(k==6)
                {
                    Send_i=Send_i+4*mathFlag;//mathFlag|0x04
                }
                else if(k==7)
                {
                    Send_i=Send_i+2*mathFlag;//mathFlag|0x02
                }
                else if(k==8)
                {
                    Send_i=Send_i+1*mathFlag;//mathFlag|0x01
                    Gray_Arraylist.add((byte)Send_i);

                    Send_i=0;
                    k=0;
                }
// =================================





                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
            int aBc=0;

        }

//        PR 0
//        FORM
//        PRINT
        //50 52 20 30 0A 46 4F 52 4D 0A 50 52 49 4E 54 0A
        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x50;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x52;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x20;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x30;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x46;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x4F;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x52;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x4D;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        StartInt=0x50;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x52;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x49;//CG
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x4E;
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x54;//CG
        Gray_Arraylist.add((byte)StartInt);
        StartInt=0x0A;
        Gray_Arraylist.add((byte)StartInt);

        final byte[] sss=new byte[Gray_Arraylist.size()];
        Gray_Send=new Byte[Gray_Arraylist.size()];
        Gray_Arraylist.toArray(Gray_Send);
        for(int xx=0;xx<Gray_Send.length;xx++){
            sss[xx]=Gray_Send[xx];
        }



        return sss;

    }


    private void printImage1(String bl,int mainimagewidth, int mainimageheight) {
        final Bitmap bitmap = bitmapdataMe;

        float bitw = (float) mainimagewidth;
        float bith = (float)mainimageheight;
        int speed =Integer.parseInt(quantityProductPage_speed.getText().toString());
        int  density = Integer.parseInt(progressbarsechk.getText().toString());

        //  final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dolphin);
        float scax= bitw / bitmap.getWidth();
        float scaly= bith / bitmap.getHeight();
        Log.e("dolon",""+bitmap);
        Log.e("zzz",""+bitmap.getWidth());
        Log.e("zzz",""+bitmap.getHeight());
        Matrix matrix=new Matrix();
        matrix.postScale(scax,scaly);
        //Bitmap resize= bitmap;//Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        Log.e("Ariful9",""+scax);
        //final Bitmap bitmap = bitmapdataMe;

        //final byte[] bitmapGetByte =BitmapToRGBbyteAA(resize); //BitmapToRGBbyteA(bitmap);//convertBitmapToRGBBytes (resize);

        final byte[] bitmapGetByte=getAngenlSendDateCpcl(bitmap,speed,density);

        Log.e("Ariful4",""+bitmapGetByte);
        String BlueMac = bl;
        Log.e("Ariful66",""+geeet);
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        ///

        ////
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();



                        for (int i=1;i<=Integer.parseInt(quantityProductPage.getText().toString());i++){
                            os = m5ocket.getOutputStream();

//                            if(resize.getHeight()>1080)
//                            {
//                                bitmapHeight=1080;
//                            }
//                            else
//                            {
//                                bitmapHeight=resize.getHeight();
//                            }
//                            bitmapWidth=resize.getWidth();
//                            int bimapWidth8=bitmapWidth/8;
//                            Log.e("Ariful1",""+resize.getWidth());
//                            Log.e("Ariful2",""+resize.getHeight());
//                            Log.e("Ariful3",""+bitmap);
                            Random random=new Random();
                            int sendingnumber=random.nextInt(10);
                            int mimisecond=sendingnumber*1000;
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // write your code here
                                    countDownTimer =new CountDownTimer(2000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                            double seconddd=millisUntilFinished/1000;
                                            printtimer.setText("Sending Data : "+seconddd+" S");


                                        }

                                        @Override
                                        public void onFinish() {
                                            try {
                                                os.write(bitmapGetByte);
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Ariful6",""+e.getMessage());
                                            }
                                            countDownTimer1=new CountDownTimer(1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    long second=  (millisUntilFinished/1000);
                                                    int mysecond=Integer.parseInt(String.valueOf(second));

                                                }

                                                @Override
                                                public void onFinish() {

                                                    printtimer.setText("Print Out");
                                                    try {

                                                        os.flush();
                                                        //os.flush();
                                                        m5ocket.close();
                                                        os=null;
                                                        if (print_flag==0)
                                                        {
                                                            Store_Speed();
                                                            print_flag++;
                                                        }
                                                        else{
                                                            Store_Speed();
                                                        }
                                                        Log.e("Ariful7","Go to print");

                                                    }catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.e("Ariful8",""+e.getMessage());
                                                    }

                                                    Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                            }.start();
                                            countDownTimer1.start();


                                        }
                                    };
                                    countDownTimer.start();
                                }
                            });
                        }

                    }
                    else {

                    }




                } catch (IOException e) {
                    // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                    // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                    Log.e("Error : ",""+e.getMessage());
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                }
            }
        });
        thread.start();//1
    }

    //for esc
    private int copiesPrinted = 0;

    boolean isConnected = false;
    private void printImage2(String bl,int mainimagewidth, int mainimageheight) {
        //Toast.makeText(this, "get", Toast.LENGTH_SHORT).show();

        final Bitmap bitmap = bitmapdataMe;
        float bitw = (float) mainimagewidth;
        float bith = (float)mainimageheight;

        // final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dolphin);
        float scax=bitw /bitmap.getWidth();
        float scaly=bith / bitmap.getHeight();
        Log.e("dolon",""+bitmap);
        Log.e("zzz",""+bitmap.getWidth());
        Log.e("zzz",""+bitmap.getHeight());
        Matrix matrix=new Matrix();
        matrix.postScale(scax,scaly);
        Bitmap resize=bitmap; //Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        Log.e("Ariful9",""+scax);
        //final Bitmap bitmap = bitmapdataMe;
        Bitmap copyBitmap = Bitmap.createBitmap(resize);
        final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
        Log.e("Ariful4",""+bitmapGetByte);
        String BlueMac = bl;
        Log.e("Ariful66",""+bl);
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        int n = Integer.parseInt(edittextquantityProductPage.getText().toString())+0;
        int totalPrintCommands = n;


        if (isConnected==true)
        {
            Log.e("Connected","Connected");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                            int copiesPrinted = 0;

                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {
                                Log.e("dolon22",""+i);
                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;


                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    //check

                                                    //check

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        // StartInt=0x01;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        StartInt=0x06 ;
                                                        Log.e("Speed6","6");
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        // StartInt=0x0b;
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        //StartInt=0x15;
                                                        StartInt=0x04;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        //StartInt=0x1f;
                                                        StartInt=0x03;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        // StartInt=0x29;
                                                        StartInt=0x02;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        //StartInt=0x33;
                                                        StartInt=0x01;
                                                        // StartInt=0x29;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);
                                                    Log.e("Ariful5","PrintCommand");
                                                   /*
                                                    StartInt=  Integer.parseInt(hexString.substring(2), 16);
                                                    os.write((byte)StartInt);
                                                    */



                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {


                                                        printtimer.setText("Print Out");
                                                        try {

                                                            os.flush();
                                                            //os.flush();
                                                            //m5ocket.close();

                                                            if (print_flag==0)
                                                            {
                                                                // Store_Speed();
                                                                print_flag++;
                                                            }
                                                            else{
                                                                print_flag++;
                                                            }
                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }




                                                        // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                        int kk =0;


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });




                            }
                            // m5ocket.close();
                            // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();



                        }
                        else {
                            Bitmap copyBitmap = Bitmap.createBitmap(resize);
                            final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
                            Log.e("Ariful4",""+bitmapGetByte);


                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {


                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;



                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);

                                                    //check

                                                    //check

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        // StartInt=0x01;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        StartInt=0x06 ;
                                                        Log.e("Speed6","6");
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        // StartInt=0x0b;
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        //StartInt=0x15;
                                                        StartInt=0x04;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        //StartInt=0x1f;
                                                        StartInt=0x03;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        // StartInt=0x29;
                                                        StartInt=0x02;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        //StartInt=0x33;
                                                        StartInt=0x01;
                                                        // StartInt=0x29;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);

                                                    Log.e("Ariful5","PrintCommand");
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {

                                                        printtimer.setText("Print Out");
                                                        try {

                                                            os.flush();
                                                            //os.flush();
                                                            // m5ocket.close();


                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });
                                // thread


                            }
                            // m5ocket.close();
                            //Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();



                        }

                        //


                    } catch (IOException e) {
                        // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                        // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                        Log.e("Error : ",""+e.getMessage());
                        // View parentLayout = findViewById(android.R.id.content);
                        //   Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                }
            });
            thread.start();
        }
        else {


            Log.e("Ariful22",""+isConnected);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            if (isConnected==false)
                            {
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            }
                            else{
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            } int copiesPrinted = 0;

                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {
                                Log.e("dolon22",""+i);

                                copiesPrinted++;



                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;


                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(1000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    if (color_detect.toString().equals("Default Color"))
                                                    {
                                                        StartInt=0x80;
                                                        os.write((byte)StartInt);
                                                    }
                                                    else  if (color_detect.toString().equals("Max Color")){
                                                        StartInt=0xFF;
                                                        os.write((byte)StartInt);
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        // StartInt=0x01;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        StartInt=0x06 ;
                                                        Log.e("Speed6","6");
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        // StartInt=0x0b;
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        //StartInt=0x15;
                                                        StartInt=0x04;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        //StartInt=0x1f;
                                                        StartInt=0x03;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        // StartInt=0x29;
                                                        StartInt=0x02;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        //StartInt=0x33;
                                                        StartInt=0x01;
                                                        // StartInt=0x29;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);
                                                    Log.e("Ariful5","PrintCommand");




                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {


                                                        printtimer.setText("Print Out");
                                                        try {
                                                            os.write(0x1B);
                                                            os.write('@');

                                                            os.write(0x1B);
                                                            os.write('E');

                                                            os.flush();


                                                            if (print_flag==0)
                                                            {
                                                                // Store_Speed();
                                                                print_flag++;
                                                            }
                                                            else{
                                                                print_flag++;
                                                            }
                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }




                                                        // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                        int kk =0;


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });




                            }
                            try {
                                Thread.sleep(1000); // Add your desired delay time in milliseconds.

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // m5ocket.close();
                            // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();



                        }
                        else {
                            Bitmap copyBitmap = Bitmap.createBitmap(resize);
                            final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
                            Log.e("Ariful4",""+bitmapGetByte);
                            if (isConnected==false)
                            {
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            }
                            else{
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            }

                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {


                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;



                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        // StartInt=0x01;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        StartInt=0x06 ;
                                                        Log.e("Speed6","6");
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        // StartInt=0x0b;
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        //StartInt=0x15;
                                                        StartInt=0x04;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        //StartInt=0x1f;
                                                        StartInt=0x03;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        // StartInt=0x29;
                                                        StartInt=0x02;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        //StartInt=0x33;
                                                        StartInt=0x01;
                                                        // StartInt=0x29;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);

                                                    Log.e("Ariful5","PrintCommand");
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {

                                                        printtimer.setText("Print Out");
                                                        try {

                                                            os.flush();
                                                            //os.flush();
                                                            // m5ocket.close();


                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });
                                // thread


                            }
                            // m5ocket.close();
                            //Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();


                            //View parentLayout = findViewById(android.R.id.content);
                            //Snackbar.make(parentLayout," e.getMessage()", Snackbar.LENGTH_SHORT).show();
                        }

                        //


                    } catch (IOException e) {
                        // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                        // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                        Log.e("Error1 : ",""+e.getMessage());
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Error : "+e.getMessage(), Snackbar.LENGTH_SHORT).show();


                    }
                }
            });
            thread.start();
        }






    }
    //print 3
    private void printImage3(String bl,int mainimagewidth, int mainimageheight) {
        //Toast.makeText(this, "get", Toast.LENGTH_SHORT).show();

        final Bitmap bitmap = bitmapdataMe;
        float bitw = (float) mainimagewidth;
        float bith = (float)mainimageheight;

        // final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dolphin);
        float scax=bitw /bitmap.getWidth();
        float scaly=bith / bitmap.getHeight();
        Log.e("dolon",""+bitmap);
        Log.e("zzz",""+bitmap.getWidth());
        Log.e("zzz",""+bitmap.getHeight());
        Matrix matrix=new Matrix();
        matrix.postScale(scax,scaly);
        Bitmap resize=bitmap; //Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        Log.e("Ariful9",""+scax);
        //final Bitmap bitmap = bitmapdataMe;
        Bitmap copyBitmap = Bitmap.createBitmap(resize);
        final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
        Log.e("Ariful4",""+bitmapGetByte);
        String BlueMac = bl;
        Log.e("Ariful66",""+bl);
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        int n = Integer.parseInt(edittextquantityProductPage.getText().toString())+0;
        int totalPrintCommands = n;
        if (isConnected==true)
        {
            Log.e("Connected","Connected");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                            int copiesPrinted = 0;

                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {
                                Log.e("dolon22",""+i);
                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;


                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    //check

                                                    //check

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        StartInt=0x06;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        Log.e("Speed6",""+PrintSpeedNow);
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        StartInt=0x14;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        StartInt=0x13;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        StartInt=0x22;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        StartInt=0x31;
                                                        // StartInt=0x1f;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);
                                                    Log.e("Ariful5","PrintCommand");




                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {


                                                        printtimer.setText("Print Out");
                                                        try {

                                                            os.flush();
                                                            //os.flush();
                                                            //m5ocket.close();

                                                            if (print_flag==0)
                                                            {
                                                                // Store_Speed();
                                                                print_flag++;
                                                            }
                                                            else{
                                                                print_flag++;
                                                            }
                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }




                                                        // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                        int kk =0;


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });




                            }
                            // m5ocket.close();
                            // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();



                        }
                        else {
                            Bitmap copyBitmap = Bitmap.createBitmap(resize);
                            final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
                            Log.e("Ariful4",""+bitmapGetByte);


                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {


                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;



                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);

                                                    //check

                                                    //check

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        StartInt=0x06;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        Log.e("Speed6",""+PrintSpeedNow);
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        StartInt=0x14;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        StartInt=0x13;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        StartInt=0x22;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        StartInt=0x31;
                                                        // StartInt=0x1f;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);

                                                    Log.e("Ariful5","PrintCommand");
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {

                                                        printtimer.setText("Print Out");
                                                        try {

                                                            os.flush();
                                                            //os.flush();
                                                            // m5ocket.close();


                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });
                                // thread


                            }
                            // m5ocket.close();
                            //Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();



                        }

                        //


                    } catch (IOException e) {
                        // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                        // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                        Log.e("Error : ",""+e.getMessage());
                        // View parentLayout = findViewById(android.R.id.content);
                        //   Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                }
            });
            thread.start();
        }
        else {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            if (isConnected==false)
                            {
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            }
                            else{
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            } int copiesPrinted = 0;

                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {
                                Log.e("dolon22",""+i);

                                copiesPrinted++;



                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;


                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        StartInt=0x06;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        Log.e("Speed6",""+PrintSpeedNow);
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        StartInt=0x14;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        StartInt=0x13;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        StartInt=0x22;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        StartInt=0x31;
                                                        // StartInt=0x1f;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);
                                                    Log.e("Ariful5","PrintCommand");




                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {


                                                        printtimer.setText("Print Out");
                                                        try {
                                                            os.write(0x1B);
                                                            os.write('@');

                                                            os.write(0x1B);
                                                            os.write('E');

                                                            os.flush();


                                                            if (print_flag==0)
                                                            {
                                                                // Store_Speed();
                                                                print_flag++;
                                                            }
                                                            else{
                                                                print_flag++;
                                                            }
                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }




                                                        // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                        int kk =0;


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });




                            }
                            try {
                                Thread.sleep(1000); // Add your desired delay time in milliseconds.

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // m5ocket.close();
                            // Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();



                        }
                        else {
                            Bitmap copyBitmap = Bitmap.createBitmap(resize);
                            final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
                            Log.e("Ariful4",""+bitmapGetByte);
                            if (isConnected==false)
                            {
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            }
                            else{
                                m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                                m5ocket.connect();
                                isConnected= true;
                            }

                            for(int i = 0; i<totalPrintCommands;i++)//angenl111
                            {


                                os = m5ocket.getOutputStream();

                                if(resize.getHeight()>bitmapHeight)
                                {
                                    bitmapHeight=1080;
                                }
                                else
                                {
                                    bitmapHeight=resize.getHeight();
                                }
                                bitmapWidth=resize.getWidth();
                                Log.e("Ariful1",""+resize.getWidth());
                                Log.e("Ariful2",""+resize.getHeight());
                                Log.e("Ariful3",""+bitmap);
                                Random random=new Random();
                                int sendingnumber=random.nextInt(10);
                                int mimisecond=sendingnumber*1000;



                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // write your code here
                                        countDownTimer =new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {

                                                double seconddd=millisUntilFinished/1000;
                                                printtimer.setText("Sending Data : "+seconddd+" S");



                                            }

                                            @Override
                                            public void onFinish() {
                                                try {



                                                    int StartInt=0;
                                                    int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                    int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                    int PrintPaperTypeNow=0;
                                                    StartInt=0x1d;
                                                    //Gray_Arraylist.add((byte)StartInt);
                                                    os.write((byte)StartInt);
                                                    StartInt=0x0e;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);

                                                    StartInt=0x4D;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x53;
                                                    os.write((byte)StartInt);
                                                    if(PrintSpeedNow==6)
                                                    {
                                                        StartInt=0x06;//PrintSpeedNow
                                                        // StartInt=0x0b;
                                                        Log.e("Speed6",""+PrintSpeedNow);
                                                    }
                                                    if(PrintSpeedNow==5)
                                                    {
                                                        StartInt=0x05;

                                                    }
                                                    if(PrintSpeedNow==4)
                                                    {
                                                        StartInt=0x14;
                                                    }
                                                    if(PrintSpeedNow==3)
                                                    {
                                                        StartInt=0x13;
                                                    }
                                                    if(PrintSpeedNow==2)
                                                    {
                                                        StartInt=0x22;
                                                    }
                                                    if(PrintSpeedNow==1)
                                                    {
                                                        StartInt=0x31;
                                                        // StartInt=0x1f;

                                                        Log.e("Speed1","1");
                                                    }
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x7E;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintDensityNow+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x60;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x50;
                                                    os.write((byte)StartInt);
                                                    StartInt=PrintPaperTypeNow+0;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x1d;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x76;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x30;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x00;
                                                    os.write((byte)StartInt);
                                                    int widthH=bitmapWidth/8/256;
                                                    int widthL=bitmapWidth/8%256;
                                                    int heightH=bitmapHeight/256;
                                                    int heightL=bitmapHeight%256;
                                                    StartInt=widthL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=widthH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightL+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    StartInt=heightH+0;//PrintDensityNow
                                                    os.write((byte)StartInt);
                                                    os.write(bitmapGetByte);
                                                    StartInt=0x1c;
                                                    os.write((byte)StartInt);
                                                    StartInt=0x5e;
                                                    os.write((byte)StartInt);

                                                    Log.e("Ariful5","PrintCommand");
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful6",""+e.getMessage());
                                                }
                                                countDownTimer1=new CountDownTimer(1000,1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        long second=  (millisUntilFinished/1000);
                                                        int mysecond=Integer.parseInt(String.valueOf(second));



                                                    }

                                                    @Override
                                                    public void onFinish() {

                                                        printtimer.setText("Print Out");
                                                        try {

                                                            os.flush();
                                                            //os.flush();
                                                            // m5ocket.close();


                                                            Log.e("Ariful7","Go to print");

                                                        }catch (Exception e) {
                                                            e.printStackTrace();
                                                            Log.e("Ariful8",""+e.getMessage());
                                                        }


                                                        return;
                                                    }
                                                }.start();
                                                countDownTimer1.start();


                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });
                                // thread


                            }
                            // m5ocket.close();
                            //Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();


                            //View parentLayout = findViewById(android.R.id.content);
                            //Snackbar.make(parentLayout," e.getMessage()", Snackbar.LENGTH_SHORT).show();
                        }

                        //


                    } catch (IOException e) {
                        // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                        // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                        Log.e("Error : ",""+e.getMessage());
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                }
            });
            thread.start();
        }





    }
    private void closeSocket() {
        try {
            if (m5ocket != null) {
                m5ocket.close();
            }
        } catch (IOException e) {
            // Handle the exception or log an error message
        }
    }

    private void printImage4Times(String bl, int mainimagewidth, int mainimageheight) {
        final Bitmap bitmap = bitmapdataMe; // Your bitmap to be printed
        float bitw = (float) mainimagewidth;
        float bith = (float) mainimageheight;

        float scax = bitw / bitmap.getWidth();
        float scaly = bith / bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scax, scaly);
        Bitmap resize = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        final byte[] bitmapGetByte = BitmapToRGBbyteAA(resize); // Convert the bitmap to a byte array
        String BlueMac = bl; // Bluetooth device's MAC address

        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);

        int totalPrintCommands = 4; // Print the bitmap 4 times

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (!isConnected) {
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected = true;
                        } else {
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected = true;
                        }

                        for (int i = 0; i < totalPrintCommands; i++) {
                            os = m5ocket.getOutputStream();

                            if(resize.getHeight()>bitmapHeight)
                            {
                                bitmapHeight=1080;
                            }
                            else
                            {
                                bitmapHeight=resize.getHeight();
                            }
                            bitmapWidth=resize.getWidth();
                            Log.e("Ariful1",""+resize.getWidth());
                            Log.e("Ariful2",""+resize.getHeight());
                            Log.e("Ariful3",""+bitmap);
                            Random random=new Random();
                            int sendingnumber=random.nextInt(10);
                            int mimisecond=sendingnumber*1000;



                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // write your code here
                                    countDownTimer =new CountDownTimer(2000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                            double seconddd=millisUntilFinished/1000;
                                            printtimer.setText("Sending Data : "+seconddd+" S");



                                        }

                                        @Override
                                        public void onFinish() {
                                            try {



                                                int StartInt=0;
                                                int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                int PrintPaperTypeNow=0;
                                                StartInt=0x1d;
                                                //Gray_Arraylist.add((byte)StartInt);
                                                os.write((byte)StartInt);
                                                StartInt=0x0e;
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);

                                                StartInt=0x4D;
                                                os.write((byte)StartInt);
                                                StartInt=0x53;
                                                os.write((byte)StartInt);
                                                if(PrintSpeedNow==6)
                                                {
                                                    StartInt=0x01;//PrintSpeedNow
                                                }
                                                else if(PrintSpeedNow==5)
                                                {
                                                    StartInt=0x0b;
                                                }
                                                else if(PrintSpeedNow==4)
                                                {
                                                    StartInt=0x15;
                                                }
                                                else if(PrintSpeedNow==3)
                                                {
                                                    StartInt=0x1f;
                                                }
                                                else if(PrintSpeedNow==2)
                                                {
                                                    StartInt=0x29;
                                                }
                                                else
                                                {
                                                    StartInt=0x33;
                                                }
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=PrintDensityNow+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=PrintPaperTypeNow+0;
                                                os.write((byte)StartInt);
                                                StartInt=0x1d;
                                                os.write((byte)StartInt);
                                                StartInt=0x76;
                                                os.write((byte)StartInt);
                                                StartInt=0x30;
                                                os.write((byte)StartInt);
                                                StartInt=0x00;
                                                os.write((byte)StartInt);
                                                int widthH=bitmapWidth/8/256;
                                                int widthL=bitmapWidth/8%256;
                                                int heightH=bitmapHeight/256;
                                                int heightL=bitmapHeight%256;
                                                StartInt=widthL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=widthH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                os.write(bitmapGetByte);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x5e;
                                                os.write((byte)StartInt);

                                                Log.e("Ariful5","PrintCommand");
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Ariful6",""+e.getMessage());
                                            }
                                            countDownTimer1=new CountDownTimer(1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    long second=  (millisUntilFinished/1000);
                                                    int mysecond=Integer.parseInt(String.valueOf(second));



                                                }

                                                @Override
                                                public void onFinish() {

                                                    printtimer.setText("Print Out");
                                                    try {



                                                        Log.e("Ariful7","Go to print");

                                                    }catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.e("Ariful8",""+e.getMessage());
                                                    }

                                                    Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                            }.start();

                                            //os.flush();

                                            countDownTimer1.start();


                                        }
                                    };
                                    countDownTimer.start();
                                }
                            });
                            // thread



                        }


                        os.flush();
                        m5ocket.close();

                    } else {
                        Bitmap copyBitmap = Bitmap.createBitmap(resize);
                        final byte[] bitmapGetByte = BitmapToRGBbyteAA(copyBitmap);//convertBitmapToRGBBytes (resize);
                        Log.e("Ariful4",""+bitmapGetByte);
                        if (isConnected==false)
                        {
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected= true;
                        }
                        else{
                            m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                            m5ocket.connect();
                            isConnected= true;
                        }

                        for(int i = 0; i<totalPrintCommands;i++)//angenl111
                        {


                            os = m5ocket.getOutputStream();

                            if(resize.getHeight()>bitmapHeight)
                            {
                                bitmapHeight=1080;
                            }
                            else
                            {
                                bitmapHeight=resize.getHeight();
                            }
                            bitmapWidth=resize.getWidth();
                            Log.e("Ariful1",""+resize.getWidth());
                            Log.e("Ariful2",""+resize.getHeight());
                            Log.e("Ariful3",""+bitmap);
                            Random random=new Random();
                            int sendingnumber=random.nextInt(10);
                            int mimisecond=sendingnumber*1000;



                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    // write your code here
                                    countDownTimer =new CountDownTimer(2000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                            double seconddd=millisUntilFinished/1000;
                                            printtimer.setText("Sending Data : "+seconddd+" S");



                                        }

                                        @Override
                                        public void onFinish() {
                                            try {



                                                int StartInt=0;
                                                int PrintSpeedNow=Integer.parseInt(quantityProductPage_speed.getText().toString());
                                                int PrintDensityNow=Integer.parseInt(progressbarsechk.getText().toString());
                                                int PrintPaperTypeNow=0;
                                                StartInt=0x1d;
                                                //Gray_Arraylist.add((byte)StartInt);
                                                os.write((byte)StartInt);
                                                StartInt=0x0e;
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);

                                                StartInt=0x4D;
                                                os.write((byte)StartInt);
                                                StartInt=0x53;
                                                os.write((byte)StartInt);
                                                if(PrintSpeedNow==6)
                                                {
                                                    StartInt=0x01;//PrintSpeedNow
                                                }
                                                else if(PrintSpeedNow==5)
                                                {
                                                    StartInt=0x0b;
                                                }
                                                else if(PrintSpeedNow==4)
                                                {
                                                    StartInt=0x15;
                                                }
                                                else if(PrintSpeedNow==3)
                                                {
                                                    StartInt=0x1f;
                                                }
                                                else if(PrintSpeedNow==2)
                                                {
                                                    StartInt=0x29;
                                                }
                                                else
                                                {
                                                    StartInt=0x33;
                                                }
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=0x7E;
                                                os.write((byte)StartInt);
                                                StartInt=PrintDensityNow+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x60;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=0x50;
                                                os.write((byte)StartInt);
                                                StartInt=PrintPaperTypeNow+0;
                                                os.write((byte)StartInt);
                                                StartInt=0x1d;
                                                os.write((byte)StartInt);
                                                StartInt=0x76;
                                                os.write((byte)StartInt);
                                                StartInt=0x30;
                                                os.write((byte)StartInt);
                                                StartInt=0x00;
                                                os.write((byte)StartInt);
                                                int widthH=bitmapWidth/8/256;
                                                int widthL=bitmapWidth/8%256;
                                                int heightH=bitmapHeight/256;
                                                int heightL=bitmapHeight%256;
                                                StartInt=widthL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=widthH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightL+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                StartInt=heightH+0;//PrintDensityNow
                                                os.write((byte)StartInt);
                                                os.write(bitmapGetByte);
                                                StartInt=0x1c;
                                                os.write((byte)StartInt);
                                                StartInt=0x5e;
                                                os.write((byte)StartInt);

                                                Log.e("Ariful5","PrintCommand");
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Ariful6",""+e.getMessage());
                                            }
                                            countDownTimer1=new CountDownTimer(1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    long second=  (millisUntilFinished/1000);
                                                    int mysecond=Integer.parseInt(String.valueOf(second));



                                                }

                                                @Override
                                                public void onFinish() {

                                                    printtimer.setText("Print Out");
                                                    try {



                                                        Log.e("Ariful7","Go to print");

                                                    }catch (Exception e) {
                                                        e.printStackTrace();
                                                        Log.e("Ariful8",""+e.getMessage());
                                                    }
                                                    copyBitmap.recycle();
                                                    Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                    return;
                                                }
                                            }.start();
                                            try {
                                                os.flush();
                                                m5ocket.close();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            //os.flush();

                                            countDownTimer1.start();


                                        }
                                    };
                                    countDownTimer.start();
                                }
                            });
                            // thread


                        }
                    }
                } catch (IOException e) {
                    // Toast.makeText(CPCLFresh.this, "Try Again. Bluetooth Connection Problem.", Toast.LENGTH_SHORT).show();
                    // printtimer.setText("Try Again. Bluetooth Connection Problem.");
                    Log.e("Error : ",""+e.getMessage());
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        thread.start();
    }
  //  BitmapToRGBbyteAA
    private  byte[]  BitmapToRGBbyteAA(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList =new ArrayList<Byte>();
        int height =1080;
        if(bitmapOrg.getHeight()>height)
        {
            height=1080;
        }
        else
        {
            height=bitmapOrg.getHeight();
        }
        int width =bitmapOrg.getWidth();
        int R = 0, B = 0, G = 0;
        //int pixles;
        int []pixels = new int[width * height];
        int x = 0, y = 0;
        Byte[] Gray_Send;
        //bitSet = new BitSet();
        try {

            bitmapOrg.getPixels(pixels, 0, width, 0, 0, width, height);
            int alpha = 0xFF << 24;
            //int []i_G=new int[7];
            int []i_G=new int[13];
            int Send_Gray=0x00;
            int StartInt=0;
            char  StartWords=' ';

            int k=0;
            int Send_i=0;
            int mathFlag=0;
            for(int i = 0; i < height; i++)
            {

                k=0;
                Send_i=0;
                for (int j = 0; j <width; j++)
                {
                    int grey = pixels[width * i + j];
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);


//==================================
                    if(grey>Integer.parseInt(color_detect))
                    {
                        //bufImage[j]=0x00;
                        mathFlag=0;

                    }
                    else
                    {
                        //bufImage[j]=0x01;
                        mathFlag=1;
                    }
                    k++;
                    if(k==1)
                    {
                        Send_i=0;
                        Send_i=Send_i+128*mathFlag;//mathFlag|0x80
                    }
                    else if(k==2)
                    {
                        Send_i=Send_i+64*mathFlag;//mathFlag|0x40
                    }
                    else if(k==3)
                    {
                        Send_i=Send_i+32*mathFlag;//mathFlag|0x20
                    }
                    else if(k==4)
                    {
                        Send_i=Send_i+16*mathFlag;//mathFlag|0x10
                    }
                    else if(k==5)
                    {
                        Send_i=Send_i+8*mathFlag;//mathFlag|0x08
                    }
                    else if(k==6)
                    {
                        Send_i=Send_i+4*mathFlag;//mathFlag|0x04
                    }
                    else if(k==7)
                    {
                        Send_i=Send_i+2*mathFlag;//mathFlag|0x02
                    }
                    else if(k==8)
                    {
                        Send_i=Send_i+1*mathFlag;//mathFlag|0x01
                        Gray_ArrayList.add((byte)Send_i);

                        Send_i=0;
                        k=0;
                    }

                }
                int aBc=0;

            }


            byte[] sss=new byte[Gray_ArrayList.size()];
            Gray_Send=new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for(int xx=0;xx<Gray_Send.length;xx++){
                sss[xx]=Gray_Send[xx];
            }
            return  sss;
        } catch (Exception e) {

        }
        return null;
    }
    int print_flag = 0;

    int bitmapWidth=384;
    public  void Store_Speed()
    {
        String density = progressbarsechk.getText().toString();
        String speed = quantityProductPage_speed.getText().toString();
        String email = "abc@gmail.com";
        DensityModel densityModel=new DensityModel(speed,density,email);

        firebaseFirestore.collection("DensityAndSpeed")
                .document("abc@gmail.com")
                .set(densityModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void getValues() {
        //validating session


        try {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
            if ((TextUtils.isEmpty(name) || name.toString() == null) && (TextUtils.isEmpty(mobile) || mobile.toString() == null)) {
                Toast.makeText(RaghHostActivity.this, "No printer Connected.", Toast.LENGTH_SHORT).show();
            }
            else{
                if(name.equals("THT"))
                {

                }
                else{
                    connectedornot.setText("Connected");
                    macdetails.setText(name+" "+mobile);
                    macaddresssname.setText(""+mobile);
                    connectedornot.setTextColor(Color.parseColor("#006400"));
                    Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                    connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                }

            }




        }catch (Exception e) {
            // Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    //get device
    private class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {
        private LayoutInflater inflater;
        private Animation topAnimation, bottomAnimation, startAnimation, endAnimation;
        private SharedPreferences onBoardingPreference;
        FirebaseFirestore firebaseFirestore;
        private List<String> addedMacAddresses;
        String bluetoothName;
        UserSession userSession;
        private HashMap<String, String> user;
        private String name, email, photo, mobile,username;
        Context context;
        public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> devices) {
            super(context, 0, devices);
            inflater = LayoutInflater.from(context);
            this.context=context;
            addedMacAddresses = new ArrayList<>();
            userSession = new UserSession(context);
            getValues();;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.show2, parent, false);


            }
            BluetoothDevice device = getItem(position);
            String deviceAddress = device.getAddress();

            // Check if the device's MAC address is already added to the list
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter != null) {
                if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    bluetoothName = bluetoothAdapter.getName();
                } else {
                    bluetoothName = bluetoothAdapter.getName();
                }


                // Use the Bluetooth name as needed
            } else {
                // Bluetooth is not supported on this device
                bluetoothName = "unknown";
            }


            TextView deviceNameTextView = view.findViewById(R.id.listedd);


            deviceNameTextView.setText(device.getName() + "\n" + device.getAddress());

            RelativeLayout carditem = view.findViewById(R.id.carditem);

            firebaseFirestore = FirebaseFirestore.getInstance();


            carditem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userSession = new UserSession(context);
                    getValues();;
                    // Toast.makeText(context, ""+photo, Toast.LENGTH_SHORT).show();

                    if (ActivityCompat.checkSelfPermission(RaghHostActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(v.getContext(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                        String BlueMac = "FB:7F:9B:F2:20:B7";
                        userSession = new UserSession(context);
                        getValues();;
                        String bluename = device.getName();
                        String bluemac= device.getAddress();
                        Connected_Model connected_model=new Connected_Model(bluename,bluemac,""+bluetoothName);

                        String sessionname = device.getName();
                        String sessionmobile =device.getAddress();
                        String sessionphoto =""+photo;
                        String sessionemail = ""+email;
                        ZoneId z = ZoneId.of( "Asia/Dhaka" );
                        LocalDate today = LocalDate.now( z );
                        String sessionusername = ""+today;
                        totalPrintCommands2=0;
                        macaddresssname.setText(""+device.getAddress());
                        //allpaireddevice();
                        connectedornot.setText("Connected");
                        macdetails.setText(device.getName()+" "+device.getAddress());
                        connectedornot.setTextColor(Color.parseColor("#006400"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        bottomSheetDialog11.dismiss();

                        userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);

                    }
                    else {
                        Toast.makeText(v.getContext(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                        String BlueMac = "FB:7F:9B:F2:20:B7";
                        userSession = new UserSession(context);
                        getValues();;
                        String bluename = device.getName();
                        String bluemac= device.getAddress();
                        Connected_Model connected_model=new Connected_Model(bluename,bluemac,""+bluetoothName);

                        String sessionname = device.getName();
                        String sessionmobile =device.getAddress();
                        String sessionphoto =""+photo;
                        String sessionemail = ""+email;
                        ZoneId z = ZoneId.of( "Asia/Dhaka" );
                        LocalDate today = LocalDate.now( z );
                        String sessionusername = ""+today;
                        totalPrintCommands2=0;
                        macaddresssname.setText(""+device.getAddress());
                        // allpaireddevice();
                        connectedornot.setText("Connected");
                        macdetails.setText(device.getName()+" "+device.getAddress());
                        connectedornot.setTextColor(Color.parseColor("#006400"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        bottomSheetDialog11.dismiss();

                        userSession.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);

                    }


                }
            });
            return view;
        }
        private void getValues() {
            //validating session


            try {
                //get User details if logged in
                userSession.isLoggedIn();
                user = userSession.getUserDetails();

                name = user.get(UserSession.KEY_NAME);
                if (TextUtils.isEmpty(name)|| name.toString()==null)
                {
                    name = "Unknown";
                }
                else {
                    name=name;
                }
                email = user.get(UserSession.KEY_EMAIL);
                mobile = user.get(UserSession.KEY_MOBiLE);
                photo = user.get(UserSession.KEY_PHOTO);
                username=user.get(UserSession.Username);
                if (TextUtils.isEmpty(mobile)|| mobile.toString()==null)
                {
                    mobile = "FB:7F:9B:F2:20:B7";
                }
                else {
                    mobile=mobile;
                }
                //Toast.makeText(this, ""+photo+""+email, Toast.LENGTH_SHORT).show();




            }catch (Exception e) {
                // Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
        }
    }

    //2
    private void getValues1() {
        //validating session


        try {
            //get User details if logged in
            scason2.isLoggedIn();
            user2 = scason2.getUserDetails();

            name2 = user2.get(UserSession.KEY_NAME);
            if (TextUtils.isEmpty(name2)|| name2.toString()==null)
            {
                name2 = "Unknown";
            }
            else {
                name2=name2;
            }
            email2 = user2.get(UserSession.KEY_EMAIL);
            mobile2 = user2.get(UserSession.KEY_MOBiLE);
            photo2 = user2.get(UserSession.KEY_PHOTO);
            username2=user2.get(UserSession.Username);
            if (TextUtils.isEmpty(mobile2)|| mobile2.toString()==null)
            {
                mobile2 = "FB:7F:9B:F2:20:B7";
            }
            else {
                mobile2=mobile2;
            }
            //Toast.makeText(this, ""+photo+""+email, Toast.LENGTH_SHORT).show();




        }catch (Exception e) {
            // Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    UserSession userSession;
    void intttt(String macadressss)
    {

        //Toast.makeText(this, ""+macadressss, Toast.LENGTH_SHORT).show();
        userSession = new UserSession(RaghHostActivity.this);
        getValues();;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        connectedornot = findViewById(R.id.connectedornot);

        if (bluetoothAdapter.isEnabled())
        {
            if (bluetoothAdapter == null) {

                return;
            }
            if (!bluetoothAdapter.isEnabled()) {

                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                connectedornot = findViewById(R.id.connectedornot);

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();

                    if(TextUtils.isEmpty(macadressss)|| macadressss.toString()==null)
                    {
                        //Toast.makeText(this, "Not"+macadressss, Toast.LENGTH_SHORT).show();

                        connectedornot.setText("Not Connected");
                        connectedornot.setTextColor(Color.parseColor("#FF0000"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

                        //Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        if (deviceAddress.equals(macadressss)|| deviceAddress.contains(macadressss))
                        {
                            try {



                                connectedornot.setText("Connected");
                                macdetails.setText(deviceName+" "+deviceAddress);
                                connectedornot.setTextColor(Color.parseColor("#006400"));
                                Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                                connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

                                // Toast.makeText(this, "got"+macadressss, Toast.LENGTH_SHORT).show();

                                break;
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else {
                            //Toast.makeText(this, "Not"+macadressss, Toast.LENGTH_SHORT).show();



                            connectedornot.setText("Not Connected");

                            connectedornot.setTextColor(Color.parseColor("#FF0000"));
                            Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                            connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);



                        }
                    }

                }
            }
            else
            {

                connectedornot = findViewById(R.id.connectedornot);

                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();

                    if(TextUtils.isEmpty(macadressss)|| macadressss.toString()==null)
                    {
                        //Toast.makeText(this, "Not"+macadressss, Toast.LENGTH_SHORT).show();

                        connectedornot.setText("Not Connected");
                        connectedornot.setTextColor(Color.parseColor("#FF0000"));
                        Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                        connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

                        //Toast.makeText(this, "not", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        if (deviceAddress.equals(macadressss)|| deviceAddress.contains(macadressss))
                        {
                            try {



                                connectedornot.setText("Connected");
                                macdetails.setText(deviceName+" "+deviceAddress);
                                connectedornot.setTextColor(Color.parseColor("#006400"));
                                Drawable icon = getResources().getDrawable(R.drawable.ic_connected);
                                connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);

                                // Toast.makeText(this, "got"+macadressss, Toast.LENGTH_SHORT).show();

                                break;
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else {
                            //Toast.makeText(this, "Not"+macadressss, Toast.LENGTH_SHORT).show();



                            connectedornot.setText("Not Connected");

                            connectedornot.setTextColor(Color.parseColor("#FF0000"));
                            Drawable icon = getResources().getDrawable(R.drawable.ic_not_connected);
                            connectedornot.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);



                        }
                    }

                }


            }
        }

        //Toast.makeText(this, "mac"+macadressss, Toast.LENGTH_SHORT).show();
    }
    String[] listofconstract={"128","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
            "41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60",
            "61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80",
            "81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100",
            "101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120",
            "121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140",
            "141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160",
            "161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180",
            "181","182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199","200",
            "201","202","203","204","205","206","207","208","209","210","211","212","213","214","215","216","217","218","219","220",
            "221","222","223","224","225","226","227","228","229","230","231","232","233","234","235","236","237","238","239","240",
            "241","242","243","244","245","246","247","248","249","250","251","252","253","254"};
    private String convertDecimalToHex(int decimalNumber) {
        // Using Integer.toHexString() to convert decimal to hex
        return Integer.toHexString(decimalNumber);
    }
}
//for devices

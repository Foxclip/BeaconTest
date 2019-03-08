package com.example.beacontest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private boolean mScanning;
    private Handler handler;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initializing UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checking if device supports BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "BLE ok", LENGTH_SHORT).show();
        }

        //getting bluetooth adapter
        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        //checking if bluetooth is on
        int REQUEST_ENABLE_BT = 1;
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //scanning available devices
        scanLeDevice(REQUEST_ENABLE_BT > 0);

    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    bluetoothAdapter.stopLeScan(leScanCallback);
//                    Toast.makeText(getApplicationContext(), "Scan stopped", Toast.LENGTH_SHORT).show();
//                }
//            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
            Toast.makeText(getApplicationContext(), "Scan started", Toast.LENGTH_SHORT).show();
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
            Toast.makeText(getApplicationContext(), "Scan stopped", Toast.LENGTH_SHORT).show();
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            Toast.makeText(getApplicationContext(), "DEVICE FOUND", Toast.LENGTH_SHORT).show();
        }
    };

}

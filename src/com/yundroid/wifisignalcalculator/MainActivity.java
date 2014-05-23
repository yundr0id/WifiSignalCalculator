package com.yundroid.wifisignalcalculator;

import com.yundroid.wifisignalcalculator.receivers.WifiSignalStrengthChangedListener;
import com.yundroid.wifisignalcalculator.receivers.WifiSignalStrengthChangedReceiver;
import com.yundroid.wifisignalreceiver.R;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements WifiSignalStrengthChangedListener{
	
	private TextView txtWifiSignalStrength;
	
	private WifiManager manager;
	
	private WifiSignalStrengthChangedReceiver wifiSignalStrengthChangedReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtWifiSignalStrength 	= (TextView)findViewById(R.id.txtWifiSignalStrength);
		
		manager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		
		wifiSignalStrengthChangedReceiver = new WifiSignalStrengthChangedReceiver(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onWifiSignalStrengthChanged(int strength) {
		switch(manager.getWifiState()){
		case 1:
			txtWifiSignalStrength.setText("Open your wi-fi connection");
			break;
		case 3:
			txtWifiSignalStrength.setText("Wifi strength level: " + strength);
			break;
			default:
				break;
		}
	}

	@Override
	protected void onResume() {
		registerReceiver(wifiSignalStrengthChangedReceiver, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
		refreshWifiSignalStrength();
		super.onResume();
	}
	
	public void refreshWifiSignalStrength() {
		//WifiManager manager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		int strength = WifiManager.calculateSignalLevel(manager.getConnectionInfo().getRssi(), 4);
		onWifiSignalStrengthChanged(strength);
	}
}

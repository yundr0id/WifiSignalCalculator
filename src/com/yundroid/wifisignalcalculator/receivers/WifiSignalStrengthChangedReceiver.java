package com.yundroid.wifisignalcalculator.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class WifiSignalStrengthChangedReceiver extends BroadcastReceiver{
	private WifiSignalStrengthChangedListener listener;
	
	public WifiSignalStrengthChangedReceiver(WifiSignalStrengthChangedListener listener){
		this.listener = listener;
	}
	
	@Override
	public void onReceive(Context context, Intent intent){
		if(listener != null){
			int strength = WifiManager.calculateSignalLevel(intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0), 4);
			listener.onWifiSignalStrengthChanged(strength);
		}
	}
	
}

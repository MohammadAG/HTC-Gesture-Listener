package com.mohammadag.htcgesturelistener;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final int REQUEST_ENABLE = 0;
	private DevicePolicyManager mDPM;
	private ComponentName mDeviceAdmin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdmin = new ComponentName(MainActivity.this, AdminReceiver.class);

		if (mDPM.isAdminActive(mDeviceAdmin)) {
			((Button) findViewById(R.id.button1)).setText(R.string.disable_device_admin);
		}

		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mDPM.isAdminActive(mDeviceAdmin)) {
					Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
					intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin);
					intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
							getString(R.string.explanation));
					startActivityForResult(intent, REQUEST_ENABLE);
				} else {
					try {
						mDPM.removeActiveAdmin(mDeviceAdmin);
						((Button) findViewById(R.id.button1)).setText(R.string.enable_device_admin);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_ENABLE) {
			((Button) findViewById(R.id.button1)).setText(R.string.disable_device_admin);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
}

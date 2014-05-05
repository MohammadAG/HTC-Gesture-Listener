package com.mohammadag.htcgesturelistener;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GestureReceiver extends BroadcastReceiver {
	private static final int GESTURE_UP = 1;
	private static final int GESTURE_RIGHT = 2;
	private static final int GESTURE_DOWN = 3;
	private static final int GESTURE_LEFT = 4;
	private static final int GESTURE_CLICK = 5;

	private static final int GESTURE_NONE = -2;
	private static final int GESTURE_UNDETERMINATED = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("com.htc.action.GestureClick".equals(intent.getAction())) {
			DevicePolicyManager mDPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
			mDPM.lockNow();
		} else if ("com.htc.action.MULTIPLE_FINGER_SWIPE_EVENT".equals(intent.getAction())) {
			int Direction = intent.getIntExtra("Direction", 0);
			Log.d("HtcGestureListener", "New Gesture: " + gestureToString(Direction));
		}
	}

	private static String gestureToString(int direction) {
		switch (direction) {
		case GESTURE_LEFT:
			return "LEFT";
		case GESTURE_RIGHT:
			return "RIGHT";
		case GESTURE_UP:
			return "UP";
		case GESTURE_DOWN:
			return "DOWN";
		case GESTURE_CLICK:
			return "CLICK";
		case GESTURE_NONE:
			return "NONE";
		case GESTURE_UNDETERMINATED:
		default:
			return "UNDETERMINATED";
		}
	}
}

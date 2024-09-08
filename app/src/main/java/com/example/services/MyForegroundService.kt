package com.example.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service() {

	companion object {
		private const val TAG = "SERVICE_TAG"
		private const val CHANNEL_ID = "channel_id"
		private const val CHANNEL_NAME = "channel_name"
		private const val NOTIFICATION_ID = 1

		fun newIntent(context: Context): Intent = Intent(context, MyForegroundService::class.java)
	}

	private val coroutineScope = CoroutineScope(Dispatchers.Main)

	@SuppressLint("ForegroundServiceType")
	override fun onCreate() {
		super.onCreate()
		log("onCreate")
		createNotificationChannel()
		startForeground(NOTIFICATION_ID, createNotification())
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		log("onStartCommand")
		coroutineScope.launch {
			for (i in 0 until 100) {
				delay(1000L)
				log("Timer: $i")
			}
		}

		return START_STICKY
	}

	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
		coroutineScope.cancel()
	}

	override fun onBind(intent: Intent?): IBinder? {
		TODO("Not yet implemented")
	}

	private fun log(message: String) {
		Log.d(TAG, "MyForegroundService: $message ")
	}

	private fun createNotificationChannel() {
		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel(
				CHANNEL_ID,
				CHANNEL_NAME,
				NotificationManager.IMPORTANCE_DEFAULT
			)
			notificationManager.createNotificationChannel(notificationChannel)
		}
	}

	private fun createNotification(): Notification = NotificationCompat.Builder(this, CHANNEL_ID)
		.setContentTitle("title")
		.setContentText("text")
		.setSmallIcon(R.drawable.ic_launcher_foreground)
		.build()
}
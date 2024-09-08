package com.example.services

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class MyIntentService : IntentService(NAME) {

	companion object {
		private const val TAG = "SERVICE_TAG"
		private const val CHANNEL_ID = "channel_id"
		private const val CHANNEL_NAME = "channel_name"
		private const val NOTIFICATION_ID = 1
		private const val NAME = "name"

		fun newIntent(context: Context): Intent = Intent(context, MyIntentService::class.java)
	}

	@Deprecated("Deprecated in Java")
	@SuppressLint("ForegroundServiceType")
	override fun onCreate() {
		super.onCreate()
		log("onCreate")
		setIntentRedelivery(false)
		createNotificationChannel()
		startForeground(NOTIFICATION_ID, createNotification())
	}


	@Deprecated("Deprecated in Java")
	override fun onHandleIntent(intent: Intent?) {
		log("onHandleIntent")
		for (i in 0 until 5) {
			Thread.sleep(1000L)
			log("Timer: $i")
		}
	}

	@Deprecated("Deprecated in Java")
	override fun onDestroy() {
		super.onDestroy()
		log("onDestroy")
	}

	private fun log(message: String) {
		Log.d(TAG, "MyIntentService: $message ")
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
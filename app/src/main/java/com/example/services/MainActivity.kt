package com.example.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	companion object {
		private const val CHANNEL_ID = "channel_id"
		private const val CHANNEL_NAME = "channel_name"
	}
	private var id = 0

	private val binding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(binding.root)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		binding.simpleService.setOnClickListener {
			startService(MyService.newIntent(this, 25))
		}
		binding.foregroundService.setOnClickListener {
			showNotification()
		}
	}

	private fun showNotification() {
		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel(
				CHANNEL_ID,
				CHANNEL_NAME,
				NotificationManager.IMPORTANCE_DEFAULT
			)
			notificationManager.createNotificationChannel(notificationChannel)
		}

		val notification = NotificationCompat.Builder(this, CHANNEL_ID)
			.setContentTitle("title")
			.setContentText("text")
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.build()
		notificationManager.notify(id++, notification)
	}
}
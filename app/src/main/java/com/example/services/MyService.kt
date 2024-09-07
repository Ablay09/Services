package com.example.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

	companion object {
		private const val TAG = "SERVICE_TAG"

		fun newIntent(context: Context): Intent = Intent(context, MyService::class.java)
	}

	private val coroutineScope = CoroutineScope(Dispatchers.Main)

	override fun onCreate() {
		super.onCreate()
		log("onCreate")
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		log("onStartCommand")
		coroutineScope.launch {
			repeat(100) {
				delay (1000L)
				log("Timer: $it")
			}
		}

		return super.onStartCommand(intent, flags, startId)
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
		Log.d(TAG, "MyService: $message ")
	}
}
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

class MyIntentService2 : IntentService(NAME) {

    companion object {
        private const val TAG = "SERVICE_TAG"
        private const val NAME = "name"
        private const val PAGE = "page"

        fun newIntent(context: Context, page: Int): Intent =
            Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }


    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 5) {
            Thread.sleep(1000L)
            log("Timer: $i, $page")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d(TAG, "MyIntentService2: $message ")
    }
}
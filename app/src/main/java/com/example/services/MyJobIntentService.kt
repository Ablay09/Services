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
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat

class MyJobIntentService : JobIntentService() {

    companion object {
        private const val TAG = "SERVICE_TAG"
        private const val PAGE = "page"
        private const val JOB_ID = 111

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int): Intent =
            Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }


    override fun onHandleWork(intent: Intent) {
        log("onHandleWork")
        val page = intent.getIntExtra(PAGE, 0)
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
        Log.d(TAG, "MyJobIntentService: $message ")
    }
}
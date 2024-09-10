package com.example.services

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {
        const val WORKER_NAME = "worker name"

        private const val TAG = "SERVICE_TAG"
        private const val PAGE = "page"

        fun makeRequest(page: Int): OneTimeWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(workDataOf(PAGE to page))
            .setConstraints(makeConstraints() )
            .build()

        private fun makeConstraints(): Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
    }

    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000L)
            log("Timer: $i, $page")
        }
        return Result.success()
    }


    private fun log(message: String) {
        Log.d(TAG, "MyWorker: $message ")
    }
}
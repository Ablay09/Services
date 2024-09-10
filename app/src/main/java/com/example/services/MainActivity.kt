package com.example.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	companion object {
	}

	private var page: Int = 0

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
			stopService(MyForegroundService.newIntent(this))
			startService(MyService.newIntent(this, 25))
		}
		binding.foregroundService.setOnClickListener {
			ContextCompat.startForegroundService(
				this,
				MyForegroundService.newIntent(this)
			)
		}
		binding.intentService.setOnClickListener {
			ContextCompat.startForegroundService(
				this,
				MyIntentService.newIntent(this)
			)
		}
		binding.jobScheduler.setOnClickListener {
			val componentName = ComponentName(this, MyJobService::class.java)

			val jobInfo = JobInfo.Builder(MyJobService.JOB_ID, componentName)
				.setRequiresCharging(true)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
				.build()

			val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				val intent = MyJobService.newIntent(page++)
				jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
			} else {
				startService(MyIntentService2.newIntent(this, page++))
			}
		}
	}
}
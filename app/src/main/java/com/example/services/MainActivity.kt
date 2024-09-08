package com.example.services

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
	}
}
package com.shov.unlimstorage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.shov.unlimstorage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

		supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment? ?: return
	}
}
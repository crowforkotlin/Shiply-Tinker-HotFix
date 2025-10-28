package com.test.hotfix

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.tencent.rfix.lib.RFix
import com.tencent.rfix.lib.dev.AbsRFixDevActivity
import com.test.hotfix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tv.text = "HOT FIX COMPLETE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! \n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  "

        binding.bt.setOnClickListener {
             RFix.getInstance().requestConfig()
        }

    }
}
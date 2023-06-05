package com.example.timer

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.INotificationSideChannel
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.example.timer.databinding.ActivityMainBinding
import java.sql.Time
import java.util.Locale
import java.util.Timer
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: CountDownTimer
    private var START_TIME_IN_MILLIS: Long = 1500000
    private var timeRunning: Boolean = false
    private var timeleftinMillis: Long = START_TIME_IN_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        binding.buttonStartPause.setOnClickListener {
            if (timeRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        binding.buttonReset.setOnClickListener {
            resetTimer()
        }

        updateTimerText()


    }

    private fun pauseTimer() {
        timer.cancel()
        timeRunning = false
        binding.buttonStartPause.text = "Start"
        binding.buttonReset.visibility = View.VISIBLE
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeleftinMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeleftinMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                timeRunning = false
                binding.buttonStartPause.text ="Start"
                binding.buttonStartPause.visibility = View.INVISIBLE
                binding.buttonReset.visibility = View.VISIBLE
            }

        }.start()
        timeRunning = true
        binding.buttonStartPause.text = "Pause"
        binding.buttonReset.visibility = View.INVISIBLE
    }

    private fun resetTimer() {
        timeleftinMillis = START_TIME_IN_MILLIS
        updateTimerText()
        binding.buttonReset.visibility = View.INVISIBLE
        binding.buttonStartPause.visibility = View.VISIBLE
    }

    private fun updateTimerText() {
        var minute: Int = ((timeleftinMillis / 1000) / 60).toInt()
        var seconds: Int = ((timeleftinMillis / 1000) % 60).toInt()

        var timeFormat: String = String.format(Locale.getDefault(), "%02d:%02d", minute, seconds)
        binding.mcounterText.text = timeFormat
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting -> Toast.makeText(this, "Setting menu selected ", Toast.LENGTH_SHORT).show()
        }
        return true
    }

}
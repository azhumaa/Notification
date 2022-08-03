package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import com.example.notification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private val channel_id = "channel_1"

    private lateinit var binding: ActivityMainBinding
    //binding mengambil xml ke activity penganti findviewbyid
    private lateinit var countDownTimer: CountDownTimer
    //class untuk megatur timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //layoutinflater: menetapkan sebuah xml terikat pada suatu activity
        setContentView(binding.root)
        //binding.root: mengikat bagian terluar dari activity

        binding.btnStart.setOnClickListener {
            countDownTimer.start()
        }
        //custom button untuk memulai (berawal dari hitungan mundur dan notif)

        countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(p0: Long) {
                binding.timer.text = getString(R.string.time_reamining, p0 / 1000)
            }
            //untuk mengatur waktu hitungan mundur 5000 = 5 detik

            override fun onFinish() {
                displayNotification()
            }
        }
        //memunculkan aksi stelah hitungan mundur selesai disini diperintahkan untuk mengeluarkan notif

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channel_id, name, importance)
            mChannel.description = descriptionText
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager!!.createNotificationChannel(mChannel)
        }
        //agar applikasi bisa berjalan di semua divice (sdk custom)
    }


    private fun displayNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            //untuk memindahkan satu activity ke activity lain bisa juga di sebut kembali
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val notificationId = 45
        val builder = NotificationCompat.Builder(this, channel_id)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("ex app")
            .setContentText("yoo bro apa cabs")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager!!.notify(notificationId, builder)

        //notification custom : untuk menyeting bentuk notifikasi yang di ingin kan


    }

}

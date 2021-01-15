package com.nikolam.qupidon.notifications


import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nikolam.qupidon.R
import org.json.JSONObject
import timber.log.Timber

class NotificationService() : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d(remoteMessage.data.toString())
        notify(remoteMessage.data["name"], remoteMessage.data["contents"])
    }

        fun notify(title: String?, message: String?) {

            val builder = NotificationCompat.Builder(this, "messages")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val managerCompat: NotificationManagerCompat = NotificationManagerCompat.from(this)
            managerCompat.notify(123, builder.build())
        }

    override fun onNewToken(p0: String) {
        val sharedPref = getSharedPreferences("FirebaseToken", MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("token", p0)
            apply()
        }

        Timber.d("FIREBASE token is $p0")

        super.onNewToken(p0)
    }
}

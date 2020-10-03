package com.example.gossip

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Some features are yet to be implemented
 */
class GossipFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received
     *
     * @param remoteMessage Object representing the message from Firebase cloud messaging
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO (Do something on message received)
        Log.d("message received", "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        sendNotification(remoteMessage)

    }

    /**
     * Called when token is changed
     *
     * @param token is the new token in, String format
     */
    override fun onNewToken(token: String) {
        Log.d("Token", "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    /**
     * Sends token to the server
     *
     * @param token the token we are sending to server, String format
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d("TokenToServer", "sendRegistrationTokenToServer($token)")
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")
    }

    /**
     * Sends out notifications. Also creates a notification channel if APK is greater than 26
     * due to android requirements.
     *
     * TODO:
     * The notification sent out is clickable. Should take you to the user that sent the message.
     *
     * @param messageBody is the message that is we are sending in the notification
     */
    private fun sendNotification(messageBody: RemoteMessage) {

        val intent = Intent(this, MessagesMenu::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)
        //TODO: Launch the chat log with the appropriate stuff like flags and name

        val channelId = getString(R.string.FCM_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(getString(R.string.FCM_message))
            .setContentText(messageBody.toString())
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
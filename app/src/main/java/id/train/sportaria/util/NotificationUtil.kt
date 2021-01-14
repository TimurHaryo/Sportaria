package id.train.sportaria.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import id.train.sportaria.R
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class NotificationUtil
@Inject constructor(
    private val notificationManager: NotificationManager
) {

    operator fun invoke(
        context: Context,
        title: String,
        message: String,
        onSuccess: Boolean,
        priority: Int = NotificationManager.IMPORTANCE_MAX
    ) {
        val notificationBuilder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buildNotificationChannel(priority)
                buildOreoNotification(context, title, message, onSuccess)
            } else {
                buildUnderOreoNotification(context, title, message, onSuccess)
            }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun buildNotificationChannel(priority: Int) {
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, priority).apply {
            enableLights(false)
            enableVibration(false)
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(this)
        }
    }

    private fun buildOreoNotification(
        context: Context,
        title: String,
        message: String,
        onSuccess: Boolean
    ): Notification.Builder {
        val smallIcon = if (onSuccess) {
            R.drawable.ic_baseline_check_green_24
        } else {
            R.drawable.ic_baseline_cancel_red_24
        }

        return Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
    }

    @Suppress("DEPRECATION")
    private fun buildUnderOreoNotification(
        context: Context,
        title: String,
        message: String,
        onSuccess: Boolean
    ): Notification.Builder {
        val smallIcon = if (onSuccess) {
            R.drawable.ic_baseline_check_green_24
        } else {
            R.drawable.ic_baseline_cancel_red_24
        }

        return Notification.Builder(context)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
    }

    companion object {
        private const val CHANNEL_ID = "id.train.fooball.notification"
        private const val CHANNEL_NAME = "Fooball Notification"
        private const val NOTIFICATION_ID = 1001
    }
}
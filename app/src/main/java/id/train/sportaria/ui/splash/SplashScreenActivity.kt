package id.train.sportaria.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseActivity
import id.train.sportaria.databinding.ActivitySplashScreenBinding
import id.train.sportaria.ui.main.MainActivity
import id.train.sportaria.util.SecretDoor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    @Inject
    lateinit var handler: Handler

    override fun resourceLayoutId(): Int = R.layout.activity_splash_screen

    override fun initView() {
        val intent = Intent(this, MainActivity::class.java)

        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 2500)
    }

}
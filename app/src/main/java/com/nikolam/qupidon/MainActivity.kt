package com.nikolam.qupidon

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.nikolam.common.messaging.MessagingManager
import com.nikolam.common.navigation.NavManager
import com.nikolam.qupidon.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val navController get() = findNavController(R.id.navHostFragment)

    private lateinit var binding: ActivityMainBinding

    private val navManager: NavManager by inject()

    private val messagingManager: MessagingManager by inject()

    //private val facebookCallBackManager: CallbackManager by inject()

    private fun initNavManager() {
        navManager.setOnNavEvent {
            navController.navigate(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavManager()

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        // The window will not be resized when virtual keyboard is shown (bottom navigation bar will be
        // hidden under virtual keyboard)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        Timber.v("onCreate ${javaClass.simpleName}")
    }

    override fun onRestart() {
        messagingManager.reconnect()
        super.onRestart()
    }

    override fun onStop() {
        messagingManager.disconnect()
        super.onStop()
    }
}
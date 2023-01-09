package ru.vsibi.btc_mathematic

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.koin.viewModel
import ru.vsibi.btc_mathematic.databinding.ActivityMainBinding
import ru.vsibi.btc_mathematic.navigation.BackPressConsumer
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.util.doOnStartStop

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragmentContainerId = R.id.container

    private val navigator by lazy { CustomAppNavigator(this, fragmentContainerId) }
    private val navigatorHolder: NavigatorHolder by inject()
    private val router: RootRouter by inject()

    private val vm: MainActivityViewModel by getKoin().viewModel(
        owner = { ViewModelOwner.from(this, this) },
    )

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        navigatorHolder.removeNavigator()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (savedInstanceState == null) {
            handleIntent(intent)
        }

        lifecycle.doOnStartStop(onStart = vm::onViewActive, onStop = vm::onViewInactive)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(fragmentContainerId)
        if (fragment is BackPressConsumer && fragment.onBackPressed()) {
            return
        }
        router.exit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
//        val pushClickResult = pushFeature.handlePushClick(intent)
//        if (pushClickResult != null)
//            vm.onAppStarted(true, pushClickResult)
//        else
//            vm.onAppStarted(false, null)
    }

}
package ru.vsibi.btc_mathematic

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.github.terrakok.cicerone.NavigatorHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.koin.viewModel
import ru.vsibi.btc_mathematic.databinding.ActivityMainBinding
import ru.vsibi.btc_mathematic.mvi.BaseViewEvent
import ru.vsibi.btc_mathematic.navigation.BackPressConsumer
import ru.vsibi.btc_mathematic.navigation.RootRouter
import ru.vsibi.btc_mathematic.settings_api.SettingsFeature
import ru.vsibi.btc_mathematic.util.LocaleUtil
import ru.vsibi.btc_mathematic.util.collectLatestWhenStarted
import ru.vsibi.btc_mathematic.util.collectWhenStarted
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
    private val settingsFeature: SettingsFeature by inject()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        navigatorHolder.removeNavigator()
        navigatorHolder.setNavigator(navigator)
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = runBlocking { settingsFeature.getSavedLocale() }
        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(locale))
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (savedInstanceState == null) {
            handleIntent(intent)
        }

        lifecycle.doOnStartStop(onStart = vm::onViewActive, onStop = vm::onViewInactive)

        vm.viewEvent.collectWhenStarted(this, ::onRecieveEvent)
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


    private fun onRecieveEvent(event: BaseViewEvent<MainActivityEvent>) {
        when (event) {
            is BaseViewEvent.ScreenEvent -> {
                when (event.event) {
                    is MainActivityEvent.OnLocaleChanged -> {
                        LocaleUtil.applyLocalizedContext(applicationContext, (event.event as MainActivityEvent.OnLocaleChanged).locale)
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
            is BaseViewEvent.ShowDialog -> Unit
            is BaseViewEvent.ShowMessage -> Unit
        }
    }

}
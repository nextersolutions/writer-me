package io.writerme.resources.common

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.StringRes
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultFadeAnimation

abstract class BaseScreen : IScreen {

    protected fun showToast(context: Context, @StringRes message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    protected fun onShowError(
        message: String?,
        navigator: RootController,
        onConfirmClicked: () -> Unit
    ) {
        // TODO
    }

    protected fun onShowLostNetworkConnection(
        navigator: RootController,
        onConfirmClicked: () -> Unit
    ) {
        // TODO
    }

    protected fun openOnWeb(context: Context, url: String) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    protected fun isLocationEnabled(context: Context): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    protected fun navigateToMainFlow(
        navigator: RootController,
        launchFlag: LaunchFlag = LaunchFlag.ClearPrevious
    ) {
        navigator
            .findRootController()
            .present(
                screen = NavigationGraphTree.HOME_FLOW.name,
                launchFlag = launchFlag
            )
    }

    protected fun navigateWithFade(
        navigator: RootController,
        screen: String,
        launchFlag: LaunchFlag? = LaunchFlag.SingleInstance
    ) {
        navigator.launch(
            screen = screen,
            animationType = defaultFadeAnimation(),
            launchFlag = launchFlag
        )
    }

    protected fun pushWithoutBack(
        navigator: RootController,
        screen: String,
        launchFlag: LaunchFlag = LaunchFlag.ClearPrevious,
        params: Any? = null
    ) {
        navigator
            .findRootController()
            .launch(
                screen = screen,
                animationType = AnimationType.None,
                launchFlag = launchFlag,
                params = params
            )
    }

    protected fun navigateWithPush(
        navigator: RootController,
        screen: String,
        launchFlag: LaunchFlag = LaunchFlag.SingleInstance,
        params: Any? = null
    ) {
        navigator
            .findRootController()
            .launch(
                screen = screen,
                animationType = AnimationType.None,
                launchFlag = launchFlag,
                params = params
            )
    }

    protected fun navigateWithPresent(
        navigator: RootController,
        screen: String,
        launchFlag: LaunchFlag = LaunchFlag.SingleInstance,
        params: Any? = null
    ) {
        navigator
            .findRootController()
            .present(
                screen = screen,
                launchFlag = launchFlag,
                params = params
            )
    }

    protected fun navigateBack(navigator: RootController) {
        navigator.popBackStack()
    }

    fun openAppSettings(context: Context) {
        Intent(Settings.ACTION_BIOMETRIC_ENROLL).also {
            context.startActivity(it)
        }
    }
}

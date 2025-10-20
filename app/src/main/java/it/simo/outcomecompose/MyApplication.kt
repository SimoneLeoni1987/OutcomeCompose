package it.simo.outcomecompose

import android.app.Application
import it.simo.outcomecompose.betslip.TempBetslipHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        TempBetslipHelper.init(applicationScope)
    }
}
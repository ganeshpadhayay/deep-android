package com.example.ganesh.deep_android.coroutines

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.UNMETERED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.ganesh.deep_android.coroutines.workmanager.CoroutinesExampleRefreshDataWorker
import java.util.concurrent.TimeUnit

/**
 * Created by Ganesh Padhayay on 17/05/21.
 */

/**
 * Override application to setup background work via [WorkManager]
 */
class CoroutinesExampleApp : Application() {
    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        setupWorkManagerJob()
    }

    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupWorkManagerJob() {
        // initialize WorkManager with a Factory
        val workManagerConfiguration = Configuration.Builder()
                .setWorkerFactory(CoroutinesExampleRefreshDataWorker.Factory())
                .build()
        WorkManager.initialize(this, workManagerConfiguration)

        // Use constraints to require the work only run when the device is charging and the network is unmetered
        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(UNMETERED)
                .build()

        // Specify that the work should attempt to run every day
        val work = PeriodicWorkRequestBuilder<CoroutinesExampleRefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        // Enqueue it work WorkManager, keeping any previously scheduled jobs for the same work.
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(CoroutinesExampleRefreshDataWorker::class.java.name, KEEP, work)
    }
}
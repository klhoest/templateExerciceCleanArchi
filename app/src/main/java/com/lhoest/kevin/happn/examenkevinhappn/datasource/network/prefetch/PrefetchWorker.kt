package com.lhoest.kevin.happn.examenkevinhappn.datasource.network.prefetch

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.lhoest.kevin.happn.examenkevinhappn.model.Day


class PrefetchWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val json = getForecast()
        return Result.success()
    }

    private fun getForecast(): List<Day> {
        //DayService.getDayList()
        throw NotImplementedError()
    }
}
package com.lhoest.kevin.happn.examenkevinhappn.repository

import com.lhoest.kevin.happn.examenkevinhappn.datasource.storage.DayDao
import com.lhoest.kevin.happn.examenkevinhappn.model.Day
import com.lhoest.kevin.happn.examenkevinhappn.model.Main
import com.lhoest.kevin.happn.examenkevinhappn.model.Weather
import kotlinx.coroutines.delay
import javax.inject.Inject


class DayRepositoryStub @Inject constructor() : DayRepository {

    val stubMain1 = Main(1.0, 2.0, 3.0, 1000.0, 500.0, 700.0, 3, 50.0)
    val stubWeather = Weather(1, "cloud", "some clouds", null)
    val itemList = listOf(
            Day(1485799200, stubMain1, listOf(stubWeather), null, null, null, null, null)
    )

    override var dayServiceStorage: DayDao?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override suspend fun getDayList(size: Int?): List<Day> {
        delay(3000)
        return itemList
    }
}
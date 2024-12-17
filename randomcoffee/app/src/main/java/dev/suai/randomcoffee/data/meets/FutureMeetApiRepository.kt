package dev.suai.randomcoffee.data.meets

import dev.suai.randomcoffee.domain.FutureMeetRepository
import dev.suai.randomcoffee.domain.entity.FutureMeet
import dev.suai.randomcoffee.schema.api.MeetApi
import java.time.LocalDate
import javax.inject.Inject


class FutureMeetApiRepository @Inject constructor(
    private val meetApi: MeetApi,
) : FutureMeetRepository {
    override suspend fun getFutureMeet(): FutureMeet? {

        val response = meetApi.meetFutureGet()
        if (!response.isSuccessful || response.body() == null) {
            return null
        }

        if (response.body()?.id == null)
            return null

        return response.body().let {
            FutureMeet(
                date = it?.date ?: LocalDate.now(),
                approved = it?.approved ?: false,
                id = it?.id ?: -1,
                slot = it?.slot ?: 0,
                secondTg = it?.secondTg ?: "durov",
                secondName = it?.secondName ?: "Дуров"
            )
        }
    }

    override suspend fun approveMeet(id: Int) {
        val response = meetApi.meetApproveMeetIdPost(meetId = id)
    }
}


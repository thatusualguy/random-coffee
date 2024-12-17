package dev.suai.randomcoffee.data.meets

import dev.suai.randomcoffee.domain.FutureMeetRepository
import dev.suai.randomcoffee.domain.entity.FutureMeet
import java.time.LocalDate
import javax.inject.Inject


class FutureMeetMockRepository @Inject constructor() : FutureMeetRepository {

    private var approved = false

    override suspend fun getFutureMeet(): FutureMeet? {

        return FutureMeet(
            date = LocalDate.now().plusDays(1),
            approved = approved,
            id = -1,
            slot = 1,
            secondTg = "durov",
            secondName = "Дуров"
        )
    }

    override suspend fun approveMeet(id: Int) {
        approved = true
    }
}


package dev.suai.randomcoffee.data.interests

import dev.suai.randomcoffee.domain.InterestsRepository
import dev.suai.randomcoffee.domain.entity.Interest
import dev.suai.randomcoffee.domain.entity.InterestGroup
import dev.suai.randomcoffee.domain.entity.InterestsResult
import dev.suai.randomcoffee.schema.api.InterestsApi
import javax.inject.Inject

class InterestsMockRepository @Inject constructor(
    private val api: InterestsApi,
) : InterestsRepository {

    private var selectedInterests: MutableSet<Interest> = mutableSetOf()
    override suspend fun getInterests(): InterestsResult {
        val allInterests = listOf(
            InterestGroup(
                "Технологии", listOf(
                    Interest("Веб", 1),
                    Interest("БД", 2),
                )
            ),
            InterestGroup(
                "Спорт", listOf(
                    Interest("Крикет", 2),
                    Interest("Гольф", 3),
                )
            ),
            InterestGroup(
                "Музыка", listOf(
                    Interest("Рок", 2),
                    Interest("Поп", 3),
                    Interest("Рок", 2),
                    Interest("Поп", 3),
                    Interest("Рок", 2),
                    Interest("Поп", 3),
                    Interest("Рок", 2),
                    Interest("Поп", 3),
                )
            ),
        )


        return InterestsResult(allInterests, selectedInterests.toList())
    }

    override fun toggleInterest(interest: Interest) {
        if (interest in selectedInterests)
            selectedInterests += interest
        else
            selectedInterests -= interest

    }

    override suspend fun saveInterests() {

    }

    override fun getSelected(): List<Interest> {
        return selectedInterests.toList()
    }

}
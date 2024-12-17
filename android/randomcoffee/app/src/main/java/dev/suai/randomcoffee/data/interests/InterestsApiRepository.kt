package dev.suai.randomcoffee.data.interests

import dev.suai.randomcoffee.domain.InterestsRepository
import dev.suai.randomcoffee.domain.entity.Interest
import dev.suai.randomcoffee.domain.entity.InterestsResult
import dev.suai.randomcoffee.schema.api.InterestsApi
import javax.inject.Inject
import dev.suai.randomcoffee.domain.entity.Interest as EntityInterest
import dev.suai.randomcoffee.domain.entity.InterestGroup as EntityInterestGroup
import dev.suai.randomcoffee.schema.model.Interest as ModelInterest
import dev.suai.randomcoffee.schema.model.InterestGroup as ModelInterestGroup

class InterestsApiRepository @Inject constructor(
    private val api: InterestsApi,
) : InterestsRepository {

    private var selectedInterests: MutableSet<EntityInterest> = mutableSetOf()

    override suspend fun getInterests(): InterestsResult {

        try {
            val response = api.interestsGet()
            if (response.isSuccessful && response.body() != null) {
                val all = response.body()!!.allInterests?.map {
                    convert(it)
                } ?: emptyList()
                val selected = response.body()!!.userInterests?.map {
                    convert(it)
                } ?: emptyList()

                return InterestsResult(all, selected)
            } else {
                return InterestsResult(emptyList(), emptyList())
            }
        } catch (e: Exception) {
            return InterestsResult(emptyList(), emptyList())
        }
    }

    private fun convert(interest: ModelInterest): EntityInterest {
        return EntityInterest(
            id = interest.id?.toInt() ?: 0,
            name = interest.name ?: ""
        )
    }

    override fun toggleInterest(interest: EntityInterest) {
        if (interest in selectedInterests)
            selectedInterests += interest
        else
            selectedInterests -= interest
    }

    override suspend fun saveInterests() {
        try {
            api.interestsPost(
                interest = selectedInterests.map {
                    ModelInterest(
                        id = it.id.toLong(),
                        name = it.name
                    )
                }
            )
        } catch (_: Exception) {
        }
    }

    override fun getSelected(): List<Interest> {
        return selectedInterests.toList()
    }


    private fun convert(interest: ModelInterestGroup): EntityInterestGroup {
        return EntityInterestGroup(
            name = interest.name ?: "",
            interests = interest.items?.map { convert(it) } ?: emptyList()
        )
    }
}

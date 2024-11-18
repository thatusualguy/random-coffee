package dev.suai.randomcoffee.data.interests

import dev.suai.randomcoffee.domain.InterestsRepository
import dev.suai.randomcoffee.domain.entity.Interest
import dev.suai.randomcoffee.domain.entity.InterestsResult
import dev.suai.randomcoffee.schema.api.InterestsApi
import javax.inject.Inject

class InterestsApiRepository @Inject constructor(
    private val api: InterestsApi,
) : InterestsRepository {

    var selectedInterests: MutableSet<Interest> = mutableSetOf()

    override suspend fun getInterests(): InterestsResult {
//        TODO("Not yet implemented")
        return InterestsResult(emptyList(), emptyList())
    }

    override fun toggleInterest(interest: Interest) {
        if (interest in selectedInterests)
            selectedInterests += interest
        else
            selectedInterests -= interest
    }

    override suspend fun saveInterests() {
//        api.interestsUserIdPost()
    }

}

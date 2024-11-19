package dev.suai.randomcoffee.domain

import dev.suai.randomcoffee.domain.entity.Interest
import dev.suai.randomcoffee.domain.entity.InterestsResult

interface InterestsRepository {
    suspend fun getInterests(): InterestsResult
    fun toggleInterest(interests:Interest)
    suspend fun saveInterests()
}
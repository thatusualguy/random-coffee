package dev.suai.randomcoffee.domain

import dev.suai.randomcoffee.domain.entity.FutureMeet

interface FutureMeetRepository {
    suspend fun getFutureMeet(): FutureMeet?
    suspend fun approveMeet(id: Int)
}

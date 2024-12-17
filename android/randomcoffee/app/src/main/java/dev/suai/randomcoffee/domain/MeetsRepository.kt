package dev.suai.randomcoffee.domain

import dev.suai.randomcoffee.domain.entity.Meet

interface MeetsRepository {
    suspend fun getHistory() : List<Meet>
}
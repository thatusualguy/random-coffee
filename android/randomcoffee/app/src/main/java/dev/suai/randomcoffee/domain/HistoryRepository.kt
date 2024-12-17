package dev.suai.randomcoffee.domain

import dev.suai.randomcoffee.domain.entity.Meet

interface HistoryRepository {
    suspend fun getHistory(): List<Meet>
}
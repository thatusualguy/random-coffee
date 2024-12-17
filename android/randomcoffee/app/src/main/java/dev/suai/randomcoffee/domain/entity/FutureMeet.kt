package dev.suai.randomcoffee.domain.entity

import java.time.LocalDate

data class FutureMeet(
	val date: LocalDate,
	val approved: Boolean,
	val id: Int,
	val slot: Int,
	val secondTg: String,
	val secondName: String
)


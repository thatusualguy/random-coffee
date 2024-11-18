package dev.suai.randomcoffee.domain.entity


data class InterestGroup(val name: String, val interests: List<Interest>)

data class Interest(val name: String, val id:Int)

package com.learn.sevasahyog.ngo_home.items.event.data

import com.learn.sevasahyog.ngo_home.data.NgoAccount

data class Event(
    val eventId: Int,
    val name: String,
    val shortDesc: String,
    val longDesc: String,
    val organizer: String,
    val organizerPhone: String?, // Nullable
    val location: String?, // Nullable
    val dd: Int,
    val mm: Int,
    val yyyy: Int,
    val status: Int,
    val ngoAccount: NgoAccount
)

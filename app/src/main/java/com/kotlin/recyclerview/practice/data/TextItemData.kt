package com.kotlin.recyclerview.practice.data

import java.io.Serializable

data class TextItemData(
    val id: Long,
    var text: String,
) : Serializable

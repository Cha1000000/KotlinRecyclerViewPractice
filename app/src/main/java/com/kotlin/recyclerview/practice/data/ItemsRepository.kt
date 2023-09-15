package com.kotlin.recyclerview.practice.data

fun initTextInputItems() : List<TextItemData> {
    val list = ArrayList<TextItemData>()
    for (i in 1..10) list.add(TextItemData(i.toLong(), ""))
    return list
}
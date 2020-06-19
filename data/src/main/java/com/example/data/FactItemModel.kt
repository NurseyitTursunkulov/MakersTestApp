package com.example.data

import androidx.room.PrimaryKey
import java.util.*


data class Item(
    var name: String = "",
    var price: Int = 0,
    var desc: String = "",
    var company: String = "",
    var category: String="",
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var img: String = ""
)

data class ItemListModel(var data: List<Item>)
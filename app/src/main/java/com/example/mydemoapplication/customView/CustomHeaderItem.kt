package com.example.mydemoapplication.customView

import androidx.leanback.widget.HeaderItem

class CustomHeaderItem(id: Long,
                       name: String?,
                       var item: Any? = null): HeaderItem(id, name)
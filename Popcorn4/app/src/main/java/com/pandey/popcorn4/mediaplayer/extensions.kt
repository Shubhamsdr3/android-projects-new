package com.pandey.popcorn4.mediaplayer

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.startFragment(@IdRes resourceId: Int, fragment: Fragment) {
    val ft = this.supportFragmentManager.beginTransaction()
    ft.add(resourceId, fragment, fragment::class.java.simpleName)
    ft.addToBackStack(null)
    ft.commit()
}
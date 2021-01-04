package com.pandey.popcorn4.mediaplayer

import android.content.Context
import android.util.AttributeSet
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseCustomView
import kotlinx.android.synthetic.main.audion_player_view.view.*

class AudioPlayerView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defyStyle: Int = 0): BaseCustomView(context, attributeSet, defyStyle) {


    override fun initLayout() {
        super.initLayout()
    }

    override fun initListener() {
        super.initListener()

        media_control_button.setOnClickListener {

        }
    }

    override fun getLayoutFile(): Int {
        return R.layout.audion_player_view
    }

}
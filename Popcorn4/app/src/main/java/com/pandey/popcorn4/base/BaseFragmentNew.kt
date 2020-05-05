package com.pandey.popcorn4.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.pandey.popcorn4.R

open abstract class BaseFragmentNew : Fragment() {

    /**
     * Callback to communicate between activity to fragment
     * or fragment to fragment.
     */
    private lateinit var mListener : InteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (parentFragment != null) {
            if (parentFragment is InteractionListener) {
                // if the fragment is started from other fragment
                mListener = parentFragment as InteractionListener
            } else {
                throw RuntimeException(context.toString()
                        + "Bhai, callback to define kar de"
                )
            }
        } else {
            // if the fragment is started from activity
            if (context is InteractionListener) {
                mListener = context
            } else {
                throw RuntimeException(context.toString()
                        + " Bhai, callback to define kar de"
                )
            }
        }
    }

    fun getCommunicator() : InteractionListener{
        return mListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.fragment_base, container, false)
        val inflatedView: View = inflater.inflate(getLayoutFile(), parentView.findViewById(R.id.main_container), false)
        (parentView.findViewById<View>(R.id.main_container) as FrameLayout).addView(inflatedView, 0)
        return parentView
    }

    abstract fun getLayoutFile(): Int
}
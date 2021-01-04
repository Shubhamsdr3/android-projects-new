package com.pandey.popcorn4.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class NewBaseFragment<VB: ViewBinding> : Fragment() {

    var viewBinding: VB? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = getBindingView()
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initListener()
    }

    protected abstract fun initLayout()

    protected abstract fun initListener()

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    protected abstract fun getBindingView(): VB
}
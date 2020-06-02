package com.pandey.popcorn4.chat.ui

import android.content.Intent
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ListenerRegistration
import com.pandey.popcorn4.AppConstants
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import com.pandey.popcorn4.chat.ChatActivity
import com.pandey.popcorn4.chat.adapter.PersonItemAdapter
import com.pandey.popcorn4.chat.util.FireStoreUtil
import com.pandey.popcorn4.customeviews.TitleTextToolbar
import com.xwray.groupie.*
import kotlinx.android.synthetic.main.fragment_people.*

class PeopleFragment: BaseFragment<PeopleFragment.PeopleFragmentListener>() {

    private lateinit var userListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var peopleSection: Section

    companion object {
        fun newInstance() : PeopleFragment {
            return PeopleFragment()
        }
    }

    override fun initLayout() {

    }

    override fun initListeners() {
        userListenerRegistration = FireStoreUtil.addUserListener(this.requireActivity(), this::updateRecyclerView)
    }

    private fun updateRecyclerView(items: List<Item<ViewHolder>>) {
        fun init() {
            people_list.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun update() = peopleSection.update(items)

        if (shouldInitRecyclerView) {
            init()
        } else {
            update()
        }
    }

    private val onItemClick = OnItemClickListener { item, view ->
        if (item is PersonItemAdapter) {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(AppConstants.USER_NAME, item.person.name)
            intent.putExtra(AppConstants.USER_ID, item.userId)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FireStoreUtil.removeRegistrationListener(userListenerRegistration)
        shouldInitRecyclerView = true
    }

    override fun getToolBar(): FrameLayout? {
        return TitleTextToolbar(
                requireContext(),
                "People",
                false
        )
    }

    override fun getListenerClass(): Class<PeopleFragmentListener> {
        return PeopleFragmentListener::class.java
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_people
    }

    interface PeopleFragmentListener {

    }
}
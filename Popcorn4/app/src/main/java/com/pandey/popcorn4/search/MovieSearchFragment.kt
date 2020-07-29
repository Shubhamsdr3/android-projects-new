package com.pandey.popcorn4.search

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import com.pandey.popcorn4.customeviews.KeyboardUtils
import com.pandey.popcorn4.movie.data.MoviesResponseDto
import com.pandey.popcorn4.movie.data.RecentMovieSearched
import com.pandey.popcorn4.search.MovieSearchAdapterFilterable.MovieSearchAdapterListener
import com.pandey.popcorn4.search.MovieSearchPresenter.MovieSearchView
import kotlinx.android.synthetic.main.fragment_movie_search.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class MovieSearchFragment : BaseFragment<MovieSearchFragment.MovieSearchFragmentListener>(),
        MovieSearchAdapterListener, MovieSearchView {

    private val RECENT_MOVIE_SEARCH = "RECENT_MOVIE_SEARCH"

    private var movieSearchPresenter: MovieSearchPresenter? = null

    private var sharedpreferences: SharedPreferences? = null

    private var itemCount: Int = 0

    /**
     * To cache the recent searches.
     */
    private val mRootRef = FirebaseDatabase.getInstance().reference

    var recentSearches = HashMap<String, RecentMovieSearched>()

    private val moviesList = ArrayList<MoviesResponseDto>()

    private var mListener: MovieSearchFragmentListener? = null

    override fun initLayout() {
        sharedpreferences = activity!!.getSharedPreferences(RECENT_MOVIE_SEARCH, Context.MODE_PRIVATE)
        movieSearchPresenter = MovieSearchPresenter(this)
    }

    override fun initListeners() {
        search_edit_text.requestFocus()
        KeyboardUtils.showKeyboard(search_edit_text.context, search_edit_text)

        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                movieSearchPresenter?.fetchSearchedMovie(s.toString())
            }
        })

        nav_back.setOnClickListener {
            KeyboardUtils.hideKeyword(search_edit_text.context, search_edit_text)
            activity?.onBackPressed()
        }
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_movie_search
    }

    override fun onMovieSelected(moviesResponseDto: MoviesResponseDto?) {
        itemCount++
        // Saving to fire base
        val recentMovieSearched = RecentMovieSearched(moviesResponseDto!!.id,
                moviesResponseDto.popularity,
                moviesResponseDto.isAdult,
                moviesResponseDto.poster_path,
                moviesResponseDto.title,
                moviesResponseDto.original_language,
                moviesResponseDto.overview,
                moviesResponseDto.release_date)
        recentSearches[itemCount.toString()] = recentMovieSearched

        Timber.i("Saving searched to db..%s", recentSearches)
        mRootRef.child("recentSearch").setValue(recentSearches)

        mListener?.onMovieSelectedFromSearch(moviesResponseDto)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mListener = if (context is MovieSearchFragmentListener) {
            context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onMovieFetchedSuccess(moviesResponseDtoList: MutableList<MoviesResponseDto>) {
        no_result_found.visibility = View.GONE
        KeyboardUtils.hideKeyword(search_edit_text.context, search_edit_text)
        moviesList.addAll(moviesResponseDtoList)

        val movieSearchAdapterFilterable = MovieSearchAdapterFilterable(context!!, moviesList, this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        searched_movie_list.layoutManager = mLayoutManager
        searched_movie_list.itemAnimator = DefaultItemAnimator()
        searched_movie_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        searched_movie_list.adapter = movieSearchAdapterFilterable
        movieSearchAdapterFilterable.notifyDataSetChanged()
    }

    override fun onMovieFetching() {

    }

    override fun onMovieFetchedFailed() {
        no_result_found.visibility = View.VISIBLE
        KeyboardUtils.hideKeyword(search_edit_text.context, search_edit_text)
    }

    override fun getToolBar(): FrameLayout? {
        return null
    }

    override fun getListenerClass(): Class<MovieSearchFragmentListener> {
        return MovieSearchFragmentListener::class.java
    }

    interface MovieSearchFragmentListener {

        fun onMovieSelectedFromSearch(moviesResponseDto: MoviesResponseDto?)
    }
}
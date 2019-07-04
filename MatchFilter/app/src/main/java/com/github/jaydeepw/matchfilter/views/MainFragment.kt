package com.github.jaydeepw.matchfilter.views

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.jaydeepw.matchfilter.R
import com.github.jaydeepw.matchfilter.models.entities.Match
import com.github.jaydeepw.matchfilter.utils.DebugLog
import com.github.jaydeepw.matchfilter.viewmodels.MainViewModel
import retrofit2.Response

class MainFragment : BaseFragment() {

    private var notificaitonsViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificaitonsViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)
            .create(MainViewModel::class.java)
        notificaitonsViewModel?.repository
            ?.getMatches()
            ?.observe(this, Observer<Response<Match>> { response -> showList(response)})

        notificaitonsViewModel?.repository?.loading?.observe(this,
            Observer<Boolean> { isLoading -> handleLoadingProgress(isLoading) })
    }

    private fun showList(response: Response<Match>?) {

    }

    private fun handleLoadingProgress(loading: Boolean) {
        DebugLog.d("loading: ${loading}")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_filter -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
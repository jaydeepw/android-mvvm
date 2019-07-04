package com.github.jaydeepw.matchfilter.views

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.jaydeepw.matchfilter.R
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import com.github.jaydeepw.matchfilter.utils.DebugLog
import com.github.jaydeepw.matchfilter.viewmodels.MainViewModel
import com.github.jaydeepw.matchfilter.views.adapters.MatchesAdapter
import retrofit2.Response

class MainFragment : BaseFragment() {

    private var notificaitonsViewModel: MainViewModel? = null
    private var matchesRecyclerView: RecyclerView? = null
    private var progressCircular: ProgressBar? = null
    private var adapter: MatchesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        matchesRecyclerView = view.findViewById(R.id.matchesRecyclerView)
        progressCircular = view.findViewById(R.id.progressCircular)

        notificaitonsViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)
            .create(MainViewModel::class.java)

        notificaitonsViewModel
            ?.getMatches()
            ?.observe(this, Observer<Response<MatchResponse>> {
                    response -> showList(response)})

        notificaitonsViewModel?.repository?.loading?.observe(this,
            Observer<Boolean> { isLoading -> handleLoadingProgress(isLoading) })
    }

    private fun showList(response: Response<MatchResponse>?) {
        isAdded

        if (response == null) {
            DebugLog.e("API call not successful")
            return
        }

        if (!response.isSuccessful) {
            DebugLog.e("API call not successful")
            return
        }
        val matchesReponse = response.body()
        if (matchesReponse == null) {
            DebugLog.e("API call not successful")
            return
        }
        matchesRecyclerView?.layoutManager = LinearLayoutManager(context)

        // Access the RecyclerView Adapter and load the data into it
        adapter = MatchesAdapter(matchesReponse.matches, context!!)
        matchesRecyclerView?.adapter = adapter
    }

    private fun handleLoadingProgress(loading: Boolean) {
        if (loading) {
            progressCircular?.visibility = View.VISIBLE
        } else {
            progressCircular?.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
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
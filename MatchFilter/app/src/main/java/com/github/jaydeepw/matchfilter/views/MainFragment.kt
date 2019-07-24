package com.github.jaydeepw.matchfilter.views

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.jaydeepw.matchfilter.MyApp
import com.github.jaydeepw.matchfilter.R
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import com.github.jaydeepw.matchfilter.utils.DebugLog
import com.github.jaydeepw.matchfilter.utils.Utils
import com.github.jaydeepw.matchfilter.viewmodels.MainViewModel
import com.github.jaydeepw.matchfilter.views.adapters.MatchesAdapter
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import javax.inject.Inject

class MainFragment : BaseFragment() {

    private var matchesViewModel: MainViewModel? = null
    private var matchesRecyclerView: RecyclerView? = null
    private var progressCircular: ProgressBar? = null
    private var pullToRefresh: SwipeRefreshLayout? = null
    private var adapter: MatchesAdapter? = null
    private var messageTextView: TextView? = null
    private var filter: HashMap<String, String> = HashMap()

    @Inject
    lateinit var loading: MutableLiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        DebugLog.i("")
        (activity?.application!! as MyApp).appComponent.inject(this)
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
        messageTextView = view.findViewById(R.id.messageTextView)
        pullToRefresh = view.findViewById(R.id.pullToRefresh)

        matchesViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)
            .create(MainViewModel::class.java)

        matchesViewModel?.loading = loading
        matchesViewModel?.init()

        matchesViewModel
            ?.getMatches(filter)
            ?.observe(this, Observer<Response<MatchResponse>> {
                    response -> showList(response)
            })

        DebugLog.i("--> matchesViewModel?.repository?.loading: " +
                matchesViewModel?.loading)
        matchesViewModel?.loading?.observe(this,
            Observer<Boolean> { isLoading -> handleLoadingProgress(isLoading) })

        matchesViewModel?.repository?.errorHandler?.observe(this,
            Observer<String> { throwableMessage -> showMessage(Utils.Companion.parse(activity!!, throwableMessage)) })

        initPullToRefresh()
    }

    private fun initPullToRefresh() {
        hideMessage()
        pullToRefresh?.setOnRefreshListener {
            reloadListWithFiltersApplied()
        }
    }

    private fun reloadListWithFiltersApplied() {
        matchesViewModel
            ?.getMatches(filter)
    }

    private fun showList(response: Response<MatchResponse>?) {
        isAdded

        if (response == null) {
            DebugLog.e("API call not successful")
            showMessage(R.string.msg_response_null)
            return
        }

        if (!response.isSuccessful) {
            val message = Utils.Companion.parseNetworkCode(response.code())
            showMessage(message)
            DebugLog.e("API call not successful")
            return
        }
        val matchesResponse = response.body()
        if (matchesResponse == null) {
            DebugLog.e("API call not successful")
            return
        }

        hideMessage()
        matchesRecyclerView?.layoutManager = LinearLayoutManager(context)

        // Access the RecyclerView Adapter and load the data into it
        val results = matchesResponse.matches
        if (results?.size == 0) {
            showMessage(getString(R.string.msg_no_results))
        } else {
            adapter = MatchesAdapter(results, context!!)
            matchesRecyclerView?.adapter = adapter
        }
    }

    private fun handleLoadingProgress(loading: Boolean) {
        DebugLog.i("--> loading: $loading")
        if (loading) {
            progressCircular?.visibility = View.VISIBLE
        } else {
            progressCircular?.visibility = View.GONE
        }

        pullToRefresh?.isRefreshing = loading
    }

    private fun showMessage(messageResId: Int) {
        showMessage(getString(messageResId))
    }

    private fun showMessage(message: String) {
        if (adapter == null || adapter?.itemCount == 0) {
            messageTextView?.visibility = View.VISIBLE
            messageTextView?.text =  message
        } else {
            Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun hideMessage() {
        messageTextView?.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (menu?.findItem(R.id.action_filter) == null) {
            inflater?.inflate(R.menu.menu_main, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_filter -> {
                openFilters()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTENT_FILTERS_REQUEST_CODE) {
            val possiblyFilters = data?.getSerializableExtra(FiltersDialogFragment.DATA)
            if (possiblyFilters is HashMap<*, *>) {
                filter = possiblyFilters as HashMap<String, String>
                adapter?.clear()
                reloadListWithFiltersApplied()
            }
        }
    }

    private fun openFilters() {
        val dialog = FiltersDialogFragment()
        dialog.isCancelable = true
        dialog.show(fragmentManager, "dialog")
        dialog.extras = filter
        dialog.setTargetFragment(this, INTENT_FILTERS_REQUEST_CODE)
    }

    companion object {
        const val INTENT_FILTERS_REQUEST_CODE = 99
    }
}
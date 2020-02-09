package element.list.gitbrowser.presentation.fragments.home

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import element.list.gitbrowser.R
import element.list.gitbrowser.adapter.RepoAdapter
import element.list.gitbrowser.adapter.RepoClickListener
import element.list.gitbrowser.model.GitRepository
import element.list.gitbrowser.presentation.fragments.owner.OwnerFragment
import element.list.gitbrowser.presentation.fragments.repodetails.RepoDetailsFragment
import element.list.gitbrowser.utils.FilterStatus
import element.list.gitbrowser.utils.RepoStatus
import element.list.gitbrowser.utils.hideKeyBoard
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), RepoClickListener {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var repoAdapter: RepoAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setObservers()
        setListeners()
        setSpinnerAdapter()
    }

    private fun setSpinnerAdapter() {
        val spinnerItems = arrayOf(SORT_BY, STARS, FORKS, UPDATED)
        val spinnerAdapter = ArrayAdapter(context!!, R.layout.spinner_item, spinnerItems)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_selected)
        filterSpinner.adapter = spinnerAdapter
    }

    private fun setAdapter() {
        repoAdapter = RepoAdapter(context!!)
        repoAdapter.repoClickListener = this
        flowerRecyclerView.adapter = repoAdapter
        val repositoryLayoutManager = GridLayoutManager(context, COLUMN_COUNT, RecyclerView.VERTICAL, false)
        flowerRecyclerView.layoutManager = repositoryLayoutManager
    }

    private fun setListeners() {
        searchView.setOnTouchListener(OnTouchListener { v, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchView.right - searchView.compoundDrawables[drawableRight].bounds.width()) {
                    if (!TextUtils.isEmpty(searchView.text.toString())) {
                        homeViewModel.getRepositories(searchView.text.toString())
                        filterSpinner.setSelection(0)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })

        searchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if (!TextUtils.isEmpty(searchView.text.toString())) {
                    homeViewModel.getRepositories(searchView.text.toString())
                    filterSpinner.setSelection(0)
                }
                true
            } else {
                false
            }
        }

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent!!.getItemAtPosition(position).toString()) {
                    STARS -> homeViewModel.sortList(FilterStatus.STARS)
                    FORKS -> homeViewModel.sortList(FilterStatus.FORKS)
                    UPDATED -> homeViewModel.sortList(FilterStatus.UPDATED)
                }
            }
        }
    }

    private fun setObservers() {
        homeViewModel.repositoryListLive.observe(viewLifecycleOwner, Observer {
            repoAdapter.setData(it)
            if (it.size > 0) {
                noSearchResults.visibility = View.GONE
            } else
                noSearchResults.visibility = View.VISIBLE
        })

        homeViewModel.repoStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RepoStatus.SUCCES -> hideProgressBar()
                is RepoStatus.LOADING -> showProgressBar()
                is RepoStatus.FAILURE -> {
                }
            }
        })

        homeViewModel.filterStatus.observe(viewLifecycleOwner, Observer {
            repoAdapter.setData(homeViewModel.repositoryList)
            if (homeViewModel.repositoryList.size > 0) {
                noSearchResults.visibility = View.GONE
            } else
                noSearchResults.visibility = View.VISIBLE
        })
    }

    override fun ownerImageClicked(ownerName: String) {
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, OwnerFragment(ownerName)).commit()
        fragmentTransaction.addToBackStack(OwnerFragment.TAG)
        resolveKeyboard()
    }

    override fun repoClicked(gitRepository: GitRepository) {
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, RepoDetailsFragment(gitRepository)).commit()
        fragmentTransaction.addToBackStack(RepoDetailsFragment.TAG)
        resolveKeyboard()
    }

    private fun resolveKeyboard() {
        searchView.hideKeyBoard()
        searchView.clearFocus()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    companion object {
        const val COLUMN_COUNT = 1
        const val TAG = "HomeFragment"
        const val STARS = "stars"
        const val FORKS = "forks"
        const val UPDATED = "updated"
        const val SORT_BY = "sort by:"
    }

}
package element.list.gitbrowser.presentation.fragments.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import element.list.gitbrowser.R
import element.list.gitbrowser.constants.Constants
import element.list.gitbrowser.model.OwnerDetails
import element.list.gitbrowser.utils.openBrowser
import element.list.gitbrowser.utils.resolveIfEmpty
import kotlinx.android.synthetic.main.fragment_owner.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OwnerFragment : Fragment() {
    private val ownerViewModel: OwnerViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_owner, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        if ((context as FragmentActivity).supportFragmentManager.backStackEntryCount == 0)
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        ownerViewModel.getOwnerDetails(arguments?.getString(OWNER_NAME)!!)
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        openWebBrowser.setOnClickListener {
            ownerViewModel.ownerDetails.value?.htmlUrl?.let { context?.let { context -> openBrowser(context, it) } }
        }
    }

    private fun setObservers() {
        ownerViewModel.ownerDetails.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    private fun setData(ownerDetails: OwnerDetails) {
        ownerLogin.text = ownerDetails.login ?: Constants.EMPTY_INFO_REPLACEMENT
        name.text = ownerDetails.name ?: Constants.EMPTY_INFO_REPLACEMENT
        Glide.with(context!!).load(ownerDetails.avatarUrl).into(ownerImage)
        company.text = ownerDetails.company ?: Constants.EMPTY_INFO_REPLACEMENT
        blog.text = ownerDetails.blog?.resolveIfEmpty()
        followers.text = ownerDetails.followers ?: Constants.EMPTY_INFO_REPLACEMENT
        location.text = ownerDetails.location ?: Constants.EMPTY_INFO_REPLACEMENT
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                (context as FragmentActivity).supportFragmentManager.popBackStack()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    companion object {
        private const val OWNER_NAME = "OwnerName"

        fun newInstance(ownerName: String): Fragment {
            val fragment = OwnerFragment()
            val argument = Bundle()
            argument.putString(OWNER_NAME, ownerName)
            fragment.arguments = argument
            return fragment
        }
    }
}
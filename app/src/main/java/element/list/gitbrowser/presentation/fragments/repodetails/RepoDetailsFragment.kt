package element.list.gitbrowser.presentation.fragments.repodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import element.list.gitbrowser.R
import element.list.gitbrowser.constants.Constants
import element.list.gitbrowser.model.GitRepository
import element.list.gitbrowser.presentation.fragments.owner.OwnerFragment
import element.list.gitbrowser.utils.format
import kotlinx.android.synthetic.main.fragment_repo_details.*


class RepoDetailsFragment : Fragment() {

    private lateinit var gitRepository: GitRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        gitRepository = arguments?.getSerializable(GIT_REPOSITORY) as GitRepository
        setData()
        setListeners()
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

    private fun setData() {
        repositoryName.text = gitRepository.name ?: Constants.EMPTY_INFO_REPLACEMENT
        language.text = gitRepository.language ?: Constants.EMPTY_INFO_REPLACEMENT
        watchers.text = gitRepository.watchersCount?.toString() ?: Constants.EMPTY_INFO_REPLACEMENT
        forks.text = gitRepository.forksCount?.toString() ?: Constants.EMPTY_INFO_REPLACEMENT
        issues.text = gitRepository.issuesCount?.toString() ?: Constants.EMPTY_INFO_REPLACEMENT
        creationDate.text = gitRepository.creationDate?.format() ?: Constants.EMPTY_INFO_REPLACEMENT
        updateDate.text = gitRepository.updateDate?.format() ?: Constants.EMPTY_INFO_REPLACEMENT
        owner.text = gitRepository.owner.login ?: Constants.EMPTY_INFO_REPLACEMENT
        score.text = gitRepository.score?.toString() ?: Constants.EMPTY_INFO_REPLACEMENT
    }

    private fun setListeners() {
        openWebBrowser.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(gitRepository.htmlUrl)
            startActivity(openURL)
        }

        owner.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
            (context as FragmentActivity).supportFragmentManager.popBackStack()
            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainer, OwnerFragment.newInstance(gitRepository.owner.login!!)).commit()
            fragmentTransaction.addToBackStack(OwnerFragment.TAG)
        }
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    companion object {
        const val TAG = "RepoDetailsFragment"
        private const val GIT_REPOSITORY = "GitRepository"

        fun newInstance(gitRepository: GitRepository): Fragment {
            val fragment = RepoDetailsFragment()
            val argument = Bundle()
            argument.putSerializable(GIT_REPOSITORY, gitRepository)
            fragment.arguments = argument
            return fragment
        }
    }
}
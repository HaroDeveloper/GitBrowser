package element.list.gitbrowser.presentation.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import element.list.gitbrowser.R
import element.list.gitbrowser.presentation.fragments.owner.OwnerFragment
import org.koin.core.KoinComponent
import org.koin.core.inject

object Navigation : KoinComponent {
    private val context: Context by inject()
    fun addFragment(frame: Int, fragment: Fragment) {
        val fragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, OwnerFragment("f")).commit()
        fragmentTransaction.addToBackStack(OwnerFragment.TAG)
    }
}
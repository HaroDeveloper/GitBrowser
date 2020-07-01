package element.list.gitbrowser.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import element.list.gitbrowser.R
import element.list.gitbrowser.presentation.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private var homeFragment: HomeFragment? = null
    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (homeFragment == null)
            homeFragment = HomeFragment()

        homeFragment?.let { fragmentTransaction.add(R.id.fragmentContainer, homeFragment!!).commit() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("test", "value")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Toast.makeText(this, savedInstanceState.getString("test"), Toast.LENGTH_LONG).show()
    }
}

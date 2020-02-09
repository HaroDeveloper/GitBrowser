package element.list.gitbrowser.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import element.list.gitbrowser.R
import element.list.gitbrowser.presentation.fragments.home.HomeFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        supportActionBar?.title = null
//        toolbar_title.text = "lalal"

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, HomeFragment()).commitAllowingStateLoss()
    }
}

package org.billcarsonfr.jsonviewerexample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import org.billcarsonfr.jsonviewer.JSonViewerDialog
import org.billcarsonfr.jsonviewer.JSonViewerFragment

class MainActivity : AppCompatActivity(), ChooseSampleFragment.OnFragmentMainNavigationListener {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.samples, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        val jsonViewerIsVisible = supportFragmentManager.findFragmentByTag("JSONVIEWER") != null
        menu?.forEach {
            it.isEnabled = jsonViewerIsVisible
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val jsonViewerFragment =
            supportFragmentManager.findFragmentByTag("JSONVIEWER") as? JSonViewerFragment
        when (item.itemId) {
            R.id.sample1 -> {
                val sample = resources.openRawResource(R.raw.sample1)
                    .bufferedReader().use { it.readText() }
                jsonViewerFragment?.showJson(sample, -1)
            }
            R.id.sample2 -> {
                val sample = resources.openRawResource(R.raw.sample2)
                    .bufferedReader().use { it.readText() }
                jsonViewerFragment?.showJson(sample, -1)

            }
            R.id.sample3 -> {
                val sample = resources.openRawResource(R.raw.sample3)
                    .bufferedReader().use { it.readText() }
                jsonViewerFragment?.showJson(sample, -1)

            }
            R.id.sample4 -> {
                val sample = resources.openRawResource(R.raw.sample4)
                    .bufferedReader().use { it.readText() }
                jsonViewerFragment?.showJson(sample, -1)

            }
            R.id.sample5 -> {
                val sample = resources.openRawResource(R.raw.sample5)
                    .bufferedReader().use { it.readText() }
                jsonViewerFragment?.showJson(sample, -1)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToWrapSample(wrap: Boolean) {
        val sample = resources.openRawResource(R.raw.sample1)
            .bufferedReader().use { it.readText() }
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                JSonViewerFragment.newInstance(
                    sample,
                    initialOpenDepth = -1,
                    wrap = wrap
                ),
                "JSONVIEWER"
            )
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToDialog() {
        val sample = resources.openRawResource(R.raw.sample1)
            .bufferedReader().use { it.readText() }
        JSonViewerDialog.newInstance(sample)
            .show(supportFragmentManager, "BottomSheet")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    ChooseSampleFragment.newInstance(),
                    "CHOSE"
                )
                .commit()
        }
    }

}

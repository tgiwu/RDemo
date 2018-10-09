package com.yz.rdemo.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.yz.rdemo.Constants
import com.yz.rdemo.R
import com.yz.rdemo.controllers.IOperationController
import com.yz.rdemo.controllers.OperationListController
import com.yz.rdemo.display.DemoDisplay
import com.yz.rdemo.display.IOperationDisplay
import kotlinx.android.synthetic.main.activity_conver_list.*
import kotlinx.android.synthetic.main.app_bar_conver_list.*

class OperationListActivity : BaseActivity<IOperationController<IOperationController.IOperationUI, IOperationDisplay>, IOperationDisplay>(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun getLayout(): Int = R.layout.activity_conver_list

    override fun provideController(): IOperationController<IOperationController.IOperationUI, IOperationDisplay> = OperationListController.instance

    override fun provideDisplay(): IOperationDisplay = DemoDisplay()

    override fun onFirstAttach() {
        getDisplay()?.showOperationListFragment()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.conver_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_private -> {
                getController()?.onRequestPrivate("13466739596")
            }
            R.id.nav_group_chat -> {
                getController()?.onRequestGroup(Constants.GROUP_ID, "group")
            }
            R.id.nav_chat_room -> {
                getController()?.onRequestRome(Constants.ROME_ID)
            }
            R.id.nav_discussion -> {
                getController()?.onRequestDiscussion(listOf("13466739595", "13466739596"), "discussion")
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

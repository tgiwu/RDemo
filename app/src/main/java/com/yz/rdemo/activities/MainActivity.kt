package com.yz.rdemo.activities

import android.os.Bundle
import com.yz.rdemo.controllers.IMainController
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.display.DemoDisplay
import com.yz.rdemo.display.IMainDisplay
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient

class MainActivity : BaseActivity<IMainController<IMainController.IMainUi>, IMainDisplay>() {

    override fun provideController(): IMainController<IMainController.IMainUi> = MainController.instance

    override fun provideDisplay(): IMainDisplay = DemoDisplay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getDisplay()?.showRegistry()
    }

    override fun onPause() {
        super.onPause()
    }
}

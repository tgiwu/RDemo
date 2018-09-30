package com.yz.rdemo.activities

import com.yz.rdemo.controllers.IMainController
import com.yz.rdemo.controllers.MainController
import com.yz.rdemo.display.DemoDisplay
import com.yz.rdemo.display.IMainDisplay

class MainActivity : BaseActivity<IMainController<IMainController.IMainUi>, IMainDisplay>() {

    override fun provideController(): IMainController<IMainController.IMainUi> = MainController.instance

    override fun provideDisplay(): IMainDisplay = DemoDisplay()

    override fun onFirstAttach() {
        getDisplay()?.showLogin()
    }
}

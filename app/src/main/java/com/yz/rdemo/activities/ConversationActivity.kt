package com.yz.rdemo.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yz.rdemo.R
import kotlinx.android.synthetic.main.conversation.*

class ConversationActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation)
    }
}
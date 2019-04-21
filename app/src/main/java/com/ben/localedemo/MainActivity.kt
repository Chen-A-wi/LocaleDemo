package com.ben.localedemo

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ben.localedemo.utils.LocaleHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var button: Button
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        textView = find(R.id.textView)
        button = find(R.id.button)

        button.setOnClickListener(clickEvent)
    }

    private val clickEvent = View.OnClickListener { view ->
        when (view.id) {
            R.id.button -> {
                lateinit var resources: Resources
                count++

                if (count % 2 == 0) {
                    val context: Context? = LocaleHelper.initLocale(this, "zh", "TW")
                    if (context != null) {
                        resources = context.resources
                    }
                } else {
                    val context: Context? = LocaleHelper.initLocale(this, "en")
                    if (context != null) {
                        resources = context.resources
                    }
                }

                updateView(resources)
            }
        }
    }

    private fun updateView(resources: Resources) {
        doAsync {
            uiThread {
                textView.text = resources.getText(R.string.hello_str)
                button.text = resources.getText(R.string.change_lang_str)
            }
        }
    }
}

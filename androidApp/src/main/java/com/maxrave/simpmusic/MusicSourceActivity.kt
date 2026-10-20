package com.maxrave.simpmusic

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

/**
 * Web-based music sources. Additional sources can be added to this catalog.
 * Audio playback is controlled by the website; background playback depends on its player.
 */
class MusicSourceActivity : AppCompatActivity() {
    private lateinit var browser: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Музыка — Hitmo"
        browser = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }
        setContentView(browser)
        if (savedInstanceState == null) {
            browser.loadUrl("https://ru.hitmoz.org/songs/top-rated")
        } else {
            browser.restoreState(savedInstanceState)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (browser.canGoBack()) browser.goBack() else finish()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        browser.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        browser.stopLoading()
        browser.destroy()
        super.onDestroy()
    }
}

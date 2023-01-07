package com.siddhantkushwaha.languagetools

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            val message = "This is a message about me."
            val language = LanguageIdentifier.getLanguage(message)

            Log.d("Main", "Language for message : [$message] - [$language].")

            val messageInHindi = "हिंदी में लिखो"
            val languageMessage = LanguageIdentifier.getLanguage(messageInHindi)

            val translator = AnyLanguageToEnglishTranslator(languageMessage)

            val messageInEnglish =
                translator.translate(messageInHindi, skipIfNotDownloaded = false)

            Log.d("Main", "Translated [$messageInHindi] to [$messageInEnglish].")
        }.start()
    }
}
package com.siddhantkushwaha.languagetools

import com.google.android.gms.tasks.Tasks
import com.google.mlkit.nl.languageid.LanguageIdentification


object LanguageIdentifier {
    fun getLanguage(body: String): String {
        try {
            val languageIdentifier = LanguageIdentification.getClient()
            return Tasks.await(languageIdentifier.identifyLanguage(body))
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return "und"
    }
}
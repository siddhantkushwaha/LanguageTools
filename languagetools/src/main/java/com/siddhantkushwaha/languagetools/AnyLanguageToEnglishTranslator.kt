package com.siddhantkushwaha.languagetools

import com.google.android.gms.tasks.Tasks
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.*
import com.siddhantkushwaha.languagetools.LanguageIdentifier.getLanguage


class AnyLanguageToEnglishTranslator(private val languageCode: String) {
    private fun downloadModel() {
        val modelManager = RemoteModelManager.getInstance()
        val hindiToEnglishModel = TranslateRemoteModel.Builder(languageCode).build()
        val conditions = DownloadConditions.Builder().build()
        Tasks.await(modelManager.download(hindiToEnglishModel, conditions))
    }

    private fun getTranslator(): Translator {
        val options = TranslatorOptions.Builder().setSourceLanguage(languageCode)
            .setTargetLanguage(TranslateLanguage.ENGLISH).build()
        return Translation.getClient(options)
    }

    private fun loadModel(forceDownload: Boolean = false): Translator {
        return if (isModelDownloaded() && !forceDownload) {
            getTranslator()
        } else {
            downloadModel()
            getTranslator()
        }
    }

    private fun isModelDownloaded(): Boolean {
        val modelManager = RemoteModelManager.getInstance()
        val hindiToEnglishModel = TranslateRemoteModel.Builder(languageCode).build()
        return Tasks.await(modelManager.isModelDownloaded(hindiToEnglishModel))
    }

    fun translate(body: String, skipIfNotDownloaded: Boolean = true): String? {
        try {
            if (skipIfNotDownloaded && !isModelDownloaded()) {
                return null
            }

            val language = getLanguage(body)
            if (language != languageCode)
                return null

            val translator = loadModel()
            return Tasks.await(translator.translate(body))

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }
}
package com.example.mockotisample

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

object FileReader {

    fun getStringFromFile(fileName: String): String {
        try {
            val inputStream = InstrumentationRegistry.getInstrumentation()
                .context.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}
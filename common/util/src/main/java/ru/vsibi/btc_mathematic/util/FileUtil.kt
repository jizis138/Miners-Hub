/**
 * Created by Dmitry Popov on 22.04.2022.
 */
package ru.vsibi.btc_mathematic.util

import java.io.*

fun createFile(parent : File, fileName : String) : File {
    val file = File(parent, fileName)
    if (!file.parentFile.exists())
        file.parentFile.mkdirs()
    if (!file.exists())
        file.createNewFile();
    return file
}

fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
    inputStream.use { input ->
        val outputStream = FileOutputStream(outputFile)
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024) // buffer size
            while (true) {
                val byteCount = input.read(buffer)
                if (byteCount < 0) break
                output.write(buffer, 0, byteCount)
            }
            output.flush()
        }
    }
}
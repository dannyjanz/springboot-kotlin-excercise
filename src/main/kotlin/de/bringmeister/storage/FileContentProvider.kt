package de.bringmeister.storage

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.charset.Charset

interface FileContentProvider {

    fun readLines(filename: String, charset: Charset = Charsets.UTF_8): String

}


@Component
class SystemFileContentProvider : FileContentProvider {

    override fun readLines(filename: String, charset: Charset): String =
            ClassPathResource(filename).file.inputStream().bufferedReader(charset).useLines { lines ->
                lines.fold(StringBuilder()) { content, line -> content.append(line) }.toString()
            }

}
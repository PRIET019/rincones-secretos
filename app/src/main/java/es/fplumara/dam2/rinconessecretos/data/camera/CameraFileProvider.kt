package es.fplumara.dam2.rinconessecretos.data.camera

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.util.UUID


fun createImageUri(context: Context): Uri {
    val imagesDir = File(context.filesDir, "images").apply { mkdirs() } // <- filesDir
    val file = File(imagesDir, "photo-${UUID.randomUUID()}.jpg")
    val authority = "${context.packageName}.fileprovider"
    return FileProvider.getUriForFile(context, authority, file)
}

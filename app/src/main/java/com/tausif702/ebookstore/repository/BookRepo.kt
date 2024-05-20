package com.tausif702.ebookstore.repository

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.tausif702.ebookstore.Utils.MyResponses
import java.io.File


class BookRepo(val context: Context) {

    private val downloadLD = MutableLiveData<MyResponses<DownloadModel>>()

    val downloadLiveData get() = downloadLD

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun downloadPdf(url: String, fileName: String) {
        val file=File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),fileName)
        if (file.exists()){
            val model = DownloadModel(
                process = 100,
                isDownloaded = true,
                downloadId = -1,
                filePath = file.toURI().toString()
            )
            downloadLiveData.postValue(MyResponses.Success(model))
            return
        }

        downloadLiveData.postValue(MyResponses.Loading())

        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        val downloadRequests = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            setTitle(fileName)
            setDescription("Download Book")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setAllowedOverRoaming(true)
            setAllowedOverMetered(true)
            setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )

        }

        val downloadId: Long = downloadManager.enqueue(downloadRequests)

        var isDownloaded = false
        var process = 0
        while (!isDownloaded) {
            val cursor = downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
            if (cursor.moveToNext()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                when (status) {
                    DownloadManager.STATUS_RUNNING -> {
                        val totalSize =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (totalSize > 0) {
                            val downloadByteSize =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            process = ((downloadByteSize * 100L) / totalSize).toInt()
                            downloadLiveData.postValue(MyResponses.Loading(process))
                        }

                    }

                    DownloadManager.STATUS_PENDING -> {
                        downloadLiveData.postValue(MyResponses.Loading())
                    }
                    DownloadManager.STATUS_PAUSED -> {
                        downloadLiveData.postValue(MyResponses.Loading())
                    }
                    DownloadManager.STATUS_FAILED -> {
                        val reason =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                        isDownloaded = true
                        downloadLiveData.postValue(MyResponses.Error("Failed to download $fileName.\n$reason"))
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        process = 100
                        isDownloaded = true
                        val filePath =
                            cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))

                        val model = DownloadModel(
                            process = process,
                            isDownloaded = isDownloaded,
                            downloadId = downloadId,
                            filePath = filePath
                        )

                        downloadLiveData.postValue(MyResponses.Success(model))
                    }
                }

            }

        }

    }


    data class DownloadModel(
        var process: Int = 0,
        var isDownloaded: Boolean,
        var downloadId: Long,
        var filePath: String,
    )
}
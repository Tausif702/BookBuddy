package com.tausif702.ebookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.net.toUri
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.tausif702.ebookstore.databinding.ActivityPdfactivityBinding

class PDFActivity : AppCompatActivity() {
    lateinit var binding: ActivityPdfactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val bookPdf = intent.getStringExtra("book_pdf")?.toUri()
        binding.pdfView.fromUri(bookPdf)
            .enableSwipe(true)
            .pageSnap(true)
            .autoSpacing(true)
            .pageFling(true)
            .scrollHandle(DefaultScrollHandle(this))
            .load()


     }
}
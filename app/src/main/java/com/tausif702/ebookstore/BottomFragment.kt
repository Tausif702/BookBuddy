package com.tausif702.ebookstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tausif702.ebookstore.databinding.FragmentBottomBinding


class BottomFragment : BottomSheetDialogFragment() {
    private val binding by lazy {
        FragmentBottomBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.darkBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Thank you foe click me ! ", Toast.LENGTH_LONG).show()
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

}
package com.helpet.vector

import Disease
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.helpet.R
import com.helpet.view.Books.DBHelper
import com.helpet.databinding.ActivityResultBottomSheetBinding

class ResultBottomSheet : BottomSheetDialogFragment() {

    private lateinit var receivedDiseaseInfo :Disease
    private lateinit var binding : ActivityResultBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ActivityResultBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomClose.setOnClickListener {
            dismiss()
        }

        val receivedArgs = arguments
        if (receivedArgs != null) {
            receivedDiseaseInfo = receivedArgs.getParcelable(ARG_DISEASE_INFO)!!
            binding.bottomSheetTitle.text= receivedDiseaseInfo.name
        }
        binding.bottomContent.text = "확인하고 싶은 내용의 버튼을 클릭해주세요."

        binding.toggleButton.addOnButtonCheckedListener{ toggleButton, checkedId, isChecked ->
            if(isChecked) {
                when (checkedId) {
                    R.id.button1 -> {
                        binding.bottomContent.text = receivedDiseaseInfo.symptoms
                        binding.button1.background.setTint(view.resources.getColor(R.color.pink));
                        binding.button1.setTextColor(ContextCompat.getColor(view.context, R.color.black))
                        binding.button2.background.setTint(view.resources.getColor(R.color.white));
                        binding.button2.setTextColor(ContextCompat.getColor(view.context, R.color.lightgray))
                        binding.button3.background.setTint(view.resources.getColor(R.color.white));
                        binding.button3.setTextColor(ContextCompat.getColor(view.context, R.color.lightgray))
                    }
                    R.id.button2 -> {
                        binding.bottomContent.text = receivedDiseaseInfo.causes
                        binding.button2.background.setTint(view.resources.getColor(R.color.pink));
                        binding.button2.setTextColor(ContextCompat.getColor(view.context, R.color.black))
                        binding.button1.background.setTint(view.resources.getColor(R.color.white));
                        binding.button1.setTextColor(ContextCompat.getColor(view.context, R.color.lightgray))
                        binding.button3.background.setTint(view.resources.getColor(R.color.white));
                        binding.button3.setTextColor(ContextCompat.getColor(view.context, R.color.lightgray))
                    }
                    R.id.button3 -> {
                        binding.bottomContent.text = receivedDiseaseInfo.treatments
                        binding.button3.background.setTint(view.resources.getColor(R.color.pink));
                        binding.button3.setTextColor(ContextCompat.getColor(view.context, R.color.black))
                        binding.button1.background.setTint(view.resources.getColor(R.color.white));
                        binding.button1.setTextColor(ContextCompat.getColor(view.context, R.color.lightgray))
                        binding.button2.background.setTint(view.resources.getColor(R.color.white));
                        binding.button2.setTextColor(ContextCompat.getColor(view.context, R.color.lightgray))
                    }
                }
            }
        }

    }

    companion object {
        const val TAG = "ResultBottomSheet"
        private const val ARG_DISEASE_INFO = "arg_disease_info"

        fun newInstance(diseaseInfo: DBHelper.Disease): ResultBottomSheet {

            val disease = Disease(diseaseInfo.name, diseaseInfo.species, diseaseInfo.symptoms, diseaseInfo.causes, diseaseInfo.treatments )
            val args = Bundle()
            args.putParcelable(ARG_DISEASE_INFO, disease)
            val fragment = ResultBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }
}

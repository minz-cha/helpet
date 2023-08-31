package com.helpet.vector

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.helpet.R
import com.helpet.books.DBHelper


class ResultAdapter(private val diseaseList: List<DiseaseName>, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_result_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = diseaseList[position]
        holder.bind(disease)
    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        private val bottomSheet = itemView.findViewById<FrameLayout>(R.id.bottomSheet)
//        private val persistentBottomSheet: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        private val diseaseName = itemView.findViewById<TextView>(R.id.diseaseTv)
        fun bind(disease: DiseaseName) {
            diseaseName.text = disease.name
            // 여기서 북마크 버튼 처리 등을 추가할 수 있습니다.

            itemView.setOnClickListener {
                val dbHelper = DBHelper(itemView.context) // DBHelper 인스턴스 생성
                val diseaseInfo = dbHelper.getDiseaseInformation(disease.name) // 디비에서 질병 정보 가져오기

                if (diseaseInfo != null) {
                    val resultBottomSheet = ResultBottomSheet.newInstance(diseaseInfo)
                    resultBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
                    resultBottomSheet.show(fragmentManager, ResultBottomSheet.TAG)
                } else {
                    Log.d("DB Error", "Disease information not found in the database.")
                }
            }

        }
    }
}
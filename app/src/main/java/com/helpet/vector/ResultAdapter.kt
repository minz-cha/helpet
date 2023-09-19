package com.helpet.vector

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.helpet.R
import com.helpet.view.Books.DBHelper


class ResultAdapter(private val diseaseList: List<DiseaseName>, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    private var bookCount = 0

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
        private val bookMark = itemView.findViewById<ImageButton>(R.id.bookMarks)
        fun bind(disease: DiseaseName) {
            val cleanedName = disease.name.replace("\"", "")
            Log.d("disease.name", cleanedName)
            diseaseName.text = cleanedName

            itemView.setOnClickListener {
                val dbHelper = DBHelper(itemView.context) // DBHelper 인스턴스 생성
                val diseaseInfo = dbHelper.getDiseaseInformation(cleanedName) // 디비에서 질병 정보 가져오기

                if (diseaseInfo != null) {
                    val resultBottomSheet = ResultBottomSheet.newInstance(diseaseInfo)
                    resultBottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
                    resultBottomSheet.show(fragmentManager, ResultBottomSheet.TAG)
                } else {
                    Log.d("DB Error", "Disease information not found in the database.")
                }
            }

            bookMark.setOnClickListener {
                if (bookCount == 1) {
                    bookMark.setImageResource(R.drawable.baseline_bookmark_border_24)
                    Toast.makeText(itemView.context, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                    bookCount = 0
                }
                else{
                    bookMark.setImageResource(R.drawable.baseline_bookmark_24)
                    Toast.makeText(itemView.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                    bookCount = 1

                }

            }
        }
    }
}
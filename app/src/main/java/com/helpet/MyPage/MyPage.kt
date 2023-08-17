package com.helpet.MyPage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.helpet.R
import com.helpet.databinding.FragmentMyPageBinding

class MyPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = FragmentMyPageBinding.inflate(layoutInflater)

        val root = inflater.inflate(R.layout.fragment_my_page, container, false)
        val editmyinf = root.findViewById<Button>(R.id.editMyInf) // 버튼 초기화
        val mypagepush = root.findViewById<Button>(R.id.myPagePush)
        val mypagenotice = root.findViewById<Button>(R.id.myPageNotice)
        val mypageone = root.findViewById<Button>(R.id.myPageOne)
        val mypagehelp = root.findViewById<Button>(R.id.myPageHelp)
        val mypageinsta = root.findViewById<Button>(R.id.myPageInsta)


        editmyinf.setOnClickListener { showDialog() }
        mypagepush.setOnClickListener { showDialog() }
        mypagenotice.setOnClickListener { showDialog() }
        mypagehelp.setOnClickListener { showDialog() }
        mypageone.setOnClickListener { showDialog() }
        mypageinsta.setOnClickListener { showDialog() }

        // SharedPreferences 객체 생성
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // 유저아이디 데이터 읽기
        val value = sharedPreferences?.getString("userId", "null")

        val mypageuser = root.findViewById<TextView>(R.id.myPageUser)
        mypageuser.text= value

        return root
    }
    private fun showDialog() {
        val msgBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            .setTitle("서비스 구현중 ")
            .setMessage("진행 중인 서비스입니다.")
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialogInterface, i -> })
        val msgDlg: AlertDialog = msgBuilder.create()
        msgDlg.show()
    }



}
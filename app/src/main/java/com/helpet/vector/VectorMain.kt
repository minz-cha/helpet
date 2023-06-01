package com.helpet.vector

import com.helpet.R


import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.helpet.Hospital.HospitalActivity
import kotlinx.android.synthetic.main.fragment_vector_main.*


class VectorMain : Fragment(), View.OnClickListener {

    //private lateinit var binding: FragmentVectorMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root: View = inflater.inflate(R.layout.fragment_vector_main, container, false)

        val eyeBtn = root.findViewById<ImageButton>(R.id.vector_eye)

        val hospital1 = root.findViewById<ImageButton>(R.id.hospital)


        eyeBtn.setOnClickListener {
            val intent= Intent(requireContext(), VectorChoicePet::class.java)
            startActivity(intent)
        }

        hospital1.setOnClickListener {
            val intent = Intent(requireContext(), HospitalActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onClick(v: View) {
        Log.v("test2","test2")
        when (v.id) {
            R.id.vector_eye -> {
                val intent = Intent(requireActivity(), VectorChoicePet::class.java)
                startActivity(intent)
            }
//            R.id.vector_eye ->{
//                val intent =Intent(activity, VectorChoicePet::class.java)
//                startActivity(intent)
//            }

        }
    }
}






/*
class VectorMain : Fragment() , View.OnClickListener{

    private lateinit var binding: FragmentVectorChoicePetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_vector_choice_pet,container,false)
        return binding.root

        val rootView = inflater.inflate(R.layout.fragment_vector_main, container, false)

        val skinBtn = rootView.findViewById(R.id.skinbtn) as ImageButton
        skinBtn.setOnClickListener(View.OnClickListener() {
            override fun onClick() {
                val intent = Intent(activity, VectorChoicePet1::class.java)
                startActivity(intent)
            }
        });

        skinBtn.setOnClickListener {
            val intent=Intent(activity, VectorChoicePet1::class.java )
            startActivity(intent)

        }
        // Inflate the layout for this fragment


    }



      override fun onClick(v: View?){
          println("test1")
          when(v?.id){
              R.id.skinbtn->{
                  println("test2")
                  val intent=Intent(activity,VectorChoicePet1::class.java)
                  startActivity(intent)
              }
          }
      }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        skinbtn.setOnClickListener {
            activity?.let{
                val intent = Intent(activity?.applicationContext, VectorChoicePet1::class.java)
                startActivity(intent)
            }
        }
    }

}*/

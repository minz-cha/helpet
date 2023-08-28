package com.helpet.vector


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import com.helpet.R
import com.helpet.databinding.ActivityVectorChoicePetBinding
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


class VectorChoicePet : AppCompatActivity() {

    private lateinit var binding : ActivityVectorChoicePetBinding

    private var tabs: TabLayout? = null
    var fragment1: TotalPet? = null
    var fragment2: DogPet? = null
    var fragment3: CatPet? = null
    val bundle = Bundle()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityVectorChoicePetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.petPlusActivity.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // 팝업 메뉴 생성

            // 메뉴 아이템 추가
            popupMenu.menuInflater.inflate(R.menu.pet_plus, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.plusPet -> {
                        // 우리 아이 등록하기
                        val intent = Intent(this, PetRegisterActivity::class.java)
                        startActivity(intent)
                        true // 클릭 처리 완료
                    }

                    // ...
                    else -> false
                }
            }
            popupMenu.show() // 팝업 메뉴 표시
        }

        fragment1 = TotalPet()
        fragment2 = DogPet()
        fragment3 = CatPet()
        supportFragmentManager.beginTransaction().add(R.id.petFrameLayout, fragment1!!).commit()

        // 초기에는 fragment1을 default로 선택하고 데이터를 전달
        fragment1!!.arguments = bundle

        tabs = findViewById(R.id.tabPets)
        tabs?.let { realTabs ->
            realTabs.addTab(realTabs.newTab().setText("전체"))
            realTabs.addTab(realTabs.newTab().setText("강아지"))
            realTabs.addTab(realTabs.newTab().setText("고양이"))
            tabs!!.setOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val position = tab.position
                    var selectedFragment: Fragment? = null

                    when (position) {
                        0 -> {
                            selectedFragment = fragment1
                        }
                        1 -> {
                            selectedFragment = fragment2

                        }
                        2 -> {
                            selectedFragment = fragment3

                        }
                    }

                    if (selectedFragment != null) {
                        // 프래그먼트에 bundle을 전달
                        selectedFragment.arguments = bundle

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.petFrameLayout, selectedFragment).commit()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }


    }

        //string 을  bitmap 형태로 변환하는 메서드
        @RequiresApi(Build.VERSION_CODES.O)
        fun stringToBitmap(data: String?): Bitmap? {
            var bitmap: Bitmap? = null
            val byteArray: ByteArray = Base64.getDecoder().decode(data)
            val stream = ByteArrayInputStream(byteArray)
            bitmap = BitmapFactory.decodeStream(stream)
            return bitmap
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun createLayout(img: String, name: String, species: String, birth: String, age: Int): View {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.activity_sub_pet, null) as MaterialCardView

            val petimg = stringToBitmap(img)
            val petImg = layout.findViewById<ImageView>(R.id.choicePetImg)
            val petName = layout.findViewById<TextView>(R.id.choicePetName)
            val petAge = layout.findViewById<TextView>(R.id.choicePetAge)
            val petBirth = layout.findViewById<TextView>(R.id.choicePetBirth)



            petImg?.setImageBitmap(petimg)
            petName?.text = name
            petAge?.text = "나이: $age 살"
            petBirth?.text = "생일: $birth"
//        choicePet?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
//        choicePet?.setImageResource(R.drawable.petchoice)
//        choicePet?.setPadding(0, 0, 0, 0)

            layout.setOnClickListener {
                val intent = Intent(applicationContext, VectorCamera::class.java)
                intent.putExtra("namepet", name)
                intent.putExtra("speciespet", species)
                startActivity(intent)
            }

            // Create layout parameters for the MaterialCardView
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 마진 설정
            params.topMargin = resources.getDimensionPixelSize(R.dimen.margin)
            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.margin)
            params.leftMargin = resources.getDimensionPixelSize(R.dimen.marginside)
            params.rightMargin = resources.getDimensionPixelSize(R.dimen.marginside)

            // Set the layout parameters to the MaterialCardView
            layout.layoutParams = params




            return layout
        }
}


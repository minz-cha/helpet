package com.helpet.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import com.helpet.databinding.ActivityVectDetailBinding

class VectDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVectDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val string = intent.getStringExtra("string")

        if (string == "1"){
            binding.detailVectName.text="결막염"
            binding.detailVectImg.setImageResource(R.drawable.books1)
            binding.detailVectContent1.text= "결막염에 걸리면 눈꺼풀 주위가 아프거나 가려워진다. 개는 이에 앞발로 눈을 비비거나 바닥에 얼굴을 문지르는 등의 행동을 보인다. 이때 눈꺼풀을 깨끗한 손으로 들어올려보면 눈 흰자가 붉게 충혈되고 일부는 부어오를 수 있다. 눈물이 많이 나기도 하며 눈곱이 많이 낄 수 있다.\n"
            binding.detailVectContent2.text= "크게 세 가지로, 1) 눈을 세게 문지르거나, 눈에 털이 들어가는 등의물리 & 샴푸나 약품 등의 화학적 자극 2) 세균 또는 바이러스 감염 3) 알레르겐(알레르기 원인 물질)이 눈에 직접 닿았거나, 코로 흡입, 입으로 섭취했을 경우 알레르기 반응이 원인이 된다.\n"
            binding.detailVectContent3.text= "물리 · 화학적 원인의 결막염을 예방하려면 눈썹 등 눈 부위의 털 관리를 세심히 하며, 샴푸가 눈 부위에 닿지 않도록 유의한다. 감염성 결막염을 예방하려면 청결한 몸 상태를 유지하고, 반려견이 지내는 집안 환경 역시 깔끔하게 자주 청소해준다.\n" +
                    "알레르기성 결막염 예방을 위해서는 반려견의 알레르겐이 무엇인지를 미리 알아두고, 가급적 정해진 식사만 하도록 한다.\n"
        }
        else if (string == "2"){
            binding.detailVectName.text="백내장"
            binding.detailVectImg.setImageResource(R.drawable.books2)
            binding.detailVectContent1.text= "초기에는 육안으로 백내장 여부를 인지하기 어렵다. 증상이 심해질수록 시력 결손이 발생하고, 소실까지도 이르며 수정체가 수축해 수정체에 주름이 생기며, 눈 안에 염증이 발생할 수도 있다. "
            binding.detailVectContent2.text= "백내장을 유발하는 원인은 다양한데, 개에서 가장 흔한 원인은 유전이다. 이 외에도 다른 안과 질환이나 당뇨로 인한 혈당 수치 상승 등이 백내장을 유발할 수 있고, 노화 역시 영향을 미칠 수 있다.\n"
            binding.detailVectContent3.text= "백내장은 유전적인 영향이 강하다. 내 반려견의 부모가 과거 백내장을 앓았던 경험이 있다면 조금 더 주의를 기울여 눈을 살펴볼 수 있다. 유전성 백내장은 비교적 어린 나이에도 발생하기 시작하므로, 안과 검진을 통해 백내장 여부를 확인하고 주기적으로 검진을 함으로써 백내장의 진행 여부를 파악하고 제 때 필요한 치료를 하는 것이 중요하다.\n"
        }
        else if (string == "3"){
            binding.detailVectName.text="각막궤양"
            binding.detailVectImg.setImageResource(R.drawable.books3)
            binding.detailVectContent1.text= "각막에는 많은 신경이 분포돼 있어 염증이 생기면 큰 통증을 느낀다. 따라서 개는 눈을 계속 감고 있거나 깜빡거릴 수 있다. 혹은 앞발로 눈을 문지르고 있어 한눈에 봐도 눈에 이상이 있다고 짐작 가능하다. 통증이 더 심해지면 얼굴을 바닥에 문지르며 아파하고, 눈물을 많이 흘리기도 한다."
            binding.detailVectContent2.text= "각막염의 원인은 크게 외상성, 비외상성으로 구분할 수 있다. 외상성은 말 그대로 외부 물질이 각막을 자극해서 생기는 염증으로, 대표적인 예로 눈썹이 눈을 찌르거나, 샴푸 등의 약품이 눈을 자극해서 발생하거나, 날카로운 것에 의해 상처를 입는 등이 있다.\n" +
                    "비외상성은 곰팡이, 세균, 바이러스 등에 의한 감염, 대사장애, 알레르기 반응 등이 있다. 각막염을 부르는 바이러스 감염 중 대표적인 질병은 개 디스템퍼 바이러스, 개 전염성간염 바이러스가 있다.\n"
            binding.detailVectContent3.text= "샴푸 등 화학물질이 눈에 들어가지 않도록 목욕을 할 때 눈 부위와 가까운 부위는 최대한 피한다. 만일 샴푸가 눈에 들어가 개가 아파한다면 생리식염수를 눈 부위에 흘려주어 화학물질이나 티끌, 이물질이 더 이상 퍼지지 않도록 씻어주는 효과가 있다. 다만 이후에도 눈을 계속 뜨고 있지 못하면 즉시 병원으로 옮겨 치료를 받도록 한다."
        }
        else if (string == "4"){
            binding.detailVectName.text="유루증"
            binding.detailVectImg.setImageResource(R.drawable.books4)
            binding.detailVectContent1.text= "계속 눈물이 흐르는 만큼 눈 주위의 털이 쉽게 더러워지거나 냄새가 날 수 있다. 흰색 털을 가진 개들은 눈가의 흰색 털이 갈색으로 변하기도 한다. 눈물이 흘러내리는 부위에 피부가 붉어지거나 붓고 털이 빠지는 등의 피부염 증상을 보일 수도 있다. 이 경우 가려움증을 호소하며 눈 부위를 이불에 비비거나 앞발로 긁으려 할 수 있어 증상이 더 악화될 수 있다.\n"
            binding.detailVectContent2.text= "유루증의 원인은 매우 다양해 여러 가지 요인이 복합적으로 작용하는 경우가 많다. 알러지, 작은 누점, 비루관의 구조적인 폐색, 안검내번, 첩모난생(속눈썹증), 각막과 결막의 질환, 그 외 통증을 동반한 여러 눈질환이 대표적인 원인이다. 시추, 페키니즈, 치와와 등 단두종은 구조적으로 비루관이 좁아 유루증이 잘 발생하는 경향이 있다."
            binding.detailVectContent3.text= "간식, 개껌 등의 급여를 중단하거나 사료를 교체하는 것이 유루증을 줄이는 데 도움이 될 수 있다. 유루증이 있는 경우 최대한 눈 주변의 털을 짧고 청결하게 유지하는 것이 눈물로 인한 피부염을 예방할 수 있다. 유루증이 갑자기 발생하거나 심해지면 유루증 이외 별개인 질환의 2차적인 증상일 수 있으니 수의사 진찰을 받도록 한다.\n"
        }
        else if (string == "5"){
            binding.detailVectName.text="안구건조증"
            binding.detailVectImg.setImageResource(R.drawable.books5)
            binding.detailVectContent1.text= "눈에 황색의 눈꼽이 끼는 것이 강아지 안구건조증의 가장 대표적인 증상이다. 결막이 충혈되고 강아지 눈을 보면 건조하고 뻑뻑한 상태임을 알 수 있다. 질병이 진행되면 각막이 혼탁해 보이거나 색소가 침착되기도 하며 강아지가 바닥에 눈을 비비는 등의 증상이 나타난다."
            binding.detailVectContent2.text= "대부분 비정상적인 자가면역 반응에 의해 눈물샘이 손상을 입게 되었을 때 발생한다. 경우가 드물지만 당뇨, 갑상선 기능저하증, 눈 기형, 약물 부작용 등으로 인해 발생할 수 있다. 눈물샘 제거 수술 시, 너무 많이 제거하면 안구건조증이 발생할 위험이 있다. "
            binding.detailVectContent3.text= "강아지의 면역력을 증대시킬 수 있는 고른 영양섭취가 중요하며, 실내 환기나 먼지, 적정습도 유지 등의 환경 관리에 신경을 써주어야 한다. 피로감을 줄이고 충분한 숙면 또한 기본적인 예방법이다. 발병하면 완치되기 힘든 질병으로 관리를 중요시해야 한다."
        }
    }
}
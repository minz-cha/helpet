package com.helpet.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.helpet.R
import com.helpet.databinding.ActivityVectDetailCatBinding

class VectDetailCat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityVectDetailCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val string = intent.getStringExtra("string2")

        if (string == "6"){
            binding.detailVectNameCat.text="결막염"
            binding.detailVectImgCat.setImageResource(R.drawable.books6)
            binding.detailVectContentCat1.text= "눈동자가 충혈되거나 눈 주변이 빨갛게 부어오른다. 이물감을 느껴 과도한 깜빡임을 보이고 눈을 잘 뜨지 못하고 눈을 비비는 모습이 관찰된다. 하얀색 혹은 노란색 눈곱이 나오기도 하며, 피고름을 보이기도 한다. 세균 및 바이러스가 눈에서 호흡기로 퍼진 경우에는 재채기와 기침, 콧물을 보이기도 한다.\n"
            binding.detailVectContentCat2.text= "결막염은 세균, 바이러스, 곰팡이, 기생충 등에 감염되어 발생하는 경우가 가장 흔하다. 알레르기가 있거나 눈에 속눈썹 등의 이물질이 눈에 들어가는 경우에도 발생할 수 있다. 또 눈꺼풀 종양이 있는 경우에도 마찰로 자극이 되어 결막염이 생길 수 있다."
            binding.detailVectContentCat3.text= "감염성 결막염을 예방하기 위해서는 감염된 다른 고양이와의 접촉을 최소화한다. 환경을 깨끗하게 하고 주기적으로 눈 상태를 관찰하는 것이 도움이 될 수 있다."
        }
        else if (string == "7"){
            binding.detailVectNameCat.text="백내장"
            binding.detailVectImgCat.setImageResource(R.drawable.books7)
            binding.detailVectContentCat1.text= "백내장 초기에는 시력에 큰 영향을 주지 않기에 별다른 증상을 느끼지 못할 수 있다. 시간이 지나 백내장이 많이 진행되면 검은 눈동자가 뿌옇고 하얗게 변하는 모습이 관찰된다. 이렇게 시력에 문제가 생기면 잘 걷지 못하고 벽이나 가구에 부딪히거나, 사료 및 간식을 잘 찾아 먹지 못하는 등의 문제가 발생한다. 잘 보이지 않아 점프나 착지에 실패하고, 넘어지는 경우도 있다. 불안해 구석에 숨거나 움츠러드는 행동 역시 보인다.\n"
            binding.detailVectContentCat2.text= "백내장은 다양한 원인으로 발생한다. 사람처럼 노화로 인해 백내장이 생기는 경우가 많으며, 유전적 요인으로도 발생한다. 당뇨병이나 고혈압도 백내장을 유발할 수 있다. 외상으로 눈이 손상되거나, 눈에 염증이 생긴 경우, 수정체가 탈구되는 구조적인 이상으로 백내장이 발생하기도 한다. 그밖에 특정 단백질을 대사하지 못하는 등의 대사 문제나, 영양실조로도 발생할 수 있다."
            binding.detailVectContentCat3.text= "과도한 자외선에 노출되면 백내장이 심화될 수 있기 때문에 직사광선을 쬐지 않도록 유의한다. 평소 주기적인 건강검진으로 백내장을 유발할 수 있는 질환을 파악하고 관리한다.\n"
        }
        else if (string == "8"){
            binding.detailVectNameCat.text="포도막염"
            binding.detailVectImgCat.setImageResource(R.drawable.book8)
            binding.detailVectContentCat1.text= "일반적으로 포도막염은 통증을 유발하기 때문에 눈을 가늘게 뜨고, 빛에 민감하게 반응해 눈을 찌푸리거나, 눈물을 흘릴 수 있다. 또 눈 크기가 작아 보일 수 있으며, 녹내장이 동반되면 눈이 커지거나 튀어나온 것처럼 보일 수 있다. 홍채의 색이 평소와 달라 보이거나 동공이 붉게 보이는 적목현상도 보일 수 있다."
            binding.detailVectContentCat2.text= "포도막염이 나타날 수 있는 원인은 다양하다. 나뭇가지나 다른 동물의 발톱 등 긁힘에 의해 외상이 생겨서 발생할 수 있으며, 림프종과 홍채 흑색종 같은 암에 의해서도 나타난다. 또 고양이 전염성 복막염, 톡소 플라즈마, 고양이 백혈병 바이러스, 면역결핍 바이러스 등에 의해서도 발생할 수 있다.\n"
            binding.detailVectContentCat3.text= "포도막염 재발을 관리하기 위해 1년에 3~4번 안과 검진을 해야 한다.\n"
        }
        else if (string == "9"){
            binding.detailVectNameCat.text="각막염"
            binding.detailVectImgCat.setImageResource(R.drawable.books9)
            binding.detailVectContentCat1.text= "각막에는 많은 신경이 분포돼 있어 염증이 생기면 큰 통증을 느낀다. 따라서 눈을 계속 감고 있거나 깜빡거릴 수 있다. 혹은 앞발로 눈을 문지르고 있어 한눈에 봐도 눈에 이상이 있다고 짐작 가능하다. 통증이 더 심해지면 얼굴을 바닥에 문지르며 아파하고, 눈물을 많이 흘리기도 한다.\n"
            binding.detailVectContentCat2.text= "각막염의 원인은 크게 외상성, 비외상성으로 구분할 수 있다. 외상성은 말 그대로 외부 물질이 각막을 자극해서 생기는 염증으로, 대표적인 예로 눈썹이 눈을 찌르거나, 샴푸 등의 약품이 눈을 자극해서 발생하거나, 날카로운 것에 의해 상처를 입는 등이 있다.\n" +
                    "비외상성은 곰팡이, 세균, 바이러스 등에 의한 감염, 대사장애, 알레르기 반응 등이 있다. 각막염을 부르는 바이러스 감염 중 대표적인 질병은 개 디스템퍼 바이러스, 개 전염성간염 바이러스가 있다.\n"
            binding.detailVectContentCat3.text= "샴푸 등 화학물질이 눈에 들어가지 않도록 목욕을 할 때 눈 부위와 가까운 부위는 최대한 피한다. 만일 샴푸가 눈에 들어가 아파한다면 생리식염수를 눈 부위에 흘려주어 화학물질이나 티끌, 이물질이 더 이상 퍼지지 않도록 씻어주는 효과가 있다. 다만 이후에도 눈을 계속 뜨고 있지 못하면 즉시 병원으로 옮겨 치료를 받도록 한다.\n"
        }
        else if (string == "10"){
            binding.detailVectNameCat.text="녹내장"
            binding.detailVectImgCat.setImageResource(R.drawable.books10)
            binding.detailVectContentCat1.text= "안압이 높으면 고양이가 눈에 통증을 느껴 눈을 찌푸리는 증상을 보인다. 우울해하거나 구석에 숨어있기도 한다. 눈에서 눈물이 흐르기도 하며, 눈의 흰자위(공막)가 붉게 충혈되거나 부어 보인다. 또 각막이 흐리거나 푸르스름하게 보이기도 한다. 이미 신경이 손상된 경우 앞을 보지 못하는 실명 증상을 나타낸다.\n"
            binding.detailVectContentCat2.text= "안방수가 배출되지 못해 안압이 오르게 되는 원발성 녹내장과, 다른 안과 질환이나 눈 ㅂ상으로 인해 우각 통로가 좁아지거나, 막히면서 안압이 오르는 이차성 녹내장이 있다. 이외에도, 종양이나 외상이 원인이 될 수 있다.\n"
            binding.detailVectContentCat3.text= "녹내장을 완전히 예방할 수 있는 방법은 없다. 평소에 가지고 있는 포도막염 등의 안과 질환을 치료하고, 안압이 높은 경우라면 안압을 낮추는 안약을 점안하며 정기적인 안압 체크를 받는다."
        }
    }
}
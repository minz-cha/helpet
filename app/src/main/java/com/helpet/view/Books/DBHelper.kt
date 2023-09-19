package com.helpet.view.Books

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "vector.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_DISEASES = "vector"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SPECIES = "species"
        private const val COLUMN_SYMPTOMS = "symptoms"
        private const val COLUMN_CAUSES = "causes"
        private const val COLUMN_TREATMENTS = "treatments"

        private const val CREATE_TABLE_DISEASES = "CREATE TABLE $TABLE_DISEASES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_SPECIES TEXT, " +
                "$COLUMN_SYMPTOMS TEXT, " +
                "$COLUMN_CAUSES TEXT, " +
                "$COLUMN_TREATMENTS TEXT" +
                ")"
    }

    override fun onCreate(db: SQLiteDatabase) {

        Log.d("oncreate", "Oncreate")
        db.execSQL(CREATE_TABLE_DISEASES)
        // 초기 데이터 삽입
        insertDisease(db, "결막염", "강아지", "눈 결막에 생기는 염증을 결막염이라 한다.\n" +
                "눈이 붉어지거나 누런 점액질의 눈물과 눈곱이 많아지며, 간지러워 앞발로 계속 얼굴을 긁거나 바닥에 문지르는 행동을 보인다.", "크게 세 가지로,\n1) 눈을 세게 문지르거나, 눈에 털이 들어가는 등의물리 & 샴푸나 약품 등의 화학적 자극\n2) 세균 또는 바이러스 감염\n3) 알레르겐(알레르기 원인 물질)이 눈에 직접 닿았거나, 코로 흡입, 입으로 섭취했을 경우 알레르기 반응이 원인이 된다.", "예방법\n" + "결막염을 예방하기 위해서는 항상 주위의 환경을 청결하게 해줘야 하며, 강아지 눈에 자극이 될 만한 것이 들어가지 않도록 주의해야 한다.\n" +
                "또, 눈 세정제를 사용하여 반려동물의 눈을 세척해 주거나, 가위를 사용하여 눈 주변의 털을 정리해 주는 것도 좋은 예방 방법이다.")

        insertDisease(db, "백내장", "강아지", "흔히 강아지의 백내장 발병을 알게 되는 눈에 띄는 증상으로, 강아지의 눈이 흐리고, 하얗거나 푸르스름한 것을 관찰할 수 있다. \n" +
                "백내장에 걸린 강아지는 시력에 장애가 발생하여 환경을 인식하는 데 어려움을 겪을 수 있으며,이에 따라 방향을 잃거나, 물체에 부딪히는 등 활동에 제약이 발생하게 된다.\n" +
                "백내장에 걸린 강아지는 눈의 불편함이나 자극으로 인해 눈을 가늘게 뜨거나 문지르는 행동을 더 자주 하기도 한다.\n" +
                "강아지 백내장의 원인 또는 증상으로 나타날 수 있는 염증으로 인하여 눈 주위가 붉어지거나, 붓거나, 분비물이 관찰될 수 있다.", "강아지의 종류에 따라 유전적 요인으로 인하여 백내장이 발병할 위험이 더 높을 수 있다.\n" +
                "대표적인 견종으로는 푸들, 슈나우저, 코카스패니얼, 시츄, 시베리아허스키 등이 백내장에 걸리기 쉬운 것으로 알려져 있다.\n" +
                "당뇨병에 걸린 강아지는 높은 혈당 수치로 인한 수정체 대사의 변화로 백내장에 걸릴 위험이 더 높다.\n" +
                "노화, 사고나 부상으로 인해 수정체에 손상이나 염증, 감염 발생, 부족한 영양 섭취, 일부 환경적 요인에 노출되는 것 등 여러 요인이 백내장 형성에 영향을 줄 수 있다.", "눈 건강에 도움이 되는 비타민 A, C, E와 같은 항산화 영양제가 포함된 식단을 준비해주면 좋다.\n" +
                "당뇨병은 강아지 백내장을 유발하는 질병으로 알려져 있기 때문에, 강아지의 혈당 수치를 정기적으로 확인하고, 당뇨병을 효과적으로 관리하면 백내장 발병의 위험을 크게 줄일 수 있다.\n" +
                "생활 환경에서 강아지 백내장으로 이어질 수 있는 눈 부상과 자극의 위험 요소를 최소화하는 것이 도움이 될 수 있다.")

        insertDisease(db, "각막궤양", "강아지", "눈의 가장 표면 구조인 각막이 긁히거나 자극에 의하여 손상을 입은 질환이다.\n" +
                "강아지는 눈을 잘 뜨지 못하고, 눈에서 지나치게 많은 눈물이 흘러나오고, 눈곱도 자주 발생합니다.\n" +
                "눈 주위의 혈관들이 확장되어 눈이 붉게 부어오르며, 눈이 계속 따가운 듯한 느낌 때문에 자주 눈을 비빈다.", "부적절한 각막 보호작용과 과도한 상피층 손상이 주 원인이다.\n" +
                "다른 강아지와 장난치다가 발톱이나 단단한 사물에 긁히거나 목욕 중 샴푸가 눈에 들어가서 화학적인 상처를 입은 경우가 많다. \n" +
                "또 눈 주위 털이 안구를 향해 자라 각막을 찌를 경우에도 발생한다.", "이러한 증상으로 동물병원에 오면 히스토리를 파악한 후 형광색 염색약으로 각막을 염색하는 검사를 진행한다. 각막에 형광색으로 염색된 부위가 확인되면 각막궤양으로 진단하고 치료한다.\n" +
                "치료 시 항생제, 각막재생유도제 등 몇 종류의 안약을 처방해 하루 여러 차례 점안한다. \n" +
                "각막궤양의 정도가 심하지 않은 경우에는 일주일 정도면 호전된다.\n" +
                "하지만 심한 각막궤양의 경우 호전되더라도 각막에 흉터가 남을 수 있고 때로는 수술이 필요할 수 있다.")

        insertDisease(db, "유루증", "강아지", "눈 주변의 털이 지속적으로 축축해지고 붉은색으로 변하는 현상을 유루증이라고 한다.\n" +
                "강아지의 눈물에는 털을 붉은색으로 변화시키는 색소가 포함되어 있어 말티즈나 푸들처럼 하얀 털을 갖고 있는 강아지의 경우, 눈 주위의 털이 붉게 변하게 된다.\n" +
                "붉은색으로 변화된 털은 세정 약품을 써도 원래 색으로 돌아오지 않기 때문에 지속적인 관심과 예방이 중요하다.", "1) 눈물을 코로 배출시켜주는 코 눈물관(비루관)이 기능을 제대로 수행하지 못했을 경우\n " + "2) 각막염이나 결막염을 앓았을 경우\n" + "3) 눈꺼풀과 속눈썹이 눈을 찌르는 경우 등\n", "에방법\n" +"유루증은 미관상의 문제를 일으킬 뿐만 아니라 눈물이 많이 흐른 피부와 털 주변에 세균이 자라나기 좋은 환경을 만들어 눈 주변 조직에 피부염을 일으킬 수 있기 때문에 항상 눈 주위를 청결하게 해주고 전용 사료를 급여하는 것이 중요하다.\n")

        insertDisease(db, "안검내반증", "강아지", "속눈썹이 계속해서 각막을 자극하기 때문에 개는 가렵고 불편한 통증과 위화감 등으로 수시로 눈을 깜박거리고 눈물을 흘리며 눈을 문지르게 된다. \n" +
                "심한 경우 눈꺼풀의 경련과 혼탁한 눈곱도 보이게 된다. \n" +
                "위의 증상이 반복된다면 개의 각막에 상처가 나며 각막염과 결막염이 발병할 수 있게 되는데 내반증으로 인한 각막염은 만성으로 이어지기가 쉽다.\n ", "안검내반증의 원인은 선천적으로 유전에 의한 경우가 대부분이다. \n" +
                "외상이나 다른 눈병이 원인이 되는 경우도 간혹 있을 수 있다. \n" +
                "만성 각막염이나 결막염의 심한 통증으로 눈꺼풀이 붓고 경련이 발생하면 눈꺼풀이 안쪽으로 휘게 되어 안검 내반을 발생시킬 수도 있는 것이다. \n" +
                "노견의 경우 안륜근이 약해져서 발생하는 경우도 있을 수 있다.\n ", "예방법\n" + "내반증의 형태가 경미하거나 일시적 또는, 부분적으로 발생한다면 자극하는 눈썹을 그때마다 뽑고 점안 약을 넣어줌으로써 증상을 가라앉힐 수 있다.\n" +
                "또한, 레이저 치료를 이용해 내반으로 자라는 눈썹의 모근 자체를 없애주는 방법도 있다. \n" +
                "이 경우, 최소 4~6번의 반복 치료를 요하게된다. \n" +
                "하지만, 위의 치료법으로는 감당하기 힘든 중증이거나 눈썹 전체가 내번의 형태를 가지고 있다면 개의 눈을 외번의 형태로 교정시키는 외과적 수술요법으로 치료를 해줘야 한다.\n")



        insertDisease(db, "안검염", "강아지", "강아지 눈꺼풀 혹은 그 주변에 염증이 생겨 붉게 부어오르는 질병이다.\n", "알레르기성 피부병, 기생충으로 인한 피부병, 곰팡이성 피부병과 같은 피부병이 원인이 되어 강아지 안검염까지 발생할 수 있다.\n" +
                "천포창, 원반모양 홍반 루푸스와 같은 자가면역질환이 원인이 될 수 있다.\n" +
                "벌레 물림 혹은 접촉성 과민 반응 또는 결막염, 각막염에 걸리면 간지러운 증상 때문에 눈을 비비게 되면서 발생하는 2차적 세균 감염이 원인이 될 수 있다.\n", "예방법\n" + "강아지가 가려워 눈을 계속 긁는다면 긁지 못하도록 넥카라를 씌워주는 것이 좋다.\n" +
                "안악 사용 후 강아지가 안검염 증상을 보인다면 우선 안약 사용을 중단해주는 것을 권장한다.\n" +
                "강아지 눈에서 분비물이 많이 나오는 경우에는 따뜻한 수건으로 닦아준다.")


        insertDisease(db, "궤양성각막질환", "강아지", "가장 일반적인 증상 중 하나로, 눈의 피부 주위가 붓고 붉어진다.\n" +
                "강아지가 눈물을 많이 분비하거나, 통증과 불편으로 인해 자주 눈을 깜박이거나 비비게 된다. \n" +
                "빛에 민감해질 수 있으며, 강아지가 빛을 피하거나 눈을 감을 수 있다.\n", "가장 주요 원인으로, 물리적 손상, 스크래치, 혹은 눈에 이물질이 들어갈 때와 같은 외상으로 인해 발생할 수 있다.\n" +
                "세균, 바이러스 등 눈에 감염이 생기면 각막에 염증이 발생할 수 있다.\n" +
                "건조한 공기 또는 바람에 노출되면 각막이 건조해지고 염증을 유발할 수 있다.\n", "예방법\n" + "규칙적인 눈 관리가 가장 기본적인 예방법으로, 눈을 깨끗하게 유지하고, 눈물이 너무 많이 흐르지 않도록 주의해줘야한다.\n" +
                "또한, 다른 동물과의 접촉을 피하고, 눈에 이물질이 들어가지 않도록 주의한다.\n" +
                "건조한 환경에서 강아지를 보호하고, 필요한 경우 가습기를 사용하면 좋다.\n" +
                "\n")
        insertDisease(db, "비궤양성각막질환", "강아지", "강아지의 눈 주위나 각막 부분이 붉게 변할 수 있다.\n" +
                "눈물이 과다하게 분비되거나 반대로 눈이 건조해질 수 있으며, 눈을 자주 깜빡이거나 눈물이 코로 흐르는 것을 관찰할 수 있다.\n" +
                "강아지의 각막이 흐려질 수 있으며, 이는 시력 문제의 징후일 수 있다.\n", "강아지가 알레르기 반응을 일으키는 물질에 노출되면 눈에 염증이 발생할 수 있다.\n" +
                "강아지의 눈물 분비가 충분하지 않거나 눈이 너무 빨리 증발하는 경우 눈이 건조해지고 염증이 발생할 수 있다.\n" +
                "눈에 외부 충격을 받거나 스크래치를 입는 등의 물리적 손상도 비궤양성 각막질환을 유발할 수 있다.\n", "규칙적으로 강아지의 눈을 청소하고 관리하여 물질 또는 이물질이 각막에 축적되지 않도록 한다.\n" +
                "알레르기 반응이 있는 경우, 주의 깊은 관리 및 치료를 제공하여 염증을 예방해야한다.\n" +
                "강아지에게 건강한 식사와 충분한 수분을 제공하여 눈 건강을 유지한다.")

        insertDisease(db, "색소침착성각막염", "강아지", "색소침착성 각막염은 강아지의 각막에 면역반응이 일어나면서 발생하는 각막질환 중 하나이다.\n" +
                "증상으로는 강아지의 눈 주위가 붉게 변할 수 있으며, 눈물이 과다하게 분비되거나 각막 주위에 부분적인 눈물이 흐를 수 있습니다.\n" +
                "빛에 민감해져 눈을 감거나 어두운 장소를 찾으려고 할 수 있습니다.\n" +
                "각막의 투명성이 감소하고 흐려질 수 있으며, 이는 시력 문제를 초래할 수 있다.\n", "색소침착성 각막염의 원인은 면역 시스템의 이상 반응으로 인한 것으로, 정확한 원인은 아직까지 밝혀진 것이 없다. \n" +
                "그러나 면역반응이 각막에 있는 색소세포에 영향을 미치면서 각막염이 발생한다.\n", "예방법\n" + "강아지의 정기적인 건강 검진과 눈 검사를 통해 질환의 조기 발견과 치료를 도울 수 있다.\n" +"다소 스트레스가 있는 환경을 피하고 눈 건강을 위한 건강한 생활 습관을 유지한다.\n" +"강아지의 눈 건강을 지속적으로 관찰하고 어떠한 이상 증상이 나타날 경우, 즉시 수의사에게 상담하고 치료를 받는 것이 중요하다.\n")


        insertDisease(db, "핵경화", "강아지", "핵경화는 노화로 인해 강아지의 눈의 렌즈에 변화가 생기는 일반적인 현상이다.\n" +
                "이러한 변화로 인해 렌즈가 약간 흐릿하게 보일 수 있지만 대개 강아지의 시력에 큰 문제를 일으키지 않는다.\n" +
                "주요 증상으로는 렌즈가 약간 흐려져서 눈의 색깔이 희미하게 파란색 또는 그레이로 보일 수 있다.\n" +
                "렌즈의 흐릿함으로 인해 빛이 덜 투과되기 때문에, 강아지의 시력이 조금 감소할 수 있다. \n" +
                "(강아지의 눈은 핵경화로 인해 큰 부담을 받지 않으며, 대부분의 경우 눈 건강은 유지된다.)\n", "핵경화는 노화로 인해 발생하는 정상한 눈 변화이다.\n" +
                "강아지가 노화하면서 렌즈 내부의 단백질이 쌓이고 농축되면서 발생한다. \n" +
                "이것은 눈의 자연적인 과정 중 하나이며, 특별한 원인이나 외부 요인에 의한 것이 아니다.\n", "예방법\n"+"핵경화는 대개 강아지의 눈 건강을 크게 위협하지 않는 자연적인 과정이므로, 일반적으로 크게 걱정할 필요는 없지만, 눈 건강을 유지하기 위해 정기적인 눈 검사와 적절한 영양 공급을 고려하는 것이 중요하다.\n")



        insertDisease(db, "결막염", "고양이", "눈동자가 충혈되거나 눈 주변이 빨갛게 부어오른다. \n" +
                "이물감을 느껴 과도한 깜빡임을 보이고 눈을 잘 뜨지 못하고 눈을 비비는 모습이 관찰된다. \n" +
                "하얀색 혹은 노란색 눈곱이 나오기도 하며, 피고름을 보이기도 한다. \n" +
                "세균 및 바이러스가 눈에서 호흡기로 퍼진 경우에는 재채기와 기침, 콧물을 보이기도 한다.\n", "결막염은 세균, 바이러스, 곰팡이, 기생충 등에 감염되어 발생하는 경우가 가장 흔하다. \n" +
                "알레르기가 있거나 눈에 속눈썹 등의 이물질이 눈에 들어가는 경우에도 발생할 수 있다. \n" +
                "또 눈꺼풀 종양이 있는 경우에도 마찰로 자극이 되어 결막염이 생길 수 있다.\n", "치료법\n" + "원인에 따라 다르게 치료한다. \n" +
                "세균 감염의 경우 항생제와 항염증제 안약을 처방하여 증상을 완화한다. \n" +
                "상태에 따라 안약을 하루 1~3회 눈에 점안하기도 한다.\n" +"예방법\n" + "감염성 결막염을 예방하기 위해서는 감염된 다른 고양이와의 접촉을 최소화한다. \n" +
                "환경을 깨끗하게 하고 주기적으로 눈 상태를 관찰하는 것이 도움이 될 수 있다.\n")

        insertDisease(db, "각막궤양", "고양이", "눈 주변이 뿌옇게 변하거나, 눈 주위가 붉게 충혈될 수 있다. \n" +
                "또한 빛을 비췄을 때 경련하듯 깜빡이는 모습을 보이기도 한다. \n" +
                "녹색 분비물이 눈에서 나오는 것을 목격할 수도 있다. \n" +
                "고양이가 눈에 가려움증을 느껴 긁으려 행동하는 모습을 보일 수 있다.\n", "정확한 기전이 알려지지 않았지만, 호산구성 각막염은 허피스 바이러스에 의해 나타난다는 추정이 유력하다. \n" +
                "또한 눈에 외상을 입거나, 눈을 완전히 감을 수 없어 눈 표면이 먼지나 화학물질 등에 노출될 경우에도 각막염이 발생할 수 있다.\n", "치료법\n" + "궤양성 각막염은 스테로이드 성분이 상태를 악화시킬 수 있으므로 항생제를 포함한 점안약물들을 사용해 치료한다. \n" +
                "허피스 바이러스가 원인인 각막염의 경우 항바이러스제, 시도포비어와 같은 약물을 사용할 수 있다. 각막 보호 및 치유에 도움이 되는 렌즈삽입을 실시하기도 한다.\n" + "예방법\n" + "허피스 바이러스의 경우 예방접종을 통해 감염을 예방할 수 있지만, 그 외의 각막염은 별도의 예방이 어렵다.\n")

        insertDisease(db, "각막부골편", "고양이", "각막이 갈색-검은색으로 변색해 보이거나 갈색 점처럼 보인다. \n" +
                "갈색 점은 병이 경과한 시간에 따라 사이즈, 모양, 깊이가 다양하게 나타난다. \n" +
                "임상증상으로 통증에 따라 과도한 눈물 흘림과 눈 찌푸림, 3안검의 노출 등이 나타날 수 있다.\n", "명확하게 밝혀지지는 않았지만, 지금까지 추정되는 원인으로는 만성각막궤양, 안검내번 및 첩모로 인한 오랜 각막염, 안구건조증, 고양이 허피스바이러스(FHV-1)에 의한 각막염 등이다.\n", "치료법\n" + "일반적으로 항생제 안약과 각막재생을 촉진하는 안약을 사용해 치료한다. \n" +
                "특히 고양이 허피스바이러스 감염에 의한 것으로 진단될 땐 항바이러스제 안약 점안과 함께 구강투여용 항바이러스제 복용을 동시에 진행한다. \n" +
                "심해질 경우 수슬적 제거가 필요하다.\n" + "예방법\n" + "안과전문의에게 주기적으로 안과검진을 받아야 한다. \n" +
                "또한 허피스바이러스 활성화에 영향을 끼치는 스트레스요인(환경변화, 식이변화, 새로운 동물의 입양 등)을 피하는 것도 중요하다.\n")
        insertDisease(db, "비궤양성각막염", "고양이", "각막이 팽창하거나, 눈에 뿌연 막이 생기며, 분비물이 흐르게 된다. \n" +
                "평소보다 눈물을 흘리는 횟수가 늘고 눈곱이 많아지며 빨갛게 충혈된다.\n" +
                "\n", "가장 큰 원인은 헤르페스 바이러스 감염과 녹내장, 외상성 부상 등이 있다.", "치료법\n" + "비궤양성 각막염의 경우 국소 스테로이드 약물을 사용해 치료할 수 있다.\n")
        insertDisease(db, "안검염", "고양이", "눈꺼풀 피부, 속눈썹 주변 부위에 염증이 생기는 흔한 질병 중 하나이다.\n" +
                "안검염이 발생한 부위는 빨갛게 부어오르고 눈곱이 끼며 만성화되면 해당 부위의 탈모, 미란, 각질, 삼출액이 발생한다.\n", "감염에 의해 발생하거나 알레르기, 자가면역질환과 같은 기저질환에 의한 세균감염과 같은 다양한 원인에 의해 발생한다.\n", "치료법\n" + "해당 부위를 긁지않도록 넥카라를 이용하는 것이 좋다. \n" +
                "안약에 의해 접촉성으로 발생한 안검염의 경우, 해당 약물을 잠시 중단하는 것을 권장한다.\n" +
                "수건에 따뜻한 물을 적셔서 분비물들을 부드럽게 닦아주면 치료에 도움이 될 수 있다.\n" +
                "이때 눈알에는 직접적으로 닿지않도록 주의해야한다.\n")


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DISEASES")
        onCreate(db)
    }

    private fun insertDisease(db: SQLiteDatabase, name: String, species: String, symptoms: String, causes: String, treatments: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_SPECIES, species)
        values.put(COLUMN_SYMPTOMS, symptoms)
        values.put(COLUMN_CAUSES, causes)
        values.put(COLUMN_TREATMENTS, treatments)
        val rowId = db.insert(TABLE_DISEASES, null, values)
        Log.d("DBHelper", "Inserted disease: $name, Species: $species, Row ID: $rowId")

    }

    fun getDiseaseInformation(diseaseName: String): Disease? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DISEASES,
            arrayOf(COLUMN_NAME, COLUMN_SPECIES, COLUMN_SYMPTOMS, COLUMN_CAUSES, COLUMN_TREATMENTS),
            "$COLUMN_NAME=?",
            arrayOf(diseaseName),
            null,
            null,
            null
        )

        var disease: Disease? = null
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
            val speciesIndex = cursor.getColumnIndex(COLUMN_SPECIES)
            val symptomsIndex = cursor.getColumnIndex(COLUMN_SYMPTOMS)
            val causesIndex = cursor.getColumnIndex(COLUMN_CAUSES)
            val treatmentsIndex = cursor.getColumnIndex(COLUMN_TREATMENTS)

            if (nameIndex >= 0 && symptomsIndex >= 0 && causesIndex >= 0 && treatmentsIndex >= 0) {
                val name = cursor.getString(nameIndex)
                val species = cursor.getString(speciesIndex)
                val symptoms = cursor.getString(symptomsIndex)
                val causes = cursor.getString(causesIndex)
                val treatments = cursor.getString(treatmentsIndex)
                disease = Disease(name,species, symptoms, causes, treatments)
            } else {
                Log.d("Cursor Error", "One or more column indexes are invalid.")
            }
        } else {
            Log.d("Cursor Error", "Cursor is empty.")
        }

        cursor.close()
        db.close()
        return disease
    }

    fun getAllDiseases(): List<Disease> {
        val diseaseList = mutableListOf<Disease>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DISEASES, null, null, null, null, null, null)

        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val speciesIndex = cursor.getColumnIndex(COLUMN_SPECIES)
        val symptomsIndex = cursor.getColumnIndex(COLUMN_SYMPTOMS)
        val causesIndex = cursor.getColumnIndex(COLUMN_CAUSES)
        val treatmentsIndex = cursor.getColumnIndex(COLUMN_TREATMENTS)

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            val species = cursor.getString(speciesIndex)
            val symptoms = cursor.getString(symptomsIndex)
            val causes = cursor.getString(causesIndex)
            val treatments = cursor.getString(treatmentsIndex)
            val disease = Disease(name, species, symptoms, causes, treatments)
            diseaseList.add(disease)
        }

        cursor.close()
        db.close()
        return diseaseList
    }

    fun getSpeciesDisease(diseaseSpecies : String): Disease?{
            val db = readableDatabase
            val cursor = db.query(
                TABLE_DISEASES,
                arrayOf(COLUMN_NAME, COLUMN_SPECIES, COLUMN_SYMPTOMS, COLUMN_CAUSES, COLUMN_TREATMENTS),
                "$COLUMN_SPECIES=?",
                arrayOf(diseaseSpecies),
                null,
                null,
                null
            )

            var disease: Disease? = null
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val speciesIndex = cursor.getColumnIndex(COLUMN_SPECIES)
                val symptomsIndex = cursor.getColumnIndex(COLUMN_SYMPTOMS)
                val causesIndex = cursor.getColumnIndex(COLUMN_CAUSES)
                val treatmentsIndex = cursor.getColumnIndex(COLUMN_TREATMENTS)

                if (nameIndex >= 0 && symptomsIndex >= 0 && causesIndex >= 0 && treatmentsIndex >= 0) {
                    val name = cursor.getString(nameIndex)
                    val species = cursor.getString(speciesIndex)
                    val symptoms = cursor.getString(symptomsIndex)
                    val causes = cursor.getString(causesIndex)
                    val treatments = cursor.getString(treatmentsIndex)
                    disease = Disease(name,species, symptoms, causes, treatments)
                } else {
                    Log.d("Cursor Error", "One or more column indexes are invalid.")
                }
            } else {
                Log.d("Cursor Error", "Cursor is empty.")
            }

            cursor.close()
            db.close()
            return disease
    }

    data class Disease(val name : String, val species : String, val symptoms: String, val causes: String, val treatments: String)
}
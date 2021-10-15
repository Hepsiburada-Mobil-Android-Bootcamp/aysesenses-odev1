package com.android.figma_task.guess_game.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GameViewModel : ViewModel() {

    //MutableLiveData değeri değiştirilebilen Bir LiveDatadır.
    //Genel bir sınıf olduğu için tutacağımız değerin türünü de belirtmeliyiz.
    val word = MutableLiveData<String>()
    val score = MutableLiveData<Int>()

    val gameFinish = MutableLiveData<Boolean>()
    val buttonVisible = MutableLiveData<Boolean>()

    private lateinit var wordList: MutableList<String>

    // Oyunun bittiği süre
    private val DONE = 0L
    private val TOTALTIME = 20000L
    private var timer: CountDownTimer? = null
    val currentTime = MutableLiveData<Long>()


    init {
        //viewmodel oluştuğu zaman ilk önce init bloğundaki kodlar çalışır.
        //başlangıç puanını ve kelime başlatıldı
        word.value = ""
        score.value = 0

        //viewmodel oluşur oluşmaz zamanlayıcı başlar. İlk currentTime değeri null dur.
        startTimer()

        //Bu yöntemler init bloğunda tutuyoruz, çünkü kelime listesini fragment her oluşturulduğunda değil,
        //ViewModel oluşturulduğunda yenilememiz gerekir.
        resetList()
        nextWord()
        Log.i("button", "${buttonVisible.value}")

    }

    fun startTimer() {
        buttonVisible.value = false

        //şuan ki zamanı bir değişkene atayıp koruyoruz. Bunu zamanın değerini tekrar başlatmak için Long? çevirdik.
        val time = currentTime.value?.times(1000)

        //currentTime viewmodel ilk başalatıldığında null du. time değeri null ise  zaman time2 den başlasın
        timer = object : CountDownTimer(time ?: TOTALTIME, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                //milisaniye cinsinden geçen zamanı günceller
                currentTime.value = millisUntilFinished / 1000
            }

            //süre bittiğinde bu method çağrılır ve sürenin değeri OL olduğunda oyun biter
            override fun onFinish() {
                currentTime.value = DONE
                onGameFinish()
            }
        }
        timer?.start()
    }

    //zamanlayıcıyı durdurur
    fun stopTimer() {
        buttonVisible.value = true
        timer?.cancel()
    }

    //ekranda hangi kelimeyi tutacağımızı koruması için bu method viewmodel içinde kalır.
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        //shuffle ile kelime listesi karıştırılır.
        Collections.shuffle(wordList)
    }

    fun onGameFinish() {
        gameFinish.value = true
    }

    fun onGameFinishComplete() {
        gameFinish.value = false
    }

    private fun nextWord() {
        //liste boşalınca kelime listesini yenileyip başlat
        if (wordList.isEmpty()) {
            resetList()
        } else {
            //Listeden seçilen kelime silinir
            word.value = wordList.removeAt(0)
        }
    }

    fun onSkip() {
        //score değeriini bir azaltıyoruz. ve null-safety kontrolü yapıyoruz
        score.value = (score.value)?.plus(-1)
        nextWord()
    }

    fun onPass() {
        nextWord()
    }

    fun onCorrect() {
        score.value = (score.value)?.plus(1)
        nextWord()
    }

    //onCleared ViewModel silinmeden önce çağrılır.
    //Bellek sızıntısı yaşanmaması için ViewModel yok edilmeden önce zamanlayıcıyı kaldırılır
    override fun onCleared() {
        super.onCleared()
        // zamanlayıcıyı iptal eder
        stopTimer()
    }
}
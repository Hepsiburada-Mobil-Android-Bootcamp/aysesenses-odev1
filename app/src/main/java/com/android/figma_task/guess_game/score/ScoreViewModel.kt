package com.android.figma_task.guess_game.score

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    val score = MutableLiveData<Int>()
    val playAgain = MutableLiveData<Boolean>()

    init {
        score.value = finalScore
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }

    fun onPlayAgain() {
        playAgain.value = true
    }

    fun onPlayAgainComplete() {
        playAgain.value = false
    }
}
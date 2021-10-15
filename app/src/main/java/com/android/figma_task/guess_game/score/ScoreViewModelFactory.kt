package com.android.figma_task.guess_game.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {

    //Bu uygulamada, puanı doğrudan viewModel.score değişkenine atayabileceğiniz için ScoreViewModel için bir ViewModelFactory eklemek gerekli değildir.
    // Ancak bazen verilere tam olarak viewModel başlatıldığında ihtiyaç duyarsınız.

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        // isAssignableFrom() modelClass ile ScoreViewModel i karşılaştırır.Aynı sınıftan mı değil mi die kontrol eder.
        // Aynı ise ScoreViewModel nesnesini oluşturur.
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.android.figma_task.guess_game.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.android.figma_task.R
import com.android.figma_task.databinding.GameFragmentBinding
import com.android.figma_task.guess_game.data.Words

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //ViewModelProvider ile GameViewModel yalnızca bir kez oluşturulur ve silinmez.
        //Eger ViewModel ile başlatsaydık GameViewModel her çağrıldığında için yeniden oluşturulurdu.
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        binding.gameViewModel = viewModel

        //gözlemlenen LiveData nesnesinde tutulan veri değiştiğinde, observe içinde, scorText puanını yeni puanla günceller.
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
            })
        // kelimeyi gözlemliyoruz, Livedata nesnesinde tutulan veri değiştiğinde, worsText yeni kelime ile güncellenir.
        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord.toString()
        })
        viewModel.gameFinish.observe(viewLifecycleOwner, Observer { hasGameFinished ->
            if(hasGameFinished) gameFinished()
        })
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { currentTime ->
            binding.timerText.text = currentTime.toString()
        })
        viewModel.buttonVisible.observe(viewLifecycleOwner, Observer { isVisible ->
           binding.apply {
               when {
                   isVisible -> {
                       contiuneTimer.visibility = View.VISIBLE
                       stopTimer.visibility = View.GONE
                       correctButton.isClickable = false
                       tabuButton.isClickable = false
                       skipButton.isClickable= false
                   }
                   else -> {
                       stopTimer.visibility = View.VISIBLE
                       contiuneTimer.visibility = View.GONE
                       correctButton.isClickable = true
                       tabuButton.isClickable = true
                       skipButton.isClickable= true
                   }
               }
           }
        })

        return binding.root
    }

    //GameFragment te oyun skorumuzu SaveArgs ile ScoreFragmentine argüment olarak güvenli şekilde yolluyoruz.
    private fun gameFinished() {
        Toast.makeText(activity, "Game finished", Toast.LENGTH_SHORT).show()

        val action = GameFragmentDirections.actionGameToScore()
        //livedata da tutulan score değeri eğer null ise 0 değerini döndürür.
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        //GameViewModel'de, oyun tamamlandı olayı gameFinish'i sıfırlamak için bir onGameFinishComplete() yöntemi ekledik.
        viewModel.onGameFinishComplete()
    }
}

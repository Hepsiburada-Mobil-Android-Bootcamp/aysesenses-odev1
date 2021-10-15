package com.android.figma_task.guess_game.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.figma_task.R
import com.android.figma_task.databinding.ScoreFragmentBinding


class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )

        //ilk önce ScoreViewFactory başlatılır. Bundle da tutulan score argümentin son puanı iletilir.
        viewModelFactory =
            ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        //sonra viewModelFactory sınıfında tanımlanan factory yöntemini kullanarak ScoreViewModel nesnesini oluşturulur
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel

        //viewModel'i başlattıktan sonra, LiveData ve gözlemci kullanarak sscoreText te skor puanı her değiştiğinde güncellenir.
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.playAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            //playAgain true ise oyunu yeniden başlat
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionScoreToGame())
                viewModel.onPlayAgainComplete()
            }
        })

        //binding.playAgainButton.setOnClickListener {  viewModel.onPlayAgain()  }
        return binding.root
    }
}

package com.example.dispositivosmoviles.fragment


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    private  lateinit var binding: FragmentSecondBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(
            layoutInflater, container,false

        )

        var like = false


        binding.imageView.setOnClickListener {
            like = likeAnimation(binding.imageView, R.raw.bandai_dokkan, like )
        }


        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false)// se necesita 3 cosas ,
    }

    private fun likeAnimation(imageView: LottieAnimationView,
                              animation: Int,
                              like: Boolean) : Boolean {

        if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        } else {
            imageView.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animator: Animator) {

                        imageView.setImageResource(R.drawable.corazones_100)
                        imageView.alpha = 1f
                    }

                })

        }

        return !like
    }

}
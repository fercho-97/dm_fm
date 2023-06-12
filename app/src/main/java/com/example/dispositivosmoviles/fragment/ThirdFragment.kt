package com.example.dispositivosmoviles.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.FragmentSecondBinding
import com.example.dispositivosmoviles.databinding.FragmentThirdBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    private  lateinit var binding: FragmentThirdBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThirdBinding.inflate(
            layoutInflater, container,false

        )

        var like = false


        binding.imageView.setOnClickListener {
            like = likeAnimation(binding.imageView, R.raw.joy, like )
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
package com.example.dispositivosmoviles.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class FrgamentsManager {

    fun replaceFrgament(manager: FragmentManager, container: Int, fragment: Fragment) {

        with(manager.beginTransaction()) {
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }


    }

    fun addFrgament(manager: FragmentManager, container: Int, fragment: Fragment){

        with(manager.beginTransaction()){
            add(container, fragment)
            addToBackStack(null)
            commit()
        }



    }

}
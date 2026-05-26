package com.mooji.cod.moojifood.util

import java.lang.Exception

interface BasePresenter<T> {

    fun onAttach(view : T)

    fun onDetach()



}


interface BaseView {


    // Write your functions for View here =>


}


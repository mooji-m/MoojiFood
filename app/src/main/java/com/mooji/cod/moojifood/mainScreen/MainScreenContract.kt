package com.mooji.cod.moojifood.mainScreen

import com.mooji.cod.moojifood.model.Food
import com.mooji.cod.moojifood.util.BasePresenter
import com.mooji.cod.moojifood.util.BaseView
import java.text.FieldPosition

interface MainScreenContract {



    interface Presenter : BasePresenter<MainScreenContract.View> {

        fun firstRun()

        fun onSearchingFood(filter : String)

        fun onAddNewFoodClicked(food : Food)

        fun onDeleteAllFoods()

        fun onUpdateFood(food:Food,pos: Int)

        fun onDeleteFood(food:Food, pos:Int)

    }



    interface View : BaseView {

        fun showAllFoods(data:List<Food>)

        fun refreshFood(data:List<Food>)

        fun addNewFood(newFood:Food)

        fun deleteFood(oldFood:Food,pos: Int)

        fun updateFood(editingFood:Food,pos:Int)



    }
}
package com.mooji.cod.moojifood

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mooji.cod.moojifood.databinding.ItemFoodBinding
import com.mooji.cod.moojifood.room.Food
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(private val data:ArrayList<Food>, private val foodEvent: foodEvents) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {


    @SuppressLint("SetTextI18n")
    fun dataBind(position:Int) {

        binding.itemTxtSubject.text = data[position].txtSubject
        binding.itemTxtCity.text = data[position].txtCity
        binding.itemTxtPrice.text = "$ " + data[position].txtPrice + " vip"
        binding.itemTxtDistance.text = data[position].txtDistance + " miles from you"
        binding.itemRating.rating = data[position].rating
        binding.itemTxtRating.text = "(" + data[position].numOfRating.toString() + "Ratings )"

        Glide
            .with(binding.root.context)
            .load(data[position].urlImage)
            .transform(RoundedCornersTransformation(16,4))
            .into(binding.itemImgMain)

        itemView.setOnClickListener {
            foodEvent.onFoodClicked(data[adapterPosition],adapterPosition)
        }

        itemView.setOnLongClickListener {
            foodEvent.onFoodLongClicked(data[adapterPosition],adapterPosition)

            true
        }
    }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FoodViewHolder(binding)


    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.dataBind(position)


    }

    override fun getItemCount(): Int {
            return data.size
    }

    fun addFood(newFood: Food) {
        // add food to list :
        data.add(0,newFood)
        notifyItemInserted(0)


    }

    fun removeFood(oldFood: Food, oldPosition:Int) {
        // remove item from list :
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(updateFood: Food, position:Int) {

        // update item from list :
        data[position] = updateFood
        notifyItemChanged(position)
    }

    fun setData(newList:ArrayList<Food>) {

        // search item from list :
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()


    }

    interface foodEvents {

        // 1. create interface in adapter
        // 2. get an object  of interface in args of adapter
        // 3. fill (call) object of interface with your data
        // 4. implementation in MainActivity

        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food : Food, position: Int)


    }


}

package com.mooji.cod.moojifood

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mooji.cod.moojifood.databinding.*
import com.mooji.cod.moojifood.room.Food
import com.mooji.cod.moojifood.room.FoodDao
import com.mooji.cod.moojifood.room.MyDatabase
import kotlin.collections.ArrayList
import kotlin.random.Random

const val BASE_URL_IMAGE = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food"
class MainActivity : AppCompatActivity(), FoodAdapter.foodEvents {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter
    private lateinit var foodDao: FoodDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        // how to use recycler view :
//        // 1. create view of recyclerView in activity_main.xml
//        // 2. create item for recyclerView
//        // 3. create adapter and view holder for recyclerView
//        // 4. set adapter to recyclerView and set layout manager

        //در نهایت برای استفاده از دیتابیس ما به دیتابیس خود نیازی نداریم بلکه به Dao نیاز داریم
        foodDao = MyDatabase.getDatabase(this).foodDao

        val sharedPreferences = getSharedPreferences("firstFood",Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("first_run",true)) {
            firstRun()
            sharedPreferences.edit().putBoolean("first_run",false).apply()
        }

        showAllData()


        binding.btnRemoveAllFood.setOnClickListener {

            removeAllData()
        }



        binding.btnAddNewFood.setOnClickListener {

            addNewFood()

        }



        binding.edtSearch.addTextChangedListener {editTextInput ->

            searchOnDatabase(editTextInput.toString())
        }


    }

    private fun searchOnDatabase(editTextInput:String) {




        if (editTextInput.length > 0) {

            val searchedData = foodDao.searchFood(editTextInput)

              //without database =>
//            val cloneList = foodList.clone() as ArrayList<Food>
//
//            val filteredList = cloneList.filter { itemFood->
//                itemFood.txtSubject.contains(editTextInput,true )
//            }

            myAdapter.setData(ArrayList(searchedData))

        } else {

            val data = foodDao.getAllFood()
            myAdapter.setData(ArrayList(data))

        }
    }

    private fun addNewFood() {
        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        //در ایجا اگر دیالوگ را جدا کریت کنیم و بعد دایلوگ دیسمیس را صدا بزنیم
        //ارور میدهد چون کریت به بیلدر اشاره دارد نه نمونه دیالوگ و دیس میس هم روی  بیلدر صدا زده میشود در صورتی که باید روی نمونه دیالوگ صدا زده شود پس برای ذخیره کردن بیلدر به تنهایی کافی نیست و باید بعدش ان را کریت کنیم

        dialogBinding.dialogBtnDone.setOnClickListener {

            if (dialogBinding.dialogEdtNameFood.length() > 0 && dialogBinding.dialogEdtFoodCity.length() > 0
                && dialogBinding.dialogEdtFoodPrice.length() > 0 && dialogBinding.dialogEdtFoodDistance.length() > 0) {

                val txtName = dialogBinding.dialogEdtNameFood.text.toString()
                val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                val txtRatingNumber:Int = (1..150).random()
                val ratingBarStar:Float = Random.nextFloat() *  6 + 0

                val randomImg = (1..13).random()
                val urlImgRandom = "$BASE_URL_IMAGE$randomImg.jpg"

                // create new food to add to recycler view
                val newFood = Food(
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = urlImgRandom,
                    numOfRating = txtRatingNumber,
                    rating = ratingBarStar
                )
                // add item :
                myAdapter.addFood(newFood)
                foodDao.insert(newFood) // اگر به غذای جدید آیدی میدادیم در دیتابیس در آیدی موجود غذا را جایگذاری میکرد برا همین آیدی ندادیم

                binding.recyclerMain.scrollToPosition(0)
                dialog.dismiss()

            } else {
                Toast.makeText(this, "لطفا همه مقادیر را وارد کنید :)", Toast.LENGTH_SHORT).show()

            }


        }
    }

    private fun removeAllData() {
        foodDao.deleteAllData()
        showAllData()
    }

    private fun firstRun() {


        val foodList = arrayListOf(
            Food(
                txtSubject = "Hamburger" ,
                txtPrice = "15" ,
                txtDistance = "3" ,
                txtCity = "Isfahan, Iran" ,
                urlImage = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg" ,
                numOfRating = 20 ,
                rating = 4.5f
            ) ,
            Food(
                txtSubject ="Grilled fish" ,
                txtPrice ="20" ,
                txtDistance ="2.1" ,
                txtCity ="Tehran, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,
                numOfRating =10 ,
                rating =4f
            ) ,
            Food(
                txtSubject ="Lasania" ,
                txtPrice ="40" ,
                txtDistance ="1.4" ,
                txtCity ="Isfahan, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,
                numOfRating =30 ,
                rating =2f
            ) ,
            Food(
                txtSubject ="pizza" ,
                txtPrice ="10" ,
                txtDistance ="2.5" ,
                txtCity ="Zahedan, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg" ,
                numOfRating =80 ,
                rating =1.5f
            ) ,
            Food(
                txtSubject ="Sushi" ,
                txtPrice ="20" ,
                txtDistance ="3.2" ,
                txtCity ="Mashhad, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,
                numOfRating =200 ,
                rating =3f
            ) ,
            Food(
                txtSubject ="Roasted Fish" ,
                txtPrice ="40",
                txtDistance ="3.7" ,
                txtCity ="Jolfa, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,
                numOfRating =50 ,
                rating =3.5f
            ) ,
            Food(
                txtSubject ="Fried chicken" ,
                txtPrice ="70" ,
                txtDistance ="3.5" ,
                txtCity ="NewYork, USA" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,
                numOfRating =70 ,
                rating =2.5f
            ) ,
            Food(
                txtSubject ="Vegetable salad" ,
                txtPrice ="12" ,
                txtDistance ="3.6" ,
                txtCity ="Berlin, Germany" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,
                numOfRating =40 ,
                rating =4.5f
            ) ,
            Food(
                txtSubject ="Grilled chicken" ,
                txtPrice ="10" ,
                txtDistance ="3.7" ,
                txtCity ="Beijing, China" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,
                numOfRating =15 ,
                rating =5f
            ) ,
            Food(
                txtSubject ="Baryooni" ,
                txtPrice ="16" ,
                txtDistance ="10" ,
                txtCity ="Ilam, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,
                numOfRating =28 ,
                rating =4.5f
            ) ,
            Food(
                txtSubject ="Ghorme Sabzi" ,
                txtPrice ="11.5" ,
                txtDistance ="7.5" ,
                txtCity ="Karaj, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,
                numOfRating =27 ,
                rating =5f
            ) ,
            Food(
                txtSubject ="Rice" ,
                txtPrice ="12.5" ,
                txtDistance ="2.4" ,
                txtCity ="Shiraz, Iran" ,
                urlImage ="https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,
                numOfRating =35 ,
                rating =2.5f
            ) ,
        )
        foodDao.insertAllFood(foodList)

    }
     fun showAllData() {
        val foodData = foodDao.getAllFood()
        myAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    override fun onFoodClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        updateDialogBinding.dialogEdtNameFood.setText(food.txtSubject)
        updateDialogBinding.dialogEdtFoodCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtFoodDistance.setText(food.txtDistance)

        updateDialogBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        updateDialogBinding.dialogUpdateUpdate.setOnClickListener {
            if (updateDialogBinding.dialogEdtNameFood.length() > 0 && updateDialogBinding.dialogEdtFoodCity.length() > 0
                && updateDialogBinding.dialogEdtFoodPrice.length() > 0 && updateDialogBinding.dialogEdtFoodDistance.length() > 0) {
                val txtName = updateDialogBinding.dialogEdtNameFood.text.toString()
                val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtFoodDistance.text.toString()

                val updateFood = Food(
                    id = food.id,
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = food.urlImage,
                    numOfRating = food.numOfRating,
                    rating = food.rating
                )

                // update item in  adapter =>
                myAdapter.updateFood(updateFood, position)

                //update item in database =>
                foodDao.updateFood(updateFood)

                dialog.dismiss()
            } else {
                Toast.makeText(this, "لطفا همه مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onFoodLongClicked(food: Food, position: Int) {
        val dialogRemove = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogRemoveItemBinding.inflate(layoutInflater)
        dialogRemove.setView(dialogDeleteBinding.root)
        dialogRemove.setCancelable(true)
        dialogRemove.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialogRemove.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {
            dialogRemove.dismiss()
            myAdapter.removeFood(food, position)

            //از روی ایدی میرود غذای ما را پاک میکند
            foodDao.deleteFood(food)

            //در اینجا نمیخواد اول از دیتابیس پاک کنیم و دوباره دیتا رو توی اداپتر بریزیمو نمایش بدیم
            //اول از اداپتر پاک میکنیم همونو از دیتابیس هم پاک میکنیم
        }
    }


}
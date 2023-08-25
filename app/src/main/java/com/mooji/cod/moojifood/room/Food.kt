package com.mooji.cod.moojifood.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_food")
data class Food(

    //در اینجا میخواهیم خودکار primary key ما را تولید کند و ما نخواهیم خودمان کلید را بسازیم پس در داخل () مینوسیم :
    // و چون غذاهایی که ایجاد کردیم و دادیم id ندارند در اکتیویتی ارور میدهد که آرگمان آیدی هم وارد کنیم برای اینکار
    //مقدار id را null قرار میدهیم تا در اکتیویتی گیر نده منتها باید مشخص کنیم هر آیتم غذا که دادیم برای کدام قسمت است
    //تا ارور ندهد
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null ,

    val txtSubject : String,
    val txtPrice : String,
    val txtDistance :String,
    val txtCity : String,

    @ColumnInfo(name = "url") //میتوانیم اسم جدول را هم به دلخواه خود تغییر دهیم
    val urlImage : String,


    val numOfRating : Int,
    val rating : Float)
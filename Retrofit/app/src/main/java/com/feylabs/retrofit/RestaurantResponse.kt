package com.feylabs.retrofit

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    val error: Boolean,
    val message: String,
    val restaurant: Restaurant
){
    data class Restaurant(
        val address: String,
        val categories: List<Category>,
        val city: String,
        val customerReviews: List<CustomerReview>,
        val description: String,
        val id: String,
        val menus: Menus,
        val name: String,
        val pictureId: String,
        val rating: Double
    ){
        data class Category(
            val name: String
        )

        data class Menus(
            val drinks: List<Drink>,
            val foods: List<Food>
        ){
            data class Food(
                val name: String
            )
            data class Drink(
                val name: String
            )
        }
        data class CustomerReview(
            val date: String,
            val name: String,
            val review: String
        )
    }
}

data class PostReviewResponse(

    @field:SerializedName("customerReviews")
    val customerReviews: List<RestaurantResponse.Restaurant.CustomerReview>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)

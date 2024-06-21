package com.capstone.allergysavvy.utils

fun imageUrlIngredient(ingredient: String): String {
    return when (ingredient) {
        "Legumes" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Legumes.png"
        "Spices" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Spices.png"
        "Fats" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Fats.png"
        "Nuts & Seeds" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Nuts%20and%20Seeds.png"
        "Herbs" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Herbs.png"
        "Beverages" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Beverages.png"
        "Condiments" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Condiments.png"
        "Fruits" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Fruits.png"
        "Grains" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Grains.png"
        "Dairy" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Dairy.png"
        "Vegetables" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Vegetables.png"
        "Sweeteners" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Sweeteners.png"
        "Sauces" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Sauces.png"
        "Alcohol" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Alcohol.png"
        "Meat" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Meat.png"
        "Pasta & Noodles" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Pasta%20%26%20Noodles.png"
        "Snacks" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Snacks.png"
        "Confectionery" -> "https://raw.githubusercontent.com/AllergySavvy/Icon-Image-for-Ingredients/main/Confectionery.png"
        else -> ""
    }
}

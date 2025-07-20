package it.simo.outcomecompose.data

import android.content.Context
import com.google.gson.Gson
import it.simo.outcomecompose.data.response.BetItemsResponse
import it.simo.outcomecompose.data.response.GameGroupsResponse

class DataGetter {
    fun getGameGroupList(context: Context, fileName: String): GameGroupsResponse {
        val response = parseJsonFromAssetsToType(context, fileName, GameGroupsResponse::class.java)
        return response ?: GameGroupsResponse(emptyList())
    }
    
    fun getBetItemList(context: Context, fileName: String): BetItemsResponse {
        val response = parseJsonFromAssetsToType(context, fileName, BetItemsResponse::class.java)
        return response ?: BetItemsResponse(emptyList())
    }
}

fun loadJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun <T> parseJsonFromAssetsToType(
    context: Context,
    fileName: String,
    clazz: Class<T> // Pass the class of T
): T? { // Return T? to allow for parsing errors
    return try {
        val json = loadJsonFromAssets(context, fileName)
        Gson().fromJson(json, clazz)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
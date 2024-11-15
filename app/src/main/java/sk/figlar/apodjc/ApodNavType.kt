package sk.figlar.apodjc

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import sk.figlar.apodjc.api.ApodApiModel

object CustomNavType {

    val Apod = object : NavType<ApodApiModel>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): ApodApiModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ApodApiModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: ApodApiModel): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: ApodApiModel) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

}
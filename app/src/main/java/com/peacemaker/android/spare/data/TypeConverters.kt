package com.peacemaker.android.spare.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class PaymentMethodTypeConverter {

    @TypeConverter
    fun fromJson(json: String?): PaymentMethod? {
        return json?.let { Gson().fromJson(it, PaymentMethod::class.java) }
    }

    @TypeConverter
    fun toJson(paymentMethod: PaymentMethod?): String? {
        return paymentMethod?.let { Gson().toJson(it) }
    }
}

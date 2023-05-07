package com.peacemaker.android.spare.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peacemaker.android.spare.data.*
import com.peacemaker.android.spare.data.dao.BankAccountDao
import com.peacemaker.android.spare.data.dao.TransactionDao
import com.peacemaker.android.spare.data.dao.UserDao
import com.peacemaker.android.spare.data.dao.WalletDao

@Database(entities = [User::class, Transaction::class, BankAccount::class, Wallets::class, PaymentMethod::class], version = 1)
//@TypeConverters(PaymentMethodTypeConverter::class)
abstract class SpareDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun bankAccountDao(): BankAccountDao
    abstract fun creditCardDao(): WalletDao

    companion object {
        @Volatile
        private var instance: SpareDatabase? = null

        fun getInstance(context: Context): SpareDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    SpareDatabase::class.java,
                    "spare_database"
                ).build().also { instance = it }
            }
        }
    }
}



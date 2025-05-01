package com.learn.cmm.core.database.portofolio

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.cmm.portofolio.data.local.PortfolioCoinEntity
import com.learn.cmm.portofolio.data.local.PortfolioDao
import com.learn.cmm.portofolio.data.local.UserBalanceDao
import com.learn.cmm.portofolio.data.local.UserBalanceEntity

@Database(entities = [PortfolioCoinEntity::class, UserBalanceEntity::class], version = 2)
@ConstructedBy(PortfolioDatabaseCreator::class)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
    abstract fun userBalanceDao(): UserBalanceDao
}

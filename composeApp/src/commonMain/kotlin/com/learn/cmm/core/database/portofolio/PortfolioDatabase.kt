package com.learn.cmm.core.database.portofolio

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.learn.cmm.portofolio.data.local.PortfolioCoinEntity
import com.learn.cmm.portofolio.data.local.PortfolioDao

@Database(entities = [PortfolioCoinEntity::class], version = 1)
@ConstructedBy(PortfolioDatabaseCreator::class)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
}

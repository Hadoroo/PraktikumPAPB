package com.example.praktikumpapb.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class TugasRepo(application: Application) {
    private val mTugasDao: TugasDAO
    private val executorService = Executors.newSingleThreadExecutor()
    init {
        val db = TugasDb.getDatabase(application)
        mTugasDao = db.tugasDao()
    }
    fun getAllTugas(): LiveData<List<Tugas>> = mTugasDao.getAllTugas()
    fun insert(tugas: Tugas){
        executorService.execute {mTugasDao.insertTugas(tugas)}
    }
}
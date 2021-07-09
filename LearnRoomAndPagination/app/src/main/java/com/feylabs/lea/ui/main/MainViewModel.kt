package com.feylabs.lea.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.feylabs.lea.database.Note
import com.feylabs.lea.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository = NoteRepository(application)
    fun getAllNotes(): LiveData<PagedList<Note>> = LivePagedListBuilder(mNoteRepository.getAllNotes(), 10).build()

}
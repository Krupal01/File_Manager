package com.example.filemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.filemanager.repository.FileListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val fileListRepo: FileListRepo) : ViewModel() {

    val fileListLiveData :LiveData<List<File>>
    get() = fileListRepo.fileList

    fun getFiles(path : String){
        fileListRepo.getFileList(path)
    }
}
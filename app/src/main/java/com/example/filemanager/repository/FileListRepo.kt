package com.example.filemanager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filemanager.data.ExternalFiles
import java.io.File
import javax.inject.Inject

class FileListRepo @Inject constructor(private val externalFiles: ExternalFiles) {

    private var _fileList = MutableLiveData<List<File>>()
    val fileList : LiveData<List<File>>
        get() = _fileList

    fun getFileList(path : String){
        _fileList.postValue(externalFiles.readExternalFiles(path))
    }

}
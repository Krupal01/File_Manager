package com.example.filemanager.data

import java.io.File
import javax.inject.Inject

class ExternalFiles @Inject constructor(){

    fun readExternalFiles(path: String): List<File> {
        var fileList = ArrayList<File>()
        var file = File(path)
        if (file.listFiles()!= null){
            fileList.addAll(file.listFiles())
        }
        return fileList
    }
}
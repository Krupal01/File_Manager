package com.example.filemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.R
import com.example.filemanager.adapter.FileListAdapter
import com.example.filemanager.utils.CheckPermission
import com.example.filemanager.utils.Constants
import com.example.filemanager.utils.PermissionListner
import com.example.filemanager.viewmodel.MainViewModel
import com.karumi.dexter.MultiplePermissionsReport
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , PermissionListner {

    @Inject
    lateinit var checkPermission: CheckPermission

    private lateinit var viewModel: MainViewModel
    lateinit var rcvFiles : RecyclerView
    lateinit var filePath:String

    @Inject
    lateinit var adapter : FileListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcvFiles = findViewById(R.id.rcvMain)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (intent.getStringExtra(Constants.FILE_PATH) != null){
            filePath = intent.getStringExtra(Constants.FILE_PATH)!!
        }else{
            filePath = Environment.getExternalStorageDirectory().path
        }

        checkPermission.checkStoragePermission(this)


        viewModel.fileListLiveData.observe(this
        ) {
            Log.i("krupal",it.toString())
            adapter.submitData(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun setRecycler(){
        rcvFiles.layoutManager = LinearLayoutManager(applicationContext)
        Log.i("krupal","rcv set")
        viewModel.fileListLiveData.value?.let { adapter.submitData(it) }
        rcvFiles.adapter = adapter
    }

    override fun onPermissionGranted(p0: MultiplePermissionsReport?) {
        p0.let {
            if (p0 != null) {
                if (p0.areAllPermissionsGranted()){
                    setRecycler()
                    viewModel.getFiles(filePath)
                    Log.i("krupal","permission granted")
                }else{
                    finishAffinity()
                }
            }
        }
    }
}
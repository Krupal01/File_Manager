package com.example.filemanager.utils

import android.Manifest
import android.content.Context
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class CheckPermission @Inject constructor(
    @ApplicationContext private val context: Context
    ) {

    fun checkStoragePermission(listner: PermissionListner) {

        Dexter.withContext(context)
            .withPermissions(

                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                    listner.onPermissionGranted(p0)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest();
                }
            })
            .withErrorListener {
                Log.e(Constants.DEXTER_ERROR, "There was an error: $it")
            }
            .check()
    }

}
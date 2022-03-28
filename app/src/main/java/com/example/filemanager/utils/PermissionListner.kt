package com.example.filemanager.utils

import com.karumi.dexter.MultiplePermissionsReport

interface PermissionListner {
    fun onPermissionGranted(p0: MultiplePermissionsReport?)
}
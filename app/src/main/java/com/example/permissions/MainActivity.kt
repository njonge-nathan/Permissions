package com.example.permissions


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.permissions.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            if (hasPermissions()) {
                btnRequestPermission.isEnabled = false
                tvMessage.text = "All permissions are granted"
            } else {
                btnRequestPermission.isEnabled = true
                tvMessage.text = "All permissions are not granted"
            }

            btnRequestPermission.setOnClickListener {
                requestPermissions()
            }


        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        binding.apply {
            tvMessage.text = "All permissions are granted"
            btnRequestPermission.isEnabled = false
        }
    }

    private fun hasPermissions() =
        EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS
        )


    private fun requestPermissions() {
        EasyPermissions.requestPermissions(
            this,
            "This permissions are required for this application",
            PERMISSION_REQUEST_CODE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_CONTACTS,
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }
}
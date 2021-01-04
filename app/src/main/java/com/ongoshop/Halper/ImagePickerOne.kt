package com.nauatili.Helper;

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.nauatili.BuildConfig
import com.nauatili.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


abstract class ImagePickerOne : AppCompatActivity() {

    private val selectFile = 200
    private val requestCamera = 201
    private val requestPermissionsCamera = 20
    private lateinit var mImageFile: File
    var isVideoEnable: Boolean = false
    var picturePath :String?=null
    private var from1=""

    fun checkPermissionCamera(isVideoEnable: Boolean, from: String) {
        from1=from
        this.isVideoEnable = isVideoEnable
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!cameraPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestPermissionsCamera)
               Helper.showSuccessToast(this,"You  can go to settings to allow permission")
                return
            } else {
                selectImage(isVideoEnable)
            }
        } else {
            selectImage(isVideoEnable)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        var permissionDeny = PackageManager.PERMISSION_DENIED
        for (permission in grantResults) {
            permissionCheck += permission
        }

        if (grantResults.isNotEmpty() && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            selectImage(isVideoEnable)
        }
    }

    private fun cameraPermission(permissions: Array<String>): Boolean {
        return ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, permissions[2]) == PackageManager.PERMISSION_GRANTED
    }

    private fun selectImage(isVideoEnable: Boolean) {
        if (isVideoEnable) {
            val items = resources.getStringArray(R.array.photo_array)
            val builder = AlertDialog.Builder(this)
           // builder.setTitle(resources.getString(R.string.add_choose_action))
            builder.setItems(items) { dialog, item ->
                when (item) {
                    0 -> cameraIntent()
                    1 -> galleryIntent()
                    2 -> dialog.dismiss()
                }
            }
            builder.show()
        } else {
            val items = resources.getStringArray(R.array.photo_array)
            val builder = AlertDialog.Builder(this)
          //  builder.setTitle(resources.getString(R.string.add_choose_action))
            builder.setItems(items) { dialog, item ->
                when (item) {
                    0 -> cameraIntent()
                    1 -> galleryIntent()
                    3 -> dialog.dismiss()
                }
            }
            builder.show()
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun cameraIntent() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        try {
            createImageFile(this, imageFileName, ".jpg")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", mImageFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, requestCamera)
    }

    private fun galleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, selectFile)
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context, name: String, extension: String) {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        mImageFile = File.createTempFile(
                name,
                extension,
                storageDir
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       // val result = CropImage.getActivityResult(data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                requestCamera -> {
                    val uri = Uri.fromFile(mImageFile)
                     picturePath = Helper.getAbsolutePath(this, uri)
                    Log.e("imagecamera   ",""+picturePath)
                    selectedImage(picturePath, "")
                    if (from1.equals("1"))
                    {
                      //  CropImage.activity(uri).setAspectRatio(70,70).start(this)
                    }
                    else{
                        //CropImage.activity(uri).setAspectRatio(100,70).start(this)
                    }
                }
                selectFile -> {
                    val uri = data?.data
                     picturePath = Helper.getAbsolutePath(this, uri!!)
                  selectedImage(picturePath, "")
                    if (from1.equals("1"))
                    {
                      //  CCropImage.activity(uri).setAspectRatio(70,70).start(this)
                    }
                    else{
                        //ropImage.activity(uri).setAspectRatio(100,70).start(this)
                    }
                }
              /*  CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val resultUri = result.uri
                    val picturePath = Helper.getAbsolutePath(this, resultUri)
                    selectedImage(picturePath, "")
                }*/
            }
        }
    }

    abstract fun selectedImage(imagepath: String?, thumbnailVideoPath: String)
}

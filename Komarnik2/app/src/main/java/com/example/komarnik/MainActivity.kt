package com.example.komarnik

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.komarnik.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        var preferences : SharedPreferences = getSharedPreferences("MYPREFS", AppCompatActivity.MODE_PRIVATE)


        //       val navController = findNavController(R.id.nav_host_fragment_content_main)
   //     appBarConfiguration = AppBarConfiguration(navController.graph)
     //   setupActionBarWithNavController(navController, appBarConfiguration)


    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun requestPermission(){
        val permissions = arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permissions, 0)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
   // override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
   //     menuInflater.inflate(R.menu.menu_main, menu)
   //     return true
   // }

    //override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
     //   return when (item.itemId) {
       //     R.id.action_settings -> true
         //   else -> super.onOptionsItemSelected(item)
        //}
    //}

 //   override fun onSupportNavigateUp(): Boolean {
   //     val navController = findNavController(R.id.nav_host_fragment_content_main)
     //   return navController.navigateUp(appBarConfiguration)
       //         || super.onSupportNavigateUp()
    //}
}
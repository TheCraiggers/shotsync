package com.shortsteplabs.shotsync.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.shortsteplabs.shotsync.R
import com.shortsteplabs.shotsync.camera.Discover
import com.shortsteplabs.shotsync.sync.SyncService.Companion.startSync

/**
 * Copyright (C) 2018  David Hilton <david.hilton.p@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

class MainActivity : AppCompatActivity() {
    val perms = Permissions(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onStart() {
        super.onStart()
        perms.firstRun()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SyncSettingsActivity::class.java))
            true
        }
        else ->super.onOptionsItemSelected(item)
    }

    fun startSync(view: View) {
        startSync(this)
    }

    fun addCamera(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please connect to your camera, then continue.")
        builder.setPositiveButton("Continue", fun(dialog: DialogInterface?, id: Int) {
            class discover: AsyncTask<Context, Void, String>() {
                override fun doInBackground(vararg params: Context?): String {
                    return Discover().discover(params[0]!!)
                }
            }
            discover().execute(this)
        })
        builder.setNegativeButton("Cancel", fun(_: DialogInterface?, _: Int) {})
        builder.create().show()
    }
}
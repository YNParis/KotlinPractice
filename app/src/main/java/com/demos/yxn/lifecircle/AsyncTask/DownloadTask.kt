package com.demos.yxn.lifecircle.AsyncTask

import android.os.AsyncTask
import java.net.URL

/**
 * Created by YXN on 2018/5/26.
 */
class DownloadTask() : AsyncTask<URL, Integer, Long>() {

    override fun doInBackground(vararg params: URL?): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

}
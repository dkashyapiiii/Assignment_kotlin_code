package com.diksha.employeedata.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.diksha.employeedata.ModelClass.EmployeeModel
import com.diksha.employeedata.ModelClass.Maindata
import com.diksha.employeedata.R
import com.diksha.employeedata.adapter.EmployeeAdapter
import com.diksha.employeedata.view.MainActivity
import com.diksha.employeedata.view_model.ArticleViewModel
import com.google.gson.Gson
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var my_recycler_view: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var layoutManager: LinearLayoutManager? = null
    private var employeeAdapter: EmployeeAdapter? = null
    private val articleArrayList = ArrayList<Maindata>()
    var articleViewModel: ArticleViewModel? = null
    private var jsonfileupload: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialization()
        if (isNetworkConnected) {
            getdata()
        } else {
            //  getroomdata();
            Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show()
        }
        jsonfileupload!!.setOnClickListener { showFileChooser() }
    }

    private fun initialization() {
        swipeRefreshLayout = findViewById<View>(R.id.pullToRefresh) as SwipeRefreshLayout
        my_recycler_view = findViewById<View>(R.id.recycler) as RecyclerView
        layoutManager = LinearLayoutManager(this@MainActivity)
        jsonfileupload = findViewById(R.id.jsonfile)
        my_recycler_view!!.layoutManager = layoutManager
        my_recycler_view!!.setHasFixedSize(true)
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
    }

    private fun getdata() {
        swipeRefreshLayout!!.isRefreshing = true
        articleViewModel!!.articleResponseLiveData.observe(
            this,
            { articleResponse: EmployeeModel? ->
                if (articleResponse != null) {
                    val articles = articleResponse.banner1
                    articles?.let { articleArrayList.addAll(it) }
                    employeeAdapter =
                        articleResponse.banner1?.let {
                            EmployeeAdapter(this@MainActivity,
                                it, "Live")
                        }
                    my_recycler_view!!.adapter = employeeAdapter
                    employeeAdapter!!.notifyDataSetChanged()
                    swipeRefreshLayout!!.isRefreshing = false
                }
            })
    }

    private val isNetworkConnected: Boolean
        private get() {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }

    private fun showFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRealPathFromURI(context: MainActivity, contentUri: Uri?): String {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_SELECT_CODE -> if (resultCode == RESULT_OK) {
                val uri = data!!.data
                val path = getRealPathFromURI(this@MainActivity, uri)
                val filename = path.substring(path.lastIndexOf("/") + 1)
                ReadFile(path)
                Toast.makeText(this, filename, Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun ReadFile(path: String?) {
        //    mSwipeRefreshLayout.setRefreshing(true);
        val gson = Gson()
        var text = ""
        try {
            val yourFile = File(path)
            val inputStream: InputStream = FileInputStream(yourFile)
            val stringBuilder = StringBuilder()
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append(receiveString)
                }
                inputStream.close()
                text = stringBuilder.toString()
                Parsetest(text)
                Log.d("TAG", text)
            }
        } catch (e: FileNotFoundException) {
            Log.e("file", e.message!!)
        } catch (e: IOException) {
            Log.e("file", e.message!!)
        }
    }

    private fun Parsetest(text: String) {

        val employeeModel = Gson().fromJson(text, EmployeeModel::class.java)
        employeeAdapter = employeeModel.banner1?.let { EmployeeAdapter(this, it, "Live") }
        my_recycler_view!!.adapter = employeeAdapter
        employeeAdapter!!.notifyDataSetChanged()

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val FILE_SELECT_CODE = 0
    }
}
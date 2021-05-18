package views

import adapter.AdapterMainList
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import base.BaseActivity
import constants.IntentKeys.SELECTED_ACCOUNT
import constants.SettingManager.Companion.DATABASE_NAME
import database.AppDatabase
import interfaces.OnObjectClickListener
import ir.doran_program.SecureWallet.R
import views.MainActivity
import java.io.*
import java.util.*

class MainActivity : BaseActivity() {
    private var recyclerMain: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        saveData()
        createMainRecycler()
    }

    private fun initView() {
        recyclerMain = findViewById(R.id.main_list_recycler)
    }

    //region initEvent
    fun main_list_more_click(view: View) {
        val moreList =
            ArrayList(Arrays.asList(getString(R.string.show), getString(R.string.delete)))
        popUpItemCreate(view, moreList, object : OnObjectClickListener {
            override fun onClick(`object`: Any?) {
                val selectedPopup = `object` as Int
                moreClicked(view, selectedPopup)
            }
        })
    }

    private fun moreClicked(view: View, selectedPopUp: Int) {
        val id = view.tag.toString().toInt()
        when (selectedPopUp) {
            0 -> showAccount(id)
            1 -> deleteAccount(id.toLong())
            else -> showAccount(id)
        }
    }

    fun define_onclick(view: View?) {
        val intent = Intent(this@MainActivity, RegisterAccountActivity::class.java)
        startActivityForResult(intent, REQ_ADD_ACCOUNT)
    }

    private fun createMainRecycler() {
        val accountDetailsList = AppDatabase.getInstance(this).accountDetailsDao().all
        val adapterAlarm = AdapterMainList(accountDetailsList)
        recyclerMain!!.layoutManager = GridLayoutManager(this, 1)
        recyclerMain!!.adapter = adapterAlarm
    }

    //endregion
    fun main_list_item_view_click(view: View) {
        val id = view.tag.toString().toInt()
        showAccount(id)
    }

    private fun showAccount(id: Int) {
        val intent = Intent(this, RegisterAccountActivity::class.java)
        intent.putExtra(SELECTED_ACCOUNT, id)
        startActivityForResult(intent, REQ_ADD_ACCOUNT)
    }

    private fun deleteAccount(id: Long) {
        val result = AppDatabase.getInstance(this).accountDetailsDao().deleteSelectedModel(id)
        if (result != 0) {
            Toast.makeText(this, getString(R.string.delete_successful), Toast.LENGTH_SHORT).show()
            createMainRecycler()
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData() {
        val dbFile = getDatabasePath(DATABASE_NAME)
        val sDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "SecureWalletDataBases"
        )
        val sfPath = sDir.path + File.separator + "SecureWalletDataBase" + "DBFile.db"
        if (!sDir.exists()) {
            sDir.mkdirs()
        }
        val saveFile = File(sfPath)
        try {
            saveFile.createNewFile()
            var bufferSize = 8 * 1024
            val buffer = ByteArray(bufferSize)
            val saveDb: OutputStream = FileOutputStream(sfPath)
            val indb: InputStream = FileInputStream(dbFile)
            while (indb.read(buffer, 0, bufferSize).also { bufferSize = it } > 0) {
                saveDb.write(buffer, 0, bufferSize)
            }
            saveDb.flush()
            indb.close()
            saveDb.close()
            Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) when (requestCode) {
            REQ_ADD_ACCOUNT -> createMainRecycler()
        }
    }

    companion object {
        private const val REQ_ADD_ACCOUNT = 626
    }
}
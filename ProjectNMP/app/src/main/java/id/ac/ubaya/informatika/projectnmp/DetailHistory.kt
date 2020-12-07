package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_history.*
import kotlinx.android.synthetic.main.history_layout.*
import org.json.JSONObject
import kotlin.math.tan

class DetailHistory : AppCompatActivity() {
    var histories:ArrayList<HistoryDetail> = ArrayList()
    var v: View?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history)

        setSupportActionBar(toolbar3)
        supportActionBar?.title = "NMP Online Shop"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        var iduser = intent.getIntExtra("idUser",0)
        var orderid = intent.getStringExtra("orderId")
        var tanggal = intent.getStringExtra("tanggal")
        var grandtotal = intent.getIntExtra("grandtotal", 0)

        txtTanggal.text = tanggal
        txtOrderID.text = orderid
        txtGrandtotal.text = "Grandtotal : Rp.$grandtotal"

        var q = Volley.newRequestQueue(this)
        val url = "http://ubaya.prototipe.net/nmp160418024/getHistoryUserDetail.php"
        var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
            {
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK")
                {
                    val data = obj.getJSONArray("data")

                    for(i in 0 until data.length())
                    {
                        with(data.getJSONObject(i)){
                            val history = HistoryDetail(getInt("iduser"),
                                getString("judul"),
                                getInt("jumlah"),
                                getInt("harga"),
                                getInt("grandTotal"),
                                getString("gambar"),
                                getString("orderId"),
                                getString("tanggalTransaksi"),
                                getInt("totalharga"))
                            histories.add(history)
                        }
                    }
                    updateList()
                    Log.d("detailhistory",histories.toString())
                }
            },
            {
                Log.d("detailhistory",it.message.toString())
            }
        ){
            override  fun  getParams(): MutableMap<String,String>{
                var params = HashMap<String,String>()
                params.put("iduser",iduser.toString())
                params.put("orderId",orderid.toString())
                return  params
            }
        }
        q.add(stringRequest)

    }
    fun updateList()
    {
        val lm: LinearLayoutManager = LinearLayoutManager(this)
        var recylerView = findViewById<RecyclerView>(R.id.detailHistoryView)
        recylerView?.layoutManager = lm
        recylerView?.setHasFixedSize(true)
        recylerView?.adapter = HistoryDetailAdapter(histories, this)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
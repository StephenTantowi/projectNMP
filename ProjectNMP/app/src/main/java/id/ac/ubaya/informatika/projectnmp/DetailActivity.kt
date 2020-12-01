package id.ac.ubaya.informatika.projectnmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.txtNamaP

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var idproduk = intent.getIntExtra("idproduct",0)
        txtNamaP.text = intent.getStringExtra("nama").toString()
        txtDetail.text = intent.getStringExtra("deskripsi").toString()
        txtHarga.text = intent.getIntExtra("harga",0).toString()
        txtKategori.text = intent.getStringExtra("kategori").toString()
        val url = intent.getStringExtra("gambar").toString()
        Picasso.get().load(url).into(imageView)
        var iduser = Global.users[0].id

        btnAdd.setOnClickListener {
            var q = Volley.newRequestQueue(this)
            val url = "http://ubaya.prototipe.net/nmp160418024/addKeranjangSementara.php"
            var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                {
                    Log.d("insert",it)
                },
                {
                    Log.d("insert",it.message.toString())
                }
            ){
                override  fun  getParams(): MutableMap<String,String>{
                    var params = HashMap<String,String>()
                    params.put("iduser",iduser.toString())
                    params.put("idproduct",idproduk.toString())
                    params.put("jumlah",1.toString())
                    return  params
                }
            }
            q.add(stringRequest)
        }
    }
}
package id.ac.ubaya.informatika.projectnmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.product_layout.view.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        txtNama.text = intent.getStringExtra("nama").toString()
        txtDetail.text = intent.getStringExtra("deskripsi").toString()
        txtHarga.text = intent.getIntExtra("harga",0).toString()
        txtKategori.text = intent.getStringExtra("kategori").toString()
        val url = intent.getStringExtra("gambar").toString()
        Picasso.get().load(url).into(imageView)
    }
}
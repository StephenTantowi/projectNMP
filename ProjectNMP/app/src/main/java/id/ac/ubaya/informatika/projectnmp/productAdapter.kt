package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_layout.view.*

class productAdapter(val products:ArrayList<Product>,val ctx:Context): RecyclerView.Adapter<productAdapter.ProductViewHolder>() {
    class ProductViewHolder(val v:View):RecyclerView.ViewHolder(v)
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ProductViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.product_layout,parent,false)
        return  ProductViewHolder(v)
    }

    override fun onBindViewHolder(holder: productAdapter.ProductViewHolder, position: Int) {
        val url = products[position].gambar
        Picasso.get().load(url).into(holder.v.imgProduct)
        holder.v.txtNamaP.text = products[position].nama
        holder.v.txtHarga.text = "Rp."+products[position].harga.toString()
        holder.v.txtInform.text = products[position].deskripsi

        holder.v.imgProduct.setOnClickListener {
            val intent = Intent(ctx,DetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("idproduct",products[position].id)
            intent.putExtra("nama",products[position].nama)
            intent.putExtra("harga",products[position].harga)
            intent.putExtra("deskripsi",products[position].deskripsi)
            intent.putExtra("gambar",products[position].gambar)
            intent.putExtra("kategori",products[position].kategori)
            ctx.startActivity(intent)
        }
        holder.v.btnDetailProduk.setOnClickListener {
            val intent = Intent(ctx,DetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("idproduct",products[position].id)
            intent.putExtra("nama",products[position].nama)
            intent.putExtra("harga",products[position].harga)
            intent.putExtra("deskripsi",products[position].deskripsi)
            intent.putExtra("gambar",products[position].gambar)
            intent.putExtra("kategori",products[position].kategori)
            ctx.startActivity(intent)
        }
        var iduser = Global.users[0].id
        var idproduk = products[position].id
        holder.v.btnAddCart.setOnClickListener {
            var q = Volley.newRequestQueue(ctx)
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

    override fun getItemCount(): Int {
        return products.size
    }
}
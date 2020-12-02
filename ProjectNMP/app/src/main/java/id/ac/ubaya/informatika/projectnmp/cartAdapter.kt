package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*


class cartAdapter(val carts:ArrayList<cart>,val ctx: Context): RecyclerView.Adapter<cartAdapter.CartViewHolder>() {
    class CartViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.cart_layout, parent, false)
        return CartViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val url = carts[position].gambar
        Picasso.get().load(url).into(holder.v.imgProduct)
        holder.v.txtNamaP.text = carts[position].nama
        holder.v.txtHarga.text = carts[position].harga.toString()
        holder.v.txtQuanatity.text = carts[position].jumlah.toString()

        holder.v.btnMinus.setOnClickListener {
            var q = Volley.newRequestQueue(ctx)
            val url = "http://ubaya.prototipe.net/nmp160418024/updateKeranjangSementara.php"
            var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                {
                    Log.d("update",it)
                    carts[position].jumlah--
                    holder.v.txtQuanatity.text = carts[position].jumlah.toString()
                },
                {
                    Log.d("update",it.message.toString())
                }
            ){
                override  fun  getParams(): MutableMap<String,String>{
                    var params = HashMap<String,String>()
                    params.put("iduser",Global.users[0].id.toString())
                    params.put("idproduct",carts[position].idproduct.toString())
                    params.put("aktivitas","Kurang")
                    return  params
                }
            }
            q.add(stringRequest)
        }
        holder.v.btnPlus.setOnClickListener {
            var q = Volley.newRequestQueue(ctx)
            val url = "http://ubaya.prototipe.net/nmp160418024/updateKeranjangSementara.php"
            var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                {
                    Log.d("update",it)
                    carts[position].jumlah++
                    holder.v.txtQuanatity.text = carts[position].jumlah.toString()
                },
                {
                    Log.d("update",it.message.toString())
                }
            ){
                override  fun  getParams(): MutableMap<String,String>{
                    var params = HashMap<String,String>()
                    params.put("iduser",Global.users[0].id.toString())
                    params.put("idproduct",carts[position].idproduct.toString())
                    params.put("aktivitas","Tambah")
                    return  params
                }
            }
            q.add(stringRequest)
        }

    }

    override fun getItemCount(): Int {
        return  carts.size
    }
}
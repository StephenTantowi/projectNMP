package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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
        holder.v.txtNama.text = products[position].nama
        holder.v.txtHarga.text = products[position].harga.toString()

        holder.v.imgProduct.setOnClickListener {
            val intent = Intent(ctx,DetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("nama",products[position].nama)
            intent.putExtra("harga",products[position].harga)
            intent.putExtra("deskripsi",products[position].deskripsi)
            intent.putExtra("gambar",products[position].gambar)
            intent.putExtra("kategori",products[position].kategori)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
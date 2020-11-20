package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
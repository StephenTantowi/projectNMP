package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_layout.view.*



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

    }

    override fun getItemCount(): Int {
        return  carts.size
    }
}
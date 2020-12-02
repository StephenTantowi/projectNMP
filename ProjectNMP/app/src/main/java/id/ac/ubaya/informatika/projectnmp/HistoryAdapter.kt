package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.cart_layout.view.imgProduct
import kotlinx.android.synthetic.main.cart_layout.view.txtHarga
import kotlinx.android.synthetic.main.cart_layout.view.txtNamaP
import kotlinx.android.synthetic.main.history_layout.view.*


class HistoryAdapter(val histories:ArrayList<History>,val ctx: Context): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.history_layout, parent, false)
        return HistoryAdapter.HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val url = histories[position].gambar
        Picasso.get().load(url).into(holder.v.imgProduct)
        holder.v.txtNamaP.text = histories[position].nama
        holder.v.txtHarga.text = histories[position].harga.toString()
        holder.v.txtJumlah.text = histories[position].jumlah.toString()
        holder.v.txtTotalHarga.text = histories[position].totalHarga.toString();
    }
}
package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.history_layout.view.*

class HistoryDetailAdapter(val historiedetails:ArrayList<HistoryDetail>,val ctx: Context): RecyclerView.Adapter<HistoryDetailAdapter.HistoryDetailViewHolder>() {
    class HistoryDetailViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.history_layout, parent, false)
        return HistoryDetailViewHolder(v)
    }

    override fun getItemCount(): Int {
        return historiedetails.size
    }

    override fun onBindViewHolder(holder: HistoryDetailViewHolder, position: Int) {
        val url = historiedetails[position].gambar
        Picasso.get().load(url).into(holder.v.imgProduct)
        holder.v.txtNamaP.text = historiedetails[position].namaproduk
        holder.v.txtHarga.text = historiedetails[position].harga.toString()
        holder.v.txtJumlah.text = historiedetails[position].jumlah.toString()
        holder.v.txtTotalHarga.text = historiedetails[position].totalharga.toString()
    }
}
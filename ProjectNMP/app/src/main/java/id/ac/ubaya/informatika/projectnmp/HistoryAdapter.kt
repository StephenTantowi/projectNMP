package id.ac.ubaya.informatika.projectnmp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.history_all_layout.view.*


class HistoryAdapter(val histories:ArrayList<History>,val ctx: Context): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.history_all_layout, parent, false)
        return HistoryAdapter.HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.v.txtOrderID.text = histories[position].orderid
        holder.v.txtTanggalTransaksi.text = histories[position].tanggal
        holder.v.txtGT.text = histories[position].grandtotal.toString()

        holder.v.btnDetail.setOnClickListener {
            val intent = Intent(ctx,DetailHistory::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("orderId",histories[position].orderid)
            intent.putExtra("idUser",histories[position].iduser)
            intent.putExtra("tanggal",histories[position].tanggal)
            intent.putExtra("grandtotal",histories[position].grandtotal)
            ctx.startActivity(intent)
        }
    }
}
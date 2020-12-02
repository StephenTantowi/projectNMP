package id.ac.ubaya.informatika.projectnmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderHistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var histories:ArrayList<History> = ArrayList()
    var v:View ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val q = Volley.newRequestQueue(activity)
        val url = "http://ubaya.prototipe.net/nmp160418024/getHistory.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK")
                {
                    val data = obj.getJSONArray("data")

                    for(i in 0 until data.length())
                    {
                        with(data.getJSONObject(i)){
                            val history = History(getInt("iduser"),
                                getInt("idproduct"),
                                getInt("jumlah"),
                                getInt("harga"),
                                getString("gambar"),
                                getString("judul"),
                                getInt("totalharga"))
                            histories.add(history)
                        }
                    }
                    updateList()
                    Log.d("history",histories.toString())
                }
            },
            {
                Log.d("history", it.message.toString())
            }
        ){
            override  fun  getParams(): MutableMap<String,String>{
                var params = HashMap<String,String>()
                params.put("iduser",Global.users[0].id.toString())
                return  params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_history, container, false)
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun updateList(){
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recylerView = v?.findViewById<RecyclerView>(R.id.historyView)
        recylerView?.layoutManager = lm
        recylerView?.setHasFixedSize(true)
        recylerView?.adapter = HistoryAdapter(histories, activity!!.applicationContext)
    }

}
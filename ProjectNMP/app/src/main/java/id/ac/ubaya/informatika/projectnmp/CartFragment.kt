package id.ac.ubaya.informatika.projectnmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var carts:ArrayList<cart> = ArrayList()
    var v:View ?= null
    var grandTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        carts.clear()
        updateList()
        val q = Volley.newRequestQueue(activity)
        val url = "http://ubaya.prototipe.net/nmp160418024/getKeranjangSementara.php"
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
                            val product = cart(getInt("iduser"),
                                getInt("idproduct"),
                                getInt("jumlah"),
                                getInt("harga"),
                                getString("gambar"),
                                getString("judul"))
                            carts.add(product)
                            grandTotal = getInt("GrandTotal")
                        }
                    }
                    var gt = v?.findViewById<TextView>(R.id.txtGrandtotal)
                    gt?.text = "Grandtotal : " + grandTotal.toString()
                    updateList()
                    Log.d("cart",carts.toString())
                    Log.d("cart",grandTotal.toString())
                }
            },
            {
                Log.d("cart", it.message.toString())
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
        v = inflater.inflate(R.layout.fragment_cart, container, false)
        var button = v?.findViewById<Button>(R.id.btnCheckout)
        var gt = v?.findViewById<TextView>(R.id.txtGrandtotal)

        button?.setOnClickListener {
            var q = Volley.newRequestQueue(context)
            val url = "http://ubaya.prototipe.net/nmp160418024/addHistory.php"
            var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                {
                    Log.d("update",it)
                    onResume()
                    gt?.text = "Grand Total : 0"
                },
                {
                    Log.d("update",it.message.toString())
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
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun updateList(){
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        var recylerView = v?.findViewById<RecyclerView>(R.id.cartView)
        recylerView?.layoutManager = lm
        recylerView?.setHasFixedSize(true)
        recylerView?.adapter = cartAdapter(carts, activity!!.applicationContext)
    }
}

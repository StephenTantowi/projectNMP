package id.ac.ubaya.informatika.projectnmp

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.cart_layout.*
import kotlinx.android.synthetic.main.fragment_cart.*
import org.json.JSONObject
import kotlin.math.log

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
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        with(data.getJSONObject(i)) {
                            val product = cart(
                                getInt("iduser"),
                                getInt("idproduct"),
                                getInt("jumlah"),
                                getInt("harga"),
                                getString("gambar"),
                                getString("judul")
                            )
                            carts.add(product)
                            grandTotal = getInt("GrandTotal")
                        }
                    }
                    val timer = object : CountDownTimer(1000, 1000) {
                        override fun onTick(time: Long) {
                            val q = Volley.newRequestQueue(activity)
                            val url =
                                "http://ubaya.prototipe.net/nmp160418024/getKeranjangSementara.php"
                            var stringRequest = object : StringRequest(
                                Request.Method.POST,
                                url,
                                {
                                    val obj = JSONObject(it)
                                    if (obj.getString("result") == "OK") {
                                        val data = obj.getInt("GrandTotal")

                                        var gt = v?.findViewById<TextView>(R.id.txtGrandtotal)
                                        gt?.text = "Grandtotal : " + data.toString()
                                    }
                                    else if(obj.getString("result") == "UpdateList")
                                    {
                                        carts.clear()
                                        updateList()
                                    }
                                    else
                                    {
                                        val data = obj.getInt("GrandTotal")

                                        var gt = v?.findViewById<TextView>(R.id.txtGrandtotal)
                                        gt?.text = "Grandtotal : " + data.toString()
                                        carts.clear()
                                        updateList()
                                    }
                                },
                                {
                                    Log.d("cart", it.message.toString())
                                }
                            ) {
                                override fun getParams(): MutableMap<String, String> {
                                    var params = HashMap<String, String>()
                                    params.put("iduser", Global.users[0].id.toString())
                                    return params
                                }
                            }
                            q.add(stringRequest)
                        }

                        override fun onFinish() {
                            start()
                        }
                    }
                    timer.start()
                    updateList()
                    Log.d("cart", carts.toString())
                    Log.d("cart", grandTotal.toString())
                }
            },
            {
                Log.d("cart", it.message.toString())
            }
        ){
            override  fun  getParams(): MutableMap<String, String>{
                var params = HashMap<String, String>()
                params.put("iduser", Global.users[0].id.toString())
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
                    var obj = JSONObject(it)
                    if(obj.getString("result") == "OK")
                    {

                        onResume()
                        gt?.text = "Grand Total : 0"

                        val alert = AlertDialog.Builder(context)
                        val inflater = layoutInflater
                        val dialog:View = inflater.inflate(R.layout.custom_dialog,null)
                        alert.setView(dialog)
                        alert.show()
                    }
                    else
                    {
                        val data = obj.getString("message")
                        Snackbar.make(consfcart, data, Snackbar.LENGTH_LONG).show()
                    }
                    Log.d("update", it)
                },
                {
                    Log.d("update", it.message.toString())
                }
            ){
                override  fun  getParams(): MutableMap<String, String>{
                    var params = HashMap<String, String>()
                    params.put("iduser", Global.users[0].id.toString())
                    if(carts.size != 0)
                    {
                        params.put("idp", carts[0].idproduct.toString())
                    }

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

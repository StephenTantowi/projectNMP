package id.ac.ubaya.informatika.projectnmp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.txtEmail
import kotlinx.android.synthetic.main.fragment_profile.txtOldPassword
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var v: View ?= null

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment s
        v = inflater.inflate(R.layout.fragment_profile, container, false)
        var nama = v?.findViewById<TextInputEditText>(R.id.txtNama)
        var email = v?.findViewById<TextInputEditText>(R.id.txtEmail)
        var oldPass = v?.findViewById<TextInputEditText>(R.id.txtOldPassword)
        var newPass = v?.findViewById<TextInputEditText>(R.id.txtNewPass)
        var repeatPass = v?.findViewById<TextInputEditText>(R.id.txtRepeatNewPass)
        var button = v?.findViewById<Button>(R.id.btnUpdate)

        nama?.setText(Global.users[0].nama)
        email?.setText(Global.users[0].email)
        button?.setOnClickListener {
//            if(newPass == repeatPass)
//            {
                var q = Volley.newRequestQueue(context)
                val url = "http://ubaya.prototipe.net/nmp160418024/updateUser.php"
                var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                    {
                        Log.d("updateuser",it)
                        var obj = JSONObject(it)
                        if(obj.getString("result") == "OK")
                        {
                            val data = obj.getString("message")
                            Snackbar.make(view!!.rootView, data, Snackbar.LENGTH_LONG).show()
                        }
                    },
                    {
                        Log.d("updateuser",it.message.toString())
                    }
                ){
                    override  fun  getParams(): MutableMap<String,String>{
                        var params = HashMap<String,String>()
                        params.put("iduser", Global.users[0].id.toString())
                        params.put("nama", nama?.text.toString())
                        params.put("oldPassword", oldPass?.text.toString())
                        params.put("newPassword", newPass?.text.toString())
                        return  params
                    }
                }
                q.add(stringRequest)
            }
        //}
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
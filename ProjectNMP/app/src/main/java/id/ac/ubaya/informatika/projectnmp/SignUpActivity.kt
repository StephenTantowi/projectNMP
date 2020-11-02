package id.ac.ubaya.informatika.projectnmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        btnSignup.setOnClickListener {
            var q = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2/nmp/adduser.php"
            var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                {
                    Log.d("insert",it)
                },
                {
                    Log.d("insert",it.message.toString())
                }
            ){
                override  fun  getParams(): MutableMap<String,String>{
                    var params = HashMap<String,String>()
                    params.put("email",txtEmail.text.toString())
                    params.put("password",txtPassword.text.toString())
                    params.put("nama",txtNama.text.toString())
                    return  params
                }
            }
            q.add(stringRequest)
            finish()

        }
    }
}
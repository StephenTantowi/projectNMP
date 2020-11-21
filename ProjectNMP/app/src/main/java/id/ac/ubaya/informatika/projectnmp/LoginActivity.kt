package id.ac.ubaya.informatika.projectnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txtEmail
import kotlinx.android.synthetic.main.activity_login.txtOldPassword
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            var q = Volley.newRequestQueue(this)
            val url = "http://ubaya.prototipe.net/nmp160418024/getUser.php"
            var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                {
                    Log.d("login",it)
                    var obj = JSONObject(it)
                    if(obj.getString("result") == "OK")
                    {
                        val data = obj.getJSONArray("data")
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                },
                {
                    Log.d("login",it.message.toString())
                }
            ){
                override  fun  getParams(): MutableMap<String,String>{
                    var params = HashMap<String,String>()
                    params.put("email",txtEmail.text.toString())
                    params.put("password",txtOldPassword.text.toString())
                    return  params
                }
            }
            q.add(stringRequest)
        }
    }
}
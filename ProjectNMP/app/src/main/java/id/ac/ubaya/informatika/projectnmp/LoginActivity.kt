package id.ac.ubaya.informatika.projectnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import id.ac.ubaya.informatika.projectnmp.Global.users
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
                    Log.d("login", it)
                    var obj = JSONObject(it)
                    if (obj.getString("result") == "OK") {
                        val data = obj.getJSONArray("data")
                        val user = data.getJSONObject(0)
                        val users = User(
                            user.getInt("iduser"), user.getString("nama"),
                            user.getString("email"), user.getString("password")
                        )
                        Global.users.add(users)
                        Log.d("tesuser", Global.users.toString())

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        val data = obj.getString("message")
                        Snackbar.make(cons, data, Snackbar.LENGTH_LONG).show()
                    }
                },
                {
                    Log.d("login", it.message.toString())
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

        btnSignup.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
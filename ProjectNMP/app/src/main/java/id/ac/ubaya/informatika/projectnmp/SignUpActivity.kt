package id.ac.ubaya.informatika.projectnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btnSignup
import kotlinx.android.synthetic.main.activity_sign_up.txtEmail
import kotlinx.android.synthetic.main.activity_sign_up.txtOldPassword
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        btnSignup.setOnClickListener {
                var q = Volley.newRequestQueue(this)
                val url = "http://ubaya.prototipe.net/nmp160418024/addUser.php"
                var stringRequest = object: StringRequest(com.android.volley.Request.Method.POST, url,
                    {
                        Log.d("insert",it)
                        var obj = JSONObject(it)
                        if(obj.getString("result") == "OK")
                        {
                            var q2 = Volley.newRequestQueue(this)
                            val url2 = "http://ubaya.prototipe.net/nmp160418024/getUser.php"
                            var stringRequest2 = object: StringRequest(com.android.volley.Request.Method.POST, url2,
                                {
                                    Log.d("login",it)
                                    var obj = JSONObject(it)
                                    if(obj.getString("result") == "OK")
                                    {
                                        val data = obj.getJSONArray("data")
                                        val user = data.getJSONObject(0)
                                        val users = User(user.getInt("iduser"), user.getString("nama"),
                                            user.getString("email"), user.getString("password"))
                                        Global.users.add(users)
                                        Log.d("tesuser", Global.users.toString())

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
                            q2.add(stringRequest2)
                        }
                        else
                        {
                            val data = obj.getString("message")
                            Snackbar.make(consSignUp, data, Snackbar.LENGTH_LONG).show()
                        }
                    },
                    {
                        Log.d("insert",it.message.toString())
                    }
                ){
                    override  fun  getParams(): MutableMap<String,String>{
                        var params = HashMap<String,String>()
                        params.put("email",txtEmail.text.toString())
                        params.put("password",txtOldPassword.text.toString())
                        params.put("ulangipassword",txtNewPass.text.toString())
                        params.put("nama",txtNamaP.text.toString())
                        return  params
                    }
                }
                q.add(stringRequest)

        }
    }
}
package id.ac.ubaya.informatika.projectnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        btnSignup.setOnClickListener {
            if(txtOldPassword.text.toString() == txtNewPass.text.toString())
            {
                var q = Volley.newRequestQueue(this)
                val url = "http://ubaya.prototipe.net/nmp160418024/addUser.php"
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
                        params.put("password",txtOldPassword.text.toString())
                        params.put("nama",txtNamaP.text.toString())
                        return  params
                    }
                }
                q.add(stringRequest)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
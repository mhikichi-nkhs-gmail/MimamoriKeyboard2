package com.kazumaproject.markdownhelperkeyboard.setting_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.kazumaproject.markdownhelperkeyboard.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**?.toString()?.trim() を使うことで null 安全なコードに。
        ?: "" により null の場合は空文字を代入。**/
        val loginEditUserName : EditText = findViewById(R.id.loginEditUserName)
        val userName = findViewById<EditText>(R.id.loginEditUserName).text?.toString()?.trim() ?: ""

        val loginEditPassword : EditText = findViewById(R.id.loginEditPassword)
        val password = findViewById<EditText>(R.id.loginEditPassword).text?.toString()?.trim() ?: ""

        val loginReturnButton : Button = findViewById(R.id.loginReturnButton)
        loginReturnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val loginNewUser : Button = findViewById(R.id.loginNewUser)
        loginNewUser.setOnClickListener {
            val intent = Intent(this, NewActivity1::class.java)
            startActivity(intent)
        }

        val loginButton : Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            if (userName.isNotBlank() && password.isNotBlank()) {
                val intent = Intent(this, AfterLoginSelection::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "ユーザー名とパスワードを入力してください", Toast.LENGTH_SHORT).show()
            }
        }

        val loginForgetPassword : Button = findViewById(R.id.loginForgetPassword)
        loginForgetPassword.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
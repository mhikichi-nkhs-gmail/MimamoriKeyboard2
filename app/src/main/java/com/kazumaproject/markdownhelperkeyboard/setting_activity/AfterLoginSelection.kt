package com.kazumaproject.markdownhelperkeyboard.setting_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kazumaproject.markdownhelperkeyboard.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AfterLoginSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.after_login_selection)

        val selectionEditWordButton : Button = findViewById(R.id.selectionEditWordButton)
        selectionEditWordButton.setOnClickListener {
            val intent = Intent(this, DatabaseActivity::class.java)
            startActivity(intent)
        }


        val selectionEditDetectionButton : Button = findViewById(R.id.selectionEditDetectionButton)
        selectionEditDetectionButton.setOnClickListener {
            val intent = Intent(this, EditDetectionActivity::class.java)
            startActivity(intent)
        }


        val selectionResetButton : Button = findViewById(R.id.selectionResetButton)
        selectionResetButton.setOnClickListener {
            val intent = Intent(this, ResetActivity1::class.java)
            startActivity(intent)
        }


        val logoutButton : Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
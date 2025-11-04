package com.ifpr.androidapptemplate.ui.usuario

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ifpr.androidapptemplate.R

class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var nomeEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var confirmarSenhaEditText: EditText
    private lateinit var salvarButton: Button
    private lateinit var sairButton: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        auth = FirebaseAuth.getInstance()

        nomeEditText = findViewById(R.id.registerNameEditText)
        emailEditText = findViewById(R.id.registerEmailEditText)
        senhaEditText = findViewById(R.id.registerPasswordEditText)
        confirmarSenhaEditText = findViewById(R.id.registerConfirmPasswordEditText)
        salvarButton = findViewById(R.id.salvarButton)
        sairButton = findViewById(R.id.sairButton)

        salvarButton.setOnClickListener {
            val nome = nomeEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val senha = senhaEditText.text.toString().trim()
            val confirmar = confirmarSenhaEditText.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (senha != confirmar) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        sairButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

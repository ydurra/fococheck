package com.example.fococheck

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {

            private lateinit var txtTitulo: TextView
            private lateinit var txtRecomendacao: TextView
            private lateinit var btnCompartilhar: Button
            private lateinit var btnAbrirDicas: Button
            private lateinit var btnEnviarEmail: Button

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_result)

                txtTitulo = findViewById(R.id.txtTituloResultado)
                txtRecomendacao = findViewById(R.id.txtRecomendacao)
                btnCompartilhar = findViewById(R.id.btnCompartilhar)
                btnAbrirDicas = findViewById(R.id.btnAbrirDicas)
                btnEnviarEmail = findViewById(R.id.btnEnviarEmail)

                val titulo = intent.getStringExtra("tituloResultado") ?: ""
                val recomendacao = intent.getStringExtra("textoRecomendacao") ?: ""

                txtTitulo.text = titulo
                txtRecomendacao.text = recomendacao

                val textoCompleto = "Meu perfil no FocusCheck: $titulo\n\nRecomendações: $recomendacao"

                // Intent implícita – compartilhar
                btnCompartilhar.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, textoCompleto)
                    }
                    startActivity(Intent.createChooser(shareIntent, "Compartilhar resultado"))
                }

                // Intent implícita – abrir página da web
                btnAbrirDicas.setOnClickListener {
                    val uri = Uri.parse("https://www.alura.com.br/artigos/como-estudar-melhor")
                    val webIntent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(webIntent)
                }

                // Intent implícita – enviar e-mail
                btnEnviarEmail.setOnClickListener {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_SUBJECT, "Feedback FocusCheck")
                        putExtra(Intent.EXTRA_TEXT, "Meu resultado foi: $titulo\n$recomendacao")
                    }

                    val btnRecomecar: Button = findViewById(R.id.btnRecomecar)

                    btnRecomecar.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }

                    startActivity(emailIntent)
                }
            }
        }


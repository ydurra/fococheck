package com.example.fococheck

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.focuscheck.UserAnswers

class PersonalDataActivity : AppCompatActivity() {

        private lateinit var edtNome: EditText
        private lateinit var spPeriodo: Spinner
        private lateinit var swEstudaTodoDia: Switch
        private lateinit var btnProxima: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_personal_data)

            edtNome = findViewById(R.id.edtNome)
            spPeriodo = findViewById(R.id.spPeriodo)
            swEstudaTodoDia = findViewById(R.id.swEstudaTodoDia)
            btnProxima = findViewById(R.id.btnProxima)

            // Config do Spinner
            val periodos = arrayOf("Manhã", "Tarde", "Noite")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, periodos)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spPeriodo.adapter = adapter

            btnProxima.setOnClickListener {
                val nome = edtNome.text.toString().trim()

                if (nome.isEmpty()) {
                    edtNome.error = "Informe seu nome"
                    return@setOnClickListener
                }

                val periodo = spPeriodo.selectedItem.toString()
                val estudaTodoDia = swEstudaTodoDia.isChecked

                val answers = UserAnswers(
                    nome = nome,
                    periodoEstudo = periodo,
                    estudaTodoDia = estudaTodoDia
                )

                val intent = Intent(this, QuestionsActivity::class.java)
                intent.putExtra("answers", answers)
                startActivity(intent)
            }
        }

        }


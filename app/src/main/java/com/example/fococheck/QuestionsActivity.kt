package com.example.fococheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.focuscheck.UserAnswers

class QuestionsActivity : AppCompatActivity() {

    private lateinit var btnProxima: Button
    private lateinit var answers: UserAnswers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)


        answers = intent.getSerializableExtra("answers") as UserAnswers


        btnProxima = findViewById(R.id.btnProximaPerguntas)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, QuestionsFragment())
                .commit()
        }


        btnProxima.setOnClickListener {
            val fragment = supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView) as? QuestionsFragment

            if (fragment != null) {
                val updatedAnswers = fragment.coletarRespostas(answers)

                val intent = Intent(this, ReviewActivity::class.java)
                intent.putExtra("answers", updatedAnswers)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Não consegui ler as respostas do fragmento.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

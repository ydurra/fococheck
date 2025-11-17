package com.example.fococheck

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.focuscheck.UserAnswers

class ReviewActivity : AppCompatActivity() {

    private lateinit var txtResumo: TextView
    private lateinit var btnCalcular: Button
    private lateinit var answers: UserAnswers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // GARANTE que está usando o layout certo
        setContentView(R.layout.activity_review)

        // Pega o objeto vindo da QuestionsActivity (CHAT GPT 5)
        answers = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("answers", UserAnswers::class.java)
                ?: UserAnswers()
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("answers") as? UserAnswers
                ?: UserAnswers()
        }

        // Liga os componentes do layout
        txtResumo = findViewById(R.id.txtResumo)
        btnCalcular = findViewById(R.id.btnCalcular)

        // Monta o texto de resumo
        val resumo = """
            Nome: ${answers.nome}
            Período de estudo: ${answers.periodoEstudo}
            Estuda todo dia: ${if (answers.estudaTodoDia) "Sim" else "Não"}
            Horas por dia (pontuação): ${answers.horasPorDia}
            Distrações: ${if (answers.distracoes.isEmpty()) "Nenhuma" else answers.distracoes.joinToString(", ")}
            Nível de foco: ${answers.nivelFoco}
        """.trimIndent()

        txtResumo.text = resumo

        // Clique para calcular resultado e ir para ResultActivity
        btnCalcular.setOnClickListener {
            val (titulo, recomendacao) = calcularResultado(answers)

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("tituloResultado", titulo)
                putExtra("textoRecomendacao", recomendacao)
            }
            startActivity(intent)
        }
    }

    private fun calcularResultado(ans: UserAnswers): Pair<String, String> {
        var score = 0

        score += ans.horasPorDia
        score += ans.nivelFoco * 2
        if (!ans.estudaTodoDia) score -= 2
        score -= ans.distracoes.size

        return when {
            score >= 8 -> "Produtivo nível PRO" to
                    "Seu nível de organização e foco é excelente. Mantenha a rotina e só ajuste detalhes finos."
            score in 4..7 -> "Produtivo Instável" to
                    "Você tem bons hábitos, mas falta consistência. Defina horários fixos, reduza distrações e revise seu planejamento semanal."
            else -> "Caótico, mas com potencial" to
                    "Hora de organizar o caos: comece com metas pequenas, horários definidos e elimine suas duas principais distrações."
        }
    }
}

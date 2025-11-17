package com.example.fococheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.focuscheck.UserAnswers

class QuestionsFragment : Fragment() {

    private lateinit var rgHoras: RadioGroup
    private lateinit var cbCelular: CheckBox
    private lateinit var cbRedes: CheckBox
    private lateinit var cbTv: CheckBox
    private lateinit var cbBarulho: CheckBox
    private lateinit var rgFoco: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questions, container, false)

        rgHoras = view.findViewById(R.id.rgHorasEstudo)
        cbCelular = view.findViewById(R.id.cbCelular)
        cbRedes = view.findViewById(R.id.cbRedes)
        cbTv = view.findViewById(R.id.cbTv)
        cbBarulho = view.findViewById(R.id.cbBarulho)
        rgFoco = view.findViewById(R.id.rgNivelFoco)

        return view
    }

    fun coletarRespostas(answers: UserAnswers): UserAnswers {
        val horasSelecionadasId = rgHoras.checkedRadioButtonId
        val horas = when (horasSelecionadasId) {
            R.id.rbMenosDe1 -> 0
            R.id.rb1a3 -> 2
            R.id.rb3a5 -> 4
            R.id.rbMaisDe5 -> 6
            else -> 0
        }

        val distracoes = mutableListOf<String>()
        if (cbCelular.isChecked) distracoes.add("Celular")
        if (cbRedes.isChecked) distracoes.add("Redes sociais")
        if (cbTv.isChecked) distracoes.add("TV")
        if (cbBarulho.isChecked) distracoes.add("Barulho")

        val focoId = rgFoco.checkedRadioButtonId
        val nivelFoco = when (focoId) {
            R.id.rbFocoBaixo -> 1
            R.id.rbFocoMedio -> 2
            R.id.rbFocoAlto -> 3
            else -> 1
        }

        return answers.copy(
            horasPorDia = horas,
            distracoes = distracoes,
            nivelFoco = nivelFoco
        )
    }
}

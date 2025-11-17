package com.example.focuscheck
import java.io.Serializable
data class UserAnswers( var nome: String = "", var periodoEstudo: String = "", var estudaTodoDia: Boolean = false, var horasPorDia: Int = 0, var distracoes: List<String> = emptyList(), var nivelFoco: Int = 0 ) : Serializable
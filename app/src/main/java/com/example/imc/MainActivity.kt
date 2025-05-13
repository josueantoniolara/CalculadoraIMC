package com.example.imc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var layoutInputs: LinearLayout
    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var tvResult: TextView
    private lateinit var rbMetric: RadioButton
    private lateinit var rbImperial: RadioButton
    private lateinit var btnCalculate: Button
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias
        layoutInputs = findViewById(R.id.layoutInputs)
        this.etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        tvResult = findViewById(R.id.tvResult)
        rbMetric = findViewById(R.id.rbMetric)
        rbImperial = findViewById(R.id.rbImperial)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnStart = findViewById(/* id = */ R.id.btnStart)

        // Mostrar formulario al hacer clic en "Iniciar"
        btnStart.setOnClickListener {
            LinearLayout.VISIBLE.also { layoutInputs.visibility = it }
        }

        btnCalculate.setOnClickListener {
            calcularIMC()
        }
    }

    private fun calcularIMC() {
        val peso = etWeight.text.toString().toDoubleOrNull()
        val altura = etHeight.text.toString().toDoubleOrNull()

        if ((peso == null) || (altura == null) || (peso <= 0) || (altura <= 0)) {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
            return
        }

        val imc = if (rbMetric.isChecked) {
            peso / (altura * altura)
        } else {
            val pesoKg = peso * 0.453592
            val alturaM = altura * 0.0254
            pesoKg / (alturaM * alturaM)
        }

        val categoria = when {
            imc < 18.5 -> "Bajo peso"
            imc < 24.9 -> "Normal"
            imc < 29.9 -> "Sobrepeso"
            else -> "Obesidad"
        }

        val mensajeExtra = when (categoria) {
            "Normal" -> "¡Felicidades! Estás en un rango saludable."
            "Bajo peso" -> "Considera mejorar tu alimentación."
            "Sobrepeso", "Obesidad" -> "Te sugerimos consultar a un nutricionista o realizar actividad física."
            else -> ""
        }

        tvResult.text = "Tu IMC es: %.2f\nClasificación: %s\n%s".format(imc, categoria, mensajeExtra)
    }
}

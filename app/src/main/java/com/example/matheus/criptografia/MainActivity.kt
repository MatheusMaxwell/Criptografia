package com.example.matheus.criptografia

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

abstract class MainActivity : AppCompatActivity() {
    var IV = "AAAAAAAAAAAAAAAA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCriptografar.setOnClickListener {
            try {
                if(!edtTexto.text.isNullOrEmpty()){
                    AlertDialog.Builder(this).setView(R.layout.alert_dialog_layout).setTitle("Senha").setMessage("Informe a senha: ")
                        .setPositiveButton("Ok"){dialog, which ->
                            var viewDialog = layoutInflater.inflate(R.layout.alert_dialog_layout, null)
                            var senha = viewDialog.findViewById<EditText>(R.id.edtSenha)
                            resultado.text = String(criptografia(edtTexto.text.toString(), senha.text.toString()))
                        }.setNegativeButton("Cancel"){dialog, which ->
                            dialog.dismiss()
                        }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        btnDescriptografar.setOnClickListener {
            try {
                if(!resultado.text.isNullOrEmpty()){
                    AlertDialog.Builder(this).setView(R.layout.alert_dialog_layout).setTitle("Senha").setMessage("Informe a senha: ")
                        .setPositiveButton("Ok"){dialog, which ->
                            var viewDialog = layoutInflater.inflate(R.layout.alert_dialog_layout, null)
                            var senha = viewDialog.findViewById<EditText>(R.id.edtSenha)
                            resultado.text = descriptografia(resultado.text.toString().toByteArray(), senha.text.toString())
                        }.setNegativeButton("Cancel"){dialog, which ->
                            dialog.dismiss()
                        }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }



    }


    @Throws(Exception::class)
    fun criptografia(texto: String, chave: String): ByteArray {
        val encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE")
        val key = SecretKeySpec(chave.toByteArray(charset("UTF-8")), "AES")
        encripta.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(IV.toByteArray(charset("UTF-8"))))
        return encripta.doFinal(texto.toByteArray(charset("UTF-8")))
    }

    @Throws(Exception::class)
    fun descriptografia(textoencriptado: ByteArray, chave: String): String {
        val decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE")
        val key = SecretKeySpec(chave.toByteArray(charset("UTF-8")), "AES")
        decripta.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(IV.toByteArray(charset("UTF-8"))))
        return String(decripta.doFinal(textoencriptado), charset("UTF-8"))
    }
}

package com.hadirahimi.passwordchecker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.hadirahimi.passwordchecker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val drawableSuccess = ContextCompat.getDrawable(this,R.drawable.ic_have)
        val drawableError = ContextCompat.getDrawable(this,R.drawable.ic_error)
        
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0 : CharSequence? , p1 : Int , p2 : Int , p3 : Int) {}
    
            override fun onTextChanged(p0 : CharSequence? , p1 : Int , p2 : Int , p3 : Int) {}
    
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s : Editable?)
            {
                s?.let { password ->
                    if (password.isNotEmpty())
                    {
                        val strength = checkPasswordStrength(
                            password.toString(),
                            drawableSuccess,
                            drawableError
                        )
                        //update ui
                        binding.tvResult.text = "Password Strength : $strength"
                    }else{
                        resetDrawables()
                        binding.tvLength.text = "00"
                        binding.tvResult.text = ""
                    }
                }
            }
        }
        binding.etPassword.addTextChangedListener(textWatcher)
    }
    
    private fun checkPasswordStrength(password : String , drawableSuccess : Drawable? , drawableError : Drawable?) : Any
    {
        val length = password.length
        var hasLowerCase = false
        var hasUpperCase = false
        var hasSymbol = false
        
        binding.tvLength.text = String.format("%02d",length)
        for (char in password)
        {
            if (!hasLowerCase && ! hasUpperCase && !hasSymbol)
            {
                resetDrawables()
            }
            if (Character.isLowerCase(char)){
                hasLowerCase = true
                binding.hasLowerCase.setCompoundDrawablesWithIntrinsicBounds(drawableSuccess,null,null,null)
            }
            if (Character.isUpperCase(char)){
                hasUpperCase = true
                binding.hasUpperCase.setCompoundDrawablesWithIntrinsicBounds(drawableSuccess,null,null,null)
            }
            if (!char.isLetterOrDigit()){
                hasSymbol = true
                binding.hasSymbol.setCompoundDrawablesWithIntrinsicBounds(drawableSuccess,null,null,null)
            }
        }
        if(length <8 || !(hasLowerCase && hasUpperCase && hasSymbol))
        {
            return "Weak"
        }else if (length <12)
        {
            return "Moderate"
        }else
        {
            return "Strong"
        }
    }
    
    private fun resetDrawables()
    {
        val drawableError = ContextCompat.getDrawable(this,R.drawable.ic_error)
        binding.hasLowerCase.setCompoundDrawablesWithIntrinsicBounds(drawableError,null,null,null)
        binding.hasUpperCase.setCompoundDrawablesWithIntrinsicBounds(drawableError,null,null,null)
        binding.hasSymbol.setCompoundDrawablesWithIntrinsicBounds(drawableError,null,null,null)
        
    }
}













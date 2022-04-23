package com.example.kotlinmvvm_ecommerce.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmvvm_ecommerce.R
import com.example.kotlinmvvm_ecommerce.api.UserApiInterface
import com.example.kotlinmvvm_ecommerce.di.ApiAppModule
import com.example.kotlinmvvm_ecommerce.models.user.User
import com.example.kotlinmvvm_ecommerce.models.user.UserItem
import com.example.kotlinmvvm_ecommerce.repositories.UsersApiRepository
import com.example.kotlinmvvm_ecommerce.utils.PrefManager
import com.example.kotlinmvvm_ecommerce.viewmodels.user.UserViewModel
import com.example.kotlinmvvm_ecommerce.viewmodels.user.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var userNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var usersApiInterface: UserApiInterface
    private lateinit var usersApiRepository: UsersApiRepository
    private lateinit var userViewModel: UserViewModel
    private lateinit var usersArrayList: ArrayList<UserItem>
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        linkXML()
        initUI()
        getAllUsers()

        loginButton.setOnClickListener {
            validate(it)
        }
    }

    private fun getAllUsers(){
        if (usersArrayList.size>0)
            usersArrayList.clear()

        lifecycleScope.launch {
            userViewModel.usersStateFlow.collect { user->
                user.iterator().forEach { userItem ->
                    Log.i("USERITEM", "getAllUsers: "+userItem.name)
                    usersArrayList.add(userItem)
                }
            }
        }
        userNameEditText.setText("johnd")
        passwordEditText.setText("m38rmF$")
    }
    private fun validate(it:View){
        val userName:String=userNameEditText.text.toString()
        val userPass:String=passwordEditText.text.toString()
        when {
            userName.isEmpty() -> {
                var emailError = Snackbar.make(
                    it,
                    "Username cannot be empty !",
                    Snackbar.LENGTH_LONG
                )
                emailError.show()
            }
            userPass.isEmpty() -> {
                var passError = Snackbar.make(
                    it,
                    "Password cannot be empty !",
                    Snackbar.LENGTH_LONG
                )
                passError.show()
            }
            else -> {
               findUserByUsernameAndPass(it,userName = userName,pass = userPass)
            }
        }
    }
    private fun findUserByUsernameAndPass(view: View,userName:String, pass:String){
        if(usersArrayList.size!=0){
            usersArrayList.forEach {
                if(it.username.uppercase(Locale.getDefault()) == userName.uppercase(Locale.getDefault())
                    && it.password.uppercase(Locale.getDefault())==pass.uppercase(Locale.getDefault()))
                    {
                        prefManager.storeUserInPref(userItem = it)
                        var successLogin = Snackbar.make(
                            view,
                            "Login Successful !",
                            Snackbar.LENGTH_LONG
                        )
                        successLogin.show()
                    val intent = Intent(LoginActivity@this,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    var failedLogin = Snackbar.make(
                        view,
                        "Wrong username or password !",
                        Snackbar.LENGTH_LONG
                    )
                    failedLogin.show()
                }
            }
        }

    }
    private fun linkXML(){
        userNameEditText=findViewById(R.id.usernameEditText)
        passwordEditText=findViewById(R.id.passwordEditText)
        loginButton=findViewById(R.id.loginButton)
    }


    private fun initUI(){
        usersApiInterface= ApiAppModule.provideUserRetrofitInstance(ApiAppModule.provideBaseURL())
        usersApiRepository= UsersApiRepository(usersApiInterface)
        userViewModel= ViewModelProvider(this,
            UserViewModelFactory(usersApiRepository))[UserViewModel::class.java]
        usersArrayList=ArrayList()
        prefManager=PrefManager(this@LoginActivity)

    }

}
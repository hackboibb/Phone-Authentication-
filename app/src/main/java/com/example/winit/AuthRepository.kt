package com.example.winit

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class AuthRepository{
    private val firestore = FirebaseFirestore.getInstance()
    val auth  = FirebaseAuth.getInstance()


    fun startPhoneNumberVerification(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun checkIfUserExists(
        phoneNumber:String,
        OnResult: (Boolean)->Unit
    ){
        firestore.collection("users").whereEqualTo("phoneNumber",phoneNumber).get()
            .addOnSuccessListener { documents->
                if(documents.isEmpty){
                    OnResult(false)
                }else{
                    OnResult(true)
                }
            }
            .addOnFailureListener {
                OnResult(false)
            }
    }

    fun saveuser(user:User, OnResult: (Boolean) -> Unit){
        firestore.collection("users").document(user.PhoneNumber).set(user)
            .addOnSuccessListener {
                OnResult(true)
            }
            .addOnFailureListener {
                OnResult(false)
            }
    }

    fun Logout(result:Boolean){
        if(result==true){
            auth.signOut()
        }
    }

}
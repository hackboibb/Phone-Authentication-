package com.example.winit

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class AuthViewModel (private val authRepository: AuthRepository , val user: User):ViewModel(){





    private val _verificationId = MutableLiveData<String>()
    val verificationId: LiveData<String>get() = _verificationId

    private val _verificationState = MutableLiveData<VerificationState>()
    val verificationState: LiveData<VerificationState> get() = _verificationState



    fun checkUserAndVerifyPhoneNumber(phoneNumber: String,activity: Activity){

        authRepository.checkIfUserExists(phoneNumber){ exists->

            if(exists){
                verifyPhoneNumber(phoneNumber,activity)
            }else{
                _verificationState.value = VerificationState.UserNotFound
            }

        }

    }



    fun verifyPhoneNumber(phoneNumber:String,activity:Activity){
        authRepository.startPhoneNumberVerification(phoneNumber,activity,verificationCallbacks)
    }

    fun verifyOtp(verificationId:String,otp:String){
        val credential = PhoneAuthProvider.getCredential(verificationId,otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){


                    authRepository.checkIfUserExists(user.PhoneNumber){ exists ->

                        if(!exists){

                            saveUserToFireStore()

                        }else{
                            _verificationState.value = VerificationState.Success
                        }
                    }

                }
                else{
                    _verificationState.value = VerificationState.Failed(task.exception?.message?:"Unknown Error")
                }

            }
    }


    private val verificationCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            _verificationState.value = VerificationState.VerificationCompleted
        }

        override fun onVerificationFailed(e: FirebaseException) {
            _verificationState.value = VerificationState.Failed(e.message ?: "Unknown error")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            _verificationId.value = verificationId
            _verificationState.value = VerificationState.CodeSent


        }
    }

    private fun saveUserToFireStore(){


        val user = User(
            FirstName = user.FirstName,
            LastName = user.LastName,  // Replace with actual last name if available
            PhoneNumber =user.PhoneNumber

        )

        authRepository.saveuser(user){ success ->

            if(success){
                _verificationState.value = VerificationState.Success
            }else{
                _verificationState.value =VerificationState.Failed("Failed to save user data")
            }

        }

    }






    sealed class VerificationState {
        object VerificationCompleted : VerificationState()
        object CodeSent : VerificationState()
        object Success : VerificationState()
        object UserNotFound : VerificationState()
        data class Failed(val error: String) : VerificationState()

    }


    fun signout(result:Boolean){
        authRepository.Logout(result)
    }





}
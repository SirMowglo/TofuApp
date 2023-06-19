package com.example.tofuapp.util

import com.example.tofuapp.data.model.dto.error.ErrorResponseDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

const val networkIP = "192.168.1.131"
//const val API_BASE_URL = "http://10.0.2.2:8080/" // EMULADOR
const val API_BASE_URL = "http://$networkIP:8080/" // Localhost

fun<T> apiRequestFlow(call: suspend() -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(20000L){
        val response = call()

        try {
            if(response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            }else{
                response.errorBody()?.let {error ->
                    error.close()
                    val parsedError: ErrorResponseDTO = Gson().fromJson(error.charStream(), ErrorResponseDTO::class.java)
                    emit(ApiResponse.Failure(parsedError.message, parsedError.status))
                }
            }
        }catch (e:Exception){
            emit(ApiResponse.Failure(e.message ?: e.toString(), "UNAUTHORIZED"))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again", "UNAUTHORIZED"))
}.flowOn(Dispatchers.IO)


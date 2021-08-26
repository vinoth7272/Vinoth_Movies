package com.example.movierating

import com.example.movierating.data.network.NetworkUtils
import com.example.movierating.util.AppUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AppUtilTest {


    @Test
    fun testConvertDateFormat() {
        val result = AppUtils.convertDateFormat("2021-08-26")
        Assert.assertEquals("Aug 21", result)
    }

    @Test
    fun validateNetworkError_code504(){
       val result =   NetworkUtils.getErrorMessage(401)
        Assert.assertEquals(NetworkUtils.UNAUTHORIZED,result)
    }
    @Test
    fun validateNetworkError_ServerMessage(){
       val result =   NetworkUtils.getErrorMessage(404)
        Assert.assertEquals(NetworkUtils.NOT_FOUND,result)
    }

    @Test
    fun validateNetworkError_DefaultError(){
       val result =   NetworkUtils.getErrorMessage(0)
        Assert.assertEquals(NetworkUtils.SOMETHING_WRONG,result)
    }

}
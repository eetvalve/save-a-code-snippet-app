package com.save.codesnippetapp.configuration

import com.save.codesnippetapp.utils.CryptoUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.lang.Nullable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

@Component
class ApiInterceptor(private val cryptoUtil: CryptoUtil): HandlerInterceptor {

    @Value("\${secure.code}")
    lateinit var secureCode: String

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        println("Pre Handle method is Calling")
        if (request.requestURI.startsWith("/api/validateSecureCode")) {
            return true
        }

        // if header token matches secureCode then allow api-requests
        val decryptedSecureCode: String? = cryptoUtil.decrypt(request.getHeader("token"))
        return decryptedSecureCode == secureCode
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable modelAndView: ModelAndView?) {

       // println("Post Handle method is Calling")
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable ex: Exception?) {

       // println("Request and Response is completed")
    }
}

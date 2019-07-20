package com.save.codesnippetapp.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.lang.Nullable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception

@Component
class ApiInterceptor(@Value("${SECURE_CODE}") val secureCode: String): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        println("Pre Handle method is Calling")
        if (request.requestURI.startsWith("/api/validateSecureCode")) {
            return true
        }


        val decryptedSecureCode: String = CryptoUtil.decrypt(request.getHeader("token").toString())

        return decryptedSecureCode == secureCode
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable modelAndView: ModelAndView?) {

        println("Post Handle method is Calling")
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable ex: Exception?) {

        println("Request and Response is completed")
    }
}

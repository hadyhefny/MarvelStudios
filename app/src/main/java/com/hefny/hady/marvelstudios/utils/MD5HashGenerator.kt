package com.hefny.hady.marvelstudios.utils

import java.math.BigInteger
import java.security.MessageDigest
/**
 * This class is responsible for generating hash codes using timestamp, private and public api keys
 * (*required by Marvel Api*)
 */
class MD5HashGenerator {
    companion object{
        fun md5(ts: String, privateApiKey: String, publicApiKey : String): String{
            val input: String = ts + privateApiKey + publicApiKey
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32,'0')
        }
    }
}
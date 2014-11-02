package com.example

import java.io.{InputStreamReader, BufferedReader, InputStream}
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object Https {
  def main( args:Array[String] ):Unit = {
    val urlStr = "https://httpbin.org/get?show_env=1"
    val url = new URL(urlStr)
    val con = url.openConnection().asInstanceOf[HttpsURLConnection]
    con.setDoInput(true)
    con.setRequestMethod("GET")

    val response = handleResponse(con)

    println("Successfully invoked HTTPS service.")
    println(response)
  }

  def handleResponse(con: HttpsURLConnection):String = {
    try {
      readStream(con.getInputStream)
    } catch {
      case e:Exception =>
        e.printStackTrace()
        val output = readStream(con.getErrorStream)
        throw new Exception("HTTP " + String.valueOf(con.getResponseCode) + ": " + e.getMessage)
    }
  }

  def readStream(is: InputStream): String = {
    val reader = new BufferedReader(new InputStreamReader(is))
    var output = ""
    var tmp = reader.readLine
    while (tmp != null) {
      output += tmp
      tmp = reader.readLine
    }
    output
  }
}
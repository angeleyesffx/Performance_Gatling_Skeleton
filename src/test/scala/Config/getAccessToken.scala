package Config

import Config.Env.{getAuthClientId, getAuthClientSecret, getAuthScope, getAuthUrl}
import com.auth0.jwt.JWT
import ujson.Value

import java.util.Date
import scala.sys.process._

object getAccessToken {



  def getDataFromAuth: Value = {
    val cmd = Seq("curl", "--location", "--request", "GET", getAuthUrl,
      "--header", "Content-Type: application/x-www-form-urlencoded",
      "--data-urlencode", "grant_type=client_credentials",
      "--data-urlencode", getAuthClientId,
      "--data-urlencode", getAuthScope,
      "--data-urlencode", getAuthClientSecret)
    //.TakeWhile(condition)
    // Return the curl response as String
    val cmd_result = cmd.!!
    // Change the result from String to Array, using comma as delimiter
    cmd_result.split(",").flatMap(maybeInt => scala.util.Try(maybeInt.toInt).toOption).distinct
    // Change cmd.result into json
    val data = ujson.read(cmd_result)
    // Return data from Auth
    data
  }

  // Start global variable data as null. Data variable will be used to control and refresh the Authentication
  var data: Value = null
  //var count: Int = 0

  def isExpired(data: Value): Boolean = {
    // Check if the token is expired using jwt expiration date
    val access_token = data("access_token").value.toString
    // Get the expiration date at the first request
    val expirationDate: Date = JWT.decode(access_token).getExpiresAt
    // Get the current date
    val currentDate: Date = new Date(System.currentTimeMillis())
    if (expirationDate.before(currentDate)) {
      true
    }
    else {
      false
    }
  }

  def get_token: String = {
    // Check if the token is expired or null
    if (data == null || isExpired(data)) {
      //If the condition is true, it will refresh the authentication data
      data = getDataFromAuth
    }
    // In case the token is new or still valid
    val token_type = data("token_type").value
    val access_token = data("access_token").value
    val token = token_type + " " + access_token
    token
  }

}
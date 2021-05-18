package Scenarios

import Config.Env.getUrl
import Config.Feeder.createHeaderBearer
import io.gatling.core.Predef.{PebbleFileBody, gzipBody}
import io.gatling.http.Predef.{http, status}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder


object RequestBuilder {
  //RequestBuilder Object is responsible to select what type request will be executed and created an appropriate body

  def buildPostRequest(request_name: String, template_path: String, url: String, country: String): HttpRequestBuilder = {
    http(request_name)
      .post(url)
      // Getting the body template to be filled up by feeder
      .body(PebbleFileBody(template_path)).asJson.processRequestBody(gzipBody)
      // Fill up the custom header
      .headers(createHeaderBearer(country))
      .check(status is 202)
  }

  def buildDeleteRequest(request_name: String, template_path: String, url: String, country: String): HttpRequestBuilder = {
    http(request_name)
      .delete(url)
      // Getting the body template to be filled up by feeder
      .body(PebbleFileBody(template_path)).asJson.processRequestBody(gzipBody)
      // Fill up the custom header
      .headers(createHeaderBearer(country))
      .check(status is 202)
  }

  def buildPutRequest(request_name: String, template_path: String, url: String, country: String): HttpRequestBuilder = {
    http(request_name)
      .put(url)
      // Getting the body template to be filled up by feeder
      .body(PebbleFileBody(template_path)).asJson.processRequestBody(gzipBody)
      // Fill up the custom header
      .headers(createHeaderBearer(country))
      .check(status is 202)
  }

  def buildGetRequest(request_name: String, template_path: String, url: String, country: String): HttpRequestBuilder = {
    http(request_name)
      .get(url)
      // Getting the body template to be filled up by feeder
      .body(PebbleFileBody(template_path)).asJson.processRequestBody(gzipBody)
      // Fill up the custom header
      .headers(createHeaderBearer(country))
      .check(status is 202)
  }

  def buildRequest(method: String, request_name: String, template_path: String, url: String, country: String): HttpRequestBuilder = {
    //This method is responsible for create only one body for method
    method match {
      case "post" => buildPostRequest(request_name, template_path, url, country)
      case "put" => buildPutRequest(request_name, template_path, url, country)
      case "delete" => buildDeleteRequest(request_name, template_path, url, country)
      case "get" => buildGetRequest(request_name, template_path, url, country)
    }
  }
}

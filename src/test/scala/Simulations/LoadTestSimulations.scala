package Simulations

import scala.collection.mutable
import Config.Env.{getRequestPerUser, getBaseUrl, getCountries, getInsurances, getListOfMethodsFromYaml, getUrl, getUsers}
import Config.Feeder.{getFeeder}
import Scenarios.RequestBuilder.buildRequest
import io.gatling.core.Predef._
import io.gatling.core.structure.{PopulationBuilder}
import io.gatling.http.Predef.{http}
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.{FiniteDuration, MINUTES, SECONDS}
import scala.jdk.CollectionConverters.ListHasAsScala


class LoadTestSimulations extends Simulation {

  before {
  }
  var scns = new java.util.ArrayList[PopulationBuilder]
  def sendRequest: scala.List[PopulationBuilder] = {
    for (c <- getCountries) {
      var country = c.replaceAll("[\\[\\]]", "").trim
      for (e <- getInsurances(country)) {
        var insurance = e.replaceAll("[\\[\\]]", "").trim
        for (m<- getListOfMethodsFromYaml(country, insurance)) {
          var method = m.replaceAll("[\\[\\]]", "").trim
          // Setting up the basic information to customize the request protocols
          var url = getUrl(country, insurance, method)
          val theHttpProtocolBuilder: HttpProtocolBuilder = http
            .baseUrl(getBaseUrl)
            .acceptHeader("application/xml, text/html, text/plain, application/json, */*")
            .acceptCharsetHeader("UTF-8")
            .acceptEncodingHeader("gzip, deflate")

          // Setting up the data loaded in memory, using the feeder variable. Feeder will receive all the random data created by any function at the Feeder object.
          val feeder: Iterator[Map[String, Any]] = Iterator.continually(getFeeder(country, insurance, method))

          //Setting up the Test Scenario
          val scenarioBuilder: PopulationBuilder = scenario(s"Load Test for "+method+" to the Country: " + country.toUpperCase + " and Insurance: " + Insurance.toUpperCase + "")
            // At this moment .feed will do the "session().set" into the file, using "key: value" as parameter, in other words this process will be implicit.
            // Example of ordinary session setting up:
            // exec{ session => session("username").asOption[String],
            //                  session("password").asOption[String]
            //                );
            .feed(feeder)
            // Fill up the request and send it
            .group("Load Test") {
              repeat(getRequestPerUser(country, insurance, method)) {
                exec(
                  buildRequest(method, s"Load Test for " + method + " to the Country: " + country.toUpperCase + " and Insurance: " + insurance.toUpperCase + "", "bodies/person.json", url, country)
                )
              }
            }
            .inject(atOnceUsers(getUsers(country, insurance, method)))
            //.inject(nothingFor(FiniteDuration(1, SECONDS)), // 1
            //  rampUsers(10) during (FiniteDuration(30, SECONDS)),
            //  nothingFor(FiniteDuration(1, MINUTES)),
            //  rampUsers(20) during (FiniteDuration(3, MINUTES)))
            .protocols(theHttpProtocolBuilder)
          // Create a list of all Scenarios
          scns.add(scenarioBuilder)
        }
      }
    }
    ListHasAsScala(scns).asScala.toList
  }

  //The SetUp cannot be called more than once
  setUp(
    sendRequest: _*
  )
}
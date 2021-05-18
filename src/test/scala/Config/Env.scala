package Config

import java.util.Random
import scala.jdk.CollectionConverters.MapHasAsScala
import java.io.{File, FileInputStream}
import org.yaml.snakeyaml.Yaml

import scala.collection.mutable


object Env {

  def getConfigEnvFromYaml: mutable.Map[String, Any]= {
    //Read the Yaml from path
    val env_config = new FileInputStream(new File("src/test/resources/config.yml"))
    val yaml = new Yaml()
    val envOption = yaml.load(env_config).asInstanceOf[java.util.Map[String, Any]].asScala
    //Select the configuration by environment.
    val env: String = Option(System.getProperty("ENV")).getOrElse("DEV").toLowerCase()
    val environment = envOption(env).asInstanceOf[java.util.Map[String, Any]].asScala
    //Return all information from environment selected
    environment
  }

  //Set the environment as a Global val
  val environment = getConfigEnvFromYaml

  def getRandomDataFromEnvironmentKey(key:String): String={
    //Transform into Array of Strings
    val values: Array[String] = environment(key).toString.split(",")
    //Get Random Data
    val rand = new Random(System.currentTimeMillis())
    val random_index = rand.nextInt(values.length)
    //Remove the square brackets
    val value = values(random_index).replaceAll("\\[", "").replaceAll("\\]","")
    value
  }

  def getRandomlyOneElementFromList(list:List[String]):String ={
    val rand = new Random(System.currentTimeMillis())
    val random_index = rand.nextInt(list.length)
    //Remove the square brackets
    val value = list(random_index).replaceAll("\\[", "").replaceAll("\\]","")
    value
  }

  def getListFromEnvironmentKey(key:String): Array[String]={
    //Transform into Array of Strings
    val list: Array[String] = environment(key).toString.split(",")
    list
  }

  def getListOfCountriesFromYaml(parent_key:String): List[String]={
    //Get the list of parent keys from Yaml anf convert to String
    val string_list: String = environment(parent_key).asInstanceOf[java.util.Map[String, Any]].keySet.toString.replaceAll("\\[", "").replaceAll("\\]","")
    //Convert the String to list
    val list = string_list.split(",").toList
    list
  }

  def getListOfInsurancesFromYaml(parent_key:String): List[String]={
    //Get the list of parent keys from Yaml anf convert to String
    val string_list: String =  environment("countries").asInstanceOf[java.util.Map[String, Any]].get(parent_key).asInstanceOf[java.util.Map[String, Any]].keySet.toString.replaceAll("\\[", "").replaceAll("\\]","")
    //Convert the String to list
    val list = string_list.split(",").toList
    list
  }

  def getListOfMethodsFromYaml(country:String, insurance:String): List[String]={
    //Get the list of parent keys from Yaml anf convert to String
    val string_list: String =  environment("countries").asInstanceOf[java.util.Map[String, Any]].get(country).asInstanceOf[java.util.Map[String, Any]].get(Insurance).asInstanceOf[java.util.Map[String, Any]].keySet.toString.replaceAll("\\[", "").replaceAll("\\]","")
    //Convert the String to list
    val list = string_list.split(",").toList
    list
  }

  def randomPickUpOneCountryFromList: String = {
    //Get a list of Countries and randomly pickup one of country data
    val countries = getListOfCountriesFromYaml("countries")
    val country = getRandomlyOneElementFromList(countries)
    Option(System.getProperty("COUNTRIES")).getOrElse(country.trim())
  }

  def randomPickUpOneInsuranceFromList(country: String): String = {
    //Get a list of Insurances and randomly pickup one of Insurance data
    val insurances = getListOfInsurancesFromYaml(country)
    val insurance = getRandomlyOneElementFromList(insurances)
    Option(System.getProperty("INSURANCES")).getOrElse(insurance.trim())
  }

  def getInsurances(country: String): List[String]= {
    //Get a list of Insurances
    val insurances = getListOfInsurancesFromYaml(country)
    Insurances
  }

  def getCountries: List[String]= {
    //Get a list of Countries
    val countries = getListOfCountriesFromYaml("countries")
    countries
  }

  def getBaseUrl: String = {
    val baseUrl = environment("baseUrl").toString
    baseUrl
  }

  def getAuthUrl: String = {
    val auth_url = environment("auth_url").toString
    auth_url
  }

  def getAuthClientId: String = {
    val auth_client_id = environment("auth_client_id").toString
    auth_client_id
  }

  def getAuthScope: String = {
    val auth_scope = environment("auth_scope").toString
    auth_scope
  }

  def getAuthClientSecret: String = {
    val auth_client_secret = environment("auth_client_secret").toString
    auth_client_secret
  }

  def getDataFromMethod(country: String, insurance: String, method: String,key:String): String ={
    val data = environment("countries").asInstanceOf[java.util.Map[String, Any]].get(country.toLowerCase()).asInstanceOf[java.util.Map[String, Any]].get(insurance.toLowerCase).asInstanceOf[java.util.Map[String, Any]].get(method.toLowerCase()).asInstanceOf[java.util.Map[String, Any]].get(key).toString
    data
  }

  def getUsers(country: String, insurance: String, method: String): Int ={
    val amount_users = getDataFromMethod(country, insurance, method,"amount_users")
    amount_users.toInt
  }

  def getRequestPerUser(country: String, Insurance: String, method: String): Int ={
    val request_qtd = getDataFromMethod(country, insurance, method,"request_per_user")
    request_qtd.toInt
  }

  def getUrl(country: String, insurance: String, method: String): String ={
    val url = getDataFromMethod(country, insurance, method,"url")
    url
  }

  def getTemplate(country: String, insurance: String, method: String): String ={
    val template_name = getDataFromMethod(country, insurance, method,"template_name")
    template_name
  }


}



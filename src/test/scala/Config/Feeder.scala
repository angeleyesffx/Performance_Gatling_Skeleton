package Config

import Config.Env.getTemplate
import ujson.StringRenderer

import scala.io.Source
import scala.util.parsing.json.JSON
import java.util.UUID.randomUUID

object Feeder {

  def getStringFromJsonFile(fileName: String): Any = {
    val jsonFileName = fileName.toLowerCase()+".json"
    val path = os.read(os.pwd/"src"/"test"/"resources"/"data"/jsonFileName)
    val source = ujson.read(path)
    val jsonString = ujson.transform(source, StringRenderer()).toString
    jsonString
  }

  def convertJsonToHashMap(path: String): Any = {
    //Read the json file from the path
    val source = Source.fromFile(path)
    var jsonString = ""
    for (line <- source.getLines) {
      jsonString = jsonString + line
    }
    var hashMap = JSON.parseFull(jsonString).get
    //The HashMap will return dataset like this example below:
    //HashMap(data_information -> Map(email -> nomundodalua@cultura.com.br, name -> LUCAS SILVA E SILVA, phone -> 119999999999),
    //        personId -> 100000000,
    //        contacts -> List(Map(type -> PHONE, value -> 2634535494), Map(type -> EMAIL, value -> empty@empty.com)),
    //        )
    hashMap
  }

  def createHeaderBearer: Map[String, String]  ={
    val apoliceTraceId:String = randomUUID().toString
    Map(
      "apoliceTraceId" -> apoliceTraceId,
      "Authorization" -> getAccessToken.get_token,
      "Content-Type" -> "application/json",
      "Content-Encoding" -> "gzip",
    )
  }

  def getPersonID = {
    //generate random valid cpf ----> under development
    val cpf_number:String = "90227475062" 
  }

  def getFeeder(country: String, apolice_type: String, method: String ):Map[String, Any]  = {
    val template_name = getTemplate(country, apolice_type, method)
    val jsonString = getStringFromJsonFile(template_name)
    Map(
      "apolice_type" -> apolice_type.trim.toUpperCase,
      "person_id" -> getPersonID,
      "name" -> "Joana Silva",
      "birth" -> "", //To test PebbleBody I decide to keep this empty
      "creditdata" -> jsonString.toString.replaceAll("\\\"", "\\\\\"")
    )
  }
}
package info.ditrapani

import zio.json.{DeriveJsonDecoder, JsonDecoder, DecoderOps}
import com.typesafe.config.ConfigFactory
import sttp.client3._
import sttp.client3.httpclient.HttpClientFutureBackend
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

object Main extends App {
  val config = ConfigFactory.load()
  println("Hello")
  println(config.getString("foo"))

  val sort: Option[String] = None
  val query = "http language:scala"

  val request =
    basicRequest.get(uri"https://api.github.com/search/repositories?q=$query&sort=$sort")

  val backend = HttpClientFutureBackend()
  val future = request.send(backend)

  future.foreach { response =>
    {
      println(response.headers)
    }
  }
  Await.ready(future, 100.seconds)

  println("""{"bar": "hi", "baz": 22}""".fromJson[Foo])
}

final case class Foo(bar: String, baz: Int)

object Foo {
  implicit val decoder: JsonDecoder[Foo] = DeriveJsonDecoder.gen[Foo]
}

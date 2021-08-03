package info.ditrapani

import com.typesafe.config.ConfigFactory
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import sttp.client3.{basicRequest, UriContext}
import sttp.client3.httpclient.HttpClientFutureBackend

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

  {
    import scala.concurrent.duration._
    Await.ready(future, 100.seconds)
  }

  {
    import zio.json.DecoderOps
    println("""{"bar": "hi", "baz": 22}""".fromJson[Foo])
  }
}

final case class Foo(bar: String, baz: Int)

object Foo {
  import zio.json.{DeriveJsonDecoder, JsonDecoder}
  implicit val decoder: JsonDecoder[Foo] = DeriveJsonDecoder.gen[Foo]
}

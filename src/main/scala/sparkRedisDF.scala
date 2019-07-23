

object sparkRedisDF extends App with configuration {


  case class Person(name: String, age: Int)
  val personSeq = Seq(Person("John", 30), Person("Peter", 45),Person("Mouhcine", 24), Person("Corrine", 33),
                      Person("Murielle", 42), Person("Thibault", 32),Person("Selove", 27), Person("Soumaya", 26),
                      Person("Ali", 31), Person("Pascal", 40),Person("Philippe", 34), Person("Julien", 36),
                      Person("David", 50), Person("toto", 450))

  val dfWrite = spark.createDataFrame(personSeq)
  dfWrite.write
    .format("org.apache.spark.sql.redis")
    .option("table", "person")
    .option("key.column", "name")
    .save()

  val dfRead = spark.read
    .format("org.apache.spark.sql.redis")
    .option("table", "person")
    .option("key.column", "name")
    .load()

  dfRead.printSchema()
  dfRead.show()

  if(dfRead.count()== 14 ) {
    println("OK")
  } else {
    println("KO")
  }

  spark.close()
}

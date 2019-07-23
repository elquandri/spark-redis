import java.io.BufferedOutputStream

import com.redislabs.provider.redis._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import resultcheck.generate

object sparkRedis extends App  with configuration {
                 initifile()
  var rddWrite = createRdd()
                 writeRedis(rddWrite)
                 checkresult()




  def createRdd(): RDD[(String, String)] ={
    val personSeq = Seq(("Test1", "30"), ("Test2", "45"),("Test3", "24"), ("Test4", "33"),
      ("Test5", "42"), ("Test6", "32"),("Test7", "27"), ("Test8", "26"),
      ("Test9", "31"), ("Test10", "40"))

    return sc.parallelize(personSeq)
  }

  def writeRedis(rdd: RDD[(String,String)]){
    sc.toRedisKV(rdd)
  }

  def checkresult(): Unit = {

    var c=""

    val rddRead = sc.fromRedisKV("Test*")


    if (rddRead.count() ==10)
    {
      c = generate("passed").toString
    }
    else if(rddRead.count() !=10) {
      c = generate("failed").toString
    }
    else {
      c = generate("bloqued").toString
    }

    saveResultFile(c)

  }

  def initifile(): Unit =
  {
    val c =generate("failed").toString
    saveResultFile(c)
  }

  def saveResultFile(content: String) = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", pathHdfs)

    val date = java.time.LocalDate.now.toString
    val filePath = pathResult + resultPrefix + "_" + date + ".json"

    val fs = FileSystem.get(conf)
    val output = fs.create(new Path(filePath), true)

    val writer = new BufferedOutputStream(output)

    try {
      writer.write(content.getBytes("UTF-8"))
    }
    finally {
      writer.close()
    }
  }


  spark.close()
}

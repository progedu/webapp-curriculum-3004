object Interruption extends App {

  val t = new Thread(() => {
    while (true) {
      println("Sleeping...")
      Thread.sleep(1000)
    }
  })

  t.start()
  t.interrupt()

}
object Interruption extends App {

  val th = new Thread(() => {
    try {
      while (true) {
        println("Sleeping...")
        Thread.sleep(1000)
      }
    }catch {
      case _ : InterruptedException =>
    }
  })
  th.start()
  th.interrupt()
}
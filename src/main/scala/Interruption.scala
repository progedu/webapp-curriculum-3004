object Interruption extends App {

  new Thread(() => {
    while (true) {
      println("Sleeping...")
      Thread.sleep(1000)
    }
  }).start()

  Thread.sleep(2400)
  System.exit(0)

}
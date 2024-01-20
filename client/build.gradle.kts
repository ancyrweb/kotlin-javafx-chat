application {
  mainModule = "com.ancyracademy.chat.client"
  mainClass = "com.ancyracademy.chat.client.MainApplication"
}

javafx {
  version = "21.0.2"
  modules = mutableListOf("javafx.controls", "javafx.fxml")
}

dependencies {
  implementation(project(":protocol"))
  implementation("org.controlsfx:controlsfx:11.1.2")
}

//jlink {
//  imageZip.set(project.file("${project.buildDir}/image-zip/hello-image.zip"))
//  options =
//    ["--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"]
//
//  launcher {
//    name = "app"
//  }
//}
//
//jlinkZip {
//  group = "distribution"
//}
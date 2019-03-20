 import java.io.File
  //script 
 val folders = File(args[0]).listFiles { file -> file.isDirectory() }
 folders?.forEach { folder -> println(folder) }
 // idea 可以运行,eclipse就不行报java.lang.ClassNotFoundException: gnu.trove.THashMap
  
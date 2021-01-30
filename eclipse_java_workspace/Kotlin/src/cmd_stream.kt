import java.io.File

import java.io.InputStreamReader
import java.io.BufferedReader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.mutableListOf

fun main(args: Array<String>) {


    val proc = Runtime.getRuntime().exec("cmd /C dir")
    Scanner(proc.inputStream).use {
        while (it.hasNextLine()) println(it.nextLine())
    }



//    var process = Runtime.getRuntime().exec("git name-rev --name-only HEAD")
//    process.waitFor();
////    var branch=  process.inputStream.bufferedReader().readLine()
//    var list=java.util.ArrayList<String>()
//    process.inputStream.bufferedReader().forEachLine { line ->
//        list.add(line)
//    }


    var dateTime = SimpleDateFormat("yyyyMMddHHmm").format(Date());
    var version= dateTime
    val verFile = File("./version.txt")
    verFile.writeText(version)
    println(version)
}


/*
java code 
Process  process = Runtime.getRuntime().exec("git name-rev --name-only HEAD");
process.waitFor(); 
BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
System.out.println(reader.readLine());

*/





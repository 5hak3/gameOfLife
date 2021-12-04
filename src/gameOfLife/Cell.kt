package gameOfLife

class Cell {
    var isLive: Boolean = false

    fun nextStep(around: Array<Boolean>){
        val count: Int = around.count{it}
//        println(count)

        if (count<2) isLive = false
        if (count==3) isLive = true
        if (count>3) isLive = false
    }
}
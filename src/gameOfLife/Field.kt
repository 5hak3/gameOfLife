package gameOfLife

import java.lang.IllegalArgumentException
import kotlin.random.Random

class Field (private val height: Int, private val width: Int) {
    private val cells: Array<Array<Cell>> = Array(height) {Array(width){Cell()} }

    fun setInitStates(initStates: Array<Array<Int>>){
        if (this.height!=initStates.size || this.width!=initStates[0].size)
            throw IllegalArgumentException("The size of initStates must be equal to the size of cells")
        for (i in cells.indices){
            for(j in cells[i].indices){
                if (initStates[i][j]==1)
                    cells[i][j].isLive = true
            }
        }
    }

    fun setRandomStates(seed: Int = 72){
        val r = Random(seed)
        for (i in cells.indices){
            for (j in cells[i].indices){
                if (r.nextBoolean())
                    cells[i][j].isLive = true
            }
        }
    }

    fun nextStep(){
        // 各セルを均等に扱うために，先に全セルのAroundを算出しておく
        val around: Array<Array<Array<Boolean>>> = Array(cells.size){Array(cells[0].size){Array(8){false}}}
        for (i in cells.indices){
            for (j in cells[i].indices){
                val aroundIndex: Array<Array<Int>> =
                    arrayOf(
                        arrayOf(i-1,j-1),
                        arrayOf(i-1,j),
                        arrayOf(i-1,j+1),
                        arrayOf(i,j-1),
                        arrayOf(i,j+1),
                        arrayOf(i+1,j-1),
                        arrayOf(i+1,j),
                        arrayOf(i+1,j+1)
                    )
                if (i-1<0){
                    aroundIndex[0][0] = cells.size-1
                    aroundIndex[1][0] = cells.size-1
                    aroundIndex[2][0] = cells.size-1
                }
                if (i+1>cells.size-1){
                    aroundIndex[5][0] = 0
                    aroundIndex[6][0] = 0
                    aroundIndex[7][0] = 0
                }
                if (j-1<0){
                    aroundIndex[0][1] = cells[i].size-1
                    aroundIndex[3][1] = cells[i].size-1
                    aroundIndex[5][1] = cells[i].size-1
                }
                if (j+1>cells[i].size-1){
                    aroundIndex[2][1] = 0
                    aroundIndex[4][1] = 0
                    aroundIndex[7][1] = 0
                }

                for (k in around[i][j].indices)
                    around[i][j][k] = cells[aroundIndex[k][0]][aroundIndex[k][1]].isLive
            }
        }

        // 計算したaroundをCell.nextStep()に渡す
        for (i in cells.indices) {
            for (j in cells[i].indices) {
//                print("$i,$j >> ")
                cells[i][j].nextStep(around[i][j])
            }
        }
    }

    fun view() {
        for (i in cells.indices){
            for (j in cells[i].indices){
                if (cells[i][j].isLive) print("@")
                else print(".")
            }
            println()
        }
    }
}
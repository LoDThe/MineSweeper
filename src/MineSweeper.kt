package minesweeper

import java.util.Random

enum class Cell(val symbol: Char) {
    EMPTY('.'),
    MINE('*'),
    MARK('*'),
}

class MineSweeper(var minesLeft: Int, val rows: Int = 9, val columns: Int = 9) {
    var field: Array<Array<Cell>>
    var marked: Array<Array<Boolean>>

    init {
        field = Array(rows) { Array(columns) { Cell.EMPTY } }
        marked = Array(rows) { Array(columns) { false } }
        val rnd = Random()

        for (i in 0 until minesLeft) {
            while (true) {
                val x = rnd.nextInt(rows)
                val y = rnd.nextInt(columns)

                if (field[x][y] == Cell.EMPTY) {
                    field[x][y] = Cell.MINE
                    break
                }
            }
        }
    }

    private fun getNeighbourMinesCount(x: Int, y: Int): Int {
        var result = 0

        for (i in x - 1 until x + 2) {
            for (j in y - 1 until y + 2) {
                if ((i >= 0) && (i < rows) && (j >= 0) && (j < columns) && (field[i][j] == Cell.MINE)) {
                    ++result
                }
            }
        }

        return if (field[x][y] != Cell.MINE) result else result - 1
    }

    fun printField(showMines: Boolean = false) {
        println()
        println((1..columns).joinToString("", " |", "|"))
        println("-|" + "-".repeat(columns) + "|")

        for (row in 0 until rows) {
            print((row + 1).toString() + "|")

            for (column in 0 until columns) {
                if (field[row][column] == Cell.EMPTY) {
                    if (!showMines && marked[row][column]) {
                        print(Cell.MARK.symbol)
                    } else {
                        val neighbours = getNeighbourMinesCount(row, column)
                        print(if (neighbours > 0) neighbours.toString() else field[row][column].symbol)
                    }
                } else {
                    when {
                        showMines -> print(field[row][column].symbol)
                        marked[row][column] -> print(Cell.MARK.symbol)
                        else -> print(Cell.EMPTY.symbol)
                    }
                }
            }

            println("|")
        }

        println("-|" + "-".repeat(columns) + "|")
    }

    /**
     * Return true if user founded all mines, false otherwise
     */
    fun markCell(y: Int, x: Int): Boolean {
        if (marked[x - 1][y - 1]) {
            marked[x - 1][y - 1] = false
            minesLeft += if (field[x - 1][y - 1] == Cell.MINE) 1 else 0
        } else if ((field[x - 1][y - 1] != Cell.MINE) && getNeighbourMinesCount(x - 1, y - 1) > 0) {
            println("There is a number here!")

            return false
        } else {
            marked[x - 1][y - 1] = true
            minesLeft -= if (field[x - 1][y - 1] == Cell.MINE) 1 else 0
        }

        var showMines = true

        for (row in 0 until rows) {
            for (column in 0 until columns) {
                when (marked[row][column]) {
                    true -> if (field[row][column] != Cell.MINE) showMines = false
                    false -> if (field[row][column] == Cell.MINE) showMines = false
                }
            }
        }

        printField(showMines)

        return showMines
    }
}
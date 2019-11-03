package minesweeper

import java.util.Random
import java.util.Scanner

enum class GameState { WIN, LOOSE, UNCLEAR }

class Cell(var isMine: Boolean, var isMarked: Boolean = false, var isOpened: Boolean = false) {
    override fun toString(): String = when {
        isMarked -> "*"
        isOpened -> "/"
        else -> {
            "("
        }
    }
}

class MineSweeper(var minesLeft: Int, val rows: Int = 9, val columns: Int = 9) {
    var field: Array<Array<Cell>>
    var fieldWasCreated = false

    init {
        field = Array(rows) { Array(columns) { Cell(false) } }
    }

    private fun createField(X: Int, Y: Int) {
        val rnd = Random()

        for (i in 0 until minesLeft) {
            while (true) {
                val x = rnd.nextInt(rows)
                val y = rnd.nextInt(columns)

                if ((Pair(x, y) != Pair(X, Y)) && field[x][y].isMine.not()) {
                    field[x][y].isMine = true
                    break
                }
            }
        }
    }

    private fun inRange(x: Int, maxValue: Int) = (x >= 0) && (x < maxValue)

    private fun getNeighborCells(x: Int, y: Int): MutableList<Pair<Int, Int>> {
        val result = MutableList(0) { Pair(0, 0) }

        for (i in x - 1 until x + 2) {
            for (j in y - 1 until y + 2) {
                if (inRange(i, rows) && inRange(j, columns)) {
                    result.add(Pair(i, j))
                }
            }
        }

        result.remove(Pair(x, y))

        return result
    }

    private fun getNeighborMinesCount(x: Int, y: Int): Int {
        var result = 0

        for (neighbor in getNeighborCells(x, y)) {
            if (field[neighbor.first][neighbor.second].isMine) {
                ++result
            }
        }

        return result
    }

    fun printField(isLoose: Boolean) {
        println()
        println((1..columns).joinToString("", " |", "|"))
        println("-|" + "-".repeat(columns) + "|")

        for (row in 0 until rows) {
            print((row + 1).toString() + "|")

            for (column in 0 until columns) {
                val cell = field[row][column]

                if (cell.isMine) {
                    when {
                        isLoose -> print('X')
                        cell.isMarked -> print('*')
                        else -> print('.')
                    }
                } else if (cell.isMarked) {
                    print('*')
                } else {
                    val neighbors = getNeighborMinesCount(row, column)

                    when (neighbors) {
                        0 -> print(if (cell.isOpened) '/' else '.')
                        else -> print(neighbors)
                    }
                }
            }

            println("|")
        }

        println("-|" + "-".repeat(columns) + "|")
    }

    private fun openCell(x: Int, y: Int) {
        if (field[x][y].isOpened) {
            return
        }

        field[x][y].isOpened = true
        field[x][y].isMarked = false

        if (getNeighborMinesCount(x, y) == 0) {
            for (neighbor in getNeighborCells(x, y)) {
                openCell(neighbor.first, neighbor.second)
            }
        }
    }

    private fun markCell(x: Int, y: Int) {
        field[x][y].apply { isMarked = isMarked.not()}
    }

    fun triggerCell(y: Int, x: Int, query: String): GameState {
        when (query) {
            "free" -> {
                if (fieldWasCreated.not()) {
                    createField(x - 1, y - 1)
                    fieldWasCreated = true
                }

                openCell(x - 1, y - 1)
            }
            "mine" -> markCell(x - 1, y - 1)
        }

        printField(getGameState() == GameState.LOOSE)

        return getGameState()
    }

    fun getGameState(): GameState {
        if (fieldWasCreated.not()) {
            return GameState.UNCLEAR
        }

        var allMinesAreMarked = true
        var allMinesAreUnmarked = true

        field.forEach{ it.forEach {
            if (it.isOpened && it.isMine) {
                return GameState.LOOSE
            }

            if (it.isMarked != it.isMine) {
                allMinesAreMarked = false
            }

            if (it.isMine.not() && (it.isMarked || it.isOpened.not())) {
                allMinesAreUnmarked = false
            }
        } }

        return if (allMinesAreMarked || allMinesAreUnmarked) GameState.WIN else GameState.UNCLEAR
    }
}
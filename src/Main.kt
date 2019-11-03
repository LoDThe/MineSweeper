package minesweeper

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    print("How many mines do you want on the field? ")
    val sweeper = MineSweeper(scanner.nextInt())
    sweeper.printField(false)

    do {
        print("Set/unset mines marks or claim a cell as free: ")
    } while (sweeper.triggerCell(scanner.nextInt(), scanner.nextInt(), scanner.next()) == GameState.UNCLEAR)

    if (sweeper.getGameState() == GameState.WIN) {
        println("Congratulations! You founded all mines!")
    } else {
        println("You stepped on a mine and failed!")
    }
}

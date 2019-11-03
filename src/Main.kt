package minesweeper

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    print("How many mines do you want on the field? ")
    val sweeper = MineSweeper(scanner.nextInt())
    sweeper.printField()

    do {
        print("Set/delete mines marks (x and y coordinates): ")
    } while (!sweeper.markCell(scanner.nextInt(), scanner.nextInt()))

    println("Congratulations! You founded all mines!")
}

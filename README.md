## MineSweeper
It's an [hyperskill](https://hyperskill.org) project written on Kotlin

## The game should contain the following rules:

1. The game starts with an empty field.

2. The user can mark (flag) some cells as cells that potentially have a mine. Any empty cell can be flagged, not only cells that really contain a mine. In the example, this is done by typing the word "mine" after entering the coordinates. After that, the cell is marked with a '*' (a mine flag).

3. The user can also remove mine flags from cells. In the example, the user simply types 'mine' again after entering the same coordinates of the cell. After that, the cell should be marked as an empty cell.

4. The only way to get information about the field is to explore it. In the example, this is done by typing 'free' after entering the coordinates of the cell. This means that the user thinks that this cell is free of mines and thus can be explored. Make the generation of mines as in the original game - the first cell marked as "free" cannot be a mine, it should always be an empty field. You can achieve this in many ways, it's up to you.

5. Obviously, if a cell has 0 mines around it, you can explore all the cells around it without any problems. Therefore this should be done automatically by the program. Also, if there is yet another cell next to it with 0 mines around it, the program should automatically check all the cells around that cell, and so on until no cells can be checked automatically. In the example, all cells with 0 mines around them are marked with a '/' symbol.

6. If the user wants to explore a cell with 1 to 8 mines around it, the program should only expose that cell, because mines could be anywhere.

7. If the user wants to explore a cell that contains a mine, the user loses. After that, you can show all the mines on the field. This situation is shown in the first example.

8. If the user marks all the cells with potential mines, the user wins. Note that the user must mark all the mines, but no empty cells; if the user has extra mine flags that do not contain mines, the user should continue playing. After clearing all excess mine flags, he user wins. This situation is presented in the second example.

9. There is another way to win. The user can simply explore all explorable cells, leaving only cells with mines. After that, the user wins. This situation is presented in the third example.
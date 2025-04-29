from graphics import *
from OBoard import *
from Cell import *
from Button import *

def initialPieces(cb):
    cb.cells[27].updatePiece("white")
    cb.cells[28].updatePiece("black")
    cb.cells[35].updatePiece("black")
    cb.cells[36].updatePiece("white")

def main():
    win = GraphWin("Othello", 1000, 800)
    ob = OBoard(win)

    quiButton = Button(win, "Exit", 60, Point(870, 220))
    turnT = Text(Point(850, 400), "white plays")
    turnT.draw(win)
    startButton = Button(win, "", 60, Point(850, 100))
    startT = Text(Point(850, 100), "START")
    startT.draw(win)
    checkT = Text(Point(850, 550), "")
    checkT.draw(win)
    restart = False

    while True:
        m1 = win.getMouse()
        if quiButton.isClicked(m1):
            win.close()
            exit()
        if startButton.isClicked(m1):
            startT.setText("RESTART")
            restart = True
            break

    initialPieces(ob)

    while True:
        turn = False
        turnT.setText(ob.whoMove + " plays")
        mov = ob.checkMoves()

        if len(mov) == 0:
            turnT.setText(ob.whoMove + " has no moves. game over")
            break

        m1 = win.getMouse()

        if quiButton.isClicked(m1):
            win.close()
            exit()

        if startButton.isClicked(m1):
            if restart:
                for i in range(64):
                    ob.cells[i].empty()
                initialPieces(ob)
                ob.setWhoMove("black")
                turnT.setText(ob.whoMove + " plays")
                continue

        validMove = False
        for moveIndex in mov:
            cell = ob.cells[moveIndex]
            if cell.isClicked(m1):
                ob.place(moveIndex)
                turn = True
                validMove = True
                break

        if validMove:
            for moveIndex in mov:
                ob.cells[moveIndex].unHighlight()

            AImov = ob.checkMoves()
            if len(AImov) == 0:
                turnT.setText(ob.whoMove + " has no moves. game over")
                break
            choice = ob.findBestMove(AImov)
            ob.place(choice)
            for moveIndex in AImov:
                ob.cells[moveIndex].unHighlight()

if __name__ == "__main__":
    main()

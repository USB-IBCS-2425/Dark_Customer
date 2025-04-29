from graphics import *

class Cell:

    def __init__(self, win, coord, color):
        self.win = win
        self.coord = coord
        self.piece = ""
        self.color = color
        self.highlighted = False
        self.r = None
        self.circ = None

        if win is not None:
            self.minX = coord[0] * (win.getWidth() / 8) - 300 * coord[0] / 8 + 50
            self.minY = coord[1] * (win.getHeight() / 8) - 100 * coord[1] / 8 + 50
            self.maxX = self.minX + (win.getWidth() - 300) / 8
            self.maxY = self.minY + (win.getHeight() - 100) / 8
            self.r = Rectangle(Point(self.minX, self.minY), Point(self.maxX, self.maxY))
            self.r.setFill(color)
            self.r.setOutline("black")
            self.r.draw(win)

            self.circ = Circle(Point((self.maxX + self.minX) / 2, (self.maxY + self.minY) / 2), 40)
        else:
            self.minX = self.minY = self.maxX = self.maxY = 0

    def isClicked(self, p):
        if self.r is None:
            return False
        x = p.getX()
        y = p.getY()
        return self.minX < x < self.maxX and self.minY < y < self.maxY

    def highlight(self):
        if self.r is not None:
            self.r.setOutline("blue")
            self.r.setWidth(4)
        self.highlighted = True

    def unHighlight(self):
        if self.r is not None:
            self.r.setOutline("black")
            self.r.setWidth(1)
        self.highlighted = False

    def updatePiece(self, piece):
        self.piece = piece
        if self.circ is not None:
            self.circ.setFill("black" if piece == "black" else "white")
            self.circ.undraw()
            self.circ.draw(self.win)

    def getCoord(self):
        return self.coord

    def hMove(self):
        if self.r is not None:
            self.r.setOutline("pink")
            self.r.setWidth(4)

    def unHMove(self):
        if self.r is not None:
            self.r.setOutline("black")
            self.r.setWidth(1)

    def empty(self):
        self.piece = ""
        if self.circ is not None:
            self.circ.undraw()

    @staticmethod
    def copy(cell):
        new_cell = Cell(None, cell.coord, cell.color)
        new_cell.piece = cell.piece
        return new_cell

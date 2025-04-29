from Button import *
from Cell import *

class OBoard():    
    
    def __init__(self, win):
        self.cells = []
        for i in range(8):
            for j in range(8):
                color = "white" if (i + j) % 2 == 0 else "grey"
                self.cells.append(Cell(win, (j, i), color))

        self.potentMoves = []
        self.whoMove = "black"

    def setWhoMove(self, pl):
        self.whoMove = pl

    def getPotentMoves(self):
        return self.potentMoves

    def getAllCells(self):
        return self.cells

    def indexOfMaxVal(self, nlist):
        maxindex = 0
        if len(nlist) == 0:
            return 0
        maxvalue = nlist[0]
        for i in range(len(nlist)):
            if nlist[i] > maxvalue:
                maxindex = i
                maxvalue = nlist[i]
        return maxindex

    def moveCaptureValue(self, move):
        currentpiececount = 0
        projectedpiececount = 0
        tempBoard = OBoard(None)
        tempBoard.cells = [Cell.copy(cell) for cell in self.cells]
        tempBoard.setWhoMove(self.whoMove)
        tempBoard.place(move)

        for cell in self.cells:
            if cell.piece == "white":
                currentpiececount += 1
                
        for cell in tempBoard.getAllCells():
            if cell.piece == "white":
                projectedpiececount ++ 1

        return 0.6 * (projectedpiececount - currentpiececount)

    def moveTotalValues(self, moves):
        movescores = []

        for move in moves:
            score = self.moveCaptureValue(move)

            if move in [0, 7, 56, 63]:  # corner
                score += 100
            elif move in range(0, 8) or move in range(56, 64):  # top/bottom sides
                score += 5
            elif move % 8 == 0 or (move + 7) % 8 == 0:  # left/right sides
                score += 5

            tempBoard = OBoard(None)
            tempBoard.cells = [Cell.copy(cell) for cell in self.cells]
            tempBoard.setWhoMove(self.whoMove)
            tempBoard.place(move)

            opponentMoveList = tempBoard.checkMoves()

            if len(opponentMoveList) > 0:
                opponentMoveScores = []
                for z in opponentMoveList:
                    opponentMoveScores.append(self.moveCaptureValue(z))
                score -= max(opponentMoveScores)

                for w in opponentMoveList:
                    if w in [0, 7, 56, 63]:
                        score -= 80
                        break
                    elif w in range(0, 8) or w in range(56, 64):
                        score -= 5
                        break
                    elif w % 8 == 0 or (w + 7) % 8 == 0:
                        score -= 5
                        break
            else:
                score += 50

            movescores.append(score)

        return movescores

    def findBestMove(self, moves):
        movescorelist = self.moveTotalValues(moves)
        return moves[self.indexOfMaxVal(movescorelist)]

    def checkMoves(self):
        moves = []
        directions = [[-1, -8], [0, -8], [1, -8], [-1, 0], [1, 0], [-1, 8], [0, 8], [1, 8]]
        for i in range(64):
            cell = self.cells[i]
            if cell.piece == "":
                for d in directions:
                    if (i % 8 == 0 and d[0] == -1) or (i % 8 == 7 and d[0] == 1):
                        continue
                        
                    adjIndex = i + d[0] + d[1]
                    if adjIndex < 0 or adjIndex >= 64:
                        continue
                    if (adjIndex % 8 == 0 and d[0] == -1) or (adjIndex % 8 == 7 and d[0] == 1):
                        continue
                    if self.cells[adjIndex].piece != "" and self.cells[adjIndex].piece != self.whoMove:
                        currentIndex = adjIndex + d[0] + d[1]
                        foundOwn = False
                        
                        while 0 <= currentIndex < 64:
                            if (currentIndex % 8 == 0 and d[0] == -1) or (currentIndex % 8 == 7 and d[0] == 1):
                                break
                                
                            currentCell = self.cells[currentIndex]
                            if currentCell.piece == "":
                                break
                            elif currentCell.piece == self.whoMove:
                                foundOwn = True
                                break

                            currentIndex += d[0] + d[1]
                            
                        if foundOwn and i not in moves:
                            moves.append(i)
        for m in moves:
            self.cells[m].highlight()
            
        return moves

    def place(self, c):
        cell = self.cells[c] if isinstance(c, int) else c
        cell.updatePiece(self.whoMove)
        toFlip = []
        ind = c if isinstance(c, int) else self.cells.index(c)
        directions = [[-1, -8], [0, -8], [1, -8], [-1, 0], [1, 0], [-1, 8], [0, 8], [1, 8]]
        for d in directions:
            newInd = ind + d[0] + d[1]
            tempFlip = []
            for i in range(7):
                if newInd < 0 or newInd >= 64:
                    break
                if (newInd % 8 == 7 and d[0] == 1) or (newInd % 8 == 0 and d[0] == -1):
                    break
                
                if self.cells[newInd].piece != self.whoMove and self.cells[newInd].piece != "":
                    tempFlip.append(newInd)
                elif self.cells[newInd].piece == self.whoMove:
                    toFlip.extend(tempFlip)
                    break
                else:
                    break
                newInd += d[0] + d[1]

        for f in toFlip:
            self.cells[f].updatePiece(self.whoMove)

        self.whoMove = "white" if self.whoMove == "black" else "black"

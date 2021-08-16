package com.chess.piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.chess.board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

public class King extends AbstractPiece implements Movable {


    public King(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "King";
    }


    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moveCandidates = new ArrayList<>();
        Map<Location, Square> squareMap = board.getLocationSquareMap();
        Location current = this.getCurrentSquare().getLocation();
        getMoves(moveCandidates, squareMap, current, 0, 1);
        getMoves(moveCandidates, squareMap, current, 1, 0);
        getMoves(moveCandidates, squareMap, current, 1, 1);
        getMoves(moveCandidates, squareMap, current, -1, -1);
        getMoves(moveCandidates, squareMap, current, -1, 0);
        getMoves(moveCandidates, squareMap, current, -1, 1);
        getMoves(moveCandidates, squareMap, current, 1, -1);
        getMoves(moveCandidates, squareMap, current, 0, -1);
        return moveCandidates;
    }
    private void getMoves(
            List<Location> candidates,
            Map<Location, Square> squareMap,
            Location current,
            int rankOffset,
            int fileOffset) {
        try {
            Location next = LocationFactory.build(current, fileOffset, rankOffset);
            if (squareMap.containsKey(next)) {
                if (squareMap.get(next).isOccupied()) {
                    if (squareMap.get(next).getCurrentPiece().pieceColor.equals(this.pieceColor)) return;
                    candidates.add(next);
                    return;
                }
                candidates.add(next);
            }
        } catch (Exception e) { }
    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    @Override
    public void makeMove(Square square) {
        System.out.println(this.getName() + "-> makeMove()");
    }
}
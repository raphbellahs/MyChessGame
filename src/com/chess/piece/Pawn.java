package com.chess.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.chess.board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

public class Pawn extends AbstractPiece implements Movable {

    private boolean isFirstMove = true;

    public Pawn(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Pawn";
    }


    /**
     * Return the valid moves for the pawn
     * @param  board  a board object representing the chess board
     * @return List<Location>  a list of valid moves locations
     */
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moveCandidates = new ArrayList<>();
        Location current = this.getCurrentSquare().getLocation();
        int sign = pieceColor.equals(PieceColor.DARK) ? -1:1;

        moveCandidates.add(LocationFactory
                .build(current, 0, sign));
        if(isFirstMove) {
            moveCandidates.add(LocationFactory
                    .build(current, 0, 2 * sign));
            return moveCandidates;
        }
        // adding moves by capture pieces (on the side)
        moveCandidates.add(LocationFactory.build(current, 1, sign));
        moveCandidates.add(LocationFactory.build(current, -1, sign));
        Map<Location, Square> squareMap = board.getLocationSquareMap();
        List<Location> validMoves = moveCandidates.stream()
                .filter(squareMap::containsKey)
                .collect(Collectors.toList());

        // filtering all bad moves
        return validMoves.stream().filter((candidate) -> {
            // occupied
            if(candidate.getFile().equals(this.getCurrentSquare().getLocation().getFile()) &&
                    squareMap.get(candidate).isOccupied()) {
                return false;
            }

            // occupied in front.
            if (squareMap.get(candidate).isOccupied() &&
                    candidate.getFile().equals(this.getCurrentSquare().getLocation().getFile())) {
                return false;
            }

            // occupied on diagonal with opposite color
            if (squareMap.get(candidate).isOccupied() &&
                    squareMap.get(candidate).getCurrentPiece().getPieceColor().equals(this.getPieceColor()) &&
                    candidate.getFile().equals(this.getCurrentSquare().getLocation().getFile())
            ) {
                return false;
            }

            return squareMap.get(candidate).isOccupied() ||
                    candidate.getFile().equals(this.getCurrentSquare().getLocation().getFile());
        }).collect(Collectors.toList());


    }

    @Override
    public List<Location> getValidMoves(Board board, Square square) {
        return null;
    }

    @Override
    public void makeMove(Square square) {
        if (isFirstMove) {
            isFirstMove = false;
        }
        this.currentSquare.setOccupied(false);
        this.setCurrentSquare(square);
        square.setCurrentPiece(this);
        square.setOccupied(true);
    }
}
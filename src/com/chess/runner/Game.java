package com.chess.runner;

import java.util.Scanner;

import com.chess.board.Board;
import com.chess.common.File;
import com.chess.common.Location;
import com.chess.piece.Movable;
import com.chess.squares.Square;

public class Game {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        Scanner scanner = new Scanner(System.in);
        // true -> game is not finished.
        while(true) {
            // Scan input from user
            String line = scanner.nextLine();
            String[] fromTo = line.split("->");
            File fromFile = File.valueOf(String.valueOf(Character.toUpperCase(fromTo[0].charAt(0))));
            int fromRank = Integer.parseInt(String.valueOf(fromTo[0].charAt(1)));
            File toFile = File.valueOf(String.valueOf(Character.toUpperCase(fromTo[1].charAt(0))));
            int toRank = Integer.parseInt(String.valueOf(fromTo[1].charAt(1)));

            Square fromSq = board.getLocationSquareMap().get(new Location(fromFile, fromRank));
            Square toSq = board.getLocationSquareMap().get(new Location(toFile, toRank));

            //Checking if the location is legal

            Movable currPiece = fromSq.getCurrentPiece();
            Location toLoc = toSq.getLocation();
            if (currPiece.getValidMoves(board).contains(toLoc)){
                currPiece.makeMove(toSq);
                fromSq.reset();
            }
            else {
                System.out.println("Invalid location .... Please try again ");
            }

            board.printBoard();

        }

    }

    public static void printPiece(Movable piece) {
        piece.getValidMoves(null);
    }
}
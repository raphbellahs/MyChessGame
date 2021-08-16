package com.chess.common;

public class LocationFactory {
    private static final File[] files = File.values();
    public static Location build(Location current,Integer fileOffSet,Integer rankOffSet){
        Integer currentFile = current.getFile().ordinal();
        return new Location(files[currentFile + fileOffSet],current.getRank() + rankOffSet);
    }
}

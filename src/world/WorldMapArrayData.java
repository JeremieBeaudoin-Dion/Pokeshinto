package world;

/**
 * Respository for the framework of the Maps in WorldMap
 *
 * @author Jérémie Beaudoin-Dion
 */
class WorldMapArrayData {

    int[][] getBackground(String mapID) {
        switch (mapID){
            case "Test":
                return new int[][] {{0, 1, 1, 1, 1, 1, 1, 24, 21, 50, 51, 14, 31, 31, 31, 31, 31, 31, 31, 32},
                        {20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 30, 31, 31, 31, 31, 31, 31, 13, 52},
                        {20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 30, 31, 31, 31, 31, 31, 31, 32, 21},
                        {20, 21, 21, 5, 6, 7, 21, 21, 21, 21, 21, 30, 31, 31, 31, 31, 31, 31, 32, 21},
                        {20, 21, 5, 29, 26, 28, 6, 6, 7, 21, 21, 30, 31, 31, 31, 31, 31, 31, 32, 21},
                        {20, 21, 25, 26, 26, 26, 26, 26, 27, 21, 21, 50, 51, 51, 51, 51, 14, 31, 32, 21},
                        {20, 21, 45, 9, 26, 26, 26, 26, 27, 21, 21, 21, 21, 21, 21, 21, 50, 51, 52, 21},
                        {20, 21, 21, 45, 9, 26, 26, 26, 27, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {24, 21, 21, 21, 45, 46, 46, 46, 47, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21}};
            case "FirstTemple":
                return new int[][] {{96, 89, 89, 89, 89, 89, 89, 89, 89, 97, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {78, 74, 74, 74, 74, 74, 74, 74, 74, 79, -1, -1, -1, -1, -1},
                        {98, 118, 118, 99, 74, 74, 98, 118, 118, 99, -1, -1, -1, -1, -1}};
            case "FirstForest":
                return new int[][] {{21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 3, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 4, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 22, 69, 69, 69, 69, 69, 69, 69, 69, 69, 69, 20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 22, 73, 74, 74, 74, 74, 74, 74, 74, 74, 75, 20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 22, 73, 74, 74, 74, 74, 74, 74, 74, 74, 75, 20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 22, 93, 94, 94, 94, 94, 94, 94, 94, 94, 95, 20, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 22, 73, 74, 74, 74, 74, 74, 74, 74, 74, 75, 20, 21, 10, 11, 11, 11, 11, 11, 11, 11, 11},
                        {21, 21, 21, 22, 70, 69, 69, 69, 74, 74, 69, 69, 69, 72, 20, 10, 34, 31, 31, 31, 31, 31, 31, 31, 31},
                        {21, 21, 21, 23, 1, 1, 1, 2, 26, 26, 0, 1, 1, 1, 24, 30, 31, 31, 31, 31, 31, 31, 31, 31, 31},
                        {21, 21, 21, 21, 21, 21, 21, 22, 26, 26, 20, 21, 21, 21, 21, 50, 14, 31, 31, 31, 31, 31, 31, 31, 31},
                        {21, 21, 21, 21, 21, 21, 21, 22, 26, 26, 20, 21, 21, 21, 21, 21, 50, 14, 31, 13, 51, 51, 14, 31, 31},
                        {21, 21, 21, 21, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 21, 21, 21, 30, 31, 32, 21, 21, 30, 31, 31},
                        {21, 21, 21, 21, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 21, 21, 21, 50, 51, 52, 21, 21, 30, 31, 31},
                        {21, 21, 21, 21, 26, 26, 21, 21, 10, 12, 21, 21, 26, 26, 21, 21, 21, 21, 21, 21, 21, 21, 30, 31, 31},
                        {21, 21, 21, 21, 26, 26, 21, 10, 34, 33, 12, 21, 26, 26, 21, 21, 21, 21, 21, 21, 21, 21, 50, 51, 51},
                        {21, 21, 21, 21, 26, 26, 21, 50, 51, 51, 52, 21, 26, 26, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 26, 26, 21, 21, 21, 21, 21, 21, 26, 26, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26},
                        {21, 21, 21, 21, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21},
                        {21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21}};
        }

        return null;
    }

    int[][] getSolidObject(String mapID) {
        switch (mapID){
            case "Test":
                return new int[][] {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, 220, -1, 220, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, 221, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 222, -1, -1, -1, 222, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, 220, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, 221, -1, -1, -1, -1, -1, -1, -1, -1, 221, -1, -1, -1, -1, -1, -1, -1, 222, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, 221, -1, -1, 222, -1, -1, -1, -1, -1, -1, -1, 222, -1, -1, -1},
                        {-1, -1, 222, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};
            case "FirstTemple":
                return new int[][] {{-1, -1, 193, -1, -1, -1, -1, 193, -1, -1},
                        {-1, 248, -1, -1, 248, 248, -1, -1, 248, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, 248, -1, -1, -1, -1, -1, -1, 248, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, 248, -1, -1, -1, -1, -1, -1, 248, -1},
                        {-1, -1, -1, 248, -1, -1, 248, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};
            case "FirstForest":
                return new int[][] {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 180, -1, -1, -1, 182, 220, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 222, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 182, -1, -1, 220, -1, -1, -1, 220, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, 222, -1, -1, 180, -1, 222, -1},
                        {220, 182, 224, -1, -1, 216, 216, -1, -1, -1, -1, 216, 216, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, 194, 194, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, 222, -1, 220, -1, -1, 224, -1, -1, -1, -1, 224, -1, 222, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 224, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, 222, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 182, 224, -1, -1, -1, 224, -1, -1, -1, -1},
                        {-1, 220, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, -1, 224, -1, -1, -1},
                        {-1, -1, -1, 182, -1, -1, -1, 182, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, 224, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 220, -1, -1, -1, -1, -1, -1, -1, -1, 180, -1, -1, 180, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {180, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 222, -1, -1, -1, 225, -1, -1, 224, -1},
                        {-1, -1, -1, 180, -1, -1, 180, -1, -1, 180, -1, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, -1, -1, 222, -1},
                        {-1, 222, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 182, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {224, -1, -1, -1, 220, -1, -1, -1, -1, 180, -1, 224, -1, -1, 220, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, 220, -1, -1, -1, 222, -1, -1, -1, 182, -1, -1, -1, 180, -1, 220, -1, -1, -1, 222, -1, -1, -1},
                        {-1, 220, -1, -1, 180, -1, -1, -1, -1, 220, -1, -1, -1, 222, -1, -1, -1, -1, -1, -1, 182, -1, -1, 220, -1},
                        {-1, -1, -1, -1, -1, -1, 224, 180, -1, -1, -1, -1, -1, -1, -1, 182, -1, -1, 220, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 220, -1, -1, 220, -1, -1, -1, -1, 182, 220, -1, 180, -1, -1, -1, -1, -1, -1, -1, 220, -1, -1, -1}
                };
        }

        return null;
    }

    int[][] getFloatingObject(String mapID) {
        switch (mapID){
            case "Test":
                return new int[][] {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, 200, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, 201, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 202, -1, -1, -1, 202, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, 201, -1, -1, -1, -1, -1, -1, -1, -1, 201, -1, -1, -1, -1, -1, -1, -1, 202, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, 201, -1, -1, 202, -1, -1, -1, -1, -1, -1, -1, 202, -1, -1, -1},
                {-1, -1, 202, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};
            case "FirstTemple":
                return new int[][] {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};
            case "FirstForest":
                return new int[][]{{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1},
                        {-1, -1, -1, -1, 131, 154, 154, 154, 154, 154, 154, 154, 154, 132, -1, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 202, -1, 151, 154, 154, 154, 154, 154, 154, 154, 154, 152, -1, -1, -1, -1, -1, 200, -1, -1, -1, 200, -1},
                        {-1, -1, -1, -1, 151, 154, 154, 154, 154, 154, 154, 154, 154, 152, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, 151, 174, 174, 174, 174, 174, 174, 174, 174, 152, -1, 200, -1, -1, 202, -1, -1, -1, -1, 202, -1},
                        {200, -1, 204, -1, 171, -1, -1, -1, -1, -1, -1, -1, -1, 172, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, 202, -1, 200, -1, -1, 204, -1, -1, -1, -1, 204, -1, 202, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 204, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, 202, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 204, -1, -1, -1, 204, -1, -1, -1, -1},
                        {-1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 204, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 204, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 202, -1, -1, -1, 205, -1, -1, 204, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, 202, -1},
                        {-1, 202, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {204, -1, -1, -1, 200, -1, -1, -1, -1, -1, -1, 204, -1, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, 200, -1, -1, -1, 202, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, 202, -1, -1, -1},
                        {-1, 200, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, 202, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1},
                        {-1, -1, -1, -1, -1, -1, 204, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, -1, -1},
                        {-1, -1, 200, -1, -1, 200, -1, -1, -1, -1, -1, 200, -1, -1, -1, -1, -1, -1, -1, -1, -1, 200, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};
        }

        return null;
    }



}

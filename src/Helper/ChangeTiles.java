package Helper;

/**
 * Helper class to change an array into something that java can read
 */
public class ChangeTiles {

    public static void main(String[] args) {

        int mapLength = 10;

        int[] value = new int[]{0, 0, 194, 0, 0, 0, 0, 194, 0, 0, 0, 249, 0, 0, 249, 249, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 249, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        System.out.print("{");

        for(int i=0; i<value.length; i++) {
            if (i%mapLength == 0){
                System.out.print("{");
            }
            System.out.print(value[i] - 1);

            if (i == value.length - 1) {
                System.out.println("}");
            }else if (i%mapLength == mapLength-1){
                System.out.println("},");
            } else {
                System.out.print(", ");
            }
        }

        System.out.print("};");

    }



}
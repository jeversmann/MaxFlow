import java.util.*;
import java.util.regex.*;

public class DropZone {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int caseCount = in.nextInt();
        for(int testCount = 0; testCount < caseCount; testCount++) {
            int h = in.nextInt(), w = in.nextInt();
            int[][] field = new int[h][w];
            Pattern delim = in.delimiter();
            in.useDelimiter("");
            for(int y = 0; y < h; y++) {
                for(int x = 0; x < w; x++) {
                    switch (in.next().charAt(0)) {
                        case 'X':
                            field[y][x] = -1;
                            break;
                        case '.':
                            field[y][x] = 1;
                            break;
                        case 'D':
                            field[y][x] = 0;
                            break;
                        default:
                            x--;
                    }
                }
            }
            in.useDelimiter(delim);
            System.out.printf("Test case: %d\n", testCount);
            for(int[] row: field) {
                System.out.println(Arrays.toString(row));
            }
        }
    }
}

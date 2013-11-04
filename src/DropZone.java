import java.util.*;
import java.util.regex.*;

public class DropZone {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int caseCount = in.nextInt();
        for(int testCount = 0; testCount < caseCount; testCount++) {
            int h = in.nextInt(), w = in.nextInt(), n = 1;
            int[][] field = new int[h][w];
            Pattern delim = in.delimiter();
            in.useDelimiter("");
            for(int y = 0; y < h; y++) {
                for(int x = 0; x < w; x++) {
                    switch (in.next().charAt(0)) {
                        case 'X':
                            // -2 is impassable
                            field[y][x] = -2;
                            break;
                        case '.':
                            field[y][x] = n++;
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
            MaxFlowGraph zone = new MaxFlowGraph();
            for(int y = 0; y < h; y++) {
                for(int x = 0; x < w; x++) {
                    int node = field[y][x];
                    if(node != -2) {
                        int[] nexts = new int[4];
                        // -1 is the border of the field
                        nexts[0] = y == 0 ? -1 : field[y-1][x];
                        nexts[1] = x == 0 ? -1 : field[y][x-1];
                        nexts[2] = y == h - 1 ? -1 : field[y+1][x];
                        nexts[3] = x == w - 1 ? -1 : field[y][x+1];
                        for(int sides = 0; sides < 4; sides++) {
                            if(nexts[sides] != -2) {
                                zone.addEdge(node, nexts[sides], 1);
                            }
                        }
                    }
                }
            }
            System.out.println(zone.getFlow(0, -1));
        }
    }
}

import java.util.*;

public class MaxFlowGraph {

    private HashMap<Integer,Node> adjacency;

    public MaxFlowGraph() {
        adjacency = new HashMap<Integer,Node>();
    }

    public boolean addEdge(int left, int right, int weight) {
        Node lNode;
        Node rNode;
        if(adjacency.containsKey(left)) {
            lNode = adjacency.get(left);
        } else {
            lNode = new Node(left);
        }
        if(adjacency.containsKey(right)) {
            rNode = adjacency.get(right);
        } else {
            rNode = new Node(right);
        }
        return rNode.addEdge(left, weight) & lNode.addEdge(right, weight);
    }

    public int getFlow(int left, int right) {
        if(!adjacency.containsKey(left) || !adjacency.containsKey(right)) {
            return -1;
        }

        return 0;
    }

    private class Node {

        private HashMap<Integer,Integer> myEdges;
        private int index;

        public Node(int i) {
            index = i;
            myEdges = new HashMap<Integer, Integer>();
        }

        public boolean addEdge(int dest, int weight) {
            if(myEdges.containsKey(dest)) {
                return false;
            } else {
                myEdges.put(dest, weight);
                return true;
            }
        }

    }
}

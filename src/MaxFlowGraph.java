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
        boolean resp = rNode.addEdge(left, weight) & lNode.addEdge(right, weight);
        adjacency.put(left, lNode);
        adjacency.put(right, rNode);
        return resp;
    }

    public int getFlow(int left, int right) {
        if(!adjacency.containsKey(left) || !adjacency.containsKey(right)) {
            return -1;
        }
        // TODO The actual hard stuff goes here
        return 0;
    }


    public void print() {
        for(Node n: adjacency.values()) {
            System.out.println(n);
        }
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

        public String toString() {
            String resp = String.format("%d: [", index);
            for(Integer dest: myEdges.keySet()) {
                resp += " " + dest;
            }
            return resp + "]";
        }

    }
}

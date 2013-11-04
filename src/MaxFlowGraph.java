import java.util.*;

public class MaxFlowGraph {

    private HashMap<Integer,Node> myNodes;

    public MaxFlowGraph() {
        myNodes = new HashMap<Integer,Node>();
    }

    public boolean addEdge(int left, int right, int capacity) {
        Node lNode;
        Node rNode;
        if(myNodes.containsKey(left)) {
            lNode = myNodes.get(left);
        } else {
            lNode = new Node(left);
        }
        if(myNodes.containsKey(right)) {
            rNode = myNodes.get(right);
        } else {
            rNode = new Node(right);
        }
        boolean resp = rNode.addEdge(left, capacity) &
            lNode.addEdge(right, capacity);
        myNodes.put(left, lNode);
        myNodes.put(right, rNode);
        return resp;
    }

    public int getFlow(int left, int right) {
        if(!myNodes.containsKey(left) || !myNodes.containsKey(right)) {
            return -1;
        }
        List<Node.Edge> paths = getPath(left, right, new LinkedList<Node.Edge>());
        int flow = 0;
        while(paths.size() > 0) {
            int min_capacity = Integer.MAX_VALUE;
            for(Node.Edge edge: paths) {
                //System.out.print(edge.dest + " ");
                if(min_capacity > edge.capacity - edge.flow) {
                    min_capacity = edge.capacity - edge.flow;
                }
            }
            //System.out.println();
            if(min_capacity > 0) {
                for(Node.Edge edge : paths) {
                    edge.flow += min_capacity;
                }
            }
            flow += min_capacity;
            paths = getPath(left, right, new LinkedList<Node.Edge>());
        }
        return flow;
    }

    private List<Node.Edge> getPath(int left, int right, List<Node.Edge> previous) {
        if(!myNodes.containsKey(left) || !myNodes.containsKey(right)) {
            return previous;
        }
        Node lNode = myNodes.get(left);
        for(Integer mid: lNode.getDestinations()) {
            Node.Edge next = lNode.getEdge(mid);
            if(next.capacity - next.flow == 0) {
                continue;
            }
            if(mid == right) {
                previous.add(next);
                break;
            } else {
                boolean unused = true;
                for(Node.Edge e : previous) {
                    if(e.dest == mid || e.prev == mid) {
                        unused = false;
                    }
                }
                if(unused) {
                    List<Node.Edge> prevClone = new LinkedList<Node.Edge>(previous);
                    prevClone.add(next);
                    List<Node.Edge> resulting = getPath(mid, right, prevClone);
                    if(resulting.get(resulting.size() - 1).dest == right) {
                        return resulting;
                    }
                }
            }
        }
        return previous;
    }

    public String toString() {
        String resp = "";
        for(Node n: myNodes.values()) {
            resp += n.toString();
        }
        return resp;
    }

    private class Node {

        public class Edge {

            public int capacity;
            public int flow = 0;
            public int dest;
            public int prev;

            public Edge(int c, int d, int p) {
                capacity = c;
                dest = d;
                prev = p;
            }
        }

        private HashMap<Integer,Edge> myEdges;
        private int index;

        public Node(int i) {
            index = i;
            myEdges = new HashMap<Integer,Edge>();
        }

        public boolean addEdge(int dest, int capacity) {
            if(myEdges.containsKey(dest)) {
                return false;
            } else {
                myEdges.put(dest, new Edge(capacity, dest, index));
                return true;
            }
        }

        public Edge getEdge(int dest) {
            return myEdges.get(dest);
        }

        public Collection<Integer> getDestinations() {
            return myEdges.keySet();
        }

        public void clear() {
            for(Edge e: myEdges.values()) {
                e.flow = 0;
            }
        }

        public String toString() {
            String resp = String.format("%d: [", index);
            for(Integer dest: myEdges.keySet()) {
                resp += " " + dest;
            }
            return resp + "]\n";
        }

    }
}

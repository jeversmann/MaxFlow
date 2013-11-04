import java.util.*;

public class MaxFlowGraph {

    private HashMap<Integer,Node> myNodes;

    public MaxFlowGraph() {
        myNodes = new HashMap<Integer,Node>();
    }

    public void addEdge(int left, int right, int capacity) {
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
        //Edge insertion is no longer symmetric
        lNode.addEdge(right, capacity);
        myNodes.put(left, lNode);
        myNodes.put(right, rNode);
    }

    public int getFlow(int left, int right) {
        if(!myNodes.containsKey(left) || !myNodes.containsKey(right)) {
            return -1;
        }
        List<Node.Edge> paths = getPath(left, right);
        int flow = 0;
        while(paths.size() > 0) {
            int min_capacity = Integer.MAX_VALUE;
            for(Node.Edge edge: paths) {
                if(min_capacity > edge.capacity - edge.flow) {
                    min_capacity = edge.capacity - edge.flow;
                }
            }
            if(min_capacity > 0) {
                for(Node.Edge edge : paths) {
                    edge.flow += min_capacity;
                }
            }
            flow += min_capacity;
            paths = getPath(left, right);
        }
        for(Node n: myNodes.values()) {
            n.clear();
        }
        return flow;
    }

    private List<Node.Edge> getPath(int left, int right) {
        List<Node.Edge> start = new LinkedList<Node.Edge>();
        if(!myNodes.containsKey(left) || !myNodes.containsKey(right)) {
            return start;
        }
        Queue<List<Node.Edge>> state = new LinkedList<List<Node.Edge>>();
        state.add(start);
        HashMap<Integer,Boolean> used = new HashMap<Integer, Boolean>();
        while(!state.isEmpty()) {
            List<Node.Edge> previous = state.remove();
            Node lNode;
            if(previous.isEmpty()) {
                lNode = myNodes.get(left);
                used.put(left,true);
            } else {
                lNode = myNodes.get(previous.get(previous.size() - 1).dest);
            }
            for(Node.Edge next: lNode.getEdges()) {
                if(next.capacity - next.flow == 0) {
                    continue;
                }
                if(next.dest == right) {
                    previous.add(next);
                    return previous;
                } else if(!used.containsKey(next.dest)) {
                    List<Node.Edge> prevClone = new LinkedList<Node.Edge>(previous);
                    prevClone.add(next);
                    state.add(prevClone);
                    used.put(next.dest,true);
                }
            }
        }
        return start;
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

        private Set<Edge> myEdges;
        private int index;

        public Node(int i) {
            index = i;
            myEdges = new HashSet<Edge>();
        }

        public void addEdge(int dest, int capacity) {
            myEdges.add(new Edge(capacity, dest, index));
        }

        public Collection<Edge> getEdges() {
            Collection<Edge> resp = new LinkedList<Edge>();
            for(Edge e: myEdges) {
                resp.add(e);
            }
            return resp;
        }

        public void clear() {
            for(Edge e: myEdges) {
                e.flow = 0;
            }
        }

        public String toString() {
            String resp = String.format("%d: [", index);
            for(Edge e: myEdges) {
                resp += " " + e.dest;
            }
            return resp + "]\n";
        }

    }
}

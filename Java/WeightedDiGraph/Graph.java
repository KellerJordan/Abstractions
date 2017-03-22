// Weighted DiGraph ADT

@SuppressWarnings("overrides")
class Graph {
    private static final int INF = 10000000; // don't use edge weights of greater than 1000000 pls
    private static final int NIL = 0;

    private class Edge {
        private int origin, terminus;
        private double weight;

        Edge(int origin, int terminus, double weight) {
            this.origin = origin;
            this.terminus = terminus;
            this.weight = weight;
        }

        public String toString() {
            return String.format("(%d,%d)w%.2f", origin, terminus, weight);
        }
    }
    private List edges[];       // array of LinkedList ADT which store Edge objects
    private int p[];            // parent arrays
    private double d[];         // distance from source array
    private int order, size;    // |V(G)|, |E(G)|
    private int source;         // source vertex for use in BellmanFord, Dijkstra algorithms

    Graph(int order) {
        this.order = order;
        int len = order + 1;
        this.edges = new List[len];
        for (int i = 1; i <= order; i++)
            edges[i] = new List();
        this.p = new int[len];
        this.d = new double[len];
    }

    public int getOrder() { return order; }
    public int getSize() { return size; }
    public int getSource() { return source; }
    public int getParent(int vertex) { return p[vertex]; }
    public double getDistance(int vertex) { return d[vertex]; }

    public void addEdge(int origin, int terminus, double weight) {
        if (origin < 1 || order < origin)
            throw new RuntimeException("Graph Error: addEdge() called with out of bounds origin");
        if (terminus < 1 || order < terminus)
            throw new RuntimeException("Graph Error: addEdge() called with out of bounds terminus");
        
        Edge E = new Edge(origin, terminus, weight);
        List L = edges[origin];
        for (L.moveFront(); L.index() != -1; L.moveNext()) {
            Edge F = (Edge)L.get();
            if (F.terminus > terminus) {
                L.insertBefore(E);
                return;
            }
        }
        L.append(E);
        size++;
    }

    private double getWeight(int origin, int terminus) {
        if (origin < 1 || order < origin)
            throw new RuntimeException("Graph Error: private getWeight() called with out of bounds origin");
        if (terminus < 1 || order < terminus)
            throw new RuntimeException("Graph Error: private getWeight() called with out of bounds terminus");

        List L = edges[origin];
        for (L.moveFront(); L.index() != -1; L.moveNext()) {
            Edge E = (Edge)L.get();
            if (E.terminus == terminus)
                return E.weight;
        }
        return INF;
    }
    private void Initialize(int s) {
        source = s;
        for (int i = 1; i <= order; i++) {
            p[i] = NIL;
            d[i] = INF;
        }
        d[source] = 0;
    }
    private void Relax(Edge E) {
        if (d[E.origin] != INF) {
            double newWeight = d[E.origin] + E.weight;
            if (newWeight < d[E.terminus])
                d[E.terminus] = newWeight;
        }
    }

    // only works for all positive weights
    public void Dijkstra(int s) {
        Initialize(s);
        List S = new List();
        // Relax();
    }
    // only works if no negative cycles
    public void BellmanFord(int s) {
        Initialize(s);
        for (int iter = 1; iter <= order; iter++) { // execute the following |V(G)| times
            // for each E in E(G), visit(E)
            for (int index = 1; index <= order; index++) {
                List L = edges[index];
                for (L.moveFront(); L.index() != -1; L.moveNext()) {
                    Edge E = (Edge)L.get();
                    Relax(E);
                }
            }
        }
        // check for neg loops
    }

    public void printDistances() {
        for (int i = 1; i <= order; i++)
            System.out.println(String.format("For v=%d, p=%d, d=%.2f", i, p[i], d[i]));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("Graph with order=%d, size=%d, edges=", order, size));
        for (int i = 1; i <= order; i++) {
            List L = edges[i];
            sb.append(String.format("\n%d: %s", i, L.toString()));
        }
        return new String(sb);
    }
}
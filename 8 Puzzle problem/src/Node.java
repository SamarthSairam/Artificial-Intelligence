package sxs200378;
import java.util.List;
public class Node {
	Node parent;
    private int tc, deep;
    List<List<String>> state;
    private Action action;
    private int[] emptypos;

    Node(List<List<String>> state, int pc, int deep, Node parent) {
        this.state = state;
        this.deep = deep;
        this.tc = pc;
        this.parent = parent;
        emptypos = new int[2];
    }

    Node(List<List<String>> state, int pathCost, int depth, Node parent, Action action) {
        this(state, pathCost, depth, parent);
        this.action = action;
    }
    public int getcost() {
        return tc;
    }

    public void sdepth(int depth) {
        this.deep = depth;
    }
    public int gdepth() {
        return deep;
    }
    public void totalCost(int pathCost) {
        this.tc = pathCost;
    }
    public int[] getpos() {
        return emptypos;
    }
    public String getact() {
        return action.name();
    }
    public void setpos(int row, int col) {
        emptypos[0] = row;
        emptypos[1] = col;
    }

	
	

}

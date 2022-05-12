package sxs200378;
import java.util.*;
import java.util.List;
public class Main {
	private boolean Checkgoal(List<List<String>> state) {
        for(int i = 0; i < 8; i++) {
            String ip = state.get(i/3).get(i%3);
            String goal = String.valueOf(i+1);
            if(!ip.equals(goal))
                return false;

        }
        return true;
    }

    private List<List<String>> copyParent(List<List<String>> parent) {
        List<List<String>> cstate = new ArrayList<>();
        for(List<String> row: parent) {
            cstate.add(new ArrayList<>(row));
        }
        return cstate;
    }

   private boolean checkState(List<List<String>> cstate, Stack<Node> stack) {
        for(Node nd: stack) {
            if(cstate.equals(nd.state))
                return true;
        }
        return false;
    }
    private Node produceNode(Node parent, Action act) {
        int[] emptyPos = parent.getpos();
        if((emptyPos[0] == 0 && act == Action.UP) || (emptyPos[0] == 2 && act == Action.DOWN)
                || (emptyPos[1] == 0 && act == Action.LEFT) || (emptyPos[1] == 2 && act == Action.RIGHT)) {
            return null;
        }
        int childBlock, emptystate;
        List<List<String>> parentst = parent.state;
        List<List<String>> childst = copyParent(parentst);
        String tile;
        if(act==Action.LEFT) {
        	tile = parentst.get(emptyPos[0]).get(emptyPos[1]-1);
            childst.get(emptyPos[0]).set(emptyPos[1]-1, "*");
            childBlock = emptyPos[0];
            emptystate = emptyPos[1]-1;
        }
        else if(act==Action.RIGHT) {
        	tile = parentst.get(emptyPos[0]).get(emptyPos[1]+1);
            childst.get(emptyPos[0]).set(emptyPos[1]+1, "*");
            childBlock = emptyPos[0];
            emptystate = emptyPos[1]+1;
        }
        else if(act==Action.UP) {
        	 tile = parentst.get(emptyPos[0]-1).get(emptyPos[1]);
             childst.get(emptyPos[0]-1).set(emptyPos[1], "*");
             childBlock = emptyPos[0]-1;
             emptystate = emptyPos[1];
        	
        }
        else {
        	 tile = parentst.get(emptyPos[0]+1).get(emptyPos[1]);
             childst.get(emptyPos[0]+1).set(emptyPos[1], "*");
             childBlock = emptyPos[0]+1;
             emptystate = emptyPos[1];
        	
        }
       
        childst.get(emptyPos[0]).set(emptyPos[1], tile);
        Node child = new Node(childst, parent.getcost()+1, parent.gdepth()+1, parent, act);
        child.setpos(childBlock, emptystate);
        return child;
    }
    private HashMap<String, List<Integer>> getGoal() {
        HashMap<String, List<Integer>> FinalState = new HashMap<>();
        for(int i = 0; i < 8; i++) {
            List<Integer> block = new ArrayList<>();
            block.add(i/3);
            block.add(i%3);
            FinalState.put(String.valueOf(i+1), block);
        }
        return FinalState;
    }

    
    
    private int h1(List<List<String>> game) {
        int mds = 0;
        HashMap<String, List<Integer>> goal = getGoal();
        for(int i = 0; i < 9; i++) {
            String block = game.get(i/3).get(i%3);
            if(!block.equals("*")) {
                List<Integer> blockpos = goal.get(block);
                mds += Math.abs(blockpos.get(0)-i/3) + Math.abs(blockpos.get(1)-i%3);
            }
        }
        return mds;
    }

    private int h2(List<List<String>> game) {
        String block;
        int miss = 0;
        for(int i = 0; i < 8; i++) {
            block = game.get(i/3).get(i%3);
            if(!block.equals("*") && !block.equals(String.valueOf(i+1))) {
                miss++;
            }
        }
        block = game.get(2).get(2);
        if(!block.equals("*")) {
            miss++;
        }
        return miss;
    }
    private int dfs(Node input) {
    	if(Checkgoal(input.state)) {
    		display(input);
    		return -1;
    	}
    	
    	Stack<Node> stack = new Stack<Node>(); 
    	stack.add(input);
    	HashSet<List<List<String>>> exp = new HashSet<>();
    	while(!stack.isEmpty()) {
    		Node help=stack.peek();
    		if(help.gdepth()<15) {
    			exp.add(help.state);
    			for(Action act: Action.values()) {
    				Node child = produceNode(help,act);
    				if(child!=null && !exp.contains(child.state) && !checkState(child.state, stack)) {
    					if(Checkgoal(child.state)) {
    						System.out.println("Total states enqueued is "+exp.size());
    						display(child);
    						return 1;
    					}
    					stack.add(child);
    				}
    			}
    			
    		}
    		}
    	System.out.println("Could not find the goal state using the dfs technique");
    	return -1;
    	
    }
    private Node dls(Node ip, int[] senq,int deep) {
        if(Checkgoal(ip.state)) {
            return ip;
        }
        else if(ip.gdepth() == deep) {
            return null;
        }
        else {
            senq[0]++;
            for(Action j: Action.values()) {
                Node child = produceNode(ip, j);
                Node help = null;
                if(child != null)
                    help = dls(child,senq, deep);
                if(help != null)
                    return help;
            }
            return null;
        }
    }


    private int ids(Node ip) {
        int senq[] = new int[1];
        for(int i = 0; i < 15; i++) {
            Node res = dls(ip,senq, i);
            if(res != null) {
                System.out.println("Total states that were enqueued "+senq[0]);
                display(res);
                return 1;
            }
        }
        System.out.println("Could not find the goal state using the ids technique");
        return -1;
    }
    private int Astar(Node ip, String search) {
        if(Checkgoal(ip.state)) {
            display(ip);
            return 1;
        }
        int senq = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getcost));
        queue.add(ip);
        int ocost = Integer.MAX_VALUE;
        int cost;
        Node optimal = null;
        do {
            Node help = queue.remove();
            cost = help.getcost();
            if(cost > ocost)
                break;
            if(help.gdepth() < 15) {
                senq++;
                for(Action j: Action.values()) {
                    Node child = produceNode(help, j);
                    if (child != null) {
                        int pcost = child.gdepth();
                        int heurcost = search.equals("astar1")? h2(child.state): h1(child.state);
                        child.totalCost(pcost+heurcost);
                        if (Checkgoal(child.state)) {
                            ocost = child.getcost();
                            optimal = child;
                        } else {
                            queue.add(child);
                        }
                    }
                }
            }
        } while(queue.size() != 0);
        if(optimal != null) {
            System.out.println("Total states that were enqueued "+senq);
            display(optimal);
            return 1;
        }

        return -1;
    }
    private void Astar1(Node ip) {
        ip.totalCost(h2(ip.state));
        if(Astar(ip, "Astar1") == -1)
            System.out.println("Could not reach the goal state with Astar1 search");
    }

    private void Astar2(Node ip) {
        ip.totalCost(h1(ip.state));
        if(Astar(ip, "astar2") == -1)
            System.out.println("Could not reach the goal state with Astar2 search");
    }
    private void tracePath(Node goalnd) {
        if(goalnd == null)
            return;
        tracePath(goalnd.parent);
        List<List<String>> pstate = goalnd.state;
        if(goalnd.parent != null)
            System.out.println("Action: "+goalnd.getact());
        System.out.println("------------------------");
        for(List<String> row: pstate) {
            for(String block: row) {
                System.out.print(block+" ");
            }
            System.out.println();
        }
    }
    private void display(Node goalnd) {
        System.out.println("Moves Taken "+goalnd.gdepth());
        System.out.println();
        System.out.println("Tracing path to the Goal state...");
        tracePath(goalnd);
    }

    public static void main(String[] args) {
        if(args.length == 0) 
        {
            System.out.println("Select the argument you wish to use\n" +
                    "1. Depth First Search\n" +
                    "2. Iterative deepening search\n" +
                    "3. Astar1\n" +
                    "4. Astar2\n");
            return;
        }
        Scanner in = new Scanner(System.in);
        List<List<String>> input = new ArrayList<>();
        System.out.println("Enter the input in the form of 3x3 Matrix row by row");
        int rowblock = 0, colblock = 0;
        for(int m = 0; m < 3; m++) {
           List<String> r = new ArrayList<>();
            for(int p = 0; p < 3; p++) {
                String block = in.next();
                if(block.equals("*")) {
                    rowblock = m;
                    colblock = p;
                }
                r.add(block);
            }
            input.add(r);
        }
        Node s = new Node(input, 0, 0, null);
        s.setpos(rowblock, colblock);
        Main m = new Main();
        switch (args[0]) {
            case "dfs":
                m.dfs(s);
                break;
            case "ids":
                m.ids(s);
                break;
            case "Astar1":
                m.Astar1(s);
                break;
            case "Astar2":
                m.Astar2(s);
                break;
            default:
                System.out.println("Wrong input!");

        }}


}

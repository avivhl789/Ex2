package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import api.node_data;
import ex2.DWGraph_Algo;
import ex2.DWGraph_DS;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable {
    private static EnterScreen test;
    private static MyFrame _win;
    private static Arena _ar;
    private static DWGraph_Algo algo = new DWGraph_Algo();
    private static HashMap<Integer, HashMap<Integer, CL_Agent.PathHelper>> fullgrmaping = new HashMap<>();
    private static long dt = 100;
    private static CL_Agent slowest = new CL_Agent();


    public static void main(String[] a) {
        EnterScreen gui = new EnterScreen();
        test = gui;
        while (!gui.getStartGame()) {
            System.out.print("");
        }

        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        int scenario_num = test.getlevel();
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        int id = test.getid();
        //	game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        algo.loadfromstirng(g);
        directed_weighted_graph gg = algo.getGraph();
        HashMap<Integer, node_data> grmap = ((DWGraph_DS) gg).gethashmap();
        for (Integer src : grmap.keySet()) {
            fullgrmaping.put(src, new HashMap<Integer, CL_Agent.PathHelper>());
            for (Integer dest : grmap.keySet()) {
                fullgrmaping.get(src).put(dest, algo.clientShortestPath(src, dest));
            }
        }


        init(game);
        game.startGame();
        System.out.println(game.timeToEnd() / 1000);
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind = 0;

        while (game.isRunning()) {
            if (!slowest.isMoving())
                moveAgants(game, gg);
            try {
                if (ind % 1 == 0) {
                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(@NotNull game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        //System.out.println(lg);
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);

        for (int i = 0; i < log.size(); i++) {
            for (int j = 0; j < ffs.size(); j++) {
                CL_Agent ag = log.get(i);
                if (!ag.isMoving()) {
                    CL_Pokemon pok = ffs.get(j);
                    _ar.updateEdge(pok, _ar.getGraph());
                    CL_Agent.PathHelper curr = fullgrmaping.get(ag.getSrcNode()).get(pok.get_edge().getSrc());
                    curr.setTotalCost(curr.getTotalCost() / ag.getSpeed());
                    curr.setpoke(pok);
                    if (curr.getThePath().size() != 0) {
                        if (curr.getThePath().get(curr.getThePath().size() - 1) != gg.getNode(pok.get_edge().getDest()))
                            curr.getThePath().add(gg.getNode(pok.get_edge().getDest()));
                    } else
                        curr.getThePath().add(gg.getNode(pok.get_edge().getDest()));
                    ag.getPathCompare().add(curr);
                }
            }
        }

        HashMap<CL_Pokemon, CL_Agent> pair = new HashMap<>();
        HashMap<CL_Pokemon, CL_Agent> bastpair = new HashMap<>();
        double max = -1;
        double minmax = Double.MAX_VALUE;
        for (int i = 0; i < log.size(); i++) {
            for (int j = 0; j < log.size(); j++) {
                int overflow = (i + j) % log.size();
                CL_Agent ag = log.get(overflow);
                if (!ag.getPathCompare().isEmpty()){
                PriorityQueue<CL_Agent.PathHelper> clone = new PriorityQueue<>();
                CL_Agent.PathHelper curr = ag.getPathCompare().poll();
                    clone.add(curr);
                    while (pair.containsKey(curr.getPoke()) && !ag.getPathCompare().isEmpty()) {
                        curr = ag.getPathCompare().poll();
                        clone.add(curr);
                    }
                    ag.getPathCompare().add(curr);
                    pair.put(curr.poke, ag);
                    max = Math.max(max, curr.getTotalCost());
                    while (!clone.isEmpty()) {
                        ag.getPathCompare().add(clone.poll());
                    }}

            }
            if (max < minmax) {
                minmax = max;
                bastpair = (HashMap<CL_Pokemon, CL_Agent>) pair.clone();
            }
            pair.clear();
            max = -1;
        }

        for (int i = 0; ffs.size() > i; i++) {
            CL_Agent ag = bastpair.get(ffs.get(i));
            if(ag!=null) {
                int id = ag.getID();
                int dest = ag.getNextNode();
                int src = ag.getSrcNode();
                double v = ag.getValue();
                ag.setBastpath(fullgrmaping.get(src).get(ffs.get(i).get_edge().getSrc()).getThePath());
                if (dest == -1 || !ag.isMoving()) {
                    dest = nextNode(ag);
                    game.chooseNextEdge(id, dest);
                    edge_data edge=algo.getGraph().getEdge(src, dest);
                    if(edge!=null) {
                        dt = (long) (150 * (edge.getWeight() / ag.getSpeed()));
                    }
                    dt= (long) Math.max(60.0,dt); //need it?
                    System.out.println(dt);
                    slowest = ag;

                    //  System.out.println("Agent: " + id + ", val: " + v + "   turned to node: "
                    //        + dest +"time left for game: " + game.timeToEnd()/1000);
                } else
                    System.out.println("called to fast");
            }
        }
    }


    private static int nextNode(@NotNull CL_Agent ag) {
        if (ag.getBastpath().size() >= 1) {
            if (ag.getSrcNode() == ag.getBastpath().get(0).getKey())
                ag.getBastpath().remove(0);
            node_data n = ag.getBastpath().get(0);
            return n.getKey();
        }
        return -1;
    }

    private void init(@NotNull game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        AgentsSetStartPlace(game);
    }

    private void AgentsSetStartPlace(game_service game) {
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int numOfAgents = ttt.getInt("agents");

            ArrayList<CL_Pokemon> pokedex = (ArrayList<CL_Pokemon>) _ar.getPokemons();
            HashMap<Integer, HashSet<Integer>> egdes = new HashMap<Integer, HashSet<Integer>>();
            HashSet freenode = new HashSet<Integer>(((DWGraph_DS) algo.getGraph()).gethashmap().keySet());

            for (int i = 0; i < pokedex.size(); i++) {
                _ar.updateEdge(pokedex.get(i), _ar.getGraph());
            }
            for (int i = 0; i < numOfAgents; i++) {
                if (i < pokedex.size()) {
                    CL_Pokemon pok = pokedex.get(i);
                    int startNode = pok.get_edge().getSrc();
                    int destNode = pok.get_edge().getDest();
                    if (!egdes.containsKey(startNode) || !egdes.get(startNode).contains(destNode)) {
                        if (!egdes.containsKey(startNode))
                            egdes.put(startNode, new HashSet<Integer>());
                        egdes.get(startNode).add(destNode);
                        game.addAgent(startNode);
                        game.chooseNextEdge(i, destNode);
                        freenode.remove(startNode);
                    } else {
                        int freen;
                        if (freenode.size() > 0) {
                            freen = (int) freenode.iterator().next();
                            freenode.remove(freen);
                        } else
                            freen = ((DWGraph_DS) algo.getGraph()).gethashmap().keySet().iterator().next();
                        game.addAgent(freen);
                    }

                } else {
                    int freen;
                    if (freenode.size() > 0) {
                        freen = (int) freenode.iterator().next();
                        freenode.remove(freen);
                    } else
                        freen = ((DWGraph_DS) algo.getGraph()).gethashmap().keySet().iterator().next();
                    game.addAgent(freen);

                }

            }
            if (pokedex.size() > numOfAgents) {
                for (int i = numOfAgents; i < pokedex.size(); i++) {
                    CL_Pokemon pok = pokedex.get(i);
                    int startNode = pok.get_edge().getSrc();
                    int destNode = pok.get_edge().getDest();

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

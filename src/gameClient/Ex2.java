package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import ex2.DWGraph_Algo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable {
    private static EnterScreen test;
    private static MyFrame _win;
    private static Arena _ar;
    private static DWGraph_Algo algo = new DWGraph_Algo();


    public static void main(String[] a) {
        EnterScreen gui = new EnterScreen();
        test = gui;
        while (!gui.getStartGame()) {
            System.out.println("");
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
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        algo.init(gg);
        init(game);
        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
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
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);
        for (int i = 0; i < log.size(); i++) {
            for (int j = 0; j < ffs.size(); j++) {
                log.get(i).path(algo, ffs.get(j));
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
                CL_Agent.PathHelper curr = ag.getPathCompare().poll();
                while (pair.containsKey(curr.getPoke())) {
                    curr = ag.getPathCompare().poll();
                }
                ag.getPathCompare().add(curr);
                pair.put(curr.poke, ag);
                max = Math.max(max, curr.getTotalCost());
            }
            if (max < minmax) {
                minmax = max;
                bastpair = (HashMap<CL_Pokemon, CL_Agent>) pair.clone();
            }
            max = -1;
        }
        for(CL_Pokemon p : bastpair.keySet())
        {
           bastpair.get(p).setNextNode(p.get_edge().getSrc());
        }

        CL_Agent ag = log.get(i);
        int id = ag.getID();
        int dest = ag.getNextNode();
        int src = ag.getSrcNode();
        double v = ag.getValue();
        if (dest == -1) {
            dest = nextNode(gg, src);
            game.chooseNextEdge(ag.getID(), dest);
            System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);

        }
    }


    /**
     * a very simple random walk implementation!
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int) (Math.random() * s);
        int i = 0;
        while (i < r) {
            itr.next();
            i++;
        }
        ans = itr.next().getDest();
        return ans;
    }

    private void init(game_service game) {
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
                    } else {

                    }

                } else {

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

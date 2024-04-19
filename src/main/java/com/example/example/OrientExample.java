package com.example.example;

import com.tinkerpop.blueprints.impls.orient.*;

public class OrientExample {

    public static void main(String[] args) {

        // Connect to local orientdb instance
        OrientGraphFactory factory = new OrientGraphFactory("plocal:C:/Users/Julian/dockermnt/orientdb/databases/test", "root", "root").setupPool(1, 10);
        OrientGraphNoTx graph = factory.getNoTx();


            try {

                // Create custom schemas
                OrientVertexType account = graph.createVertexType("Account");
                OrientVertexType person = graph.createVertexType("Person");

                OrientEdgeType knows = graph.createEdgeType("KNOWS");
                OrientEdgeType holds = graph.createEdgeType("HOLDS");

                // Save schemas
                graph.commit();


                // Start new transaction
                graph.begin();

                // Create custom vertices
                OrientVertex luca = graph.addVertex("class:Person");
                luca.setProperty( "name", "Luca" );
                luca.setProperty( "age", 22 );

                OrientVertex marko = graph.addVertex("class:Person",
                        "name", "Marko",
                        "age", 26
                );

                OrientVertex kim = graph.addVertex("class:Person",
                        "name", "Kim",
                        "age", 24
                );

                OrientVertex markosAccount = graph.addVertex("class:Account",
                        "credit", 3500,
                        "since", 2012
                );

                OrientVertex kimsAccount = graph.addVertex("class:Account",
                        "creadit", 2000,
                        "since", 2018
                );


                // Create custom edges
                OrientEdge lucaKnowsMarko = graph.addEdge("class:KNOWS", luca, marko, "KNOWS");
                OrientEdge lucaKnowsKim = graph.addEdge("class:KNOWS", luca, kim, "KNOWS");
                OrientEdge kimKnowsMarko = graph.addEdge("class:KNOWS", kim, marko, "KNOWS");
                OrientEdge markoKnowsLuca = graph.addEdge("class:KNOWS", marko, luca, "KNOWS");
                OrientEdge markoHoldsMarkosAccount = graph.addEdge("class:HOLDS", marko, markosAccount, "HOLDS");
                OrientEdge kimHoldsKimsAccount = graph.addEdge("class:HOLDS", kim, kimsAccount, "HOLDS");


                // Save graph data
                graph.commit();

            } finally {

                // Close connections
                graph.shutdown(true);
                factory.close();

            }

    }

}

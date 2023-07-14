select c.name as user, c.id as client_id, g.id as graph_id, n.name as node, n.id as node_id, n.x_coord, n.y_coord, n.paths_id, p.distance, n.routes_index,
    e.source_node_id as source_id, e.destination_node_id as destin_id, e.distance as length from Clients c
    left join Graphs g
    on c.id = g.clients_id
    left join nodes n
    on g.id = n.graphs_id
    left join edges e
    on n.id = e.source_node_id
    left join paths p
    on g.id = p.graphs_id;
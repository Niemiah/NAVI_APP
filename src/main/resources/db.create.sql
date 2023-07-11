-- create with cascading delete on Client

CREATE TABLE IF NOT EXISTS Clients (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(25) NOT NULL);
CREATE TABLE IF NOT EXISTS Graphs (Id INT PRIMARY KEY AUTO_INCREMENT, Clients_Id INT, FOREIGN KEY (Clients_id) REFERENCES Clients(Id) ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS Nodes (Id INT PRIMARY KEY AUTO_INCREMENT, Graphs_id INT, X_Coord INT, Y_Coord INT, Name VARCHAR(25), Paths_Id INT, Routes_Index INT, FOREIGN KEY (Graphs_id) REFERENCES Graphs(Id) ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS Edges (Id INT PRIMARY KEY AUTO_INCREMENT, Source_Node_id INT, Destination_Node_Id INT, Distance INT, FOREIGN KEY (Source_node_id) REFERENCES Nodes(Id) ON DELETE CASCADE);
CREATE TABLE IF NOT EXISTS Paths (Id INT PRIMARY KEY AUTO_INCREMENT, Source_Node_id INT, Target_Node_id INT, Distance INT, Graphs_id INT, FOREIGN KEY (Graphs_id) REFERENCES Graphs(Id) ON DELETE CASCADE);


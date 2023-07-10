package user;
import bin.Path;
import bin.Node;

public interface IUserOutputService {
    void displayWelcomeMessage(); //Displays a welcome message
    void displayGraph(); //Displays the graph
    void promptSourceNode(); //Prompts the user to select a source node
    void promptDestinationNode(); //Prompts the user to select a destination node
    void displayRoute(Path path); //Displays the shortest path from the source node to the destination node
    void displayGoodbyeMessage(); //Displays a goodbye message
}

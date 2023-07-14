package com.solvd.naviapp.utilities;
import com.solvd.naviapp.bin.Node;
import java.util.List;

public final class Utilities {
    public static void nodeSort(List<Node> nodeList) {
        for (int i = 0; i < nodeList.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nodeList.size(); j++) {
                if (Integer.parseInt(nodeList.get(j).getName()) < Integer.parseInt(nodeList.get(minIndex).getName())) {
                    minIndex = j;
                }
            }
            Node temp = nodeList.get(minIndex);
            nodeList.set(minIndex, nodeList.get(i));
            nodeList.set(i, temp);
        }
    }
}

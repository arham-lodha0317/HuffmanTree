import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Driver {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new File("Text Files/A Tale of Two Cities.txt"));

        HashMap<String, Integer> frequency = new HashMap<>();

        PriorityQueue<Node> HuffmanTree = new PriorityQueue<>();

        HashMap<String, Entry> encodingScheme = new HashMap<>();

        frequency.put("eof", 1);

        while (scanner.hasNext()) {

            String[] line = scanner.nextLine().split("");

            for (String value : line) {

                if(frequency.containsKey(value)){
                    int num = frequency.remove(value) + 1;
                    frequency.put(value, num);
                }
                else {
                    frequency.put(value, 1);
                }

            }

            if(frequency.containsKey("\n")){
                int num = frequency.remove("\n") + 1;
                frequency.put("\n", num);
            }
            else {
                frequency.put("\n", 1);
            }

        }

        Set<Map.Entry<String, Integer>> entries = frequency.entrySet();

        entries.forEach(n -> HuffmanTree.add(new Node(n.getValue(), n.getKey())));

        while (HuffmanTree.size() > 1){
            Node left = HuffmanTree.remove();
            Node right = HuffmanTree.remove();
            Node newNode = new Node(left, right);
            left.setParent(newNode);
            right.setParent(newNode);
            HuffmanTree.add(newNode);
        }

        Node tree = HuffmanTree.remove();

        createScheme(encodingScheme, tree);

        System.out.println(encodingScheme);

        scanner.close();

        Scanner scanner2 = new Scanner(new File("Text Files/A Tale of Two Cities.txt"));

        BitOutputStream outputStream = new BitOutputStream(new FileOutputStream(new File("Compressed Text/tale.binary")));

        while (scanner2.hasNext()){

            String[] line = scanner2.nextLine().split("");

            if(line.length != 1){
                for (String s : line) {
                    Entry entry = encodingScheme.get(s);
                    outputStream.writeBits(entry.getCount(), entry.getValue());
                }
            }

            outputStream.writeBits(encodingScheme.get("\n").getCount(), encodingScheme.get("\n").getValue());

        }

        outputStream.writeBits(encodingScheme.get("eof").getCount(), encodingScheme.get("eof").getValue());
        outputStream.close();

//         Decompression


        BitInputStream inputStream = new BitInputStream(new File("Compressed Text/tale.binary"));
        Node currentNode = tree;

        while (true){

            int value = inputStream.readBits(1);
            if(value == 0){
                currentNode = currentNode.getLeft();
                if(!currentNode.getCharacter().equals("")){
                    if(currentNode.getCharacter().equals("eof")){
                        break;
                    }

                    System.out.print(currentNode.getCharacter());
                    currentNode = tree;
                }
            }
            else if(value == 1) {
                currentNode = currentNode.getRight();
                if(!currentNode.getCharacter().equals("")){
                    if(currentNode.getCharacter().equals("eof")){
                        break;
                    }

                    System.out.print(currentNode.getCharacter());
                    currentNode = tree;
                }
            }

        }


    }

    public static void createScheme(HashMap<String, Entry> encodingScheme, Node node){
        if(node == null){
            return;
        }
        if(!node.getCharacter().equals("")){
            encodingScheme.put(node.getCharacter(), new Entry(node.getMoves(), node.getCharacter()));
        }

        AddLeftMove(node.getLeft());
        AddRightMove(node.getRight());

        createScheme(encodingScheme, node.getLeft());
        createScheme(encodingScheme, node.getRight());
    }

    private static void AddLeftMove(Node node){
        if(node == null){
            return;
        }

        if(!node.getCharacter().equals("")){
            node.leftMove();
            return;
        }

        AddLeftMove(node.getLeft());
        AddLeftMove(node.getRight());
    }

    private static void AddRightMove(Node node){
        if(node == null){
            return;
        }

        if(!node.getCharacter().equals("")){
            node.rightMove();
            return;
        }

        AddRightMove(node.getLeft());
        AddRightMove(node.getRight());
    }

}

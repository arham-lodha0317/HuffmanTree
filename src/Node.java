
public class Node implements Comparable<Node>{

    private int frequency;
    private String character = "";
    private Node parent;
    private Node left;
    private Node right;
    private int size;
    private StringBuilder moves = new StringBuilder();

    public Node(int freq, String character) {
        this.frequency = freq;
        this.character = character;
        size = 1;
    }

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.frequency = left.frequency + right.frequency;
        this.size = left.getSize() + right.getSize();
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getCharacter() {
        return character;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void leftMove(){
        moves.append("0");
    }

    public void rightMove(){
        moves.append("1");
    }

    public String getMoves() {
        return moves.toString();
    }

    public int getSize(){
        return size;
    }

    public int getSize(Node node){
        if(node == null){
            return 0;
        }

        return 1 + getSize(node.getRight()) + getSize(node.getLeft());
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.frequency, o.frequency);
    }

    @Override
    public String toString() {
        return transversal(this);
    }

    public String transversal(Node node){
        if(node == null){
            return "";
        }
        else if(node.character.equals("")){
            return "null" + " " + transversal(node.getLeft()) + " " + transversal(node.getRight());
        }
//        else if(node.getLeft() == null && node.getRight() == null){
//            return node.character;
//        }
//        else if(node.getLeft() == null){
//            return node.character + " " + transversal(node.right);
//        }
//        else if(node.getRight() == null){
//            return node.getCharacter() + " " + transversal(node.left);
//        }

        return node.getCharacter() + " " + transversal(node.getLeft()) + " " + transversal(node.getRight());
    }
}



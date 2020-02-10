public class Entry {

    private int value;
    private int count;
    private String binary;
    private String charac;

    public Entry(String binary, String charac){
        value = Integer.parseInt(binary, 2);
        count = binary.length();
        this.binary = binary;
        this.charac = charac;
    }

    @Override
    public String toString() {
        return binary;
    }

    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }
}

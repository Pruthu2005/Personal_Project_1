
public class Chain {
    public static void main(String[] args){
        Block block1 = new Block("First Block","0");
        System.out.println("ID for block 1: " + block1.blockId);

        Block block2 = new Block("Second Block", block1.blockId);
        System.out.println("ID for block 2: " + block2.blockId);

        Block block3 = new Block("Third Block", block2.blockId);
        System.out.println("ID for block 3: " + block3.blockId);
    }
}

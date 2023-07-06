package btree;

public class Test {

    public static void main(String args[]) {
        BTree b1;
        try {
            b1 = new BTree(5);
        }
        catch(InvalidOrderException ive){
            return;
        }
        
        b1.insert(20);
        b1.insert(30);
        b1.insert(40);
        b1.insert(50);
        b1.insert(60);
        b1.insert(32);
        b1.insert(35);
        b1.insert(39);
        b1.insert(31);
        b1.insert(19);
        b1.insert(25);
        b1.insert(33);
        b1.insert(37);
        b1.insert(36);
        
        
        b1.root.displayNode();
        b1.root.children[0].displayNode();
        b1.root.children[1].displayNode();
        b1.root.children[2].displayNode();
        b1.root.children[3].displayNode();
        b1.root.children[4].displayNode();  
        
    }

}

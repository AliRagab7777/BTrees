package btree;

public class Test {
    
    public static void main(String args[])
    {
        BTree b1 = new BTree(7);
        b1.insert(3);
        b1.insert(5);
        System.out.println(b1.root.keys[0]);
        System.out.println(b1.root.keys[1]);
        
        b1.insert(2);
        b1.insert(6);
        b1.insert(4);
        b1.insert(0);
        b1.insert(10);
        b1.root.displayNode();
        b1.root.children[0].displayNode();
        b1.root.children[1].displayNode();
        b1.insert(13);
        b1.root.displayNode();
        b1.root.children[0].displayNode();
        b1.root.children[1].displayNode();
    }
    
    
}



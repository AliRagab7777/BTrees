package btree;

public class BTree {

    int order;
    Node root;
    int minOrder;

    public BTree(int order, Node root) {
        this.order = order;
        this.root = root;
        this.minOrder = (int) Math.ceil(order / 2);
    }

    public void insert(int value) {

    }

}

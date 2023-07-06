package btree;

public class BTree {

    int order;
    Node root;
    int minOrder;
    boolean evenOrder;

    public BTree(int order) throws InvalidOrderException {

        this.checkOrder(order);
        this.order = order;
        this.minOrder = (int) Math.ceil(order / 2);
        if (this.order % 2 == 0) {
            evenOrder = true;
        }

    }

    private void checkOrder(int order) throws InvalidOrderException {
        if (order <= 2) {
            throw new InvalidOrderException();
        }
    }

    public void insert(int value) {

        //Case 1:Empty
        if (root == null) {
            this.root = new Node(null, this.order, this.minOrder, this);
            this.root.insert(value);
        } else {
            Node n = findLeaf(value);
            n.insert(value);
        }

    }

    private Node findLeaf(int value) {
        if (root == null) {
            return null;
        }
        Node parent = null;
        Node n = root;

        while (n != null) {
            try {
                parent = n;
                n = n.findChildNodePath(value);

            } catch (DuplicateValueException dve) {
                System.out.println("Duplicate Value Exception!!!!!!!!");
            }
        }

        return parent;

    }

}

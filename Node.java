package btree;

class Node {

    int keys[];
    Node children[];
    Node parent;
    int minOrder;
    int order;
    int numOfElements;

    public Node(Node parent, int order, int minOrder) {
        this.parent = parent;
        this.order = order;
        this.minOrder = minOrder;
        keys = new int[order - 1];
        children = new Node[order];
        numOfElements = 0;

    }

    public boolean isFullSize() {
        if (this.numOfElements == (this.order - 1)) {
            return true;
        }
        return false;
    }

    public boolean isMinSize() {
        if (this.numOfElements == (this.minOrder - 1)) {
            return true;
        }
        return false;
    }

    public Node findChildNodePath(int value) throws DuplicateValueException {
        int childIndex = 0;
        while (value > this.keys[childIndex] && childIndex < numOfElements) {
            childIndex++;

        }

        if (keys[childIndex] == value) {
            throw new DuplicateValueException();
        }

        return children[childIndex];
    }

}

package btree;

class Node {

    int keys[];
    Node children[];
    Node parent;
    int minOrder;
    int order;
    int numOfElements;
    BTree bTree;

    public Node(Node parent, int order, int minOrder, BTree btree) {
        this.parent = parent;
        this.order = order;
        this.minOrder = minOrder;
        keys = new int[order - 1];
        children = new Node[order];
        numOfElements = 0;
        this.bTree = btree;

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
        while (childIndex < numOfElements && value > this.keys[childIndex]) {
            childIndex++;

        }

        if (childIndex < this.numOfElements && keys[childIndex] == value) {
            throw new DuplicateValueException();
        }

        return children[childIndex];
    }

    private int findChildPathIndex(int value) throws DuplicateValueException {
        int childIndex = 0;
        while (childIndex < numOfElements && value > this.keys[childIndex]) {
            childIndex++;

        }

        if (childIndex < this.numOfElements && keys[childIndex] == value) {
            throw new DuplicateValueException();
        }

        return childIndex;
    }

    public boolean isRoot() {
        return this == this.bTree.root;
    }

    public boolean isLeaf() {
        return this.children[0] == null;
    }

    public boolean hasTemp() {
        return (this.children[this.order - 1] != null) && (this.numOfElements < this.order - 1);
    }

    public Node getTemp() {
        return this.children[this.order - 1];

    }

    public void setTemp(Node temp) {
        this.children[this.order - 1] = temp;

    }

    private void adjustChildren() {
        int i;
        for (i = this.numOfElements; i >= 0; i--) {
            if (this.children[i].hasTemp()) {
                this.children[i + 1] = this.children[i].getTemp();
                this.children[i].getTemp().parent = this;
                this.children[i].setTemp(null);
                return;
            }

            this.children[i + 1] = this.children[i];
        }
    }
    

    private void splitChildren() {
         //Used to split children and create temp node
         //In case the node is a leaf, will create empty temp node only
        Node temp = new Node(null, this.order, this.minOrder, this.bTree);
        if (!this.isLeaf()) {
            int splitIdx = (int) (Math.ceil((this.order * 1.0) / 2));
            int i, child_temp_idx = -1, c = 1;
            for (i = this.numOfElements; i >= 0; i--) {
                if (this.children[i].hasTemp()) {
                    c = 0;
                    child_temp_idx = i + 1;
                }

                if (i + c < splitIdx) {
                    this.children[i + c] = this.children[i];
                } else {
                    temp.children[i + c - splitIdx] = this.children[i];
                    this.children[i].parent = temp;
                }
            }

            if (child_temp_idx < splitIdx) {
                this.children[child_temp_idx] = this.children[child_temp_idx - 1].getTemp();
                this.children[child_temp_idx].parent = this;
            } else if (child_temp_idx == splitIdx) {
                temp.children[0] = this.children[child_temp_idx - 1].getTemp();
                temp.children[0].parent = temp;

            } else {
                temp.children[child_temp_idx - splitIdx] = temp.children[child_temp_idx - splitIdx - 1].getTemp();
                temp.children[child_temp_idx - splitIdx].parent = temp;
            }

            temp.numOfElements = this.numOfElements - splitIdx;
            this.numOfElements = splitIdx;

        }

        this.setTemp(temp);

    }

    private int splitKeys(int value) {
        int valueIndex;
        try {
            valueIndex = findChildPathIndex(value);
        } catch (DuplicateValueException dve) {
            //theortically this case should not happen.

            return -1;
        }

        int medianIndex;
        medianIndex = (int) (Math.floor(this.order * 1.0 / 2 - 0.5));

        Node temp = this.getTemp();
        int splitIndex = medianIndex + 1;
        int i, c = 1;

        for (i = this.numOfElements - 1; i >= 0; i--) {

            if (i < valueIndex) {
                c = 0;
            }

            if ((i + c) > medianIndex) {
                temp.keys[i + c - splitIndex] = this.keys[i];
            } else if (c == 1 && (i + c) <= medianIndex) {
                this.keys[i + c] = this.keys[i];
            }
        }

        temp.numOfElements = (int) Math.floor(this.order / 2);
        this.numOfElements = this.order - 1 - temp.numOfElements;

        if (valueIndex <= medianIndex) {
            this.keys[valueIndex] = value;
        } else if (valueIndex > medianIndex) {
            temp.keys[valueIndex - splitIndex] = value;
        }

        this.setTemp(temp);

        return this.keys[medianIndex];
    }

    public void insert(int value) {
        //Case: Node is not full
        if (!this.isFullSize()) {
            if (!isLeaf()) {
                this.adjustChildren();
            }

            //Insertion Sort
            keys[this.numOfElements] = value;
            this.numOfElements++;

            int i = this.numOfElements - 1;
            while (i > 0 && value < keys[i - 1]) {
                keys[i] = keys[i - 1];
                i--;
            }
            keys[i] = value;

        } //Case: Node is full
        else {

            this.splitChildren(); 
            int median = this.splitKeys(value);
            if (this.isRoot()) {
                Node newRoot = new Node(null, this.order, this.minOrder, this.bTree);

                newRoot.insert(median);
                newRoot.children[0] = this;
                newRoot.children[1] = this.getTemp();
                this.parent = newRoot;
                this.getTemp().parent = newRoot;
                this.setTemp(null);
                this.bTree.root = newRoot;
            } else {
                this.parent.insert(median);
            }

        }

    }

    public void displayNode() {
        int i;
        System.out.print("Values of this node are: ");
        for (i = 0; i < this.numOfElements; i++) {
            System.out.print(this.keys[i] + " ");
        }
        System.out.println("");

    }
}

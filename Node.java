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
        return this.parent == null;
    }

    public boolean isLeaf() {
        return this.children[0] == null;
    }

    private void adjustChildren() {

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
        medianIndex =  (int) (Math.floor(this.order*1.0 / 2 - 0.5));

        Node temp = new Node(null, this.order, this.minOrder, this.bTree);
        int splitIndex = medianIndex + 1;
        int i, c = 1;
        
        for(i = this.numOfElements - 1; i >= 0;i--)
        {
            
            
            if(i < valueIndex)
                c = 0;

            if ((i + c) > medianIndex) {
                temp.keys[i+c - splitIndex] = this.keys[i];
            }
            else if (c == 1 && (i + c) <= medianIndex) {
                this.keys[i + c] = this.keys[i];
            }     
        }
      
        temp.numOfElements = (int) Math.floor(this.order / 2);
        this.numOfElements = this.order -1 - temp.numOfElements;
        
        
        if(valueIndex <= medianIndex)
            this.keys[valueIndex] = value;
        else if(valueIndex > medianIndex)
            temp.keys[valueIndex - splitIndex] = value;
        
        this.children[this.order - 1] = temp;
        
        return this.keys[medianIndex]; 
    }

    public void insert(int value) {
        //Case: Leaf is not full
        if (!this.isFullSize()) {
            if (!isLeaf()) {
                this.adjustChildren();
            }

            keys[this.numOfElements] = value;
            this.numOfElements++;

            int i = this.numOfElements - 1;
            while (i > 0 && value < keys[i - 1]) {
                keys[i] = keys[i - 1];
                i--;
            }
            keys[i] = value;

        } //Case: Leaf is full
        else {
            
            int median = this.splitKeys(value);
            if(this.isRoot())
            {
                Node newRoot = new Node(null,this.order,this.minOrder,this.bTree);
                
                //root.insert or btree.insert;
                newRoot.keys[0] = median;
                newRoot.numOfElements = 1;
                newRoot.children[0] = this;
                newRoot.children[1] = this.children[this.order - 1];
                this.parent = newRoot;
                this.children[this.order - 1].parent = newRoot;
                this.children[this.order - 1] = null;
                this.bTree.root = newRoot;
             }
            else
                this.parent.insert(median);

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

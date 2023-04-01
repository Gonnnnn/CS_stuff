public final class TreeNode<E> implements ITreeNode<E> {
    private ITreeNode left,right,parent;
    private E value;

    public TreeNode() {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.value = null;
    }

    @Override
    public void setLeft(ITreeNode node) {
        this.left = node;
    }
    @Override
    public void setRight(ITreeNode node) {
        this.right = node;
    }
    @Override
    public void setParent(ITreeNode node) {
        this.parent = node;
    }
    @Override
    public void setVal(E value) {
        this.value = value;
    }
    @Override
    public ITreeNode left() {
        return this.left;
    }
    @Override
    public ITreeNode right() {
        return this.right;
    }
    @Override
    public ITreeNode parent() {
        return this.parent;
    }
    @Override
    public E val() {
        return this.value;
    }
}

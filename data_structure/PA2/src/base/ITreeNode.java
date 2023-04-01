public interface ITreeNode<E> {
    void setLeft(ITreeNode node);
    void setRight(ITreeNode node);
    void setParent(ITreeNode node);
    void setVal(E value);
    ITreeNode left();
    ITreeNode right();
    ITreeNode parent();
    E val();
}

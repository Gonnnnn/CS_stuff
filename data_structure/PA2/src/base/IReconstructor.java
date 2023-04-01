public interface IReconstructor {
    ITreeNode reconstruct(Integer[] postOrder, Integer[] inOrder) throws IllegalStateException;
}

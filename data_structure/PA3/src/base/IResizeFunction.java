public interface IResizeFunction {
    boolean checkResize(int tablesize, int numItems);

    int extendTable(int tablesize);
}

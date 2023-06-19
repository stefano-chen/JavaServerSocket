public interface Operation<I, O> {
    public O process(I input);
}

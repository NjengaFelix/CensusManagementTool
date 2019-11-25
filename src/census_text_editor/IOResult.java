package census_text_editor;

public class IOResult<T> {
    private T data;
    private boolean ok;

    public IOResult(T data, boolean ok) {
        this.data = data;
        this.ok = ok;
    }

    public T getData() {
        return data;
    }

    public boolean isOk() {
        return ok;
    }

    public boolean hasData() {
        return data !=null;
    }
}

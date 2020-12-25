package api;

public interface ApiExecuteListener {
    <T> void onSuccess(T result);
}

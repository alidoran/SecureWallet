package api;

public interface ApiEnqueueListener {
   <T> void onSuccess(T result);
   void failure(String errorMessage);
    }

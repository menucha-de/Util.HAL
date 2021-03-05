package havis.application.component.db;

/**
 * Provides the callback for database access
 *
 * @param <T> The expected result type
 */
public interface Callback<T> {

    /**
     * Called on failure
     * @param error
     */
    void onFailure(Error error);

    /**
     * Called on success
     * @param result
     */
    void onSuccess(T result);
}
package havis.application.component.db;

/**
 * Provides the callback for database access
 *
 * @param <T> The expected result type
 */
public interface IndexedCallback<T> {

    /**
     * Called on failure
     * @param error
     */
    void onFailure(Error error);

    /**
     * Called on success
     * @param result
     */
    void onSuccess(int index, T result);
}
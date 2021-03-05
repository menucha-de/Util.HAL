package havis.application.component.db;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

/**
 * Implements the database access. Provides methods to put, get or delete database entries or to clear the whole database.
 */
public class Database {
    
    JavaScriptObject db;
	
    /**
     * Creates a new instance. Creates a new or opens an existing database 
     * @param name The database name
     * @param version The version of database i.e. "1.0"
     * @param desc The database description
     * @param size The size of the database in bytes
     */
    public Database(String name, String version, String desc, Long size) {
    	init(name, version, desc, size);
    }

    /**
     * Initializes the database instance
     * @param name The database name
     * @param version The version of database i.e. "1.0"
     * @param desc The database description
     * @param size The size of the database in bytes
     */
    native void init(String name, String version, String desc, Long size)/*-{
    	var db = this.@havis.application.component.db.Database::db = openDatabase(name, version, desc, size);   	
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('CREATE TABLE IF NOT EXISTS storage (key unique, value)');
            });
        }
    }-*/;

    /**
     * Returns a integer object from int value
     * @param n The int value
     * @return The integer object
     */
    public static Integer toInt(int n) {
    	return new Integer(n);
    }
    
    /**
     * Makes an asynchrony call to determine the number of object in database
     * @param callback The result callback
     */
    public native void count(Callback<Integer> callback)/*-{
    	var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('SELECT count(key) AS count FROM storage', [], function(tx, results) {
                    toInt = $entry(@havis.application.component.db.Database::toInt(I));
                    callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(toInt(results.rows.item(0).count));
                }, function(tx, error) {
                    callback.@havis.application.component.db.Callback::onFailure(Lhavis/application/component/db/Error;)(error);
                });
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to got the entry by key form database
     * @param key The key
     * @param callback The result callback
     */
    public void getEntry(final String key, final Callback<Entry> callback) {
        get(key, new IndexedCallback<String>() {
        	public void onSuccess(int index, String result) {
        		callback.onSuccess(new Entry(index, key, result));
        	}
	    
        	public void onFailure(Error error) {
        		callback.onFailure(error);
        	}
        });
    }
    
    /**
     * Makes an asynchrony call to got the string value by key form database
     * @param key The key
     * @param callback The result callback
     */
    public native void get(String key, Callback<String> callback) /*-{
    	var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('SELECT value FROM storage WHERE key LIKE ?', [ key ], function(tx, results) {
                    if (results.rows.length > 0) {
                        callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(results.rows.item(0).value);
                    } else {
                        callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(null);
                    }
                }, function(tx, error) {
                    callback.@havis.application.component.db.Callback::onFailure(Lhavis/application/component/db/Error;)(error);
                });
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to got the string value by key form database
     * @param key The key
     * @param callback The result indexed callback
     */
    public native void get(String key, IndexedCallback<String> callback) /*-{
    	var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('SELECT (SELECT COUNT(oid) + 1 FROM storage WHERE oid < (SELECT oid FROM storage WHERE key LIKE ?)) "index", value FROM storage WHERE key LIKE ?', [ key, key ], function(tx, results) {
                    if (results.rows.length > 0) {
                        callback.@havis.application.component.db.IndexedCallback::onSuccess(ILjava/lang/Object;)(results.rows.item(0).index, results.rows.item(0).value);
                    } else {
                        callback.@havis.application.component.db.IndexedCallback::onSuccess(ILjava/lang/Object;)(0, null);
                    }
                }, function(tx, error) {
                    callback.@havis.application.component.db.Callback::onFailure(Lhavis/application/component/db/Error;)(error);
                });
            });
        }
    }-*/;
    
    /**
     * Makes an asynchrony call to put the string value to database by key
     * @param key The key
     * @param value The string value
     */
    public native void put(String key, String value, Callback<Void> callback) /*-{
        var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('REPLACE INTO storage (key, value) VALUES (?, ?)', [key, value ], function(tx, results) {
                    callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(null);
                }, function(tx, error) {
                    callback.@havis.application.component.db.Callback::onFailure(Lhavis/application/component/db/Error;)(error);
                })
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to put the string value to database by key
     * @param key The key
     * @param value The string value
     */
    public native void put(String key, String value) /*-{
        var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('REPLACE INTO storage (key, value) VALUES (?, ?)', [key, value ]);
            });
        }
    }-*/;
    
    /**
     * 
     * @param entries
     */
    public native void putAll(List<Entry> entries) /*-{
    	var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
            	var list = [];
            	for (entry in entries) {
            		list.push('(' + entry.getKey() + ',' + entry.getValue() + ')');
            	}
                //tx.executeSql('REPLACE INTO storage (key, value) VALUES ' + ['?,?' for (i of list)].join(','), list.join(','));
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to delete an entry from database by key
     * @param key The key
     */
    public native void delete(String key) /*-{
    	var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('DELETE FROM storage WHERE key LIKE ?', [ key ]);
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to delete an entry from database by key
     * @param key The key
     * @param callback The result indexed callback
     */
    public native void delete(String key, Callback<Void> callback) /*-{
    	var db = this.@havis.application.component.db.Database::db;
        if (db) {
            db.transaction(function(tx) {
                tx.executeSql('DELETE FROM storage WHERE key LIKE ?', [ key ], function(tx, results) {
                    callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(null);
                }, function(tx, error) {
                    callback.@havis.application.component.db.Callback::onFailure(Lhavis/application/component/db/Error;)(error);
                })
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to clear the whole database
     */
    public native void clear() /*-{
    	var db = this.@havis.application.component.db.Database::db;
    	if (db) {
            db.transaction(function(tx) {
                tx.executeSql('DELETE FROM storage');
            });
        }
    }-*/;

    /**
     * Makes an asynchrony call to got all existing keys in database
     * @param limit The result set limit
     * @param offset The result set offset 
     * @param callback The result callback
     */
    public native void keys(int limit, int offset, Callback<JsArrayString> callback) /*-{
    	var db = this.@havis.application.component.db.Database::db;
    	if (db) {
            db.transaction(function(tx) {
            	var query = 'SELECT key FROM storage';
    			if (limit > 0) {
    				 query = query + ' LIMIT ' + limit;
    				 if (offset > 0) {
    				 	query = query + ' OFFSET ' + offset;
    				 }
    			}
                tx.executeSql(query, [], function(tx, results) {
                    var result = new Array();
                    for (var i = 0; i < results.rows.length; i++) {
                        result.push(results.rows.item(i).key);
                    }
                    callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(result);
                });
            });
        }
    }-*/;
    
    /**
     * Makes an asynchrony call to got all existing keys in database
     * @param callback The result callback
     */
    public void keys(Callback<JsArrayString> callback) {
    	keys(0, 0, callback);
    }

    /**
     * Makes an asynchrony call to got all existing values in database
     * @param limit The result set limit
     * @param offset The result set offset 
     * @param callback The result callback
     */
    public native void values(int limit, int offset, Callback<JsArrayString> callback) /*-{
    	var db = this.@havis.application.component.db.Database::db;
    	if (db) {
    		db.transaction(function(tx) {
    			var query = 'SELECT value FROM storage';
    			if (limit > 0) {
    				 query = query + ' LIMIT ' + limit;
    				 if (offset > 0) {
    				 	query = query + ' OFFSET ' + offset;
    				 }
    			}
    			tx.executeSql(query, [], function(tx, results) {
    				var result = new Array();
    				for (var i = 0; i < results.rows.length; i++) {
    					result.push(results.rows.item(i).value);
    				}
    				callback.@havis.application.component.db.Callback::onSuccess(Ljava/lang/Object;)(result);
    			});
    		});
    	}
    }-*/;
    
    /**
     * Makes an asynchrony call to got all existing values in database
     * @param callback The result callback
     */
    public void values(Callback<JsArrayString> callback){
    	values(0, 0, callback);
    }
}
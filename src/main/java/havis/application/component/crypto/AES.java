package havis.application.component.crypto;

public class AES {

    public static Object KEY, IV;

    /**
     * Creates a new instance
     * @param key The key as hex string
     * @param iv The iv as hex string
     */
    public AES(String key, String iv) {
	init(key, iv);
    }

    native void init(String key, String iv)/*-{
        @havis.application.component.crypto.AES::KEY = $wnd.CryptoJS.enc.Hex.parse(key);
        @havis.application.component.crypto.AES::IV = $wnd.CryptoJS.enc.Hex.parse(iv);
    }-*/;

    /**
     * Encrypts the given hex data string
     * @param data The hex data string
     * @return The encrypted data as hex string
     */
    public native String encrypt(String data)/*-{
        return $wnd.CryptoJS.enc.Hex.stringify($wnd.CryptoJS.AES.encrypt($wnd.CryptoJS.enc.Hex.parse(data), @havis.application.component.crypto.AES::KEY, { iv : @havis.application.component.crypto.AES::IV }).ciphertext);
    }-*/;

    /**
     * Decrypts the given hex data string
     * @param data The hex data string
     * @return The decrypted data as hex string
     */
    public native String decrypt(String data)/*-{
        return $wnd.CryptoJS.enc.Hex.stringify($wnd.CryptoJS.AES.decrypt({ ciphertext : $wnd.CryptoJS.enc.Hex.parse(data) }, @havis.application.component.crypto.AES::KEY, { iv : @havis.application.component.crypto.AES::IV }));
    }-*/;
}

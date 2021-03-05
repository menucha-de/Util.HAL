package havis.application.component.crypto;

public class Base64 {

    /**
     * Returns the hex string of a base64 based string
     * @param data The base64 based data
     * @return The decoded hex string
     */
    public static native String decode(String data)/*-{
        return $wnd.CryptoJS.enc.Hex.stringify($wnd.CryptoJS.enc.Base64.parse(data));
    }-*/;
    
    /**
     * Returns the encoded base64 string of the given hex data 
     * @param data The hex data
     * @return The base64 string
     */
    public static native String encode(String data)/*-{
        return $wnd.CryptoJS.enc.Base64.stringify($wnd.CryptoJS.enc.Hex.parse(data));
    }-*/;
}
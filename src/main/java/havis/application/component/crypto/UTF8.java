package havis.application.component.crypto;

public class UTF8 {

    /**
     * Returns the hex string of a Utf-8 based string
     * @param data The Utf-8 based data
     * @return The decoded hex string
     */
    public static native String decode(String data)/*-{
        return $wnd.CryptoJS.enc.Hex.stringify($wnd.CryptoJS.enc.Utf8.parse(data));
    }-*/;
    
    /**
     * Returns the encoded Utf-8 string of the given hex data 
     * @param data The hex data
     * @return The Utf-8 string
     */
    public static native String encode(String data)/*-{
        return $wnd.CryptoJS.enc.Utf8.stringify($wnd.CryptoJS.enc.Hex.parse(data));
    }-*/;
}
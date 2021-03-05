package havis.application.component.crypto;

public class MD5 {

    /**
     * Returns the hash of the given hex string
     * @param data The hex string to hash
     * @return The hash as hex string
     */
    public static native String hash(String data)/*-{
        return $wnd.CryptoJS.enc.Hex.stringify($wnd.CryptoJS.MD5($wnd.CryptoJS.enc.Hex.parse(data)));
    }-*/;
    
    
    /**
     * Returns compressed hex string of a hashed value
     * @param input The hash hex string to compress (32 characters)
     * @return The hash as compressed hex string (8 characters) or null, if hash string has not the expected length
     */
    public static native String compress(String input)/*-{
        var result = "";
        var block1, block2, block3, block4, compressed1A, compressed1B, compressed;
        var i=0;
        
        if (input.length!=32)
        	return null;
        
        block1 = parseInt(input.substr(0, 8), 16);
	block2 = parseInt(input.substr(8, 8), 16);        
        block3 = parseInt(input.substr(16, 8), 16);
        block4 = parseInt(input.substr(24, 8), 16);
        
        compressed1A = block1 + block2;
        compressed1B = block3 + block4;
        
	if (compressed1A > 0xFFFFFFFF)
	    compressed1A = compressed1A & 0xFFFFFFFF;    
	         
	if (compressed1B > 0xFFFFFFFF)
	    compressed1B = compressed1B & 0xFFFFFFFF;   	         
        
        compressed = compressed1A + compressed1B;

	if (compressed > 0xFFFFFFFF)
	    compressed = compressed & 0xFFFFFFFF;
	    
	compressed = compressed >>> 0;         

        result += compressed.toString(16);
        
        while (result.length<8)
        {
            result = "0" + result; 
        }

        return result;
    }-*/;
    
    
}
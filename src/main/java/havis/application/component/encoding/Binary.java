package havis.application.component.encoding;

public class Binary {

    public static String ALPHANUM, ALPHABET;

    static {
	init();
    }

    public static native void init()/*-{
        @havis.application.component.encoding.Binary::ALPHANUM = '\0ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/\\';
        @havis.application.component.encoding.Binary::ALPHABET = '\0ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\xC4\xD6\xDC\xE4\xF6\xFC\xDF-. \\';
    }-*/;

    /**
     * Returns the mapped hex string of the clear text string
     * @param input The clear text input string
     * @param map The mapping string
     * @return The hex string
     */
    public static native String stringify(String input, String map)/*-{
        var result = "";
        var b1, b2, b3, c1, c2, c3, c4;
        var i = 0;

        while (i < input.length - 1) {
            
            if (i < input.length - 1) b1 = parseInt(input.substr(i, 2), 16);
            else b1 = 0x00;
            i = i + 2;
            
            if (i < input.length - 1) b2 = parseInt(input.substr(i, 2), 16);
            else b2 = 0x00;
            i = i + 2;
            
            if (i < input.length - 1) b3 = parseInt(input.substr(i, 2), 16);
            else b3 = 0x00;
            i = i + 2;
            
            c1 = b1 >> 2;
            c2 = ((b1 & 3) << 4) | (b2 >> 4);
            c3 = ((b2 & 15) << 2) | (b3 >> 6);
            c4 = b3 & 63;
            
            result += map.charAt(c1) + map.charAt(c2) + map.charAt(c3) + map.charAt(c4);
        }
        return result;
    }-*/;

    /**
     * Returns the mapped clear text string of the hex string
     * @param input The hex input string
     * @param map The mapping string
     * @return The clear text string
     */
    public static native String parse(String input, String map)/*-{
        var result = "";
        var c, c1, c2, c3, c4, b1, b2, b3;
        var i = 0;

        while (i < input.length) {
            
            if (i < input.length) {
                c1 = map.indexOf(input.charAt(i++));
                if (c1 < 0) return null;
            } else c1 = 0x00;

            if (i < input.length) {
                c2 = map.indexOf(input.charAt(i++));
                if (c2 < 0) return null;
            } else c2 = 0x00;
            
            if (i < input.length) {
                c3 = map.indexOf(input.charAt(i++));
                if (c3 < 0) return null;
            } else c3 = 0x00;
            
            if (i < input.length) {
                c4 = map.indexOf(input.charAt(i++));
                if (c4 < 0) return null;
            } else c4 = 0x00;
            
            b1 = (c1 << 2) | (c2 >> 4);
            b2 = ((c2 & 15) << 4) | (c3 >> 2);
            b3 = ((c3 & 3) << 6) | c4;
            
            if (b1 < 0x10) result += "0";
            result += b1.toString(16);
            if (b2 < 0x10) result += "0";
            result += b2.toString(16);
            if (b3 < 0x10) result += "0";
            result += b3.toString(16);
        }
        return result;
    }-*/;
    
    /**
     * Encodes a latin1 string to utf8
     * @param input The latin1 string
     * @return The utf8 string
     */
    public static native String encode_utf8(String input)/*-{
    //Normalize New Lines
    input = input.replace(/\r\n/g,"\n");
    var utftext = "";
    for(var n=0; n < input.length; n++)
    {
       // current unicode
       var c = input.charCodeAt(n);
       // all characters from 0-127 => 1byte
       if (c < 128)
       {
                       utftext += String.fromCharCode(c);
       }
       // all characters from 128 to 2047 => 2byte
       else if( (c >= 128) && (c < 2048)) 
       {
                       utftext += String.fromCharCode((c >> 6) | 192);
                       utftext += String.fromCharCode((c & 63) | 128);
       }
       // all characters from 2048 to 65535 => 3byte
       else 
       {
                       utftext += String.fromCharCode((c >> 12) | 224);
                       utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                       utftext += String.fromCharCode((c & 63) | 128);}
       }
    return utftext;
    }-*/;
    
    /**
     * Decodes a latin1 string from utf8
     * @param input The utf8 string
     * @return The latin1 string
     */
    public static native String decode_utf8(String input)/*-{
    var plaintext = ""; 
    var i=0; 
    var char1=char2=char3=0x00;

    while(i  < input.length)
    {
       char1 =  input.charCodeAt(i);
       //0xxxxxxx 1byte
       if (char1 < 128) 
       {
           plaintext += String.fromCharCode(char1);
           i++;
       }
       //110xxxxx 2 byte 
       else if((char1 >= 192) && (char1 < 224)) 
       {
           char2 =  input.charCodeAt(i + 1);
           plaintext += String.fromCharCode(((char1 & 31) << 6) | (char2 & 63));
           i += 2;
       }
       //1110xxxx 3 byte
       else 
       {
           char2 =  input.charCodeAt(i + 1); 
           char3 =  input.charCodeAt(i + 2);
           plaintext += String.fromCharCode(((char1 & 15) << 12) | ((char2 & 63) << 6) | (char3 & 63));
           i += 3;
       }
    }
    return plaintext;
    }-*/;
        
}
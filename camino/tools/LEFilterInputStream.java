package tools;
import java.io.*;

/**
 * A little-endian input stream reads two's complement,  
 * little-endian integers, floating-point numbers, and characters 
 * and returns them as Java primitive types. 
 * The standard java.io.DataInputStream class 
 * which this class imitates reads big-endian quantities.
 *
 * @author  Elliotte Rusty Harold
 * @version 1.0, 28 Aug 1998
 * @see     com.macfaq.io.LittleEndianOutputStream
 * @see     java.io.DataInputStream
 */
public class LEFilterInputStream extends FilterInputStream implements DataInput {

  /**
   * Creates a new little-endian input stream and chains it to the  
   * input stream specified by the in argument. 
   *
   * @param   in   the underlying input stream.
   * @see     java.io.FilterInputStream#out
   */
  public LEFilterInputStream(InputStream in) {
    super(in);
  }

  /**
   * Reads a <code>boolean</code> from the underlying input stream by 
   * reading a single byte. If the byte is zero, false is returned.
   * If the byte is positive, true is returned. 
   *
   * @return      b   the <code>boolean</code> value read.
   * @exception  EOFException  if the end of the underlying input stream
   *              has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public boolean readBoolean() throws IOException {
  
    int bool = in.read();
    if (bool == -1) throw new EOFException();
    return (bool != 0);
  }

  /**
   * Reads a signed <code>byte</code> from the underlying input stream
   * with value between -128 and 127.
   *
   * @return     the <code>byte</code> value read.
   * @exception  EOFException  if the end of the underlying input stream
   *              has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public byte readByte() throws IOException {
  
    int temp = in.read();
    if (temp == -1) throw new EOFException();
    return (byte) temp;
  }

  /**
   * Reads an unsigned <code>byte</code> from the underlying 
   * input stream with value between 0 and 255.
   *
   * @return     the <code>byte</code> value read.
   * @exception  EOFException  if the end of the underlying input 
   *              stream has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public int readUnsignedByte() throws IOException {
  
    int temp = in.read();
    if (temp == -1) throw new EOFException();
    return temp;
  }

  /**
   * Reads a two byte signed <code>short</code> from the underlying 
   * input stream in little-endian order, low byte first. 
   *
   * @return     the <code>short</code> read.
   * @exception  EOFException  if the end of the underlying input stream 
   *             has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public short readShort() throws IOException {

    int byte1 = in.read();
    int byte2 = in.read();
    // only need to test last byte read
    // if byte1 is -1 so is byte2
    if (byte2 == -1) throw new EOFException();
    return (short) ((byte2 << 8) + byte1);
  }

  /**
   * Reads a two byte unsigned <code>short</code> from the underlying 
   * input stream in little-endian order, low byte first. 
   *
   * @return     the int value of the unsigned short read.
   * @exception  EOFException  if the end of the underlying input stream 
   *             has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public int readUnsignedShort() throws IOException {

    int byte1 = in.read();
    int byte2 = in.read();
    if (byte2 == -1) throw new EOFException();
    return (byte2 << 8) + byte1;
  }

  /**
   * Reads a two byte Unicode <code>char</code> from the underlying 
   * input stream in little-endian order, low byte first. 
   *
   * @return     the int value of the unsigned short read.
   * @exception  EOFException  if the end of the underlying input stream 
   *             has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public char readChar() throws IOException {

    int byte1 = in.read();
    int byte2 = in.read();
    if (byte1 == -1  || byte2 == -1) throw new EOFException();
    return (char) ((byte2 << 8) + byte1);
  }

  /**
   * Reads a four byte signed <code>int</code> from the underlying 
   * input stream in little-endian order, low byte first. 
   *
   * @return     the <code>int</code> read.
   * @exception  EOFException  if the end of the underlying input stream 
   *              has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public int readInt() throws IOException {

    int byte1, byte2, byte3, byte4;
    
    synchronized (this) {
      byte1 = in.read();
      byte2 = in.read();
      byte3 = in.read();
      byte4 = in.read();
    }
    if (byte4 == -1  || byte3 == -1 || byte2 == -1 || byte1 == -1) {
      throw new EOFException();
    }
    return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
  }

  /**
   * Reads an eight byte signed <code>long</code> from the underlying 
   * input stream in little-endian order, low byte first. 
   *
   * @return     the <code>long</code> read.
   * @exception  EOFException  if the end of the underlying input stream 
   *              has been reached
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public long readLong() throws IOException {

    long byte1 = in.read();
    long byte2 = in.read();
    long byte3 = in.read();
    long byte4 = in.read();
    long byte5 = in.read();
    long byte6 = in.read();
    long byte7 = in.read();
    long byte8 = in.read();
    if (byte4 == -1  || byte3 == -1 || byte2 == -1 || byte1 == -1 ||
     byte8 == -1  || byte7 == -1 || byte6 == -1 || byte5 == -1) {
      throw new EOFException();
    }
    return (byte8 << 56) + (byte7 << 48) + (byte6 << 40) + (byte5 << 32) +
     (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
  }

  /**
   * Reads a string of no more than 65,535 characters 
   * from the underlying input stream using UTF-8 
   * encoding. This method first reads a two byte short 
   * in <b>big</b> endian order as required by the 
   * UTF-8 specification. This gives the number of bytes in 
   * the UTF-8 encoded version of the string.
   * Next this many bytes are read and decoded as UTF-8
   * encoded characters. 
   *
   * @return     the decoded string
   * @exception  UTFDataFormatException if the string cannot be decoded
   * @exception  IOException  if the underlying stream throws an IOException.
   */
  public String readUTF() throws IOException {

    int byte1 = in.read();
    int byte2 = in.read();
    if (byte2 == -1) throw new EOFException();;
    int numbytes = (byte1 << 8) + byte2;
    
    char result [] = new char[numbytes];
    int numread = 0;
    int numchars = 0;
    
    while (numread < numbytes) {
    
      int c1 = readUnsignedByte();
      int c2, c3;
      
      // Look at the first four bits of c1 to determine how many 
      // bytes in this char.
      int test = c1 >> 4;
      if (test < 8) {  // one byte
        numread++;
        result[numchars++] = (char) c1;
      }
      else if (test == 12 || test == 13) { // two bytes
        numread += 2;
        if (numread > numbytes) throw new UTFDataFormatException();     
        c2 = readUnsignedByte();
        if ((c2 & 0xC0) != 0x80) throw new UTFDataFormatException();     
        result[numchars++] = (char) (((c1 & 0x1F) << 6) | (c2 & 0x3F));
      }
      else if (test == 14) { // three bytes
        numread += 3;
        if (numread > numbytes) throw new UTFDataFormatException();     
        c2 = readUnsignedByte();
        c3 = readUnsignedByte();
        if (((c2 & 0xC0) != 0x80) || ((c3 & 0xC0) != 0x80)) {
          throw new UTFDataFormatException();
        }
        result[numchars++] = (char) 
         (((c1 & 0x0F) << 12) | ((c2 & 0x3F) << 6) | (c3 & 0x3F));
      }
      else { // malformed
        throw new UTFDataFormatException();
      }    
    }  // end while
    return new String(result, 0, numchars); 
  }
  /**
   *
   * @return     the next eight bytes of this input stream, interpreted as a
   *             little-endian <code>double</code>.
   * @exception  EOFException if end of stream occurs before eight bytes 
   *             have been read.
   * @exception  IOException   if an I/O error occurs.
   */
  public final double readDouble() throws IOException {
  
    return Double.longBitsToDouble(this.readLong());
  }
  
  /**
   *
   * @return     the next four bytes of this input stream, interpreted as a
   *             little-endian <code>float</code>.
   * @exception  EOFException if end of stream occurs before four bytes 
   *             have been read.
   * @exception  IOException  if an I/O error occurs.
   */
  public final float readFloat() throws IOException {
  
    return Float.intBitsToFloat(this.readInt());
  }
  
  /**
   * Skip exactly <code>n</code> bytes of input in the underlying 
   * input stream. This method blocks until all the bytes are skipped, 
   * the end of the stream is detected, or an exception is thrown. 
   *
   * @param      n   the number of bytes to skip.
   * @return     the number of bytes skipped, generally n
   * @exception  EOFException  if this input stream reaches the end before
   *             skipping all the bytes.
   * @exception  IOException  if the underlying stream throws an IOException.
   */
   public final int skipBytes(int n) throws IOException {
   
    for (int i = 0; i < n; i += (int) skip(n - i));
    return n;
   }


    /**
     * @inheritDoc
     * @see java.io.DataInput#readFully(byte[])
     */
    public final void readFully ( byte b[] ) throws IOException {
        in.read( b, 0, b.length );
    }
    
    
    /**
     * @inheritDoc
     * @see java.io.DataInput#readFully(byte[], int, int)
     */
    public final void readFully ( byte b[], int off, int len ) throws IOException {
        in.read( b, off, len );
    }


    /**
     * @return a rough approximation of the 8-bit stream as a 16-bit unicode
     *         string
     * @throws IOException
     * @deprecated This method does not properly convert bytes to characters.
     *             Use a Reader instead with a little-endian encoding.
     */
    @Deprecated public final String readLine () throws IOException {
	throw new UnsupportedOperationException("Can't use this deprecated method");
    }

}

					
 

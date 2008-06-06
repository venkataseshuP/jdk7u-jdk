/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package sun.nio.cs.ext;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;

public class MS932_0213 extends Charset {
    public MS932_0213() {
        super("x-MS932_0213", ExtendedCharsets.aliasesFor("MS932_0213"));
    }

    public boolean contains(Charset cs) {
        return ((cs.name().equals("US-ASCII"))
                || (cs instanceof MS932)
                || (cs instanceof MS932_0213));
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    protected static class Decoder extends SJIS_0213.Decoder {
        MS932DB.Decoder decMS932;
        protected Decoder(Charset cs) {
            super(cs);
            decMS932 = new MS932DB.Decoder(cs);
        }

        protected char decodeDouble(int b1, int b2) {
            char c = decMS932.decodeDouble(b1, b2);
            if (c == DoubleByteDecoder.REPLACE_CHAR)
                return super.decodeDouble(b1, b2);
            return c;
        }
    }

    protected static class Encoder extends SJIS_0213.Encoder {
        MS932DB.Encoder encMS932;
        protected Encoder(Charset cs) {
            super(cs);
            encMS932 = new MS932DB.Encoder(cs);
        }

        protected int encodeChar(char ch) {
            int db = encMS932.encodeDouble(ch);
            if (db == 0)
                return super.encodeChar(ch);
            return db;
        }
    }
}
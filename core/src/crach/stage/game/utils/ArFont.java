package crach.stage.game.utils;

/*
 * **************************************************************************
 * Copyright 2017 See AUTHORS file.
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * *************************************************************************
 *
 */


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.CharArray;

import java.awt.event.KeyEvent;

public class ArFont {
    private static Array<ArGlyph> glyphs = new Array<ArGlyph>();

    public String typing(char c) {
        if (c == KeyEvent.VK_BACK_SPACE)
            popChar();
        else
            addChar(new ArGlyph(c, !ArUtils.isLTR(c)));
        return getText();
    }

    public static String getText(String given) {
        char[] chars = given.toCharArray();
        for (char c : chars)
            addChar(new ArGlyph(c, !ArUtils.isLTR(c)));
        String text = getText();
        glyphs.clear();
        return text;
    }

    private static void addChar(ArGlyph glyph) {
        glyphs.add(glyph);
        for (int i = 1; i <= 2; i += 1)  filterLastChars(i);
    }

    private void popChar() {
        if (glyphs.size > 0) {
            ArGlyph aChar = glyphs.pop();
            if (aChar instanceof ArGlyph.ArGlyphComplex) {
                Array<ArGlyph> glyphComplex = ((ArGlyph.ArGlyphComplex) aChar).getSimpleChars();
                for (int i = glyphComplex.size - 1; i >= 0; i--)
                    glyphs.add(glyphComplex.get(i));
                glyphs.pop();
            }
            filterLastChars(1);
        }
    }

    private static String getText() {
        CharArray ltrChars = new CharArray();
        String text = "";

        // add only LTR characters into array.
        for (int i = glyphs.size - 1; i >= 0; i--) {
            if (glyphs.get(i).isRTL()) break;

            ltrChars.add(glyphs.get(i).getOriginalChar());
        }

        // fix space between RTL and LTR characters.
        fixSpace(ltrChars);

        // reverse LTR characters and update main glyphs.
        for (int i = glyphs.size - 1; i >= 0; i--) {
            if (glyphs.get(i).isRTL()) break;

            glyphs.get(i).setChar(ltrChars.pop());
        }

        // append glyphs together.
        for (int i = glyphs.size - 1; i >= 0; i--) {
            char c = glyphs.get(i).getChar();
            text += c == KeyEvent.KEY_LOCATION_UNKNOWN ? KeyEvent.VK_SPACE : c;
        }

        return text;
    }

    /**
     * This method can fix approximately the space between RTL and LTR characters.
     *
     * @param ltrChars contains LTR characters ONLY.
     */
    private static void fixSpace(CharArray ltrChars) {
        if (ltrChars.size > 0)
            if (ltrChars.first() == KeyEvent.VK_SPACE || ltrChars.first() == KeyEvent.KEY_LOCATION_UNKNOWN) {
                if (ltrChars.first() == KeyEvent.VK_SPACE && ltrChars.peek() == KeyEvent.VK_SPACE)
                    return;
                ltrChars.add(ltrChars.removeIndex(0));

            } else if (ltrChars.peek() == KeyEvent.VK_SPACE || ltrChars.peek() == KeyEvent.KEY_LOCATION_UNKNOWN)
                ltrChars.insert(0, ltrChars.pop());

    }

    /**
     * CONSOLE
     **/


    /**
     * @param glyph
     * @return ArGlyph after filtering process.
     */
    private static void filter(ArGlyph glyph) {

        ArGlyph before = getPositionGlyph(glyph, -1);
        ArGlyph after = getPositionGlyph(glyph, +1);


        /** CASE 1 **/
        if (before == null && after == null  /** CONSOLE **/)
            glyph.setChar(ArUtils.getIndividualChar(glyph.getOriginalChar()));


        /** CASE 2 **/
        if (before == null && after != null )
            glyph.setChar(ArUtils.getStartChar(glyph.getOriginalChar()));


        /** CASE 3 **/
        if (before != null && after == null )
            if (ArUtils.isALFChar(glyph.getOriginalChar()) && ArUtils.isLAMChar(before.getOriginalChar())) {
                addComplexChars(glyph);
            } else {
                if (before.getType() == ArCharCode.X2)
                    glyph.setChar(ArUtils.getIndividualChar(glyph.getOriginalChar()));
                else
                    glyph.setChar(ArUtils.getEndChar(glyph.getOriginalChar()));
            }


        /** CASE 4 **/
        if (before != null && after != null)
            if (glyph.getType() == ArCharCode.X4) {
                if (before.getType() == ArCharCode.X2)
                    glyph.setChar(ArUtils.getStartChar(glyph.getOriginalChar()));
                else
                    glyph.setChar(ArUtils.getCenterChar(glyph.getOriginalChar()));
            } else {
                if (before.getType() == ArCharCode.X2)
                    glyph.setChar(ArUtils.getIndividualChar(glyph.getOriginalChar()));
                else
                    glyph.setChar(ArUtils.getEndChar(glyph.getOriginalChar()));
            }

    }
	/**
     * @param arGlyph current glyph.
     * @param pos     value always between [-1,1] : -1 is before arGlyph or +1 is after arGlyph.
     * @return correct position of glyph.
     */
    private static ArGlyph getPositionGlyph(ArGlyph arGlyph, int pos) {
        int i = glyphs.lastIndexOf(arGlyph, false) + (pos = MathUtils.clamp(pos, -1, 1));
        ArGlyph glyph = (pos > 0 ? i < glyphs.size : i > -1) ? glyphs.get(i) : null;
        return glyph != null ? ArUtils.isInvalidChar(glyph.getOriginalChar()) ? null : glyph : null;
    }

    private static void addComplexChars(ArGlyph arGlyph) {
        ArGlyph.ArGlyphComplex glyph = new ArGlyph.ArGlyphComplex(ArUtils.getLAM_ALF(arGlyph.getOriginalChar()));
        glyph.setSimpleGlyphs(arGlyph, getPositionGlyph(arGlyph, -1));
        for (int i = 0; i < glyph.getSimpleChars().size; i++) glyphs.pop();
        addChar(glyph);
    }

    private static void filterLastChars(int i) {
        if (glyphs.size - i > -1)
            filter(glyphs.get(glyphs.size - i));
    }

    public static class ArUtils {
        private static final char EMPTY = 0;
        private static final ArCharCode.IndividualChar[] chars;

        static {
            chars = ArCharCode.IndividualChar.values();
        }

        public static boolean isInvalidChar(char c) {
            return getCharType(c) == EMPTY;
        }

        public static boolean isLTR(char c) {
            return Languages.inRange(Languages.ENGLISH, c);
        }

        public static int getCharType(char c) {
            for (ArCharCode.IndividualChar individualChar : chars)
                if (individualChar.getChar() == c) return individualChar.getType();
            return 0;
        }

        public static char getIndividualChar(char c) {
            return c;
        }

        public static char getStartChar(char c) {
            for (int i = 0; i < chars.length; i++)
                if (chars[i].getChar() == c) return ArCharCode.StartChar.getChar(i);
            return c;
        }

        public static char getCenterChar(char c) {
            for (int i = 0; i < chars.length; i++)
                if (chars[i].getChar() == c) return ArCharCode.CenterChar.getChar(i);
            return c;
        }

        public static char getEndChar(char c) {
            for (int i = 0; i < chars.length; i++)
                if (chars[i].getChar() == c) return ArCharCode.EndChar.getChar(i);
            return c;
        }

        /*************** SPECIAL CASE *****************/
        public static char getLAM_ALF(char alf) {
            switch (alf) {
                case 1575:
                    return ArCharCode.IndividualChar.LAM_ALF.getChar();
                case 1570:
                    return ArCharCode.IndividualChar.LAM_ALF_MAD.getChar();
                case 1571:
                    return ArCharCode.IndividualChar.LAM_ALF_HAMZA_UP.getChar();
                case 1573:
                    return ArCharCode.IndividualChar.LAM_ALF_HAMZA_DOWN.getChar();
            }
            return alf;
        }

        public static boolean isALFChar(char c) {
            return
                    c == ArCharCode.IndividualChar.ALF.getChar()
                            || c == ArCharCode.IndividualChar.ALF_HAMZA_UP.getChar()
                            || c == ArCharCode.IndividualChar.ALF_HAMZA_DOWN.getChar()
                            || c == ArCharCode.IndividualChar.ALF_MAD.getChar()
                    ;
        }

        public static boolean isLAMChar(char c) {
            return c == ArCharCode.IndividualChar.LAM.getChar();
        }

        public static boolean isComplexChar(ArGlyph glyph) {
            return glyph instanceof ArGlyph.ArGlyphComplex;
        }

        public static CharArray getAllChars() {
            CharArray array = new CharArray();
            for (int i = 0; i < chars.length; i++)
                if (!array.contains(ArCharCode.IndividualChar.getChar(i)))
                    array.add(ArCharCode.IndividualChar.getChar(i));

            for (int i = 0; i < chars.length; i++)
                if (!array.contains(ArCharCode.StartChar.getChar(i)))
                    array.add(ArCharCode.StartChar.getChar(i));

            for (int i = 0; i < chars.length; i++)
                if (!array.contains(ArCharCode.CenterChar.getChar(i)))
                    array.add(ArCharCode.CenterChar.getChar(i));

            for (int i = 0; i < chars.length; i++)
                if (!array.contains(ArCharCode.EndChar.getChar(i)))
                    array.add(ArCharCode.EndChar.getChar(i));

            return array;
        }
    }
    public static  class ArGlyph {

        protected final char originalChar;
        protected final int type;
        protected char modifiedChar;
        private boolean rtl;

        public ArGlyph(char c, boolean rtl) {
            this.originalChar = c;
            this.rtl = rtl;
            type = ArFont.ArUtils.getCharType(c);
        }

        public char getOriginalChar() {
            return originalChar;
        }

        public char getChar() {
            return modifiedChar;
        }

        public boolean isRTL() {
            return rtl;
        }

        public void setChar(char c) {
            this.modifiedChar = c;
        }

        public int getType() {
            return type;
        }



        public static class ArGlyphComplex extends ArGlyph {

            private Array<ArGlyph> simpleGlyphs;

            // originalChar is a complex
            public ArGlyphComplex(char complexChar) {
                super(complexChar, true);
                simpleGlyphs = new Array<ArGlyph>();
            }

            public Array<ArGlyph> getSimpleChars() {
                return simpleGlyphs;
            }

            public ArGlyph getElementChar(int i) {
                return simpleGlyphs.get(i);
            }

            public char getComplexChar() {
                return modifiedChar;
            }

            public void setComplexChar(char c) {
                this.modifiedChar = c;
            }

            public int getType() {
                return type;
            }

            public void setSimpleGlyphs(ArGlyph... glyphs) {
                if (simpleGlyphs.size == 0)
                    simpleGlyphs.addAll(glyphs);
            }

            public void setSimpleGlyphs(Array<ArGlyph> glyphs) {
                this.simpleGlyphs = glyphs;
            }
        }
    }
    public static class ArCharCode {
        public static final int X0 = 0;
        public static final int X2 = 2;
        public static final int X4 = 4;

        public enum IndividualChar {
            SPACE(32, X0),
            EMPTY(0, X0),
            HAMZA(1569,X0),
            /************** X2 *************/
            ALF(1575, X2),
            ALF_MAD(1570, X2),
            ALF_HAMZA_UP(1571, X2),
            ALF_HAMZA_DOWN(1573, X2),
            ALF_MAQSORA(1609, X2),

            LAM_ALF(65275, X2),
            LAM_ALF_MAD(65269, X2),
            LAM_ALF_HAMZA_UP(65271, X2),
            LAM_ALF_HAMZA_DOWN(65273, X2),

            DAL(1583, X2),
            ZAL(1584, X2),

            RAA(1585, X2),
            ZAY(1586, X2),

            WAW(1608, X2),
            WAW_HAMZA(1572, X2),

            TAA_MARBOTA(1577, X2),


            /************** X4 *************/
            ALF_MAQSORA_HAMZA(1574, X4),

            BAA(1576, X4),
            TAA(1578, X4),
            THA(1579, X4),
            GEM(1580, X4),
            HAA(1581, X4),
            KHA(1582, X4),

            SEN(1587, X4),
            SHN(1588, X4),
            SAD(1589, X4),
            DAD(1590, X4),
            TTA(1591, X4),
            ZAA(1592, X4),
            AIN(1593, X4),
            GHN(1594, X4),
            FAA(1601, X4),
            QAF(1602, X4),
            KAF(1603, X4),
            LAM(1604, X4),
            MEM(1605, X4),
            NON(1606, X4),
            HHA(1607, X4),

            YAA(1610, X4),;

            private final int id;
            private final int type;

            IndividualChar(int id, int type) {
                this.id = id;
                this.type = type;
            }

            public static char getChar(int index) {
                return values()[index].getChar();
            }

            public char getChar() {
                return (char) id;
            }

            public int getType() {
                return type;
            }
        }

        public enum EndChar {
            SPACE(32, X0),
            EMPTY(0, X0),
            HAMZA(1569,X0),
            /************** X2 *************/
            ALF(65166, X2),
            ALF_MAD(65154, X2),
            ALF_HAMZA_UP(65156, X2),
            ALF_HAMZA_DOWN(65160, X2),
            ALF_MAQSORA(65264, X2),

            LAM_ALF(65276, X2),
            LAM_ALF_MAD(65270, X2),
            LAM_ALF_HAMZA_UP(65272, X2),
            LAM_ALF_HAMZA_DOWN(65274, X2),

            DAL(65194, X2),
            ZAL(65196, X2),

            RAA(65198, X2),
            ZAY(65200, X2),

            WAW(65262, X2),
            WAW_HAMZA(65158, X2),

            TAA_MARBOTA(65172, X2),


            /************ 4X ************/
            ALF_MAQSORA_HAMZA(65162, X4),

            BAA(65168, X4),
            TAA(65174, X4),
            THA(65178, X4),
            GEM(65182, X4),
            HAA(65186, X4),
            KHA(65190, X4),

            SEN(65202, X4),
            SHN(65206, X4),
            SAD(65210, X4),
            DAD(65214, X4),
            TTA(65218, X4),
            ZAA(65222, X4),
            AIN(65226, X4),
            GHN(65230, X4),
            FAA(65234, X4),
            QAF(65238, X4),
            KAF(65242, X4),
            LAM(65246, X4),
            MEM(65250, X4),
            NON(65254, X4),
            HHA(65258, X4),

            YAA(65266, X4),;

            private final int id;
            private final int type;

            EndChar(int id, int type) {
                this.id = id;
                this.type = type;
            }

            public static char getChar(int index) {
                return values()[index].getChar();
            }

            public char getChar() {
                return (char) id;
            }

            public int getType() {
                return type;
            }
        }

        public enum StartChar {
            SPACE(32, X0),
            EMPTY(0, X0),
            HAMZA(1569,X0),
            /************** X2 *************/
            ALF(1575, X2),
            ALF_MAD(1570, X2),
            ALF_HAMZA_UP(1571, X2),
            ALF_HAMZA_DOWN(1573, X2),
            ALF_MAQSORA(1609, X2),

            LAM_ALF(65275, X2),
            LAM_ALF_MAD(65269, X2),
            LAM_ALF_HAMZA_UP(65271, X2),
            LAM_ALF_HAMZA_DOWN(65273, X2),

            DAL(1583, X2),
            ZAL(1584, X2),

            RAA(1585, X2),
            ZAY(1586, X2),

            WAW(1608, X2),
            WAW_HAMZA(1572, X2),

            TAA_MARBOTA(1577, X2),

            /************* 4X **************/
            ALF_MAQSORA_HAMZA(65163, X4),

            BAA(65169, X4),
            TAA(65175, X4),
            THA(65179, X4),
            GEM(65183, X4),
            HAA(65187, X4),
            KHA(65191, X4),

            SEN(65203, X4),
            SHN(65207, X4),
            SAD(65211, X4),
            DAD(65215, X4),
            TTA(65219, X4),
            ZAA(65223, X4),
            AIN(65227, X4),
            GHN(65231, X4),
            FAA(65235, X4),
            QAF(65239, X4),
            KAF(65243, X4),
            LAM(65247, X4),
            MEM(65251, X4),
            NON(65255, X4),
            HHA(65259, X4),

            YAA(65267, X4),;

            private final int id;
            private final int type;

            StartChar(int id, int type) {
                this.id = id;
                this.type = type;
            }

            public static char getChar(int index) {
                return values()[index].getChar();
            }

            public char getChar() {
                return (char) id;
            }

            public int getType() {
                return type;
            }
        }

        public enum CenterChar {
            SPACE(32, X0),
            EMPTY(0, X0),
            HAMZA(1569,X0),
            /************** X2 *************/
            ALF(65166, X2),
            ALF_MAD(1570, X2),
            ALF_HAMZA_UP(65156, X2),
            ALF_HAMZA_DOWN(65160, X2),
            ALF_MAQSORA(1609, X2),

            LAM_ALF(65275, X2),
            LAM_ALF_MAD(65269, X2),
            LAM_ALF_HAMZA_UP(65271, X2),
            LAM_ALF_HAMZA_DOWN(65273, X2),

            DAL(65194, X2),
            ZAL(65196, X2),

            RAA(65198, X2),
            ZAY(65200, X2),

            WAW(65262, X2),
            WAW_HAMZA(65158, X2),

            TAA_MARBOTA(65172, X2),

            /*********** 4X ************/
            ALF_MAQSORA_HAMZA(65164, X4),

            BAA(65170, X4),
            TAA(65176, X4),
            THA(65180, X4),
            GEM(65184, X4),
            HAA(65188, X4),
            KHA(65192, X4),

            SEN(65204, X4),
            SHN(65208, X4),
            SAD(65212, X4),
            DAD(65216, X4),
            TTA(65220, X4),
            ZAA(65224, X4),
            AIN(65228, X4),
            GHN(65232, X4),
            FAA(65236, X4),
            QAF(65240, X4),
            KAF(65244, X4),
            LAM(65248, X4),
            MEM(65252, X4),
            NON(65256, X4),
            HHA(65260, X4),

            YAA(65268, X4),;

            private final int id;
            private final int type;

            CenterChar(int id, int type) {
                this.id = id;
                this.type = type;
            }

            public static char getChar(int index) {
                return values()[index].getChar();
            }

            public char getChar() {
                return (char) id;
            }

            public int getType() {
                return type;
            }
        }
    }
    public enum Languages {

        ENGLISH(32, 160);

        private final int from;
        private final int to;

        Languages(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public static boolean inRange(Languages languages, char c) {
            return languages.from <= c && c <= languages.to;
        }
    }
}


/******************************************************************************
 *  Compilation:  javac LongestCommonSubstring.java
 *  Execution:    java  LongestCommonSubstring file1.txt file2.txt
 *  Dependencies: SuffixArray.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/63suffix/tale.txt
 *                https://algs4.cs.princeton.edu/63suffix/mobydick.txt
 *  
 *  Read in two text files and find the longest substring that
 *  appears in both texts.
 * 
 *  % java LongestCommonSubstring tale.txt mobydick.txt
 *  ' seemed on the point of being '
 *
 ******************************************************************************/

package com.github.dlx4.compare;

/**
 *  The {@code LongestCommonSubstring} class provides a {@link SuffixArray}
 *  client for computing the longest common substring that appears in two
 *  given strings.
 *  <p>
 *  This implementation computes the suffix array of each string and applies a
 *  merging operation to determine the longest common substring.
 *  For an alternate implementation, see
 *  <a href = "https://algs4.cs.princeton.edu/63suffix/LongestCommonSubstringConcatenate.java.html">LongestCommonSubstringConcatenate.java</a>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/63suffix">Section 6.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  <p>
 *     
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class LongestCommonSubstring {

    // Do not instantiate.
    private LongestCommonSubstring() { }

    // return the longest common prefix of suffix s[p..] and suffix t[q..]
    public static String lcp(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.substring(p, p + i);
        }
        return s.substring(p, p + n);
    }

    // compare suffix s[p..] and suffix t[q..]
    public static int compare(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.charAt(p+i) - t.charAt(q+i);
        }
        if      (s.length() - p < t.length() - q) return -1;
        else if (s.length() - p > t.length() - q) return +1;
        else                                      return  0;
    }

    /**
     * Returns the longest common string of the two specified strings.
     *
     * @param  s one string
     * @param  t the other string
     * @return the longest common string that appears as a substring
     *         in both {@code s} and {@code t}; the empty string
     *         if no such string
     */
    public static String lcs(String s, String t) {
        SuffixArray suffix1 = new SuffixArray(s);
        SuffixArray suffix2 = new SuffixArray(t);

        // find longest common substring by "merging" sorted suffixes 
        String lcs = "";
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            int p = suffix1.index(i);
            int q = suffix2.index(j);
            String x = lcp(s, p, t, q);
            if (x.length() > lcs.length()) lcs = x;
            if (compare(s, p, t, q) < 0) i++;
            else                         j++;
        }
        return lcs;
    }



    /**
     * Unit tests the {@code lcs()} method.
     * Reads in two strings from files specified as command-line arguments;
     * computes the longest common substring; and prints the results to
     * standard output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // String s = "JjdJjd(jjdbh=3311252004150700016, zjjdbh=3311252004150200011, bjfsdm=1, jjlxdm=1, jjdcllxdm=3, jjdwdm=331125440000, jjdwmc=云和县公安局指挥中心, jjybh=331125020, jjyxm=李颖（小）, jjtbh=YHJJX03, jjtip=10.122.240.51, hrsj=2020-04-15T16:53:06, bjsj=2020-04-15T16:53:06, hzsj=2020-04-15T16:53:33, hrsc=0, jjsc=null, bjdh=13575391617, yhxm=null, yhsfz=null, yhdz=null, jjlyh=158694078523502528, bjrxm=null, bjrxb=null, lxdh=13575391617, zzdw=null, bm=1, afsj=null, xzqh=331125, afdd=null, bjp=null, bjnr=null, jqgjz=null, bjlbdm=100000, bjlxdm=100900, jjdzt=00000, sjgxsj=2020-04-15T16:53:06, gxdwdm=null, jqjb=null, fkzj=0, dhdwjd=119.550619, dhdwwd=28.105314, fxdwjd=null, fxdwwd=null, jqhm=110, xxaj=1, afxzqh=331125, sjdwdm=null, gxdwzt=0, cjllbh=null, cjlllbbh=null, bjsdxq=3, bjsdxs=16, insertTime=null, xsza=null, bjsdfz=53)\t";
        // String t = "JjdJjd(jjdbh=3311252004150700016, zjjdbh=3311252004150200011, bjfsdm=1, jjlxdm=1, jjdcllxdm=3, jjdwdm=331125440000, jjdwmc=云和县公安局指挥中心, jjybh=331125020, jjyxm=李颖（小）, jjtbh=YHJJX03, jjtip=10.122.240.51, hrsj=2020-04-15T16:53:06, bjsj=2020-04-15T16:53:06, hzsj=2020-04-15T16:53:33, hrsc=0, jjsc=22, bjdh=13575391617, yhxm=null, yhsfz=null, yhdz=0, jjlyh=158694078523502528, bjrxm=匿名, bjrxb=1, lxdh=13575391617, zzdw=null, bm=1, afsj=null, xzqh=331125, afdd=null, bjp=null, bjnr=null, jqgjz=null, bjlbdm=100000, bjlxdm=100900, jjdzt=00000, sjgxsj=2020-04-15T16:53:29, gxdwdm=null, jqjb=null, fkzj=0, dhdwjd=119.550619, dhdwwd=28.105314, fxdwjd=null, fxdwwd=null, jqhm=110, xxaj=1, afxzqh=331125, sjdwdm=null, gxdwzt=0, cjllbh=null, cjlllbbh=null, bjsdxq=3, bjsdxs=16, insertTime=null, xsza=null, bjsdfz=53)";
        String s = "s";
        String t = "t";

        System.out.println("'" + lcs(s, t) + "'");
    }
}


/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/

package com.github.dlx4.compare;

/**
 * 字符串差异识别并标记
 *
 * @author dinglingxiang
 */
public class DiffMark {

    private String a;
    private String b;
    private boolean[] markA;
    private boolean[] markB;

    public DiffMark(String a, String b) {
        this.a = a;
        this.b = b;
        markA = new boolean[a.length()];
        markB = new boolean[b.length()];

        for (int i = 0; i < markA.length; i++) {
            markA[i] = false;
        }

        for (int i = 0; i < markB.length; i++) {
            markB[i] = false;
        }
    }


    private void markCommon(int aFrom, int bFrom, int length) {

        for (int i = 0; i < length; i++) {
            markA[aFrom + i] = true;
            markB[bFrom + i] = true;
        }
    }

    /**
     * 字符串差异识别并标记
     * @param aFrom
     * @param aTo
     * @param bFrom
     * @param bTo
     */
    private void diffMark(int aFrom, int aTo, int bFrom, int bTo) {
        String s = a.substring(aFrom, aTo);
        String t = b.substring(bFrom, bTo);
        SuffixArray suffix1 = new SuffixArray(s);
        SuffixArray suffix2 = new SuffixArray(t);

        // find longest common substring by "merging" sorted suffixes
        String lcs = "";
        int lcsAFrom = -1;
        int lcsBFrom = -1;

        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            int p = suffix1.index(i);
            int q = suffix2.index(j);
            String x = LongestCommonSubstring.lcp(s, p, t, q);
            if (x.length() > lcs.length()) {
                lcs = x;
                lcsAFrom = aFrom + p;
                lcsBFrom = bFrom + q;
            }
            if (LongestCommonSubstring.compare(s, p, t, q) < 0) i++;
            else j++;
        }

        if (!StringUtils.isEmpty(lcs)) {
            markCommon(lcsAFrom, lcsBFrom, lcs.length());

            // 求解其余部分
            diffMark(aFrom, lcsAFrom, bFrom, lcsBFrom);
            diffMark(lcsAFrom + lcs.length(), aTo, lcsBFrom + lcs.length(), bTo);
        }

    }

    public DiffMark diffMark() {
        this.diffMark(0, a.length(), 0, b.length());
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < markA.length; i++) {
            if (markA[i]) {
                sb.append(a.charAt(i));
            } else {
                sb.append("\033[92;7m" + a.charAt(i) + "\033[0m");
            }
        }

        sb.append("\r\n=================================================================");
        sb.append("\r\n=================================================================\r\n");

        for (int i = 0; i < markB.length; i++) {
            if (markB[i]){
                sb.append(b.charAt(i));
            } else {
                sb.append("\033[92;7m" + b.charAt(i) + "\033[0m");
            }
        }
        return sb.toString();
    }

    private void show() {
        System.out.println(this.toString());
    }

    public static void main(String[] args) {
        String a = "转租杭州/滨江/湘云雅苑/主卧/1600\n" +
                "常见疑问：\n" +
                "户型：两室一厅+厨房+卫生间\n" +
                "朝向：朝南，落地大窗，阳光透气\n" +
                "楼层：6楼，有电梯\n" +
                "价格：1600元一个月，水电费另算（当初整租过来的价格就是3200）\n" +
                "家电配有：冰箱，洗衣机，空调，电热水器，饮水机（可以显著提升生活质量），电饭煲，电磁炉\n" +
                "网络：电信100M，wifi覆盖，房间带有线网口（宽带一年的价格是1000元）\n" +
                "桌椅：宽桌，人体工程学椅子，可以较为舒服的操作电脑\n" +
                "衣柜：主卧阳台侧面整面墙柜，能放很多东西\n" +
                "餐桌：客厅配有餐桌，可以三人同时就餐\n" +
                "厨房：没有燃气，但是有电磁炉\n" +
                "租期：长租短租都接受\n" +
                "其它福利：可以免费使用囤积的牙膏，垃圾袋，洗衣液，拖把，扫帚等已有的生活用品\n" +
                "注意事项：\n" +
                "只接受一人租用，谢绝情侣，兄弟，家人一起住\n" +
                "本人是男生，女生请谨慎考虑可能会有一些不方便\n" +
                "入住时建议将门锁换掉，自己保留钥匙，君子不立危墙之下\n" +
                "对于公用的洗衣机，建议按照时间表错开使用\n";
        String b = "转租杭州/滨江/湘云雅苑/主卧/1600\n" +
                "常见疑问：\n" +
                "户型：两室一厅+厨房+卫生间\n" +
                "朝向：朝南，落地大窗，阳光透气。\n" +
                "楼层：6楼，有电梯\n" +
                "价格：1600元一个月，水电费另算（当初整租过来的价格就是3200）\n" +
                "家电配有：冰箱，洗衣机，空调，电热水器，饮水机（可以显著提升生活质量），电饭煲，电磁炉\n" +
                "网络：电信100M，wifi覆盖，房间带有线网口（宽带一年的价格是1000元）\n" +
                "桌椅：宽桌，人体工程学椅子，可以较为舒服的操作电脑\n" +
                "衣柜：主卧阳台侧面整面墙柜，能放很多东西\n" +
                "餐桌：客厅配有餐桌，可以三人同时就餐\n" +
                "厨房：没有燃气，但是有电磁炉\n" +
                "租期：长租短租都接受\n" +
                "其它福利：可以免费使用囤积的牙膏，垃圾袋，洗衣液，拖把，扫帚等已有的生活用品\n" +
                "注意事项：\n" +
                "只接受一人租用，谢绝情侣，兄弟，家人一起住\n" +
                "本人是男生，希望男生租用，女生会有一些不方便。门口装有门帘布，卫生间是在门外的独立房间，理论能够互相保护隐私\n" +
                "入住时建议将门锁换掉，自己保留钥匙，君子不立危墙之下\n" +
                "对于公用的洗衣机，建议按照时间表错开使用\n";
        DiffMark diffMark = new DiffMark(a, b);

        diffMark.diffMark();
        diffMark.show();
    }

}

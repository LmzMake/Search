package com.bwie.search.home.modul.utils;

import java.util.List;

/**
 * 作者 ：   王兵洋
 * 时间 ：   2017/7/18
 * 类的作用 ：
 * 实现思路 ：
 *          毕生所求，不过是在她的眼中，能看到我的影子。——唐七公子 《三生三世枕上书》
 */

public class ResultBean {
    public static final String TAG = "ResultBean";


    /**
     * sn : 1
     * ls : true
     * bg : 0
     * ed : 0
     * ws : [{"bg":0,"cw":[{"w":"今天","sc":0}]},{"bg":0,"cw":[{"w":"的","sc":0}]},{"bg":0,"cw":[{"w":"天气","sc":0}]},{"bg":0,"cw":[{"w":"怎么样","sc":0}]},{"bg":0,"cw":[{"w":"。 ","sc":0}]}]
     */

    private int sn;
    private boolean ls;
    private int bg;
    private int ed;
    /**
     * bg : 0
     * cw : [{"w":"今天","sc":0}]
     */

    private List<WsBean> ws;

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public boolean isLs() {
        return ls;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    public List<WsBean> getWs() {
        return ws;
    }

    public void setWs(List<WsBean> ws) {
        this.ws = ws;
    }

    public static class WsBean {
        private int bg;
        /**
         * w : 今天
         * sc : 0
         */

        private List<CwBean> cw;

        public int getBg() {
            return bg;
        }

        public void setBg(int bg) {
            this.bg = bg;
        }

        public List<CwBean> getCw() {
            return cw;
        }

        public void setCw(List<CwBean> cw) {
            this.cw = cw;
        }

        public static class CwBean {
            private String w;
            private int sc;

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }

            public int getSc() {
                return sc;
            }

            public void setSc(int sc) {
                this.sc = sc;
            }
        }
    }
}

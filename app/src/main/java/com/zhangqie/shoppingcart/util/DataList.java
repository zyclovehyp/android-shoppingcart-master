package com.zhangqie.shoppingcart.util;

import com.zhangqie.shoppingcart.model.TreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangqie on 2016/11/26.
 */

public class DataList {

    public static final String JSONDATA() {
        return "{\"errcode\":0,\"errmsg\":\"success\",\"data\":[{\"shop_id\":\"1636\",\"shop_name\":\"水城县阳光佳美食材经营部\",\"items\":[{\"itemid\":\"100189\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/06\\/20170609105502145359.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/06\\/20170609105502145359.jpg\",\"price\":\"3.00\",\"title\":\"油菜一斤\"}]},{\"shop_id\":\"1778\",\"shop_name\":\"商品测试专卖店\",\"items\":[{\"itemid\":\"103677\",\"quantity\":\"2\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/09\\/20170926150650687701.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/09\\/20170926150650687701.jpg\",\"price\":\"0.10\",\"title\":\"测试商品1\"},{\"itemid\":\"103629\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/10\\/20171016134627837135.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/10\\/20171016134627837135.jpg\",\"price\":\"2.50\",\"title\":\"测试商品2\"},{\"itemid\":\"104015\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/10\\/20171016135318646327.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/10\\/20171016135318646327.jpg\",\"price\":\"1.00\",\"title\":\"测试商品3\"}]}]}";
    }


    public static String[] danJin() {


        String[] danJin = {"5", "6",
                "7", "8",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "18",
                "19",
                "20",
                "21",
                "22",
                "23",
                "24",
                "25"};
        return danJin;
    }

    public static String[] doubleJin() {

        String[] doubleJin = {

                "6",
                "8",
                "10",
                "12",
                "14",
                "16",
                "18",
                "20",
                "22",
                "24",
                "26",
                "28",
                "30",
                "32",
                "34",
                "36",
                "38",
                "40"

        };

        return doubleJin;

    }


    public static List<TreeModel> build(Integer headerId, String[] arr) {


        List<TreeModel> all = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(-1);
            treeModel.setJinJie(arr[i]);
            treeModel.setSheetId(headerId);
            all.add(treeModel);
        }
        return all;

    }
}

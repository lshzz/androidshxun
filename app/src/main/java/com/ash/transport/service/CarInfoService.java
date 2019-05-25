package com.ash.transport.service;

import android.util.Xml;

import com.ash.transport.bean.CarInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.service
 * @fileName:  CarInfoService.java
 * @describe:  车辆信息服务类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-22 20:43
 *----------------------------------------------*/
public class CarInfoService {

    // 通过输入流从 XML 文件中读取数据
    public static List<CarInfo> getFromXML(InputStream is) throws IOException, XmlPullParserException {

        List<CarInfo> list = null;      // 定义 CarInfo 对象列表集合
        CarInfo carInfo = null;         // 定义 CarInfo 暂存对象

        XmlPullParser parser = Xml.newPullParser();     // 创建 pull 解析器
        parser.setInput(is,"utf-8");      // 通过 xml 文件的输入流 初始化解析器

        int type = parser.getEventType();               // 得到当前事件的类型

        while (type != XmlPullParser.END_DOCUMENT) {    // END_DOCUMENT 文档结束标签
            switch (type) {
                case XmlPullParser.START_TAG:           // 一个节点的开始标签

                    if ("info".equals(parser.getName())) {          // 解析到全局开始的标签info根节点
                        list = new ArrayList<>();
                    } else if ("carId".equals(parser.getName())) {
                        carInfo = new CarInfo();                    // 重新开始初始化 CarInfo 对象
                    } else if ("make".equals(parser.getName())) {
                        carInfo.setMake(parser.nextText());         // parser.nextText() 得到该 tag 节点中的内容
                    } else if ("engine".equals(parser.getName())) {
                        carInfo.setEngine(parser.nextText());
                    } else if ("frame".equals(parser.getName())) {
                        carInfo.setFrame(parser.nextText());
                    } else if ("type".equals(parser.getName())) {
                        carInfo.setType(parser.nextText());
                    } else if ("licenseType".equals(parser.getName())) {
                        carInfo.setLicenseType(parser.nextText());
                    } else if ("licenseNum".equals(parser.getName())) {
                        carInfo.setLicenseNum(parser.nextText());
                    } else if ("VioNum".equals(parser.getName())) {
                        carInfo.setVioNum(parser.nextText());
                    } else if ("points".equals(parser.getName())) {
                        carInfo.setPoints(parser.nextText());
                    } else if ("fine".equals(parser.getName())) {
                        carInfo.setFine(parser.nextText());
                    }
                    break;

                case XmlPullParser.END_TAG:             // 一个节点的结束标签
                    if ("carId".equals(parser.getName())) {
                        list.add(carInfo);              // 一辆小车的信息处理完毕 加入列表集合
                        carInfo = null;                 // 清空 carInfo 对象数据
                    }
                    break;
            }

            type = parser.next();                       // 每遍循环结束就指向下个节点
        }

        // 返回读取到 CarInfo 列表集合
        return list;
    }
}

package org.techtown.client;

public class login_mem {
    static String ID;
    static String name;
    static String frame_num; // 어떤 프레임인지
    static int photo_count; // 몇장을 인화할건지
    static String goods;
    static int price_per; // 장당 가격
    static int price = photo_count * price_per; // photo_count * price_per
    static String orderId;
}

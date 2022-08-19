package org.techtown.client;

// 변수에 저장된 정보 없애기
public class clean_info {
    static void clean() {
        login_mem.ID=null;
        login_mem.name=null;
        login_mem.frame_num=null; // 어떤 프레임인지
        login_mem.photo_count=0; // 몇장을 인화할건지
        login_mem.goods=null;
        login_mem.price_per=0; // 장당 가격
        login_mem.price=0; // photo_count * price_per
        login_mem.orderId = null;
    }

    static void semi_clean() {
        login_mem.frame_num=null; // 어떤 프레임인지
        login_mem.photo_count=0; // 몇장을 인화할건지
        login_mem.goods=null;
        login_mem.price_per=0; // 장당 가격
        login_mem.price=0; // photo_count * price_per
        login_mem.orderId = null;
    }


}

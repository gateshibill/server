package com.ylb.util;
//package com.sjy.util;
//
//import com.gta.qts.c2j.adaptee.IGTAQTSCallbackBase;
//import com.gta.qts.c2j.adaptee.structure.*;
//
///**
// * 订阅消息数据回调接口
// * */
//public class GTACallbackBase implements IGTAQTSCallbackBase {
//
//    @Override
//    public void OnConnectionState(int msgType, int errCode) {
//        //System.out.println("OnConnectionState  msgType:" + msgType + " errCode:" + errCode);
//    }
//
//    @Override
//    public void OnDelayTime(DelayTime delayTime) {
//
//    }
//
//    @Override
//    public void OnLoginState(int errCode) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL1_Static(SSEL1_Static data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL1_Quotation(SSEL1_Quotation data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSE_IndexPress(SSE_IndexPress data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEIOL1_Quotation(SSEIOL1_Quotation data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEIOL1_Static(SSEIOL1_Static data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL2_Auction(SSEL2_Auction data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL2_Index(SSEL2_Index data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL2_Overview(SSEL2_Overview data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL2_Quotation(SSEL2_Quotation data) {
//    }
//
//    @Override
//    public void OnSubscribe_SSEL2_Static(SSEL2_Static data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SSEL2_Transaction(SSEL2_Transaction data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL2_Index(SZSEL2_Index data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL2_Order(SZSEL2_Order data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL2_Quotation(SZSEL2_Quotation data) {
//        //System.out.println("OnSubscribe_SZSEL2_Quotation");
//        //SZSEL2_Quotation_FileOut.printData(data);
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL2_Static(SZSEL2_Static data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL2_Status(SZSEL2_Status data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL2_Transaction(SZSEL2_Transaction data) {
//        //System.out.println("OnSubscribe_SZSEL2_Transaction");
//        //SZSEL2_Transaction_FileOut.printData(data);
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL1_Bulletin(SZSEL1_Bulletin arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL1_Quotation(SZSEL1_Quotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_SZSEL1_Static(SZSEL1_Static arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_CFFEXL2_Static(CFFEXL2_Static data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_CFFEXL2_Quotation(CFFEXL2_Quotation data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SHFEL1_Static(SHFEL1_Static data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_SHFEL1_Quotation(SHFEL1_Quotation data) {
//
//    }
//
//    @Override
//    public void OnSubscribe_INEL1_Static(INEL1_Static inel1_static) {
//
//    }
//
//    @Override
//    public void OnSubscribe_INEL1_Quotation(INEL1_Quotation inel1_quotation) {
//
//    }
//
//    @Override
//    public void OnSubscribe_CZCEL1_Quotation(CZCEL1_Quotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_CZCEL1_Static(CZCEL1_Static arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL1_ArbiQuotation(DCEL1_ArbiQuotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL1_Quotation(DCEL1_Quotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL1_Static(DCEL1_Static arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL2_ArbiQuotation(DCEL2_ArbiQuotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL2_MarchPriceQty(DCEL2_MarchPriceQty arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL2_OrderStatistic(DCEL2_OrderStatistic arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL2_Quotation(DCEL2_Quotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL2_RealTimePrice(DCEL2_RealTimePrice arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_DCEL2_Static(DCEL2_Static arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_ESUNNY_Index(ESUNNY_Index arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_HKEXL2_BrokerQueue(HKEXL2_BrokerQueue arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_HKEXL2_Index(HKEXL2_Index arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_HKEXL2_Overview(HKEXL2_Overview arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_HKEXL2_Quotation(HKEXL2_Quotation arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void OnSubscribe_HKEXL2_Static(HKEXL2_Static arg0) {
//        // TODO Auto-generated method stub
//
//    }
//}

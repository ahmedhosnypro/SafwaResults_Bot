package com.safwah.bot.result._1444;

public enum ResultMessageText1444 {
    START_MESSAGE("""
            أدخل الرقم الجامعي للحصول على نتيجتك
            """),
    HELP("""
            فضلا التأكد من أنك تستخدم الرقم الجامعي الصحيح الخاص بك أولا
            يمكنك الحصول على الرقم الجامعي من هنا
            <a href="https://t.me/SafwahCode_Bot">(اضغط هنا) بوت الرقم الجامعي </a>
            
أو محاولة البحث بالاسم الرباعي او البريد الذي سجل في الاختبارات            
                        
            قم بملئ النموذج التالي اذا كان هناك خطأ في نتيجتك 
            <a href="https://docs.google.com/forms/d/e/1FAIpQLSfXf5ruUkr6-YaOeHK4pb4h9PDsnpMLt0AgtA-K6pk9GxslRg/viewform"> نموذج مشاكل النتائج (اضغط هنا) </a>
            """),

    //    او ارسال المشكلة الى
//            <a href="https://t.me/Sufwah1_bot"> بوت تلقي الإشكالات </a>
    CODE_ERROR("""
            ❗️  يوجد خطأ بالرقم الجامعي  ❗️
            """);

    private final String message;

    ResultMessageText1444(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

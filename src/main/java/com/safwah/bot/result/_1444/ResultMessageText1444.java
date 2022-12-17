package com.safwah.bot.result._1444;

public enum ResultMessageText1444 {
    START_MESSAGE("أدخل الرقم الجامعي"),
    HELP("""
            قم بملئ النموذج التالي اذا كان هناك خطأ في نتيجتك 
            <a href="https://docs.google.com/forms/d/e/1FAIpQLSfXf5ruUkr6-YaOeHK4pb4h9PDsnpMLt0AgtA-K6pk9GxslRg/viewform"> نموذج مشاكل النتائج </a>
                        
            يمكنك الحصول على الرقم الجامعي من هنا
            <a href="https://t.me/SafwahCode_Bot"> بوت الرقم الجامعي </a>
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

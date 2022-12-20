package com.safwah.bot.code;

public enum CodeMessageText {
    START_MESSAGE("أدخل اسمك أو بريدك الإلكتروني"),

    PROBLEM("""
            يمكنك البحث عن طريق الاسم أو البريد الإلكتروني

            يمكنك الحصول على مزيد من المعلومات في قناة الاشكالات https://t.me/joinchat/C1-JbwjvT9E5NzA0
                        
            او ارسال المشكلة الى بوت تلقي الإشكالات @Sufwah1_bot وسيقوم الدعم بالرد عليكم
            """),

    EMAIL_ERROR("""
            ❗️  افحص البريد واكتبه بصورة صحيحة  ❗️
            """),
    SHORT_NAME_ERROR("اكتب الاسم ثلاثيًا أو رباعيًا"),
    NOT_FOUND_NAME_ERROR("""
            ❗️ حاول البحث باستخدام البريد الإلكتروني ❗️
            """),
    NOT_FOUND_EMAIL_ERROR("""
            ❗️  حاول البحث باستخدام الاسم  ❗️
            """);
    private final String message;

    CodeMessageText(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

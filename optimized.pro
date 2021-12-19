-libraryjars <java.home>/jmods/java.base.jmod
-libraryjars <java.home>/jmods/java.management.jmod
-libraryjars <java.home>/jmods/java.sql.jmod
-libraryjars <java.home>/jmods/jdk.unsupported.jmod

-keep public class Test {
    public static void main(java.lang.String[]);
}

-dontwarn javax.annotation.**
-dontwarn javax.naming.**
-dontwarn sun.reflect.**
-dontwarn sun.misc.**
-dontwarn android.**
-dontwarn dalvik.**
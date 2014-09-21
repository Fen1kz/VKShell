package main;

public final class Consts {

    public static final String VK_API_VERSION = "5.24";
    //https://vk.com/dev/auth_mobile
    public static final String AUTH_BASE_URI = "https://oauth.vk.com/authorize";
    public static final String AUTH_APP_ID = "4498629";
    public static final String AUTH_PERMISSIONS;
    public static final String AUTH_REDIRECT_URI = "https://oauth.vk.com/blank.html";
    public static final String AUTH_DISPLAY = "wap";
    public static final String AUTH_RESPONSE_TYPE = "token";

    //https://vk.com/dev/permissions
    public static final int PERMISSION_NOTIFY = 1; // Пользователь разрешил отправлять ему уведомления.
    public static final int PERMISSION_FRIENDS = 2; // Доступ к друзьям.
    public static final int PERMISSION_PHOTOS = 4; // Доступ к фотографиям.
    public static final int PERMISSION_AUDIO = 8; // Доступ к аудиозаписям.
    public static final int PERMISSION_VIDEO = 16; // Доступ к видеозаписям.
    public static final int PERMISSION_DOCS = 131072; // Доступ к документам.
    public static final int PERMISSION_NOTES = 2048; // Доступ к заметкам пользователя.
    public static final int PERMISSION_PAGES = 128; // Доступ к wiki-страницам.
    public static final int PERMISSION_LEFT = 256; // Добавление ссылки на приложение в меню слева.
    public static final int PERMISSION_STATUS = 1024; // Доступ к статусу пользователя.
    public static final int PERMISSION_OFFERS = 32; // Доступ к предложениям (устаревшие методы.
    public static final int PERMISSION_QUESTIONS = 64; // Доступ к вопросам (устаревшие методы.
    public static final int PERMISSION_WALL = 8192; // Доступ к обычным и расширенным методам работы со стеной.
    public static final int PERMISSION_GROUPS = 262144; // Доступ к группам пользователя.
    public static final int PERMISSION_MESSAGES = 4096; // (для Standalone-приложений Доступ к расширенным методам работы с сообщениями.
    public static final int PERMISSION_EMAIL = 4194304; // Доступ к email пользователя. Доступно только для сайтов.
    public static final int PERMISSION_NOTIFICATIONS = 524288; // Доступ к оповещениям об ответах пользователю.
    public static final int PERMISSION_STATS = 1048576; // Доступ к статистике групп и приложений пользователя, администратором которых он является.
    public static final int PERMISSION_ADS = 32768; // Доступ к расширенным методам работы с рекламным API.
    public static final int PERMISSION_OFFLINE = 65536; // Доступ к API в любое время со стороннего сервера (при использовании этой опции параметр expires_in, возвращаемый вместе с access_token, содержит 0 — токен бессрочный.

    //public static final int PERMISSION_NOHTTPS                    Возможность осуществлять запросы к API без HTTPS.

    static {
        AUTH_PERMISSIONS = String.valueOf(
                PERMISSION_AUDIO
                        + PERMISSION_WALL
                        + PERMISSION_NOTIFY
                        + PERMISSION_GROUPS
        );
    }
}

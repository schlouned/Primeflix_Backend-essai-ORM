package com.BackPrimeflix.constants;

public class WebConstants {
    //user
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ADMIN_ROLE = "ADMIN";
    public static final String USER_CLIENT_ROLE = "CLIENT";
    public static final String USER_STOREKEEPER_ROLE = "STOREKEEPER";
    public static final String USER_MARKETINGMANAGER_ROLE = "MARKETINGMANAGER";
    public static final String USER_LAST_NAME = "lastName";
    public static final String USER_FIRST_NAME = "firstName";
    public static final String USER_ADDRESS = "address";
    public static final String USER_STREET = "street";
    public static final String USER_HOUSE_NUMBER = "houseNumber";
    public static final String USER_ZIP_CODE = "zipCode";
    public static final String USER_CITY = "city";
    public static final String USER_COUNTRY_CODE = "countryCode";

    // STORE
    public static final String COMPANY_EMAIL = "schlouned@isl-edu.be";
    public static final String COMPANY_NAME = "companyName";
    public static final String COMPANY_NAME_VALUE = "Primeflix";


    // Domains
    public static final String ALLOWED_URL = "https://localhost:4200";

    //email
    public static final String EmailDenis = "primeflixdenis@hotmail.com";
    public static final String EmailVerificationUrl = "/registration/accountVerificationByEmail";

    //openstreetmap
    public static final String urlGPSCalculation = "https://nominatim.openstreetmap.org/search?";
    public static final String urlGPSStreet="street=";
    public static final String urlGPSZipCode ="&postalcode=";
    public static final String urlGPSCity="&city=";
    public static final String urlGPSCountryCode="&countrycodes=";
    public static final String urlGPSFormat="&format=json&limit=1";

    //deliveryType
    public static final String DELIVERY_CODE = "DELIVERY";
    public static final String PICKUP_CODE = "PICKUP";

    //order state
    public static final String ORDER_STATE_VALIDATED = "VALIDATED";
    public static final String ORDER_STATE_PAYED = "PAYED";
    public static final String ORDER_STATE_PREPARED = "PREPARED";
    public static final String ORDER_STATE_DELIVERY_IN_PROGRESS = "DELIVERY_IN_PROGRESS";
    public static final String ORDER_STATE_DELIVERED = "DELIVERED";

    //stripe keys
    public static final String STRIPE_SECRET_KEY = "sk_test_51JvkfnGBfErLrOJIWiYipaNo4djwBEWXNPCH1jWrPn88ygKRssGG2OAGZse6KADtEFfBC4l37S4zh5oKf473KJCh0099wwD0oY";
    public static final String STRIPE_PUBLIC_KEY = "pk_test_51JvkfnGBfErLrOJISXZk0HhedtblObwnBKsF2Q5YfHuscSeJSwqH73eDzLyViNFvLxYTAzbmUF2yMLaWYPUTTjGv00VwVyaoPt";

    //order 0
    public static final String STOCK_0 = "STOCK_0";






}

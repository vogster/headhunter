package com.unlogicon.headhunter;

/**
 * Created by nik on 04.09.14.
 */
public class Constants {

    public static final String BASE_URL = "https://api.hh.ru/";

    public static final String CLIENT_ID = "UVB7K29EDNOME4DIISJUJJ26NAB451LTEM08B7UC10TUDVCT392BC3EJUCUARQO1";
    public static final String CLIENT_SECRET = "U3T8S8K8A7NCRH1NFJDOH596I3C840MM30L4U0OA5DS2552KDUPGMFNI1ICQR6HB";

    public static final String REDIRECT_URL = "unlogicon.ru";

    public static final String AUTHORIZATION_URL = "https://m.hh.ru/oauth/authorize?response_type=code&client_id="
            + CLIENT_ID;

    public static final String TOKEN_URL = "https://m.hh.ru/oauth/token/";

    public static final String REFRESH_TOKEN = "https://m.hh.ru/oauth/token\n" +
            "grant_type=refresh_token&refresh_token=";

}
